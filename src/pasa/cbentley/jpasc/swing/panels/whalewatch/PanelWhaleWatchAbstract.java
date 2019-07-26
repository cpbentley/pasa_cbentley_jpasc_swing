/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.whalewatch;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.ListTaskBlockAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.ListTaskBlockInThePast;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IMyLookUp;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperBlockPast;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationFullData;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * 
 * @author Charles Bentley
 *
 */
public abstract class PanelWhaleWatchAbstract extends TablePanelOperationAbstract implements IMyTab, ActionListener, IWorkerPanel, IMyLookUp {

   private static final int EID_4_WHALE_PRICE_WATCH_TABLE_LOAD = 4;

   private static final int PID_3_WHALE_WATCHER                = 3;

   protected PanelHelperBlockPast blockInThePast;

   /**
    * set this parameters to null if another change is made
    */
   private Integer          lastBlockUpdate;

   /**
    * The Panel that can host several components alongside the {@link PanelHelperBlockPast}
    */
   protected JPanel         north;

   public PanelWhaleWatchAbstract(PascalSwingCtx psc, String id, IRootTabPane root) {
      super(psc, id, root);
   }

   public void actionPerformed(ActionEvent e) {
      lastBlockUpdate = null; //force an update of the table
   }

   public void addNorth(JComponent pane) {
      north.add(pane, 0);
   }

   private void checkTable() {
      if (isTableUpdateNeeded()) {
         lastBlockUpdate = psc.getPCtx().getRPCConnection().getLastBlockMined();
         cmdTableRefresh();

         //do not play if
         //generate an event which pascal audio may intercept
         psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_PLOUFS);
      } else {
         //
         psc.getLog().consoleLog("Data update is not needed. The table is already up to date for block " + lastBlockUpdate);
      }
   }

   protected ModelTableOperationAbstract createTableModel() {
      return new ModelTableOperationFullData(psc);
   }

   public void disposeTab() {

   }

   public ListTaskBlockAbstract getBlockTaskList() {
      int number = blockInThePast.getBlockPastInt();
      ListTaskBlockAbstract listBlocks = new ListTaskBlockInThePast(psc.getPCtx(), number);
      return listBlocks;
   }

   public void initTab() {

      super.initTab();

      north = new JPanel(new FlowLayout(FlowLayout.LEADING));
      blockInThePast = new PanelHelperBlockPast(psc, this);
      north.add(blockInThePast);

      this.add(north, BorderLayout.NORTH);

   }

   /**
    * False if block hasn't changed.
    * True if search parameters has changed
    * @return
    */
   protected boolean isTableUpdateNeeded() {
      return lastBlockUpdate != psc.getPCtx().getRPCConnection().getLastBlockMined();
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker worker) {
      super.panelSwingWorkerCancelled(worker);
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      //generate event
      psc.getEventBusPascal().createEvent(PID_3_WHALE_WATCHER, EID_4_WHALE_PRICE_WATCH_TABLE_LOAD, this);
   }

   /**
    * Worker panel may update progress data 
    * @param worker
    * @param entryCount
    */
   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
      super.panelSwingWorkerProcessed(worker, entryCount);
   }

   public void tabGainFocus() {

      psc.playTabFocus(this);

      checkTable();
      //#debug
      toDLog().pFlow(lastBlockUpdate + " : " + psc.getPCtx().getRPCConnection().getLastBlockMined(), this, PanelWhaleWatchAbstract.class, "tabGainFocus", ITechLvl.LVL_04_FINER, true);

   }

   public void tabLostFocus() {
      psc.playTabFocusLost(this);
   }

   public void updateTable() {
      lastBlockUpdate = null; //force an update of the table in tabGainFocus
      checkTable();
   }
}