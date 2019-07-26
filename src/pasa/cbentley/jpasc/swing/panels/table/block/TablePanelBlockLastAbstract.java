/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.block;

import javax.swing.JPanel;

import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperRefresh;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockFullData;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaChain;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.jpasc.swing.workers.table.block.WorkerTableBlocksInThePast;
import pasa.cbentley.swing.model.ModelTableBAbstract;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public abstract class TablePanelBlockLastAbstract extends TablePanelBlockAbstract {

   /**
    * 
    */
   private static final long  serialVersionUID        = 3700280270517173649L;


   private static final int   STAT_INDEX_0_NUM_BLOCKS = 0;

   private static final int   STAT_INDEX_1_NUM_OPS    = 1;

   private static final int   STAT_INDEX_2_FEES       = 2;

   private static final int   STAT_INDEX_3_AVERAGE_HS = 3;

   private int              numBlocksInThePast;

   public TablePanelBlockLastAbstract(PascalSwingCtx psc, String ID, int numBlocksInThePast) {
      super(psc, ID);
      if(numBlocksInThePast <= 0 ) {
         this.numBlocksInThePast = 100;
      } else {
         this.numBlocksInThePast = numBlocksInThePast;
      }
   }

   protected WorkerListTaskPage createWorker() {
      return new WorkerTableBlocksInThePast(psc, this, getTableModel(), numBlocksInThePast);
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItems(menu);
   }

   protected void subInitPanelSouth(JPanel south) {
      panelRefresh = new PanelHelperRefresh(psc, this);
      statPanel.setCompProgressBefore(panelRefresh);
      statPanel.resetToSize(4); //4 stats here
      statPanel.set(STAT_INDEX_0_NUM_BLOCKS, "stat.blocks", 6);
      statPanel.set(STAT_INDEX_1_NUM_OPS, "stat.ops", 8);
      statPanel.set(STAT_INDEX_2_FEES, "stat.fee", 8);
      statPanel.set(STAT_INDEX_3_AVERAGE_HS, "stat.averagehs", 8);
      statPanel.addToPanelSerially(south); //boilerplate for linking widgets
      south.add(statPanel);
   }

   protected void subUpdateStatPanel() {
      ModelTableBlockAbstract model = getTableModel();
      statPanel.setStat(STAT_INDEX_0_NUM_BLOCKS, model.getTotalBlocks());
      statPanel.setStat(STAT_INDEX_1_NUM_OPS, model.getTotalOps());
      statPanel.setStat(STAT_INDEX_2_FEES, model.getTotalFees());
      statPanel.setStat(STAT_INDEX_3_AVERAGE_HS, model.getAverageHashRate());
   }

   protected ModelTableBAbstract<Block> createTableModel() {
      return new ModelTableBlockFullData(psc);
   }

}
