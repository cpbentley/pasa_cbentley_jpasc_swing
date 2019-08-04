/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.abstrakt;

import java.util.List;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.pages.PagerAbstract;
import pasa.cbentley.jpasc.pcore.task.ListTask;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.WorkerStat;

/**
 * With paging
 * 
 * @author Charles Bentley
 *
 * @param <K>
 * @param <V>
 */
public abstract class WorkerListTaskPage<K, V> extends WorkerListTask<K, V>  {

   private ListTaskPage<V> taskPage;

   public WorkerListTaskPage(SwingCtx sc, IWorkerPanel wp) {
      super(sc, wp);
   }

   protected abstract ListTaskPage<V> createTaskPage();

   protected ListTask<V> createTask() {
      taskPage = createTaskPage();
      return taskPage;
   }
   /**
    * Called in the worker thread
    */
   public void newDataAvailable(List<V> list) {
      //update the worker stat 
       PagerAbstract<V> pager = taskPage.getPager();
       WorkerStat ws = getWorkerStat();
       ws.setEntriesTotal(pager.getCountTotalItems());
       //the start of the pager gives the actual
       ws.setEntriesCount(pager.getStart());
      super.newDataAvailable(list);
   }
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerListTaskPage");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerListTaskPage");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
}
