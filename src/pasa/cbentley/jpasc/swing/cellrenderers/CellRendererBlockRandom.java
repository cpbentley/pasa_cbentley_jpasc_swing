/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class CellRendererBlockRandom extends DefaultTableCellRenderer implements TableCellRenderer {
   
   private Number baseValue   = 0;


   private PascalSwingCtx psc;

   public CellRendererBlockRandom(PascalSwingCtx psc) {
      this.psc = psc;
      super.setOpaque(true);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object aNumberValue, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, aNumberValue, isSelected, hasFocus, row, column);
      if (aNumberValue == null)
         return this;
      Number value = (Number) aNumberValue;
      if (baseValue == null) {
         baseValue = value;
      }
      int color = psc.getRandom().nextInt();
      Color c = new Color(color);
      renderer.setForeground(c);
      return this;
   }

}