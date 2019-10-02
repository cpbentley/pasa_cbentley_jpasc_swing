/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import java.util.List;

import com.github.davidbolet.jpascalcoin.api.model.Account;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * 
 * Find top donators to a given account until a given condition.
 * 
 * 
 * @author Charles Bentley
 *
 */
public class WorkerTopAccountDonators extends PanelSwingWorker<ModelTableAccountAbstract, Account> {


   private final ModelTableAccountAbstract tableModel;


   private PascalSwingCtx psc;

   public WorkerTopAccountDonators(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel) {
      super(psc.getSwingCtx(),wp);
      this.psc = psc;
      this.tableModel = tableModel;
   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   @Override
   protected ModelTableAccountAbstract doInBackground() {

      
      return null;
   }

   @Override
   protected void process(List<Account> chunks) {
      
   }
}