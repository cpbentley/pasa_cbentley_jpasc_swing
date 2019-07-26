/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * We want to group accounts by color in the Operations table.
 * <br>
 * The cell renderer must be created each time the table is refreshed
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererAccount extends PascalTableCellRenderer implements TableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = 2629606866970459604L;

   public CellRendererAccount(PascalSwingCtx psc) {
      super(psc);
      this.setHorizontalAlignment(JLabel.TRAILING);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object account, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, account, isSelected, hasFocus, row, column);
      if (account == null)
         return this;
      if(psc.isIgnoreCellEffects()) {
         return this;
      }
      Integer value = (Integer) account;
      Color color = null;
      if (isDarkTheme()) {
         color = psc.getIntToColor().getColorDarkBgAccount(value);
      } else {
         color = psc.getIntToColor().getColorLightBgAccount(value);
      }
      if (isSelected) {
         color = psc.getCellRendereManager().getSelectedColor(color);
      }
      renderer.setBackground(color);
      return this;
   }

   public void dataUpdate() {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "CellRendererAccount");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CellRendererAccount");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
}