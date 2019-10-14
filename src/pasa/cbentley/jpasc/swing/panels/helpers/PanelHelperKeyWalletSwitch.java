/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.ComboModelMapPublicKeyJava;
import pasa.cbentley.jpasc.swing.models.ModelProviderPublicJavaKeyAbstract;
import pasa.cbentley.jpasc.swing.models.ModelProviderPublicJavaKeyPrivate;
import pasa.cbentley.jpasc.swing.models.ModelProviderPublicJavaKeyPublic;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.interfaces.IStringPrefIDable;
import pasa.cbentley.swing.widgets.b.BCheckBox;

/**
 * Panel that displays a Drop down combo box of wallet keys.
 * 
 * with a switch between can use and cannot use
 * 
 * @author Charles Bentley
 *
 */
public class PanelHelperKeyWalletSwitch extends PanelHelperKeyAbstract {

   /**
    * 
    */
   private static final long serialVersionUID = -6390888337199401137L;

   /**
    * Possibly null
    */
   private BCheckBox         cbAllowDaemonPublic;

   private boolean           isDefaultPublic;

   private boolean           isCurrentPublic;

   public PanelHelperKeyWalletSwitch(PascalSwingCtx psc, IStringPrefIDable idable, ICommandableRefresh refresh) {
      super(psc, idable, refresh);
   }

   public void buildUI() {
      isCurrentPublic = isDefaultPublic;
      super.buildUI();
      if (comboKeys != null) {
         cbAllowDaemonPublic = new BCheckBox(sc, this, "cb.allowpublic");
         cbAllowDaemonPublic.setSelected(isDefaultPublic);
         this.add(labPublicKey);
         this.add(comboKeys);
         this.add(cbAllowDaemonPublic);
      }
   }

   public boolean isDefaultPublic() {
      return isDefaultPublic;
   }

   public void setDefaultPublic(boolean isDefaultPublic) {
      this.isDefaultPublic = isDefaultPublic;
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == cbAllowDaemonPublic) {
         isCurrentPublic = cbAllowDaemonPublic.isSelected();
         String key = idable.getSelectorKeyPrefID() + ".combochoice";
         super.refreshModel();
      } else {
         super.actionPerformed(e);
      }

   }

   public boolean isCanUse() {
      return !isCurrentPublic;
   }

   protected ComboModelMapPublicKeyJava createModel() {
      ModelProviderPublicJavaKeyAbstract provider = null;
      
      if (isCanUse()) {
         provider = psc.getModelProviderPublicJavaKeyPrivate();
      } else {
         provider = psc.getModelProviderPublicJavaKeyPublic();
      }
      
      if (isWildcarded()) {
         return provider.createModelPublicKeyJavaWildcards();
      } else {
         return provider.createModelPublicKeyJava();
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelHelperKeyWalletSwitch");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelHelperKeyWalletSwitch");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
