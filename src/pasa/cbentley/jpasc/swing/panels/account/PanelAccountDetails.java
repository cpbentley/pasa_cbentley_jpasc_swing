/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.account;

import java.awt.BorderLayout;
import java.awt.Color;
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
import pasa.cbentley.jpasc.pcore.utils.PascalCoinDouble;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.ITechPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByAccount;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.utils.DocRefresher;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BPanel;
import pasa.cbentley.swing.widgets.b.BTextField;
import pasa.dekholm.riverlayout.RiverLayout;

public class PanelAccountDetails extends PanelTabAbstractPascal implements DocumentListener, IMyTab, IMyGui, ActionListener, ICommandableRefresh {

   public static final String           ID               = "account_details";

   /**
    * 
    */
   private static final long            serialVersionUID = -1733914857369658375L;

   private Account                      account;

   private Integer                      accountNumber;

   private BButton                      butClear;

   private BButton                      butFindAccount;

   private BCheckBox                    cbIsPrivate;

   private BLabel                       labAccount;

   private BLabel                       labBalance;

   private BLabel                       labBlocks;

   private BLabel                       labChecksum;

   private BLabel                       labEncPubKey;

   private BLabel                       labLastBlock;

   private BLabel                       labLastOpTime;

   private BLabel                       labLockBlock;

   private BLabel                       labMolina;

   private BLabel                       labName;

   private BLabel                       labNumOperation;

   private BLabel                       labPrice;

   private BLabel                       labPublicKey;

   private BLabel                       labSeller;

   private BLabel                       labType;

   private BPanel                       operationsAccountPanel;

   private JTextField                   textAccount;

   private JTextArea                    textAreaEncPubKey;

   private JTextArea                    textAreaPubKey;

   private JTextField                   textBalance;

   private JTextField                   textCheckSum;

   private JTextField                   textLastBlock;

   private JTextField                   textLastOpTime;

   private JTextField                   textLockBlock;

   private JTextField                   textMolina;

   private JTextField                   textName;

   private JTextField                   textNumOperations;

   private JTextComponent               textPrice;

   private JTextField                   textSeller;

   private JTextField                   textType;

   private IRootTabPane                 root;

   private TablePanelOperationByAccount accountOperations;

   private BButton                      butNext;

   private BButton                      butPrev;

   private BButton                      butTopLeft;

   private JTextField                   textNameFind;

   private BButton                      butCopyAccountCk;

   private DocRefresher                 docRefresherAccountNumber;

   private BLabel                       labFindName;

