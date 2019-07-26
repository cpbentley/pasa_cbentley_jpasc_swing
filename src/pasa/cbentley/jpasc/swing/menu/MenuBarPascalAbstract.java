/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.menu;

import java.awt.event.InputEvent;

import javax.swing.JMenuBar;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.menu.ITechMenu;
import pasa.cbentley.swing.window.CBentleyFrame;

public abstract class MenuBarPascalAbstract extends JMenuBar implements IMyGui, ITechMenu {

   /**
    * 
    */
   private static final long serialVersionUID = 1774778387836051346L;


   protected CBentleyFrame   owner;

   protected PascalSwingCtx  psc;

   public MenuBarPascalAbstract(PascalSwingCtx psc, CBentleyFrame owner) {
      this.psc = psc;
      this.owner = owner;

   }

   public void guiUpdate() {
      SwingCtx sc = psc.getSwingCtx();
      sc.guiUpdateOnChildren(this);
   }

   public void showMenu() {

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalSwingMenuBar");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalSwingMenuBar");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

   private void toStringPrivate(Dctx dc) {

   }

}
