/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.whale;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

public abstract class WorkerTableWhaleWatchAbstract extends WorkerTableOperationAbstract {
   
   /**
    * Whale watching look up blocks in the past from current block
    */
   protected Integer numBlocks;

   public WorkerTableWhaleWatchAbstract(PascalSwingCtx psc, IWorkerPanel wp, ModelTableOperationAbstract tableModel) {
      super(psc, wp, tableModel);
   }

}