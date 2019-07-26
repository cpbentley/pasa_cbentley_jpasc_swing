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

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.jpasc.pcore.interfaces.IBlockListener;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class CellRendererBlockRGB extends DefaultTableCellRenderer implements TableCellRenderer, IBlockListener {

   private PascalSwingCtx psc;

   private int            currentBlock = 0;

   public CellRendererBlockRGB(PascalSwingCtx psc) {
      this.psc = psc;
      super.setOpaque(true);

      //listen to blocks
      psc.addBlockListener(this);
   }

   public void pingNewBlock(Integer newBlock, long millis) {
      if(newBlock != null) {
         currentBlock = newBlock.intValue();
      }
   }

   public void pingNoBlock(long millis) {
   }

   public void pingNewPendingCount(Integer count, Integer oldCount) {
   }

   public void pingDisconnect() {
   }

   public void pingError() {
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object aNumberValue, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, aNumberValue, isSelected, hasFocus, row, column);
      if (aNumberValue == null)
         return this;
      if(isSelected) {
         return this;
      }
      Number value = (Number) aNumberValue;
      int block = value.intValue();
      int diff = currentBlock - block;
      int div = diff / 128;
      int mod = diff % 128;
      int divBig = diff / (128 * 128);
      int blueDiff = div;
      int redDiff = divBig;
      int greenDiff = mod;
      int color = ColorUtils.getRGBInt(redDiff, greenDiff, 255 - blueDiff);
      Color c = new Color(color);
      renderer.setForeground(c);
      return this;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CellRendererBlockRGB");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CellRendererBlockRGB");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

}