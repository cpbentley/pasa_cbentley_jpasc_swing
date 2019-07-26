/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Component;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class CellRendererTime extends DefaultTableCellRenderer {

   private PascalSwingCtx psc;

   public CellRendererTime(PascalSwingCtx psc) {
      this.psc = psc;
      //time formatting
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if (value instanceof Date) {
         value = psc.getFormatDateTime().format(value);
      }
      super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      return this;

   }
}