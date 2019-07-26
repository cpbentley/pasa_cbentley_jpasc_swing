/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.models;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * 
 * stub Class that manages a local copy of all wallet keys as {@link PublicKeyJava}.
 * 
 * @author Charles Bentley
 * @see ModelProviderPublicJavaKeyPrivate
 */
public abstract class ModelProviderPublicJavaKeyAbstract implements IWorkerPanel, IMyGui, IEventConsumer, IEventsPascalSwing {

   public ModelProviderPublicJavaKeyAbstract(PascalSwingCtx psc) {
      this.psc = psc;
      //TODO listen to user modifiation key events.. connections as well
      psc.getEventBusPascal().addConsumer(this, PID_5_CONNECTIONS, EID_5_CONNECTIONS_0_ANY);
      psc.getEventBusPascal().addConsumer(this, PID_6_KEY_LOCAL_OPERATION, EID_6_KEY_LOCAL_OPERATION_0_ANY);
   }

   public void consumeEvent(BusEvent e) {
      if (e.getProducerID() == PID_5_CONNECTIONS) {
         //refresh
         if (e.getEventID() == EID_5_CONNECTIONS_1_CONNECTED) {
            //we have to clear current models data
         }
         e.flagActed();
      } else if (e.getProducerID() == PID_6_KEY_LOCAL_OPERATION) {

      }
   }

   protected final PascalSwingCtx psc;

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
