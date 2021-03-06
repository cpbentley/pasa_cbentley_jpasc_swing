/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

public class ModelTableAccountFullData extends ModelTableAccountAbstract {

   public static final int   INDEX_00_ACCOUNT     = 0;

   public static final int   INDEX_01_CHECKSUM    = 1;

   public static final int   INDEX_02_PUBLICKEY   = 2;

   public static final int   INDEX_03_BALANCE     = 3;

   public static final int   INDEX_04_NAME        = 4;

   public static final int   INDEX_05_AGE_ACTIVE  = 5;

   public static final int   INDEX_06_AGE_PASSIVE = 6;

   public static final int   INDEX_07_OPS         = 7;

   public static final int   INDEX_08_TYPE        = 8;

   public static final int   INDEX_09_PRICE       = 9;

   public static final int   INDEX_10_PRIVATE     = 10;

   public static final int   INDEX_11_SELLER      = 11;

   public static final int   INDEX_12_LOCK        = 12;

   public static final int   INDEX_13_DATA        = 13;

   public static final int   INDEX_14_SEAL        = 14;

   /**
    * Default configuration with all possible columns. Used for fast switch
    */
   public static final int   NUM_COLUMNS          = 15;

   /**
    * 
    */
   private static final long serialVersionUID     = -4962292913762595283L;

   public ModelTableAccountFullData(PascalSwingCtx psc) {
      super(psc, NUM_COLUMNS);

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.account.coltitle.");
      columnModel.setKeyPrefixTip("table.account.coltip.");

      columnModel.setString(INDEX_02_PUBLICKEY, "keyname");
      columnModel.setString(INDEX_04_NAME, "name");
      columnModel.setString(INDEX_13_DATA, "data");
      columnModel.setString(INDEX_14_SEAL, "seal");

      columnModel.setInteger(INDEX_00_ACCOUNT, "account");
      columnModel.setInteger(INDEX_01_CHECKSUM, "cks");
      columnModel.setInteger(INDEX_05_AGE_ACTIVE, "agea");
      columnModel.setInteger(INDEX_06_AGE_PASSIVE, "agep");
      columnModel.setInteger(INDEX_07_OPS, "ops");
      columnModel.setInteger(INDEX_08_TYPE, "type");
      columnModel.setInteger(INDEX_11_SELLER, "seller");
      columnModel.setInteger(INDEX_12_LOCK, "lock");

      columnModel.setDouble(INDEX_09_PRICE, "price");
      columnModel.setDouble(INDEX_03_BALANCE, "balance");
      columnModel.setBoolean(INDEX_10_PRIVATE, "private");

      //check if all columns have been initialized
      //#debug
      columnModel.sanityCheck();
   }

   public int getColumnIndexAccount() {
      return INDEX_00_ACCOUNT;
   }

   public int getColumnIndexAccountName() {
      return INDEX_04_NAME;
   }

   public int getColumnIndexAgeActive() {
      return INDEX_05_AGE_ACTIVE;
   }

   public int getColumnIndexAgePassive() {
      return INDEX_06_AGE_PASSIVE;
   }

   public int getColumnIndexChecksum() {
      return INDEX_01_CHECKSUM;
   }

   public int getColumnIndexData() {
      return INDEX_13_DATA;
   }

   public int getColumnIndexKey() {
      return INDEX_02_PUBLICKEY;
   }

   public int getColumnIndexOps() {
      return INDEX_07_OPS;
   }

   public int getColumnIndexPrice() {
      return INDEX_09_PRICE;
   }

   public int getColumnIndexSeal() {
      return INDEX_14_SEAL;
   }

   public int getColumnIndexSeller() {
      return INDEX_11_SELLER;
   }

   public Object getValueAt(int row, int col) {
      Account a = getRow(row);
      if (a == null) {
         return null;
      }
      switch (col) {
         case INDEX_00_ACCOUNT:
            //account
            return a.getAccount();
         case INDEX_01_CHECKSUM:
            //account checksum
            return psc.getPCtx().calculateChecksum(a.getAccount());
         case INDEX_04_NAME:
            return a.getName();
         case INDEX_03_BALANCE:
            return a.getBalance();
         case INDEX_07_OPS:
            return a.getnOperation();
         case INDEX_08_TYPE:
            return a.getType();
         case INDEX_09_PRICE:
            return a.getPrice();
         case INDEX_10_PRIVATE:
            return a.getPrivateSale();
         case INDEX_11_SELLER:
            return a.getSellerAccount();
         case INDEX_12_LOCK:
            return a.getLockedUntilBlock();
         case INDEX_14_SEAL:
            return a.getSeal();
         case INDEX_13_DATA:
            return a.getData();
         case INDEX_05_AGE_ACTIVE:
            return psc.getPCtx().getRPCConnection().getLastBlockMined() - a.getUpdatedBlockActive();
         case INDEX_06_AGE_PASSIVE:
            return psc.getPCtx().getRPCConnection().getLastBlockMined() - a.getUpdatedBlockPassive();
         case INDEX_02_PUBLICKEY:
            //owner
            return getPublicKeyName(a);
         default:
            return "";
      }
   }

}