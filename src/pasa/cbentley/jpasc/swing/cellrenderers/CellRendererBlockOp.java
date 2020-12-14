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

import pasa.cbentley.jpasc.pcore.rpc.model.Block;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockFullData;

public class CellRendererBlockOp extends PascalTableCellRenderer implements TableCellRenderer {

   public CellRendererBlockOp(PascalSwingCtx psc) {
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
      ModelTableBlockFullData model = (ModelTableBlockFullData) table.getModel();
      
      //map row to
      int modelIndex = table.convertRowIndexToModel(row);
      Block block = model.getRow(modelIndex);
      Number value = (Number) aNumberValue;
      int iv = value.intValue();
      Color colorFg = psc.getIntToColor().getColorLightFgOp(iv);
      renderer.setForeground(colorFg);
      return this;
   }

   public void dataUpdate() {

   }
}