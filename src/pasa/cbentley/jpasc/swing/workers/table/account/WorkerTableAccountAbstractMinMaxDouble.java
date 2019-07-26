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
public abstract class WorkerTableAccountAbstractMinMaxDouble extends WorkerTableAccountAbstract {

   protected Double max;

   protected Double min;

   public WorkerTableAccountAbstractMinMaxDouble(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel) {
      super(psc, wp, tableModel);
   }

   public WorkerTableAccountAbstractMinMaxDouble(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel, Double min, Double max) {
      super(psc, wp, tableModel);
      this.min = min;
      this.max = max;
   }

   public Double getMax() {
      return max;
   }

   public Double getMin() {
      return min;
   }

   public void setMax(Double max) {
      this.max = max;
   }

   public void setMin(Double min) {
      this.min = min;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountAbstractMinMaxDouble");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("min", min);
      dc.appendVarWithSpace("max", max);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountAbstractMinMaxDouble");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}