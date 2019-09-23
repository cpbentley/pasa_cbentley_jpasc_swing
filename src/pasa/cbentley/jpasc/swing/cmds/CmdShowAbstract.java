/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.swing.cmd.CmdSwingAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;

public abstract class CmdShowAbstract<T> extends CmdSwingAbstract<T> implements ITechShow {
   protected final PascalCmdManager pcm;

   protected final int              type;

   public CmdShowAbstract(PascalCmdManager pcm, int type) {
      this.pcm = pcm;
      this.type = type;
   }

   public void executeWith(T t) {
      if (type == SHOW_TYPE_0_HOME) {
         executeWithTab(t);
      } else if (type == SHOW_TYPE_1_NEW_WIN) {
         executeWithWin(t);
      }
   }

   public String getCmdString() {
      SwingCtx sc = pcm.getPSC().getSwingCtx();
      String s1 = sc.getResString(getKeyBase());
      String s2 = sc.getResString(getKeyShowType());
      return s2 + " " + s1;
   }

   private String getKeyShowType() {
      if (type == SHOW_TYPE_1_NEW_WIN) {
         return pcm.getKeyInNewWindowCap();
      }
      return pcm.getKeyInTabCap();
   }

   public String getCmdStringTip() {
      SwingCtx sc = pcm.getPSC().getSwingCtx();
      String s1 = sc.getResString(getKeyBaseTip());
      return s1;
   }

   protected abstract String getKeyBase();

   protected abstract String getKeyBaseTip();

   protected abstract void executeWithTab(T t);

   protected abstract void executeWithWin(T t);
}
