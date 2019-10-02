/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.interfaces.IStrAcceptor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.strings.StrAcceptorContained;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWalletNameAcceptor;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * Looks account whose name contains a string.
 * @author Charles Bentley
 *
 */
public class WorkerTableAccountNameContains extends WorkerTableAccountAbstract {

   private final IStrAcceptor acceptor;

   public WorkerTableAccountNameContains(PascalSwingCtx psc, ModelTableAccountAbstract tableModel, IWorkerPanel wp, String name) {
      super(psc, wp, tableModel);
      if (name == null) {
         throw new NullPointerException();
      }
      this.acceptor = new StrAcceptorContained(psc.getUCtx(), name);
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      return new ListTaskAccountWalletNameAcceptor(psc.getPCtx(), this, acceptor);
   }

   public String getNameForUser() {
      return "AccountNameContains";
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountNameContains");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountNameContains");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}