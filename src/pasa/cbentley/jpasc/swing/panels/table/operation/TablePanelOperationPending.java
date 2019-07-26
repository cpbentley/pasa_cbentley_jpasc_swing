/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.operation;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationFullData;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationPending;
import pasa.cbentley.swing.imytab.IMyTab;

public class TablePanelOperationPending extends TablePanelOperationAbstract implements IMyTab {

   public static final String ID               = "operations_pending";

   /**
    * 
    */
   private static final long  serialVersionUID = -714617517830781134L;

   private IRootTabPane       root;

   public TablePanelOperationPending(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, ID, root);
      this.root = root;
   }

   protected ModelTableOperationAbstract createTableModel() {
      return new ModelTableOperationFullData(psc);
   }

   protected WorkerTableOperationAbstract createWorker() {
      return new WorkerTableOperationPending(psc, getTableModel(), this);
   }

}