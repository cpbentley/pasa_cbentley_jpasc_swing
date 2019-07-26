/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.menu;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_T;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ITechPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.interfaces.ITechUserMode;
import pasa.cbentley.jpasc.swing.interfaces.IWizardNoob;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.BackForwardTabPage;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.menu.MenuNavigate;
import pasa.cbentley.swing.menu.MenuWindow;
import pasa.cbentley.swing.widgets.b.BMenu;
import pasa.cbentley.swing.widgets.b.BMenuItem;
import pasa.cbentley.swing.widgets.b.BRadioButtonMenuItem;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Main menu bar for the main frame.
 * @author Charles Bentley
 *
 */
public class MenuBarPascalMain extends MenuBarPascalAbstract implements ActionListener, IMyGui, MenuListener {
   private JMenuItem            jmiAbout;

   private JRadioButtonMenuItem jmiAccountCaching;

   private JMenuItem            jmiClear;

   private JMenuItem            jmiContents;

   private JRadioButtonMenuItem jmiDE;

   private BMenuItem            jmiDebugGuiUpdate;

   private JRadioButtonMenuItem jmiDebugMissing;

   private BMenuItem            jmiDebugRevalidateTree;

   private JRadioButtonMenuItem jmiEffectsNone;

   private JRadioButtonMenuItem jmiEffectsOriginal;

   private JRadioButtonMenuItem jmiES;

   private JMenuItem            jmiExit;

   private JRadioButtonMenuItem jmiFR;

   private BMenuItem            jmiHelpTranslate;

   private JRadioButtonMenuItem jmiIconClassic;

   private JMenuItem            jmiIconContribute;

   private JRadioButtonMenuItem jmiIconGems;

   private JRadioButtonMenuItem jmiIconNone;

   private BRadioButtonMenuItem jmiLogAllTranslation;

   private JMenuItem            jmiNoobWizard;

   private JMenuItem            jmiPrint;

   private JRadioButtonMenuItem jmiRU;

   private JRadioButtonMenuItem jmiSoundClassic;

   private JMenuItem            jmiSoundContribute;

   private JRadioButtonMenuItem jmiSoundNone;

   private JMenuItem            jmiSoundVolume;

   private BMenuItem            jmiToStringRoot;

   private BMenuItem            jmiToStringTab;

   private JRadioButtonMenuItem jmiUS;

   private JMenu                menuDebug;

   private JMenu                menuEdit;

   private JMenu                menuFile;

   private JMenu                menuFullscreenTo;

   private JMenu                menuHelp;

   private JButton              menuItemBack;

   private JButton              menuItemFoward;

   private JMenuItem            menuItemPayload;

   private JMenuItem            menuItemPlainSS;

   private JMenuItem            menuItemSecureSS;

   private BMenuItem            menuItemToggleTabText;

   private ArrayList<BMenuItem> menuItemWizards;

   private JMenu                menuLanguage;

   private JMenu                menuMaxTo;

   private JMenu                menuMidTo;

   private BMenu                menuMode;

   private JMenu                menuNav;

   private MenuNavigate         menuNavigate;

   private JMenu                menuSettings;

   private JMenu                menuTools;

   private BMenu                menuToString;

   private MenuWindow           menuWindow;

  
   private BRadioButtonMenuItem modeDev;

   private BRadioButtonMenuItem modeExpert;

   private BRadioButtonMenuItem modeNormal;

   private BRadioButtonMenuItem modeRookie;

  
   private SwingCtx             sc;

