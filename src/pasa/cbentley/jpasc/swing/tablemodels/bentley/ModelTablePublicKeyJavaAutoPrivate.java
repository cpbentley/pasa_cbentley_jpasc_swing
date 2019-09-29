/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

/**
 * Rendere
 * @author Charles Bentley
 *
 */
public class ModelTablePublicKeyJavaAutoPrivate extends ModelTablePublicKeyJavaAutoAbstract {

   public static final int   INDEX_0_NAME_PRIVATE = 0;

   public static final int   INDEX_1_NAME_PUBLIC  = 1;

   public static final int   INDEX_2_NUM_ACCOUNTS = 2;

   public static final int   INDEX_3_COINS        = 3;

   public static final int   INDEX_4_TYPE         = 4;

   public static final int   INDEX_5_BASE58       = 5;

   public static final int   INDEX_6_ENCODED      = 6;

   public static final int   NUM_COLUMNS          = 7;

   /**
    * 
    */
   private static final long serialVersionUID     = -6342532470248234931L;

   public ModelTablePublicKeyJavaAutoPrivate(PascalSwingCtx psc) {
      super(psc);
   }

   public int getColIndexFromValue(int value) {
      switch (value) {
         case VALUE_0_NAME_PRIVATE:
            return INDEX_0_NAME_PRIVATE;
         case VALUE_1_NAME_PUBLIC:
            return INDEX_1_NAME_PUBLIC;
         case VALUE_2_NUM_ACCOUNTS:
            return INDEX_2_NUM_ACCOUNTS;
         case VALUE_3_TYPE:
            return INDEX_4_TYPE;
         case VALUE_4_BASE58:
            return INDEX_5_BASE58;
         case VALUE_5_ENCODED:
            return INDEX_6_ENCODED;
         case VALUE_6_NUM_COINS:
            return INDEX_3_COINS;
         default:
            return -1;
      }
   }

   public int getColumnIndexKeyName() {
      return INDEX_0_NAME_PRIVATE;
   }

   public int getColumnIndexNumAccount() {
      return INDEX_2_NUM_ACCOUNTS;
   }

   public int getNumColumns() {
      return NUM_COLUMNS;
   }

   protected int mapColToValueType(int col) {
      switch (col) {
         case INDEX_0_NAME_PRIVATE:
            return VALUE_0_NAME_PRIVATE;
         case INDEX_1_NAME_PUBLIC:
            return VALUE_1_NAME_PUBLIC;
         case INDEX_2_NUM_ACCOUNTS:
            return VALUE_2_NUM_ACCOUNTS;
         case INDEX_3_COINS:
            return VALUE_6_NUM_COINS;
         case INDEX_4_TYPE:
            return VALUE_3_TYPE;
         case INDEX_5_BASE58:
            return VALUE_4_BASE58;
         case INDEX_6_ENCODED:
            return VALUE_5_ENCODED;
         default:
            break;
      }
      return 0;
   }

}