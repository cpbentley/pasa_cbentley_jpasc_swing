/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.SortOrder;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.ctx.IEventsPCore;
import pasa.cbentley.jpasc.pcore.ctx.ITechPascRPC;
import pasa.cbentley.jpasc.swing.cellrenderers.CellRendereManager;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperSwap;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountRangePrices;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountSwapAccount;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountChainStatusType;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Table panel to display accounts being in any kind of swap on the chain.
 * 
 * <li>Provides a panel for swap type filtering
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountChainSwapAccount extends TablePanelAccountAbstract implements IMyTab, IEventConsumer {

   public static final String KEY              = "list_swapaccount";

   /**
    * 
    */
   private static final long  serialVersionUID = -2766201426771646733L;

   protected PanelHelperSwap  panelSwapHelper;

   public TablePanelAccountChainSwapAccount(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, KEY, root);

      //register for events
      psc.getPCtx().getEventBusPCore().addConsumer(this, IEventsPCore.PID_1_BLOCK_OPS, IEventsPCore.EID_1_BLOCK_OPS_1_PASA_TRADE);

   }

   /**
    * special behavior for table refresh. only refresh is new blocks have sell/buy accounts operations
    */
   public void consumeEvent(BusEvent e) {

   }

   protected PanelHelperSwap createPanelHelperSwap() {
      return new PanelHelperSwap(psc, this);
   }

   /**
    * Override for an another kind of 
    */
   protected ModelTableAccountAbstract createTableModel() {
      return new ModelTableAccountSwapAccount(psc);
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountChainStatusType worker = new WorkerTableAccountChainStatusType(psc, this, getTableModel());
      worker.setStatusType(ITechPascRPC.STATUS_TYPE_FOR_SWAP_ACCOUNT);
      return worker;
   }

   /**
    * Sorts the table in {@link SortOrder#DESCENDING}
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      sortTableColDescending(ModelTableAccountRangePrices.INDEX_09_PRICE);
   }

   protected void setWorkerData(WorkerTableAccountChainStatusType worker) {
      if (panelSwapHelper != null) {
         String statusType = panelSwapHelper.getSwapSearchType();
         worker.setStatusType(statusType);
      }
   }

   /**
    * Overrides to add swap account menu items
    */
   protected void subPopulatePopMenu(BPopupMenu menu) {
      psc.getPascalBPopupMenuFactory().addSwapAccountMenuItems(menu, this, this);
   }

   /**
    * 
    */
   protected void subSetColumnRenderers() {
      CellRendereManager rm = psc.getCellRendereManager();
      ModelTableAccountSwapAccount model = (ModelTableAccountSwapAccount) getTableModel();
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, TablePanelAccountChainSwapAccount.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, TablePanelAccountChainSwapAccount.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   


}