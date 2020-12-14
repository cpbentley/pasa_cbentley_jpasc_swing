/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import pasa.cbentley.jpasc.pcore.interfaces.ISignVerifier;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.pcore.utils.PascalUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IMyLookUp;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.dekholm.riverlayout.RiverLayout;

public class MsgVerifyPanel extends AbstractMyTab implements IMyTab, IWorkerPanel, ActionListener, IMyLookUp {
   public static final String ID               = "msg_verify";

   /**
    * 
    */
   private static final long  serialVersionUID = 3950884082507838830L;

   private JButton            butDecodeKey;

   private JButton            butEncodeKey;

   private JButton            butPasteEncodedKey;

   private JButton            butPasteKeyBase58;

   private JButton            butPasteMessage;

   private JButton            butPasteSig;

   private BButton            butSign;

   private BButton            butSignBottom;

   private BButton            butVerify;

   private JPanel             east;

   private JTextArea          textDigest;

   private JTextArea          textSignature;

   private JLabel             labDecodeEncode;

   private JLabel             labelExplain;

   private JLabel             labelKey58;

   private JLabel             labelKeyEnc;

   private JLabel             labelSignature;

   private JLabel             labelText;

   private JLabel             labVerifyMsg;

   private IRootTabPane       root;

   private JTextArea          textKey58;

   private JTextArea          textKeyEnc;

   private JPanel             west;

   private ISignVerifier      verifier;

   private PascalSwingCtx     psc;

   private JPanel             decodeEncodePanel;

   private JButton            butClearKeyData;

