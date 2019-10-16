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

   private int[] rgb = new int[3];
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
      int intValue = value.intValue();
      int lum100 = 97 - (intValue / 3);
      lum100 = Math.max(40, lum100);
      int diff = intValue*2;
      ColorUtils.getRGBFromH360S100L100Clip(diff, 50, lum100, rgb);
      int rgbFg = ColorUtils.getComplementaryColor(rgb);
      
      colorBg = sc.getSwingColorStore().getColorRGB(rgb);
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