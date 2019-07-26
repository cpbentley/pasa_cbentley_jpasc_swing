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
 * Render each cell with a specific background and foreground
 * @author Charles Bentley
 *
 */
public class CellRendererKeyName extends PascalTableCellRenderer implements TableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = 3238201311870749943L;

   public CellRendererKeyName(PascalSwingCtx psc) {
      super(psc);
      this.setHorizontalAlignment(JLabel.LEADING);
   }

   public Component getTableCellRendererComponent(JTable table, Object name, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, name, isSelected, hasFocus, row, column);
      if (name == null) {
         setText("null");
         return this;
      }
      String str = (String) name;
      if (psc.isIgnoreCellEffects()) {
         return this;
      }
      Color c = null;
      if (isDarkTheme()) {
         c = psc.getIntToColor().getColorDarkBgName(str);
      } else {
         c = psc.getIntToColor().getColorLightBgName(str);
      }
      if (isSelected) {
         c = psc.getCellRendereManager().getSelectedColor(c);
      }
      renderer.setBackground(c);
      setText(str);
      return this;
   }

   /**
    * Called by {@link CellRendereManager} when object has to update its data for rendering
    */
   public void dataUpdate() {
      if (isDarkTheme()) {
      } else {
      }
   }
}
