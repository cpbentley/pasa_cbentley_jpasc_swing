package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;

public class CmdLockLock extends CmdSwingAbstract<ICommandable> implements IEventsPascalSwing {

   protected final PascalCmdManager pcm;

   public CmdLockLock(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
   }

   public String getCmdString() {
      return sc.getResString("cmd.lock");
   }

   public String getCmdStringTip() {
      return sc.getResString("cmd.lock.tip");
   }

   public void executeWith(ICommandable t) {
      //first test if 
      PascalSwingCtx psc = pcm.getPSC();
      RPCConnection con = psc.getPCtx().getRPCConnection();
      if (con.isConnected()) {
         if(con.isLocked()) {
            psc.getLog().consoleLogGreen("Wallet is already locked. Double locking it!");
         }
         boolean b = con.lock();
         if (b) {
            psc.getLog().consoleLogDateGreen("Wallet locked.");
            //send an event to confirm it was locked
            //generate a GUI disconnect event
            BusEvent be = psc.getEventBusPascal().createEvent(PID_4_WALLET_LOCK, EID_4_WALLET_LOCK_1_LOCKED, this);
            be.setFlag(BusEvent.FLAG_3_USER_EVENT, true);
            be.putOnBus();
         } else {
            psc.getLog().consoleLogError("Wallet failed to lock!");
         }
         
         //we never disable the lock cmd. because we want the user to press the lock whenever he wants
      } else {
         psc.getLog().consoleLogError("You must be connected in order to lock/unlock a wallet");
      }
   }

}
