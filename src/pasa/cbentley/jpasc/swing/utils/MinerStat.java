/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import java.util.concurrent.TimeUnit;

import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;

public class MinerStat {

   private Integer          blockTimeAverage;

   private Integer          blockTimeHighest;

   private int              blockTimeHighestValue;

   private Integer          blockTimeLowest;

   private int              blockTimeLowestValue = Integer.MAX_VALUE;

   private int              blockTimeTotalValue;

   private Integer          numBlockMined;

   private int              numBlockMinedValue;

   protected final PCoreCtx pc;

   private Integer          percent;

   private String           statStr;

   public MinerStat(PCoreCtx pc) {
      this.pc = pc;

   }

   public void addBlockTime(int diffUnitTime) {
      if (diffUnitTime > blockTimeHighestValue) {
         blockTimeHighestValue = diffUnitTime;
      }
      if (diffUnitTime < blockTimeLowestValue) {
         blockTimeLowestValue = diffUnitTime;
      }
      blockTimeTotalValue += diffUnitTime;
   }

   public Integer getBlockTimeAverage() {
      if (blockTimeAverage == null) {
         blockTimeAverage = new Integer(blockTimeTotalValue / numBlockMinedValue);
      }
      return blockTimeAverage;
   }

   public Integer getBlockTimeHighest() {
      if (blockTimeHighest == null) {
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
   public String computeStatString() {
      if (statStr == null) {
         String timeBlocAverage = getBlockTimeUIThread(getBlockTimeAverage());
         String timeBlockHigh = getBlockTimeUIThread(getBlockTimeHighest());
         String timeBlockLow = getBlockTimeUIThread(getBlockTimeLowest());
         statStr = "#" + numBlockMinedValue + " " + percent + "%" + " avg:" + timeBlocAverage + " high:" + timeBlockHigh + " low:" + timeBlockLow;
      }
      return statStr;
   }

   public String getBlockTimeUIThread(long diffUnixTime) {
      StringBBuilder blockTimeBuilder = new StringBBuilder();
      long millis = diffUnixTime * 1000;
      int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(millis);
      int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes));
      //Formatter fr = new Formatter();
      blockTimeBuilder.append(minutes);
      blockTimeBuilder.append(',');
      int c = blockTimeBuilder.getCount();
      blockTimeBuilder.append(seconds);
      if (c + 1 == blockTimeBuilder.getCount()) {
         blockTimeBuilder.append('0');
      }
      String str = blockTimeBuilder.toString();
      blockTimeBuilder.reset();
      return str;
   }

}
