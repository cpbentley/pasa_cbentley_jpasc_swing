/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.SortOrder;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountFullData;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountJavaPublicKey;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountWalletKey;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Table showing the accounts of a {@link PublicKeyJava}
 * 
 * For wallet key, it will fetch
 * 
 * The UI does not provide a way to change the key. It can only be set by outside
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountPublicKeyJava extends TablePanelAccountAbstract implements IMyTab {

   public static final String KEY              = "list_account_pkjava";

   /**
    * 
    */
   private static final long  serialVersionUID = -2019545956569117458L;

   private PublicKeyJava      pk;

   public TablePanelAccountPublicKeyJava(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, KEY, root);
   }

   protected WorkerTableAccountAbstract createWorker() {
      if (pk == null) {
         pk = new PublicKeyJava(psc.getPCtx());
         pk.setEncPubKey("emptykey");
      }
      if (pk.isWalletKey()) {
         WorkerTableAccountWalletKey worker = new WorkerTableAccountWalletKey(psc, this, getTableModel());
         //make sure the pubkey has been fetched
         String encPubKey = pk.getEncPubKey();
         if (encPubKey == null) {
            String base58 = pk.getBase58PubKey();
            if (base58 == null) {
               throw new IllegalArgumentException("Key without data");
            }
         }
         worker.setEncPubKey(pk.getEncPubKey());
         return worker;
      } else {
         WorkerTableAccountJavaPublicKey worker = new WorkerTableAccountJavaPublicKey(psc, this, getTableModel());
         worker.setPublicKeyJava(pk);
         return worker;
      }
   }

   /**
    * Sorts the table in {@link SortOrder#DESCENDING}
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      sortTableColDescending(ModelTableAccountFullData.INDEX_03_BALANCE);
   }

   public void setPublicKeyJava(PublicKeyJava pk) {
      this.workerTable = null; //resets the worker
      this.pk = pk;
      //check if key is wallet key
      boolean isWalletKey = psc.getPCtx().getKeyNameProvider().getPkNameStorePrivate().hasWalletKey(pk.getEncPubKey());
      if (isWalletKey) {
         pk.setWalletKey(true);
      }
      //
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItemsNoSendNoKey(menu);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountWalletBalance");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountWalletBalance");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}