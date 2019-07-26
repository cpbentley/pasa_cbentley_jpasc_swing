/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

public abstract class TabsPascalSwing extends TabbedBentleyPanel {

   /**
    * 
    */
   private static final long      serialVersionUID = -7565250868525348302L;

   protected final PascalSwingCtx psc;

   public TabsPascalSwing(PascalSwingCtx psc, String id) {
      super(psc.getSwingCtx(), id);
      this.psc = psc;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TabsPascalSwing");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabsPascalSwing");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
