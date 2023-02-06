/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import javax.swing.JComboBox;

import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheAbstract;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheKeyPrivate;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * 
 * Class that manages a local copy of all wallet keys as {@link PublicKeyJava}.
 * 
 * Provides method to create different data models for ui widgets
 * 
 * Generates a {@link ComboModelMapPublicKeyJava} that can be loaded in {@link JComboBox}
 * 
 * @author Charles Bentley
 * 
 * @see ModelProviderPublicJavaKeyAbstract
 */
public class ModelProviderPublicJavaKeyPrivate extends ModelProviderPublicJavaKeyAbstract implements IWorkerPanel, IMyGui, IEventConsumer, IEventsPascalSwing {


   public ModelProviderPublicJavaKeyPrivate(PascalSwingCtx psc) {
      super(psc);
   }

   protected WorkerHashMapCacheAbstract createWorkerKey(HashMapCacheKeys model) {
      return new WorkerHashMapCacheKeyPrivate(psc, this, model);
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ModelProviderPublicJavaKeyPrivate.class);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ModelProviderPublicJavaKeyPrivate.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
