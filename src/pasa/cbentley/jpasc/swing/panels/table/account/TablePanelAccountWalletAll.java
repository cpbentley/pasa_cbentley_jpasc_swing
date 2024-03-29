/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountWalletAll;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountWalletAll extends TablePanelAccountAbstractAll implements IMyTab {

   public static final String KEY              = "wallet_accounts";

   /**
    * 
    */
   private static final long  serialVersionUID = -3040964171971420256L;

   public TablePanelAccountWalletAll(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, root, KEY);
      //sorting policy? we don't want any sort
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountWalletAll worker = new WorkerTableAccountWalletAll(psc, this, getTableModel());
      return worker;
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItems(menu);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountWalletAll");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountWalletAll");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}