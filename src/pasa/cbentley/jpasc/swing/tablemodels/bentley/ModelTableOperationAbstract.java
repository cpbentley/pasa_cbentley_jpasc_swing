/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import java.util.HashSet;

import pasa.cbentley.jpasc.pcore.rpc.model.Operation;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelTableBAbstractWithColModel;

public abstract class ModelTableOperationAbstract extends ModelTableBAbstractWithColModel<Operation> {

   HashSet<Integer>               accounts         = new HashSet<>();

   HashSet<Integer>               accountsReceiver = new HashSet<>();

   private PascalCoinValue        amountTotal;

   private int                    numAccounts;

   private int                    numOperations;

   protected final PascalSwingCtx psc;

   private PascalCoinValue        totalFees;

   private int                    numBytesPayload;

   public ModelTableOperationAbstract(PascalSwingCtx psc, int numCols) {
      super(psc.getSwingCtx(), numCols);
      this.psc = psc;

      totalFees = psc.getPCtx().getZero();
      amountTotal = psc.getPCtx().getZero();

   }

   public void clear() {
      //clear stats
      numAccounts = 0;
      numBytesPayload = 0;
      totalFees = psc.getPCtx().getZero();
      amountTotal = psc.getPCtx().getZero();
      numOperations = 0;
      accounts.clear();
      accountsReceiver.clear();
      super.clear();
   }

   public void computeStats(Operation a, int row) {
      Double d = a.getFee();
      if (d != null) {
         totalFees = totalFees.add(psc.getPCtx().create(d));
      }
      Double amount = a.getAmount();
      if (amount != null) {
         amountTotal = amountTotal.add(psc.getPCtx().create(amount));
      }
      String str = a.getPayLoad();
      if (str != null) {
         numBytesPayload += (str.length() / 2);
      }
      numOperations++;
      Integer ac = a.getAccount();
      if (!accounts.contains(ac)) {
         accounts.add(ac);
         numAccounts++;
      }
      Integer receiver = a.getDestAccount();
      if (receiver != null && !accountsReceiver.contains(receiver)) {
         accountsReceiver.add(receiver);
      }
   }

   public double getAmountTotal() {
      return 0 - amountTotal.getDouble();
   }

   public abstract int getColumnIndexAccount();

   public abstract int getColumnIndexAccountReceiver();

   public abstract int getColumnIndexAccountSender();

   public abstract int getColumnIndexAccountSigner();

   public abstract int getColumnIndexBlock();

   public abstract int getColumnIndexOpCount();

   public abstract int getColumnIndexTime();

   public abstract int getColumnIndexType();

   public abstract int getColumnIndexTypeSub();

   /**
    * Return the number of different accounts implicated in the returned operations
    * @return
    */
   public int getNumAccounts() {
      return numAccounts;
   }

   public int getNumBytesPayload() {
      return numBytesPayload;
   }

   public int getNumReceivers() {
      return accountsReceiver.size();
   }

   public int getNumOperations() {
      return numOperations;
   }

   public double getTotalFees() {
      return 0 - totalFees.getDouble();
   }
}