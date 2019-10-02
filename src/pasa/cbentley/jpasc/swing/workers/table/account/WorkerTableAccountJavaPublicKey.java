/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.chain.ListTaskAccountPublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * List all accounts
 * @author Charles Bentley
 *
 */
public class WorkerTableAccountJavaPublicKey extends WorkerTableAccountAbstract {

   private PublicKeyJava publicKeyJava;

   /**
    * Key cannot be null here
    * @param psc
    * @param wp
    * @param tableModel
    * @param pk
    */
   public WorkerTableAccountJavaPublicKey(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel) {
      super(psc, wp, tableModel);
   }

   protected ListTaskAccountAbstract createTaskAccount() {
      ListTaskAccountPublicKeyJava task = new ListTaskAccountPublicKeyJava(psc.getPCtx(), this, publicKeyJava);
      return task;
   }

   public PublicKeyJava getPublicKeyJava() {
      return publicKeyJava;
   }

   public void setPublicKeyJava(PublicKeyJava publicKeyJava) {
      this.publicKeyJava = publicKeyJava;
   }
   
   public String getNameForUser() {
      return "Account Key "+ ((publicKeyJava != null) ? publicKeyJava.getName() : "");
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountWalletKey");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountWalletKey");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}