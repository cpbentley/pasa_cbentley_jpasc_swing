/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.key;

import java.util.List;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.task.ListTask;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAbstract;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.threads.IWorkerPanel;

public abstract class WorkerTableKeyAbstract extends WorkerListTaskPage<ModelTablePublicKeyJavaAbstract, PublicKeyJava>  {

   protected final ModelTablePublicKeyJavaAbstract tableModel;

   protected final PascalSwingCtx                      psc;

   public WorkerTableKeyAbstract(PascalSwingCtx psc, IWorkerPanel wp, ModelTablePublicKeyJavaAbstract tableModel) {
      super(psc.getSwingCtx(), wp);
      this.psc = psc;
      this.tableModel = tableModel;
   }

   protected ModelTablePublicKeyJavaAbstract getModel() {
      return tableModel;
   }

   protected void process(List<PublicKeyJava> chunks) {
      tableModel.addRows(chunks);
      wp.panelSwingWorkerProcessed(this, chunks.size());
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableOperation");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableOperation");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}