/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

/**
 * Rendere
 * @author Charles Bentley
 *
 */
public abstract class ModelTablePublicKeyJavaAutoAbstract extends ModelTablePublicKeyJavaAbstract {

   public static final int   VALUE_0_NAME_PRIVATE = 0;

   public static final int   VALUE_1_NAME_PUBLIC  = 1;

   public static final int   VALUE_7_NAME_CTX     = 7;

   public static final int   VALUE_2_NUM_ACCOUNTS = 2;

   public static final int   VALUE_3_TYPE         = 3;

   public static final int   VALUE_4_BASE58       = 4;

   public static final int   VALUE_5_ENCODED      = 5;

   public static final int   VALUE_6_NUM_COINS    = 6;

   /**
    * 
    */
   private static final long serialVersionUID     = -6342532470248234931L;

   private int               numAccounts;

   private int               numKeys;

   public ModelTablePublicKeyJavaAutoAbstract(PascalSwingCtx psc) {
      super(psc);

      initColumnModel(getNumColumns());

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.key.coltitle.");
      columnModel.setKeyPrefixTip("table.key.coltip.");

      columnModel.setString(getColIndexFromValue(VALUE_0_NAME_PRIVATE), "nameprivate");
      columnModel.setString(getColIndexFromValue(VALUE_1_NAME_PUBLIC), "namepublic");
      columnModel.setString(getColIndexFromValue(VALUE_7_NAME_CTX), "name");
      columnModel.setInteger(getColIndexFromValue(VALUE_2_NUM_ACCOUNTS), "numaccount");
      columnModel.setString(getColIndexFromValue(VALUE_3_TYPE), "type");
      columnModel.setString(getColIndexFromValue(VALUE_4_BASE58), "base58value");
      columnModel.setString(getColIndexFromValue(VALUE_5_ENCODED), "encoded");
      columnModel.setDouble(getColIndexFromValue(VALUE_6_NUM_COINS), "coins");

   }

   /**
    * 
    * @param value
    * @return -1 if not shown
    */
   public abstract int getColIndexFromValue(int value);

   public abstract int getNumColumns();

   public void clear() {
      super.clear();
      numAccounts = 0;
      numKeys = 0;
   }

   protected void computeStats(PublicKeyJava a, int row) {
      if (a != null) {
         numKeys++;
         if (a.getCache() != null) {
            //get it from the cached associated
            numAccounts = a.getCache().getNumAccounts();
         } else {
            numAccounts += a.getNumAccounts();
         }
      }
   }

   public int getNumAccounts() {
      return numAccounts;
   }

   public int getNumKeys() {
      return numKeys;
   }

   protected abstract int mapColToValueType(int col);

   public Object getValueAt(int row, int col) {
      PublicKeyJava a = getRow(row);
      if (a == null) {
         return null;
      }
      //
      int valueType = mapColToValueType(col);
      String encKey = a.getEncPubKey();
      switch (valueType) {
         case VALUE_0_NAME_PRIVATE:
            //returns null if no private name
            return psc.getPCtx().getKeyNameProvider().getPkNameStorePrivate().getKeyName(encKey);
         case VALUE_1_NAME_PUBLIC:
            //returns a non null string 
            return psc.getPCtx().getKeyNameProvider().getPkNameStorePublic().getKeyNameAdd(encKey);
         case VALUE_2_NUM_ACCOUNTS:
            return a.getNumAccounts();
         case VALUE_3_TYPE:
            return a.getKeyType();
         case VALUE_4_BASE58:
            return a.getBase58PubKey();
         case VALUE_5_ENCODED:
            return a.getEncPubKey();
         case VALUE_6_NUM_COINS:
            if (a.getNumCoins() != null) {
               return a.getNumCoins().getDouble();
            }
            return 0.0d;
         case VALUE_7_NAME_CTX:
            return psc.getPCtx().getKeyNameProvider().getKeyName(encKey);
         default:
            break;
      }
      return null;
   }
}