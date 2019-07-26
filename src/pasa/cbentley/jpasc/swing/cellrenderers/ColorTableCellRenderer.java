/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ColorTableCellRenderer extends JLabel implements TableCellRenderer {
   Color curColor;

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
      if (curColor instanceof Color) {
         curColor = (Color) value;
      } else {
         curColor = table.getBackground();
      }
      return this;
   }

   public void paint(Graphics g) {
      g.setColor(curColor);
      g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
   }
}