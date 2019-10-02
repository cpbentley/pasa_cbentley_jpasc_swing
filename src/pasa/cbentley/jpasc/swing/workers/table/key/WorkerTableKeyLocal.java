/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.key;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.task.ListTask;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.pcore.task.list.java.key.ListTaskPublicKeyJavaLocal;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableKeyLocal extends WorkerTableKeyAbstract {

   public WorkerTableKeyLocal(PascalSwingCtx psc, IWorkerPanel wp, ModelTablePublicKeyJavaAbstract tableModel) {
      super(psc, wp, tableModel);
   }

   protected ListTaskPage<PublicKeyJava> createTaskPage() {
      //use task of the core framework
      ListTaskPublicKeyJavaLocal task = new ListTaskPublicKeyJavaLocal(psc.getPCtx(), this);
      return task;
   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableKeyWalletPublic");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableKeyWalletPublic");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}