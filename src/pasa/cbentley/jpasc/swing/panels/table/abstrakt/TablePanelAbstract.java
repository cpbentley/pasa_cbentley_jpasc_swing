/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.abstrakt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.cmds.ICmdIDs;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperLoadingStat;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperRefresh;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperTable;
import pasa.cbentley.jpasc.swing.panels.table.key.TablePanelPublicKeyJavaAbstract;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.model.ModelTableBAbstract;
import pasa.cbentley.swing.table.AbstractTabTable;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.threads.WorkerStat;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;

/**
 * Base Panel class for all Pascal Coin Table listing
 * Account, keys, operations, blocks
 * @author Charles Bentley
 */
public abstract class TablePanelAbstract<T> extends AbstractTabTable<T> implements ICmdIDs, IWorkerPanel, ICommandableRefresh {

   /**
    * 
    */
   private static final long        serialVersionUID = -7955180093657758280L;


   /**
    * This flag tells Table to resize columns once the worker has finished.
    */
   protected boolean                isAutoResizeColumns;

   /**
    * Tells the table to resize columns on GuiUpdate. Potentially costly, therefore turned off
    * by default.
    */
   protected boolean                isAutoResizeColumnsOnGuiUpdate;

   /**
    * 
    */
   protected boolean                isFixedStatTextField;

   protected boolean                isTableUpdatingDisabled;

   protected PanelHelperRefresh     panelHelperRefresh;

   protected final PascalSwingCtx   psc;

   /**
    * Not null
    */
   protected PanelHelperLoadingStat panelHelperLoadingStat;


   private PanelHelperTable panelHelperTable;

   public TablePanelAbstract(PascalSwingCtx psc, String internalID) {
      super(psc.getSwingCtx(), internalID);
      this.psc = psc;
      isFixedStatTextField = true;
      isAutoResizeColumns = true;
      isAutoResizeColumnsOnGuiUpdate = false;
      panelHelperLoadingStat = new PanelHelperLoadingStat(psc);

      panelHelperTable = new PanelHelperTable(psc, this);
   }

   protected void cmdClear() {
      //stop current worker
      if (this.workerTable != null) {
         this.workerTable.cancel(true);
      }
      getBenTable().clearTableModel();
      this.repaint();
   }

   public void cmdRefresh(Object src) {
      cmdTableRefresh();
   }

   /**
    * Refresh the table with a new worker, no checks, cancel previous worker if any.
    */
   public void cmdTableRefresh() {

      //#debug
      toDLog().pFlow("", this, TablePanelAbstract.class, "cmdTableRefresh", LVL_05_FINE, true);

      subWillRefreshTable();

      //cancel any worker working
      if (workerTable != null) {
         psc.getSwingCtx().swingWorkerCancel(workerTable);
      }
      //
      getBenTable().clearTableModel();
      //set the task to a swing worker
      WorkerListTaskPage worker = createWorker();
      //#mdebug
      if (worker == null) {
         throw new NullPointerException("createWorker for " + this.getClass().getSimpleName() + " cannot return null");
      }
      //#enddebug
      if (worker == null) {
         return;
      }

      this.workerTable = worker;

      panelHelperLoadingStat.setWorker(worker);
      panelHelperLoadingStat.setStateScanning();

      //TODO add worker to global list.. with max number of workers.. and ability to stop 
      //view active workers in a list
      //message user when switching tab if he wants worker to continue its work
      //add worker in top bar. user can pause or stop it

      psc.getSwingCtx().swingWorkerExecute(worker);

      //we don't really want a user seeing this
      //psc.getLog().consoleLog("Refreshing table for " + getTabTitle());

      subDidRefreshTable();
   }

   protected abstract WorkerListTaskPage createWorker();

   /**
    * Return the index of the column to be sorted by default
    * @return
    */
   protected abstract int getDefSortColumnIndex();

   public ModelTableBAbstract<T> getTableModelAbstract() {
      return getBenTable().getModel();
   }

