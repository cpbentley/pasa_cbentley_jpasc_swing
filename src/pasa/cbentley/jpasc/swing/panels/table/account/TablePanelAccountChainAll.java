/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.SortOrder;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountFullData;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountChainAll;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountChainAll extends TablePanelAccountAbstractAll implements IMyTab {

   /**
    * 
    */
   private static final long serialVersionUID = -3040964171971420256L;

   public TablePanelAccountChainAll(PascalSwingCtx psc, IRootTabPane root) {
      this(psc, root, "list_all");
   }

   /**
    * 
    * @param psc
    * @param root
    * @param id
    */
   public TablePanelAccountChainAll(PascalSwingCtx psc, IRootTabPane root, String id) {
      super(psc, root, id);
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountChainAll worker = new WorkerTableAccountChainAll(psc, getTableModel(), this);
      return worker;
   }

   /**
    * Sorts the table in {@link SortOrder#DESCENDING}
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      //we don't want sorting by default. 
   }

   public void initTab() {
      super.initTab();
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItemsNoSend(menu);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountChainAll");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountChainAll");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}