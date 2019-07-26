/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.operation;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.filter.IFilterOperation;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.ListTaskBlockAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.operation.ListTaskOperationAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.operation.ListTaskOperationByFilter;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableOperationsByFilter extends WorkerTableOperationAbstract {


   private ListTaskBlockAbstract listBlocks;

   private IFilterOperation filter;
   public WorkerTableOperationsByFilter(PascalSwingCtx psc, ModelTableOperationAbstract tableModel, IWorkerPanel wp, IFilterOperation filter, ListTaskBlockAbstract listBlocks) {
      super(psc, wp, tableModel);
      this.filter = filter;
      this.listBlocks = listBlocks;
   }

   protected ListTaskOperationAbstract createTaskPage() {
      return new ListTaskOperationByFilter(psc.getPCtx(), this, listBlocks, filter);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableOperationsByFilter");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableOperationsByFilter");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}