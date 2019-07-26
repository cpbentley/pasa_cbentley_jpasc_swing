/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CellRendererPasaPrice extends DefaultTableCellRenderer implements TableCellRenderer {
   DecimalFormat df          = new DecimalFormat("##,###,###.####");

   private Color fDarkBlue   = Color.blue.darker();

   private Color fDarkGreen  = Color.green.darker();

   private Color fDarkOrange = Color.ORANGE.darker();

   private Color fDarkRed    = Color.red.darker();

   public CellRendererPasaPrice() {
      super.setOpaque(true);
      df.setDecimalSeparatorAlwaysShown(true);
      df.setMinimumFractionDigits(4);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object aNumberValue, boolean isSelected, boolean hasFocus, int row, int column) {
      /* 
       * Implementation Note :
       * It is important that no 'new' objects be present in this 
       * implementation (excluding exceptions):
       * if the table is large, then a large number of objects would be 
       * created during rendering.
       */
      Component renderer = super.getTableCellRendererComponent(table, aNumberValue, isSelected, hasFocus, row, column);
      //this call must be AFTER super.getTable... otherwise bug.. it uses the previous value
      if (aNumberValue == null)
         return this;
      Number value = (Number) aNumberValue;
      double dvalue = value.doubleValue();
      if (dvalue == 0) {
         renderer.setForeground(fDarkOrange);
      } else if (dvalue < 1) {
         renderer.setForeground(fDarkGreen);
      } else if (dvalue < 3) {
         renderer.setForeground(fDarkBlue);
      } else if (dvalue > 20) {
         renderer.setForeground(fDarkRed);
      }
      return this;
   }

   /**
    * Sets the text
    */
   public void setValue(Object aValue) {
      if ((aValue != null) && (aValue instanceof Number)) {
         Number numberValue = (Number) aValue;
         double d = numberValue.doubleValue();
         setText(df.format(d));
      } else {
         super.setValue(aValue);
      }
   }
}