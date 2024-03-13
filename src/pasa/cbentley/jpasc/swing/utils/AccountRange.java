/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import java.awt.Color;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.CounterInt;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class AccountRange implements IStringable {

   private Integer                rangeEnd;

   private Integer                rangeStart;

   private Color                  color;

   private int                    distanceRangeAfter;

   /**
    * computed on demand
    */
   private int                    distanceRangeBefore;

   private CounterInt             packValue;

   private CounterInt             closeAfterCounter;

   private Integer                packInteger;

   /**
    * Number of holes around a 500
    */
   private Integer                loneliness;

   protected final PascalSwingCtx psc;

   private AccountRange           rangeAfter;

   private AccountRange           rangeBefore;

   private Integer                rangeSizeObject;

   private Integer                distanceRangeBeforeInteger;

   private Integer                distanceRangeAfterInteger;

   private Integer                closeInteger;

   public AccountRange(PascalSwingCtx psc, Integer start, Integer end) {
      this.psc = psc;
      this.rangeStart = start;
      this.rangeEnd = end;
   }

   /**
    * true if account ranged was initialized with the same reference for start and end
    * @return
    */
   public boolean isSingle() {
      return rangeStart == rangeEnd;
   }

   public Integer getAccountRangeEnd() {
      return rangeEnd;
   }

   public Integer getAccountRangeStart() {
      return rangeStart;
   }

   /**
    * Possibly null if not set
    * @return
    */
   public Color getColor() {
      return color;
   }

   public int getDistanceRangeAfter() {
      if (distanceRangeAfter == 0) {
         //compute it
         if (rangeAfter != null) {
            Integer rangeAfterStart = rangeAfter.getAccountRangeStart();
            distanceRangeAfter = rangeAfterStart.intValue() - this.rangeEnd.intValue() - 1;
         } else {
            distanceRangeAfter = 10;
         }
      }
      return distanceRangeAfter;
   }

   public int getDistanceRangeBefore() {
      if (distanceRangeBefore == 0) {
         //compute it
         if (rangeBefore != null) {
            Integer rangeEnd = rangeBefore.getAccountRangeEnd();
            distanceRangeBefore = this.rangeStart.intValue() - rangeEnd.intValue() - 1;
         } else {
            distanceRangeBefore = 10;
         }
      }
      return distanceRangeBefore;
   }

   public Integer getDistanceRangeAfterInteger() {
      if (distanceRangeAfterInteger == null) {
         distanceRangeAfterInteger = new Integer(getDistanceRangeAfter());
      }
      return distanceRangeAfterInteger;
   }

   public Integer getDistanceRangeBeforeInteger() {
      if (distanceRangeBeforeInteger == null) {
         distanceRangeBeforeInteger = new Integer(getDistanceRangeBefore());
      }
      return distanceRangeBeforeInteger;
   }

   /**
    * Cumulative distance over 200
    * @return
    */
   public Integer getLoneliness() {
      if (loneliness == null) {
         int countDistance = 4;
         int totalDistanceBefore = 0;
         AccountRange before = this;
         int count = 1;
         while (count < countDistance && before != null) {
            int distance = before.getDistanceRangeBefore();
            totalDistanceBefore += (distance - 1);
            //next loop
            before = before.getRangeBefore();
            count += 1;
         }
         int totalDistanceAfter = 0;
         AccountRange after = this;
         count = 1;
         while (count < countDistance && after != null) {
            int distance = after.getDistanceRangeAfter();
            totalDistanceAfter += (distance - 1);
            //next loop
            after = after.getRangeAfter();
            count += 1;
         }
         //compute lone
         loneliness = new Integer(totalDistanceBefore + totalDistanceAfter);
      }
      return loneliness;
   }

   /**
    * The number of accounts in adjacent ranges with the loniness
    * @return
    */
   public int getPackValue() {
      if (packValue == null) {
         int loneLiness = getLoneliness();
         int total = getRangeSizeValue();
         CounterInt ci = new CounterInt(psc.getUC(), total);
         AccountRange range = rangeAfter;
         do {
            range = recursivePack(loneLiness, ci, range, false);
         } while (range != null);

         range = rangeBefore;
         do {
            range = recursivePack(loneLiness, ci, range, true);
         } while (range != null);
         packValue = ci;
      }
      return packValue.getCount();
   }

   public int getCloseValue() {
      if (closeAfterCounter == null) {
         int distanceRangeAfterRoot = getDistanceRangeAfter();
         int total = getRangeSizeValue();
         CounterInt ci = new CounterInt(psc.getUC(), total);
         AccountRange range = rangeAfter;
         do {
            range = recursiveClose(distanceRangeAfterRoot, ci, range);
         } while (range != null);
         closeAfterCounter = ci;
      }
      return closeAfterCounter.getCount();
   }

   public Integer getCloseInteger() {
      if (closeInteger == null) {
         closeInteger = new Integer(getCloseValue());
      }
      return closeInteger;
   }

   public Integer getPackInteger() {
      if (packInteger == null) {
         packInteger = new Integer(getPackValue());
      }
      return packInteger;
   }

   private AccountRange recursiveClose(int distanceRangeAfterRoot, CounterInt total, AccountRange range) {
      if (range != null) {
         int distanceRangeAfter = range.getDistanceRangeAfter();
         if (distanceRangeAfter == distanceRangeAfterRoot) {
            int num = range.getRangeSizeValue();
            total.increment(num);
            range.closeAfterCounter = total;
            return range.rangeAfter;
         }
      }
      return null;
   }

   private AccountRange recursivePack(int lone, CounterInt total, AccountRange range, boolean isBefore) {
      if (range != null) {
         int afterLone = range.getLoneliness();
         if (afterLone == lone) {
            int num = range.getRangeSizeValue();
            total.increment(num);
            range.packValue = total;
            if (isBefore) {
               return range.rangeBefore;
            } else {
               return range.rangeAfter;
            }
         }
      }
      return null;
   }

   public AccountRange getRangeAfter() {
      return rangeAfter;
   }

   public AccountRange getRangeBefore() {
      return rangeBefore;
   }

   public int getRangeSizeValue() {
      return rangeEnd.intValue() - rangeStart.intValue() + 1;
   }

   public Integer getRangeSizeObject() {
      if (rangeSizeObject == null) {
         rangeSizeObject = new Integer(getRangeSizeValue());
      }
      return rangeSizeObject;
   }

   public void setRangeEnd(Integer rangeEnd) {
      this.rangeEnd = rangeEnd;
   }

   public void setRangeStart(Integer rangeStart) {
      this.rangeStart = rangeStart;
   }

   public void setColor(Color color) {
      this.color = color;
      //#debug
      //toDLog().pFlow("", this, AccountRange.class, "setColor", LVL_05_FINE, true);
   }

   public void setRangeAfter(AccountRange rangeAfter) {
      this.rangeAfter = rangeAfter;
   }

   public void setRangeBefore(AccountRange rangeBefore) {
      this.rangeBefore = rangeBefore;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "AccountRange");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AccountRange");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUC();
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("rangeStart", rangeStart);
      dc.appendVarWithSpace("rangeEnd", rangeEnd);
      dc.appendVarWithSpace("size", getRangeSizeValue());
      dc.appendVarWithSpace("color", psc.getSwingCtx().toSD().d1(color));
   }

   //#enddebug

}
