/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import java.util.Collection;

import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJavaNamer;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelComboSortedHashMapAbstract;

public class ComboBoxModelSortedHashMapPublicKeyJava extends ModelComboSortedHashMapAbstract<INameable<PublicKeyJava>, PublicKeyJava> {

   /**
    * 
    */
   private static final long      serialVersionUID = -1787528807762478044L;

   protected final PascalSwingCtx psc;

   public ComboBoxModelSortedHashMapPublicKeyJava(PascalSwingCtx psc) {
      super(psc.getSwingCtx());
      this.psc = psc;
   }

   public void addKey(PublicKeyJava keyJava) {
      //wrap it in a namer
      PublicKeyJavaNamer pk = new PublicKeyJavaNamer(keyJava);
      this.addNamer(pk);

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

   protected ComboBoxModelSortedHashMapPublicKeyJava createNewInstance() {
      return new ComboBoxModelSortedHashMapPublicKeyJava(psc);
   }

   public PublicKeyJava getKeyFromEncPubKey(String encPubKey) {
      if (encPubKey == null) {
         return null;
      }
      Collection<INameable<PublicKeyJava>> values = pks.values();
      for (INameable<PublicKeyJava> nameable : values) {
         PublicKeyJava keyJava = nameable.getNamedObject();
         if (encPubKey.equals(keyJava.getEncPubKey())) {
            return keyJava;
         }
      }
      return null;
   }

   public void addRealFrom(ComboBoxModelSortedHashMapPublicKeyJava model) {
      for (String name : model.pks.keySet()) {
         INameable<PublicKeyJava> iNameable = model.pks.get(name);
         this.addNamer(iNameable);
      }
   }

}
