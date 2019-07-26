/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.account;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.pcore.utils.AddressValidationResult;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.ITechPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByAccount;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.dekholm.riverlayout.RiverLayout;

public class PanelAccountDetails extends PanelTabAbstractPascal implements DocumentListener, IMyTab, IMyGui, ActionListener {

   public static final String ID = "account_details";

   /**
    * 
    */
   private static final long        serialVersionUID = -1733914857369658375L;

   private Account                  account;

   private Integer                  accountNumber;

   private JButton                  butClear;

   private JButton                  butFindAccount;

   private JCheckBox                cbIsPrivate;

   private JLabel                   labAccount;

   private JLabel                   labBalance;

   private JLabel                   labBlocks;

   private JLabel                   labChecksum;

   private JLabel                   labEncPubKey;

   private JLabel                   labLastBlock;

   private JLabel                   labLastOpTime;

   private JLabel                   labLockBlock;

   private JLabel                   labMolina;

   private JLabel                   labName;

   private JLabel                   labNumOperation;

   private JLabel                   labPrice;

   private JLabel                   labPublicKey;

   private JLabel                   labSeller;

   private JLabel                   labType;

   private JPanel                   operationsAccountPanel;

   private JTextField               textAccount;

   private JTextArea                textAreaEncPubKey;

   private JTextArea                textAreaPubKey;

   private JTextField               textBalance;

   private JTextField               textCheckSum;

   private JTextField               textLastBlock;

   private JTextField               textLastOpTime;

   private JTextField               textLockBlock;

   private JTextField               textMolina;

   private JTextField               textName;

   private JTextField               textNumOperations;

   private JTextComponent           textPrice;

   private JTextField               textSeller;

   private JTextField               textType;


   private IRootTabPane             root;

   private TablePanelOperationByAccount accountOperations;

   private JButton                  butNext;

   private JButton                  butPrev;

   private BButton butTopLeft;

   private JTextField textNameFind;

   private JButton butCopyAccountCk;

   public PanelAccountDetails(PascalSwingCtx psc, IRootTabPane root) {
      this(psc, root, ID);
   }

   public PanelAccountDetails(PascalSwingCtx psc, IRootTabPane root, String id) {
      super(psc, id);
      this.root = root;
      this.setLayout(new BorderLayout());

   }

   /**
    * 
    */
   public void changedUpdate(DocumentEvent e) {
      //System.out.println("changedUpdate");
      updateAccount();
   }

   public void disposeTab() {

   }

   public void guiUpdate() {

   }

   public void initTab() {

      JPanel container = new JPanel();

      //table.setPreferredScrollableViewportSize(new Dimension(500, 70));

      accountOperations = new TablePanelOperationByAccount(psc, root, true);
      accountOperations.initCheck();

      RiverLayout rl = new RiverLayout();
      container.setLayout(rl);

      butClear = new JButton("Clear");
      butClear.setToolTipText("Clear all data");
      butClear.addActionListener(this);

      butNext = new JButton("Next >>");
      butPrev = new JButton("<< Previous");

      butNext.addActionListener(this);
      butPrev.addActionListener(this);

      butFindAccount = new JButton("Find Account");
      butFindAccount.setToolTipText("Show account along others");
      butFindAccount.addActionListener(this);
      
      Icon icon = getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
      Icon iconSel = getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);
      butTopLeft = new BButton(sc, icon, iconSel);
      container.add("", butTopLeft);

      JPanel panelButtons = new JPanel();
      panelButtons.add(butClear);
      panelButtons.add(butPrev);
      panelButtons.add(butNext);
      panelButtons.add(butFindAccount);
      panelButtons.add(new JLabel("Find by name"));
      
      textNameFind = new JTextField(16);
      panelButtons.add(textNameFind);
      
      container.add("tab", panelButtons);

      labAccount = new JLabel(psc.getSwingCtx().getResString("account"));
      container.add("p", labAccount);
      textAccount = new JTextField(11);

      int acs = psc.getPascPrefs().getInt(ITechPrefsPascalSwing.UI_EXPLORER_ACCOUNT, 0);
      textAccount.setText(String.valueOf(acs));

      container.add("tab", textAccount);
      textAccount.getDocument().addDocumentListener(this);
      psc.setIntFilter(textAccount);

