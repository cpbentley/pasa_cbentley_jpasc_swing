/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.pcore.actions.ActionSendCoins;
import pasa.cbentley.jpasc.pcore.domain.java.PayloadJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.image.SphereColors;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.layout.RiverPanel;
import pasa.cbentley.swing.style.Style;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BTextField;

/**
 * 
 * @author Charles Bentley
 *
 */
public class WalletSendCoinsLikeBank extends AbstractMyTab implements ActionListener, DocumentListener {

   public static final String             ID                      = "wallet_send_coin";

   public static final String             COMBO_CHOICE_SEND_COINS = "COMBO_CHOICE_SEND_COINS";

   private RiverPanel                     amountPanel;

   private Animator                       animator;

   private Animator                       animatorMessage;

   private BButton                        butAddressBook;

   private BButton                        butAmountHalf;

   private BButton                        butAmountMax;

   private BButton                        butAmountMin;

   private BButton                        butFeeMin;

   private BButton                        butFeeZero;

   private BButton                        butSend;

   private BCheckBox                      cbWithMessage;

   private JPanel                         eastPanel;

   private JPanel                         encryptChoicesPanel;

   private PanelSelectAccount             fromPanel;

   private ButtonGroup                    groupButton;

   private BLabel                         labAmount;

   private BLabel                         labFee;

   private BLabel                         labMax;

   private JLabel                         labPayloadPassword;

   private BLabel                         labTitleSend;

   private JLabel                         labTotalWalletCoins;


   private PanelPayload                   messagePanel;

   private JPanel                         payloadPanel;

   private PascalSwingCtx                 psc;

   private JRadioButton                   rbPayloadEncryptDestPublic;

   private JRadioButton                   rbPayloadEncryptPassword;

   private JRadioButton                   rbPayloadEncryptSenderPublic;

   private JRadioButton                   rbPayloadPublic;

   private IRootTabPane                   root;

   private BTextField                     textAmount;

   private JTextField                     textCoinsAvailable;

   private JTextField                     textFee;

   private JTextArea                      textPayload;

   private JTextField                     textPayloadPassword;

   private PanelSelectAccount             toPanel;

   private JPanel                         westPanel;

   private SphereComponent                westSphere;

   private SphereColors                   westSphereColors;

   private SphereColors                   eastSphereColors;

   private SphereComponent                eastSphere;

