/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.menu;

import javax.swing.JMenuBar;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabPascalSwing;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.ITabMenuBarFactory;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Create a menu bar.
 * 
 * What if the panel is decided by the brander?
 * 
 * @author Charles Bentley
 *
 */
public class MenuBarPascalFactory implements ITabMenuBarFactory {

   protected final PascalSwingCtx psc;

   public MenuBarPascalFactory(PascalSwingCtx psc) {
      this.psc = psc;

   }

   public JMenuBar getMenuBar(Object owner, CBentleyFrame frame) {
      if (owner instanceof PanelTabPascalSwing) {
         return new MenuBarPascalMain(psc, frame);
      } else if (owner instanceof IMyTab) {
         return new MenuBarPascalTab(psc, frame, (IMyTab) owner);
      }

      //#debug
      psc.toDLog().pUI("No JMenuBar", (owner instanceof IStringable) ? (IStringable) owner : null, MenuBarPascalFactory.class, "getMenuBar");
      return null;
   }
}