      labChecksum = new JLabel("Check sum");
      container.add("br", labChecksum);
      textCheckSum = new JTextField(2);
      textCheckSum.setEnabled(false);
      container.add("tab", textCheckSum);

      labType = new JLabel("Type");
      container.add("tab", labType);
      textType = new JTextField(7);
      container.add("tab", textType);

      butCopyAccountCk = new JButton("Copy Account-Ck");

      container.add("tab", butCopyAccountCk);

      //////////////////// new line
      
      labName = new JLabel(psc.getSwingCtx().getResString("name"));
      container.add("br", labName);
      textName = new JTextField(54);
      textName.setEditable(false);
      container.add("tab", textName);

      labBalance = new JLabel("Balance");
      container.add("br", labBalance);
      textBalance = new JTextField(15);
      textBalance.setEditable(false);
      container.add("tab", textBalance);

      labMolina = new JLabel("Molinas");
      container.add("tab", labMolina);
      textMolina = new JTextField(5);
      textMolina.setEditable(false);
      container.add("tab", textMolina);

      labPrice = new JLabel("Price");
      container.add("br", labPrice);
      textPrice = new JTextField(15);
      textPrice.setEditable(false);
      container.add("tab", textPrice);
      cbIsPrivate = new JCheckBox("Private Sale");
      cbIsPrivate.setEnabled(false);

      labSeller = new JLabel("Seller Account");
      container.add("tab", labSeller);
      textSeller = new JTextField(15);
      textSeller.setEditable(false);
      container.add("tab", textSeller);

      labLockBlock = new JLabel("Account locked for");
      container.add("tab", labLockBlock);
      textLockBlock = new JTextField(6);
      textLockBlock.setEditable(false);
      labBlocks = new JLabel("Blocks");
      container.add("tab", textLockBlock);
      container.add("", labBlocks);

      container.add("tab", cbIsPrivate);

      labNumOperation = new JLabel("#Operations");
      labNumOperation.setToolTipText("Number of output operation made from this account");
      textNumOperations = new JTextField(10);
      textNumOperations.setEditable(false);
      container.add("br", labNumOperation);
      container.add("tab", textNumOperations);

      labLastBlock = new JLabel("Last Block");
      labLastBlock.setToolTipText("Block of last operation");
      textLastBlock = new JTextField(10);
      textLastBlock.setEditable(false);
      container.add("tab", labLastBlock);
      container.add("tab", textLastBlock);

      labLastOpTime = new JLabel("Last operation");
      textLastOpTime = new JTextField(10);
      textLastOpTime.setEnabled(false);
      container.add("tab", labLastOpTime);
      container.add("tab", textLastOpTime);

      labPublicKey = new JLabel("Base58 Public key");
      textAreaPubKey = new JTextArea(2, 100);
      textAreaPubKey.setLineWrap(true);
      textAreaPubKey.setEditable(false);
      container.add("p", labPublicKey);
      container.add("tab", textAreaPubKey);

      labEncPubKey = new JLabel("Encoded Public key");
      textAreaEncPubKey = new JTextArea(2, 100);
      textAreaEncPubKey.setLineWrap(true);
      textAreaEncPubKey.setEditable(false);
      container.add("p", labEncPubKey);
      container.add("tab", textAreaEncPubKey);

      //container.add("p hfill", tableBen.getScrollPane());
      container.add("p hfill", accountOperations);