   public MenuBarPascalMain(PascalSwingCtx psc, CBentleyFrame owner) {
      super(psc, owner);
      sc = psc.getSwingCtx();

      buildMenuFile(sc);

      buildMenuEdit(sc);

      BackForwardTabPage bfManager = psc.getBackForwardTabPage();

      addBackForwardButtons(bfManager);

      buildMenuLanguage(sc);

      buildMenuHelp(sc);

      buildMenuTools();

      buildMenuSettings(sc);

      menuNavigate = new MenuNavigate(sc);
      menuWindow = new MenuWindow(sc);

      this.add(menuFile);
      this.add(menuEdit);

      //TODO implement them :)
      //this.add(menuTools);

      this.add(psc.getPascalSkinManager().getRootMenu());
      this.add(menuLanguage);
      this.add(menuSettings);

      if (psc.isModeAboveRookie()) {
         this.add(menuNavigate);
         this.add(menuWindow);
      }

      this.add(menuHelp);

      if (psc.isModeDev()) {
         buildMenuDebug();
         this.add(menuDebug);
      }
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      //Help
      if (src == jmiAbout) {
         psc.getCmds().cmdShowAbout(owner);
      } else if (src == jmiContents) {
         psc.getCmds().cmdShowContents();
      }
      //File
      if (src == jmiExit) {
         psc.getCmds().cmdExit();
      } else if (src == jmiPrint) {
         psc.getCmds().cmdPrint();
      }
      /// Language
      else if (src == jmiUS) {
         psc.getCmds().cmdChangeLanguage("en", "US");
      } else if (src == jmiFR) {
         psc.getCmds().cmdChangeLanguage("fr", "FR");
      } else if (src == jmiRU) {
         psc.getCmds().cmdChangeLanguage("ru", "RU");
      } else if (src == jmiES) {
         psc.getCmds().cmdChangeLanguage("es", "ES");
      } else if (src == jmiDE) {
         psc.getCmds().cmdChangeLanguage("de", "DE");
      } else if (src == jmiDebugMissing) {
         psc.getCmds().cmdToggleDebugMissingTranslations();
      } else if (src == jmiHelpTranslate) {
         psc.getCmds().cmdHelpTranslate();
      }
      //Settings
      else if (src == jmiSoundNone) {
         psc.getCmds().cmdToggleSounds(ITechPrefsPascalSwing.PREF_PLAY_SOUND_0_NONE);
      } else if (src == jmiSoundClassic) {
         psc.getCmds().cmdToggleSounds(ITechPrefsPascalSwing.PREF_PLAY_SOUND_1_CLASSIC);
      } else if (src == jmiIconClassic) {
         psc.applyIconSettings(ITechPrefsPascalSwing.PREF_TAB_ICONS_1_CLASSIC);
         psc.guiUpdate();
      } else if (src == jmiIconGems) {
         psc.applyIconSettings(ITechPrefsPascalSwing.PREF_TAB_ICONS_2_GEMS);
         psc.guiUpdate();
      } else if (src == jmiIconNone) {
         psc.applyIconSettings(ITechPrefsPascalSwing.PREF_TAB_ICONS_0_NONE);
         psc.guiUpdate();
      } else if (src == jmiEffectsNone) {
         psc.getCmds().cmdToggleCellEffects(ITechPrefsPascalSwing.PREF_EFFECTS_0_NONE);
         psc.guiUpdate();
      } else if (src == jmiEffectsOriginal) {
         psc.getCmds().cmdToggleCellEffects(ITechPrefsPascalSwing.PREF_EFFECTS_1_ORIGINAL);
         psc.guiUpdate();
      } else if (src == jmiSoundVolume) {
         psc.getCmds().cmdChangeVolume();
      } else if (src == jmiAccountCaching) {
         psc.getCmds().cmdToggleAccountCaching(jmiAccountCaching.isSelected());
      }
      // Mode change
      else if (src == modeDev) {
         psc.getCmds().cmdChangeMode(ITechUserMode.MODE_0_DEV);
      } else if (src == modeRookie) {
         psc.getCmds().cmdChangeMode(ITechUserMode.MODE_1_ROOKIE);
      } else if (src == modeNormal) {
         psc.getCmds().cmdChangeMode(ITechUserMode.MODE_2_NORMAL);
      } else if (src == modeExpert) {
         psc.getCmds().cmdChangeMode(ITechUserMode.MODE_3_EXPERT);
      }

      ///DEBUG
      else if (src == jmiToStringRoot) {
         //#debug
         psc.toDLog().pAlways(psc.toStringAll(), null, MenuBarPascalMain.class, "actionPerformed", ITechLvl.LVL_05_FINE, false);
      } else if (src == jmiToStringTab) {
         IMyTab focusedTab = sc.getFocusedTab();
         //#debug
         psc.toDLog().pAlways("Debug Focused tab", focusedTab, MenuBarPascalMain.class, "actionPerformed", ITechLvl.LVL_05_FINE, false);
      } else if (src == jmiDebugGuiUpdate) {
         sc.guiUpdate();
      } else if (src == jmiDebugRevalidateTree) {
         sc.revalidateSwingTree();
      } else {
         int index = -1;
         if ((index = menuItemWizards.indexOf(src)) != -1) {
            IWizardNoob wizard = psc.getWizards().get(index);
            wizard.cmdShowNoobWizard(this);
         }
      }
   }

