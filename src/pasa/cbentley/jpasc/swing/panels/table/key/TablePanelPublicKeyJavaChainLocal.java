/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.key;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAutoPrivate;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAutoPublic;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyLocal;

/**
 * The keys that were registered by the user.
 * 
 * In public mode, only shows public keys
 * In private mode, shows a column with private name and one with public name
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelPublicKeyJavaChainLocal extends TablePanelPublicKeyJavaChainAbstract {
   
   
   public static final String KEY                       = "list_local_pub_keys";

   /**
    * 
    */
   private static final long  serialVersionUID          = 1L;


   public TablePanelPublicKeyJavaChainLocal(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, KEY, root);
      
      psc.getEventBusPascal().addConsumer(this, PID_7_PRIVACY_CHANGES, EID_7_PRIVACY_0_ANY);
   }


   public void consumeEvent(BusEvent e) {
      if (e.getProducerID() == PID_7_PRIVACY_CHANGES) {
         this.workerTable = null; //reset worker so it is created again with new model
         getBenTable().setModel(createTableModel());
         cmdTableRefresh();
      }
      super.consumeEvent(e);
   }
   
   /**
    * We override for {@link TableModelPublicKeyJavaMyAssets}
    */
   protected ModelTablePublicKeyJavaAbstract createTableModel() {
      if(psc.isPrivateCtx()) {
         return new ModelTablePublicKeyJavaAutoPrivate(psc);
      } else {
         return new ModelTablePublicKeyJavaAutoPublic(psc);
      }
   }
   
   
   /**
    * Worker for getting the keys
    */
   protected WorkerTableKeyLocal createWorker() {
      WorkerTableKeyLocal worker = new WorkerTableKeyLocal(psc, this, getTableModel());
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
