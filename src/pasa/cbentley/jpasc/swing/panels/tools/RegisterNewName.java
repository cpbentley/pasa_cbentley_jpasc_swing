/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.rpc.exception.RPCApiException;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.pcore.rpc.model.Operation;
import pasa.cbentley.jpasc.pcore.rpc.model.PayLoadEncryptionMethod;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
import pasa.cbentley.jpasc.pcore.utils.PascalUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.account.PanelAccountDetails;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyWallet;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperNameWallet;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletName;
import pasa.cbentley.jpasc.swing.workers.WorkerSearchExactName;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.KeyedSentence;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BTextField;

public class RegisterNewName extends PanelTabAbstractPascal implements IMyGui, IWorkerPanel, ActionListener, IMyTab, ICommandableRefresh, DocumentListener, ListSelectionListener {
   private static final String           CHANGEKEY        = "changekey";

   private static final String           CHANGETYPE       = "changetype";

   public static final String            ID               = "register_name";

   /**
    * 
    */
   private static final long             serialVersionUID = 3053613301193488738L;

   private Account                       accountOwner;

   private BButton                       butRegister;

   private BButton                       butViewAccount;

   private BCheckBox                     cbChangeKey;

   private JCheckBox                     cbChangeType;

   private JButton                       clear;

   private PanelHelperKeyWallet          panelHelperKeyWalletSendTo;

   private Color                         greenRes         = new Color(ColorUtils.FR_VERT_Absinthe);

   private JLabel                        labAccountValue;

   private JLabel                        labFilter;

   private JLabel                        labHelpFee;

   private JLabel                        labHelpKey;

   private JLabel                        labHelpKeyValue;

   private BLabel                        labHelpType;

   private JLabel                        labHelpTypeValue;

   private BLabel                        labInvalidChars;

   private BLabel                        labLetters;

   private JLabel                        labRegisterHelp;

   private BLabel                        labResult;

   private JLabel                        labResultType;

   private BLabel                        labValidChars;

   private WorkerSearchExactName         lastWorkerNameSearch;

   protected TablePanelAccountWalletName tablePanelAccountWalletName;

   private int                           modelIndexAccount;

   private String                        name;

   private Color                         orangeRes        = new Color(ColorUtils.FR_ORANGE_Citrouille);

   private Color                         redRes           = new Color(ColorUtils.FR_ROUGE_Brique);

   private IRootTabPane                  root;

   private IRootTabPane                  rootNameCheck;

   private Account                       selectedAccount;

   protected JTextField                  textFee;

   private BTextField                    textInvalidChars;

   private BLabel                        textLetters;

   private JTextField                    textName;

   private BTextField                    textType;

   private BTextField                    textValidChars;

   protected JPanel                      top1;

