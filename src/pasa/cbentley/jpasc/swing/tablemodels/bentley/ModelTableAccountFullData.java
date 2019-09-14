/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import java.awt.Color;

import com.github.davidbolet.jpascalcoin.api.model.Account;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.utils.AccountRange;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

public class ModelTableAccountFullData extends ModelTableAccountAbstract {

   public static final int   INDEX_00_ACCOUNT   = 0;

   public static final int   INDEX_01_CHECKSUM  = 1;

   public static final int   INDEX_02_PUBLICKEY = 2;

   public static final int   INDEX_03_BALANCE   = 3;

   public static final int   INDEX_04_NAME      = 4;

   public static final int   INDEX_05_AGE       = 5;

   public static final int   INDEX_06_OPS       = 6;

   public static final int   INDEX_07_TYPE      = 7;

   public static final int   INDEX_08_PRICE     = 8;

   public static final int   INDEX_09_PRIVATE   = 9;

   public static final int   INDEX_10_SELLER    = 10;

   public static final int   INDEX_11_LOCK      = 11;

   /**
    * Default configuration with all possible columns. Used for fast switch
    */
   public static final int   NUM_COLUMNS        = 12;

   /**
    * 
    */
   private static final long serialVersionUID   = -4962292913762595283L;

   public ModelTableAccountFullData(PascalSwingCtx psc) {
      super(psc, NUM_COLUMNS);

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.account.coltitle.");
      columnModel.setKeyPrefixTip("table.account.coltip.");

      columnModel.setString(INDEX_02_PUBLICKEY, "keyname");
      columnModel.setString(INDEX_04_NAME, "name");

      columnModel.setInteger(INDEX_01_CHECKSUM, "cks");
      columnModel.setInteger(INDEX_00_ACCOUNT, "account");
      columnModel.setInteger(INDEX_11_LOCK, "lock");
      columnModel.setInteger(INDEX_06_OPS, "ops");
      columnModel.setInteger(INDEX_07_TYPE, "type");
      columnModel.setInteger(INDEX_10_SELLER, "seller");
      columnModel.setInteger(INDEX_05_AGE, "age");

      columnModel.setDouble(INDEX_08_PRICE, "price");
      columnModel.setDouble(INDEX_03_BALANCE, "balance");
      columnModel.setBoolean(INDEX_09_PRIVATE, "private");

      //check if all columns have been initialized
      //#debug
      columnModel.sanityCheck();
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
         case INDEX_06_OPS:
            return a.getnOperation();
         case INDEX_07_TYPE:
            return a.getType();
         case INDEX_08_PRICE:
            return a.getPrice();
         case INDEX_09_PRIVATE:
            return a.getPrivateSale();
         case INDEX_10_SELLER:
            return a.getSellerAccount();
         case INDEX_11_LOCK:
            return a.getLockedUntilBlock();
         case INDEX_05_AGE:
            return psc.getPCtx().getRPCConnection().getLastBlockMined() - a.getUpdatedB();
         case INDEX_02_PUBLICKEY:
            //owner
            return getPublicKeyName(a);
         default:
            return "";
      }
   }

   public int getColumnIndexAccount() {
      return INDEX_00_ACCOUNT;
   }

   public int getColumnIndexAccountName() {
      return INDEX_04_NAME;
   }

   public int getColumnIndexAge() {
      return INDEX_05_AGE;
   }

   public int getColumnIndexKey() {
      return INDEX_02_PUBLICKEY;
   }

   public int getColumnIndexChecksum() {
      return INDEX_01_CHECKSUM;
   }

   public int getColumnIndexOps() {
      return INDEX_06_OPS;
   }

   public int getColumnIndexPrice() {
      return INDEX_08_PRICE;
   }

   public int getColumnIndexSeller() {
      return INDEX_10_SELLER;
   }

}