package pasa.cbentley.jpasc.swing.task;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.ICallBack;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.audio.SoundPlay;
import pasa.cbentley.jpasc.swing.cmds.PascalCmdManager;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Responsible to call the exits of each modules
 * @author Charles Bentley
 *
 */
public class ExitTaskPascal implements IExitable, ICallBack {

   protected final PascalSwingCtx psc;

   public ExitTaskPascal(PascalSwingCtx psc) {
      this.psc = psc;
   }

   public void prepareExit() {
      int activeCount = Thread.activeCount();
   }

   public void cmdExit() {
      //#debug
      toDLog().pFlow("", null, ExitTaskPascal.class, "cmdExit", LVL_05_FINE, true);

      SwingCtx sc = psc.getSwingCtx();
      PCoreCtx pc = psc.getPCtx();
      //
      pc.cmdExit();
      sc.cmdExit();
      psc.cmdExit();
      
      System.exit(0);
   }

   public void callBack(Object o) {
      //which call back ?
      if (o instanceof SoundPlay) {
         System.exit(0);
      } else {

         //#debug
         toDLog().pTest("Unknown Class " + o, this, PascalCmdManager.class, "callBack", LVL_05_FINE, true);
         System.exit(0);
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "ExitTask");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ExitTask");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }

   //#enddebug

}
