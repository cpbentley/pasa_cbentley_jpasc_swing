/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import java.util.ArrayList;
import java.util.List;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.core.src5.utils.ICacheLoadingListener;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheKeyPrivate;
import pasa.cbentley.jpasc.swing.workers.cache.WorkerHashMapCacheKeyPublic;
import pasa.cbentley.jpasc.swing.workers.combo.WorkerComboWalletKeyPrivate;
import pasa.cbentley.jpasc.swing.workers.combo.WorkerComboWalletKeyPublic;
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
 */
public class ModelProviderPublicJavaKey implements IWorkerPanel, IMyGui, IEventConsumer, IEventsPascalSwing {

   private HashMapCacheKeys                              cacheKeyPrivate;

   /**
    * 
    */
   private HashMapCacheKeys                              cacheKeyPrivateAll;

   private HashMapCacheKeys                              cacheKeyPublic;

   private PascalSwingCtx                                psc;

   private final PublicKeyJava                           publicKeyJavaAll;

   /**
    * Cloned models for private data
    */
   private List<ComboBoxModelSortedHashMapPublicKeyJava> publicKeyJavaAllClonedModelsPrivate;

   private List<ComboBoxModelSortedHashMapPublicKeyJava> publicKeyJavaAllClonedModelsPublic;

   private ComboBoxModelSortedHashMapPublicKeyJava       publicKeyJavaChainFavorites;

   private final PublicKeyJava                           publicKeyJavaEmpties;

   /**
    * The model reference never leaks out of this class.
    * Getter creates a clone
    */
   private ComboBoxModelSortedHashMapPublicKeyJava       publicKeyJavaPrivateAll;

   //use this as base for can use keys
   private ComboBoxModelSortedHashMapPublicKeyJava       publicKeyJavaSinglesPrivate;

   //use this as base for can not use keys
   private ComboBoxModelSortedHashMapPublicKeyJava       publicKeyJavaSinglesPublic;

   /**
    * unique instance of the worker fetching private public keys.
    */
   private WorkerHashMapCacheKeyPrivate                  workerPrivate;

   private WorkerHashMapCacheKeyPublic                   workerPublic;

   public ModelProviderPublicJavaKey(PascalSwingCtx psc) {
      this.psc = psc;

      publicKeyJavaAllClonedModelsPrivate = new ArrayList<ComboBoxModelSortedHashMapPublicKeyJava>();

      publicKeyJavaAll = new PublicKeyJava(psc.getPCtx());
      publicKeyJavaAll.setName(psc.getSwingCtx().getResString("text.all.keys"));

      publicKeyJavaEmpties = new PublicKeyJava(psc.getPCtx());
      publicKeyJavaEmpties.setName(psc.getSwingCtx().getResString("text.keys.empties"));

      //TODO listen to user modifiation key events.. connections as well
      psc.getEventBusPascal().addConsumer(this, PID_5_CONNECTIONS, EID_5_CONNECTIONS_0_ANY);
      psc.getEventBusPascal().addConsumer(this, PID_6_KEY_LOCAL_OPERATION, EID_6_KEY_LOCAL_OPERATION_0_ANY);
   }

   public void consumeEvent(BusEvent e) {
      if (e.getProducerID() == PID_5_CONNECTIONS) {
         //refresh
         if (e.getEventID() == EID_5_CONNECTIONS_1_CONNECTED) {
            //we have to clear current models data
            if (publicKeyJavaSinglesPrivate != null) {
               publicKeyJavaSinglesPrivate.removeAllElements();
               WorkerComboWalletKeyPrivate worker = new WorkerComboWalletKeyPrivate(psc, this, publicKeyJavaSinglesPrivate);
               worker.execute();
            }
            if (publicKeyJavaSinglesPublic != null) {
               publicKeyJavaSinglesPublic.removeAllElements();
               WorkerComboWalletKeyPublic worker = new WorkerComboWalletKeyPublic(psc, this, publicKeyJavaSinglesPublic);
               worker.execute();
            }
         }
         e.flagActed();
      } else if (e.getProducerID() == PID_6_KEY_LOCAL_OPERATION) {

      }
   }

   public void removeModel(ComboBoxModelSortedHashMapPublicKeyJava model) {
      publicKeyJavaAllClonedModelsPrivate.remove(model);
      publicKeyJavaAllClonedModelsPublic.remove(model);
   }


