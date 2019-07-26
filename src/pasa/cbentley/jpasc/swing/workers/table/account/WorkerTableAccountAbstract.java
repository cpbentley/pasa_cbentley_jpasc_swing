/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.table.account;

import java.util.List;

import com.github.davidbolet.jpascalcoin.api.model.Account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.filter.SetFilterKey;
import pasa.cbentley.jpasc.pcore.listlisteners.IListListener;
import pasa.cbentley.jpasc.pcore.task.ListTask;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.ListTaskAccountAbstract;
import pasa.cbentley.jpasc.pcore.task.list.dbolet.account.wallet.ListTaskAccountWallet;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
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
public abstract class WorkerTableAccountAbstract extends WorkerListTaskPage<ModelTableAccountAbstract, Account> implements IListListener<Account> {

   protected final PascalSwingCtx                    psc;

   /**
    * Since Key is truly universal to accounts, it is defined here.
    */
   protected SetFilterKey                            setFilterKey;

   protected final ModelTableAccountAbstract tableModel;

   public WorkerTableAccountAbstract(PascalSwingCtx psc, IWorkerPanel wp, ModelTableAccountAbstract tableModel) {
      super(psc.getSwingCtx(), wp);
      this.psc = psc;
      this.tableModel = tableModel;
   }

   /**
    * Create the specific {@link ListTask} of Accounts.
    * <br>
    * This is a {@link ListTask} defined in the UI agnostic module pascalcoin.javacore.
    * Examples are
    * <li> {@link ListTaskAccountWallet}
    */
   protected ListTaskPage<Account> createTaskPage() {
      ListTaskAccountAbstract task = createTaskAccount();
      task.setFilterKeySet(setFilterKey);
      return task;
   }

   protected abstract ListTaskAccountAbstract createTaskAccount();
   /**
    * Lazy creation.. Used by outside.
    * Subclass check if null directly.
    * @return
    */
   public SetFilterKey createGetKeyFilter() {
      if (setFilterKey == null) {
         setFilterKey = new SetFilterKey(psc.getPCtx());
      }
      return setFilterKey;
   }

   /**
    * Returns the {@link ModelTableBAbstractArray}
    */
   protected ModelTableAccountAbstract getModel() {
      return tableModel;
   }

   protected void process(List<Account> chunks) {
      tableModel.addRows(chunks);
      wp.panelSwingWorkerProcessed(this, chunks.size());
   }

   public void setFilterSet(SetFilterKey set) {
      this.setFilterKey = set;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableAccountAbstract");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableAccountAbstract");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}