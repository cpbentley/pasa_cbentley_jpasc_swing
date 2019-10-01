package pasa.cbentley.jpasc.swing.cmds;

import javax.swing.JComponent;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;

public class CmdConnectTestNetwork extends CmdSwingAbstract<ICommandable> {

   protected final PascalCmdManager pcm;

   public CmdConnectTestNetwork(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
   }

   public String getCmdString() {
      return sc.getResString("cmd.connecttestnet");
   }

   public String getCmdStringTip() {
      return sc.getResString("cmd.connecttestnet.tip");
   }

   public void executeWith(ICommandable t) {
      //first test if 
      PascalSwingCtx psc = pcm.getPSC();
      PCoreCtx pc = psc.getPCtx();
      RPCConnection rpc = new RPCConnection(pc);
      boolean b = rpc.connectLocalhostTestNet();
      if (b) {
         //get current conneciton
         CmdConnectDisconnect cmd = psc.getCmds().getCmdConnectDisconnect();
         cmd.executeWith(null);
         pc.setRPCConnection(rpc);
         BusEvent be = psc.getEventBusPascal().createEvent(IEventsPascalSwing.PID_5_CONNECTIONS, IEventsPascalSwing.EID_5_CONNECTIONS_1_CONNECTED, null);
         //be.setUserEvent(); we don't know here
         psc.getEventBusPascal().putOnBus(be);
         psc.getLog().consoleLogDateGreen("Connected to the test net!");
      } else {
         JComponent comp = null;
         if(t instanceof JComponent) {
            comp = (JComponent)t;
         }
         String msg = "could not connect to local test network. check if its running";
         psc.getLog().consoleLogDateRed(msg);
         psc.showMessageErrorForUI(comp, msg);
      }
   }

}
