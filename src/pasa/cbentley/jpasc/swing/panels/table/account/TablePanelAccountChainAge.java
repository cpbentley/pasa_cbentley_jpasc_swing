/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountChainAge;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountChainAge extends TablePanelAccountAbstractAge implements IMyTab {

   public static final String KEY              = "list_ages";

   /**
    * 
    */
   private static final long  serialVersionUID = -3040964171971420256L;

   public TablePanelAccountChainAge(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, root, KEY);
   }

   protected WorkerTableAccountAbstract createWorker() {
      Integer min = panelMinMaxInteger.getMinInteger();
      Integer max = panelMinMaxInteger.getMaxInteger();
      return new WorkerTableAccountChainAge(psc, this, getTableModel(), min, max);
   }

 

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountChainAge");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountChainAge");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}