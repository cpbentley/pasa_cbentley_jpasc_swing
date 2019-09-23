/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.ICallBack;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.audio.SoundPlay;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ITechPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.widgets.HelpDialog;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;
import pasa.cbentley.swing.dialogs.JOptionPaneWithSlider;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.window.CBentleyFrame;

public class PascalCmdManager implements IStringable, ICallBack, ITechShow {

   private CmdAccountSendFrom              cmdAccountSendFrom;

   private CmdAccountSendFrom              cmdAccountSendFromWin;

   private CmdAccountSendTo                cmdAccountSendTo;

   private CmdAccountSendTo                cmdAccountSendToWin;

   private CmdShowAccountKey               cmdAccountShowKeyHome;

   private CmdShowAccountKeyNames          cmdAccountShowKeyNames;

   private CmdShowAccountKeyNames          cmdAccountShowKeyNamesWin;

   private CmdShowAccountKey               cmdAccountShowKeyWin;

   private CmdShowBlockOperations          cmdBlockShowOperationsHome;

   private CmdShowBlockOperations          cmdBlockShowOperationsWin;

   private CmdKeyChangeName                cmdKeyChangeName;

   private CmdShowAccountInInspector       cmdShowAccountInInspectorTab;

   private CmdShowAccountInInspector       cmdShowAccountInInspectorWin;

   private CmdShowKeyAccountNames          cmdShowAccountKeyAccounts;

   private CmdShowKeyAccountNames          cmdShowAccountKeyAccountsWin;

   private CmdShowAccountSellerInInspector cmdShowAccountSellerInInspectorTab;

   private CmdShowAccountSellerInInspector cmdShowAccountSellerInInspectorWindow;

   private CmdShowKeyAccounts              cmdShowKeyAccounts;

   private CmdShowKeyAccounts              cmdShowKeyAccountsWin;

   private int                             currentMode;

   private HelpDialog                      frameHelpDialog;

   private boolean                         isPlaySounds;

   private final PascalSwingCtx            psc;

   private CmdTogglePrivacyCtx cmdTogglePrivacyCtx;

   public PascalCmdManager(PascalSwingCtx psc) {
      this.psc = psc;
   }

   public void callBack(Object o) {
      //which call back ?
      if (o instanceof SoundPlay) {
         System.exit(0);
      } else {

         //#debug
         toDLog().pTest("Unknown Class " + o, this, PascalCmdManager.class, "callBack", LVL_05_FINE, true);
         System.exit(0);
      }
   }

   public void cmdChangeLanguage(String lang, String country) {
      psc.getSwingCtx().updateLocale(lang, country);
   }

   public void cmdChangeMode(int mode) {
      if (psc.getMode() != mode) {
         psc.setMode(mode);
      }
   }

   public void cmdChangeVolume() {
      JOptionPaneWithSlider sl = new JOptionPaneWithSlider();
      sl.show(psc.getFrameRoot());
      int val = ((Integer) sl.getInputValue()).intValue();
      psc.getUIPref().putInt(PascalAudio.PREF_SOUND_VOLUME, val);
   }

   
   
   /**
    * Try to connect to the blockchain daemon using defautl login parameters.
    * Must refresh current loaded tab
    */
   public void cmdConnectInitJPascalCoin() {
      psc.getLogin().cmdConnect();
   }

