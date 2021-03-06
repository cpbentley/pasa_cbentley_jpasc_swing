/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWalletNameNull;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableAccountNameEmpty extends WorkerTableAccountAbstract {

   public WorkerTableAccountNameEmpty(PascalSwingCtx psc, ModelTableAccountAbstract tableModel, IWorkerPanel wp) {
      super(psc, wp, tableModel);
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      return new ListTaskAccountWalletNameNull(psc.getPCtx(), this);
   }
   
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountNameEmpty");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountNameEmpty");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}