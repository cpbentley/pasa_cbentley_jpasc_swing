/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.operation;

import javax.swing.event.ChangeListener;

import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.interfaces.IBlockListener;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.table.operation.PanelDetailsOperation;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByAccount;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByBlock;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByExchanges;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByKey;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationHistoryRecent;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationPending;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabPosition;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * When Block Panel needs the {@link TablePanelOperationByBlock},
 * it must make sure the 
 * @author Charles Bentley
 *
 */
public class TabsOperations extends TabbedBentleyPanel implements IMyTab, ChangeListener, IBlockListener {

   /**
    * 
    */
   private static final long          serialVersionUID = -5940455045019097140L;

   private TablePanelOperationByBlock     listOperationPanel;

   private TablePanelOperationHistoryRecent     opHistory;

   private TablePanelOperationPending     pendingOpPanel;

   private IRootTabPane               root;

   private PascalSwingCtx             psc;

   private TablePanelOperationByAccount   accountOperations;

   private PanelDetailsOperation      operationDetails;

   private TablePanelOperationByExchanges exchangeOperations;

   private TablePanelOperationByKey       keyOperations;

   public TabsOperations(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), "root_operations");
      this.psc = psc;
      this.root = root;
      psc.addBlockListener(this);
   }

   public void disposeTab() {
      if (isInitialized()) {
         operationDetails.disposeTab();
         pendingOpPanel.disposeTab();
         opHistory.disposeTab();
         listOperationPanel.disposeTab();
         accountOperations.disposeTab();
         keyOperations.disposeTab();

         operationDetails = null;
         pendingOpPanel = null;
         opHistory = null;
         listOperationPanel = null;
         accountOperations = null;
         keyOperations = null;
         removeAlltabs();
      }
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void initTabs() {
      operationDetails = new PanelDetailsOperation(psc, root);
      pendingOpPanel = new TablePanelOperationPending(psc, root);
      opHistory = new TablePanelOperationHistoryRecent(psc, root);
      listOperationPanel = new TablePanelOperationByBlock(psc, root);
      accountOperations = new TablePanelOperationByAccount(psc, root, false);
      exchangeOperations = new TablePanelOperationByExchanges(psc, root);
      keyOperations = new TablePanelOperationByKey(psc, root);

      addMyTab(pendingOpPanel);
      addMyTab(opHistory);
      addMyTab(listOperationPanel);
      addMyTab(accountOperations);
      addMyTab(exchangeOperations);
      addMyTab(keyOperations);
      addMyTab(operationDetails);
   }

   public void setPendingCount(Integer count) {
      if (pendingOpPanel != null) {
         String newTitle = pendingOpPanel.getTabTitle() + " #" + count;
         TabPosition pos = pendingOpPanel.getTabPosition();
         if (pos.isFramed()) {
            pos.getFrame().setTitle(newTitle);
         } else {
            int index = pos.getIndex();
            jtabbePane.setTitleAt(index, newTitle);
         }
         //only update the table if it is the user active tab of its parent
         if (isUserActive() || isSelected(pendingOpPanel)) {
            pendingOpPanel.cmdTableRefresh();
         }
         pendingOpPanel.repaint();
      }
   }

   /**
    * Make tab showing block data active
    * @param ac
    */
   public void showBlock(Block ac) {
      initCheck();
   }

   public void pingNoBlock(long millis) {
   }

   public void pingDisconnect() {
   }

   public void pingError() {
   }

   public void pingNewPendingCount(Integer count, Integer oldCount) {
      setPendingCount(count);
   }

   public void pingNewBlock(Integer newBlock, long millis) {
      setPendingCount(0);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TabsOperations");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabsOperations");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}