   /**
    * Only single keys that can be used
    * @return
    */
   public HashMapCacheKeys getCacheKeyPrivate(ICacheLoadingListener<INameable<PublicKeyJava>, PublicKeyJava> lis) {
      if (cacheKeyPrivate == null) {
         cacheKeyPrivate = new HashMapCacheKeys(psc);
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
   public HashMapCacheKeys getCacheKeyPrivateAll(ICacheLoadingListener<INameable<PublicKeyJava>, PublicKeyJava> lis) {
      if (cacheKeyPrivateAll == null) {
         cacheKeyPrivateAll = new HashMapCacheKeys(psc);
         cacheKeyPrivateAll.addKey(publicKeyJavaAll);
         cacheKeyPrivateAll.addKey(publicKeyJavaEmpties);
         if (cacheKeyPrivate != null) {
            if (cacheKeyPrivate.isDataLoaded()) {
               //best case scenario
               cacheKeyPrivateAll.addAllFrom(cacheKeyPrivate);
            } else {
               //wait for loading
               if (workerPrivate == null) {
                  //there is an error
               }
            }
         } else {
            getCacheKeyPrivate(cacheKeyPrivateAll);
         }
      }
      //returns early but might be empty
      return cacheKeyPrivateAll;
   }

   public HashMapCacheKeys getCacheKeyPublic() {
      if (cacheKeyPublic == null) {
         cacheKeyPublic = new HashMapCacheKeys(psc);
         //populate the cache
         workerPublic = new WorkerHashMapCacheKeyPublic(psc, this, cacheKeyPublic);
         //define task to be done at execution in UI thread
         workerPublic.execute();
      }
      //returns early but might be empty
      return cacheKeyPublic;
   }

   public PublicKeyJava getPublicKeyJavaAll() {
      return publicKeyJavaAll;
   }

   /**
    * Only single keys that can be used
    * @return
    */
   public ComboBoxModelSortedHashMapPublicKeyJava getPublicKeyJavaChainFavorites() {
      if (publicKeyJavaChainFavorites == null) {
         publicKeyJavaChainFavorites = new ComboBoxModelSortedHashMapPublicKeyJava(psc);
         //populate the model
         WorkerComboWalletKeyPublic worker = new WorkerComboWalletKeyPublic(psc, this, publicKeyJavaChainFavorites);
         worker.execute();
      }
      ComboBoxModelSortedHashMapPublicKeyJava clone = (ComboBoxModelSortedHashMapPublicKeyJava) publicKeyJavaSinglesPublic.cloneUpperModel();
      publicKeyJavaAllClonedModelsPublic.add(clone);
      return clone;
   }

   public PublicKeyJava getPublicKeyJavaEmpties() {
      return publicKeyJavaEmpties;
   }

   /**
    * Only single keys that can be used
    * @return
    */
   public ComboBoxModelSortedHashMapPublicKeyJava getPublicKeyJavaSinglesPrivate() {
      if (publicKeyJavaSinglesPrivate == null) {
         publicKeyJavaSinglesPrivate = new ComboBoxModelSortedHashMapPublicKeyJava(psc);
         //populate the model
         WorkerComboWalletKeyPrivate worker = new WorkerComboWalletKeyPrivate(psc, this, publicKeyJavaSinglesPrivate);
         worker.execute();
      }
      ComboBoxModelSortedHashMapPublicKeyJava clone = (ComboBoxModelSortedHashMapPublicKeyJava) publicKeyJavaSinglesPrivate.cloneUpperModel();
      publicKeyJavaAllClonedModelsPrivate.add(clone);
      return clone;
   }

   /**
    * Only single keys that can be used
    * @return
    */
   public ComboBoxModelSortedHashMapPublicKeyJava getPublicKeyJavaSinglesPublic() {
      if (publicKeyJavaSinglesPublic == null) {
         publicKeyJavaSinglesPublic = new ComboBoxModelSortedHashMapPublicKeyJava(psc);
         //populate the model
         WorkerComboWalletKeyPublic worker = new WorkerComboWalletKeyPublic(psc, this, publicKeyJavaSinglesPublic);
         worker.execute();
      }
      ComboBoxModelSortedHashMapPublicKeyJava clone = (ComboBoxModelSortedHashMapPublicKeyJava) publicKeyJavaSinglesPublic.cloneUpperModel();
      publicKeyJavaAllClonedModelsPublic.add(clone);
      return clone;
   }

   public void guiUpdate() {
      publicKeyJavaAll.setName(psc.getSwingCtx().getResString("text.allkeys"));
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker worker) {
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      if (worker == workerPrivate) {
         //cascade notify private HashMapCacheKeys

         processFoundSinglesPrivate();
         workerPrivate = null;
      }
      if (worker == workerPublic) {
         //update the clones for those
         processFoundSinglesPublic();
         workerPublic = null;
      }
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {

   }

   private void processFoundSinglesPrivate() {
      for (ComboBoxModelSortedHashMapPublicKeyJava clone : publicKeyJavaAllClonedModelsPrivate) {
         clone.cloneReplaceData(publicKeyJavaSinglesPrivate);
         clone.notifyFinishLoading();
      }
      //
      if (publicKeyJavaSinglesPrivate != null) {
         publicKeyJavaPrivateAll.addRealFrom(publicKeyJavaSinglesPrivate);
      }
   }

   private void processFoundSinglesPublic() {
      for (ComboBoxModelSortedHashMapPublicKeyJava clone : publicKeyJavaAllClonedModelsPublic) {
         //this erase special keys...
         clone.cloneReplaceData(publicKeyJavaSinglesPublic);
      }
      if (publicKeyJavaSinglesPublic != null) {
         publicKeyJavaPrivateAll.addRealFrom(publicKeyJavaSinglesPublic);
      }
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

      dc.nlLvl(publicKeyJavaAll, "publicKeyJavaAll");
      dc.nlLvl(publicKeyJavaEmpties, "publicKeyJavaEmpties");
      dc.nlLvl(publicKeyJavaSinglesPublic, "publicKeyJavaSinglesPublic");
      dc.nlLvl(publicKeyJavaSinglesPrivate, "publicKeyJavaSinglesPrivate");
      dc.nlLvl(publicKeyJavaPrivateAll, "publicKeyJavaPrivateAll");
      dc.nlLvl(publicKeyJavaChainFavorites, "publicKeyJavaChainFavorites");

      C5Ctx c5 = psc.getPCtx().getC5();
      c5.toStringListStringable(dc, publicKeyJavaAllClonedModelsPrivate, "publicKeyJavaAllClonedModelsPrivate");
      c5.toStringListStringable(dc, publicKeyJavaAllClonedModelsPublic, "publicKeyJavaAllClonedModelsPublic");
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