   private DocRefresher                 docRefresherNameFind;

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
      super.guiUpdate();
   }

   public void initTab() {

      JPanel container = new JPanel();

      //table.setPreferredScrollableViewportSize(new Dimension(500, 70));

      accountOperations = new TablePanelOperationByAccount(psc, root, true);
      accountOperations.initCheck();

      RiverLayout rl = new RiverLayout();
      container.setLayout(rl);

      butClear = new BButton(sc, this, "but.clear");

      butNext = new BButton(sc, this, "but.acc.next");
      butPrev = new BButton(sc, this, "but.acc.previous");

      butFindAccount = new BButton(sc, this, "but.find.account");

      Icon icon = getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
      Icon iconSel = getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);

      //if icon is null.. use app icons
      if (icon == null) {
         icon = sc.getResIcon("default", "main", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
         icon = sc.getResIcon("default", "main", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);
      }

      butTopLeft = new BButton(sc, icon, iconSel);

      container.add("", butTopLeft);

      JPanel panelButtons = new JPanel();
      panelButtons.add(butPrev);
      panelButtons.add(butNext);
      panelButtons.add(butFindAccount);
      labFindName = new BLabel(sc, "but.findbyname");
      panelButtons.add(labFindName);
      panelButtons.add(butClear);

      textNameFind = new JTextField(16);
      docRefresherNameFind = new DocRefresher(sc, this);
      textNameFind.getDocument().addDocumentListener(docRefresherNameFind);
      panelButtons.add(textNameFind);

      container.add("tab", panelButtons);

      labAccount = new BLabel(sc, "text.account");
      container.add("p", labAccount);
      textAccount = new JTextField(11);
      String strKey = getAccountPrefKey();
      int acs = psc.getPascPrefs().getInt(strKey, 0);
      textAccount.setText(String.valueOf(acs));

      docRefresherAccountNumber = new DocRefresher(sc, this);

      container.add("tab", textAccount);
      textAccount.getDocument().addDocumentListener(docRefresherAccountNumber);
      psc.setIntFilter(textAccount);

      labChecksum = new BLabel(sc, "text.checksum");
      container.add("br", labChecksum);
      textCheckSum = new JTextField(2);
      textCheckSum.setEnabled(false);
      container.add("tab", textCheckSum);

      labType = new BLabel(sc, "text.type");
      container.add("tab", labType);
      textType = new JTextField(7);
      textType.setEnabled(false);
      container.add("tab", textType);

      butCopyAccountCk = new BButton(sc, this, "but.copyaccountck");

      container.add("tab", butCopyAccountCk);

      //////////////////// new line

      labName = new BLabel(sc, "text.accountname");
      container.add("br", labName);
      textName = new JTextField(54);
      textName.setEditable(false);
      container.add("tab", textName);

      labBalance = new BLabel(sc, "text.balance");
      container.add("br", labBalance);
      textBalance = new JTextField(15);
      textBalance.setEditable(false);
      container.add("tab", textBalance);

      labMolina = new BLabel(sc, "text.molinas");
      container.add("tab", labMolina);
      textMolina = new JTextField(5);
      textMolina.setEditable(false);
      container.add("tab", textMolina);

      labPrice = new BLabel(sc, "text.price");
      container.add("br", labPrice);
      textPrice = new JTextField(15);
      textPrice.setEditable(false);
      container.add("tab", textPrice);
      cbIsPrivate = new BCheckBox(sc, this, "text.privatesale");
      cbIsPrivate.setEnabled(false);

      labSeller = new BLabel(sc, "text.selleraccount");
      container.add("tab", labSeller);
      textSeller = new JTextField(15);
      textSeller.setEditable(false);
      container.add("tab", textSeller);

      labLockBlock = new BLabel(sc, "text.accountlocked");
      container.add("tab", labLockBlock);
      textLockBlock = new JTextField(6);
      textLockBlock.setEditable(false);
      labBlocks = new BLabel(sc, "text.blocks");
      container.add("tab", textLockBlock);
      container.add("", labBlocks);

      container.add("tab", cbIsPrivate);

      labNumOperation = new BLabel(sc, "text.operationsnum");
      textNumOperations = new JTextField(10);
      textNumOperations.setEditable(false);
      container.add("br", labNumOperation);
      container.add("tab", textNumOperations);

      labLastBlock = new BLabel(sc, "text.lastblock");
      textLastBlock = new JTextField(10);
      textLastBlock.setEditable(false);
      container.add("tab", labLastBlock);
      container.add("tab", textLastBlock);

      labLastOpTime = new BLabel(sc, "text.lastoperation");
      textLastOpTime = new JTextField(10);
      textLastOpTime.setEnabled(false);
      container.add("tab", labLastOpTime);
      container.add("tab", textLastOpTime);

      labPublicKey = new BLabel(sc, "text.keybase58");
      textAreaPubKey = new JTextArea(2, 100);
      textAreaPubKey.setLineWrap(true);
      textAreaPubKey.setEditable(false);
      container.add("p", labPublicKey);
      container.add("tab", textAreaPubKey);

      labEncPubKey = new BLabel(sc, "text.keyencoded");
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

   private String getAccountPrefKey() {
      return ITechPrefsPascalSwing.UI_EXPLORER_ACCOUNT + getTabInternalID();
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
      if (text == null || text.equals("")) {
         return;
      }
      AddressValidationResult av = psc.getPCtx().getAddressValidator().validate(text);
      if (av.isValid()) {
         Integer iac = av.getAccount();
         psc.getPascPrefs().putInt(getAccountPrefKey(), iac.intValue());
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

      if (!textAccount.getText().equals(newText)) {
         docRefresherAccountNumber.setEnabled(false);
         textAccount.setText(newText);
         docRefresherAccountNumber.setEnabled(true);
      }

      textCheckSum.setText("" + this.psc.getPCtx().calculateChecksum(account.getAccount()));
      textName.setText(account.getName() + "");

      PascalCoinDouble accountBalance = psc.getPCtx().getAccountBalance(account);

      textBalance.setText(accountBalance.getString());
      textMolina.setText(accountBalance.getMolinasStr());

      textNumOperations.setText(account.getnOperation().toString());
      String encPubKey = account.getEncPubkey();
      PublicKey pk = this.psc.getPascalClient().decodePubKey(encPubKey, null);
      textAreaPubKey.setText(pk.getBase58PubKey());
      textAreaEncPubKey.setText(encPubKey);
      Integer updateB = account.getUpdatedB();
      textLastBlock.setText("" + updateB);
      RPCConnection con = psc.getPCtx().getRPCConnection();
      if (con.isConnected()) {
         int ageDiff = con.getLastBlockMined().intValue() - updateB.intValue();
         String timeAgo = psc.getPascalSwingUtils().computeTimeFromBlockAge(ageDiff);
         textLastOpTime.setText(ageDiff + " : " + timeAgo);
      } else {
         textLastOpTime.setText("Not connected");
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
      sc.executeLaterInUIThread(new Runnable() {
         public void run() {
            if (textCheckSum.getText() == null || textCheckSum.getText().equals("")) {
               newAccountStringTyped(textAccount.getText());
            }
         }
      });
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
         cmdClear();
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

   private void cmdClear() {
      textNameFind.setText("");

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
   }

   public void cmdRefresh(Object source) {
      if (source == docRefresherAccountNumber) {
         newAccountStringTyped(textAccount.getText());
      } else if (source == docRefresherNameFind) {
         String nameStr = textNameFind.getText();
         Account account = root.getAccessPascal().getAccessAccountDBolet().getAccountWithName(nameStr);
         if (account != null) {
            setAccount(account);
            textNameFind.setForeground(Color.green);
         } else {
            textNameFind.setForeground(Color.red);
         }
      }
   }

}