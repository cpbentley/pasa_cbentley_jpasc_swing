/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.SortOrder;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyAbstract;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyWallet;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperMinMaxDouble;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountFullData;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountWalletMinMaxBalance;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountWalletBalance extends TablePanelAccountAbstractMinMaxDouble implements IMyTab {

   public static final String KEY = "list_my_rich";

   public TablePanelAccountWalletBalance(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, root, KEY);
   }

   protected PanelHelperKeyAbstract createPanelHelperKey() {
      return new PanelHelperKeyWallet(psc, this, this);
   }

   protected PanelHelperMinMaxDouble createPanelMinMaxDouble() {
      PanelHelperMinMaxDouble panel = new PanelHelperMinMaxDouble(psc,this,"balance");
      panel.setNullIfEmpty();
      return panel;
   }

   /**
    * Sorts the table in {@link SortOrder#DESCENDING}
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      sortTableColDescending(ModelTableAccountFullData.INDEX_03_BALANCE);
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountWalletMinMaxBalance worker = new WorkerTableAccountWalletMinMaxBalance(psc, this, getTableModel());
      setWorkerData(worker);
      return worker;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountWalletBalance");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountWalletBalance");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}