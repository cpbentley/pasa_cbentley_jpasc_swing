/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyAbstract;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyWallet;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperMinMaxInteger;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountWalletAge;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountWalletAge extends TablePanelAccountAbstractMinMaxInteger implements IMyTab {

   public static final String KEY = "wallet_age";

   public TablePanelAccountWalletAge(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, root, KEY);
   }

   protected PanelHelperKeyAbstract createPanelHelperKey() {
      return new PanelHelperKeyWallet(psc, this, this);
   }

   protected PanelHelperMinMaxInteger createPanelMinMaxInteger() {
      PanelHelperMinMaxInteger panel = new PanelHelperMinMaxInteger(psc, this, "blockage",this);
      panel.setNullIfEmpty();
      return panel;
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountWalletAge worker = new WorkerTableAccountWalletAge(psc, this, getTableModel());
      super.setWorkerData(worker);
      return worker;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountWalletAge");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountWalletAge");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}