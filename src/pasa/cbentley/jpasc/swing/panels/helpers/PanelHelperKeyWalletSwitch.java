/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.ComboModelMapPublicKeyJava;
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

   private BCheckBox                       cbAllowDaemonPublic;

   
   public PanelHelperKeyWalletSwitch(PascalSwingCtx psc, IStringPrefIDable idable, ICommandableRefresh refresh) {
      super(psc, idable, refresh);
   }

   public void buildUI() {
      super.buildUI();
      
      cbAllowDaemonPublic = new BCheckBox(sc, this, "cb.allowpublic");
      this.add(labPublicKey);
      this.add(comboKeys);
      this.add(cbAllowDaemonPublic);

   }

   
   public boolean isCanUse() {
      return !cbAllowDaemonPublic.isSelected();
   }
   
   protected ComboModelMapPublicKeyJava createModel() {
      return psc.getModelProviderPublicJavaKeyPrivate().createPublicKeyJavaPrivate();
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
