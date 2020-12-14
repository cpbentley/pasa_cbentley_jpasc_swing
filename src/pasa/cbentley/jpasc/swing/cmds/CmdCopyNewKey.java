/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CmdCopyNewKey extends CmdSwingAbstract<ICommandableAccount> {

   protected final PascalCmdManager pcm;

   protected String                 keyBase = "cmd.key.copy.newkey";

   public CmdCopyNewKey(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
   }

   public String getCmdString() {
      return pcm.getStringFromKey(keyBase);
   }


   public String getCmdStringTip() {
      return pcm.getStringFromKey(keyBase + ".tip");

   }

   public void executeWith(Account ac) {
      if (ac != null) {
         PascalSwingCtx psc = pcm.getPSC();
         if (ac.getNewEncPubkey() != null) {
            psc.copyToClipboard(ac.getHashedSecret(), "New Key");
         } else {
            psc.getLog().consoleLogError("New Key is null or empty");
         }
      }
   }

   public void executeWith(ICommandableAccount t) {
      Account selectedAccount = t.getSelectedAccount();
      this.executeWith(selectedAccount);
   }

}
