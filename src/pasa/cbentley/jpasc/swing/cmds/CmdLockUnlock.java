package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;

public class CmdLockUnlock extends CmdSwingAbstract<ICommandable> implements IEventsPascalSwing {

   protected final PascalCmdManager pcm;

   public CmdLockUnlock(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
   }

   public String getCmdString() {
      return sc.getResString("cmd.unlock");
   }

   public String getCmdStringTip() {
      return sc.getResString("cmd.unlock.tip");
   }

   public void executeWith(ICommandable t) {
      //first test if 
      PascalSwingCtx psc = pcm.getPSC();
      PCoreCtx pc = psc.getPCtx();
      RPCConnection rpc = pc.getRPCConnection();
      if (rpc.isConnected()) {
         if (rpc.isLocked()) {
            unlockMethod(psc);
         } else {
            psc.getLog().consoleLogGreen("Wallet seems to already be unlocked.. Synching status");
            //todo add consumer for a given number of consumption?
            psc.getSwingCtx().executeLaterInUIThread(new Runnable() {
               public void run() {
                  //call that in a worker thread with a callback
                  rpc.synchLockStatus();
                  if(rpc.isLocked()) {
                     //it was locked.. unlock it
                     unlockMethod(psc);
                  }
               }
            });
         }
      } else {
         psc.getLog().consoleLogError("You must be connected in order to lock/unlock a wallet");
      }
   }

   private void unlockMethod(PascalSwingCtx psc) {
      boolean isUnlocked = psc.askToUnlock("Enter the Daemon Wallet Password", "", psc.getFrameRoot());

      if (isUnlocked) {
         psc.getLog().consoleLogGreen("Wallet is now unlocked");
         BusEvent be = psc.getEventBusPascal().createEvent(PID_4_WALLET_LOCK, EID_4_WALLET_LOCK_2_UNLOCKED, this);
         be.setFlag(BusEvent.FLAG_3_USER_EVENT, true);
         be.putOnBus();
      } else {
         psc.getLog().consoleLog("Wallet fail to unlock! Probably wrong password");
      }
   }

}
