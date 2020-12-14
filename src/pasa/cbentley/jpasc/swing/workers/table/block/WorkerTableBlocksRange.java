/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.block;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.rpc.model.Block;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.ListTaskBlockRange;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableBlocksRange extends WorkerTableBlockAbstract {

   private Integer blockEnd;

   private Integer blockStart;

   public WorkerTableBlocksRange(PascalSwingCtx psc, IWorkerPanel wp, ModelTableBlockAbstract tableModel, Integer blockStart, Integer blockEnd) {
      super(psc, wp, tableModel);
      this.blockStart = blockStart;
      this.blockEnd = blockEnd;
   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   protected ListTaskPage<Block> createTaskPage() {
      //use task of the core framework
      return new ListTaskBlockRange(psc.getPCtx(), this, blockStart, blockEnd);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableBlocksRange");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableBlocksRange");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}