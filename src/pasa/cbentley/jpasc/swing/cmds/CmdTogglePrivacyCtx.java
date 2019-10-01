package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CmdTogglePrivacyCtx extends CmdSwingAbstract<ICommandable> implements IEventsPascalSwing {

   protected final PascalSwingCtx   psc;

   protected final PascalCmdManager pcm;

   public CmdTogglePrivacyCtx(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
      this.psc = pcm.getPSC();
   }

   private String getKey() {
      String key = null;
      if (psc.isPrivateCtx()) {
         key = "action.toggle.private";
      } else {
         key = "action.toggle.public";
      }
      return key;
   }

   public String getCmdString() {
      String key = getKey();
      return psc.getSwingCtx().getResString(key);
   }

   public String getCmdStringTip() {
      String key = psc.getSwingCtx().buildStringUISerial(getKey(), ".tip");
      return psc.getSwingCtx().getResString(key);
   }

   public void executeWith(ICommandable t) {
      boolean currentCtx = psc.isPrivateCtx();
      boolean newValue = !currentCtx;
      psc.setPrivateCtx(newValue);
      
      //save it in the settings for when the app starts again
      psc.getPascPrefs().putBoolean(IPrefsPascalSwing.PREFS_PRIVATE_CTX, newValue);
      
      BusEvent event = psc.getEventBusPascal().createEvent(PID_7_PRIVACY_CHANGES, EID_7_PRIVACY_1_CTX, this);
      psc.getEventBusPascal().putOnBus(event);
      psc.getSwingCtx().guiUpdateLater();

      
      //TODO what about key names? we don't want to reveal them
      //custom keys have a private name and a public name
      //by default keys from wallet are private.. and are overriden in public context with a public name
      if (psc.isPrivateCtx()) {
         psc.getSwingCtx().getLog().consoleLog("Showing private data. Screenshots will reveal private information");
      } else {
         psc.getSwingCtx().getLog().consoleLog("Showing public data from daemon. Screenshots will not reveal private information");
      }
   }

}
