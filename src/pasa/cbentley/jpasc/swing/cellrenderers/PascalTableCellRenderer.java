/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public abstract class PascalTableCellRenderer extends DefaultTableCellRenderer implements IStringable, TableCellRenderer {

   /**
    * 
    */
   private static final long      serialVersionUID = -4312805049295226129L;

   protected final PascalSwingCtx psc;

   public PascalTableCellRenderer(PascalSwingCtx psc) {
      this.psc = psc;
   }

   public boolean isDarkTheme() {
      return psc.getCellRendereManager().isDarkTheme();
   }

   public abstract void dataUpdate();

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public IDLog toDLog() {
      return psc.toDLog();
   }
   
   public void toString(Dctx dc) {
      dc.root(this, "PascalTableCellRenderer");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalTableCellRenderer");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUC();
   }
   //#enddebug

}
