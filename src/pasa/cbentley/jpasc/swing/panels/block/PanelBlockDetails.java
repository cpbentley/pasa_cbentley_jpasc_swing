package pasa.cbentley.jpasc.swing.panels.block;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.pcore.rpc.model.Block;
import pasa.cbentley.jpasc.pcore.utils.AddressValidationResult;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinDouble;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.account.PanelAccountDetails;
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

public class PanelBlockDetails extends PanelTabAbstractPascal implements DocumentListener, IMyTab, IMyGui, ActionListener, ICommandableRefresh {

   public static final String           ID               = "block_details";

   /**
    * 
    */
   private static final long            serialVersionUID = -1733914857369658375L;

   private Block                        block;

   private TablePanelOperationByAccount accountOperations;

   private BButton                      butClear;

   private BButton                      butCopyBlockPayload;

   private BButton                      butSafeBoxHash;

   private BButton                      butNext;

   private BButton                      butPrev;

   private BButton                      butPow;

   private BButton                      butTopLeft;

   private DocRefresher                 docRefresherBlockNumber;

   private DocRefresher                 docRefresherNameFind;

   private BLabel                       labAccount;

   private BLabel                       labVolume;

   private BLabel                       labBlocks;

   private BLabel                       labTimestamp;

   private BLabel                       labBlockAgePascal;

   private BLabel                       labPayload;

   private BLabel                       labNumOperation;

   private BLabel                       labVersion;

   private BLabel                       labTarget;

   private BLabel                       labFee;

   private BPanel                       operationsAccountPanel;

   private IRootTabPane                 root;

   private JTextField                   textBlock;

   private BTextArea                    textAreaSafeBoxHash;

   private BTextArea                    textAreaPow;

   private BTextField                   textFee;

   private BTextField                   textPayload;

   private BTextField                   textBlockAgePascal;

   private BTextField                   textVolume;

   private BTextField                   textName;

   private BTextField                   textBlockNumFind;

   private BTextField                   textNumOperations;

   private BTextField                   textVersion;

   private BTextField                   textTarget;

   private BTextField                   textTimestamp;

   private BButton                      butRandom;

   private BButton                      butDeviation;

   private BTextField                   textDeviation;

   private BButton                      butOpenOwnWindow;

   private BButton                      butLast;

   public PanelBlockDetails(PascalSwingCtx psc, IRootTabPane root) {
      this(psc, root, ID);
   }

