/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class CellRendererKeyAccountNum extends PascalTableCellRenderer implements TableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = -2783864594750841445L;

   public CellRendererKeyAccountNum(PascalSwingCtx psc) {
      super(psc);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object aNumberValue, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, aNumberValue, isSelected, hasFocus, row, column);
      if (psc.isIgnoreCellEffects()) {
         return this;
      }
      if (aNumberValue == null)
         return this;
      if (isSelected) {
         return this;
      }
      Number value = (Number) aNumberValue;
      int iv = value.intValue();
      int c = 0;
      if (isDarkTheme()) {
         c = psc.getIntToColor().getColorDarkFgOp(iv);
      } else {
         c = psc.getIntToColor().getColorLightFgOp(iv);
      }
      renderer.setForeground(new Color(c));
      return this;
   }

   public void dataUpdate() {

   }

}
