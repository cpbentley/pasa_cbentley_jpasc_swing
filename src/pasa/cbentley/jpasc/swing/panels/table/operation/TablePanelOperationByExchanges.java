/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationFullData;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationByAccounts;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * 
 * Uses a list of exchange accounts.
 * 
 * <br>
 * <br>
 * Everytime a Block is published, this class parse the operations and append operations
 * that below to an exchange account.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelOperationByExchanges extends TablePanelOperationAbstract implements IMyTab, ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = -2535271381632584761L;


   //hardcoded poloniex account
   private Integer           account          = 86646;

   public TablePanelOperationByExchanges(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "operations_exchanges", root);
   }

   public void actionPerformed(ActionEvent e) {
   }


   protected ModelTableOperationAbstract createTableModel() {
      return new ModelTableOperationFullData(psc);
   }

   
   protected WorkerTableOperationAbstract createWorker() {
      return new WorkerTableOperationByAccounts(psc, getTableModel(), this, account);
   }

}