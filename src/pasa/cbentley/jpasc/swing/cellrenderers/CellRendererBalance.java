/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class CellRendererBalance extends DefaultTableCellRenderer implements TableCellRenderer {
   Border                 big;

   DecimalFormat          df             = new DecimalFormat("## ### ### ###.####");

   private Color          fDarkGreen     = new Color(ColorUtils.FR_VERT_Absinthe);

   private Color          fDarkDarkGreen = Color.green.darker().darker();

   private Color          fDarkRed       = Color.red.darker();

   private Color          defForeground;

   private PascalSwingCtx psc;

   public CellRendererBalance(PascalSwingCtx psc) {
      this.psc = psc;
      super.setOpaque(true);
      setHorizontalAlignment(JLabel.RIGHT);
      big = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.YELLOW);
      
      defForeground = psc.getSwingCtx().getUIData().getCLabelForeground();

      //listen to UI change events
      
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
      if (renderer instanceof JLabel) {

      }
      Number value = (Number) aNumberValue;
      double ivalue = value.doubleValue();
      if (ivalue == 0.0) {
         //default foregounrd color
         renderer.setForeground(defForeground);
      } else if (ivalue < 0) {
         renderer.setForeground(fDarkRed);
      } else if (ivalue < 1.0) {
         renderer.setForeground(fDarkRed);
      } else if (ivalue < 10) {
         renderer.setForeground(fDarkGreen);
      } else if (ivalue < 100) {
         renderer.setForeground(fDarkDarkGreen);
      } else if (ivalue < 1000) {
         renderer.setForeground(fDarkDarkGreen);
      } else {
         //for big numbers
         ((JLabel) renderer).setBackground(Color.YELLOW);
         ((JLabel) renderer).setBorder(big);
      }
      return this;
   }

   /**
    * Sets the text
    */
   public void setValue(Object aValue) {
      Object result = aValue;
      if ((aValue != null) && (aValue instanceof Number)) {
         Number numberValue = (Number) aValue;
         double d = numberValue.doubleValue();
         setText(psc.getPCtx().getPU().getPrettyPascBalance(d, ","));
      } else {
         super.setValue(result);
      }
   }

}