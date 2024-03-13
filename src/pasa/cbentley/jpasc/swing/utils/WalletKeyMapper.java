/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.tools.KeyNameProvider;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.WorkerWalletMapping;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * Update the {@link KeyNameProvider} with daemon wallet keys
 * 
 * @author Charles Bentley
 *
 */
public class WalletKeyMapper implements IWorkerPanel, IEventsPascalSwing, IEventConsumer {

   private PascalSwingCtx psc;

   public WalletKeyMapper(PascalSwingCtx psc) {
      this.psc = psc;

      psc.getEventBusPascal().addConsumer(this, PID_5_CONNECTIONS, EID_5_CONNECTIONS_0_ANY);

   }

   public void consumeEvent(BusEvent e) {
      if (e.getProducerID() == PID_5_CONNECTIONS) {
         if (e.getEventID() == EID_5_CONNECTIONS_1_CONNECTED) {
            WorkerWalletMapping wwm = new WorkerWalletMapping(psc, this);
            wwm.execute();
         }
         e.flagActed();
      }
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker worker) {
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      if (worker instanceof WorkerWalletMapping) {
         try {
            Map<String, String> ownerWallet = ((WorkerWalletMapping) worker).get();
            if (ownerWallet != null) {
               psc.getPCtx().getKeyNameProvider().getPkNameStorePrivate().setWalletMapping(ownerWallet);
            }
         } catch (InterruptedException e) {
            e.printStackTrace();
         } catch (ExecutionException e) {
            e.printStackTrace();
         }
      }
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "WalletKeyMapper");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WalletKeyMapper");
   }
   //#enddebug

   public UCtx toStringGetUCtx() {
      return psc.getUC();
   }

}
