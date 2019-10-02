/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.block;

import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.task.ListTask;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.ListTaskBlockInThePast;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableBlocksInThePast extends WorkerTableBlockAbstract {

   private Integer blockInThePastNum;

   public WorkerTableBlocksInThePast(PascalSwingCtx psc,IWorkerPanel wp,  ModelTableBlockAbstract tableModel, Integer blockInThePastNum) {
      super(psc, wp, tableModel);
      this.blockInThePastNum = blockInThePastNum;
   }

   protected ListTaskPage<Block> createTaskPage() {
      //use task of the core framework
      return new ListTaskBlockInThePast(psc.getPCtx(), this, blockInThePastNum);
   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableBlocksInThePast");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableBlocksInThePast");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}