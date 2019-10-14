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

import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Color accounts with a high contiguous count.
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererAccountPackCount extends PascalTableCellRenderer implements TableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = 6713574784498923775L;

   protected final SwingCtx  sc;

   public CellRendererAccountPackCount(PascalSwingCtx psc) {
      super(psc);
      sc = psc.getSwingCtx();
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
      Color colorBg = null;
      Color colorFg = null;
      int diff = value.intValue() * 10;
      int rgbBg = Integer.MAX_VALUE - diff;
      int rgbFg = ColorUtils.getComplementaryColor(rgbBg);
      
      colorBg = sc.getSwingColorStore().getColorRGB(rgbBg);
      colorFg = sc.getSwingColorStore().getColorRGB(rgbFg);
      
      if (isSelected) {
         colorBg = psc.getCellRendereManager().getSelectedColor(colorBg);
      }
      renderer.setForeground(colorFg);
      renderer.setBackground(colorBg);
      return this;
   }

   public void dataUpdate() {

   }
}