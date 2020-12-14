/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.run;

import java.awt.Dimension;
import java.awt.Image;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.BaseDLogger;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ITechConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.logging.ITechTags;
import pasa.cbentley.jpasc.pcore.ctx.ITechPCore;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.swing.menu.MenuBarPascalDemo;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabLoginConsole;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyWalletSwitch;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.interfaces.IStringPrefIDable;
import pasa.cbentley.swing.utils.PrefIdable;
import pasa.cbentley.swing.window.CBentleyFrame;

public class RunPanelHelperKeyWalletSwitch extends RunPascalSwingAbstract implements IExitable, ICommandableRefresh {


   public static void main(final String[] args) {
      //create runner
      final RunPanelHelperKeyWalletSwitch runner = new RunPanelHelperKeyWalletSwitch();
      runner.initUIThreadOutside();
      //init UI stuff inside AWT thread.
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            runner.initUIThreadInside();
         }
      });
   }

   private PanelHelperKeyWalletSwitch panelHelperKeyWalletSwitch1;

   private PanelHelperKeyWalletSwitch panelHelperKeyWalletSwitch2;

   /**
    * Super constructor initializes all the contexts
    */
   public RunPanelHelperKeyWalletSwitch() {
      super();
   }

   protected void addI18nPascal(List<String> list) {
      list.add("i18nPascalSwingDemo");
   }

   public void cmdRefresh(Object source) {
      //log it in the user console
      if (source instanceof PanelHelperKeyWalletSwitch) {
         PanelHelperKeyWalletSwitch panel = (PanelHelperKeyWalletSwitch) source;
         PublicKey pkNew = panel.getSelectedKeyPublicKey();
         if (pkNew != null) {
            psc.getLog().consoleLog("Selected key is now =" + pkNew.getName() + " " + pkNew.getBase58PubKey());
         } else {
            psc.getLog().consoleLog("Selected key is null");
         }
      }
   }

   protected void initForPrefsPascal(IPrefs prefs) {

   }

   /**
    * Called once inside the GUI thread.
    */
   protected CBentleyFrame initUIThreadInsideSwing() {
      initSkinner();

      frame = new CBentleyFrame(sc, "demo_panelhelper_keywalletswitch");
      //no key
      frame.setTitle("Demo of the PanelHelperKeyWalletSwitch");

      //we don't want auto lock
      pc.getPrefs().putBoolean(ITechPCore.PKEY_AUTO_LOCK, false);

      PanelTabLoginConsole panel = createLoginConsole();

      PrefIdable prefIdable1 = new PrefIdable(sc,"panel1");
      panelHelperKeyWalletSwitch1 = new PanelHelperKeyWalletSwitch(psc, prefIdable1, this);
      panelHelperKeyWalletSwitch1.setKeySelectionEnabled(true);
      panelHelperKeyWalletSwitch1.buildUI();

      PrefIdable prefIdable2 = new PrefIdable(sc,"panel2");
      panelHelperKeyWalletSwitch2 = new PanelHelperKeyWalletSwitch(psc, prefIdable2, this);
      panelHelperKeyWalletSwitch2.setKeySelectionEnabled(true);
      panelHelperKeyWalletSwitch2.buildUI();

      JPanel center = new JPanel();
      center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
      center.add(panelHelperKeyWalletSwitch1);
      center.add(Box.createRigidArea(new Dimension(0, 1)));
      center.add(panelHelperKeyWalletSwitch2);

      panel.setPanelCenter(center);
      //init the tab
      panel.initCheck();

      Image image = psc.createImage("/icons/logo/logo_demo_keys_greenorange_64.png", "");
      frame.setIconImage(image);
      frame.getContentPane().add(panel);

      //set menubar
      MenuBarPascalDemo pascalMenuBarDemo = new MenuBarPascalDemo(psc, frame);
      frame.setJMenuBar(pascalMenuBarDemo);

      return frame;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "RunPanelHelperKeyWalletSwitch");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "RunPanelHelperKeyWalletSwitch");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   /**
    * setup the logger at. sub class may override.
    * Default opens all at finest level
    */
   protected void toStringSetupLogger(UCtx uc) {
      BaseDLogger loggerFirst = (BaseDLogger) uc.toDLog();
      IDLogConfig config = loggerFirst.getDefault().getConfig();
      config.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      config.setFlagTag(ITechTags.FLAG_09_PRINT_FLOW, true);

      config.setFlagPrint(ITechConfig.MASTER_FLAG_08_OPEN_ALL_BUT_FALSE, true);
      //negatives
      config.setFlagTagNeg(ITechTags.FLAG_07_PRINT_EVENT, true);
   }

   //#enddebug

}
