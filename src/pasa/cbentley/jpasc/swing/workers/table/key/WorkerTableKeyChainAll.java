/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.key;

import java.util.List;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJavaCache;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.pcore.task.list.java.key.ListTaskPublicKeyJavaChainAll;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public class WorkerTableKeyChainAll extends WorkerTableKeyAbstract {

   public WorkerTableKeyChainAll(PascalSwingCtx psc, IWorkerPanel wp, ModelTablePublicKeyJavaAbstract tableModel) {
      super(psc, wp, tableModel);
   }

   private ListTaskPublicKeyJavaChainAll task;

   protected ListTaskPage<PublicKeyJava> createTaskPage() {
      //use task of the core framework
      task = new ListTaskPublicKeyJavaChainAll(psc.getPCtx(), this);
      task.setComputeNumAccounts(true);
      return task;
   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   /**
    * Only add rows if new. otherwise. update
    */
   protected void process(List<PublicKeyJava> chunks) {
      for (PublicKeyJava key : chunks) {
         int index = key.getCachedTableIndex();
         if (index == -1) {
            int size = tableModel.getRowCount();
            tableModel.addRow(key);
            key.setCachedTableIndex(size);
         } else {
            tableModel.fireTableRowsUpdated(index, index);
         }
      }
      wp.panelSwingWorkerProcessed(this, chunks.size());
   }

   public PublicKeyJavaCache getCache() {
      if(task != null) {
         return task.getCachePublicKeyJava();
      }
      return null; 
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableKeyChainAll");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableKeyChainAll");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}