   public void cmdExit() {

      //save pref

      //for each frame, save history
      //TODO later
      psc.getUIPref().putInt(ITechPrefsPascalSwing.PREF_FRAME_NUM, 0);
      //store preference for all running

      psc.cmdExit();

      //wait for sound to play and then exit
      try {
         //wrap in a try in case whatever happens. Audio might be buggy.
         //we have no idea
         psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_BYE, this);
      } catch (Error e) {
         e.printStackTrace();
         doEffectiveExit();
      }

   }

   /**
    * 
    */
   public void cmdFreeMemory() {
      //Dispose non active tabs
      psc.getRootPageManager().getRoot().disposeTab();
   }

   public void cmdHelpTranslate() {
      //#debug
      toDLog().pFlow("", null, PascalCmdManager.class, "cmdHelpTranslate", ITechLvl.LVL_04_FINER, true);

   }

   public void cmdPrefsClear() {
      psc.getSwingCtx().getPrefs().clear();
   }

   public void cmdPrefsExport() {
      //just export
      String path = System.getProperty("user.dir");
      JFileChooser fc = new JFileChooser(path);
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fc.showOpenDialog(null);
      File file = fc.getSelectedFile();
      String format = "yyyy-MM-dd_HH-mm";
      DateFormat df = new SimpleDateFormat(format);
      String dataf = df.format(new Date(System.currentTimeMillis()));
      String name = "pasc_java_swing_wallet_tools_prefs_" + dataf + ".xml";
      final File destFile = new File(file, name);
      Thread runner = new Thread(new Runnable() {
         public void run() {
            try {
               FileOutputStream fis = new FileOutputStream(destFile);
               psc.getUIPref().export(fis);
               fis.close();
            } catch (Exception e) {
               Toolkit.getDefaultToolkit().beep();
            }
         }
      });
      runner.start();

   }

   public void cmdPrefsImport() {
      String path = System.getProperty("user.dir");
      JFileChooser fc = new JFileChooser(path);
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fc.showOpenDialog(null);
      File file = fc.getSelectedFile();
      try {
         FileInputStream fis = new FileInputStream(file);
         Preferences.importPreferences(fis);
         fis.close();
      } catch (Exception e) {
         psc.getLog().consoleLogError("Could not load preference file " + file.getAbsolutePath());
         Toolkit.getDefaultToolkit().beep();
      }
   }

   /**
    * Prints currently active tab
    */
   public void cmdPrint() {
      if (psc.isModeDev()) {
         //unless dev
         cmdPrint(psc.getSwingCtx().getFocusedTab());
      } else {
         //show under construction animation dialog
      }
   }

   public void cmdPrint(IMyTab tab) {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPrintable(new PrintableTab(psc.getSwingCtx(), tab));
      boolean ok = job.printDialog();
      if (ok) {
         try {
            job.print();
         } catch (PrinterException ex) {
            /* The job did not successfully complete */
         }
      }
   }

   public void cmdShowAbout(CBentleyFrame owner) {
      JOptionPane.showMessageDialog(owner, "Pascal Coin Java Swing by Charles Bentley");
   }

   public void cmdShowContents() {
      if (frameHelpDialog == null) {
         frameHelpDialog = new HelpDialog(psc.getSwingCtx(), psc.getFrameRoot());
         psc.positionNewFrame(frameHelpDialog);
      }
      frameHelpDialog.setVisible(true);
   }

   /**
    * Displays the Help Content with given page selected
    * @param page
    */
   public void cmdShowContents(String page) {
      cmdShowContents();
      frameHelpDialog.displayPage(page);
   }

   public void cmdToggleAccountCaching(boolean selected) {
      psc.getPCtx().getRPCConnection().setEnableCaching(selected);

      //#debug
      toDLog().pCmd("" + selected, psc.getPCtx().getRPCConnection(), PascalCmdManager.class, "cmdToggleAccountCaching", ITechLvl.LVL_05_FINE, false);
   }

   public void cmdToggleCellEffects(int effectID) {
      psc.getUIPref().putInt(ITechPrefsPascalSwing.PREF_EFFECTS, effectID);
      psc.setCellEffect(effectID);
      if (effectID == ITechPrefsPascalSwing.PREF_EFFECTS_0_NONE) {
         psc.getLog().consoleLog("Table cells effects disabled");
      } else {
         psc.getLog().consoleLog("Table cells effects enabled");
      }
   }

   public void cmdToggleDebugMissingTranslations() {
      //#debug
      toDLog().pFlow("", null, PascalCmdManager.class, "cmdToggleDebugMissingTranslations", ITechLvl.LVL_04_FINER, true);
   }

   /**
    * 
    * @param value
    */
   public void cmdToggleSounds(int value) {
      psc.getUIPref().putInt(ITechPrefsPascalSwing.PREF_PLAY_SOUND, value);
      boolean isPlaySounds = value != ITechPrefsPascalSwing.PREF_PLAY_SOUND_0_NONE;
      psc.getAudio().setEnableAudio(isPlaySounds);
      if (isPlaySounds) {
         psc.getLog().consoleLog("Audio enabled");
      } else {
         psc.getLog().consoleLog("Audio disabled");
      }
   }

   public void cmdToggleTabIcons(int value) {
      psc.getUIPref().putInt(ITechPrefsPascalSwing.PREF_TAB_ICONS, value);
      psc.applyIconSettings(value);
      psc.getSwingCtx().guiUpdate();
      boolean isIconsShown = value != ITechPrefsPascalSwing.PREF_TAB_ICONS_0_NONE;
      if (isIconsShown) {
         psc.getLog().consoleLog("Tab Icons enabled");
      } else {
         psc.getLog().consoleLog("Tab Icons disabled");
      }
   }

   private void doEffectiveExit() {
      System.exit(0);
   }

   public CmdAccountSendFrom getCmdAccountSendFrom() {
      if (cmdAccountSendFrom == null) {
         cmdAccountSendFrom = new CmdAccountSendFrom(this, SHOW_TYPE_0_HOME);
      }
      return cmdAccountSendFrom;
   }

   public CmdAccountSendFrom getCmdAccountSendFromWin() {
      if (cmdAccountSendFromWin == null) {
         cmdAccountSendFromWin = new CmdAccountSendFrom(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdAccountSendFromWin;
   }

   public CmdAccountSendTo getCmdAccountSendTo() {
      if (cmdAccountSendTo == null) {
         cmdAccountSendTo = new CmdAccountSendTo(this, SHOW_TYPE_0_HOME);
      }
      return cmdAccountSendTo;
   }

   public CmdAccountSendTo getCmdAccountSendToWin() {
      if (cmdAccountSendToWin == null) {
         cmdAccountSendToWin = new CmdAccountSendTo(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdAccountSendToWin;
   }

   public CmdShowAccountKey getCmdAccountShowKey() {
      if (cmdAccountShowKeyHome == null) {
         cmdAccountShowKeyHome = new CmdShowAccountKey(this, SHOW_TYPE_0_HOME);
      }
      return cmdAccountShowKeyHome;
   }

   public CmdShowAccountKeyNames getCmdAccountShowKeyNames() {
      if (cmdAccountShowKeyNames == null) {
         cmdAccountShowKeyNames = new CmdShowAccountKeyNames(this, SHOW_TYPE_0_HOME);
      }
      return cmdAccountShowKeyNames;
   }

   public CmdShowAccountKeyNames getCmdAccountShowKeyNamesWin() {
      if (cmdAccountShowKeyNamesWin == null) {
         cmdAccountShowKeyNamesWin = new CmdShowAccountKeyNames(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdAccountShowKeyNamesWin;
   }

   public CmdShowAccountKey getCmdAccountShowKeyWin() {
      if (cmdAccountShowKeyWin == null) {
         cmdAccountShowKeyWin = new CmdShowAccountKey(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdAccountShowKeyWin;
   }

   public CmdShowBlockOperations getCmdBlockShowOperationsHome() {
      if (cmdBlockShowOperationsHome == null) {
         cmdBlockShowOperationsHome = new CmdShowBlockOperations(this, SHOW_TYPE_0_HOME);
      }
      return cmdBlockShowOperationsHome;
   }

   public CmdShowBlockOperations getCmdBlockShowOperationsWin() {
      if (cmdBlockShowOperationsWin == null) {
         cmdBlockShowOperationsWin = new CmdShowBlockOperations(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdBlockShowOperationsWin;
   }

   public CmdKeyChangeName getCmdKeyChangeName() {
      if (cmdKeyChangeName == null) {
         cmdKeyChangeName = new CmdKeyChangeName(this);
      }
      return cmdKeyChangeName;
   }

   public CmdShowAccountInInspector getCmdShowAccountInInspectorTab() {
      if (cmdShowAccountInInspectorTab == null) {
         cmdShowAccountInInspectorTab = new CmdShowAccountInInspector(this, SHOW_TYPE_0_HOME);
      }
      return cmdShowAccountInInspectorTab;
   }

   public CmdShowAccountInInspector getCmdShowAccountInInspectorWin() {
      if (cmdShowAccountInInspectorWin == null) {
         cmdShowAccountInInspectorWin = new CmdShowAccountInInspector(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdShowAccountInInspectorWin;
   }

   public CmdSwingAbstract<ICommandableAccount> getCmdShowAccountSellerInInspectorTab() {
      if (cmdShowAccountSellerInInspectorTab == null) {
         cmdShowAccountSellerInInspectorTab = new CmdShowAccountSellerInInspector(this, SHOW_TYPE_0_HOME);
      }
      return cmdShowAccountSellerInInspectorTab;
   }
   
   public CmdTogglePrivacyCtx getCmdTogglePrivacyCtx() {
      if(cmdTogglePrivacyCtx==null) {
         cmdTogglePrivacyCtx = new CmdTogglePrivacyCtx(psc);
      }
      return cmdTogglePrivacyCtx;
   }

   public CmdSwingAbstract<ICommandableAccount> getCmdShowAccountSellerInInspectorWin() {
      if (cmdShowAccountSellerInInspectorWindow == null) {
         cmdShowAccountSellerInInspectorWindow = new CmdShowAccountSellerInInspector(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdShowAccountSellerInInspectorWindow;
   }

   public CmdShowKeyAccounts getCmdShowKeyAccounts() {
      if (cmdShowKeyAccounts == null) {
         cmdShowKeyAccounts = new CmdShowKeyAccounts(this, SHOW_TYPE_0_HOME);
      }
      return cmdShowKeyAccounts;
   }

   public CmdShowKeyAccounts getCmdShowKeyAccountsWin() {
      if (cmdShowKeyAccountsWin == null) {
         cmdShowKeyAccountsWin = new CmdShowKeyAccounts(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdShowKeyAccountsWin;
   }

   public CmdShowKeyAccountNames getCmdShowKeyNames() {
      if (cmdShowAccountKeyAccounts == null) {
         cmdShowAccountKeyAccounts = new CmdShowKeyAccountNames(this, SHOW_TYPE_0_HOME);
      }
      return cmdShowAccountKeyAccounts;
   }

   public CmdShowKeyAccountNames getCmdShowKeyNamesWin() {
      if (cmdShowAccountKeyAccountsWin == null) {
         cmdShowAccountKeyAccountsWin = new CmdShowKeyAccountNames(this, SHOW_TYPE_1_NEW_WIN);
      }
      return cmdShowAccountKeyAccountsWin;
   }

   public String getKeyInNewWindowCap() {
      return "cmd.show.newwindow.cap";
   }

   public String getKeyInTabCap() {
      return "cmd.show.tab.cap";
   }
   
   public String getKeyInNewWindow() {
      return "cmd.new.window";
   }

   public String getKeyInTab() {
      return "cmd.tab";
   }

   public PascalSwingCtx getPSC() {
      return psc;
   }

   public String getStringFromKey(String key) {
      return psc.getSwingCtx().getResString(key);
   }

   //#mdebug
   public IDLog toDLog() {
      return psc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalCmdManager");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalCmdManager");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug
}