   private void addBackForwardButtons(BackForwardTabPage bfManager) {
      menuItemBack = new JButton(bfManager.getActionBack());
      menuItemBack.addActionListener(this);

      //      menuItemBack.setMinimumSize(menuFile.getSize());
      //      menuItemBack.setPreferredSize(menuFile.getSize());
      //      menuItemBack.setMaximumSize(menuFile.getSize());
      //      menuItemBack.setActionCommand("ActionText");

      menuItemFoward = new JButton(bfManager.getActionForward());
      menuItemFoward.addActionListener(this);

      //      menuItemFoward.setMinimumSize(menuFile.getSize());
      //      menuItemFoward.setPreferredSize(menuFile.getSize());
      //      menuItemFoward.setMaximumSize(menuFile.getSize());
      //      menuItemFoward.setActionCommand("ActionText");

      this.add(menuItemBack);
      this.add(menuItemFoward);
   }

   public void applyMode(IPrefs prefs) {
      int modeID = prefs.getInt(ITechPrefsPascalSwing.PREF_MODE, ITechUserMode.MODE_1_ROOKIE);
      switch (modeID) {
         case ITechUserMode.MODE_1_ROOKIE:
            modeRookie.setSelected(true);
            break;
         case ITechUserMode.MODE_2_NORMAL:
            modeNormal.setSelected(true);
            break;
         case ITechUserMode.MODE_3_EXPERT:
            modeExpert.setSelected(true);
            break;
         default:
            modeDev.setSelected(true);
            break;
      }
   }

   /**
    * Match the preferences with the right radiobuttons.
    * Read the 
    * @param prefs
    */
   public void applyPref(IPrefs prefs) {
      int soundID = prefs.getInt(ITechPrefsPascalSwing.PREF_PLAY_SOUND, 0);
      if (soundID == ITechPrefsPascalSwing.PREF_PLAY_SOUND_0_NONE) {
         jmiSoundNone.setSelected(true);
      } else if (soundID == ITechPrefsPascalSwing.PREF_PLAY_SOUND_1_CLASSIC) {
         jmiSoundClassic.setSelected(true);
      }

      int iconsID = prefs.getInt(ITechPrefsPascalSwing.PREF_TAB_ICONS, 0);
      if (iconsID == ITechPrefsPascalSwing.PREF_TAB_ICONS_0_NONE) {
         jmiIconNone.setSelected(true);
      } else if (iconsID == ITechPrefsPascalSwing.PREF_TAB_ICONS_1_CLASSIC) {
         jmiIconClassic.setSelected(true);
      } else if (iconsID == ITechPrefsPascalSwing.PREF_TAB_ICONS_2_GEMS) {
         jmiIconGems.setSelected(true);
      }

      int rowEffectsID = prefs.getInt(ITechPrefsPascalSwing.PREF_EFFECTS, 0);
      if (rowEffectsID == ITechPrefsPascalSwing.PREF_EFFECTS_0_NONE) {
         jmiEffectsNone.setSelected(true);
      } else if (rowEffectsID == ITechPrefsPascalSwing.PREF_EFFECTS_1_ORIGINAL) {
         jmiEffectsOriginal.setSelected(true);
      }

      jmiDebugMissing.setSelected(prefs.getBoolean(ITechPrefsPascalSwing.PREF_DEBUG_TRANSLATION, true));

      applyMode(prefs);

      psc.getCmds().cmdToggleSounds(soundID);

   }

