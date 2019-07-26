/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.whalewatch;

import java.awt.event.ActionListener;

import javax.swing.Icon;

import com.github.davidbolet.jpascalcoin.api.model.OperationSubType;

import pasa.cbentley.jpasc.pcore.filter.operation.FilterOperationBySubtype1OrSubtype2;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.ListTaskBlockAbstract;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationsByFilter;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * Lists pasas that made any operations during the last X Blocks
 * @author Charles Bentley
 *
 */
public class PanelWhaleWatchPasaActive extends PanelWhaleWatchAbstract implements IMyTab, ActionListener {
   private Icon iconWhalePasa;

   public PanelWhaleWatchPasaActive(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "whale_pasa_list", root);
      iconWhalePasa = psc.createImageIcon("/icons/pasc_whale_pinkblue_16.png", "");

   }

   public void disposeTab() {
      super.disposeTab();
   }

   public Icon getTabIcon(int size) {
      return iconWhalePasa;
   }

   public String getTabTip() {
      return "Lists pasas that made any operations during the last X Blocks";
   }

   public String getTabTitle() {
      return "Pasa List Watch";
   }

   public void initTab() {
      super.initTab();
   }

   protected WorkerTableOperationAbstract createWorker() {
      OperationSubType subtype1 = OperationSubType.BUYACCOUNT_BUYER;
      OperationSubType subtype2 = OperationSubType.BUYACCOUNT_SELLER;
      FilterOperationBySubtype1OrSubtype2 filter = new FilterOperationBySubtype1OrSubtype2(psc.getPCtx(), subtype1, subtype2);
      ListTaskBlockAbstract listBlocks = getBlockTaskList();
      WorkerTableOperationsByFilter worker = new WorkerTableOperationsByFilter(psc, getTableModel(), this, filter, listBlocks );
      return worker;
   }

}