/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.chain.ListTaskAccountChain;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * List all accounts
 * @author Charles Bentley
 *
 */
public class WorkerTableAccountChainAll extends WorkerTableAccountAbstract {

   public WorkerTableAccountChainAll(PascalSwingCtx psc, ModelTableAccountAbstract tableModel, IWorkerPanel wp) {
      super(psc, wp, tableModel);
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      return new ListTaskAccountChain(psc.getPCtx(), this);
   }

   public String getNameForUser() {
      return "Account Chain All";
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