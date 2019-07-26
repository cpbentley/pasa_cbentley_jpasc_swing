/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.JPanel;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyAbstract;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyWallet;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperName;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperNameWallet;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountWalletNames;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountWalletName extends TablePanelAccountAbstractKeyName implements IMyTab {

   private static final String KEY              = "list_my_names";

   /**
    * 
    */
   private static final long   serialVersionUID = -3040964171971420256L;

   /**
    * 
    * @param psc
    * @param root
    * @param id
    */
   public TablePanelAccountWalletName(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, KEY, root);
   }

   protected PanelHelperName createPanelHelperName() {
      return new PanelHelperNameWallet(psc, this);
   }
   
   /**
    * Returns the helper bar that controls this table
    * @return
    */
   public PanelHelperNameWallet getPanelNameHelperWallet() {
      return (PanelHelperNameWallet) getPanelNameHelper();
   }
   
   protected PanelHelperKeyAbstract createPanelHelperKey() {
      return new PanelHelperKeyWallet(psc, this, this);
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountWalletNames worker = new WorkerTableAccountWalletNames(psc,this,getTableModel());
      setWorkerData(worker);
      return worker;
   }

   protected void subInitPanelNorth(JPanel north) {
      super.subInitPanelNorth(north); //adds panel refresh
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItems(menu);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountWalletName");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountWalletName");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

 

}