/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class CellRendererBlockSomeTesting extends DefaultTableCellRenderer implements TableCellRenderer {

   private String         str = "";

   private PascalSwingCtx psc;

   private Random         r;

   private int          bgColor;

   private int            fgColor;

   private boolean isSelected;

   public CellRendererBlockSomeTesting(PascalSwingCtx psc) {
      this.psc = psc;
      super.setOpaque(true);
      r = new Random();
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
      Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, vColIndex);

      if (rowIndex == 0) {
         fgColor = r.nextInt();
         bgColor = r.nextInt();
      } else {
         fgColor--;
         bgColor--;
      }

      if (value == null)
         return this;
      this.isSelected = isSelected;
    
      Number valueN = (Number) value;
      str = String.valueOf(valueN.intValue());
      
      //#mdebug
      String dstr = str + " isSel=" + isSelected + " hasF=" + hasFocus + " row" + rowIndex + " col=" + vColIndex;
      psc.toDLog().pTest(dstr, null, CellRendererBlockSomeTesting.class, "getTableCellRendererComponent", ITechLvl.LVL_04_FINER, true);
      //#enddebug
      
      return renderer;
   }

   @Override
   protected void paintComponent(Graphics g) {
      // Let UI delegate paint first 
      // (including background filling, if I'm opaque)
      super.paintComponent(g); // fill the JPanel's background and invoke ui.paint()

      Rectangle rec = g.getClipBounds();

      int x = rec.x;
      int y = rec.y;
      int w = rec.width;
      int h = rec.height;
      Color c = null;
      if (isSelected) {
         c = getBackground();
      } else {
         c = new Color(bgColor);
      }
      g.setColor(c);
      g.fillRect(x, y, w, h);

      // paint my contents next...
      FontMetrics f = g.getFontMetrics();
      y += f.getHeight() - f.getDescent();
      x += (getWidth() - f.stringWidth(str)) / 2;
      g.setColor(new Color(fgColor));
      g.drawString(str, x, y);
   }

}