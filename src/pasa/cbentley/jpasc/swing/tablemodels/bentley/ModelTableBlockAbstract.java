/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import java.util.HashMap;

import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.utils.MinerStat;
import pasa.cbentley.jpasc.swing.utils.SupportBlock;
import pasa.cbentley.swing.model.ModelTableBAbstractWithColModel;

public abstract class ModelTableBlockAbstract extends ModelTableBAbstractWithColModel<Block> {

   /**
    * 
    */
   private static final long            serialVersionUID = 8477950625426394691L;

   private int                          totalBlocks;

   private PascalCoinValue              totalFees;

   private int                          totalOps;

   private PascalCoinValue              totalVolume;

   private long                         totalHashRate;

   protected PascalSwingCtx             psc;

   /**
    * Current block
    */
   protected int                        referenceBlock;

   private PascalCoinValue              totalBalanceCount;

   protected HashMap<String, MinerStat> minersCount;

   /**
    * RPC, Snapshot
    */
   private int                          type;

   public ModelTableBlockAbstract(PascalSwingCtx psc, int numCols) {
      super(psc.getSwingCtx(), numCols);
      this.psc = psc;
      totalBalanceCount = psc.getPCtx().getZero();
      minersCount = new HashMap<>();
   }

   public abstract int getColumnIndexBlock();
   
   public abstract int getColumnIndexBlockTime();
   public abstract int getColumnIndexMinerStat();
   public abstract int getColumnIndexOps();

   public int getTotalOps() {
      return totalOps;
   }

   public double getTotalFees() {
      return totalFees.getDouble();
   }

   public double getTotalVolume() {
      return totalVolume.getDouble();
   }

   public long getTotalHashRate() {
      return totalHashRate;
   }

   public int getAverageHashRate() {
      if (totalBlocks == 0) {
         return 0;
      }
      return (int) (totalHashRate / totalBlocks);
   }

   /**
    * Number of blocks
    * @return
    */
   public int getTotalBlocks() {
      return totalBlocks;
   }

   public void clear() {
      //clear stats
      totalBlocks = 0;
      totalFees = psc.getPCtx().getZero();
      totalVolume = psc.getPCtx().getZero();
      totalOps = 0;
      totalHashRate = 0;
      minersCount.clear();
      super.clear();
   }

   protected void computeStats(Block a, int row) {
      if (a == null) {
         return;
      }
      Double d = a.getFee();
      if (d != null) {
         totalFees = totalFees.add(psc.getPCtx().create(d));
      }
      Integer i = a.getOperationCount();
      if (i != null) {
         totalOps = (totalOps + i);
      }
      totalBlocks = (totalBlocks + 1);
      totalHashRate += a.getLast50HashRateKhs();

      //TODO optimization make key unique
      String minerKey = a.getPayload().substring(0, 6);
      MinerStat minerStat = minersCount.get(minerKey);
      if (minerStat == null) {
         minerStat = new MinerStat(psc.getPCtx());
         minersCount.put(minerKey, minerStat);
      }
      minerStat.incrementNumBlockMined();

      SupportBlock supportBlock = new SupportBlock(psc.getPCtx());
      a.setObjectSupport(supportBlock);

      supportBlock.setMinerKey(minerKey);
      supportBlock.setMinerStat(minerStat);
   }

   public abstract int getColumnIndexMiner();

}