/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.block;

import java.util.List;

import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.listlisteners.IListListener;
import pasa.cbentley.jpasc.pcore.task.ListTask;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWallet;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableBlockAbstract;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.model.ModelTableBAbstractArray;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * Stub worker for all Account listing workers.
 * 
 * @see WorkerTableWalletAccountAll
 * @author Charles Bentley
 *
 */
public abstract class WorkerTableBlockAbstract extends WorkerListTaskPage<ModelTableBlockAbstract, Block> implements IListListener<Block> {

   protected final ModelTableBlockAbstract tableModel;

   protected final PascalSwingCtx   psc;

   public WorkerTableBlockAbstract(PascalSwingCtx psc, IWorkerPanel wp, ModelTableBlockAbstract tableModel) {
      super(psc.getSwingCtx(), wp);
      this.psc = psc;
      this.tableModel = tableModel;
   }


   /**
    * Returns the {@link ModelTableBAbstractArray}
    */
   protected ModelTableBlockAbstract getModel() {
      return tableModel;
   }

   protected void process(List<Block> chunks) {
      tableModel.addRows(chunks);
      wp.panelSwingWorkerProcessed(this, chunks.size());
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableBlockAbstract");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableBlockAbstract");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}