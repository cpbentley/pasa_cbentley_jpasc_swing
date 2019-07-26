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
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperMinMaxDouble;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstractMinMaxDouble;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public abstract class TablePanelAccountAbstractMinMaxDouble extends TablePanelAccountAbstractKey implements IMyTab {

   /**
    * 
    */
   private static final long   serialVersionUID = -3040964171971420256L;

   protected PanelHelperMinMaxDouble panelMinMaxDouble;

   /**
    * 
    * @param psc
    * @param root
    * @param id
    */
   public TablePanelAccountAbstractMinMaxDouble(PascalSwingCtx psc, IRootTabPane root, String id) {
      super(psc, id, root);
   }

   /**
    * Override must not call super.
    */
   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItems(menu);
   }

   protected void subInitPanelNorth(JPanel north) {
      super.subInitPanelNorth(north); //adds panel refresh
      //also want an integer min max
      north.add(getPanelMinMaxDouble());
   }
   
   protected abstract PanelHelperMinMaxDouble createPanelMinMaxDouble();

   public PanelHelperMinMaxDouble getPanelMinMaxDouble() {
      if (panelMinMaxDouble == null) {
         panelMinMaxDouble = createPanelMinMaxDouble();
      }
      return panelMinMaxDouble;
   }
   
   public void setDoubleMaxNoRefresh(String value) {
      tableRefreshDisable();
      getPanelMinMaxDouble().setMax(value);
      tableRefreshEnable();
   }

   public void setDoubleMinNoRefresh(String value) {
      tableRefreshDisable();
      getPanelMinMaxDouble().setMin(value);
      tableRefreshEnable();
   }
   
   protected void setWorkerData(WorkerTableAccountAbstractMinMaxDouble worker) {
      super.setWorkerData(worker);
      if (panelMinMaxDouble != null) {
         worker.setMin(getDoubleMin());
         worker.setMax(getDoubleMax());
      }
   }
   
   public Double getDoubleMax() {
      return panelMinMaxDouble.getMaxDouble();
   }

   public Double getDoubleMin() {
      return panelMinMaxDouble.getMinDouble();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountAbstractMinMaxDouble");
      super.toString(dc.sup());
      dc.nlLvl(panelMinMaxDouble, "panelMinMaxDouble");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountAbstractMinMaxDouble");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}