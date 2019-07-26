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

   private MinerStat        minerStat;

   protected final PCoreCtx pc;

   private long             timeBlockDiff;

   private String           timeBlockDiffStr;

   private String           minerKey;

   public SupportBlock(PCoreCtx pc) {
      this.pc = pc;
   }

   public BlockRange getBlockRange() {
      return blockRange;
   }

   public MinerStat getMinerStat() {
      return minerStat;
   }

   public long getTimeBlockDiff() {
      return timeBlockDiff;
   }

   public void setBlockRange(BlockRange blockRange) {
      this.blockRange = blockRange;
   }

   public void setMinerStat(MinerStat minerStat) {
      this.minerStat = minerStat;
   }

   public void setTimeBlockDiff(long timeBlockDiff) {
      this.timeBlockDiff = timeBlockDiff;
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

   public String getTimeBlockDiffStr() {
      return timeBlockDiffStr;
   }

   public void setTimeBlockDiffStr(String timeBlockDiffStr) {
      this.timeBlockDiffStr = timeBlockDiffStr;
   }

   public String getMinerKey() {
      return minerKey;
   }

   public void setMinerKey(String minerKey) {
      this.minerKey = minerKey;
   }

   //#enddebug

}
