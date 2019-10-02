/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.chain.ListTaskAccountChainAge;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * List all accounts
 * @author Charles Bentley
 *
 */
public class WorkerTableAccountChainAge extends WorkerTableAccountAbstractMinMaxInteger {

   public WorkerTableAccountChainAge(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel, Integer min, Integer max) {
      super(psc, wp, tableModel, min, max);
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      return new ListTaskAccountChainAge(psc.getPCtx(), this, min, max);
   }

   public String getNameForUser() {
      return "Account Chain Age";
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountChainAge");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountChainAge");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}