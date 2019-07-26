/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.widgets;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.widgets.b.BPanel;

/**
 * {@link BPanel} automatically managed gui updates.
 * 
 * Uses the Build
 * 
 * @author Charles Bentley
 *
 */
public class PanelPascal extends BPanel implements IMyGui {

   /**
    * 
    */
   private static final long      serialVersionUID = 3617693757648059895L;

   protected final PascalSwingCtx psc;

   public PanelPascal(PascalSwingCtx psc) {
      super(psc.getSwingCtx());
      this.psc = psc;
   }

   /**
    * Called before the panel is being drawn
    */
   public void buildUI() {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelPascal");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelPascal");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
