/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.core.src5.utils.ICacheLoadingListener;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheAbstract;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheKeyPrivateWild;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * 
 * stub Class that manages a local copy of all wallet keys as {@link PublicKeyJava}.
 * 
 * @author Charles Bentley
 * @see ModelProviderPublicJavaKeyPrivate
 */
public abstract class ModelProviderPublicJavaKeyAbstract implements IWorkerPanel, IMyGui, IEventConsumer, IEventsPascalSwing {

   /**
    * 
    */
   protected HashMapCacheKeys                 cacheKeyPrivate;

   /**
    * Model with wild card selectors
    */
   protected HashMapCacheKeys                 cacheKeyPrivateWildcards;

   /**
    * 
    */
   protected final PascalSwingCtx             psc;

   /**
    * Cloned models for private data
    */
   protected List<ComboModelMapPublicKeyJava> publicKeyJavaAllClonedModelsPrivate;

   /**
    * unique instance of the worker fetching private public keys.
    */
   private WorkerHashMapCacheAbstract         workerPrivate;

   /**
    * 
    */
   private WorkerHashMapCacheAbstract         workerPrivateWild;

   public ModelProviderPublicJavaKeyAbstract(PascalSwingCtx psc) {
      this.psc = psc;
      publicKeyJavaAllClonedModelsPrivate = new ArrayList<ComboModelMapPublicKeyJava>();
      //TODO listen to user modifiation key events.. connections as well
      psc.getEventBusPascal().addConsumer(this, PID_5_CONNECTIONS, EID_5_CONNECTIONS_0_ANY);
      psc.getEventBusPascal().addConsumer(this, PID_6_KEY_LOCAL_OPERATION, EID_6_KEY_LOCAL_OPERATION_0_ANY);
   }

   public void consumeEvent(BusEvent e) {
      if (e.getProducerID() == PID_5_CONNECTIONS) {
         //refresh
         if (e.getEventID() == EID_5_CONNECTIONS_1_CONNECTED) {
            //we have to clear current models data
         }
         e.flagActed();
      } else if (e.getProducerID() == PID_6_KEY_LOCAL_OPERATION) {

      }
   }

   /**
    * Factory method for creating the 
    * @param model
    * @return WorkerHashMapCacheAbstract
    */
   protected abstract WorkerHashMapCacheAbstract createWorkerKey(HashMapCacheKeys model);

   public void guiUpdate() {
      //update the value of wildcards
      psc.getPCtx().getPublicKeyJavaManager().languageUpdate();
   }

   /**
    * Only single keys that can be used
    * @return
    */
   public void loadCacheKeyPrivate(ICacheLoadingListener<INameable<PublicKeyJava>, PublicKeyJava> lis) {
      initMainCache();
      //
      if (workerPrivate != null) {
         //we have a worker currently loading
         workerPrivate.addTaskListener(lis);
      } else {
         lis.modelDidFinishLoading(cacheKeyPrivate);
      }
   }

   private void initMainCache() {
      if (cacheKeyPrivate == null) {
         //#debug
         toDLog().pInit("Creating cacheKeyPrivate", this, ModelProviderPublicJavaKeyAbstract.class, "getCacheKeyPrivate", LVL_05_FINE, true);
         cacheKeyPrivate = new HashMapCacheKeys(psc);
         //populate the cache
         workerPrivate = createWorkerKey(cacheKeyPrivate);
         //define task to be done at execution in UI thread
         workerPrivate.execute();
      }
   }

   /**
    * Only single keys that can be used
    * 
    * @param listener Will get notified when the {@link HashMapCacheKeys} is loaded
    * @return
    */
   public void loadCacheKeyPrivateWildcards(ICacheLoadingListener<INameable<PublicKeyJava>, PublicKeyJava> listener) {
      //hashmap with wildcards and private keys
      if (cacheKeyPrivateWildcards == null) {
         //#debug
         toDLog().pInit("Creating cacheKeyPrivateWildcards", this, ModelProviderPublicJavaKeyAbstract.class, "getCacheKeyPrivateWildcards", LVL_05_FINE, true);
         cacheKeyPrivateWildcards = new HashMapCacheKeys(psc);

         initMainCache();

         workerPrivateWild = new WorkerHashMapCacheKeyPrivateWild(psc, this, cacheKeyPrivateWildcards, cacheKeyPrivate, workerPrivate);
         //define task to be done at execution in UI thread
         workerPrivateWild.execute();
      }
      if (workerPrivateWild != null) {
         //we have a worker currently loading
         workerPrivateWild.addTaskListener(listener);
      } else {
         listener.modelDidFinishLoading(cacheKeyPrivateWildcards);
      }
   }

   /**
    * 
    * @return ComboModelMapPublicKeyJava
    */
   public ComboModelMapPublicKeyJava createModelPublicKeyJava() {
      ComboModelMapPublicKeyJava model = new ComboModelMapPublicKeyJava(psc);
      //track all models. add it before calling
      publicKeyJavaAllClonedModelsPrivate.add(model);

      model.addElement(psc.getSwingCtx().getResString("text.loading.keys"));
      loadCacheKeyPrivate(model);
      return model;
   }

   /**
    * Creates a new model for a {@link JComboBox}
    * 
    * The underlying data used
    * Returns a model with all private usable keys and AllKey on top.
    * Other options include EmptyKeys, Barely Empty, FavoritesKey,
    * any keyset defined in preferences.
    * 
    * Which thread may call this? Only GUI
    * @return
    */
   public ComboModelMapPublicKeyJava createModelPublicKeyJavaWildcards() {
      ComboModelMapPublicKeyJava model = new ComboModelMapPublicKeyJava(psc);

      //track all models. add it before calling
      publicKeyJavaAllClonedModelsPrivate.add(model);

      //by default assume model is not loaded
      model.addElement(psc.getSwingCtx().getResString("text.loading.keys"));

      loadCacheKeyPrivateWildcards(model);
      return model;
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker worker) {
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {

      //#debug
      psc.getSwingCtx().checkUIThread();

      if (worker == workerPrivate) {
         //cascade notify private HashMapCacheKeys
         workerPrivate.notifyListeners();
         workerPrivate = null;
      }

      if (worker == workerPrivateWild) {
         //cascade notify private HashMapCacheKeys
         workerPrivateWild.notifyListeners();
         workerPrivateWild = null;
      }
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {

   }

   public void removeModel(ComboBoxModelSortedHashMapPublicKeyJava model) {
      publicKeyJavaAllClonedModelsPrivate.remove(model);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ModelProviderPublicJavaKeyAbstract.class);
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ModelProviderPublicJavaKeyAbstract.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUC();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