   private void buildMenuDebug() {

      menuDebug = new BMenu(sc, "menu.debug");

      jmiDebugGuiUpdate = new BMenuItem(sc, this, "menu.debug.guiupdate");
      jmiDebugGuiUpdate.setMnemonic(VK_G);
      jmiDebugGuiUpdate.setAccelerator(KeyStroke.getKeyStroke(VK_G, modCtrlShift));

      jmiDebugRevalidateTree = new BMenuItem(sc, this, "menu.debug.revalidate");
      jmiDebugRevalidateTree.setMnemonic(VK_R);
      jmiDebugRevalidateTree.setAccelerator(KeyStroke.getKeyStroke(VK_R, modCtrlShift));

      menuToString = new BMenu(sc, "menu.tostring");
      menuToString.setMnemonic(VK_S);

      jmiToStringRoot = new BMenuItem(sc, this, "menu.debug.tostring.root");
      jmiToStringRoot.setMnemonic(VK_A);
      jmiToStringRoot.setAccelerator(KeyStroke.getKeyStroke(VK_A, modCtrlAltShift));
      menuToString.add(jmiToStringRoot);

      jmiToStringTab = new BMenuItem(sc, this, "menu.debug.tostring.tabcurrent");
      jmiToStringTab.setMnemonic(VK_T);
      jmiToStringTab.setAccelerator(KeyStroke.getKeyStroke(VK_T, modCtrlAltShift));
      menuToString.add(jmiToStringTab);

      menuDebug.add(jmiDebugGuiUpdate);
      menuDebug.add(jmiDebugRevalidateTree);
      menuDebug.add(menuToString);

   }

   private void buildMenuEdit(SwingCtx sc) {
      menuEdit = new JMenu("Edit");
      menuEdit.setMnemonic(VK_E);

      jmiClear = new JMenuItem(sc.getCmds().getClearAction());
      jmiClear.setAccelerator(KeyStroke.getKeyStroke(VK_BACK_SPACE, CTRL_DOWN_MASK));
      menuEdit.add(jmiClear);
   }

   private void buildMenuFile(SwingCtx sc) {
      menuFile = new BMenu(sc, this, "menu.file");
      menuFile.setMnemonic(VK_F);
      jmiExit = new BMenuItem(sc, this, "menu.item.exit");
      jmiExit.setMnemonic(VK_E);
      jmiExit.setAccelerator(KeyStroke.getKeyStroke(VK_Q, CTRL_DOWN_MASK));
      jmiExit.addActionListener(this);

      jmiPrint = new JMenuItem("Print");
      jmiPrint.addActionListener(this);

      menuFile.addSeparator();
      menuFile.add(jmiExit);
      menuFile.add(jmiPrint);
   }

