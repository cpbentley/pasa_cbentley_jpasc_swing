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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.utils.DateUtils;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.pcore.rpc.model.Block;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.pcore.utils.AddressValidationResult;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinDouble;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByAccount;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.utils.DocRefresher;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BPanel;
import pasa.cbentley.swing.widgets.b.BTextArea;
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

   private TablePanelOperationByAccount accountOperations;

   private BButton                      butClear;

   private BButton                      butCopyAccountCk;

   private BButton                      butEncPubKey;

   private BButton                      butFindAccount;

   private BButton                      butShowAccountOperations;

   private BButton                      butNext;

   private BButton                      butPrev;

   private BButton                      butPublicKey58;

   private BButton                      butTopLeft;

   private BCheckBox                    cbIsPrivate;

   private DocRefresher                 docRefresherAccountNumber;

   private DocRefresher                 docRefresherNameFind;

   private BLabel                       labAccount;

   private BLabel                       labBalance;

   private BLabel                       labBlocks;

   private BLabel                       labChecksum;

   private BLabel                       labFindName;

   private BLabel                       labLastBlockActive;

   private BLabel                       labLastOpTimeActive;

   private BLabel                       labLockBlock;

   private BLabel                       labMolina;

   private BLabel                       labName;

   private BLabel                       labKeyNames;

   private BLabel                       labKeyNamePrivate;

   private BLabel                       labKeyNamePublic;

   private BTextField                   textKeyNamePrivate;

   private BTextField                   textKeyNamePublic;

   private BLabel                       labNumOperation;

   private BLabel                       labPrice;

   private BLabel                       labSeller;

   private BLabel                       labType;

   private BPanel                       operationsAccountPanel;

   private IRootTabPane                 root;

   private JTextField                   textAccount;

   private BTextArea                    textAreaEncPubKey;

   private BTextArea                    textAreaPubKey;

   private JTextField                   textBalance;

   private JTextField                   textCheckSum;

   private JTextField                   textLastBlockActive;

   private JTextField                   textLastOpTimeActive;

   private JTextField                   textLockBlock;

   private JTextField                   textMolina;

   private JTextField                   textName;

   private JTextField                   textNameFind;

   private JTextField                   textNumOperations;

   private JTextComponent               textPrice;

   private JTextField                   textSeller;

   private JTextField                   textType;

   private BButton                      butRandom;

   private BButton                      butChangeKeyNames;

   private BLabel                       labSeal;

   private JTextField                   textSeal;

   private BLabel                       labData;

   private JTextField                   textData;

   private BLabel                       labLastBlockPassive;

   private JTextField                   textLastBlockPassive;

   private BLabel                       labLastOpTimePassive;

   private JTextField                   textLastOpTimePassive;

   private BButton                      butOpenOwnWindow;

   public PanelAccountDetails(PascalSwingCtx psc, IRootTabPane root) {
      this(psc, root, ID);
   }

   public PanelAccountDetails(PascalSwingCtx psc, IRootTabPane root, String id) {
      super(psc, id);
      this.root = root;
      this.setLayout(new BorderLayout());
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butClear) {
         cmdClear();
      } else if (src == butFindAccount) {
         newAccountStringTyped(textAccount.getText());
      } else if (src == butNext) {
         Integer account = root.getAccountNext(getAccountAsInt());
         textAccount.setText(account.toString());
      } else if (src == butPrev) {
         Integer account = root.getAccountPrev(getAccountAsInt());
         textAccount.setText(account.toString());
      } else if (src == butRandom) {
         Integer account = psc.getUC().getRandom().nextInt(psc.getPCtx().getLastValidAccount());
         textAccount.setText(account.toString());
      } else if (src == butCopyAccountCk) {
         String value = textAccount.getText() + "-" + textCheckSum.getText();
         psc.copyToClipboard(value, "Account");
      } else if (src == butEncPubKey) {
         String value = textAreaEncPubKey.getText();
         psc.copyToClipboard(value, "Encoded key");
      } else if (src == butPublicKey58) {
         String value = textAreaPubKey.getText();
         psc.copyToClipboard(value, "Public key");
      } else if (src == butChangeKeyNames) {
         if (account != null) {
            psc.getCmds().getCmdKeyChangeName().executeWith(account);
            //update the names
            privateUpdateKeyNames();
         }
      } else if (src == butOpenOwnWindow) {
         if (account != null) {
            PanelAccountDetails details = new PanelAccountDetails(psc, root);
            details.setAccount(account);
            psc.getSwingCtx().showInNewFrame(details);
         }
      }
   }

   private void privateUpdateKeyNames() {
      String encKey = account.getEncPubkey();
      String name = psc.getPCtx().getKeyNameProvider().getPkNameStorePublic().getKeyNameAdd(encKey);
      textKeyNamePublic.setText(name);
      if (psc.isPrivateCtx()) {
         name = psc.getPCtx().getKeyNameProvider().getPkNameStorePrivate().getKeyName(encKey);
         textKeyNamePrivate.setText(name);
      } else {
         textKeyNamePrivate.setText(sc.getResString("text.publicmodehidekeyname"));
      }

   }

   /**
    * 
    */
   public void changedUpdate(DocumentEvent e) {
      //System.out.println("changedUpdate");
      updateAccount();
   }

   private void cmdClear() {
      textNameFind.setText("");

      textAccount.setText("");
      textType.setText("");
      textAreaEncPubKey.setText("");
      textAreaPubKey.setText("");
      textBalance.setText("");
      textCheckSum.setText("");
      textLastBlockActive.setText("");
      textLastOpTimeActive.setText("");
      textLastBlockPassive.setText("");
      textLastOpTimePassive.setText("");
      textData.setText("");
      textSeal.setText("");
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

   public void disposeTab() {

   }

   public Integer getAccountAsInt() {
      String str = textAccount.getText();

      return Integer.parseInt(str);
   }

   private String getAccountPrefKey() {
      return IPrefsPascalSwing.UI_EXPLORER_ACCOUNT + getTabInternalID();
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
      butRandom = new BButton(sc, this, "but.acc.random");
      butOpenOwnWindow = new BButton(sc, this, "but.open.window");

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
      panelButtons.add(butFindAccount);
      labFindName = new BLabel(sc, "but.findbyname");
      panelButtons.add(labFindName);

      textNameFind = new JTextField(16);
      docRefresherNameFind = new DocRefresher(sc, this);
      textNameFind.getDocument().addDocumentListener(docRefresherNameFind);
      panelButtons.add(textNameFind);
      panelButtons.add(butClear);

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

      container.add("tab", butPrev);
      container.add("tab", butNext);
      container.add("tab", butRandom);
      container.add("tab", butOpenOwnWindow);

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

      //-------------------------

      labNumOperation = new BLabel(sc, "text.operationsnum");
      textNumOperations = new JTextField(10);
      textNumOperations.setEditable(false);
      container.add("br", labNumOperation);
      container.add("tab", textNumOperations);

      labSeal = new BLabel(sc, "text.seal");
      textSeal = new JTextField(40);
      textSeal.setEnabled(false);
      container.add("tab", labSeal);
      container.add("tab", textSeal);

      //-----------------------

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

      butChangeKeyNames = new BButton(sc, this, "but.changekeynames");
      labKeyNamePrivate = new BLabel(sc, "text.keynameprivate");
      labKeyNamePublic = new BLabel(sc, "text.keynamepublic");

      textKeyNamePrivate = new BTextField(sc, 20);
      textKeyNamePrivate.setKeyTip("text.keynameprivate.tip");
      textKeyNamePrivate.setEnabled(false);

      textKeyNamePublic = new BTextField(sc, 20);
      textKeyNamePublic.setKeyTip("text.keynamepublic.tip");
      textKeyNamePublic.setEnabled(false);

      container.add("br", butChangeKeyNames);
      container.add("tab", labKeyNamePrivate);
      container.add("tab", textKeyNamePrivate);
      container.add("tab", labKeyNamePublic);
      container.add("tab", textKeyNamePublic);

      butPublicKey58 = new BButton(sc, this, "text.keybase58");
      textAreaPubKey = new BTextArea(sc, 1, 100);
      textAreaPubKey.setLineWrap(true);
      textAreaPubKey.setEditable(false);
      textAreaPubKey.setTextKeyTip("text.keybase58.tip");

      container.add("p", butPublicKey58);
      container.add("tab", textAreaPubKey);

      butEncPubKey = new BButton(sc, this, "text.keyencoded");
      textAreaEncPubKey = new BTextArea(sc, 1, 130);
      textAreaEncPubKey.setLineWrap(true);
      textAreaEncPubKey.setEditable(false);
      textAreaPubKey.setTextKeyTip("text.keyencoded.tip");

      container.add("p", butEncPubKey);
      container.add("tab", textAreaEncPubKey);

      labLastBlockActive = new BLabel(sc, "text.lastblock.active");
      textLastBlockActive = new JTextField(10);
      textLastBlockActive.setEditable(false);
      container.add("br", labLastBlockActive);
      container.add("tab", textLastBlockActive);

      labLastOpTimeActive = new BLabel(sc, "text.lastoperation.active");
      textLastOpTimeActive = new JTextField(60);
      textLastOpTimeActive.setEnabled(false);
      container.add("tab", labLastOpTimeActive);
      container.add("tab", textLastOpTimeActive);

      labLastBlockPassive = new BLabel(sc, "text.lastblock.passive");
      textLastBlockPassive = new JTextField(10);
      textLastBlockPassive.setEditable(false);
      container.add("br", labLastBlockPassive);
      container.add("tab", textLastBlockPassive);

      labLastOpTimePassive = new BLabel(sc, "text.lastoperation.passive");
      textLastOpTimePassive = new JTextField(60);
      textLastOpTimePassive.setEnabled(false);
      container.add("tab", labLastOpTimePassive);
      container.add("tab", textLastOpTimePassive);

      //container.add("p hfill", tableBen.getScrollPane());
      container.add("p hfill", accountOperations);

      labData = new BLabel(sc, "text.data");
      textData = new JTextField(50);
      textData.setEnabled(false);
      container.add("br", labData);
      container.add("tab", textData);

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

      Integer updateBlockActive = account.getUpdatedBlockActive();
      textLastBlockActive.setText("" + updateBlockActive);

      Integer updateBlockPassive = account.getUpdatedBlockPassive();
      textLastBlockPassive.setText("" + updateBlockPassive);

      RPCConnection con = psc.getPCtx().getRPCConnection();
      if (con.isConnected()) {
         int lastBlockInt = con.getLastBlockMined().intValue();
         String strBlockAge = computeBlockAgeStr(updateBlockActive, con, lastBlockInt);
         textLastOpTimeActive.setText(strBlockAge);
         strBlockAge = computeBlockAgeStr(updateBlockPassive, con, lastBlockInt);
         textLastOpTimePassive.setText(strBlockAge);
      } else {
         textLastOpTimeActive.setText("Not connected");
         textLastOpTimePassive.setText("Not connected");
      }

      textType.setText("" + account.getType());

      Double price = account.getPrice();
      if (price == null) {
         textPrice.setText("");
      } else {
         PascalCoinDouble pricePasc = new PascalCoinDouble(psc.getPCtx(), price);
         textPrice.setText(pricePasc.getString());
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
      String data = account.getData();
      if (data == null) {
         data = "";
      }
      textData.setText(data);

      String seal = account.getSeal();
      if (seal == null) {
         seal = "";
      }
      textSeal.setText(seal);

      privateUpdateKeyNames();

      accountOperations.clear();

      accountOperations.showAccount(account);

   }

   private String computeBlockAgeStr(Integer updateBlockActive, RPCConnection con, int lastBlockInt) {
      int updateBlockActiveInt = updateBlockActive.intValue();
      int ageDiffBlockTime = lastBlockInt - updateBlockActiveInt;
      String timeAgo = psc.getPascalSwingUtils().computeTimeFromBlockAgePascalTime(ageDiffBlockTime);
      Block block = con.getPClient().getBlock(updateBlockActive);
      Long timeStamp = block.getTimestamp();
      String realTimeAgo = DateUtils.getDaysNazad(timeStamp.longValue() * 1000, System.currentTimeMillis());
      String strBlockAge = ageDiffBlockTime + " blocks ago i.e. " + timeAgo + " in pascal time, i.e." + realTimeAgo + " in our time";
      return strBlockAge;
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
      boolean isManualRefresh = psc.getPascPrefs().getBoolean(IPrefsPascalSwing.PREF_GLOBAL_MANUAL_REFRESH, false);
      if (!isManualRefresh) {
         accountOperations.tabGainFocus();
      }
   }

   public void tabLostFocus() {
      accountOperations.tabLostFocus();
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

   /**
    * Wait a few microseconds to make sure the user has finished typing.
    * 
    */
   public void updateAccount() {
      newAccountStringTyped(textAccount.getText());
   }

}