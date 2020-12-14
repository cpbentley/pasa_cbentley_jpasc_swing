/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTable;

import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;

/**
 * List of PASAs that will buy
 * @author Charles Bentley
 *
 */
public class PasaBuyerSelection {

   private PascalSwingCtx     psc;

   private ArrayList<Account> list;

   public PasaBuyerSelection(PascalSwingCtx psc) {
      this.psc = psc;
      list = new ArrayList<Account>();
   }

   public void compute(JTable table, ModelTableAccountAbstract model, PascalCoinValue fee) {
      int[] rows = table.getSelectedRows();
      for (int i = 0; i < rows.length; i++) {
         int row = rows[i];
         int rowModel = table.convertRowIndexToModel(row);
         Account ac = model.getRow(rowModel);
         list.add(ac);
      }
   }

   public List<Account> getAccounts() {
      return list;
   }

   /**
    * Smallest balance first
    */
   public void sortByBalance() {
      Collections.sort(list, new Comparator<Account>() {

         public int compare(Account o1, Account o2) {
            if (o1.getBalance() < o2.getBalance()) {
               return 1;
            } else if (o1.getBalance() == o2.getBalance()) {
               return 0;
            } else {
               return -1;
            }
         }
      });
   }

}
