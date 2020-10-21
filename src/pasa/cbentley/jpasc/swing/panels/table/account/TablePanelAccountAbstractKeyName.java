/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import javax.swing.JPanel;
import javax.swing.SortOrder;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ICommandableNameList;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperName;
import pasa.cbentley.jpasc.swing.panels.tools.RegisterNewName;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstractName;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public abstract class TablePanelAccountAbstractKeyName extends TablePanelAccountAbstractKey implements IMyTab, ICommandableNameList {

   /**
    * 
    */
   private static final long serialVersionUID = -3040964171971420256L;

   protected PanelHelperName panelNameHelper;

   /**
    * 
    * @param psc
    * @param root
    * @param id
    */
   public TablePanelAccountAbstractKeyName(PascalSwingCtx psc, String id, IRootTabPane root) {
      super(psc, id, root);
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItems(menu);
   }

   /**
    * The public key for filtering
    * @param pl
    */
   public void setPublicKey(PublicKeyJava pl) {

   }

   protected void subInitPanelNorth(JPanel north) {
      super.subInitPanelNorth(north); //adds panel refresh

      north.add(getPanelNameHelper());
   }

   public PanelHelperName getPanelNameHelper() {
      if (panelNameHelper == null) {
         panelNameHelper = createPanelHelperName();
      }
      return panelNameHelper;
   }

   protected abstract PanelHelperName createPanelHelperName();

   public void cmdGoToEdit() {
      TabbedBentleyPanel parent = this.getTabPosition().getParent();
      if (parent != null) {
         parent.showChildByID(RegisterNewName.ID);
      }
   }

   protected void setWorkerData(WorkerTableAccountAbstractName worker) {
      if (panelNameHelper != null) {
         worker.setName(panelNameHelper.getFilterNameString());
         worker.setOnlyEmty(panelNameHelper.isOnlyEmptyNames());
      }
      super.setWorkerData(worker);
   }

   /**
    * Sorts the table in {@link SortOrder#DESCENDING}
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      sortTableColAscending(getColumnIndexAccountName());
   }
   
  
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountAbstractName");
      super.toString(dc.sup());
      dc.nlLvlNullTitle("panelNameHelper", panelNameHelper);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountAbstractName");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}