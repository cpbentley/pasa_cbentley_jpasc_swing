/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.block;

import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.jpasc.swing.cellrenderers.CellRendereManager;
import pasa.cbentley.jpasc.swing.cmds.BCMenuItem;
import pasa.cbentley.jpasc.swing.cmds.ICommandableBlock;
import pasa.cbentley.jpasc.swing.cmds.PascalCmdManager;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.table.abstrakt.TablePanelAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public abstract class TablePanelBlockAbstract extends TablePanelAbstract<Block> implements ICommandableBlock {

   public TablePanelBlockAbstract(PascalSwingCtx psc, String internalID) {
      super(psc, internalID);
   }

   public ModelTableBlockAbstract getTableModel() {
      return (ModelTableBlockAbstract) getBenTable().getModel();
   }

   protected int getColumnIndexOps() {
      return getTableModel().getColumnIndexOps();
   }

   protected int getDefSortColumnIndex() {
      return getColumnIndexBlock();
   }

   protected int getColumnIndexMiner() {
      return getTableModel().getColumnIndexMiner();
   }

   protected int getColumnIndexBlock() {
      return getTableModel().getColumnIndexBlock();
   }

   public void cmdShowSelectedBlockOperationsTabHome() {
      //#debug
      toDLog().pFlow("", this, TablePanelBlockAbstract.class, "cmdShowSelectedBlockOperationsTabHome", LVL_05_FINE, true);

   }

   public void cmdShowSelectedBlockOperationsNewWindow() {
      //#debug
      toDLog().pFlow("", this, TablePanelBlockAbstract.class, "cmdShowSelectedBlockOperationsNewWindow", LVL_05_FINE, true);

   }

   public void setColumnRenderers() {
      CellRendereManager crm = psc.getCellRendereManager();
      getBenTable().setColumnRenderer(getColumnIndexBlock(), crm.getBlock());
      getBenTable().setColumnRenderer(getColumnIndexOps(), crm.getCellRendererBlockOp());
      getBenTable().setColumnRenderer(getColumnIndexMiner(), crm.getCellRendererBlockMiner());
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
}