   public WalletSendCoinsLikeBank(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      this.root = root;
      //north show how much coins you have.. Global Send
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butSend) {
         cmdSend();
      } else if (src == cbWithMessage) {
         cmdWithMessageToggle();
      } else if (src == butFeeMin) {
         cmdFeeMin();
      } else if (src == butFeeZero) {
         cmdFeeZero();
      } else if (src == butAmountMin) {
         cmdAmountMin();
      } else if (src == butAmountHalf) {
         cmdAmountHalf();
      } else if (src == butAmountMax) {
         cmdAmountMax();
      }
   }

   private void cmdAmountMax() {
      // TODO Auto-generated method stub

   }

   private void cmdAmountHalf() {
      // TODO Auto-generated method stub

   }

   private void cmdAmountMin() {
      // TODO Auto-generated method stub

   }

   private void cmdFeeZero() {
      // TODO Auto-generated method stub

   }

   private void cmdFeeMin() {
      // TODO Auto-generated method stub

   }

   public void changedUpdate(DocumentEvent e) {
      // TODO Auto-generated method stub

   }

   /**
    * 
    */
   private void cmdSend() {
      PayloadJava payloadJava = null;
      if (messagePanel != null) {
         payloadJava = messagePanel.getPayloadJava();
      }

      //create a send operations
      ActionSendCoins sendCoinsAction = new ActionSendCoins(psc.getPCtx());

      sendCoinsAction.setPayload(payloadJava);
      sendCoinsAction.setFee(psc.getFee(textFee));
      sendCoinsAction.setAmount(psc.getAmount(textAmount));

      sendCoinsAction.setToAccountInteger(toPanel.getAccountInteger());
      sendCoinsAction.setToAccountName(toPanel.getAccountName());

      sendCoinsAction.setFromAccountInteger(fromPanel.getAccountInteger());
      sendCoinsAction.setFromAccountName(fromPanel.getAccountName());

      boolean isValid = sendCoinsAction.isValid();
      if (isValid) {
         sendCoinsAction.cmdSend();
      } else {

      }
   }

   /**
    * 
    */
   private void cmdWithMessageToggle() {
      if (cbWithMessage.isSelected()) {
         PanelPayload panel = getMessagePanel();
         if (animatorMessage != null) {
            animatorMessage.stop();
         }
         int currentHeight = panel.getHeight();
         //make the panel for message appear
         animatorMessage = PropertySetter.createAnimator(1000, panel, "heightAnim", currentHeight, 200);
         animatorMessage.start();
      } else {
         PanelPayload panel = getMessagePanel();
         //make the panel for message disappear
         if (animatorMessage != null) {
            animatorMessage.stop();
         }
         int currentHeight = panel.getHeight();
         //make the panel for message appear
         animatorMessage = PropertySetter.createAnimator(1000, panel, "heightAnim", currentHeight, 0);
         animatorMessage.start();
      }
   }

   public void disposeTab() {
   }

   public JPanel getAmountPanel() {
      if (amountPanel == null) {
         //river layout
         amountPanel = new RiverPanel(sc);

         labAmount = new BLabel(sc, "text.amount");
         textAmount = new BTextField(sc);
         textAmount.setColumns(15);

         butAmountMin = new BButton(sc, this, "but.min");
         butAmountHalf = new BButton(sc, this, "but.half");
         butAmountMax = new BButton(sc, this, "but.max");
         labMax = new BLabel(sc);

         amountPanel.add(labAmount);
         amountPanel.add(textAmount);
         amountPanel.add(butAmountMin);
         amountPanel.add(butAmountHalf);
         amountPanel.add(butAmountMax);
         amountPanel.add(labMax);

         labFee = new BLabel(sc, "text.fee");
         textFee = new BTextField(sc);
         textFee.setColumns(15);

         butFeeZero = new BButton(sc, this, "but.zero");
         butFeeMin = new BButton(sc, this, "but.min");

         cbWithMessage = new BCheckBox(sc, this, "checkbox.withmessage");

         amountPanel.raddBr(labFee);
         amountPanel.raddTab(textFee);
         amountPanel.raddTab(butFeeZero);
         amountPanel.raddTab(butFeeMin);
         amountPanel.raddTab(cbWithMessage);
      }
      return amountPanel;
   }

   private JPanel getFromPanel() {
      if (fromPanel == null) {
         fromPanel = new PanelSelectAccount(psc, "text.from");
         Style s = new Style(sc);
         //
         Color bgColor = new Color(15, 220, 50, 96);
         s.setBgColor(bgColor);
         s.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, bgColor.brighter(), bgColor.darker()));
         fromPanel.setStylePanel(s);

         fromPanel.init();
      }
      return fromPanel;

   }

   private PanelPayload getMessagePanel() {
      if (messagePanel == null) {
         messagePanel = new PanelPayload(psc);
      }
      return messagePanel;
   }

   public JPanel getPayloadPanel() {
      if (payloadPanel == null) {
         payloadPanel = new JPanel();

         payloadPanel.setLayout(new BorderLayout());

         textPayload = new JTextArea();
         payloadPanel.add(textPayload, BorderLayout.CENTER);

         textPayloadPassword = new JTextField(15);
         textPayloadPassword.setEnabled(false);

         encryptChoicesPanel = new JPanel();

         rbPayloadEncryptPassword = new JRadioButton("Encrypt with password");
         rbPayloadEncryptPassword.setToolTipText("");
         rbPayloadEncryptPassword.addActionListener(this);

         rbPayloadEncryptDestPublic = new JRadioButton("Encrypt with destination public key");
         rbPayloadEncryptDestPublic.setToolTipText("");

         rbPayloadEncryptSenderPublic = new JRadioButton("Encrypt with sender public key");
         rbPayloadEncryptSenderPublic.setToolTipText("");

         rbPayloadPublic = new JRadioButton("Do not encrypt.", true);
         rbPayloadPublic.setToolTipText("");

         groupButton = new ButtonGroup();
         groupButton.add(rbPayloadPublic);
         groupButton.add(rbPayloadEncryptDestPublic);
         groupButton.add(rbPayloadEncryptPassword);
         groupButton.add(rbPayloadEncryptSenderPublic);

      }
      return payloadPanel;
   }

   private JPanel getToPanel() {

      if (toPanel == null) {
         toPanel = new PanelSelectAccount(psc, "text.to");
         Style s = new Style(sc);
         Color bgColor = new Color(220, 50, 50, 96);
         s.setBgColor(bgColor);
         s.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, bgColor.brighter(), bgColor.darker()));
         toPanel.setStylePanel(s);

         toPanel.init();
      }
      return toPanel;
   }

   public void guiUpdate() {

      //#debug
      toDLog().pFlow("", this, WalletSendCoinsLikeBank.class, "guiUpdate", ITechLvl.LVL_05_FINE, true);
      super.guiUpdate();

   }

   private Component initCenter() {
      Box vertbox = Box.createVerticalBox();

      Box box = Box.createHorizontalBox();

      box.add(getFromPanel());
      box.add(getToPanel());

      vertbox.add(box);

      vertbox.add(getAmountPanel());
      vertbox.add(getMessagePanel());

      //

      //JScrollPane js = new JScrollPane(vertbox);
      return vertbox;

   }

   public void initNorth() {
      JPanel pane = new JPanel();

      butAddressBook = new BButton(sc, this, "Address Book");

      pane.add(labTotalWalletCoins);
      pane.add(textCoinsAvailable);

      pane.add(butSend);
      pane.add(butAddressBook);
   }

   public void initTab() {
      this.setLayout(new BorderLayout());

      JPanel north = new JPanel();
      //TODO title style
      labTitleSend = new BLabel(sc, "text.newcointransfer");
      north.add(labTitleSend);
      //global style for title.. related to the current skin
      Style style = new Style(sc);
      style.setFontRelSize(3);
      style.setFontStyle(Font.BOLD);
      labTitleSend.setStyle(style);

      this.add(north, BorderLayout.NORTH);

      JPanel south = new JPanel();

      butSend = new BButton(sc, this, "but.send");
      butSend.setIcon(WalletSendCoinsLikeBank.ID, "tab", IconFamily.ICON_SIZE_2_MEDIUM);
      south.add(butSend);

      this.add(south, BorderLayout.SOUTH);

      westPanel = new JPanel();

      westSphereColors = new SphereColors(sc);
      westSphere = new SphereComponent(sc, westSphereColors);

      westPanel.setSize(50, 20);
      westPanel.add(westSphere);

      westPanel.setBackground(Color.BLACK);
      this.add(westPanel, BorderLayout.WEST);

      eastPanel = new JPanel();

      eastSphereColors = new SphereColors(sc);
      eastSphere = new SphereComponent(sc, eastSphereColors);
      eastPanel.setSize(50, 0);
      eastPanel.add(eastSphere);
      eastPanel.setBackground(Color.darkGray);
      this.add(eastPanel, BorderLayout.EAST);

      Component center = initCenter();
      this.add(center, BorderLayout.CENTER);

   }

   public void insertUpdate(DocumentEvent e) {
      // TODO Auto-generated method stub

   }

   public void removeUpdate(DocumentEvent e) {
      // TODO Auto-generated method stub

   }

   public void tabGainFocus() {
      if (animator == null) {
         JPanel panelFrom = getFromPanel();
         //pointer to the value to animate
         animator = PropertySetter.createAnimator(1000, panelFrom, "background", new Color(15, 220, 50, 0), new Color(15, 220, 50));
         animator.setRepeatCount(4);
      }
      if (animator != null && !animator.isRunning()) {
         //animator.start();
      }

   }

   public void tabLostFocus() {
      if (animator != null) {
         animator.stop();
      }
   }

}