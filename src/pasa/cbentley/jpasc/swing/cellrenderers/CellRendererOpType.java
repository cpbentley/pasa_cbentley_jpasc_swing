/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.jpasc.pcore.rpc.model.OperationType;
import pasa.cbentley.jpasc.pcore.utils.PascalUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * Renders
 * @author Charles Bentley
 *
 */
public class CellRendererOpType extends PascalTableCellRenderer {

   private Color[] colors = new Color[4];

   public CellRendererOpType(PascalSwingCtx psc) {
      super(psc);
      //fill in with default colors
      dataUpdate();
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object opType, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, opType, isSelected, hasFocus, row, column);
      if (opType == null) {
         setText("null");
         return this;
      }
      if(psc.isIgnoreCellEffects()) {
         return this;
      }
      OperationType type = (OperationType) opType;
      Color bgColor = colors[3]; //default rarest case
      if (type == OperationType.TRANSACTION) {
         bgColor = colors[0];
      } else if (type == OperationType.CHANGEKEY) {
         bgColor = colors[1];
      } else if (type == OperationType.BLOCKCHAINREWARD) {
         bgColor = colors[2];
      }
      if(isSelected) {
         bgColor = psc.getCellRendereManager().getSelectedColor(bgColor);
      }
      renderer.setBackground(bgColor);
      setText(PascalUtils.getOperationTypeUserString(type));
      return this;
   }

   /**
    * Called by {@link CellRendereManager} when object has to update its data for rendering
    */
   public void dataUpdate() {
      if (isDarkTheme()) {
         colors[0] = new Color(ColorUtils.FR_GRIS_Ardoise);
         colors[1] = new Color(ColorUtils.FR_GRIS_Argent);
         colors[2] = new Color(ColorUtils.FR_GRIS_Plomb);
         colors[3] = new Color(ColorUtils.FR_GRIS_Ardoise_fonce);
      } else {
         colors[0] = new Color(ColorUtils.FR_BEIGE_Blanc_Lavande);
         colors[1] = new Color(ColorUtils.FR_BEIGE_Peche);
         colors[2] = new Color(ColorUtils.FR_BEIGE_Ventre_de_biche);
         colors[3] = new Color(ColorUtils.FR_ROSE_Clair); //rare
      }
   }

}