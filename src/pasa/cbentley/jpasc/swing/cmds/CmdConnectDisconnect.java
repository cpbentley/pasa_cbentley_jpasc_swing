package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;

public class CmdConnectDisconnect extends CmdSwingAbstract<ICommandable> implements IEventsPascalSwing {

   protected final PascalCmdManager pcm;

   public CmdConnectDisconnect(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
      //by default not connected
      setEnableFalse();
   }

   public String getCmdString() {
      return sc.getResString("cmd.disconnect");
   }

   public String getCmdStringTip() {
      return sc.getResString("cmd.disconnect.tip");
   }

   public void executeWith(ICommandable t) {
      //first test if 
      PascalSwingCtx psc = pcm.getPSC();
      PCoreCtx pc = psc.getPCtx();
      //generate a GUI disconnect event
      BusEvent be = psc.getEventBusPascal().createEvent(PID_5_CONNECTIONS, EID_5_CONNECTIONS_3_DISCONNECTED_WILL_BE, this);
      be.setFlag(BusEvent.FLAG_3_USER_EVENT, true);
      psc.getEventBusPascal().putOnBus(be);
      
      RPCConnection rpc = pc.getRPCConnection();
      rpc.disconnect();
      
      //enables cmds
      pcm.getCmdConnectConnect().setEnabled(true);
      this.setEnabled(false);
      
      
      //generate a GUI disconnect event
      be = psc.getEventBusPascal().createEvent(PID_5_CONNECTIONS, EID_5_CONNECTIONS_2_DISCONNECTED, this);
      be.setFlag(BusEvent.FLAG_3_USER_EVENT, true);
      psc.getEventBusPascal().putOnBus(be);
   }

}
