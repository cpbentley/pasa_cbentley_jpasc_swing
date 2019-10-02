/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.chain.ListTaskAccountChain;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.chain.ListTaskAccountChainNameAny;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.chain.ListTaskAccountChainNameNull;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableAccountChainNames extends WorkerTableAccountAbstractName {

   public WorkerTableAccountChainNames(PascalSwingCtx psc, ModelTableAccountAbstract tableModel, IWorkerPanel wp) {
      super(psc, wp, tableModel);
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      ListTaskAccountAbstract listTaskAccountChain = null;
      if (isOnlyEmty) {
         listTaskAccountChain = new ListTaskAccountChainNameNull(psc.getPCtx(), this);
      } else if (name == null) {
         listTaskAccountChain = new ListTaskAccountChainNameAny(psc.getPCtx(), this, "");
      } else {
         listTaskAccountChain = new ListTaskAccountChainNameAny(psc.getPCtx(), this, name);
      }
      return listTaskAccountChain;
   }

   public String getNameForUser() {
      return "Account Names";
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountChainNames");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountChainNames");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}