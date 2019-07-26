/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.pcore.interfaces.ISignVerifier;
import pasa.cbentley.jpasc.pcore.utils.PascalUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IMyLookUp;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyWalletNoWildcards;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.dekholm.riverlayout.RiverLayout;

public class MsgSignPanel extends AbstractMyTab implements IMyTab, IWorkerPanel, ActionListener, IMyLookUp, ICommandableRefresh {

   public static final String              ID               = "msg_sign";

   /**
    * 
    */
   private static final long               serialVersionUID = 3950884082507838830L;

   private JButton                         butCopyKeyBase58;

   private JButton                         butCopyKeyEnc;

   private JButton                         butCopyMsg;

   private JButton                         butCopySig;

   private JButton                         butPasteMsg;

   private BButton                         butSign;

   private JButton                         butVerifyBot;

   private JButton                         butVerifyTop;

   private PanelHelperKeyWalletNoWildcards dropDownPublicKey;

   private JTextArea                       jtextDigest;

   private JTextArea                       jtextSignature;

   private JLabel                          labelSignature;

   private JLabel                          labelText;

   private PascalSwingCtx                  psc;

   private IRootTabPane                    root;

   private ISignVerifier                   verifier;

   public MsgSignPanel(PascalSwingCtx psc, IRootTabPane root, ISignVerifier verifier) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      this.root = root;
      if (verifier == null) {
         throw new NullPointerException();
      }
      this.verifier = verifier;

   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butSign) {
         cmdSign();
      } else if (src == butCopySig) {
         cmdCopySignature();
      } else if (src == butCopyKeyEnc) {
         cmdCopyEncodedKey();
      } else if (src == butCopyKeyBase58) {
         cmdCopyBase58();
      } else if (src == butCopyMsg) {
         cmdCopyMessage();
      } else if (src == butPasteMsg) {
         cmdPasteMessage();
      } else if (src == butVerifyTop || src == butVerifyBot) {
         TabbedBentleyPanel parent = this.getTabPosition().getParent();
         if (parent != null) {
            parent.showChildByID(MsgVerifyPanel.ID);
         }
      }
   }

   private void cmdCopyBase58() {
      PublicKey pk = dropDownPublicKey.getSelectedKeyPublicKey();
      if (pk != null) {
         String base58 = pk.getBase58PubKey();
         psc.getSwingCtx().copyStringToClipboard(base58);
      }
   }

   private void cmdCopyEncodedKey() {
      PublicKey pk = dropDownPublicKey.getSelectedKeyPublicKey();
      if (pk != null) {
         String encPubKey = pk.getEncPubKey();
         psc.getSwingCtx().copyStringToClipboard(encPubKey);
      }
   }

   private void cmdCopyMessage() {
      String msg = jtextDigest.getText();
      psc.getSwingCtx().copyStringToClipboard(msg);
   }

   private void cmdCopySignature() {
      String signature = jtextSignature.getText();
      psc.getSwingCtx().copyStringToClipboard(signature);
   }

   private void cmdPasteMessage() {
      String text = psc.getSwingCtx().getClipboardString();
      if (text != null) {
         jtextDigest.setText(text);
      } else {
         psc.getLog().consoleLog("The clipboard does not contain a String");
      }
   }

   public void cmdRefresh(Object src) {
      // TODO Auto-generated method stub

   }

   private void cmdSign() {
      if (psc.getPCtx().getRPCConnection().isLocked()) {
         //TODO
         //nice pop asking for password to unlock
         String msg = "Wallet is locked. Wallet must be unlocked to sign messages";
         //
         boolean isUnlocked = psc.askToUnlock(msg, "", this);
         if (isUnlocked) {

         } else {
            psc.getLog().consoleLogError(msg);
            return;
         }
      }
      PublicKey pk = dropDownPublicKey.getSelectedKeyPublicKey();
      if (pk != null) {
         String encPubKey = pk.getEncPubKey();
         String b58PubKey = null;
         String digest = jtextDigest.getText();
         if (digest == null || digest.equals("")) {
            psc.getLog().consoleLogError("Cannot sign an empty message");
         } else {
            String hexString = PascalUtils.hexTo(digest);
            String sig = verifier.signMessage(hexString, encPubKey, b58PubKey);
            jtextSignature.setText(sig);
         }

      } else {
         psc.getLog().consoleLogError("Publick Key is null");
      }
   }

   public void disposeTab() {
   }

   public void initTab() {
      setLayout(new BorderLayout());

      SwingCtx sc = getSwingCtx();
      Icon iconVerify = sc.getResIcon(MsgVerifyPanel.ID, "tab", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
      Icon icoVerifySel = sc.getResIcon(MsgVerifyPanel.ID, "tab", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);

      butVerifyTop = new BButton(sc, this, iconVerify, icoVerifySel);
      butVerifyBot = new BButton(sc, this, iconVerify, icoVerifySel);

      Icon icoCopy = sc.getResIcon("copy", "action", IconFamily.ICON_SIZE_1_SMALL, IconFamily.ICON_MODE_0_DEFAULT);
      Icon icoCopySel = sc.getResIcon("copy", "action", IconFamily.ICON_SIZE_1_SMALL, IconFamily.ICON_MODE_1_SELECTED);
      Icon icoPaste = sc.getResIcon("paste", "action", IconFamily.ICON_SIZE_1_SMALL, IconFamily.ICON_MODE_0_DEFAULT);
      Icon icoPasteSel = sc.getResIcon("paste", "action", IconFamily.ICON_SIZE_1_SMALL, IconFamily.ICON_MODE_1_SELECTED);

      JPanel riverlayoutPanel = new JPanel();
      JScrollPane sp = new JScrollPane(riverlayoutPanel);
      RiverLayout rl = new RiverLayout();
      riverlayoutPanel.setLayout(rl);

      dropDownPublicKey = new PanelHelperKeyWalletNoWildcards(psc, this, this);
      dropDownPublicKey.setLabelTextKey("lab.signingkey");
      
      riverlayoutPanel.add("br", dropDownPublicKey);

      butCopyKeyEnc = new BButton(sc, this, "but.copyenckey", icoCopy, icoCopySel);
      riverlayoutPanel.add("tab", butCopyKeyEnc);

      butCopyKeyBase58 = new BButton(sc, this, "but.copybase58key", icoCopy, icoCopySel);
      riverlayoutPanel.add("tab", butCopyKeyBase58);

      jtextDigest = new JTextArea(6, 100);
      jtextDigest.setLineWrap(true);
      JScrollPane spText = new JScrollPane(jtextDigest);

      labelText = new JLabel("Message:");
      riverlayoutPanel.add("br", labelText);
      riverlayoutPanel.add("tab", spText);

      Icon iconSign = sc.getResIcon(MsgSignPanel.ID, "tab", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
      Icon icoSignSel = sc.getResIcon(MsgSignPanel.ID, "tab", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);

      butSign = new BButton(sc, this, "but.sign", iconSign, icoSignSel);
      butSign.setCursorImage("/cursors/cursor_red_8.png");

      riverlayoutPanel.add("br", butVerifyBot);
      riverlayoutPanel.add("tab", butSign);

      butCopyMsg = new BButton(sc, this, "but.copymsgclip", icoCopy, icoCopySel);
      riverlayoutPanel.add("tab", butCopyMsg);

      butPasteMsg = new BButton(sc, this, "but.pastemsgclip", icoPaste, icoPasteSel);
      riverlayoutPanel.add("tab", butPasteMsg);

      labelSignature = new JLabel("Signature:");
      jtextSignature = new JTextArea(2, 100);
      jtextSignature.setLineWrap(true);
      jtextSignature.setEditable(false);
      riverlayoutPanel.add("br", labelSignature);
      riverlayoutPanel.add("tab", jtextSignature);

      butCopySig = new BButton(sc, this, "but.copysign", icoCopy, icoCopySel);
      riverlayoutPanel.add("br", butVerifyTop);
      riverlayoutPanel.add("tab", butCopySig);

      JPanel north = new JPanel();

      this.add(north, BorderLayout.NORTH);
      this.add(sp, BorderLayout.CENTER);

      JPanel south = new JPanel();
      south.setPreferredSize(new Dimension(50, 50));
      south.setBackground(Color.BLACK);
      this.add(south, BorderLayout.SOUTH);

      JPanel east = new JPanel();
      east.setPreferredSize(new Dimension(50, 50));
      east.setBackground(Color.orange);
      this.add(east, BorderLayout.EAST);

      JPanel west = new JPanel();
      west.setPreferredSize(new Dimension(50, 50));
      west.setBackground(Color.orange);
      this.add(west, BorderLayout.WEST);
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker tableBlockSwingWorker) {
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {

   }

   public void tabGainFocus() {

   }

   public void tabLostFocus() {
      psc.playTabFocusLost(this);
   }

   public void updateTable() {
   }

}