      JScrollPane scrollPaneAll = new JScrollPane(container);
      this.add(scrollPaneAll, BorderLayout.CENTER);
   }

   public void insertUpdate(DocumentEvent e) {
      //System.out.println("insertUpdate");
      updateAccount();
   }

   /**
    * 
    * @param text
    */
   public void newAccountStringTyped(String text) {
      if(text == null || text.equals("")) {
         return;
      }
      AddressValidationResult av = psc.getPCtx().getAddressValidator().validate(text);
      if (av.isValid()) {
         Integer iac = av.getAccount();
         psc.getPascPrefs().putInt(ITechPrefsPascalSwing.UI_EXPLORER_ACCOUNT, iac.intValue());
         setAccount(iac);
      } else {
         //#debug
         toDLog().pFlow("", null, PanelAccountDetails.class, "newAccountStringTyped", ITechLvl.LVL_04_FINER, true);
         psc.getLog().consoleLogError(av.getMessage());
      }
   }

   public void removeUpdate(DocumentEvent e) {
      //System.out.println("removeUpdate");
      updateAccount();
   }

   public void setAccount(Account account) {
      initCheck();

      DateFormat df = psc.getFormatDateTime();
      this.account = account;
      textAccount.getText();
      String newText = account.getAccount() + "";
      //         if(!textAccount.getText().equals(newText)) {
      //            textAccount.setText(); throw an exception
      //         }
      textCheckSum.setText("" + this.psc.getPCtx().calculateChecksum(account.getAccount()));
      textName.setText(account.getName() + "");

   
      Double d = account.getBalance();
      int decimal = 0;
      double fractional = 0.0;
      if (d != null) {
         double number = d.doubleValue(); // you have this
         decimal = (int) d.doubleValue(); // you have 12345
         fractional = number - decimal; // you have 0.6789
      }
      textBalance.setText(d.toString());
      String molinas = df.format(fractional);
      textMolina.setText("" + molinas);

      textNumOperations.setText(account.getnOperation().toString());
      String encPubKey = account.getEncPubkey();
      PublicKey pk = this.psc.getPascalClient().decodePubKey(encPubKey, null);
      textAreaPubKey.setText(pk.getBase58PubKey());
      textAreaEncPubKey.setText(encPubKey);
      Integer updateB = account.getUpdatedB();
      textLastBlock.setText("" + updateB);
      RPCConnection con = psc.getPCtx().getRPCConnection();
      if (con.isConnected()) {
         int diff = con.getLastBlockMined().intValue() - updateB.intValue();
         int numMinutes = diff * 5;
         // numDays
         int numDays = numMinutes / (60 * 24);
         textLastOpTime.setText(numDays + " days ago");
      } else {
         textLastOpTime.setText("");
      }
      textType.setText("" + account.getType());

      Double price = account.getPrice();
      if (price == null) {
         textPrice.setText("");
      } else {
         double val = price.doubleValue();
         String sprice = df.format(val);
         textPrice.setText(sprice);
      }
      Boolean b = account.getPrivateSale();
      if (b == null) {
         cbIsPrivate.setSelected(false);
      } else {
         cbIsPrivate.setSelected(b.booleanValue());
      }
      Integer seller = account.getSellerAccount();
      if (seller == null) {
         textSeller.setText("");
      } else {
         textSeller.setText(seller.toString());
      }
      Integer lockTillBlock = account.getLockedUntilBlock();
      if (seller == null) {
         textLockBlock.setText("");
      } else {
         textLockBlock.setText(lockTillBlock.toString());
      }

      accountOperations.clear();
      
      accountOperations.showAccount(account);

   }

   public Integer getAccountAsInt() {
      String str = textAccount.getText();

      return Integer.parseInt(str);
   }

   public void setAccount(Integer account) {
      Account ac = psc.getPascalClient().getAccount(account);
      setAccount(ac);
   }

   /**
    * 
    * @param account
    */
   public void setAccountExternal(Account account) {
      setAccountExternal(account.getAccount());
   }

   public void setAccountExternal(Integer account) {
      initCheck();
      textAccount.setText(account.toString());
   }

   public void tabGainFocus() {
      accountOperations.tabGainFocus();
   }

   public void tabLostFocus() {
      accountOperations.tabLostFocus();
   }

   /**
    * Wait a few microseconds to make sure the user has finished typing.
    * 
    */
   public void updateAccount() {
      newAccountStringTyped(textAccount.getText());
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "AccountDetailsPanel");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AccountDetailsPanel");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butClear) {
         textAccount.setText("");
         textType.setText("");
         textAreaEncPubKey.setText("");
         textAreaPubKey.setText("");
         textBalance.setText("");
         textCheckSum.setText("");
         textLastBlock.setText("");
         textLastOpTime.setText("");
         textLockBlock.setText("");
         textName.setText("");
         textNumOperations.setText("");
         textType.setText("");
         cbIsPrivate.setSelected(false);
         textMolina.setText("");
         accountOperations.clear();
      } else if (e.getSource() == butFindAccount) {
         newAccountStringTyped(textAccount.getText());
      } else if (e.getSource() == butNext) {
         Integer account = root.getAccountNext(getAccountAsInt());
         textAccount.setText(account.toString());
      } else if (e.getSource() == butPrev) {
         Integer account = root.getAccountPrev(getAccountAsInt());
         textAccount.setText(account.toString());
      }
   }

}