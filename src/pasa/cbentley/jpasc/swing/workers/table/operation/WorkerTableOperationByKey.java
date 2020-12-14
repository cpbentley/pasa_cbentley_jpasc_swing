/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.operation;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.operation.ListTaskOperationAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.operation.ListTaskOperationByKey;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableOperationByKey extends WorkerTableOperationAbstract {

   private PublicKey pk;

   public WorkerTableOperationByKey(PascalSwingCtx psc, ModelTableOperationAbstract tableModel, IWorkerPanel wp, PublicKey pk) {
      super(psc, wp, tableModel);
      this.pk = pk;
   }

   protected ListTaskOperationAbstract createTaskPage() {
      return new ListTaskOperationByKey(psc.getPCtx(), this, pk);
   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableOperationByKey");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableOperationByKey");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}