/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.filter.predicates.BlockPredicate;
import pasa.cbentley.jpasc.pcore.filter.predicates.PPredicate;
import pasa.cbentley.jpasc.pcore.interfaces.IObjectListener;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.BlockFinderTask;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * Finds a block according to some search constraints.
 * <br>
 * <li> The next non empty block with at least X txs...
 * <li>
 * @author Charles Bentley
 *
 */
public class WorkerBlockFinder extends PanelSwingWorker<String, Block> implements IObjectListener<Block> {

   private BlockPredicate blockPredicate;

   private PascalSwingCtx psc;

   private Block blockFound;
   
   public WorkerBlockFinder(PascalSwingCtx psc, BlockPredicate blockPredicate, IWorkerPanel wp) {
      super(psc.getSwingCtx(), wp);
      this.psc = psc;
      this.blockPredicate = blockPredicate;
   }

   public void newObjectAvailable(Block object, PPredicate predicate) {
      blockFound = object;
   }

   public void newObjectfail(Block object, PPredicate predicate) {

   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   public Block getBlockFound() {
      return blockFound;
   }

   protected String doInBackground() throws Exception {
      //give the predicate
      BlockFinderTask finderTask = new BlockFinderTask(psc.getPCtx(), this, blockPredicate);
      finderTask.runAbstract();
      return "";
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerBlockFinder");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerBlockFinder");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}