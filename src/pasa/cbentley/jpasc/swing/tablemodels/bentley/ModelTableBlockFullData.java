/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import java.util.List;

import pasa.cbentley.jpasc.pcore.rpc.model.Block;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.utils.BlockRange;
import pasa.cbentley.jpasc.swing.utils.MinerStat;
import pasa.cbentley.jpasc.swing.utils.SupportBlock;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

public class ModelTableBlockFullData extends ModelTableBlockAbstract {

   public static final int INDEX_00_BLOCK        = 0;

   public static final int INDEX_01_DATE         = 1;

   public static final int INDEX_02_TIME         = 2;

   public static final int INDEX_03_OPERATIONS   = 3;

   public static final int INDEX_04_VOLUME       = 4;

   public static final int INDEX_05_REWARD       = 5;

   public static final int INDEX_06_FEE          = 6;

   public static final int INDEX_07_HASH_RATE    = 7;

   public static final int INDEX_08_MINER_STR    = 8;

   public static final int INDEX_09_PAYLOAD      = 9;

   public static final int INDEX_10_VERSION      = 10;

   public static final int INDEX_11_TARGET       = 11;

   public static final int INDEX_12_PROOF_WORK   = 12;

   public static final int INDEX_13_SAFEBOX_HASH = 13;

   public static final int NUM_COLUMNS           = 14;

   private BlockRange      currentRange;

   public ModelTableBlockFullData(PascalSwingCtx psc) {
      super(psc, NUM_COLUMNS);

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.block.coltitle.");
      columnModel.setKeyPrefixTip("table.block.coltip.");

      columnModel.setInteger(INDEX_00_BLOCK, "block");
      columnModel.setString(INDEX_01_DATE, "date");
      columnModel.setString(INDEX_02_TIME, "time");
      columnModel.setInteger(INDEX_03_OPERATIONS, "ops");
      columnModel.setDouble(INDEX_04_VOLUME, "volume");
      columnModel.setDouble(INDEX_05_REWARD, "reward");
      columnModel.setDouble(INDEX_06_FEE, "fee");
      columnModel.setLong(INDEX_07_HASH_RATE, "hashrate");
      columnModel.setString(INDEX_08_MINER_STR, "minerstat");
      columnModel.setString(INDEX_09_PAYLOAD, "payload");
      columnModel.setString(INDEX_10_VERSION, "version");
      columnModel.setString(INDEX_11_TARGET, "target");
      columnModel.setString(INDEX_12_PROOF_WORK, "pow");
      columnModel.setString(INDEX_13_SAFEBOX_HASH, "safeboxhash");

   }

   public void computeStatsGlobal() {

      List<Block> blocks = getAllDataReference();
      int rowLast = getRowCount() - 1;
      int row = 0;
      for (Block blockAtRow : blocks) {

         //#debug
         toDLog().pFlow("block " + blockAtRow.getBlock() + " row=" + row, null, ModelTableBlockFullData.class, "computeStatsGlobal", LVL_05_FINE, true);
         //support was created during computeStats above
         SupportBlock supportBlock = (SupportBlock) blockAtRow.getObjectSupport();
         Integer accountAtRowInteger = blockAtRow.getBlock();
         //TODO this depends on the ordering of incoming blocks
         if (row != rowLast) {
            //we must take the block not from the row 
            Block blockBefore = getRow(row + 1);
            SupportBlock supportBlockBefore = (SupportBlock) blockBefore.getObjectSupport();
            long diffUnixTime = blockAtRow.getTimestamp().longValue() - blockBefore.getTimestamp().longValue();
            String timeBlockDiffStr = psc.getBlockTimeUIThread(diffUnixTime);
            supportBlock.setTimeBlockDiff(diffUnixTime);
            supportBlock.setTimeBlockDiffStr(timeBlockDiffStr);

            //was set during creation
            supportBlock.getMinerStat().addBlockTime((int) diffUnixTime);

            if (row == 0) {
               //first row
               BlockRange newRange = createNewRangeForAccount(blockAtRow, supportBlock);
               currentRange = newRange;
            } else {
               //we want to compute 
               boolean isInSameRange = supportBlockBefore.getMinerKey().equals(supportBlock.getMinerKey());
               if (isInSameRange) {
                  currentRange.setRangeEnd(accountAtRowInteger); //range is is auto computed
                  supportBlock.setBlockRange(currentRange);
               } else {
                  //create a new range
                  BlockRange newRange = createNewRangeForAccount(blockAtRow, supportBlock);
                  currentRange.setRangeAfter(newRange);
                  newRange.setRangeBefore(currentRange);
                  currentRange = newRange;
               }
            }
         }
         row += 1; //increment
      }

      //update miner stats
      int numBlocks = rowLast + 1;
      for (MinerStat miner : minersCount.values()) {
         int value = miner.getNumBlockMinedValue();
         int percent = (value * 100) / numBlocks;
         miner.setPercent(percent);

         miner.computeStatString();
      }

   }

   private BlockRange createNewRangeForAccount(Block accountAtRow, SupportBlock support) {
      Integer accountInteger = accountAtRow.getBlock();
      BlockRange newRange = new BlockRange(psc.getPCtx(), accountInteger, accountInteger);
      psc.getIntToColor().incrementLighBgCarrouselIndex();
      support.setBlockRange(newRange);
      return newRange;
   }

   public int getColumnIndexBlock() {
      return INDEX_00_BLOCK;
   }

   public int getColumnIndexBlockTime() {
      return INDEX_02_TIME;
   }

   public int getColumnIndexMiner() {
      return INDEX_09_PAYLOAD;
   }

   public int getColumnIndexMinerStat() {
      return INDEX_08_MINER_STR;
   }

   public int getColumnIndexOps() {
      return INDEX_03_OPERATIONS;
   }

   /**
    * Called by Table when displaying rows
    */
   public Object getValueAt(int row, int col) {
      Block a = getRow(row);
      if (a == null) {
         return null;
      }
      SupportBlock support = null;
      switch (col) {
         case INDEX_00_BLOCK:
            return a.getBlock();
         case INDEX_01_DATE:
            support = (SupportBlock) a.getObjectSupport();
            if (support != null) {
               return support.getTimeStr();
            } else {
               return null;
            }
         case INDEX_02_TIME:
            support = (SupportBlock) a.getObjectSupport();
            if (support != null) {
               return support.getTimeBlockDiffStr();
            } else {
               return null;
            }
         case INDEX_03_OPERATIONS:
            Integer i = a.getOperationCount();
            return i;
         case INDEX_06_FEE:
            Double d = a.getFee();
            return d;
         case INDEX_05_REWARD:
            return a.getReward();
         case INDEX_11_TARGET:
            return a.getCompactTarget();
         case INDEX_08_MINER_STR:
            support = (SupportBlock) a.getObjectSupport();
            if (support != null) {
               return support.getMinerStat().computeStatString();
            } else {
               return "";
            }
         case INDEX_10_VERSION:
            return a.getVersion();
         case INDEX_09_PAYLOAD:
            return a.getPayload();
         case INDEX_12_PROOF_WORK:
            return a.getProofOfWork();
         case INDEX_13_SAFEBOX_HASH:
            return a.getSafeBoxHash();
         case INDEX_07_HASH_RATE:
            return a.getLast50HashRateKhs();
         default:
            break;
      }
      return null;
   }
}
