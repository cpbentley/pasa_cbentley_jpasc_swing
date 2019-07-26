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
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperLoadingStat;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperRefresh;
import pasa.cbentley.jpasc.swing.panels.table.key.TablePanelPublicKeyJavaAbstract;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.model.ModelTableBAbstract;
import pasa.cbentley.swing.table.AbstractTabTable;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.threads.WorkerStat;
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

   protected boolean                isTableUpdatingDisabled;

   protected final PascalSwingCtx   psc;

   /**
    * 
    */
   protected boolean                isFixedStatTextField;

   /**
    * Not null
    */
   protected PanelHelperLoadingStat statPanel;

   protected PanelHelperRefresh     panelRefresh;

   protected BCheckBox              cbRowNumbers;

   protected boolean                isAutoResizeColumns;

   public TablePanelAbstract(PascalSwingCtx psc, String internalID) {
      super(psc.getSwingCtx(), internalID);
      this.psc = psc;
      isFixedStatTextField = true;
      isAutoResizeColumns = true;
      statPanel = new PanelHelperLoadingStat(psc);

      cbRowNumbers = new BCheckBox(sc, new ActionListener() {

         public void actionPerformed(ActionEvent e) {

            JOptionPane jop = new JOptionPane();
            jop.setMessageType(JOptionPane.PLAIN_MESSAGE);
            jop.setMessage("Please wait...");
            JDialog dialog = jop.createDialog(null, "Message");
            dialog.setVisible(true);
            cmdToggleRowHeader();
            dialog.dispose();
         }
      }, "cb.rownumber");
   }

   private void initPanelNorth() {
      PanelPascal north = new PanelPascal(psc);
      north.setLayout(new FlowLayout(FlowLayout.LEFT));
      north.add(cbRowNumbers); //by default first position
      subInitPanelNorth(north);
      this.add(north, BorderLayout.NORTH);
   }

   protected void subInitPanelNorth(JPanel north) {

   }

   protected void subInitPanelSouth(JPanel south) {

   }

   protected void subWillRefreshTable() {

   }

   protected void subDidRefreshTable() {

   }

   private void initPanelSouth() {
      PanelPascal south = new PanelPascal(psc);
      south.setLayout(new FlowLayout(FlowLayout.LEFT));
      subInitPanelSouth(south);
      this.add(south, BorderLayout.SOUTH);
   }

   public void cmdRefresh(Object src) {
      cmdTableRefresh();
   }

   /**
    * Return the index of the column to be sorted by default
    * @return
    */
   protected abstract int getDefSortColumnIndex();

   protected abstract WorkerListTaskPage createWorker();

   protected void cmdClear() {
      //stop current worker
      if (this.workerTable != null) {
         this.workerTable.cancel(true);
      }
      getBenTable().clearTableModel();
      this.repaint();
   }

   /**
    * 
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

      statPanel.setWorker(worker);
      statPanel.setStateScanning();

      //TODO add worker to global list.. with max number of workers.. and ability to stop 
      //view active workers in a list
      //message user when switching tab if he wants worker to continue its work
      //add worker in top bar. user can pause or stop it

      psc.getSwingCtx().swingWorkerExecute(worker);

      //we don't really want a user seeing this
      //psc.getLog().consoleLog("Refreshing table for " + getTabTitle());

      subDidRefreshTable();
   }

   /**
    * By default, workers are paused, unless the user explicity asked the work to continue because
    * user knows it will take some time and he wants to do something else while its working.
    * 
    * IF key MAJ key is pressed when lost focus occurs and command ctx
    */
   public void tabLostFocus() {
      if (workerTable != null) {

         //pause it if supports exists until tab gets back
         //worker.pause();
         boolean b = workerTable.cancel(true);

         //#debug
         toDLog().pWork("Worker was not null. Cancel method returns " + b, this, TablePanelAbstract.class, "tabLostFocus", ITechLvl.LVL_05_FINE, true);
         workerTable = null;
         //table data is kept
      }
   }

   public void guiUpdate() {
      super.guiUpdate();
      //since the whole structure of the table is updated.. set back renderers
      if (isInitialized()) {
         //do no update this if not initialized
         psc.setDefaultRenderers(getBenTable().getTable());
         setColumnRenderers();
      }
      if (isAutoResizeColumns) {
         super.resizeTableColumns();
      }
   }

   protected void initTab() {
      super.initTab();

      getBenTable().setDefSort(getDefSortColumnIndex());
      psc.setDefaultRenderers(getBenTable().getTable());
      setColumnRenderers();

      //the sub class decides what to put there
      initPanelNorth();
      //the sub class decides what to put there
      initPanelSouth();
   }

   public abstract void setColumnRenderers();

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

   /**
    * Implementation reads its model and update the statistics of the worker
    */
   protected void subUpdateStatPanel() {

   }

   public ModelTableBAbstract<T> getTableModelAbstract() {
      return getBenTable().getModel();
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker tableBlockSwingWorker) {
      //#debug
      toDLog().pWork("", this, TablePanelAbstract.class, "panelSwingWorkCancelled", ITechLvl.LVL_05_FINE, true);
   }

   /**
    * Checks whether table data must be updated.
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
    * @return
    */
   public boolean isTableUpdateRequired() {
      if (isTableUpdatingDisabled) {
         return false;
      }
      if (workerTable != null) {
         return false;
      }
      if (panelRefresh != null) {
         Integer blockLastMine = psc.getPCtx().getRPCConnection().getLastBlockMined();
         Integer blockLastRefresh = panelRefresh.getRefreshBlock();
         if (blockLastRefresh != null && blockLastMine != null) {
            if (blockLastMine == blockLastRefresh) {
               return false;
            }
         }
         //TODO ask when new block if refresh is needed. each active panel gets a chance
      }
      return true;
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
      statPanel.setStateStat(workerStats);
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {
      //check current block
      if (panelRefresh != null) {
         Integer block = psc.getPCtx().getRPCConnection().getLastBlockMined();
         panelRefresh.setRefreshBlock(block);
      }
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
    * Called when the worker is completely done
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      //#debug
      toDLog().pFlow("", worker, TablePanelAbstract.class, "panelSwingWorkerDone", ITechLvl.LVL_05_FINE, false);

      //before updating stats. request model global stats computation
      getTableModelAbstract().computeStatsGlobal();
      subUpdateStatPanel();
      this.workerTable = null;
      statPanel.setStateDone();
      if (isAutoResizeColumns) {
         super.resizeTableColumns();
      }
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

   protected void resizeTableColumnDefault() {
      ModelTableBAbstract model = getTableModelAbstract();
      if (model.getRowCount() < 200) {
         super.resizeTableColumns();
      }
   }
}
