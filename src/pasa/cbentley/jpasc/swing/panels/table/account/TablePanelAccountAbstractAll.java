/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.JPanel;
import javax.swing.SortOrder;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public abstract class TablePanelAccountAbstractAll extends TablePanelAccountAbstract implements IMyTab {

   /**
    * 
    */
   private static final long serialVersionUID = -3040964171971420256L;
   
   /**
    * 
    * @param psc
    * @param root
    * @param id
    */
   public TablePanelAccountAbstractAll(PascalSwingCtx psc, IRootTabPane root, String id) {
      super(psc, id, root);
   }

   /**
    * by default we just want a refresh
    */
   protected void subInitPanelNorth(JPanel north) {
      super.subInitPanelNorth(north);
   }
   
   /**
    * Sorts the table in {@link SortOrder#DESCENDING}
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
   }
   
 
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountAbstractAll");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountAbstractAll");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug



}