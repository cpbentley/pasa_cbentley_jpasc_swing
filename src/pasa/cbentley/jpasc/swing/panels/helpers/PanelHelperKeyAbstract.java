/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.filter.SetFilterKey;
import pasa.cbentley.jpasc.pcore.filter.publickeyjava.FilterKeyJavaEmpty;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.ComboModelMapPublicKeyJava;
import pasa.cbentley.jpasc.swing.models.IComboModelMapPublicKeyJavaListener;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.jpasc.swing.widgets.PublicKeyJavaComboBoxCached;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.interfaces.IStringPrefIDable;
import pasa.cbentley.swing.widgets.b.BLabel;

/**
 * Panel that displays a Drop down combo box of keys.
 * 
 * Can be disabled by default
 * 
 * @author Charles Bentley
 *
 */
public abstract class PanelHelperKeyAbstract extends PanelPascal implements ActionListener, IComboModelMapPublicKeyJavaListener {

   protected PublicKeyJavaComboBoxCached comboKeys;

   private boolean                       isDefChangeKeySelected;

   private boolean                       isKeySelectionEnabled;

   protected boolean                     isShowLabel;

   protected BLabel                      labPublicKey;

   protected IStringPrefIDable           owner;

   protected ICommandableRefresh         refresh;

   public PanelHelperKeyAbstract(PascalSwingCtx psc, IStringPrefIDable idable, ICommandableRefresh refresh) {
      super(psc);
      this.owner = idable;
      this.refresh = refresh;

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
      labPublicKey = new BLabel(sc, "text.publickey");
      labPublicKey.setKeyTip("text.publickey.tip");

      if (isKeySelectionEnabled) {
         ComboModelMapPublicKeyJava model = createModel();
         if (model.isDataLoaded()) {
            selectPreviousKey();
         } else {
            //TODO if data model is finished loading during if call.. listener is useless. very rare case.
            model.setListenerComboMap(this);
         }
         comboKeys = new PublicKeyJavaComboBoxCached(psc, this, model);
         comboKeys.setMaxVisible();
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
      return owner.getSelectorKeyPrefID() + ".combochoice";
   }

   public SetFilterKey getFilterSet() {
      PublicKeyJava pk = getSelectedKeyAsPublicKeyJava();
      SetFilterKey filterSet = null;
      if (pk != null) {
         filterSet = new SetFilterKey(psc.getPCtx());
         if (pk == psc.getModelProviderPublicJavaKey().getPublicKeyJavaAll()) {
            //do not set any... null equals all keys
         } else if (pk == psc.getModelProviderPublicJavaKey().getPublicKeyJavaEmpties()) {
            filterSet.addFilter(new FilterKeyJavaEmpty(psc.getPCtx()));
         } else {
            //check if well formed
            if (pk.getCanUse() != null) {
               filterSet.addKey(pk);
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
      return isKeySelectionEnabled;
   }

   public void modelDidFinishLoading(ComboModelMapPublicKeyJava model) {
      selectPreviousKey();
   }

   private void selectPreviousKey() {
      if (comboKeys != null) {
         //select the previously selected key
         String key = psc.getPascPrefs().get(getComboPrefString(), "");
         comboKeys.setSelectedKeyNoEvent(key);
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
      this.isKeySelectionEnabled = isKeySelectionEnabled;
   }

   public void setLabelTextKey(String key) {
      if (labPublicKey == null) {
         labPublicKey = new BLabel(sc, key);
      } else {
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

      dc.nlLvl(psc.getModelProviderPublicJavaKey());

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
