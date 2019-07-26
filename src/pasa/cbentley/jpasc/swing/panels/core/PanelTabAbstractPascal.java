/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;

public abstract class PanelTabAbstractPascal extends AbstractMyTab {

   /**
    * 
    */
   private static final long      serialVersionUID = 3801470848018565704L;

   protected final PascalSwingCtx psc;

   public PanelTabAbstractPascal(PascalSwingCtx psc, String id) {
      super(psc.getSwingCtx(), id);
      this.psc = psc;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelTabAbstractPascal");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelTabAbstractPascal");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
