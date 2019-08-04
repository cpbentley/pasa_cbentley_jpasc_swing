/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.BaseDLogger;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IConfig;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.others.CentralLogger;
import pasa.cbentley.jpasc.swing.others.PascalSkinManager;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabConsole;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabLogin;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabLoginConsole;
import pasa.cbentley.swing.SwingPrefs;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.gif.ctx.SwingGifCtx;
import pasa.cbentley.swing.images.ctx.ImgCtx;
import pasa.cbentley.swing.run.RunSwingAbstract;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * 
 * @author Charles Bentley
 *
 */
public abstract class RunPascalSwingAbstract extends RunSwingAbstract implements IExitable, IStringable {

   protected final SwingGifCtx    gifc;

   protected final ImgCtx         imgc;

   protected final PCoreCtx       pc;

   protected final PascalSwingCtx psc;

   public RunPascalSwingAbstract() {
      this.imgc = new ImgCtx(sc);
      this.gifc = new SwingGifCtx(sc, imgc);
      this.pc = new PCoreCtx(uc, c5);
      this.psc = new PascalSwingCtx(pc, sc, gifc);
   }

   /**
    * Final. Force the use of {@link RunPascalSwingAbstract#addI18nPascal(List)} 
    */
   protected final void addI18n(List<String> list) {
      psc.addI18NKey(list);
      pc.addI18NKey(list);
      gifc.addI18NKey(list);

      addI18nPascal(list);
   }

   /**
    * 
    */
   protected final void initOutsideUIForPrefs(IPrefs prefs) {
      pc.setPrefs(prefs);
      psc.setPrefs(prefs);
      initForPrefsPascal(prefs);
   }

   /**
    * 
    * @param prefs
    */
   protected abstract void initForPrefsPascal(IPrefs prefs);

   /**
    * 
    * @param list
    */
   protected abstract void addI18nPascal(List<String> list);

   /**
    * 
    */
   public void cmdExit() {
      frame.savePrefs();
      psc.getCmds().cmdExit();
   }

   /**
    * 
    */
   protected void initSkinner() {
      //load the look and feel before any Swing component
      PascalSkinManager pascalSkinManager = new PascalSkinManager(psc);
      ImageIcon icon = psc.createImageIcon("/icons/look/yantra16.png", "");
      pascalSkinManager.setIconSelected(icon);
      psc.setPascalSkinManager(pascalSkinManager);
   }

   /**
    * 
    * @return
    */
   protected PanelTabLoginConsole createLoginConsole() {
      //must be first if init needs to use it
      PanelTabConsole consolePanel = new PanelTabConsole(psc);
      CentralLogger logger = new CentralLogger(psc, consolePanel);
      uc.setUserLog(logger);

      PanelTabLogin loginPanel = new PanelTabLogin(psc);
      PanelTabLoginConsole panelLoginConsole = new PanelTabLoginConsole(psc, loginPanel, consolePanel);

      return panelLoginConsole;
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "RunPascalSwingAbstract");
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(imgc);
      dc.nlLvl(gifc);
      dc.nlLvl(pc);
      dc.nlLvl(psc);

   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "RunPascalSwingAbstract");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   


}
