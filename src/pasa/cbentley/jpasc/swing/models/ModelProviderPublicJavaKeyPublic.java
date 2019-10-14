/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheAbstract;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheKeyPublic;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * 
 * Class that manages a local copy of all wallet keys as {@link PublicKeyJava}.
 * 
 * Provides method to create different data models for ui widgets
 * 
 * @author Charles Bentley
 * 
 * @see ModelProviderPublicJavaKeyAbstract
 */
public class ModelProviderPublicJavaKeyPublic extends ModelProviderPublicJavaKeyAbstract implements IWorkerPanel, IMyGui, IEventConsumer, IEventsPascalSwing {


   public ModelProviderPublicJavaKeyPublic(PascalSwingCtx psc) {
      super(psc);
   }

   protected WorkerHashMapCacheAbstract createWorkerKey(HashMapCacheKeys model) {
      return new WorkerHashMapCacheKeyPublic(psc, this, model);
   }


}
