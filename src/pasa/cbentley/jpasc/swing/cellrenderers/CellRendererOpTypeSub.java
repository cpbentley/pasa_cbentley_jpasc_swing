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
import pasa.cbentley.jpasc.pcore.rpc.model.OperationSubType;
import pasa.cbentley.jpasc.pcore.utils.PascalUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * Renders
 * @author Charles Bentley
 *
 */
public class CellRendererOpTypeSub extends PascalTableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID          = -8384983445734085576L;

   private static final int  COLOR_0_TX_SENDER         = 0;

   private static final int  COLOR_1_CHANGE_KEY        = 1;

   private static final int  COLOR_2_LIST_ACC_PUB_SALE = 2;

   private Color[]           colors                    = new Color[8];

   public CellRendererOpTypeSub(PascalSwingCtx psc) {
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
      if (psc.isIgnoreCellEffects()) {
         return this;
      }
      OperationSubType type = (OperationSubType) opType;
      Color bgColor = colors[3]; //default rarest case
      if (type == OperationSubType.TRANSACTION_SENDER) {
         bgColor = colors[COLOR_0_TX_SENDER];
      } else if (type == OperationSubType.CHANGE_KEY) {
         bgColor = colors[COLOR_1_CHANGE_KEY];
      } else if (type == OperationSubType.LIST_ACCOUNT_FOR_PUBLIC_SALE) {
         bgColor = colors[COLOR_2_LIST_ACC_PUB_SALE];
      } else if (type == OperationSubType.DELIST_ACCOUNT) {
         bgColor = colors[3];
      } else if (type == OperationSubType.CHANGE_ACCOUNT_INFO) {
         bgColor = colors[6];
      } else if (type == OperationSubType.LIST_ACCOUNT_FOR_PRIVATE_SALE) {
         bgColor = colors[4];
      } else if (type == OperationSubType.BUYACCOUNT_BUYER) {
         bgColor = colors[5];
      }
      if (isSelected) {
         bgColor = psc.getCellRendereManager().getSelectedColor(bgColor);
      }
      renderer.setBackground(bgColor);
      setText(PascalUtils.getOperationSubTypeUserString(type));
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
         colors[4] = new Color(ColorUtils.FR_GRIS_Plomb);
         colors[5] = new Color(ColorUtils.FR_GRIS_Ardoise_fonce);
         colors[6] = new Color(ColorUtils.FR_GRIS_Ardoise_fonce);
         colors[7] = new Color(ColorUtils.FR_GRIS_Ardoise_fonce);
      } else {
         colors[0] = new Color(ColorUtils.FR_BEIGE_Blanc_Lavande);
         colors[1] = new Color(ColorUtils.FR_BEIGE_Peche);
         colors[2] = new Color(ColorUtils.FR_BEIGE_Ventre_de_biche);
         colors[3] = new Color(ColorUtils.FR_ROSE_Clair); //rare
         colors[4] = new Color(ColorUtils.FR_GRIS_Plomb);
         colors[5] = new Color(ColorUtils.FR_GRIS_Ardoise_fonce);
         colors[6] = new Color(ColorUtils.FR_GRIS_Ardoise_fonce);
         colors[7] = new Color(ColorUtils.FR_GRIS_Ardoise_fonce);

      }
   }

}