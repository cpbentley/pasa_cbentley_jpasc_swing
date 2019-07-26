/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;

public class MinerStat {

   private Integer          blockTimeAverage;

   private Integer          blockTimeHighest;

   private int              blockTimeHighestValue;

   private Integer          blockTimeLowest;

   private int              blockTimeLowestValue;

   private int              blockTimeTotalValue;

   private Integer          numBlockMined;

   private int              numBlockMinedValue;

   protected final PCoreCtx pc;

   private Integer          percent;

   public MinerStat(PCoreCtx pc) {
      this.pc = pc;

   }

   public void addBlockTime(int diffUnitTime) {
      if (diffUnitTime > blockTimeHighestValue) {
         blockTimeHighestValue = diffUnitTime;
      }
      if (diffUnitTime < blockTimeLowestValue) {
         blockTimeHighestValue = diffUnitTime;
      }
      blockTimeTotalValue += diffUnitTime;
   }

   public Integer getBlockTimeAverage() {
      if(blockTimeAverage == null) {
         blockTimeAverage = new Integer(blockTimeTotalValue / numBlockMinedValue);
      }
      return blockTimeAverage;
   }

   public Integer getBlockTimeHighest() {
      if(blockTimeHighest == null) {
         blockTimeHighest = new Integer(blockTimeHighestValue);
      }
      return blockTimeHighest;
   }

   public Integer getBlockTimeLowest() {
      if (blockTimeLowest == null) {
         blockTimeLowest = new Integer(blockTimeLowestValue);
      }
      return blockTimeLowest;
   }

   public Integer getNumBlockMined() {
      if (numBlockMined == null) {
         numBlockMined = new Integer(numBlockMinedValue);
      }
      return numBlockMined;
   }

   public int getNumBlockMinedValue() {
      return numBlockMinedValue;
   }

   public Integer getPercent() {
      return percent;
   }

   public void incrementNumBlockMined() {
      numBlockMinedValue++;
      numBlockMined = null;
   }


   public void setPercent(Integer percent) {
      this.percent = percent;
   }

   /**
    * Compute a Str to display in a table
    */
   public void computeStatString() {
      
   }

}
