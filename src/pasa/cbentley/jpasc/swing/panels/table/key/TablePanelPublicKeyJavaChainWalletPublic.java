/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.key;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaChain;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaMyAssets;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyAbstract;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyWalletPublic;

/**
 * The keys that cannot be used in the reference wallet
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelPublicKeyJavaChainWalletPublic extends TablePanelPublicKeyJavaChainAbstract {
   
   
   public static final String KEY                       = "list_wallet_pub_keys";

   /**
    * 
    */
   private static final long  serialVersionUID          = 1L;


   public TablePanelPublicKeyJavaChainWalletPublic(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, KEY, root);
   }


   /**
    * Worker for getting the keys
    */
   protected WorkerTableKeyAbstract createWorker() {
      WorkerTableKeyWalletPublic worker = new WorkerTableKeyWalletPublic(psc, this, getTableModel());
      return worker;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelPublicKeyJavaChainWallet");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelPublicKeyJavaChainWallet");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
