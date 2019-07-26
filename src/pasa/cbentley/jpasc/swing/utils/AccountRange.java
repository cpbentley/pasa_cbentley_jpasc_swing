/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import java.awt.Color;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;

public class AccountRange implements IStringable {

   private Integer          rangeEnd;

   private Integer          rangeStart;

   private Color            color;

   private int              distanceRangeAfter;

   /**
    * computed on demand
    */
   private int              distanceRangeBefore;

   /**
    * Number of holes around a 500
    */
   private Integer          loneliness;

   protected final PCoreCtx pc;

   private AccountRange     rangeAfter;

   private AccountRange     rangeBefore;

   private Integer          rangeSizeObject;

   private Integer          distanceRangeBeforeInteger;

   private Integer          distanceRangeAfterInteger;

   public AccountRange(PCoreCtx pc, Integer start, Integer end) {
      this.pc = pc;
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
            if(distance == 1) {
               //do nothing.. this is the best case scenario
            } else if(distance == 2) {
               //very good as well
               totalDistanceBefore += 1;
            } else {
               totalDistanceBefore += distance;
            }
            //next loop
            before = before.getRangeBefore();
            count += 1;
         }
         int totalDistanceAfter = 0;
         AccountRange after = this;
         count = 1;
         while (count < countDistance && after != null) {
            int distance = after.getDistanceRangeAfter();
            // a distance of 1 is zero
            if(distance == 1) {
               //do nothing.. this is the best case scenario
            } else if(distance == 2) {
               //very good as well
               totalDistanceAfter += 1;
            } else {
               totalDistanceAfter += distance;
            }
            //next loop
            after = after.getRangeAfter();
            count += 1;
         }
         //compute lone
         loneliness = new Integer(totalDistanceBefore + totalDistanceAfter);
      }
      return loneliness;
   }

   public AccountRange getRangeAfter() {
      return rangeAfter;
   }

   public AccountRange getRangeBefore() {
      return rangeBefore;
   }

   public Integer getRangeSizeObject() {
      if (rangeSizeObject == null) {
         int rangeSize = rangeEnd.intValue() - rangeStart.intValue() + 1;
         rangeSizeObject = new Integer(rangeSize);
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
      return pc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("rangeStart", rangeStart);
      dc.appendVarWithSpace("rangeEnd", rangeEnd);
   }

   //#enddebug

}
