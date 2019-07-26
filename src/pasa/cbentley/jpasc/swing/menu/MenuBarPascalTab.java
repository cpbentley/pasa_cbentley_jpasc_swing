/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.menu;

import javax.swing.JMenuItem;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.menu.MenuNavigate;
import pasa.cbentley.swing.menu.MenuWindow;
import pasa.cbentley.swing.window.CBentleyFrame;

public class MenuBarPascalTab extends MenuBarPascalAbstract {

   /**
    * 
    */
   private static final long serialVersionUID = -7322815975196057591L;

   private JMenuItem         jmiHelp;

   private MenuNavigate      navigateMenu;

   private IMyTab            tab;

   private MenuWindow        windowMenu;

   public MenuBarPascalTab(PascalSwingCtx psc, CBentleyFrame frame, IMyTab tab) {
      super(psc, frame);
      this.tab = tab;

      navigateMenu = new MenuNavigate(psc.getSwingCtx());
      this.add(navigateMenu);

      windowMenu = new MenuWindow(psc.getSwingCtx());
      this.add(windowMenu);

      jmiHelp = new JMenuItem("Help for " + tab.getTabTitle());
      this.add(jmiHelp);
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "MenuBarTab");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MenuBarTab");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}
