/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * Shows the best possible name
 * @author Charles Bentley
 *
 */
public class ModelTablePublicKeyJavaAutoChain extends ModelTablePublicKeyJavaAutoAbstract {

   public static final int   INDEX_0_NAME     = 0;

   public static final int   INDEX_1_NUM      = 1;

   public static final int   INDEX_2_COINS    = 2;

   public static final int   INDEX_3_ENCODED  = 3;

   public static final int   NUM_COLUMNS      = 4;

   /**
    * 
    */
   private static final long serialVersionUID = -6342532470248234931L;

   public ModelTablePublicKeyJavaAutoChain(PascalSwingCtx psc) {
      super(psc);
   }

   public int getColIndexFromValue(int value) {
      switch (value) {
         case VALUE_7_NAME_CTX:
            return INDEX_0_NAME;
         case VALUE_2_NUM_ACCOUNTS:
            return INDEX_1_NUM;
         case VALUE_6_NUM_COINS:
            return INDEX_2_COINS;
         case VALUE_5_ENCODED:
            return INDEX_3_ENCODED;
         default:
            return -1;
      }
   }

   public int getColumnIndexKeyName() {
      return INDEX_0_NAME;
   }

   public int getColumnIndexNumAccount() {
      return INDEX_1_NUM;
   }

   public int getNumColumns() {
      return NUM_COLUMNS;
   }

   protected int mapColToValueType(int col) {
      switch (col) {
         case INDEX_0_NAME:
            return VALUE_7_NAME_CTX;
         case INDEX_1_NUM:
            return VALUE_2_NUM_ACCOUNTS;
         case INDEX_2_COINS:
            return VALUE_6_NUM_COINS;
         case INDEX_3_ENCODED:
            return VALUE_5_ENCODED;
         default:
            break;
      }
      return 0;
   }
}