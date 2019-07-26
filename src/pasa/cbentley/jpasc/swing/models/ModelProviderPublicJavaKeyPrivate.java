/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import java.util.ArrayList;
import java.util.List;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.core.src5.utils.ICacheLoadingListener;
import pasa.cbentley.jpasc.pcore.domain.PublicKeyJavaManager;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheKeyPrivate;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

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
public class ModelProviderPublicJavaKeyPrivate extends ModelProviderPublicJavaKeyAbstract implements IWorkerPanel, IMyGui, IEventConsumer, IEventsPascalSwing {

   private HashMapCacheKeys                 cacheKeyPrivate;

   /**
    * Model with wild card selectors
    */
   private HashMapCacheKeys                 cacheKeyPrivateWildcards;

   /**
    * Cloned models for private data
    */
   private List<ComboModelMapPublicKeyJava> publicKeyJavaAllClonedModelsPrivate;

   /**
    * unique instance of the worker fetching private public keys.
    */
   private WorkerHashMapCacheKeyPrivate     workerPrivate;

   public ModelProviderPublicJavaKeyPrivate(PascalSwingCtx psc) {
      super(psc);
      publicKeyJavaAllClonedModelsPrivate = new ArrayList<ComboModelMapPublicKeyJava>();
   }

   public ComboModelMapPublicKeyJava createPublicKeyJavaPrivate() {
      ComboModelMapPublicKeyJava model = new ComboModelMapPublicKeyJava(psc);
      //track all models. add it before calling
      publicKeyJavaAllClonedModelsPrivate.add(model);

      HashMapCacheKeys privateAll = getCacheKeyPrivate(model);
      if (privateAll.isDataLoaded()) {
         model.setMap(privateAll);
      } else {
         //the model will be updated by the panelSwingDone
         String loadingString = psc.getSwingCtx().getResString("text.loading.keys");
         model.addElement(loadingString);
         model.setSelectedItem(loadingString);
      }
      return model;
   }

   /**
    * Returns a model with all private usable keys and AllKey on top.
    * Other options include EmptyKeys, Barely Empty, FavoritesKey,
    * any keyset defined in preferences.
    * 
    * Which thread may call this? Only GUI
    * @return
    */
   public ComboModelMapPublicKeyJava createPublicKeyJavaPrivateWildcards() {
      ComboModelMapPublicKeyJava model = new ComboModelMapPublicKeyJava(psc);
      //track all models. add it before calling
      publicKeyJavaAllClonedModelsPrivate.add(model);

      HashMapCacheKeys privateWildcards = getCacheKeyPrivateWildcards(model);
      if (privateWildcards.isDataLoaded()) {
         model.setMap(privateWildcards);
      } else {
         //the model will be updated by the panelSwingDone
         model.addElement(psc.getSwingCtx().getResString("text.loading.keys"));
      }
      return model;
   }

   /**
    * Only single keys that can be used
    * @return
    */
   public HashMapCacheKeys getCacheKeyPrivate(ICacheLoadingListener<INameable<PublicKeyJava>, PublicKeyJava> lis) {
      if (cacheKeyPrivate == null) {

         //#debug
         toDLog().pInit("Creating cacheKeyPrivate", this, ModelProviderPublicJavaKeyPrivate.class, "getCacheKeyPrivate", LVL_05_FINE, true);

         cacheKeyPrivate = new HashMapCacheKeys(psc);
         cacheKeyPrivate.setCacheLoadingListener(lis);
         //populate the cache
         workerPrivate = new WorkerHashMapCacheKeyPrivate(psc, this, cacheKeyPrivate);
         //define task to be done at execution in UI thread
         workerPrivate.execute();
      }
      //returns early but might be empty
      return cacheKeyPrivate;
   }

   /**
    * Only single keys that can be used
    * @return
    */
   public HashMapCacheKeys getCacheKeyPrivateWildcards(ICacheLoadingListener<INameable<PublicKeyJava>, PublicKeyJava> listener) {
      if (cacheKeyPrivateWildcards == null) {

         //#debug
         toDLog().pInit("Creating cacheKeyPrivateWildcards", this, ModelProviderPublicJavaKeyPrivate.class, "getCacheKeyPrivateWildcards", LVL_05_FINE, true);

         cacheKeyPrivateWildcards = new HashMapCacheKeys(psc);
         cacheKeyPrivateWildcards.setCacheLoadingListener(listener);
         //load wildcards
         PublicKeyJavaManager pkjm = psc.getPCtx().getPublicKeyJavaManager();
         cacheKeyPrivateWildcards.addKey(pkjm.getAll());
         cacheKeyPrivateWildcards.addKey(pkjm.getEmpties());

         if (cacheKeyPrivate != null) {
            if (cacheKeyPrivate.isDataLoaded()) {
               //best case scenario
               cacheKeyPrivateWildcards.addAllFrom(cacheKeyPrivate);
            } else {
               //wait for loading
               if (workerPrivate == null) {
                  //there is an error
               }
               cacheKeyPrivate.setCacheLoadingListener(cacheKeyPrivateWildcards);
            }
         } else {
            getCacheKeyPrivate(cacheKeyPrivateWildcards);
         }
      }
      //returns early but might be empty
      return cacheKeyPrivateWildcards;
   }

   public void guiUpdate() {
      //update the value of wildcards
      psc.getPCtx().getPublicKeyJavaManager().languageUpdate();
   }

   /**
    * Called when a global refresh of keys is required (new or deleted key)
    */
   public void modelUpdate() {

   }

   public void panelSwingWorkerCancelled(PanelSwingWorker worker) {
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      if (worker == workerPrivate) {
         //cascade notify private HashMapCacheKeys
         cacheKeyPrivate.notifyFinishLoading();

         workerPrivate = null;
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
      dc.root(this, "ModelProviderPublicJavaKey");
      toStringPrivate(dc);

      dc.nlLvl(cacheKeyPrivate, "cacheKeyPrivate");
      dc.nlLvl(cacheKeyPrivateWildcards, "cacheKeyPrivateWildcards");
      dc.nlLvl(workerPrivate, "workerPrivate");

   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ModelProviderPublicJavaKey");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
