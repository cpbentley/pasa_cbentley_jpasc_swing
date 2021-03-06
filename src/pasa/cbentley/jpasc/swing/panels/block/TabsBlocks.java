/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.block;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.rpc.model.Block;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.core.TabsPascalSwing;
import pasa.cbentley.jpasc.swing.panels.table.block.TablePanelBlockLast1000;
import pasa.cbentley.jpasc.swing.panels.table.block.TablePanelBlockLast24h;
import pasa.cbentley.jpasc.swing.panels.table.block.TablePanelBlockLast48h;
import pasa.cbentley.jpasc.swing.panels.table.block.TablePanelBlockLastMonth;
import pasa.cbentley.jpasc.swing.panels.table.block.TablePanelBlockLastWeek;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByBlock;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * When Block Panel needs the {@link TablePanelOperationByBlock},
 * it must make sure the 
 * @author Charles Bentley
 *
 */
public class TabsBlocks extends TabsPascalSwing implements IMyTab {

   /**
    * 
    */
   private static final long        serialVersionUID = -5940455045019097140L;

   private IRootTabPane             root;

   private TablePanelBlockLast48h   tablePanelBlockLast48h;

   private TablePanelBlockLast24h   tablePanelBlockLast24h;

   private TablePanelBlockLastWeek  tablePanelBlockLastWeek;

   private TablePanelBlockLastMonth tablePanelBlockLastMonth;

   private TablePanelBlockLast1000  tablePanelBlockLast1000;

   private PanelBlockDetails        panelBlockDetails;

   public TabsBlocks(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "root_blocks");
      this.root = root;
   }

   public void showBlockDetails(Block ac) {
      initCheck();
      panelBlockDetails.setBlock(ac);
      showTab(panelBlockDetails);
   }

   public void disposeTab() {
      if (isInitialized()) {
         panelBlockDetails.disposeTab();
         tablePanelBlockLast24h.disposeTab();
         tablePanelBlockLast48h.disposeTab();
         tablePanelBlockLastWeek.disposeTab();
         tablePanelBlockLastMonth.disposeTab();

         panelBlockDetails = null;
         tablePanelBlockLast24h = null;
         tablePanelBlockLast48h = null;
         tablePanelBlockLastWeek = null;
         tablePanelBlockLastMonth = null;
         removeAlltabs();
      }
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void initTabs() {

      panelBlockDetails = new PanelBlockDetails(psc, root);
      tablePanelBlockLast24h = new TablePanelBlockLast24h(psc, root);
      tablePanelBlockLast48h = new TablePanelBlockLast48h(psc, root);
      tablePanelBlockLast1000 = new TablePanelBlockLast1000(psc, root);

      //tablePanelBlockLastWeek = new TablePanelBlockLastWeek(psc, root);
      //tablePanelBlockLastMonth = new TablePanelBlockLastMonth(psc, root);

      addMyTab(panelBlockDetails);
      addMyTab(tablePanelBlockLast24h);
      addMyTab(tablePanelBlockLast48h);
      addMyTab(tablePanelBlockLast1000);

      //addMyTab(tablePanelBlockLastWeek);
      //addMyTab(tablePanelBlockLastMonth);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TabsBlocks");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabsBlocks");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}