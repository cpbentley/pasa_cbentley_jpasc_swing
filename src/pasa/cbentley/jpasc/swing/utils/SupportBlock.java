/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;

public class SupportBlock implements IStringable {

   private BlockRange       blockRange;

   private String           minerKey;

   private MinerStat        minerStat;

   protected final PCoreCtx pc;

   private long             timeBlockDiff;

   private String           timeBlockDiffStr;

   private String timeStr;

   public SupportBlock(PCoreCtx pc) {
      this.pc = pc;
   }

   public BlockRange getBlockRange() {
      return blockRange;
   }

   public String getMinerKey() {
      return minerKey;
   }

   public MinerStat getMinerStat() {
      return minerStat;
   }

   /**
    * Get block diff in seconds
    * @return
    */
   public long getTimeBlockDiff() {
      return timeBlockDiff;
   }

   public String getTimeBlockDiffStr() {
      return timeBlockDiffStr;
   }

   public void setBlockRange(BlockRange blockRange) {
      this.blockRange = blockRange;
   }

   public void setMinerKey(String minerKey) {
      this.minerKey = minerKey;
   }

   public void setMinerStat(MinerStat minerStat) {
      this.minerStat = minerStat;
   }
   
   
   
   public String getTimeStr() {
      return timeStr;
   }
   public void setTimeStr(String timeStr) {
      this.timeStr = timeStr;
   }

   /**
    * Block diff in seconds
    * @param timeBlockDiff
    */
   public void setTimeBlockDiff(long timeBlockDiff) {
      this.timeBlockDiff = timeBlockDiff;
   }

   public void setTimeBlockDiffStr(String timeBlockDiffStr) {
      this.timeBlockDiffStr = timeBlockDiffStr;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "SupportBlock");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SupportBlock");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return pc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }

 

   //#enddebug

}
