/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.cache;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.synch.MutexSignal;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.core.src5.utils.HashMapCache;
import pasa.cbentley.core.src5.utils.ICacheLoadingListener;
import pasa.cbentley.jpasc.pcore.domain.PublicKeyJavaManager;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJavaNamer;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.pcore.task.ListTaskPageCustomRunEmpty;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.HashMapCacheKeys;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * Fill a combo model with {@link PublicKeyJavaNamer}
 * @author Charles Bentley
 *
 */
public class WorkerHashMapCacheKeyPrivateWild extends WorkerHashMapCacheAbstract<HashMapCacheKeys, PublicKeyJava> implements ICacheLoadingListener {

   private HashMapCacheKeys            mapReal;

   private WorkerHashMapCacheAbstract  workerReal;

   private HashMapCacheKeys            map;

   private ListTaskPage<PublicKeyJava> task;

   private MutexSignal                 mutex;

   /**
    * Worker depends on another model and its worker
    * @param psc
    * @param wp
    * @param map
    * @param mapReal
    * @param workerReal
    */
   public WorkerHashMapCacheKeyPrivateWild(PascalSwingCtx psc, IWorkerPanel wp, HashMapCacheKeys map, HashMapCacheKeys mapReal, WorkerHashMapCacheAbstract workerReal) {
      super(psc, wp, map);
      this.map = map;
      this.mapReal = mapReal;
      this.workerReal = workerReal;

      if (workerReal == null || mapReal.isDataLoaded()) {
         //best case scenario just publish
         modelDidFinishLoading(mapReal);
         //list task will return early
         task = new ListTaskPageCustomRunEmpty<PublicKeyJava>(psc.getPCtx(), this);
      } else {
         mutex = new MutexSignal(psc.getUC());
         workerReal.addTaskListener(this);
         //list task will wait
         task = new ListTaskFromWorkerCache(psc.getPCtx(), this, mutex);
      }
   }

   public void modelDidFinishLoading(HashMapCache model) {
      //#debug
      psc.getSwingCtx().checkUIThread();

      //load wildcards
      PublicKeyJavaManager pkjm = psc.getPCtx().getPublicKeyJavaManager();

      map.addKey(pkjm.getAll());
      map.addKey(pkjm.getEmpties());
      map.addAllFrom(mapReal);

      if (mutex != null) {
         //release the task
         mutex.release();
      }
   }

   protected ListTaskPage<PublicKeyJava> createTaskPage() {
      return task;
   }

   public WorkerHashMapCacheAbstract getWorkerReal() {
      return workerReal;
   }

   public HashMapCacheKeys getMapReal() {
      return mapReal;
   }

   protected INameable<PublicKeyJava> getNameable(PublicKeyJava v) {
      return new PublicKeyJavaNamer(v);
   }

   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerHashMapCacheKeyPrivate");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerHashMapCacheKeyPrivate");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}