/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.threads.WorkerStat;

public class WorkerStatOperations extends WorkerStat {

   public volatile int totalOperations     = 0;

   public volatile int operationsProcessed = 0;

   public volatile int blocksProcessed     = 0;

   public WorkerStatOperations(SwingCtx sc) {
      super(sc);
   }
}
