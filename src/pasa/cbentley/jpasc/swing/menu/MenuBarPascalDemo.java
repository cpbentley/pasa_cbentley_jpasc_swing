/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.menu;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_Q;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.KeyStroke;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.menu.MenuDebug;
import pasa.cbentley.swing.menu.MenuLanguage;
import pasa.cbentley.swing.menu.MenuWindow;
import pasa.cbentley.swing.widgets.b.BMenu;
import pasa.cbentley.swing.widgets.b.BMenuItem;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Menu bar used by demo frames.
 * 
 * @author Charles Bentley
 *
 */
public class MenuBarPascalDemo extends MenuBarPascalAbstract implements ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = -7322815975196057591L;

   private BMenuItem         itemExit;

   private MenuDebug         menuDebug;

   private BMenu             menuFile;

   private MenuLanguage      menuLanguage;

   private MenuWindow        windowMenu;

   public MenuBarPascalDemo(PascalSwingCtx psc, CBentleyFrame frame) {
      super(psc, frame);

      SwingCtx sc = psc.getSwingCtx();

      buildMenuFile(sc);

      this.add(psc.getPascalSkinManager().getRootMenu());

      menuLanguage = new MenuLanguage(sc);
      menuLanguage.addLanguage("English US", "en", "US");
      menuLanguage.addLanguage("Français FR", "fr", "FR");

      this.add(menuLanguage);

      windowMenu = new MenuWindow(sc);
      this.add(windowMenu);

      menuDebug = new MenuDebug(sc);
      this.add(menuDebug);

   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == itemExit) {
         psc.getCmds().cmdExit();
      }
   }

   private void buildMenuFile(SwingCtx sc) {
      menuFile = new BMenu(sc, "menu.demo");
      menuFile.setMnemonic(VK_F);
      itemExit = new BMenuItem(sc, this, "menu.item.exit");
      itemExit.setMnemonic(VK_E);
      itemExit.setAccelerator(KeyStroke.getKeyStroke(VK_Q, CTRL_DOWN_MASK));

      menuFile.add(itemExit);

      this.add(menuFile);
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PascalMenuBarDemo");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalMenuBarDemo");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

   private void toStringPrivate(Dctx dc) {

   }

}
