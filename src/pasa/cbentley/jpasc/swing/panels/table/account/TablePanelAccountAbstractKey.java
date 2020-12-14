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
import pasa.cbentley.jpasc.pcore.filter.SetFilterKey;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ICommandableNameList;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyAbstract;
import pasa.cbentley.jpasc.swing.panels.tools.RegisterNewName;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
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
public abstract class TablePanelAccountAbstractKey extends TablePanelAccountAbstract implements IMyTab, ICommandableNameList {

   /**
    * 
    */
   private static final long        serialVersionUID      = -3040964171971420256L;

   protected PanelHelperKeyAbstract panelKeyHelper;

   protected boolean                isKeyFilteringEnabled = true;

   /**
    * 
    * @param psc
    * @param root
    * @param id
    */
   public TablePanelAccountAbstractKey(PascalSwingCtx psc, String id, IRootTabPane root) {
      super(psc, id, root);
   }

   public void cmdGoToEdit() {
      TabbedBentleyPanel parent = this.getTabPosition().getParent();
      if (parent != null) {
         parent.showChildByID(RegisterNewName.ID);
      }
   }

   /**
    * Creates an implementation of {@link PanelHelperKeyAbstract}
    * @return
    */
   protected abstract PanelHelperKeyAbstract createPanelHelperKey();

   public PanelHelperKeyAbstract getPanelKeyHelperAbstract() {
      if (panelKeyHelper == null) {
         panelKeyHelper = createPanelHelperKey();
         panelKeyHelper.setKeySelectionEnabled(isKeyFilteringEnabled);
      }
      return panelKeyHelper;
   }

   public void setPublicKey(PublicKeyJava pk) {
      getPanelKeyHelperAbstract().setPublicKeyNoEvent(pk);
   }

   public void setPublicKeyEnc(String encPubKey) {
      getPanelKeyHelperAbstract().setPublicKeyEncNoEvent(encPubKey);
   }

   protected void setWorkerData(WorkerTableAccountAbstract worker) {
      if (panelKeyHelper != null) {
         //depending on the key, set a special key filter on the worker
         SetFilterKey filterSet = panelKeyHelper.getFilterSet();
         worker.setFilterSet(filterSet);
      }
   }

   protected void subInitPanelNorth(JPanel north) {
      super.subInitPanelNorth(north); //adds panel refresh
      PanelHelperKeyAbstract panelKeyHelper = getPanelKeyHelperAbstract();
      panelKeyHelper.buildUI();
      north.add(panelKeyHelper);
   }

   /**
    * Override must not call super.
    */
   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItems(menu);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountAbstractKey");
      super.toString(dc.sup());
      dc.nlLvl(panelKeyHelper, "panelKeyHelper");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountAbstractKey");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}