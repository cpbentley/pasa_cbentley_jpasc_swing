/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.ComboModelMapPublicKeyJava;
import pasa.cbentley.jpasc.swing.panels.table.abstrakt.TablePanelAbstract;
import pasa.cbentley.swing.cmd.ICommandableRefresh;

/**
 * Panel that displays a Drop down combo box of keys
 * and a text field for copy paste a encoded key
 * 
 * @author Charles Bentley
 *
 */
public class PanelHelperKeyGlobal extends PanelHelperKeyAbstract {

   /**
    * 
    */
   private static final long serialVersionUID = 5413343421740692468L;

   public PanelHelperKeyGlobal(PascalSwingCtx psc, TablePanelAbstract owner, ICommandableRefresh refresh) {
      super(psc, owner, refresh);
   }

   public void buildUI() {
      super.buildUI();
      this.add(labPublicKey);
      if(comboKeys != null) {
         this.add(comboKeys);
      }
   }

   protected ComboModelMapPublicKeyJava createModel() {
      return psc.getModelProviderPublicJavaKeyPrivate().createModelPublicKeyJava();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelHelperKeyGlobal");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelHelperKeyGlobal");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
