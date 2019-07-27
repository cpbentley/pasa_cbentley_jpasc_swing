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
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockFullData;
import pasa.cbentley.jpasc.swing.utils.SupportBlock;

/**
 * Renders the payload cell of a block, depends on the miner string
 * @author Charles Bentley
 *
 */
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
      
      ModelTableBlockFullData model = (ModelTableBlockFullData)table.getModel();
      int modelIndex = table.convertRowIndexToModel(row);
      SupportBlock supportBlock = (SupportBlock) model.getRow(modelIndex).getObjectSupport();
      String minerKey = supportBlock.getMinerKey();
      Color c = null;
      if (isDarkTheme()) {
         c = psc.getIntToColor().getColorDarkBgName(minerKey);
      } else {
         c = psc.getIntToColor().getColorLightBgNameMiner(minerKey);
      }
      if (isSelected) {
         c = psc.getCellRendereManager().getSelectedColor(c);
      }
      renderer.setBackground(c);
      return this;
   }

   public void dataUpdate() {

   }
}