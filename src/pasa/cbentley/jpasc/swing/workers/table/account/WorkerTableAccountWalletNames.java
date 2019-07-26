/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.strings.StrAcceptorContains;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWallet;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWalletNameAcceptor;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWalletNameAny;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWalletNameNull;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableAccountWalletNames extends WorkerTableAccountAbstractName {

   public WorkerTableAccountWalletNames(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel) {
      super(psc, wp, tableModel);
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      ListTaskAccountWallet listTaskAccountWallet = null;
      if (isOnlyEmty) {
         listTaskAccountWallet = new ListTaskAccountWalletNameNull(psc.getPCtx(), this);
      } else if (name == null) {
         listTaskAccountWallet = new ListTaskAccountWalletNameAny(psc.getPCtx(), this);
      } else {
         StrAcceptorContains acceptor = new StrAcceptorContains(psc.getUCtx(), name);
         listTaskAccountWallet = new ListTaskAccountWalletNameAcceptor(psc.getPCtx(), this, acceptor);
      }
      return listTaskAccountWallet;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountWalletNames");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountWalletNames");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}