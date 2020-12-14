/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

/**
 * Compute ranges of contiguous accounts
 * 
 * @author Charles Bentley
 *
 */
public class ModelTableAccountSwapAccount extends ModelTableAccountAbstract {

   public static final int   INDEX_00_ACCOUNT       = 0;

   public static final int   INDEX_01_CHECKSUM      = 1;

   public static final int   INDEX_02_PUBLICKEY     = 2;

   public static final int   INDEX_03_BALANCE       = 3;

   public static final int   INDEX_04_NAME          = 4;

   public static final int   INDEX_05_AGEA          = 5;

   public static final int   INDEX_06_AGEP          = 6;

   public static final int   INDEX_07_OPS           = 7;

   public static final int   INDEX_08_TYPE          = 8;

   public static final int   INDEX_09_HASHED_SECRET = 9;

   public static final int   INDEX_10_NEW_KEY       = 10;

   public static final int   INDEX_11_LOCK          = 11;

   public static final int   INDEX_12_LOCK_DIFF     = 12;

   public static final int   INDEX_13_LOCK_TIME     = 13;

   /**
    * Default configuration with all possible columns. Used for fast switch
    */
   public static final int   NUM_COLUMNS            = 14;

   /**
    * 
    */
   private static final long serialVersionUID       = -4962292913762595283L;

   public ModelTableAccountSwapAccount(PascalSwingCtx psc) {
      super(psc, NUM_COLUMNS);

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.account.coltitle.");
      columnModel.setKeyPrefixTip("table.account.coltip.");

      columnModel.setString(INDEX_02_PUBLICKEY, "keyname");
      columnModel.setString(INDEX_04_NAME, "name");
      columnModel.setString(INDEX_10_NEW_KEY, "newkey");
      columnModel.setString(INDEX_09_HASHED_SECRET, "hashedsecret");
      columnModel.setString(INDEX_13_LOCK_TIME, "lock_time");

      columnModel.setDouble(INDEX_03_BALANCE, "balance");

      columnModel.setInteger(INDEX_00_ACCOUNT, "account");
      columnModel.setInteger(INDEX_01_CHECKSUM, "cks");
      columnModel.setInteger(INDEX_05_AGEA, "agea");
      columnModel.setInteger(INDEX_06_AGEP, "agep");
      columnModel.setInteger(INDEX_08_TYPE, "price");
      columnModel.setInteger(INDEX_07_OPS, "ops");
      columnModel.setInteger(INDEX_11_LOCK, "lock");
      columnModel.setInteger(INDEX_12_LOCK_DIFF, "lock_diff");

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
      return INDEX_05_AGEA;
   }

   public int getColumnIndexAgePasive() {
      return INDEX_06_AGEP;
   }

   public int getColumnIndexAgePassive() {
      return INDEX_06_AGEP;
   }

   public int getColumnIndexChecksum() {
      return INDEX_01_CHECKSUM;
   }

   public int getColumnIndexKey() {
      return INDEX_02_PUBLICKEY;
   }

   public int getColumnIndexOps() {
      return INDEX_07_OPS;
   }

   public int getColumnIndexPrice() {
      return -1;
   }

   public int getColumnIndexSeller() {
      return -1;
   }

   public Object getValueAt(int row, int col) {
      //never create an object here
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
         case INDEX_09_HASHED_SECRET:
            return a.getHashedSecret();
         case INDEX_10_NEW_KEY:
            return a.getNewEncPubkey();
         case INDEX_11_LOCK:
            return a.getSellerAccount();
         case INDEX_12_LOCK_DIFF:
            int diff = a.getLockedUntilBlock() - psc.getPCtx().getRPCConnection().getLastBlockMined();
            return diff;
         case INDEX_05_AGEA:
            return psc.getPCtx().getRPCConnection().getLastBlockMined() - a.getUpdatedBlockActive();
         case INDEX_06_AGEP:
            return psc.getPCtx().getRPCConnection().getLastBlockMined() - a.getUpdatedBlockPassive();
         case INDEX_02_PUBLICKEY:
            //owner
            return getPublicKeyName(a);
         default:
            return "";
      }
   }

}