   public PanelBlockDetails(PascalSwingCtx psc, IRootTabPane root, String id) {
      super(psc, id);
      this.root = root;
      this.setLayout(new BorderLayout());
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butClear) {
         cmdClear();
      } else if (src == butNext) {
         Integer block = root.getBlockNext(getAccountAsInt());
         textBlock.setText(block.toString());
      } else if (src == butPrev) {
         Integer block = root.getBlockPrev(getAccountAsInt());
         textBlock.setText(block.toString());
      } else if (src == butLast) {
         Integer block = root.getBlockLast();
         textBlock.setText(block.toString());
      } else if (src == butRandom) {
         int lastValidBlock = root.getBlockLast().intValue();
         Integer block = psc.getUCtx().getRandom().nextInt(lastValidBlock);
         textBlock.setText(block.toString());
      } else if (src == butCopyBlockPayload) {
         String value = textBlock.getText() + "-" + textPayload.getText();
         psc.copyToClipboard(value, "Block Payload");
      } else if (src == butSafeBoxHash) {
         String value = textAreaSafeBoxHash.getText();
         psc.copyToClipboard(value, "Safebox Hash");
      } else if (src == butDeviation) {
         String value = textDeviation.getText();
         psc.copyToClipboard(value, "Block Deviation");
      } else if (src == butPow) {
         String value = textAreaPow.getText();
         psc.copyToClipboard(value, "Public key");
      } else if (src == butOpenOwnWindow) {
         if (block != null) {
            PanelBlockDetails details = new PanelBlockDetails(psc, root);
            details.setBlock(block);
            psc.getSwingCtx().showInNewFrame(details);
         }
      }
   }

   /**
    * 
    */
   public void changedUpdate(DocumentEvent e) {
      //System.out.println("changedUpdate");
      blockUpdate();
   }

   private void cmdClear() {
      textBlockNumFind.setText("");

      textBlock.setText("");
      textTimestamp.setText("");
      textAreaSafeBoxHash.setText("");
      textAreaPow.setText("");
      textFee.setText("");
      textPayload.setText("");
      textBlockAgePascal.setText("");
      textDeviation.setText("");
      textName.setText("");
      textNumOperations.setText("");
      textTimestamp.setText("");
      textVolume.setText("");
      accountOperations.clear();
   }

   public void cmdRefresh(Object source) {
      if (source == docRefresherBlockNumber) {
         newBlockStringTyped(textBlock.getText());
      }
   }

   public void disposeTab() {

   }

   public Integer getAccountAsInt() {
      String str = textBlock.getText();

      return Integer.parseInt(str);
   }

   private String getPrefKeyBlock() {
      return IPrefsPascalSwing.UI_EXPLORER_BLOCK + getTabInternalID();
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

      butNext = new BButton(sc, this, "but.next.block");
      butPrev = new BButton(sc, this, "but.previous.block");
      butRandom = new BButton(sc, this, "but.random.block");
      butLast = new BButton(sc, this, "but.block.last");
      butOpenOwnWindow = new BButton(sc, this, "but.open.window");

      Icon icon = getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
      Icon iconSel = getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);

      //if icon is null.. use app icons
      if (icon == null) {
         icon = sc.getResIcon("default", "main", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
         icon = sc.getResIcon("default", "main", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);
      }

      butTopLeft = new BButton(sc, icon, iconSel);

      container.add("", butTopLeft);

      labAccount = new BLabel(sc, "text.block");
      container.add("p", labAccount);
      textBlock = new JTextField(11);
      String strKey = getPrefKeyBlock();
      int acs = psc.getPascPrefs().getInt(strKey, 0);
      textBlock.setText(String.valueOf(acs));

      docRefresherBlockNumber = new DocRefresher(sc, this);
      container.add("tab", textBlock);
      textBlock.getDocument().addDocumentListener(docRefresherBlockNumber);
      psc.setIntFilter(textBlock);

      container.add("tab", butPrev);
      container.add("tab", butNext);
      container.add("tab", butRandom);
      container.add("tab", butLast);
      container.add("tab", butOpenOwnWindow);

      //-------------------------

      labTimestamp = new BLabel(sc, "text.timestamp");
      container.add("br", labTimestamp);
      textTimestamp = new BTextField(sc, 40);
      textTimestamp.setEnabled(false);
      container.add("tab", textTimestamp);

      // Pascal AGE -----------------------

      labBlockAgePascal = new BLabel(sc, "text.pascalage");
      textBlockAgePascal = new BTextField(sc, 30);
      textBlockAgePascal.setEditable(false);
      container.add("br", labBlockAgePascal);
      container.add("tab", textBlockAgePascal);

      //-------------------------

      butCopyBlockPayload = new BButton(sc, this, "text.payload");
      textPayload = new BTextField(sc, 55);
      textPayload.setEnabled(false);

      container.add("br", butCopyBlockPayload);
      container.add("tab", textPayload);

      //-------------------------

      labNumOperation = new BLabel(sc, "text.operationsnum");
      textNumOperations = new BTextField(sc, 10);
      textNumOperations.setEditable(false);
      container.add("br", labNumOperation);
      container.add("tab", textNumOperations);

      //VOLUME and FEE -----------------------

      labVolume = new BLabel(sc, "text.volume");
      textVolume = new BTextField(sc, 10);
      textVolume.setEditable(false);

      container.add("br", labVolume);
      container.add("tab", textVolume);

      labFee = new BLabel(sc, "text.blockfee");
      textFee = new BTextField(sc, 10);
      textFee.setEditable(false);

      container.add("tab", labFee);
      container.add("tab", textFee);

      //-----------------------

      labVersion = new BLabel(sc, "text.version");
      container.add("br", labVersion);
      textVersion = new BTextField(sc, 10);
      textVersion.setEditable(false);
      container.add("tab", textVersion);

      labTarget = new BLabel(sc, "text.target");
      container.add("tab", labTarget);
      textTarget = new BTextField(sc, 15);
      textTarget.setEditable(false);
      container.add("tab", textTarget);

      butPow = new BButton(sc, this, "text.pow");
      textAreaPow = new BTextArea(sc, 1, 100);
      textAreaPow.setLineWrap(true);
      textAreaPow.setEditable(false);
      textAreaPow.setTextKeyTip("text.pow.tip");

      container.add("p", butPow);
      container.add("tab", textAreaPow);

      butSafeBoxHash = new BButton(sc, this, "text.safeboxhash");
      textAreaSafeBoxHash = new BTextArea(sc, 1, 130);
      textAreaSafeBoxHash.setLineWrap(true);
      textAreaSafeBoxHash.setEditable(false);
      textAreaSafeBoxHash.setTextKeyTip("text.safeboxhash.tip");

      container.add("p", butSafeBoxHash);
      container.add("tab", textAreaSafeBoxHash);

      //-----------------------

      butDeviation = new BButton(sc, this, "text.deviation");
      textDeviation = new BTextField(sc, 100);
      textDeviation.setEnabled(false);
      container.add("br", butDeviation);
      container.add("tab", textDeviation);

      container.add("p hfill", accountOperations);

      JScrollPane scrollPaneAll = new JScrollPane(container);
      this.add(scrollPaneAll, BorderLayout.CENTER);

   }

   public void insertUpdate(DocumentEvent e) {
      //System.out.println("insertUpdate");
      blockUpdate();
   }

   /**
    * 
    * @param text
    */
   public void newBlockStringTyped(String text) {
      if (text == null || text.equals("")) {
         return;
      }
      AddressValidationResult av = psc.getPCtx().getAddressValidator().validate(text);
      if (av.isValid()) {
         Integer iac = av.getAccount();
         psc.getPascPrefs().putInt(getPrefKeyBlock(), iac.intValue());
         setBlock(iac);
      } else {
         //#debug
         toDLog().pFlow("", null, PanelAccountDetails.class, "newAccountStringTyped", ITechLvl.LVL_04_FINER, true);
         psc.getLog().consoleLogError(av.getMessage());
      }
   }

   public void removeUpdate(DocumentEvent e) {
      //System.out.println("removeUpdate");
      blockUpdate();
   }

   public void setBlock(Block block) {
      initCheck();

      DateFormat df = psc.getFormatDateTime();
      this.block = block;

      textBlock.getText();
      String newText = block.getBlock() + "";

      if (!textBlock.getText().equals(newText)) {
         docRefresherBlockNumber.setEnabled(false);
         textBlock.setText(newText);
         docRefresherBlockNumber.setEnabled(true);
      }

      textPayload.setText(block.getPayload());

      PascalCoinDouble fee = new PascalCoinDouble(psc.getPCtx(), block.getFee());
      textFee.setText(fee.getString());

      textNumOperations.setTextIntegerNullVoid(block.getOperationCount());

      RPCConnection con = psc.getPCtx().getRPCConnection();
      if (con.isConnected()) {
         int lastBlockInt = con.getLastBlockMined().intValue();
         int ageDiff = lastBlockInt - block.getBlock();
         String timeAgo = psc.getPascalSwingUtils().computeTimeFromBlockAgePascalTime(ageDiff);
         textBlockAgePascal.setText(ageDiff + " : " + timeAgo);
      } else {
         textBlockAgePascal.setText("Not connected");
      }

      Long timestamp = block.getTimestamp();

      String timeStr = "null";
      if (timestamp != null) {
         Date dateUnit = psc.getDateUnit(timestamp.longValue());
         timeStr = psc.getFormatDateTime().format(dateUnit);
      }

      textTimestamp.setTextStringNullVoid(timeStr);

      textTarget.setTextIntegerNullVoid(block.getCompactTarget());
      textVersion.setTextIntegerNullVoid(block.getAvailableVersion());

      textDeviation.setText(block.getSafeBoxHash());

      textAreaPow.setText(block.getProofOfWork());
      textAreaSafeBoxHash.setText(block.getEncPubKey());

   }

   public void setBlock(Integer account) {
      Block ac = psc.getPascalClient().getBlock(account);
      setBlock(ac);
   }

   /**
    * 
    * @param account
    */
   public void setBlockExternal(Block account) {
      setBlockExternal(account.getBlock());
   }

   public void setBlockExternal(Integer account) {
      initCheck();
      textBlock.setText(account.toString());
   }

   /**
    * 
    */
   public void tabGainFocus() {
      sc.executeLaterInUIThread(new Runnable() {
         public void run() {
            if (textPayload.getText() == null || textPayload.getText().equals("")) {
               newBlockStringTyped(textBlock.getText());
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
      dc.root(this, PanelBlockDetails.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, PanelBlockDetails.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

   /**
    * Wait a few microseconds to make sure the user has finished typing.
    * 
    */
   public void blockUpdate() {
      newBlockStringTyped(textBlock.getText());
   }

}
