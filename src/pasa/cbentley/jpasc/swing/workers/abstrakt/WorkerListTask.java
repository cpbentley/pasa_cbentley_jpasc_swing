/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.abstrakt;

import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.listlisteners.IListListener;
import pasa.cbentley.jpasc.pcore.task.ListTask;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.threads.WorkerStat;

/**
 * 
 * {@link PanelSwingWorker}
 * 
 * @author Charles Bentley
 *
 * @param <K>
 * @param <V>
 */
public abstract class WorkerListTask<K, V> extends PanelSwingWorker<K, V> implements IListListener<V> {

   public WorkerListTask(SwingCtx sc, IWorkerPanel wp) {
      super(sc, wp);
   }

   private ListTask<V> task;

   /**
    * The {@link ListTask} that was created.
    * 
    * null if not yet created
    * @return
    */
   public ListTask<V> getTask() {
      return task;
   }

   /**
    * Create a new instance of the specific {@link ListTask} that will be run inside this SwingWorker.
    * <br>
    * @return
    */
   protected abstract ListTask<V> createTask();

   /**
    * The TableModel that is filled with data
    * @return
    */
   protected abstract K getModel();

   /**
    * Called in the worker thread
    */
   public void newDataAvailable(List<V> list) {
      publicList(list);
   }

   /**
    * 
    * {@link SwingWorker#cancel(boolean)} will interrupt our thread.
    * If the thread is stuck in a low level io task, that's good.
    * If the thread is simply doing work, we want to cleanly let it
    * stop at the next isContinue() check.
    * 
    * Its not possible to override cancel, so cancel must be avoided
    * and {@link PanelSwingWorker}
    */
   @Override
   protected K doInBackground() {
      task = createTask();
      task.run();
      //tasks finishes by itself or it is interruped or close by ?
      return getModel();
   }

   /**
    * Publish the elements in List using {@link SwingWorker#publish} protected final method
    * @param list
    */
   public void publicList(List<V> list) {
      Iterator<V> it = list.iterator();
      while (it.hasNext()) {
         V a = it.next();
         publish(a);
         Thread.yield();
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerListTask");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(task, "ListTask");
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerListTask");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
