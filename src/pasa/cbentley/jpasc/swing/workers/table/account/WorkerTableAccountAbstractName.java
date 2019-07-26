/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * Stub worker for all Account listing workers.
 * 
 * @see WorkerTableWalletAccountAll
 * @author Charles Bentley
 *
 */
public abstract class WorkerTableAccountAbstractName extends WorkerTableAccountAbstract {

   protected boolean isOnlyEmty;

   protected String  name;

   public WorkerTableAccountAbstractName(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel) {
      super(psc, wp, tableModel);
   }

   public String getName() {
      return name;
   }

   public boolean isOnlyEmty() {
      return isOnlyEmty;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setOnlyEmty(boolean isOnlyEmty) {
      this.isOnlyEmty = isOnlyEmty;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountAbstractName");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountAbstractName");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("name", name);
      dc.appendVarWithSpace("isOnlyEmty", isOnlyEmty);
   }

   //#enddebug

}