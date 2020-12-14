/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CmdCopyKeyEncoded extends CmdSwingAbstract<ICommandableKey> {

   protected final PascalCmdManager pcm;

   protected String                 keyBase = "cmd.key.copy.encoded";

   public CmdCopyKeyEncoded(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
   }

   public String getCmdString() {
      return pcm.getStringFromKey(keyBase);
   }

   public void executeWith(ICommandableKey t) {
      t.cmdCopyKeyEncoded(this);
   }

   public String getCmdStringTip() {
      return pcm.getStringFromKey(keyBase + ".tip");

   }

   public void executeWithEncoded(String encPubKey) {
      if (encPubKey != null) {
         PascalSwingCtx psc = pcm.getPSC();
         psc.copyToClipboard(encPubKey, "Encoded Public Key");
      }
   }

   public void executeWith(PublicKeyJava pk) {
      if (pk != null) {
         PascalSwingCtx psc = pcm.getPSC();
         if (pk.getEncPubKey() != null) {
            psc.copyToClipboard(pk.getEncPubKey(), "Encoded public key");
         } else {
            psc.getLog().consoleLogError("Encoded key is null or empty");
         }
      }
   }

   public void executeWith(PublicKey publicKey) {
      if (publicKey != null) {
         PascalSwingCtx psc = pcm.getPSC();
         if (publicKey.getEncPubKey() != null) {
            psc.copyToClipboard(publicKey.getEncPubKey(), "Encoded public key");
         } else {
            psc.getLog().consoleLogError("Encoded key is null or empty");
         }
      }
   }
}
