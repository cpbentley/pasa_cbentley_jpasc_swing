/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.combo;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJavaNamer;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.pcore.task.list.java.key.ListTaskPublicKeyJavaWalletCanUse;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.ComboBoxModelSortedHashMapPublicKeyJava;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * Fill a combo model with {@link PublicKeyJavaNamer}
 * @author Charles Bentley
 *
 */
public class WorkerComboWalletKeyPrivate extends WorkerComboAbstract<ComboBoxModelSortedHashMapPublicKeyJava,PublicKeyJava> {

   public WorkerComboWalletKeyPrivate(PascalSwingCtx psc, IWorkerPanel wp, ComboBoxModelSortedHashMapPublicKeyJava comboModel) {
      super(psc, wp, comboModel);
   }

   protected ListTaskPage<PublicKeyJava> createTaskPage() {
      return new ListTaskPublicKeyJavaWalletCanUse(psc.getPCtx(), this);
   }

   protected INameable<PublicKeyJava> getNameable(PublicKeyJava v) {
      return new PublicKeyJavaNamer(v);
   }

   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerComboWalletKeyPrivate");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerComboWalletKeyPrivate");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   


}