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
 * We want to group accounts by color in the Operations table.
 * <br>
 * The cell renderer must be created each time the table is refreshed
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererAccountAge extends PascalTableCellRenderer implements TableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = 6713574784498923775L;

   public CellRendererAccountAge(PascalSwingCtx psc) {
      super(psc);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object aNumberValue, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, aNumberValue, isSelected, hasFocus, row, column);
      if (aNumberValue == null)
         return this;
      Integer value = (Integer) aNumberValue;
      
      //#debug
      //toDLog().pFlow("msg", this, CellRendererAccountAge.class, "getTableCellRendererComponent", IDLog.LVL_05_FINE, true);
      
      if (psc.isIgnoreCellEffects()) {
         return this;
      }
      Color c = null;
      if (isDarkTheme()) {
         c = psc.getAccountAgeColorDark(value.intValue());
      } else {
         c = psc.getAccountAgeColorLight(value.intValue());
      }
      if (isSelected) {
         c = psc.getCellRendereManager().getSelectedColor(c);
      }
      if(renderer instanceof JLabel) {
         //TODO compute only when mouse over
         String msg = psc.getPascalSwingUtils().computeTimeFromBlockAgePascalTime(value);
         ((JLabel)renderer).setToolTipText(msg);
      }
      renderer.setBackground(c);
      return this;
   }

   public void dataUpdate() {

   }
}