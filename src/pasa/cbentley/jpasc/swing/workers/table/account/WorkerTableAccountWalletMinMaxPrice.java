/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWalletPriceMinMax;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;


/**
 * List all accounts
 * @author Charles Bentley
 *
 */
public class WorkerTableAccountWalletMinMaxPrice extends WorkerTableAccountAbstractMinMaxDouble {

   public WorkerTableAccountWalletMinMaxPrice(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel) {
      super(psc, wp, tableModel);
   }
   
   public WorkerTableAccountWalletMinMaxPrice(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel, Double min, Double max) {
      super(psc, wp, tableModel, min, max);
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      ListTaskAccountWalletPriceMinMax task = new ListTaskAccountWalletPriceMinMax(psc.getPCtx(), this, min, max);
      return task;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountWalletMinMaxPrice");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountWalletMinMaxPrice");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   


}