/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.cache;

import java.util.ArrayList;
import java.util.List;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.core.src5.utils.HashMapCache;
import pasa.cbentley.core.src5.utils.ICacheLoadingListener;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.model.ModelTableBAbstractArray;
import pasa.cbentley.swing.threads.IWorkerPanel;

public abstract class WorkerHashMapCacheAbstract<T extends HashMapCache<INameable<V>,V>,V extends IStringable> extends WorkerListTaskPage<T, V> {

   private final T model;

   protected final PascalSwingCtx                              psc;

   protected ArrayList<ICacheLoadingListener> listeners;
   
   public WorkerHashMapCacheAbstract(PascalSwingCtx psc, IWorkerPanel wp, T model) {
      super(psc.getSwingCtx(), wp);
      this.psc = psc;
      this.model = model;
      listeners = new ArrayList<ICacheLoadingListener>(2);
   }

   /**
    * Returns the {@link ModelTableBAbstractArray}
    */
   protected T getModel() {
      return model;
   }

   protected abstract INameable<V> getNameable(V v);
   
   protected void process(List<V> chunks) {
      //additional step to wrap V in a INameable
      for (V v : chunks) {
         INameable<V> namer = getNameable(v);
         model.addNamer(namer);
      }
      wp.panelSwingWorkerProcessed(this, chunks.size());
   }

   /**
    * Must be called in the UI thread
    */
   public void addTaskListener(ICacheLoadingListener listener) {
      //#debug
      psc.getSwingCtx().checkUIThread();
      
      listeners.add(listener);
   }
   
   /**
    * Must be called in the UI thread by 
    * 
    * {@link IWorkerPanel#panelSwingWorkerDone(pasa.cbentley.swing.threads.PanelSwingWorker)}
    */
   public void notifyListeners() {
      //#debug
      psc.getSwingCtx().checkUIThread();
      
      for (ICacheLoadingListener lis : listeners) {
         lis.modelDidFinishLoading(model);
      }
   }
}