   private void buildMenuHelp(SwingCtx sc) {
      menuHelp = new BMenu(sc, this, "menu.help");
      jmiAbout = new BMenuItem(sc, this, "menu.about");
      jmiContents = new BMenuItem(sc, this, "menu.helpcontent");
      jmiContents.setMnemonic(KeyEvent.VK_F1);

      menuHelp.add(jmiAbout);
      menuHelp.add(jmiContents);

      List<IWizardNoob> wizards = psc.getWizards();
      if (wizards.size() != 0) {
         menuHelp.addSeparator();

         menuItemWizards = new ArrayList<>(wizards.size());

         for (IWizardNoob wizard : wizards) {
            BMenuItem butWizardItem = new BMenuItem(sc, this, wizard.getStringKeyTitle());
            int buttonsIconSize = IconFamily.ICON_SIZE_0_SMALLEST;
            butWizardItem.setIcon(wizard.getID(), "tab", buttonsIconSize);
            menuItemWizards.add(butWizardItem);
         }

      }

      jmiNoobWizard = new JMenuItem("Noob Wizard");
      jmiNoobWizard.addActionListener(this);
      menuHelp.add(jmiNoobWizard);
   }

   protected void buildMenuLanguage(SwingCtx sc) {
      menuLanguage = new BMenu(sc, this, "menu.language");

      //ENGLISH SU
      jmiUS = new JRadioButtonMenuItem("English US");
      jmiUS.setSelected(true);
      jmiUS.addActionListener(this);
      menuLanguage.add(jmiUS);

      //FRANCAIS FR
      jmiFR = new JRadioButtonMenuItem("Français FR");
      jmiFR.addActionListener(this);
      menuLanguage.add(jmiFR);

      //RUSSIAN RU
      jmiRU = new JRadioButtonMenuItem("Русский RU");
      jmiRU.addActionListener(this);
      menuLanguage.add(jmiRU);

      //SPANISH ES
      jmiES = new JRadioButtonMenuItem("Spanish ES");
      jmiES.addActionListener(this);
      menuLanguage.add(jmiES);

      //GERMAN DE
      jmiDE = new JRadioButtonMenuItem("German DE");
      jmiDE.addActionListener(this);
      menuLanguage.add(jmiDE);

      jmiDebugMissing = new BRadioButtonMenuItem(sc, this, "menu.lang.debug");
      jmiLogAllTranslation = new BRadioButtonMenuItem(sc, this, "menu.lang.logall");

      jmiHelpTranslate = new BMenuItem(sc, this, "menu.lang.help");

      menuLanguage.addSeparator();
      menuLanguage.add(jmiDebugMissing);
      menuLanguage.add(jmiLogAllTranslation);
      menuLanguage.addSeparator();
      menuLanguage.add(jmiHelpTranslate);

      ButtonGroup group = new ButtonGroup();
      group.add(jmiUS);
      group.add(jmiFR);
      group.add(jmiRU);
      group.add(jmiES);
      group.add(jmiDE);
   }

