/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import java.util.ArrayList;
import java.util.List;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.AccountState;

import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelTableBAbstractWithColModel;

public abstract class ModelTableAccountAbstract extends ModelTableBAbstractWithColModel<Account> {

   /**
    * 
    */
   private static final long serialVersionUID           = -6460134786804551436L;

   private int               accountWithPositiveBalance = 0;

   /**
    * Counts the number of owners/pk
    */
   private int               nameCount                  = 0;

   private int               numPrivates                = 0;

   private int               numSales                   = 0;

   /**
    * Number of differents public keys listed
    */
   private int               ownerCount                 = 0;

   private List<String>      ownerNamess                = new ArrayList<>();

   protected PascalSwingCtx  psc;

   /**
    * Current block
    */
   protected int             referenceBlock;

   private PascalCoinValue   totalBalanceCount;

   /**
    * RPC, Snapshot
    */
   private int               type;

   public ModelTableAccountAbstract(PascalSwingCtx psc, int numCols) {
      super(psc.getSwingCtx(), numCols);
      this.psc = psc;
      totalBalanceCount = psc.getPCtx().getZero();

   }

   public void clear() {
      //clear stats
      nameCount = 0;
      numPrivates = 0;
      numSales = 0;
      totalBalanceCount = psc.getPCtx().getZero();
      accountWithPositiveBalance = 0;
      super.clear();
   }

   protected void computeStats(Account a, int row) {
      if (a == null) {
         return;
      }
      //compute number of unique public keys
      //toDO implements a special data structure
      //ownerNamess.add(null);

      Double d = a.getBalance();
      if (d != null) {
         double number = d.doubleValue(); // you have this
         if (number != 0) {
            accountWithPositiveBalance++;
            totalBalanceCount = totalBalanceCount.add(psc.getPCtx().create(d));
         }
      }
      AccountState as = a.getState();
      if (as == AccountState.LISTED) {
         numSales++;
         Boolean isPrivate = a.getPrivateSale();
         if (isPrivate != null) {
            if (isPrivate.booleanValue()) {
               numPrivates++;
            }
         }
      }
      String name = a.getName();
      if (name != null && !name.equals("")) {
         nameCount++;
      }
   }

   public abstract int getColumnIndexAccount();

   public abstract int getColumnIndexAccountName();

   public abstract int getColumnIndexAge();

   public abstract int getColumnIndexKey();

   public abstract int getColumnIndexOps();

   public abstract int getColumnIndexPrice();

   public int getNameCount() {
      return nameCount;
   }

   public int getNumAccounts() {
      return getRowCount();
   }

   public int getNumAccountWithPositiveBalance() {
      return accountWithPositiveBalance;
   }

   public int getNumPrivates() {
      return numPrivates;
   }

   public int getNumSales() {
      return numSales;
   }

   public String getPublicKeyName(Account a) {
      String pub = a.getEncPubkey();
      return psc.getPCtx().getPkNameStore().getKeyName(pub);
   }

   public double getTotalBalanceCount() {
      return totalBalanceCount.getDouble();
   }
}