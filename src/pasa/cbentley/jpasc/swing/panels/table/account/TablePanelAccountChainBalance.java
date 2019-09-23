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
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyChain;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperMinMaxDouble;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountFullData;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountChainMinMaxBalance;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountWalletMinMaxBalance;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Table listing rich accounts on chain
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountChainBalance extends TablePanelAccountAbstractMinMaxDouble implements IMyTab {

   /**
    * 
    */
   private static final long  serialVersionUID = 1811148107819629998L;

   public static final String KEY              = "list_rich";

   public TablePanelAccountChainBalance(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, root, KEY);
      isKeyFilteringEnabled = false; //we don't want by default
   }

   protected PanelHelperKeyAbstract createPanelHelperKey() {
      return new PanelHelperKeyChain(psc, this, this);
   }

   protected PanelHelperMinMaxDouble createPanelMinMaxDouble() {
      PanelHelperMinMaxDouble panel = new PanelHelperMinMaxDouble(psc, this, "balance");
      panel.setNullIfEmpty();
      return panel;
   }
   
   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItemsNoSendNoKey(menu);
   }


   /**
    * Sorts the table in {@link SortOrder#DESCENDING}
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      sortTableColDescending(ModelTableAccountFullData.INDEX_03_BALANCE);
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountChainMinMaxBalance worker = new WorkerTableAccountChainMinMaxBalance(psc, this, getTableModel());
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