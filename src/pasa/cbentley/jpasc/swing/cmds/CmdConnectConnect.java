package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;

public class CmdConnectConnect extends CmdSwingAbstract<ICommandableConnect> implements IEventsPascalSwing {

   protected final PascalCmdManager pcm;

   public CmdConnectConnect(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
   }

   public String getCmdString() {
      return sc.getResString("cmd.connectmain");
   }

   public String getCmdStringTip() {
      return sc.getResString("cmd.connectmain.tip");
   }

   public void executeWith(ICommandableConnect t) {
      //first test if 
      PascalSwingCtx psc = pcm.getPSC();
      PCoreCtx pc = psc.getPCtx();
      //generate a GUI disconnect event
      BusEvent be = psc.getEventBusPascal().createEvent(PID_5_CONNECTIONS, EID_5_CONNECTIONS_4_CONNECT_WILL, this);
      be.setFlag(BusEvent.FLAG_3_USER_EVENT, true);
      psc.getEventBusPascal().putOnBus(be);

      //
      RPCConnection rpc = new RPCConnection(pc);
      boolean success = rpc.connectLocalhost();
      if (success) {

         pc.setRPCConnection(rpc);
         rpc.startPinging();
         
         this.setEnableFalse();
         pcm.getCmdConnectDisconnect().setEnableTrue();

         //show the main window?
         if (t != null) {
            t.showUISuccess();
         }

         //generate a GUI disconnect event
         be = psc.getEventBusPascal().createEvent(PID_5_CONNECTIONS, EID_5_CONNECTIONS_1_CONNECTED, this);
         be.setFlag(BusEvent.FLAG_3_USER_EVENT, true);
         psc.getEventBusPascal().putOnBus(be);

      } else {
         if (t != null) {
            t.showUIFailure();
         }
      }
   }

}
