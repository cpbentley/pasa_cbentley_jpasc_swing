/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.operation;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.operation.ListTaskOperationAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.operation.ListTaskOperationPending;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableOperationPending extends WorkerTableOperationAbstract {


   public WorkerTableOperationPending(PascalSwingCtx psc, ModelTableOperationAbstract tableModel, IWorkerPanel wp) {
      super(psc, wp, tableModel);
   }

   protected ListTaskOperationAbstract createTaskPage() {
      //use task of the core framework
      return new ListTaskOperationPending(psc.getPCtx(), this);
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