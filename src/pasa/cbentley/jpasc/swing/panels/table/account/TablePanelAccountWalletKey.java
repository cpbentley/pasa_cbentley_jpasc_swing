/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.JPanel;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyAbstract;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyWalletNoWildcards;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountWalletKey;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountWalletKey extends TablePanelAccountAbstractKey implements IMyTab {

   private static final String KEY              = "list_mykey_accounts";

   /**
    * 
    */
   private static final long   serialVersionUID = -3040964171971420256L;

   /**
    * 
    * @param psc
    * @param root
    * @param id
    */
   public TablePanelAccountWalletKey(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, KEY, root);
   }

   protected PanelHelperKeyAbstract createPanelHelperKey() {
      //we don't want the ALL key here
      PanelHelperKeyWalletNoWildcards helper = new PanelHelperKeyWalletNoWildcards(psc, this, this);
      return helper;
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountWalletKey worker = new WorkerTableAccountWalletKey(psc, this, getTableModel());
      setWorkerData(worker);
      return worker;
   }

   protected void subInitPanelNorth(JPanel north) {
      super.subInitPanelNorth(north); //adds panel refresh
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItems(menu);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountWalletKey");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountWalletKey");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}