/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.cache;

import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJavaNamer;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.pcore.task.list.java.key.ListTaskPublicKeyJavaWalletCanUse;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.HashMapCacheKeys;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * Fill a combo model with {@link PublicKeyJavaNamer}
 * @author Charles Bentley
 *
 */
public class WorkerHashMapCacheKeyPrivate extends WorkerHashMapCacheAbstract<HashMapCacheKeys, PublicKeyJava> {

   public WorkerHashMapCacheKeyPrivate(PascalSwingCtx psc, IWorkerPanel wp, HashMapCacheKeys map) {
      super(psc, wp, map);
   }

   protected ListTaskPage<PublicKeyJava> createTaskPage() {
      return new ListTaskPublicKeyJavaWalletCanUse(psc.getPCtx(), this);
   }

   protected INameable<PublicKeyJava> getNameable(PublicKeyJava v) {
      return new PublicKeyJavaNamer(v);
   }

}