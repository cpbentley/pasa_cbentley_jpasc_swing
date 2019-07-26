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

public class CellRendererBlockMiner extends PascalTableCellRenderer implements TableCellRenderer {

   public CellRendererBlockMiner(PascalSwingCtx psc) {
      super(psc);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object minerName, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, minerName, isSelected, hasFocus, row, column);
      if (psc.isIgnoreCellEffects()) {
         return this;
      }
      if (minerName == null)
         return this;
      if (isSelected) {
         return this;
      }
      String minerNameStr = (String) minerName;
      int min = Math.min(8, minerNameStr.length());
      String str8 = minerNameStr.substring(0, min);
      Color c = null;
      if (isDarkTheme()) {
         c = psc.getIntToColor().getColorDarkBgName(str8);
      } else {
         c = psc.getIntToColor().getColorLightBgNameMiner(str8);
      }
      if (isSelected) {
         c = psc.getCellRendereManager().getSelectedColor(c);
      }
      renderer.setBackground(c);
      setText(minerNameStr);
      return this;
   }

   public void dataUpdate() {

   }
}