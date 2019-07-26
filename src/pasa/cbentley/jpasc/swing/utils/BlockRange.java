/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import java.awt.Color;

import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;

public class BlockRange {

   private Integer          rangeEnd;

   private Integer          rangeStart;

   private int              distanceRangeAfter;

   /**
    * computed on demand
    */
   private int              distanceRangeBefore;

   protected final PCoreCtx pc;

   private BlockRange       rangeAfter;

   private BlockRange       rangeBefore;

   private Integer          rangeSizeObject;

   private Integer          distanceRangeBeforeInteger;

   private Integer          distanceRangeAfterInteger;

   public BlockRange(PCoreCtx pc, Integer start, Integer end) {
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

   public BlockRange getRangeAfter() {
      return rangeAfter;
   }

   public BlockRange getRangeBefore() {
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

   public void setRangeAfter(BlockRange rangeAfter) {
      this.rangeAfter = rangeAfter;
   }

   public void setRangeBefore(BlockRange rangeBefore) {
      this.rangeBefore = rangeBefore;
   }
}
