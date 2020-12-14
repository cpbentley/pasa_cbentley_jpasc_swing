/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.PublicKeyJavaManager;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.filter.SetFilterKey;
import pasa.cbentley.jpasc.pcore.filter.publickeyjava.FilterKeyJavaEmpty;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.ComboModelMapPublicKeyJava;
import pasa.cbentley.jpasc.swing.models.IComboModelMapPublicKeyJavaListener;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.jpasc.swing.widgets.PublicKeyJavaComboBoxCached;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.interfaces.IStringPrefIDable;
import pasa.cbentley.swing.widgets.b.BLabel;

/**
 * Base Panel for selecting a {@link PublicKeyJava}.
 * 
 * <li> that displays a Drop down combo box of keys.
 * 
 * Can be disabled by default
 * 
 * @author Charles Bentley
 *
 */
public abstract class PanelHelperKeyAbstract extends PanelPascal implements ActionListener, IComboModelMapPublicKeyJavaListener {

   /**
    * Possibly null
    */
   protected PublicKeyJavaComboBoxCached comboKeys;

   protected IStringPrefIDable           idable;

   private boolean                       isDefChangeKeySelected;

   /**
    * When true, shows a drop down combo
    */
   private boolean                       isDropDownComboEnabled;

   protected boolean                     isShowLabel;

   private boolean                       isWildcarded;

   protected BLabel                      labPublicKey;

   protected ICommandableRefresh         refresh;

   public PanelHelperKeyAbstract(PascalSwingCtx psc, IStringPrefIDable idable, ICommandableRefresh refresh) {
      super(psc);
      this.idable = idable;
      this.refresh = refresh;
      isDropDownComboEnabled = true;

      //TODO we want to listen to key events? to add and remove keys?
   }

   public void actionPerformed(ActionEvent e) {
      //#debug
      toDLog().pFlow(sc.toSD().d1(e), this, PanelHelperKeyAbstract.class, "actionPerformed", LVL_05_FINE, true);

      //only refresh if its an explicit user action
      boolean isUpdating = comboKeys.getModelKey().isUpdating();
      if (isUpdating) {
         //we don't want a refresh when model is simply updating
         return;
      }
      PublicKeyJava pk = comboKeys.getSelectedKeyJava();
      if (pk != null) {
         psc.getPascPrefs().put(getComboPrefString(), pk.getName());
      }
      if (refresh != null) {
         refresh.cmdRefresh(this);
      }
   }

   public void buildUI() {
      if (isBuilt()) {
         //issue warning TODO Dev warn disparu?
         //#debug
         toDLog().pNull("Object already built", this, PanelHelperKeyAbstract.class, "initUI", LVL_09_WARNING, true);
         return;
      }
      String labelKey = this.labelKey;
      if (labelKey == null) {
         labelKey = "text.publickey";
      }
      labPublicKey = new BLabel(sc, labelKey);

      if (isDropDownComboEnabled) {
         ComboModelMapPublicKeyJava model = createModel();
         comboKeys = new PublicKeyJavaComboBoxCached(psc, this, model);
         comboKeys.setMaxVisible();
         if (model.isDataLoaded()) {
            selectPreviousKey();
         } else {
            //TODO if data model is finished loading during if call.. listener is useless. very rare case.
            model.addListenerComboMap(this);
         }
      }
   }

   public void refreshModel() {
      if (isDropDownComboEnabled) {
         ComboModelMapPublicKeyJava model = createModel();
         comboKeys.setModelCombo(model);
         if (model.isDataLoaded()) {
            selectPreviousKeyWithEvent(comboKeys);
         } else {
            //TODO if data model is finished loading during if call.. listener is useless. very rare case.
            model.addListenerComboMap(this);
         }
      }
   }

   /**
    * Create the model with its data for the Combobox.
    * The model might not be populated.. so an event is required for this.
    * At least one row gives a message if full empty or if loading key
    * @return
    */
   protected abstract ComboModelMapPublicKeyJava createModel();

   /**
    * Depends on the ID of this helper
    * @return
    */
   private String getComboPrefString() {
      return idable.getSelectorKeyPrefID() + ".combochoice";
   }

