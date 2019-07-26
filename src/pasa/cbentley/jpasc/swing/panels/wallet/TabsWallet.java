/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import pasa.cbentley.jpasc.pcore.interfaces.ISignVerifier;
import pasa.cbentley.jpasc.pcore.utils.RPCWalletSigVerifier;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

public class TabsWallet extends TabbedBentleyPanel {

   /**
    * 
    */
   private static final long       serialVersionUID = 5039918564796952398L;

   private IRootTabPane            root;

   private MsgSignPanel            signPanel;

   private MsgVerifyPanel          verifyPanel;

   private WalletPaymentCoinPanel  walletPaymentCoins;

   private WalletPaymentPasaPanel  walletPaymentPASA;

   private WalletSendCoinsLikeBank walletSendCoins;

   private PascalSwingCtx          psc;

   public TabsWallet(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), "root_wallet");
      this.psc = psc;
      this.root = root;
   }

   public void disposeTab() {
   }

   /**
    * 
    */
   public void initTabs() {
      ISignVerifier verifier = new RPCWalletSigVerifier(psc.getPCtx());
      signPanel = new MsgSignPanel(psc, root, verifier);
      verifyPanel = new MsgVerifyPanel(psc, root, verifier);
      walletPaymentPASA = new WalletPaymentPasaPanel(psc, root);
      walletPaymentCoins = new WalletPaymentCoinPanel(psc, root);
      walletSendCoins = new WalletSendCoinsLikeBank(psc, root);

      addMyTab(walletSendCoins);
      addMyTab(walletPaymentCoins);
      addMyTab(walletPaymentPASA);

      addMyTab(signPanel);
      addMyTab(verifyPanel);

   }

   public void tabGainFocus() {
      super.tabGainFocus();
      psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_INSTRUMENTS);
   }
}