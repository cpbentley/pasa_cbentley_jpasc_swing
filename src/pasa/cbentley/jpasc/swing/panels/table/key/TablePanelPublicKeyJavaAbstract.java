/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.key;

import java.awt.BorderLayout;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.cellrenderers.CellRendereManager;
import pasa.cbentley.jpasc.swing.cmds.ICommandableKey;
import pasa.cbentley.jpasc.swing.cmds.PascalCmdManager;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.table.abstrakt.TablePanelAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAbstract;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BCMenuItem;
import pasa.cbentley.swing.widgets.b.BMenuItem;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * 
 * Provide services 
 * 
 * <li> Specific popup manager for account rows
 * 
 * implementation populate menu with required items
 * 
 * @author Charles Bentley
 *
 */
public abstract class TablePanelPublicKeyJavaAbstract extends TablePanelAbstract<PublicKeyJava> implements IEventsPascalSwing, ICommandableKey, IMyTab, IWorkerPanel, IMyGui, IEventConsumer {

   /**
    * 
    */
   private static final long    serialVersionUID = 6884309134642220505L;

   /**
    * Not null. Provides the context (chain, private, snapshot)
    */
   protected final IRootTabPane root;

   /**
    * 
    * @param psc
    * @param id
    * @param root {@link IRootTabPane} from which to get the {@link IDataAccess} when querying for the accounts
    * @param job
    */
   public TablePanelPublicKeyJavaAbstract(PascalSwingCtx psc, String id, IRootTabPane root) {
      super(psc, id);
      if (root == null) {
         throw new NullPointerException();
      }
      isTableUpdatingDisabled = false;
      this.root = root;
      setLayout(new BorderLayout());

      //we want to know when key
      psc.getEventBusPascal().addConsumer(this, PID_6_KEY_LOCAL_OPERATION, EID_6_KEY_LOCAL_OPERATION_0_ANY);

   }

   /**
    * Called by a sub class when it does not have a custom pop up menu of commands for keys
    * @param popupMenu
    */
   public void addDefaultKeyMenuItems(BPopupMenu popupMenu) {
      psc.getPascalBPopupMenuFactory().addKeyMenuItemsShowNameCopy(popupMenu, this);
   }

   public void addKeyMenuItems(BPopupMenu popupMenu) {
      psc.getPascalBPopupMenuFactory().addKeyMenuItemsShowNameCopy(popupMenu, this);
   }
   
   public void cmdTableRefresh() {
      super.cmdTableRefresh();
   }

   public void consumeEvent(BusEvent e) {
      if (e.getProducerID() == PID_6_KEY_LOCAL_OPERATION) {
         cmdTableRefresh();
      }
   }

   /**
    * A Worker which extends {@link WorkerTableKeyAbstract} and thus
    * {@link WorkerListTaskPage}
    */
   protected abstract WorkerTableKeyAbstract createWorker();

   public void disposeTab() {

   }

   protected int getColumnIndexKeyName() {
      return getTableModel().getColumnIndexKeyName();
   }

   protected int getColumnIndexNumAccount() {
      return getTableModel().getColumnIndexNumAccount();
   }

   protected int getDefSortColumnIndex() {
      return getColumnIndexKeyName();
   }

   public IRootTabPane getRoot() {
      return root;
   }

   public PublicKeyJava getSelectedPublicKeyA() {
      int selRow = getJTable().getSelectedRow();
      PublicKeyJava pk = null;
      if (selRow >= 0) {
         selRow = getJTable().convertRowIndexToModel(selRow);
         pk = getTableModel().getRow(selRow);
      }
      return pk;
   }

   public ModelTablePublicKeyJavaAbstract getTableModel() {
      return (ModelTablePublicKeyJavaAbstract) getBenTable().getModel();
   }

   public WorkerTableKeyAbstract getWorkerTableKeyAbstract() {
      return (WorkerTableKeyAbstract) workerTable;
   }

   
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      super.resizeTableColumns();
   }

   /**
    * Worker panel may update progress data 
    * @param worker
    * @param entryCount
    */
   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
      //call parent default for doing the boilersplate
      super.panelSwingWorkerProcessed(worker, entryCount);
      //here we want to resize rows asap
      if (getTableModel().getRowCount() < 200) {
         super.resizeTableColumns();
      }
   }

   public void setColumnRenderers() {
      CellRendereManager rm = psc.getCellRendereManager();
      getBenTable().setColumnRenderer(getColumnIndexKeyName(), rm.getCellRendererKeyName());
   }

   /**
    * Add {@link BMenuItem} that fit the class implementation design
    * @param menu
    */
   protected abstract void subPopulatePopMenu(BPopupMenu menu);

   protected void subWillRefreshTable() {
      psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_BELLS);
   }

   public void tabGainFocus() {
      super.tabGainFocus();
   }

   /**
    * By default, workers are paused, unless the user explicity asked the work to continue because
    * user knows it will take some time and he wants to do something else while its working.
    * 
    * IF key MAJ key is pressed when lost focus occurs and command ctx
    */
   public void tabLostFocus() {
      super.tabLostFocus();
   }

}