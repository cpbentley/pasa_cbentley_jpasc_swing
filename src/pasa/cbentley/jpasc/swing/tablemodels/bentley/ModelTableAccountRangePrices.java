/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import java.awt.Color;

import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.utils.AccountRange;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

/**
 * Compute ranges of contiguous accounts
 * 
 * @author Charles Bentley
 *
 */
public class ModelTableAccountRangePrices extends ModelTableAccountAbstract {

   public static final int   INDEX_00_ACCOUNT           = 0;

   public static final int   INDEX_01_CHECKSUM          = 1;

   public static final int   INDEX_02_PUBLICKEY         = 2;

   public static final int   INDEX_03_BALANCE           = 3;

   public static final int   INDEX_04_NAME              = 4;

   public static final int   INDEX_05_AGEA              = 5;

   public static final int   INDEX_06_AGEP              = 6;

   public static final int   INDEX_07_OPS               = 7;

   public static final int   INDEX_08_TYPE              = 8;

   public static final int   INDEX_09_PRICE             = 9;

   public static final int   INDEX_10_PRIVATE           = 10;

   public static final int   INDEX_11_SELLER            = 11;

   public static final int   INDEX_12_LOCK              = 12;

   public static final int   INDEX_13_RANGE_SIZE        = 13;

   private static final int  INDEX_14_RANGE_DIST_BEFORE = 14;

   private static final int  INDEX_15_RANGE_DIST_AFTER  = 15;

   public static final int   INDEX_16_LONELINESS        = 16;

   public static final int   INDEX_17_PACK              = 17;

   public static final int   INDEX_18_CLOSE             = 18;

   /**
    * special not a column. must be last. does not count to the num of columns
    */
   public static final int   INDEX_19_RANGE_COLOR       = 19;

   /**
    * Default configuration with all possible columns. Used for fast switch
    */
   public static final int   NUM_COLUMNS                = 19;

   /**
    * 
    */
   private static final long serialVersionUID           = -4962292913762595283L;

   private AccountRange      currentRange;

   public ModelTableAccountRangePrices(PascalSwingCtx psc) {
      super(psc, NUM_COLUMNS);

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.account.coltitle.");
      columnModel.setKeyPrefixTip("table.account.coltip.");

      columnModel.setString(INDEX_02_PUBLICKEY, "keyname");
      columnModel.setString(INDEX_04_NAME, "name");

      columnModel.setInteger(INDEX_01_CHECKSUM, "cks");
      columnModel.setInteger(INDEX_00_ACCOUNT, "account");
      columnModel.setInteger(INDEX_12_LOCK, "lock");
      columnModel.setInteger(INDEX_07_OPS, "ops");
      columnModel.setInteger(INDEX_08_TYPE, "type");
      columnModel.setInteger(INDEX_11_SELLER, "seller");
      columnModel.setInteger(INDEX_05_AGEA, "agea");
      columnModel.setInteger(INDEX_06_AGEP, "agep");

      columnModel.setDouble(INDEX_09_PRICE, "price");
      columnModel.setDouble(INDEX_03_BALANCE, "balance");
      columnModel.setBoolean(INDEX_10_PRIVATE, "private");
      columnModel.setInteger(INDEX_13_RANGE_SIZE, "rangesize");
      columnModel.setInteger(INDEX_14_RANGE_DIST_BEFORE, "rangedistbefore");
      columnModel.setInteger(INDEX_15_RANGE_DIST_AFTER, "rangedistafter");
      columnModel.setInteger(INDEX_16_LONELINESS, "loneliness");
      columnModel.setInteger(INDEX_17_PACK, "pack");
      columnModel.setInteger(INDEX_18_CLOSE, "close");

      //check if all columns have been initialized
      //#debug
      columnModel.sanityCheck();
   }

   protected void computeStats(Account a, int row) {
      super.computeStats(a, row);

      computeStatStreak(a, row);

   }

   private void computeStatStreak(Account accountAtRow, int row) {
      Integer accountAtRowInteger = accountAtRow.getAccount();
      //#debug
      //toDLog().pFlow("account="+account + " row="+row, null, ModelTableAccountRangePrices.class, "computeStats", LVL_05_FINE, true);
      if (row == 0) {
         //first row
         AccountRange newRange = createNewRangeForAccount(accountAtRow);
         currentRange = newRange;
      } else {
         //we want to compute 
         int difference = accountAtRowInteger - currentRange.getAccountRangeEnd();
         boolean isInSameRange = difference == 1;
         if (isInSameRange) {
            currentRange.setRangeEnd(accountAtRowInteger); //range is is auto computed
            accountAtRow.setObjectSupport(currentRange);
         } else {
            //create a new range
            AccountRange newRange = createNewRangeForAccount(accountAtRow);
            currentRange.setRangeAfter(newRange);
            //set the color of the range based on its size
            if (currentRange.getRangeSizeValue() != 1) {
               psc.getIntToColor().incrementLighBgCarrouselIndex();
               Color color = psc.getIntToColor().getColorLightBgCarrousel(0);
               currentRange.setColor(color);
            }
            newRange.setRangeBefore(currentRange);
            currentRange = newRange;
         }
      }
   }

   private AccountRange createNewRangeForAccount(Account accountAtRow) {
      Integer accountInteger = accountAtRow.getAccount();
      AccountRange newRange = new AccountRange(psc, accountInteger, accountInteger);
      accountAtRow.setObjectSupport(newRange);
      return newRange;
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

   public int getColumnIndexChecksum() {
      return INDEX_01_CHECKSUM;
   }

   public int getColumnIndexKey() {
      return INDEX_02_PUBLICKEY;
   }

   public int getColumnIndexOps() {
      return INDEX_07_OPS;
   }

   public int getColumnIndexPack() {
      return INDEX_17_PACK;
   }

   public int getColumnIndexPrice() {
      return INDEX_09_PRICE;
   }

   public int getColumnIndexSeller() {
      return INDEX_11_SELLER;
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
         case INDEX_09_PRICE:
            return a.getPrice();
         case INDEX_10_PRIVATE:
            return a.getPrivateSale();
         case INDEX_11_SELLER:
            return a.getSellerAccount();
         case INDEX_12_LOCK:
            return a.getLockedUntilBlock();
         case INDEX_05_AGEA:
            return psc.getPCtx().getRPCConnection().getLastBlockMined() - a.getUpdatedBlockActive();
         case INDEX_06_AGEP:
            return psc.getPCtx().getRPCConnection().getLastBlockMined() - a.getUpdatedBlockPassive();
         case INDEX_02_PUBLICKEY:
            //owner
            return getPublicKeyName(a);
         case INDEX_13_RANGE_SIZE: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getRangeSizeObject();
            }
         }
         case INDEX_14_RANGE_DIST_BEFORE: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getDistanceRangeBeforeInteger();
            }
         }
         case INDEX_15_RANGE_DIST_AFTER: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getDistanceRangeAfterInteger();
            }
         }
         case INDEX_16_LONELINESS: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getLoneliness();
            }
         }
         case INDEX_19_RANGE_COLOR: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getColor();
            }
         }
         case INDEX_17_PACK: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getPackInteger();
            }
         }
         case INDEX_18_CLOSE: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getCloseInteger();
            }
         }
         default:
            return "";
      }
   }

   public int getColumnIndexAgePassive() {
      // TODO Auto-generated method stub
      return 0;
   }

}