   /**
    * Get the {@link SetFilterKey} based on current selection.
    * @return
    */
   public SetFilterKey getFilterSet() {
      PublicKeyJava pk = getSelectedKeyAsPublicKeyJava();
      SetFilterKey filterSet = null;
      if (pk != null) {
         filterSet = new SetFilterKey(psc.getPCtx());
         //wildcard
         if (pk.isWildcard()) {
            PublicKeyJavaManager pkManager = psc.getPCtx().getPublicKeyJavaManager();
            if (pk == pkManager.getAll()) {
               //do not set any... null equals all keys
            } else if (pk == pkManager.getEmpties()) {
               filterSet.addFilter(new FilterKeyJavaEmpty(psc.getPCtx()));
            }
         } else {
            //check if well formed
            if (pk.isWellFormed()) {
               filterSet.addKey(pk);
            } else {
               //#debug
               toDLog().pNull("PublicKeyJava not well formed", this, PanelHelperKeyAbstract.class, "getFilterSet", LVL_05_FINE, true);
            }
         }
      }
      return filterSet;
   }

   /**
    * Returns null for all
    * @return
    */
   public PublicKeyJava getSelectedKeyAsPublicKeyJava() {
      PublicKeyJava key = null;
      if (comboKeys != null) {
         key = comboKeys.getSelectedKeyJava();
      }
      return key;
   }

   /**
    * Automatically maps {@link PublicKeyJava} to a {@link PublicKey}
    * Returns null for all
    * @return
    */
   public PublicKey getSelectedKeyPublicKey() {
      PublicKeyJava selectedKeyAsPublicKeyJava = getSelectedKeyAsPublicKeyJava();
      //
      return psc.getPCtx().getDomainMapper().mapPublicKeyJava(selectedKeyAsPublicKeyJava);
   }

   public boolean isBuilt() {
      return comboKeys != null;
   }

   public boolean isKeySelectionEnabled() {
      return isDropDownComboEnabled;
   }

   public void modelDidFinishLoading(ComboModelMapPublicKeyJava model) {
      //#debug
      toDLog().pFlow("", model, PanelHelperKeyAbstract.class, "modelDidFinishLoading", LVL_05_FINE, true);

      selectPreviousKey();
   }

   private void selectPreviousKey() {
      if (comboKeys != null) {
         //select the previously selected key
         String keySelected = psc.getPascPrefs().get(getComboPrefString(), "");
         if (keySelected.equals("")) {
            comboKeys.setSelectedIndex(0);
         } else {
            comboKeys.setSelectedKeyNoEvent(keySelected);
         }
      }
   }

   private void selectPreviousKeyWithEvent(PublicKeyJavaComboBoxCached comboKeys) {
      if (comboKeys != null) {
         //select the previously selected key
         String keySelected = psc.getPascPrefs().get(getComboPrefString(), "");
         if (keySelected.equals("")) {
            comboKeys.setSelectedIndex(0);
         } else {
            comboKeys.setSelectedKeyWithEvent(keySelected);
         }
      }
   }

   /**
    * 
    * @param isDefChangeKeySelected
    */
   public void setEnabledKeyChoice(boolean isDefChangeKeySelected) {
      this.isDefChangeKeySelected = isDefChangeKeySelected;
      if (isBuilt()) {
         //update 
      }
   }

   public void setKeySelectionEnabled(boolean isKeySelectionEnabled) {
      this.isDropDownComboEnabled = isKeySelectionEnabled;
   }

   public boolean isWildcarded() {
      return isWildcarded;
   }

   private String labelKey;

   public void setWildcarded(boolean isWildcarded) {
      this.isWildcarded = isWildcarded;
   }

   public void setLabelTextKey(String key) {
      labelKey = key;
      if (labPublicKey != null) {
         labPublicKey.setKey(key);
      }
   }

   /**
    * Called when a command wants to set a new pub key to be shown.
    * 
    * The refresh will be called when the pane gets the focus?
    * 
    * If the key is not found what we do?
    * 
    * @param encPubKey
    */
   public void setPublicKeyEncNoEvent(String encPubKey) {
      if (comboKeys != null) {
         comboKeys.setSelectedEncPubKeyNoEvent(encPubKey);
      }
   }

   /**
    * Only accept key that can be used
    * @param pk
    */
   public void setPublicKeyNoEvent(PublicKeyJava pk) {
      if (comboKeys != null) {
         comboKeys.setSelectedKeyNoEvent(pk.getName());
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelKeyHelperAbstract");
      toStringPrivate(dc);
      super.toString(dc.sup());

      PublicKeyJava pkj = getSelectedKeyAsPublicKeyJava();
      dc.nlLvl(pkj, "SelectedKeyJava");

      dc.nl();
      dc.append("PrefComboString key" + getComboPrefString() + " value=" + psc.getPascPrefs().get(getComboPrefString(), ""));

      dc.nlLvl(comboKeys, "comboKeys");

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelKeyHelperAbstract");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      if (comboKeys != null) {
         dc.append("Selection=" + comboKeys.getSelectedKeyString());

      }
   }

   //#enddebug

}
