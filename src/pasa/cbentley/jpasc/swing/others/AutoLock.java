/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.others;

import java.util.TimerTask;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class AutoLock extends TimerTask {

   private PascalSwingCtx psc;

   public AutoLock(PascalSwingCtx psc) {
      this.psc = psc;
   }
   
   public void run() {
      boolean b = psc.getPCtx().getRPCConnection().lock();
      if(b) {
         psc.getSwingCtx().publishUILog(IUserLog.consoleLogGreen, "Wallet was auto locked");
         //send an event.. but we are on another thread..not our problem.. consumer requires
         IEventBus pascalBus = psc.getEventBusPascal();
         BusEvent be = pascalBus.createEvent(IEventsPascalSwing.PID_4_WALLET_LOCK, IEventsPascalSwing.EID_4_WALLET_LOCK_1_LOCKED, this);
         psc.getSwingCtx().publishUIEvent(be, pascalBus);
      } else {
         psc.getSwingCtx().publishUILog(IUserLog.consoleLogError, "Wallet failed to auto lock ");
      }
   }

}
