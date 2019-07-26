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
import javax.swing.table.TableModel;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * Renders account contiguous when being sold
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererAccountSoldContiguous extends PascalTableCellRenderer implements TableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = 2629606866970459604L;

   private int               colorColumnModelIndex;

   public CellRendererAccountSoldContiguous(PascalSwingCtx psc, int colorColumnModelIndex) {
      super(psc);
      this.colorColumnModelIndex = colorColumnModelIndex;
      this.setHorizontalAlignment(JLabel.TRAILING);
   }

   public void dataUpdate() {

   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object account, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, account, isSelected, hasFocus, row, column);
      if (account == null)
         return this;
      if (psc.isIgnoreCellEffects()) {
         return this;
      }
      Color color = null;
      TableModel model = table.getModel();
      int modelIndex = table.convertRowIndexToModel(row);
      color = (Color) model.getValueAt(modelIndex, colorColumnModelIndex);
      renderer.setBackground(color);
      return this;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "CellRendererAccountSoldContiguous");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CellRendererAccountSoldContiguous");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
}