   public void guiUpdate() {
      //#debug
      toDLog().pFlow("start", this, TablePanelAbstract.class, "guiUpdate", LVL_04_FINER, true);
      super.guiUpdate();
      //since the whole structure of the table is updated.. set back renderers
      if (isInitialized()) {
         //do no update this if not initialized
         psc.setDefaultRenderers(getBenTable().getTable());
         setColumnRenderers();
         setComparators(); // model.fireTableStructureChanged() in TableBentley
      }
      //TODO this is potentially very costly when many tables are active. so only update if visible
      //when no visible save guiUpdate for this componenent
      if (isAutoResizeColumnsOnGuiUpdate) {
         //#debug
         toDLog().pFlow("isAutoResizeColumnsOnGuiUpdate", this, TablePanelAbstract.class, "guiUpdate", LVL_03_FINEST, true);
         super.resizeTableColumns();
      }
      //#debug
      toDLog().pFlow("end", this, TablePanelAbstract.class, "guiUpdate", LVL_04_FINER, true);

   }

   private void initPanelNorth() {
      PanelPascal north = new PanelPascal(psc);
      north.setLayout(new FlowLayout(FlowLayout.LEFT));
      north.add(panelHelperTable); //by default first position
      subInitPanelNorth(north);
      this.add(north, BorderLayout.NORTH);
   }

   private void initPanelSouth() {
      PanelPascal south = new PanelPascal(psc);
      south.setLayout(new FlowLayout(FlowLayout.LEFT));
      subInitPanelSouth(south);
      this.add(south, BorderLayout.SOUTH);
   }

   protected void initTab() {
      super.initTab();

      getBenTable().setDefSort(getDefSortColumnIndex());
      psc.setDefaultRenderers(getBenTable().getTable());
      setColumnRenderers();
      setComparators();
      //the sub class decides what to put there
      initPanelNorth();
      //the sub class decides what to put there
      initPanelSouth();
   }

   /**
    * True if {@link TablePanelAbstract#panelSwingWorkerDone(PanelSwingWorker)} will resize table columns
    * 
    * GUI update will also resize columns since
    * @return
    */
   public boolean isAutoResizeColumns() {
      return isAutoResizeColumns;
   }

   /**
    * Checks whether table data must be updated. Called by 
    * 
    * <li> the block is the same as the current block
    * <li> table updating has been explicitely disabled with {@link TablePanelAbstract#tableRefreshDisable()}
    * <li> a worker is already working. depends on the policy
    * 
    * Calling a setter on parameters
    * then one should call cmdTableRefresh?
    * 
    * TODO Sub class may decide to not refresh because its data does not depend on block.
    * operations for instance
    * 
    * Explicit refresh {@link TablePanelAbstract#cmdTableRefresh()} does not use this method.
    * 
    * @return
    */
   public boolean isTableUpdateRequired() {
      if (isTableUpdatingDisabled) {
         return false;
      }
      //if table is empty we want to refresh it as it costs nothing to try again
      //and it might be the first time
      if (getBenTable().getModel().getRowCount() == 0) {
         return true;
      }

      //check if global manual refresh setting. it overrides everything
      boolean isManualRefresh = psc.getPascPrefs().getBoolean(IPrefsPascalSwing.PREF_GLOBAL_MANUAL_REFRESH, false);
      if (isManualRefresh) {
         return false;
      }

      if (workerTable != null) {
         return false;
      }
      if (panelHelperRefresh != null) {
         Integer blockLastMine = psc.getPCtx().getRPCConnection().getLastBlockMined();
         Integer blockLastRefresh = panelHelperRefresh.getRefreshBlock();
         if (blockLastRefresh != null && blockLastMine != null) {
            if (blockLastMine == blockLastRefresh) {
               return false;
            }
         }
         //TODO ask when new block if refresh is needed. each active panel gets a chance
      }
      return true;
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker tableBlockSwingWorker) {
      //#debug
      toDLog().pWork("", this, TablePanelAbstract.class, "panelSwingWorkCancelled", ITechLvl.LVL_05_FINE, true);
      
      getTableModelAbstract().computeStatsGlobal();
      subUpdateStatPanel();
      this.workerTable = null;
      panelHelperLoadingStat.setStateCanceled();
      panelHelperLoadingStat.setWorker(null);
      if (isAutoResizeColumns) {
         super.resizeTableColumns();
      }
   }

