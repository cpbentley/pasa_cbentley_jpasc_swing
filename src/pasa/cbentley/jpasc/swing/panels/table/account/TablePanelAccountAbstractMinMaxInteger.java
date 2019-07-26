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
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperMinMaxInteger;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstractMinMaxInteger;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Tab to tell the users which account is near the 4 years consensus rule.
 * 
 * @author Charles Bentley
 *
 */
public abstract class TablePanelAccountAbstractMinMaxInteger extends TablePanelAccountAbstractKey implements IMyTab {

   /**
    * 
    */
   private static final long    serialVersionUID = -3040964171971420256L;

   protected PanelHelperMinMaxInteger panelMinMaxInteger;

   /**
    * 
    * @param psc
    * @param root
    * @param id
    */
   public TablePanelAccountAbstractMinMaxInteger(PascalSwingCtx psc, IRootTabPane root, String id) {
      super(psc, id, root);
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultAccountMenuItems(menu);
   }

   protected void subInitPanelNorth(JPanel north) {
      super.subInitPanelNorth(north); //adds panel refresh
      //also want an integer min max
      north.add(getPanelMinMaxInteger());
   }

   protected abstract PanelHelperMinMaxInteger createPanelMinMaxInteger();

   public PanelHelperMinMaxInteger getPanelMinMaxInteger() {
      if (panelMinMaxInteger == null) {
         panelMinMaxInteger = createPanelMinMaxInteger();
      }
      return panelMinMaxInteger;
   }

   public Integer getIntegerMax() {
      return panelMinMaxInteger.getMaxInteger();
   }

   public Integer getIntegerMin() {
      return panelMinMaxInteger.getMinInteger();
   }
   public void setIntegerMaxNoRefresh(String value) {
      tableRefreshDisable();
      getPanelMinMaxInteger().setMax(value);
      tableRefreshEnable();
   }

   public void setIntegerMinNoRefresh(String value) {
      tableRefreshDisable();
      getPanelMinMaxInteger().setMin(value);
      tableRefreshEnable();
   }
   
   protected void setWorkerData(WorkerTableAccountAbstractMinMaxInteger worker) {
      super.setWorkerData(worker);
      if (panelMinMaxInteger != null) {
         worker.setMin(getIntegerMin());
         worker.setMax(getIntegerMax());
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountAbstractMinMaxInteger");
      super.toString(dc.sup());
      dc.nlLvl(panelMinMaxInteger, "panelMinMaxInteger");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountAbstractMinMaxInteger");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}