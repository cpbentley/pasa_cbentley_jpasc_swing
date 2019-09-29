/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CmdCopyKeyBase58 extends CmdSwingAbstract<ICommandableKey> {

   protected final PascalCmdManager pcm;

   protected String                 keyBase = "cmd.key.copy.58";

   public CmdCopyKeyBase58(PascalCmdManager pcm) {
      super(pcm.getSwingCtx());
      this.pcm = pcm;
   }

   public String getCmdString() {
      return pcm.getStringFromKey(keyBase);
   }

   public void executeWith(ICommandableKey t) {
      t.cmdCopyKeyBase58(this);
   }

   public String getCmdStringTip() {
      return pcm.getStringFromKey(keyBase + ".tip");

   }

   public void executeWithEncoded(String encPubKey) {
      if(encPubKey != null) {
         PublicKey pk = pcm.getPSC().getPascalClient().decodePubKey(encPubKey, null);
         pcm.getPSC().copyToClipboard(pk.getBase58PubKey(), "Base58 Key");   
      }
   }
   public void executeWith(PublicKeyJava pk) {
      if (pk != null) {
         PascalSwingCtx psc = pcm.getPSC();
         if(pk.getBase58PubKey() != null) {
            psc.copyToClipboard(pk.getBase58PubKey(), "Base58 public key");
         } else {
            psc.getLog().consoleLogError("Base58 Key is null or empty");
         }
      }
   }

   public void executeWith(PublicKey publicKey) {
      if (publicKey != null) {
         PascalSwingCtx psc = pcm.getPSC();
         if(publicKey.getBase58PubKey() != null) {
            psc.copyToClipboard(publicKey.getBase58PubKey(), "Base58 public key");
         } else {
            psc.getLog().consoleLogError("Base58 Key is null or empty");
         }
      }
   }
}
