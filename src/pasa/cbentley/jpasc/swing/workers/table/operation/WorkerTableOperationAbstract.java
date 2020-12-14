/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.operation;

import java.util.List;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.listlisteners.IListListener;
import pasa.cbentley.jpasc.pcore.rpc.model.Operation;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.operation.ListTaskOperationAbstract;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.threads.IWorkerPanel;

public abstract class WorkerTableOperationAbstract extends WorkerListTaskPage<ModelTableOperationAbstract, Operation> implements IListListener<Operation> {

   protected final ModelTableOperationAbstract tableModel;

   protected final PascalSwingCtx      psc;

   public WorkerTableOperationAbstract(PascalSwingCtx psc, IWorkerPanel wp, ModelTableOperationAbstract tableModel) {
      super(psc.getSwingCtx(), wp);
      this.psc = psc;
      this.tableModel = tableModel;
   }

   /**
    * 
    */
   protected abstract ListTaskOperationAbstract createTaskPage();

   protected ModelTableOperationAbstract getModel() {
      return tableModel;
   }

   /**
    * Override for specific purpose
    */
   protected void process(List<Operation> chunks) {
      tableModel.addRows(chunks);
      wp.panelSwingWorkerProcessed(this, chunks.size());
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableOperation");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableOperation");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}