   /**
    * Called when the worker is completely done
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      //#debug
      toDLog().pFlow("", worker, TablePanelAbstract.class, "panelSwingWorkerDone", ITechLvl.LVL_05_FINE, false);

      //before updating stats. request model global stats computation
      getTableModelAbstract().computeStatsGlobal();
      subUpdateStatPanel();
      this.workerTable = null;
      panelHelperLoadingStat.setStateDone();
      panelHelperLoadingStat.setWorker(null);
      if (isAutoResizeColumns) {
         super.resizeTableColumns();
      }
   }

   /**
    * Worker panel may update progress data 
    * 
    * {@link TablePanelAbstract#subUpdateStatPanel()} is called to let sub class update its statistics based
    * on the model.
    * 
    * @param worker
    * @param entryCount
    */
   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
      subUpdateStatPanel();
      //
      WorkerStat workerStats = worker.getWorkerStat();
      panelHelperLoadingStat.setStateStat(workerStats);
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {
      //check current block
      if (panelHelperRefresh != null) {
         Integer block = psc.getPCtx().getRPCConnection().getLastBlockMined();
         panelHelperRefresh.setRefreshBlock(block);
      }
   }

   /**
    * 
    * @param t
    * @return
    */
   public boolean removeFromModel(T t) {
      if (isInitialized()) {
         return getBenTable().getModel().removeRow(t);
      }
      return false;
   }

   protected void resizeTableColumnDefault() {
      ModelTableBAbstract model = getTableModelAbstract();
      if (model.getRowCount() < 200) {
         super.resizeTableColumns();
      }
   }

   public void setComparators() {

   }

   public abstract void setColumnRenderers();

   protected void subDidRefreshTable() {

   }

   protected void subInitPanelNorth(JPanel north) {

   }

   protected void subInitPanelSouth(JPanel south) {

   }

   /**
    * Implementation reads its model and update the statistics of the worker
    */
   protected void subUpdateStatPanel() {

   }

   protected void subWillRefreshTable() {

   }

   public void tabGainFocus() {
      //#debug
      toDLog().pFlow("isTableUpdatingDisabled=" + isTableUpdatingDisabled, this, TablePanelAbstract.class, "tabGainFocus", ITechLvl.LVL_04_FINER, true);

      if (isTableUpdateRequired()) {
         cmdTableRefresh();
      }
   }

   /**
    * Disable event updates from text field or dropdown menus
    */
   public void tableRefreshDisable() {
      isTableUpdatingDisabled = true;
   }

   /**
    * Called after {@link TablePanelPublicKeyJavaAbstract#tableRefreshDisable()}
    * 
    */
   public void tableRefreshEnable() {
      isTableUpdatingDisabled = false;
   }

   /**
    * By default, workers are paused, unless the user explicity asked the work to continue because
    * user knows it will take some time and he wants to do something else while its working.
    * 
    * IF key MAJ key is pressed when lost focus occurs and command ctx
    * 
    * works with {@link TablePanelAbstract#shouldTabBeHiddenByAnotherTab(IMyTab)} to disable or not
    * the current running task.
    */
   public void tabLostFocus() {
      //#debug
      toDLog().pFlow("", this, TablePanelAbstract.class, "tabLostFocus", ITechLvl.LVL_04_FINER, true);

      if (workerTable != null) {
         //#debug
         toDLog().pFlow("workerTable is not null", workerTable, TablePanelAbstract.class, "tabLostFocus", ITechLvl.LVL_04_FINER, true);

         if(panelHelperTable.isTaskKeptWhenFocusOut()) {
            sc.getLog().consoleLog("Task "+ workerTable.getNameForUser() + " keeps running");
         } else {
            //pause it if supports exists until tab gets back
            //worker.pause();
            boolean b = workerTable.cancel(true);

            //#debug
            toDLog().pWork("Worker was not null. Cancel method returns " + b, this, TablePanelAbstract.class, "tabLostFocus", ITechLvl.LVL_05_FINE, true);
            workerTable = null;
         }
      }
   }

   public boolean shouldTabBeHiddenByAnotherTab(IMyTab newSelectedTab) {
      //check if its ok to be hidden and user really wants to hide the stand in the current state
      if (workerTable != null) {
         //depends on saved settings
         return true;
      }
      return true;
   }
}
