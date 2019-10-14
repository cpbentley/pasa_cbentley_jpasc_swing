/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComboBox;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.core.src5.utils.HashMapCache;
import pasa.cbentley.core.src5.utils.ICacheLoadingListener;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJavaNamer;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelComboMapCache;

/**
 * a model of {@link PublicKeyJava} for {@link JComboBox}
 * 
 * @author Charles Bentley
 *
 */
public class ComboModelMapPublicKeyJava extends ModelComboMapCache<INameable<PublicKeyJava>, PublicKeyJava> implements ICacheLoadingListener<INameable<PublicKeyJava>, PublicKeyJava> {

   /**
    * 
    */
   private static final long                   serialVersionUID = -1787528807762478044L;

   private IComboModelMapPublicKeyJavaListener listenerComboMap;

   private ArrayList<IComboModelMapPublicKeyJavaListener> listeners;

   protected final PascalSwingCtx              psc;

   public ComboModelMapPublicKeyJava(PascalSwingCtx psc) {
      super(psc.getSwingCtx());
      this.psc = psc;
   }

   public void addKey(PublicKeyJava keyJava) {
      //wrap it in a namer
      PublicKeyJavaNamer pk = new PublicKeyJavaNamer(keyJava);
      this.addNamer(pk);

   }

   public void addListenerComboMap(IComboModelMapPublicKeyJavaListener listenerComboMap) {
      if (listeners == null) {
         listeners = new ArrayList<IComboModelMapPublicKeyJavaListener>(2);
      }
      this.listeners.add(listenerComboMap);
   }

   protected ComboModelMapPublicKeyJava createNewInstance() {
      return new ComboModelMapPublicKeyJava(psc);
   }

   public PublicKeyJava getKeyFromEncPubKey(String encPubKey) {
      if (encPubKey == null) {
         return null;
      }
      Collection<INameable<PublicKeyJava>> values = hashMapCache.getValues();
      for (INameable<PublicKeyJava> nameable : values) {
         PublicKeyJava keyJava = nameable.getNamedObject();
         if (encPubKey.equals(keyJava.getEncPubKey())) {
            return keyJava;
         }
      }
      return null;
   }

   public IComboModelMapPublicKeyJavaListener getListenerComboMap() {
      return listenerComboMap;
   }

   /**
    * Returns the currently selected {@link PublicKeyJava}
    * @return
    */
   public PublicKeyJava getSelectedPublicKeyJava() {
      INameable<PublicKeyJava> selectedObject = getSelectedObject();
      if (selectedObject == null) {
         return null;
      } else {
         return selectedObject.getNamedObject();
      }
   }

   /**
    * Called by {@link ICacheLoadingListener#modelDidFinishLoading(HashMapCache)} when
    * the model data has been loaded.
    * 
    * This method call model listener so that UI can select the first element
    */
   public void modelDidFinishLoading(HashMapCache<INameable<PublicKeyJava>, PublicKeyJava> model) {
      this.setMap(model);
      //cascade to a listener notify
      notifyListeners();
   }

   private void notifyListeners() {
      if (listeners != null) {
         for (IComboModelMapPublicKeyJavaListener lis : listeners) {
            lis.modelDidFinishLoading(this);
         }
      }
   }

   public void removeListenerComboMap(IComboModelMapPublicKeyJavaListener listenerComboMap) {
      if (listeners != null) {
         this.listeners.remove(listenerComboMap);
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "ComboModelMapPublicKeyJava");
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(listenerComboMap, "IComboModelMapPublicKeyJavaListener");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ComboModelMapPublicKeyJava");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
