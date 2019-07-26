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

import com.github.davidbolet.jpascalcoin.api.model.Account;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;

public class PasaBuySelection implements IStringable {

   private PascalSwingCtx     psc;

   private PascalCoinValue    totalPrice;

   private ArrayList<Account> list;

   public PasaBuySelection(PascalSwingCtx psc) {
      this.psc = psc;
      totalPrice = psc.getPCtx().getZero();
      list = new ArrayList<Account>();
   }

   public List<Account> getList() {
      return list;
   }

   public void compute(JTable tablePrice, ModelTableAccountAbstract model, PascalCoinValue fee) {
      PCoreCtx pc = psc.getPCtx();
      int[] rows = tablePrice.getSelectedRows();
      for (int i = 0; i < rows.length; i++) {
         int row = rows[i];
         int rowModel = tablePrice.convertRowIndexToModel(row);
         Account ac = model.getRow(rowModel);
         //make sure it is sellable
         if (ac.getPrice() != null && ac.getPrice() > 0) {
            list.add(ac);
            totalPrice = totalPrice.add(new PascalCoinValue(pc, ac.getPrice()));
            totalPrice = totalPrice.add(fee);
         }
      }
   }

   public void setExtraFunding(Double extraFunding) {

   }

   public double getPriceTotal() {
      return totalPrice.getDouble();
   }

   public void sortByPrice() {
      Collections.sort(list, new Comparator<Account>() {

         public int compare(Account o1, Account o2) {
            if (o1.getPrice() > o2.getPrice()) {
               return 1;
            }
            if (o1.getPrice() < o2.getPrice()) {
               return -1;
            }
            if (o1.getAccount() > o2.getAccount()) {
               return 1;
            }
            if (o1.getAccount() < o2.getAccount()) {
               return -1;
            }
            //#debug
            psc.toDLog().pFlow("Should never happen. It means 2 same accounts accounts in the list " + o1 + " and " + o2, PasaBuySelection.this, PasaBuySelection.class, "compare", ITechLvl.LVL_09_WARNING, false);
            return 0;
         }
      });
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PasaBuySelection");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PasaBuySelection");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

}
