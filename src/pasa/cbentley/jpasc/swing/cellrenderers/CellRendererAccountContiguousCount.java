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

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * Color accounts with a high contiguous count.
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererAccountContiguousCount extends PascalTableCellRenderer implements TableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = 6713574784498923775L;

   public CellRendererAccountContiguousCount(PascalSwingCtx psc) {
      super(psc);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object aNumberValue, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, aNumberValue, isSelected, hasFocus, row, column);
      if (aNumberValue == null)
         return this;
      Integer value = (Integer) aNumberValue;
      
      if (psc.isIgnoreCellEffects()) {
         return this;
      }
      Color c = null;
      if (isDarkTheme()) {
         c = psc.getAccountContiguousColorLight(value.intValue());
      } else {
         c = psc.getAccountContiguousColorLight(value.intValue());
      }
      if (isSelected) {
         c = psc.getCellRendereManager().getSelectedColor(c);
      }
      renderer.setBackground(c);
      return this;
   }

   public void dataUpdate() {

   }
}