/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.whalewatch;

import java.awt.event.ActionListener;

import javax.swing.Icon;

import pasa.cbentley.jpasc.pcore.filter.operation.FilterOperationBySubtype1OrSubtype2;
import pasa.cbentley.jpasc.pcore.rpc.model.OperationSubType;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.ListTaskBlockAbstract;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationsByFilter;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * 
 * @author Charles Bentley
 *
 */
public class PanelWhaleWatchPasaListedForSale extends PanelWhaleWatchAbstract implements IMyTab, ActionListener {
   private Icon iconWhalePasa;

   public PanelWhaleWatchPasaListedForSale(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "whale_pasa_new_sale", root);
      iconWhalePasa = psc.createImageIcon("/icons/pasc_whale_blue_16_2.png", "");
   }

   public void disposeTab() {
      super.disposeTab();
   }

   public Icon getTabIcon(int size) {
      return iconWhalePasa;
   }

   public String getTabTip() {
      return "Monitor PASA listed for Sale";
   }

   public String getTabTitle() {
      return "New Pasas Listed for Sale";
   }

   protected WorkerTableOperationAbstract createWorker() {
      OperationSubType subtype1 = OperationSubType.LIST_ACCOUNT_FOR_PUBLIC_SALE;
      OperationSubType subtype2 = OperationSubType.DELIST_ACCOUNT;
      FilterOperationBySubtype1OrSubtype2 filter = new FilterOperationBySubtype1OrSubtype2(psc.getPCtx(), subtype1, subtype2);
      ListTaskBlockAbstract listBlocks = getBlockTaskList();
      WorkerTableOperationsByFilter worker = new WorkerTableOperationsByFilter(psc, getTableModel(), this, filter, listBlocks );
      return worker;
   }
   public void initTab() {
      super.initTab();
   }
}