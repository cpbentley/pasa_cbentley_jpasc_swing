/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.swing.cmd.CmdSwingAbstract;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CmdKeyChangeName extends CmdSwingAbstract<ICommandableKey> {

   protected final PascalCmdManager pcm;

   protected String                 keyBase    = "cmd.key.changename";

   protected String                 keyBaseTip = "cmd.key.changename.tip";

   public CmdKeyChangeName(PascalCmdManager pcm) {
      this.pcm = pcm;
   }

   public String getCmdString() {
      return pcm.getStringFromKey(keyBase);
   }

   public void executeWith(ICommandableKey t) {
      t.cmdChangeKeyName();
   }

   public String getCmdStringTip() {
      return pcm.getStringFromKey(keyBaseTip);

   }
}
