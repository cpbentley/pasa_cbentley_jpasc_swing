/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.others;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class PascalValueDefault implements IStringable {

   private PascalSwingCtx psc;

   public PascalValueDefault(PascalSwingCtx psc) {
      this.psc = psc;

   }

   public Double getWhalePriceMinimum() {
      return 1.0;
   }

   public Integer getWhaleWatchBlockPast() {
      return 100;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalValueDefault");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalValueDefault");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

}
