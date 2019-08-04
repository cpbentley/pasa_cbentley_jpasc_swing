/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.SortOrder;

import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.ctx.IEventsPCore;
import pasa.cbentley.jpasc.swing.cellrenderers.CellRendereManager;
import pasa.cbentley.jpasc.swing.cmds.BCMenuItem;
import pasa.cbentley.jpasc.swing.cmds.ICommandableAccount;
import pasa.cbentley.jpasc.swing.cmds.ICommandableKey;
import pasa.cbentley.jpasc.swing.cmds.PascalCmdManager;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyAbstract;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyGlobal;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperMinMaxDouble;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountRangePrices;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountChainMinMaxPrice;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Table panel to display accounts being sold on the chain.
 * 
 * <li>Provides a panel for chain key filtering
 * <li>Provides a panel for filtering accounts based on price
 * <li>Provides a panel for filtering accounts based on name TODO
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountChainPrice extends TablePanelAccountAbstractMinMaxDouble implements IMyTab, IEventConsumer {

   public static final String KEY              = "list_price";

   /**
    * 
    */
   private static final long  serialVersionUID = -2766201426771646733L;

   public TablePanelAccountChainPrice(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, root, KEY);
      isKeyFilteringEnabled = false; //we don't want by default

      //register for events

      psc.getPCtx().getEventBusPCore().addConsumer(this, IEventsPCore.PID_1_BLOCK_OPS, IEventsPCore.EID_1_BLOCK_OPS_1_PASA_TRADE);
      
   }

   protected PanelHelperKeyAbstract createPanelHelperKey() {
      return new PanelHelperKeyGlobal(psc, this, this);
   }

   /**
    * We define 
    * <li> Show Account Being Sold in Inspector
    * <li> Show Seller Account in Inspector
    */
   protected void subPopulatePopMenu(BPopupMenu menu) {
      SwingCtx sc = psc.getSwingCtx();
      PascalCmdManager pcm = psc.getCmds();

      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdShowAccountInInspectorTab()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdShowAccountSellerInInspectorTab()));
      menu.addSeparator();
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdShowAccountInInspectorWin()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdShowAccountSellerInInspectorWin()));
      menu.addSeparator();
      menu.add(new BCMenuItem<ICommandableKey>(sc, this, pcm.getCmdKeyChangeName()));
      
   }
   
   /**
    * Override for an another kind of 
    */
   protected ModelTableAccountAbstract createTableModel() {
      return new ModelTableAccountRangePrices(psc);
   }

   protected PanelHelperMinMaxDouble createPanelMinMaxDouble() {
      PanelHelperMinMaxDouble panel = new PanelHelperMinMaxDouble(psc, this, "price");
      panel.setNullIfEmpty();
      return panel;
   }

   protected WorkerTableAccountAbstract createWorker() {
      //#debug
      toDLog().pFlow("WorkerTableAccountChainMinMaxPrice", this, TablePanelAccountChainPrice.class, "createWorker", LVL_05_FINE, true);

      WorkerTableAccountChainMinMaxPrice worker = new WorkerTableAccountChainMinMaxPrice(psc, this, getTableModel());
      setWorkerData(worker);
      return worker;
   }

   /**
    * special behavior for table refresh. only refresh is new blocks have sell/buy accounts operations
    */
   public void consumeEvent(BusEvent e) {
      
   }

   /**
    * 
    */
   protected void subSetColumnRenderers() {
      CellRendereManager rm = psc.getCellRendereManager();
      getBenTable().setColumnRenderer(getColumnIndexAccount(), rm.getCellRendererAccountSoldContiguous());
      int tableIndexRangeCount = ModelTableAccountRangePrices.INDEX_12_RANGE_SIZE;
      getBenTable().setColumnRenderer(tableIndexRangeCount, rm.getCellRendererAccountContiguousCount());
   }

   /**
    * Sorts the table in {@link SortOrder#DESCENDING}
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      sortTableColDescending(ModelTableAccountRangePrices.INDEX_08_PRICE);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountWalletPrice");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountWalletPrice");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}