   public MsgVerifyPanel(PascalSwingCtx psc, IRootTabPane root, ISignVerifier verifier) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      this.root = root;
      this.verifier = verifier;
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butVerify) {
         cmdVerify();
      } else if (e.getSource() == butPasteSig) {
         cmdPasteSignature();
      } else if (e.getSource() == butPasteMessage) {
         cmdPasteMessage();
      } else if (e.getSource() == butPasteEncodedKey) {
         cmdPasteEncodedKey();
      } else if (e.getSource() == butPasteKeyBase58) {
         cmdPasteKey58();
      } else if (e.getSource() == butEncodeKey) {
         cmdEncodeKey();
      } else if (e.getSource() == butDecodeKey) {
         cmdDecodeKey();
      } else if (e.getSource() == butSign || e.getSource() == butSignBottom) {
         cmdSign();
      } else if (e.getSource() == butClearKeyData) {
         cmdClearData();
      }
   }

   private void cmdPasteSignature() {
      String sig = psc.getSwingCtx().getClipboardString();
      textSignature.setText(sig);
   }

   private void cmdPasteMessage() {
      String sig = psc.getSwingCtx().getClipboardString();
      textDigest.setText(sig);
   }

   private void cmdPasteEncodedKey() {
      String sig = psc.getSwingCtx().getClipboardString();
      textKeyEnc.setText(sig);
   }

   private void cmdPasteKey58() {
      String sig = psc.getSwingCtx().getClipboardString();
      textKey58.setText(sig);
   }

   private void cmdClearData() {
      textKey58.setText("");
      textKeyEnc.setText("");
   }

   private void cmdSign() {
      TabbedBentleyPanel parent = this.getTabPosition().getParent();
      if (parent != null) {
         parent.showChildByID(MsgSignPanel.ID);
      }
   }

   private void cmdDecodeKey() {
      String encPubKey = textKeyEnc.getText();
      PublicKey pk = psc.getPascalClient().decodePubKey(encPubKey, null);
      if (pk == null) {
         psc.getLog().consoleLogError("Wrong encoded key");
      } else {
         textKey58.setText(pk.getBase58PubKey());
      }
   }

   private void cmdEncodeKey() {
      String base58 = textKey58.getText();
      PublicKey pk = psc.getPascalClient().decodePubKey(null, base58);
      if (pk == null) {
         psc.getLog().consoleLogError("Wrong base58 key");
      } else {
         textKeyEnc.setText(pk.getEncPubKey());
      }
   }

   private void cmdVerify() {
      String signature = textSignature.getText();
      String keybase58 = textKey58.getText();
      String keyEnc = textKeyEnc.getText();
      if (keyEnc == null || keyEnc.equals("")) {
         if (keybase58 == null || keybase58.equals("")) {
            return;
         }
         //might generate an exception
         PublicKey pk = psc.getPascalClient().decodePubKey(null, keybase58);
         keyEnc = pk.getEncPubKey();
      } else {
         //validate encoded key
      }
      String digest = textDigest.getText();
      String hexString = PascalUtils.hexTo(digest);
      boolean match = verifier.verifySign(hexString, keyEnc, signature);
      if (match) {
         labVerifyMsg.setText("Message matches!");
         west.setBackground(Color.GREEN);
         east.setBackground(Color.GREEN);
      } else {
         labVerifyMsg.setText("BAD! No matches!");
         west.setBackground(Color.RED);
         east.setBackground(Color.RED);
      }
   }

   public void disposeTab() {
   }

   public void initTab() {

      setLayout(new BorderLayout());

      Icon icoVerify = this.getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM);
      Icon icoVerifySel = this.getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);

      Icon iconSign = sc.getResIcon(MsgSignPanel.ID, "tab", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
      Icon icoSignSel = sc.getResIcon(MsgSignPanel.ID, "tab", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);

      butSign = new BButton(sc, this, iconSign, icoSignSel);
      butSignBottom = new BButton(sc, this, iconSign, icoSignSel);

      butSign.addActionListener(this);
      butSignBottom.addActionListener(this);

      Icon icoPaste = sc.getResIcon("paste", "action", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
      Icon icoPasteSel = sc.getResIcon("paste", "action", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);

      JPanel riverlayoutPanel = new JPanel();
      JScrollPane sp = new JScrollPane(riverlayoutPanel);
      RiverLayout rl = new RiverLayout();
      riverlayoutPanel.setLayout(rl);

      labelExplain = new JLabel("Paste one of the key format. Encoding/Decoding for extra checks");
      riverlayoutPanel.add("", labelExplain);

      labelKeyEnc = new JLabel("Encoded Key:");
      textKeyEnc = new JTextArea(4, 100); //dynamic size base on size of container
      textKeyEnc.setLineWrap(true);
      butPasteEncodedKey = new BButton(sc, icoPaste, icoPasteSel, "Paste Encoded Key");
      butPasteEncodedKey.addActionListener(this);

      riverlayoutPanel.add("br", labelKeyEnc);
      riverlayoutPanel.add("tab", textKeyEnc);
      riverlayoutPanel.add("tab", butPasteEncodedKey);

      ///middle

      labDecodeEncode = new JLabel("↠");
      riverlayoutPanel.add("br", labDecodeEncode);

      butClearKeyData = new BButton(sc, this, "but.clearkeys");

      decodeEncodePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

      butDecodeKey = new JButton("⇊Decode Key⇊");
      butDecodeKey.addActionListener(this);
      butEncodeKey = new JButton("⇈Encode Key⇈");
      butEncodeKey.addActionListener(this);
      decodeEncodePanel.add(butEncodeKey);
      decodeEncodePanel.add(butDecodeKey);
      decodeEncodePanel.add(butClearKeyData);

      riverlayoutPanel.add("tab", decodeEncodePanel);

      /////////////////////////////

      labelKey58 = new JLabel("Base58 Key:");
      textKey58 = new JTextArea(4, 100);
      textKey58.setLineWrap(true);
      butPasteKeyBase58 = new BButton(sc, icoPaste, icoPasteSel, "Paste Base58 Key");
      butPasteKeyBase58.addActionListener(this);
      riverlayoutPanel.add("br", labelKey58);
      riverlayoutPanel.add("tab", textKey58);
      riverlayoutPanel.add("tab", butPasteKeyBase58);

      labelSignature = new JLabel("Signature:");
      textSignature = new JTextArea(4, 100);
      textSignature.setLineWrap(true);
      riverlayoutPanel.add("br", labelSignature);
      riverlayoutPanel.add("tab", textSignature);
      butPasteSig = new BButton(sc, icoPaste, icoPasteSel, "Paste Signature");
      butPasteSig.addActionListener(this);
      riverlayoutPanel.add("tab", butPasteSig);

      labelText = new JLabel("Message:");
      textDigest = new JTextArea(6, 100);
      textDigest.setLineWrap(true);
      riverlayoutPanel.add("br", labelText);
      riverlayoutPanel.add("tab", textDigest);
      butPasteMessage = new BButton(sc, icoPaste, icoPasteSel, "Paste Message");
      butPasteMessage.addActionListener(this);
      riverlayoutPanel.add("tab", butPasteMessage);

      butVerify = new BButton(sc, icoVerify, icoVerifySel, "Verify");
      butVerify.addActionListener(this);
      labVerifyMsg = new JLabel();
      riverlayoutPanel.add("br", butSignBottom);
      riverlayoutPanel.add("tab", butVerify);
      riverlayoutPanel.add("tab", labVerifyMsg);

      JPanel north = new JPanel();

      this.add(north, BorderLayout.NORTH);
      this.add(sp, BorderLayout.CENTER);

      JPanel south = new JPanel();
      south.setPreferredSize(new Dimension(50, 50));
      south.setBackground(Color.BLACK);
      this.add(south, BorderLayout.SOUTH);

      east = new JPanel();
      east.setPreferredSize(new Dimension(50, 50));
      east.setBackground(Color.RED);
      this.add(east, BorderLayout.EAST);

      west = new JPanel();
      west.setPreferredSize(new Dimension(50, 50));
      west.setBackground(Color.RED);
      this.add(west, BorderLayout.WEST);
   }

   public void tabGainFocus() {
   }

   public void tabLostFocus() {
   }

   public void updateTable() {
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker tableBlockSwingWorker) {
   }
 public void panelSwingWorkerStarted(PanelSwingWorker worker) {
      
   }
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

}