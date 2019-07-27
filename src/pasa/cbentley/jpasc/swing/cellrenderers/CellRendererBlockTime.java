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
import javax.swing.table.TableModel;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockFullData;
import pasa.cbentley.jpasc.swing.utils.SupportBlock;

/**
 * Colors the block time cell with a color
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererBlockTime extends PascalTableCellRenderer implements TableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = -1650424196228520407L;

   public CellRendererBlockTime(PascalSwingCtx psc) {
      super(psc);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object aNumberValue, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, aNumberValue, isSelected, hasFocus, row, column);
      if(psc.isIgnoreCellEffects()) {
         return this;
      }
      if (aNumberValue == null)
         return this;
      if(isSelected) {
         return this;
      }
      //we have a string.. we need the value
      ModelTableBlockFullData model = (ModelTableBlockFullData)table.getModel();
      int modelIndex = table.convertRowIndexToModel(row);
      SupportBlock supportBlock = (SupportBlock) model.getRow(modelIndex).getObjectSupport();
      int diffSeconds = (int) supportBlock.getTimeBlockDiff();
      //should be 5 minutes so 
      int minutes5 = 60 * 5;
      int diff = minutes5 - diffSeconds;
      Color color = null;
      if(diff > 0) {
         //less get a 
         color = psc.getIntToColor().getColorLightBlockTimeBelow(diff);
      } else {
         color = psc.getIntToColor().getColorLightBlockTimeAbove(Math.abs(diff));
      }
      renderer.setBackground(color);
      return this;
   }

   public void dataUpdate() {

   }
}