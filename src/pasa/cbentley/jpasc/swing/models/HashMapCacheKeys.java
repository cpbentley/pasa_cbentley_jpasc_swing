/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.core.src5.utils.HashMapCache;
import pasa.cbentley.core.src5.utils.ICacheLoadingListener;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJavaNamer;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class HashMapCacheKeys extends HashMapCache<INameable<PublicKeyJava>, PublicKeyJava> implements ICacheLoadingListener<INameable<PublicKeyJava>, PublicKeyJava> {

   protected PascalSwingCtx psc;

   public HashMapCacheKeys(PascalSwingCtx psc) {
      super(psc.getC5());
      this.psc = psc;
   }

   public void addKey(PublicKeyJava keyJava) {
      //wrap it in a namer
      PublicKeyJavaNamer pk = new PublicKeyJavaNamer(keyJava);
      this.addNamer(pk);

   }

   public void addAllFrom(HashMapCacheKeys from) {
      this.pks.putAll(from.pks);
   }

   public void modelDidFinishLoading(HashMapCache<INameable<PublicKeyJava>, PublicKeyJava> model) {
      model.putAllInto(pks);
   }
}
