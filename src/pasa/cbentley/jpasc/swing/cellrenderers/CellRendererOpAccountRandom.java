/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * We want to group accounts by color in the Operations table.
 * <br>
 * The cell renderer must be created each time the table is refreshed
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererOpAccountRandom extends DefaultTableCellRenderer implements TableCellRenderer {

   private HashMap<Integer, Color> accounts = new HashMap<>();
   private PascalSwingCtx psc2;

   public CellRendererOpAccountRandom(PascalSwingCtx psc ) {
      psc2 = psc;
      super.setOpaque(true);

   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object aNumberValue, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, aNumberValue, isSelected, hasFocus, row, column);
      if (aNumberValue == null)
         return this;
      Integer value = (Integer) aNumberValue;
      Color color = accounts.get(value);
      if (color == null) {
         int v = psc2.getRandom().nextInt();
         color = new Color(v);
         accounts.put(value, color);
      }
      renderer.setBackground(color);
      return this;
   }
}