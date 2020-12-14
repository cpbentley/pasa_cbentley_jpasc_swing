/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyAbstract;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyChain;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperName;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperNameChain;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountChainNames;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelAccountChainName extends TablePanelAccountAbstractKeyName implements IMyTab {

   private static final String KEY              = "list_names";

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
   public TablePanelAccountChainName(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, KEY, root);
      isKeyFilteringEnabled = false; //we don't want by default
   }

   /**
    * When a search/refresh occurs, {@link TablePanelAccountAbstract#cmdRefresh(Object)} generates
    * a new createWroker
    */
   protected PanelHelperName createPanelHelperName() {
      return new PanelHelperNameChain(psc, this);
   }

   protected PanelHelperKeyAbstract createPanelHelperKey() {
      return new PanelHelperKeyChain(psc, this, this);
   }

   protected WorkerTableAccountAbstract createWorker() {
      WorkerTableAccountChainNames worker = new WorkerTableAccountChainNames(psc, getTableModel(), this);
      setWorkerData(worker);
      return worker;
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItemsNoSendNoKey(menu);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountChainName");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountChainName");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}