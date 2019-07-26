/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.system;

import java.awt.BorderLayout;

import com.github.davidbolet.jpascalcoin.api.model.Connection;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.ModelTableConnections;
import pasa.cbentley.jpasc.swing.workers.WorkerTableConnections;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.model.ModelTableBAbstractArray;
import pasa.cbentley.swing.table.AbstractTabTable;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public class ListConnectionsPanel extends AbstractTabTable<Connection> implements IMyTab, IWorkerPanel {

   /**
    * 
    */
   private static final long     serialVersionUID = 2555205610098062536L;


   private PascalSwingCtx psc;

   public ListConnectionsPanel(PascalSwingCtx psc) {
      super(psc.getSwingCtx(), "connections");
      this.psc = psc;
      this.setLayout(new BorderLayout());
   }

   public void disposeTab() {
      super.disposeTab();
   }


   public void initTab() {
      super.initTab();
   }


   public void tabGainFocus() {
      getBenTable().clearTableModel();
      WorkerTableConnections worker = new WorkerTableConnections(psc, getTableModel(), this);
      worker.execute();
   }

   public ModelTableConnections getTableModel() {
      return (ModelTableConnections) getBenTable().getModel();
   }

   public void tabLostFocus() {

   }

   public void panelSwingWorkerCancelled(PanelSwingWorker tableBlockSwingWorker) {
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.resizeTableColumns();
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

   protected ModelTableBAbstractArray<Connection> createTableModel() {
      return new ModelTableConnections(psc);
   }

   public BPopupMenu createTablePopUpMenu() {
      return null;
   }

}