   public RegisterNewName(PascalSwingCtx psc, IRootTabPane root, IRootTabPane rootNameCheck) {
      super(psc, ID);
      this.root = root;
      this.rootNameCheck = rootNameCheck;
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == clear) {
         cmdClear();
      } else if (src == butRegister) {
         cmdRegister();
      } else if (src == butViewAccount) {
         if (accountOwner != null) {
            root.showAccountDetails(accountOwner);
         }
      } else if (src == cbChangeType) {
         cmdToggleChangeType();
      } else if (src == panelHelperKeyWalletSendTo) {
         cmdSendKeyChange();
      } else if (src == cbChangeKey) {
         cmdToggleChangeKey();
      }
   }

   public void changedUpdate(DocumentEvent e) {
      processDocumentEvent(e);
   }

   private void cmdClear() {
      textName.setText("");
      registerHelpUpdate();
   }

   /**
    * 
    */

   private void cmdRegister() {

      //get the data
      if (psc.getPCtx().getRPCConnection().isLocked()) {
         boolean isUnlocked = psc.askToUnlock("Enter the Daemon Wallet Password", "", psc.getFrameRoot());
         if (!isUnlocked) {
            sc.getLog().consoleLogError("Failed to unlock");

         }
      }

      boolean isUpdateType = cbChangeType.isSelected();

      boolean isUpdateKey = cbChangeKey.isSelected();

      String name = textName.getText();

      PascalCoinValue fee = psc.getFee(textFee);

      if (selectedAccount == null) {
         return;
      }

      PCoreCtx pcore = psc.getPCtx();
      Integer accountInteger = selectedAccount.getAccount();
      if (!pcore.getPasaServices().isAccountGreenForNewFreeOperation(accountInteger)) {
         sc.getLog().consoleLogError("Account " + accountInteger + " is already busy on a free tx. Wait for next block to use it again");
         return;
      }
      //create a new operation of the type Java
      //target is the same as signer
      Integer accountTarget = accountInteger;
      Integer accountSigner = accountInteger;

      String newEncPubKey = null;
      if (isUpdateKey) {
         PublicKey selectedKey = panelHelperKeyWalletSendTo.getSelectedKeyPublicKey();
         if (selectedKey != null) {
            newEncPubKey = selectedKey.getEncPubKey();
         }
      }
      String newB58PubKey = null;
      String newName = name;
      Short newType = null;
      if (isUpdateType) {
         try {
            newType = Short.valueOf(textType.getText());
         } catch (NumberFormatException e) {
            sc.getLog().consoleLogError("Type is invalid. Number from 0 to max 65535 ");
            return;
         }
      }
      byte[] payload = null;
      PayLoadEncryptionMethod payloadMethod = null;
      String pwd = null;
      Double pfee = fee.getDouble();

      //#debug
      toDLog().pFlow("newName=" + newName + " newEncPubKey=" + newEncPubKey + " newType=" + newType, null, RegisterNewName.class, "cmdRegister", ITechLvl.LVL_05_FINE, true);
      try {
         Operation op = psc.getPascalClient().changeAccountInfo(accountTarget, accountSigner, newEncPubKey, newB58PubKey, newName, newType, pfee, payload, payloadMethod, pwd);
         if (op != null) {
            sc.getLog().consoleLogGreen("changeAccountInfo operation successfull ");
            pcore.getPasaServices().registerAccountInPendingOperations(op);
            cmdClear();
            //update account in model
            selectedAccount.setName(newName);
            //fire model update
            tablePanelAccountWalletName.getTableModel().fireTableRowsUpdated(modelIndexAccount, modelIndexAccount);
         } else {
            sc.getLog().consoleLogError("changeAccountInfo operation failed ");
         }
      } catch (RPCApiException e) {
         e.printStackTrace();
         sc.getLog().consoleLogError("changeAccountInfo operation failed " + e.getMessage());
      }

   }

   private void cmdSendKeyChange() {
      PublicKey pk = panelHelperKeyWalletSendTo.getSelectedKeyPublicKey();
      labHelpKeyValue.setText(pk.getName());
   }

   private void cmdToggleChangeKey() {
      boolean isChecked = cbChangeKey.isSelected();
      psc.getPascPrefs().getBoolean(CHANGEKEY, isChecked);
      panelHelperKeyWalletSendTo.setEnabled(isChecked);
   }

   private void cmdToggleChangeType() {
      boolean isChecked = cbChangeType.isSelected();
      psc.getPascPrefs().getBoolean(CHANGETYPE, isChecked);
      textType.setEnabled(isChecked);
      if (isChecked) {

      } else {
         labResultType.setText("");
      }
   }

   public void disposeTab() {
   }

   private void documentChangeName() {
      //   worker.cancel(true); 
      final String name = textName.getText();
      if (name == null) {
         return;
      }
      if (name.length() > 64) {
         labResult.setForeground(Color.red);
         labResult.setText("name must be 64 characters or less");
         return;
      }
      int index = PascalUtils.hasInvalidIndex(name);
      if (index != -1) {
         labResult.setForeground(Color.red);

         KeyedSentence sentence = labResult.newSentence();
         sentence.key("text.character");
         sentence.c(name.charAt(index));
         sentence.key("text.invalid");
         sentence.key("text.position");
         sentence.i(index + 1);
         sentence.setKeyToolTip("label.invalidchar.tip");

         labResult.guiUpdate();
         return;
      }
      textLetters.setText(String.valueOf(name.length()));
      if (name.length() < 3) {
         labResult.setForeground(Color.red);
         labResult.setText("name must be 3 characters or more");
         return;
      }

      char c0 = name.charAt(0);
      if (sc.getUC().getCU().isNumerical(c0)) {
         labResult.setForeground(Color.red);
         labResult.setText("name cannot start with 0 1 2 3 4 5 6 7 8 or 9");
         return;
      }

      //otherwise update
      labResult.setForeground(orangeRes);
      labResult.setText("Searching for " + name + "...");

      lastWorkerNameSearch = new WorkerSearchExactName(sc, this, rootNameCheck, name);
      lastWorkerNameSearch.execute();
   }

   private void documentChangeType() {
      Integer newType = 0;
      try {
         newType = Integer.valueOf(textType.getText());
      } catch (NumberFormatException e) {
         labResultType.setForeground(Color.red);
         labResultType.setText("Type invalid. Must be a number between 0 and  65535");
         return;
      }
      if (newType >= 0 && newType <= 65535) {
         labResultType.setForeground(Color.green);
         labResultType.setText("Type " + newType + " is ok");
      } else {
         labResultType.setForeground(Color.red);
         labResultType.setText("Type invalid. Must be a number between 0 and  65535");

      }
   }

   public String getGiverString() {
      return textName.getText();
   }

   /**
    * Called at the end of initTab
    * 
    * Sub class may create its own items
    */
   protected void initTabSub() {

   }

   public void initTab() {
      this.setLayout(new BorderLayout());

      JPanel north = new JPanel();
      north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));

      top1 = new JPanel();
      top1.setLayout(new FlowLayout(FlowLayout.LEADING));
      JPanel top12 = new JPanel();
      top12.setLayout(new FlowLayout(FlowLayout.LEADING));

      JPanel top2 = new JPanel();
      top2.setLayout(new FlowLayout(FlowLayout.LEADING));

      JPanel topChangeTypeKey = new JPanel();
      topChangeTypeKey.setLayout(new FlowLayout(FlowLayout.LEADING));

      JPanel top4 = new JPanel();
      top4.setLayout(new FlowLayout(FlowLayout.LEADING));

      labFilter = new JLabel("Check if name is available");
      textName = new JTextField(64);
      textName.setToolTipText("");
      textName.getDocument().addDocumentListener(this);

    

      clear = new JButton("Clear");
      clear.addActionListener(this);
      labLetters = new BLabel(sc, "lab.letters");
      textLetters = new BLabel(sc);

      labHelpType = new BLabel(sc, "text.type");
      textType = new BTextField(sc);
      textType.setColumns(5);
      textType.setToolTipText("text.type.tip");
      textType.getDocument().addDocumentListener(this);

      labResult = new BLabel(sc);

      butViewAccount = new BButton(sc, this, "but.account.inspector");
      butViewAccount.setIcon(PanelAccountDetails.ID, "tab", IconFamily.ICON_SIZE_1_SMALL);
      butViewAccount.setEnabled(false);

      top1.add(labFilter);
      top1.add(textName);
      top1.add(clear);
      top1.add(textLetters);
      top1.add(labLetters);

     

      labValidChars = new BLabel(sc, "lab.validchars");
      textValidChars = new BTextField(sc, "text.validchars");
      textValidChars.setToolTipText("text.validchars.tip");
      textValidChars.setForeground(greenRes);
      textValidChars.setEditable(false);

      labInvalidChars = new BLabel(sc, "lab.invalidchars");
      textInvalidChars = new BTextField(sc, "text.invalidchars");
      textInvalidChars.setToolTipText("text.invalidchars.tip");
      textInvalidChars.setForeground(redRes);
      textInvalidChars.setEditable(false);

      cbChangeType = new BCheckBox(sc, this, "cb.changetype");
      boolean isDefSelected = psc.getPascPrefs().getBoolean(CHANGETYPE, false);
      cbChangeType.setSelected(isDefSelected);
      textType.setEnabled(isDefSelected);

      labResultType = new JLabel();

      cbChangeKey = new BCheckBox(sc, this, "cb.changekey");
      boolean isDefChangeKeySelected = psc.getPascPrefs().getBoolean(CHANGEKEY, false);

      panelHelperKeyWalletSendTo = new PanelHelperKeyWallet(psc, this, this);
      panelHelperKeyWalletSendTo.setEnabledKeyChoice(isDefChangeKeySelected);

      top12.add(labValidChars);
      top12.add(textValidChars);

      top12.add(labInvalidChars);
      top12.add(textInvalidChars);

      //the text is dynamically created
      butRegister = new BButton(sc, this);
      butRegister.setIcon(RegisterNewName.ID, "tab", IconFamily.ICON_SIZE_1_SMALL);
      butRegister.setEnabled(false);

      labRegisterHelp = new JLabel("<-");

      labAccountValue = new JLabel();
      labAccountValue.setForeground(greenRes);

      labHelpFee = new BLabel(sc, "text.withafee");
      textFee = new JTextField(6);
      textFee.setText("0.0000");
      textFee.setToolTipText("fee.tooltip");

      labHelpTypeValue = new JLabel();

      labHelpKey = new JLabel(" to key ");
      labHelpKeyValue = new JLabel();

      top4.add(butViewAccount);
      top4.add(labResult);

      topChangeTypeKey.add(cbChangeKey);
      topChangeTypeKey.add(panelHelperKeyWalletSendTo);
      topChangeTypeKey.add(cbChangeType);
      topChangeTypeKey.add(textType);
      topChangeTypeKey.add(labResultType);

      top2.add(butRegister);
      top2.add(labRegisterHelp);
      top2.add(labAccountValue);
      top2.add(labHelpType);
      top2.add(labHelpTypeValue);
      top2.add(labHelpKey);
      top2.add(labHelpKeyValue);
      top2.add(labHelpFee);
      top2.add(textFee);

      //todo append a message payload in the change of name.. like a command or something similar

      north.add(top1);
      north.add(top12);
      north.add(top4);
      north.add(topChangeTypeKey);
      north.add(top2);

      this.add(north, BorderLayout.NORTH);

      JPanel south = new JPanel(new FlowLayout(FlowLayout.LEADING));
      this.add(south, BorderLayout.SOUTH);

      //center we list accounts

      tablePanelAccountWalletName = new TablePanelAccountWalletName(psc, root);
      tablePanelAccountWalletName.setSelectionModeSingle();
      tablePanelAccountWalletName.getJTable().getSelectionModel().addListSelectionListener(this);

      //configure panel helper
      PanelHelperNameWallet panelNameHelper = tablePanelAccountWalletName.getPanelNameHelperWallet();
      panelNameHelper.setOnlyEmptyNamesNoEvent(true);
      panelNameHelper.setAddEditingLink(false);

      tablePanelAccountWalletName.initCheck();
      this.add(tablePanelAccountWalletName, BorderLayout.CENTER);

      initTabSub();
   }

   public void insertUpdate(DocumentEvent e) {
      processDocumentEvent(e);

   }

   private void processDocumentEvent(DocumentEvent e) {
      Object src = e.getDocument();
      if (src == textName.getDocument()) {
         documentChangeName();
      } else if (src == textType.getDocument()) {
         documentChangeType();
      }
   }

   /**
    * Construct a string with given parameters
    */
   private void registerHelpUpdate() {
      //constr
      if (accountOwner == null) {
         labResult.setForeground(greenRes);
         labResult.setText(name + " is valid");
         butViewAccount.setEnabled(false);
         boolean isOneAccountSelected = selectedAccount != null;
         if (isOneAccountSelected) {
            if (selectedAccount.getName().equals(name)) {
               //do not allow an account to register existing name
               butRegister.setEnabled(false);
            } else {
               butRegister.setEnabled(true);
               labRegisterHelp.setText("<- register " + name + " to ");
               labAccountValue.setText(selectedAccount.getAccount().toString());
               textType.setText(selectedAccount.getType().toString());
            }
         } else {
            butRegister.setEnabled(false);
         }
      } else {
         labResult.setForeground(redRes);
         String text = null;
         String keyName = psc.getPCtx().getWalletKeyOwner(accountOwner.getAccount());
         if (keyName != null) {
            text = this.name + " owned your account #" + accountOwner.getAccount() + " on key " + keyName;
         } else {
            text = this.name + " owned by account #" + accountOwner.getAccount();
         }
         labResult.setText(text);

         butViewAccount.setEnabled(true);
         butRegister.setEnabled(false);
      }
   }

   public void removeUpdate(DocumentEvent e) {
      processDocumentEvent(e);
   }

   public void tabGainFocus() {
      tablePanelAccountWalletName.tabGainFocus();
   }

   public void tabLostFocus() {
      tablePanelAccountWalletName.tabLostFocus();
   }

   /**
    * When Table is refreshed.. what happens to the selection index?
    */
   public void valueChanged(ListSelectionEvent e) {
      //#debug
      toDLog().pFlow("" + sc.toSD().d1(e), this, RegisterNewName.class, "valueChanged", ITechLvl.LVL_05_FINE, true);
      if (e.getValueIsAdjusting()) {
         return;
      }

      try {
         //map index to model index;
         int index = tablePanelAccountWalletName.getJTable().getSelectedRow();
         if (index == -1) {
            selectedAccount = null;
            textName.setText("");
         } else {
            modelIndexAccount = tablePanelAccountWalletName.getJTable().convertRowIndexToModel(index);
            Account ac = tablePanelAccountWalletName.getTableModel().getRow(modelIndexAccount);
            selectedAccount = ac;
            if (!ac.getName().equals("")) {
               textName.setText(ac.getName());
            }
         }
         registerHelpUpdate();
      } catch (Exception ex) {
         //#debug
         toDLog().pFlow("", tablePanelAccountWalletName, RegisterNewName.class, "valueChanged", ITechLvl.LVL_10_SEVERE, false);

         ex.printStackTrace();
      }
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker worker) {
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {

   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      if (worker == lastWorkerNameSearch) {
         accountOwner = lastWorkerNameSearch.getAccount();
         name = lastWorkerNameSearch.getName();
         registerHelpUpdate();
      } else {
         //just log search result
         if (worker instanceof WorkerSearchExactName) {
            WorkerSearchExactName wns = (WorkerSearchExactName) worker;
            Account ac = wns.getAccount();
            if (ac == null) {
               sc.getLog().consoleLog(wns.getName() + " is available");
            } else {
               sc.getLog().consoleLog(wns.getName() + " is taken by " + ac.getAccount());
            }
         }
      }
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

   public void cmdRefresh(Object src) {
      //called when the drop down has been refreshed
      if (src == panelHelperKeyWalletSendTo) {

      }
   }

}