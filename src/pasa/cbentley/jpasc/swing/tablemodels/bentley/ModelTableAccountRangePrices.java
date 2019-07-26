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

   public static final int   INDEX_05_AGE               = 5;

   public static final int   INDEX_06_OPS               = 6;

   public static final int   INDEX_07_TYPE              = 7;

   public static final int   INDEX_08_PRICE             = 8;

   public static final int   INDEX_09_PRIVATE           = 9;

   public static final int   INDEX_10_SELLER            = 10;

   public static final int   INDEX_11_LOCK              = 11;

   public static final int   INDEX_12_RANGE_SIZE        = 12;

   private static final int  INDEX_13_RANGE_DIST_BEFORE = 13;

   public static final int   INDEX_16_RANGE_COLOR       = 16;

   private static final int  INDEX_14_RANGE_DIST_AFTER  = 14;

   public static final int   INDEX_15_LONELINESS        = 15;

   /**
    * Default configuration with all possible columns. Used for fast switch
    */
   public static final int   NUM_COLUMNS                = 16;

   /**
    * 
    */
   private static final long serialVersionUID           = -4962292913762595283L;

   private AccountRange currentRange;

   public ModelTableAccountRangePrices(PascalSwingCtx psc) {
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
      columnModel.setInteger(INDEX_12_RANGE_SIZE, "rangesize");
      columnModel.setInteger(INDEX_13_RANGE_DIST_BEFORE, "rangedistbefore");
      columnModel.setInteger(INDEX_14_RANGE_DIST_AFTER, "rangedistafter");
      columnModel.setInteger(INDEX_15_LONELINESS, "loneliness");

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
            newRange.setRangeBefore(currentRange);
            currentRange = newRange;
         }
      }
   }

   private AccountRange createNewRangeForAccount(Account accountAtRow) {
      Integer accountInteger = accountAtRow.getAccount();
      AccountRange newRange = new AccountRange(psc.getPCtx(), accountInteger, accountInteger);
      psc.getIntToColor().incrementLighBgCarrouselIndex();
      Color color = psc.getIntToColor().getColorLightBgCarrousel(0);
      newRange.setColor(color);
      accountAtRow.setObjectSupport(newRange);
      return newRange;
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

   public int getColumnIndexOps() {
      return INDEX_06_OPS;
   }

   public int getColumnIndexPrice() {
      return INDEX_08_PRICE;
   }

   public Object getValueAt(int row, int col) {
      //#never create an object here
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
         case INDEX_12_RANGE_SIZE: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getRangeSizeObject();
            }
         }
         case INDEX_13_RANGE_DIST_BEFORE: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getDistanceRangeBeforeInteger();
            }
         }
         case INDEX_14_RANGE_DIST_AFTER: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getDistanceRangeAfterInteger();
            }
         }
         case INDEX_15_LONELINESS: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getLoneliness();
            }
         }
         case INDEX_16_RANGE_COLOR: {
            Object o = a.getObjectSupport();
            if (o == null) {
               return null;
            } else {
               return ((AccountRange) o).getColor();
            }
         }
         default:
            return "";
      }
   }

}