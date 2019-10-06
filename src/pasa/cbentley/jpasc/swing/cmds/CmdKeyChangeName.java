/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import javax.swing.JOptionPane;

import com.github.davidbolet.jpascalcoin.api.model.Account;

import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.tools.KeyNameProvider;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperChangeKeyName;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CmdKeyChangeName extends CmdSwingAbstract<ICommandableKey> {

   protected final PascalSwingCtx psc;

   protected String               keyBase    = "cmd.key.changename";

   protected String               keyBaseTip = "cmd.key.changename.tip";

   public CmdKeyChangeName(PascalSwingCtx psc) {
      super(psc.getSwingCtx());
      this.psc = psc;
   }

   public String getCmdString() {
      return psc.getCmds().getStringFromKey(keyBase);
   }

   public void executeWith(ICommandableKey t) {
      t.cmdChangeKeyName(this);
   }

   public String getCmdStringTip() {
      return psc.getCmds().getStringFromKey(keyBaseTip);

   }

   public void executeWith(PublicKeyJava pkJava) {
      String encodedPk = pkJava.getEncPubKey();
      executeWith(encodedPk);
   }

   public void executeWith(String encodedPk) {
      PanelHelperChangeKeyName panel = psc.getPanelHelperChangeKeyName();
      //set current
      PCoreCtx pc = psc.getPCtx();
      KeyNameProvider keyNameProvider = pc.getKeyNameProvider();
      String name = keyNameProvider.getPkNameStorePublic().getKeyNameAdd(encodedPk);
      panel.setKeyPublic(name);
      if (psc.isPrivateCtx()) {
         name = keyNameProvider.getPkNameStorePrivate().getKeyName(encodedPk);
         panel.setKeyPrivate(name);
         if(keyNameProvider.getPkNameStorePrivate().isPrivateWallet(encodedPk)) {
            panel.setKeyPrivateEnabled(false);
         } else {
            panel.setKeyPrivateEnabled(true);
         }
      } else {
         panel.setKeyPrivate(sc.getResString("text.publicmodehidekeyname"));
         panel.setKeyPrivateEnabled(false);
      }

      panel.guiUpdate();

      int result = JOptionPane.showConfirmDialog(psc.getFrameRoot(), panel, "Local Name Database: What's the new name?", JOptionPane.OK_CANCEL_OPTION);
      //name is null if canceled
      if (result == JOptionPane.OK_OPTION) {
         //only set private name if private context
         if (psc.isPrivateCtx()) {
            String namePriv = panel.getKeyPrivate();
            keyNameProvider.getPkNameStorePrivate().setPkName(encodedPk, namePriv);
         }
         String namePub = panel.getKeyPublic();
         keyNameProvider.getPkNameStorePublic().setPkName(encodedPk, namePub);
         keyNameProvider.cmdSave();
      }
   }

   public void executeWith(Account ac) {
      if (ac != null) {
         String encodedPk = ac.getEncPubkey();
         executeWith(encodedPk);
      }
   }
}
