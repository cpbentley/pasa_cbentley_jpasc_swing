/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.chain.ListTaskAccountChain;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.chain.ListTaskAccountChainRange;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * List all accounts on the blockchain based on a range
 * @author Charles Bentley
 *
 */
public class WorkerTableAccountChainRange extends WorkerTableAccountAbstract {

   private int rangeEnd;

   private int rangeStart;

   public WorkerTableAccountChainRange(PascalSwingCtx psc, ModelTableAccountAbstract tableModel, IWorkerPanel wp, int rangeStart, int rangeEnd) {
      super(psc, wp, tableModel);
      this.rangeStart = rangeStart;
      this.rangeEnd = rangeEnd;
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      return new ListTaskAccountChainRange(psc.getPCtx(), this, rangeStart, rangeEnd);
   }

   public int getRangeEnd() {
      return rangeEnd;
   }

   public int getRangeStart() {
      return rangeStart;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountChainAll");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountChainAll");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}