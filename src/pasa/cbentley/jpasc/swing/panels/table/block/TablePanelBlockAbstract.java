/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.block;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.jpasc.swing.cellrenderers.CellRendereManager;
import pasa.cbentley.jpasc.swing.cmds.ICommandableBlock;
import pasa.cbentley.jpasc.swing.cmds.PascalCmdManager;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.account.PanelAccountDetails;
import pasa.cbentley.jpasc.swing.panels.table.abstrakt.TablePanelAbstract;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByBlock;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BCMenuItem;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public abstract class TablePanelBlockAbstract extends TablePanelAbstract<Block> implements ICommandableBlock {

   protected IRootTabPane root;

   public TablePanelBlockAbstract(PascalSwingCtx psc, String internalID, IRootTabPane root) {
      super(psc, internalID);
      this.root = root;
   }

   /**
    * Called by a sub class when it does not have a custom pop up menu of commands for keys
    * @param menu
    */
   public void addDefaultAccountMenuItems(BPopupMenu menu) {
      SwingCtx sc = psc.getSwingCtx();
      PascalCmdManager pcm = psc.getCmds();

      menu.add(new BCMenuItem<ICommandableBlock>(sc, this, pcm.getCmdBlockShowOperationsHome()));
      menu.add(new BCMenuItem<ICommandableBlock>(sc, this, pcm.getCmdBlockShowOperationsWin()));
      menu.addSeparator();

   }

   public void cmdShowSelectedBlockOperationsNewWindow() {
      //#debug
      toDLog().pFlow("", this, TablePanelBlockAbstract.class, "cmdShowSelectedBlockOperationsNewWindow", LVL_05_FINE, true);
      Block block = getSelectedBlock();
      if (block != null) {
         //which window to use? 
         TablePanelOperationByBlock blockOps = new TablePanelOperationByBlock(psc, root);
         blockOps.showBlock(block);
         psc.getSwingCtx().showInNewFrame(blockOps);
      }
   }

   public void cmdShowSelectedBlockOperationsTabHome() {
      //#debug
      toDLog().pFlow("", this, TablePanelBlockAbstract.class, "cmdShowSelectedBlockOperationsTabHome", LVL_05_FINE, true);
      Block block = getSelectedBlock();
      if (block != null) {
         root.showBlock(block);
      }
   }

   public Block getSelectedBlock() {
      Block ac = null;
      int selectedRow = this.getJTable().getSelectedRow();
      if (selectedRow >= 0) {
         selectedRow = this.getJTable().convertRowIndexToModel(selectedRow);
         ac = this.getTableModel().getRow(selectedRow);
      }
      return ac;
   }

   protected int getColumnIndexBlock() {
      return getTableModel().getColumnIndexBlock();
   }

   protected int getColumnIndexBlockMinerStat() {
      return getTableModel().getColumnIndexMinerStat();
   }

   protected int getColumnIndexBlockTime() {
      return getTableModel().getColumnIndexBlockTime();
   }

   protected int getColumnIndexMiner() {
      return getTableModel().getColumnIndexMiner();
   }

   protected int getColumnIndexOps() {
      return getTableModel().getColumnIndexOps();
   }

   protected int getDefSortColumnIndex() {
      return getColumnIndexBlock();
   }

   public ModelTableBlockAbstract getTableModel() {
      return (ModelTableBlockAbstract) getBenTable().getModel();
   }

   public void setColumnRenderers() {
      CellRendereManager crm = psc.getCellRendereManager();
      getBenTable().setColumnRenderer(getColumnIndexBlock(), crm.getCellRendererBlockInteger());
      getBenTable().setColumnRenderer(getColumnIndexBlockTime(), crm.getCellRendererBlockTime());
      getBenTable().setColumnRenderer(getColumnIndexOps(), crm.getCellRendererBlockOp());
      getBenTable().setColumnRenderer(getColumnIndexMiner(), crm.getCellRendererBlockMiner());
      getBenTable().setColumnRenderer(getColumnIndexBlockMinerStat(), crm.getCellRendererBlockMiner());
   }
}