   private void buildMenuSettings(SwingCtx sc) {
      menuSettings = new BMenu(sc, "menu.settings");

      JMenu soundTheme = new BMenu(sc, "menu.sound");

      jmiSoundVolume = new BMenuItem(sc, this, "menu.sound.volume");
      soundTheme.add(jmiSoundVolume);

      soundTheme.addSeparator();

      jmiSoundNone = new BRadioButtonMenuItem(sc, this, "menu.sound.none");
      jmiSoundClassic = new BRadioButtonMenuItem(sc, this, "menu.sound.classic");

      soundTheme.add(jmiSoundNone);
      soundTheme.add(jmiSoundClassic);

      soundTheme.addSeparator();

      jmiSoundContribute = new BMenuItem(sc, this, "menu.sound.contribute");
      soundTheme.add(jmiSoundContribute);

      ButtonGroup groupSound = new ButtonGroup();
      groupSound.add(jmiSoundNone);
      groupSound.add(jmiSoundClassic);

      menuSettings.add(soundTheme);

      JMenu tableEffects = new BMenu(sc, "menu.tableffect");
      jmiEffectsNone = new BRadioButtonMenuItem(sc, this, "menu.tableffect.none");
      jmiEffectsOriginal = new BRadioButtonMenuItem(sc, this, "menu.tableffect.original");

      tableEffects.add(jmiEffectsNone);
      tableEffects.add(jmiEffectsOriginal);

      ButtonGroup groupEffects = new ButtonGroup();
      groupEffects.add(jmiEffectsNone);
      groupEffects.add(jmiEffectsOriginal);
      menuSettings.add(tableEffects);

      JMenu icons = new BMenu(sc, "menu.tabicons");

      jmiIconNone = new BRadioButtonMenuItem(sc, this, "menu.tabicons.none");
      jmiIconClassic = new BRadioButtonMenuItem(sc, this, "menu.tabicons.classic");

      icons.add(jmiIconNone);
      icons.add(jmiIconClassic);

      //      jmiIconGems = new JRadioButtonMenuItem("Ying Yang Gems");
      //      jmiIconGems.addActionListener(this);
      //      icons.add(jmiIconGems);

      ButtonGroup groupIcon = new ButtonGroup();
      groupIcon.add(jmiIconNone);
      groupIcon.add(jmiIconClassic);
      //groupIcon.add(jmiIconGems);

      menuItemToggleTabText = new BMenuItem(sc, this, "menu.tab.toggletext");
      jmiIconContribute = new BMenuItem(sc, this, "menu.tabicons.contribute");

      icons.addSeparator();
      icons.add(menuItemToggleTabText);
      icons.addSeparator();
      icons.add(jmiIconContribute);

      menuSettings.add(icons);

      menuMode = new BMenu(sc, "menu.mode");
      modeRookie = new BRadioButtonMenuItem(sc, this, "menu.mode.rookie");
      modeNormal = new BRadioButtonMenuItem(sc, this, "menu.mode.normal");
      modeExpert = new BRadioButtonMenuItem(sc, this, "menu.mode.expert");
      modeDev = new BRadioButtonMenuItem(sc, this, "menu.mode.dev");

      modeRookie.setAccelerator(KeyStroke.getKeyStroke(VK_1, modCtrlShift));
      modeNormal.setAccelerator(KeyStroke.getKeyStroke(VK_2, modCtrlShift));
      modeExpert.setAccelerator(KeyStroke.getKeyStroke(VK_3, modCtrlShift));
      modeDev.setAccelerator(KeyStroke.getKeyStroke(VK_4, modCtrlShift));

      ButtonGroup groupMode = new ButtonGroup();
      groupMode.add(modeRookie);
      groupMode.add(modeNormal);
      groupMode.add(modeExpert);
      groupMode.add(modeDev);

      modeRookie.setSelected(true); //default

      menuMode.add(modeRookie);
      menuMode.add(modeNormal);
      menuMode.add(modeExpert);
      menuMode.add(modeDev);

      menuSettings.add(menuMode);

      jmiAccountCaching = new BRadioButtonMenuItem(sc, this, "menu.radio.accountcaching");

      menuSettings.add(jmiAccountCaching);
   }

   private void buildMenuTools() {
      menuTools = new JMenu("Tools");

      //password list zeus
      menuItemPayload = new JMenuItem("Payload password list");

      menuItemSecureSS = new JMenuItem("Secure Screenshot");
      menuItemSecureSS.setToolTipText("Screenshot of current screen without sensistive information such as balance");

      menuItemPlainSS = new JMenuItem("Plain Screenshot");

      //manages payload passwords

      menuTools.add(menuItemPayload);
      menuTools.addSeparator();
      menuTools.add(menuItemSecureSS);
      menuTools.add(menuItemPlainSS);
   }

   /**
    * Called when language has changed
    */
   public void guiUpdate() {
      SwingCtx sc = psc.getSwingCtx();
      psc.getPascalSkinManager().guiUpdate(sc.getResBund());

      super.guiUpdate();
   }

   public void menuCanceled(MenuEvent e) {
   }

   public void menuDeselected(MenuEvent e) {
   }

   public void menuSelected(MenuEvent e) {
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalMenuBar");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalMenuBar");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

}
