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
   private static final long                serialVersionUID = -5940455045019097140L;

   private TablePanelOperationByAccount     tablePanelOperationByAccount;

   private TablePanelOperationByExchanges   tablePanelOperationByExchanges;

   private TablePanelOperationByKey         tablePanelOperationByKey;

   private TablePanelOperationByBlock       tablePanelOperationByBlock;

   private PanelDetailsOperation            operationDetails;

   private TablePanelOperationHistoryRecent tablePanelOperationHistoryRecent;

   private TablePanelOperationPending       tablePanelOperationPending;

   private PascalSwingCtx                   psc;

   private IRootTabPane                     root;

   public TabsOperations(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), "root_operations");
      this.psc = psc;
      this.root = root;
      psc.addBlockListener(this);
   }

   public void disposeTab() {
      if (isInitialized()) {
         operationDetails.disposeTab();
         tablePanelOperationPending.disposeTab();
         tablePanelOperationHistoryRecent.disposeTab();
         tablePanelOperationByBlock.disposeTab();
         tablePanelOperationByAccount.disposeTab();
         tablePanelOperationByKey.disposeTab();

         operationDetails = null;
         tablePanelOperationPending = null;
         tablePanelOperationHistoryRecent = null;
         tablePanelOperationByBlock = null;
         tablePanelOperationByAccount = null;
         tablePanelOperationByKey = null;
         removeAlltabs();
      }
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void initTabs() {
      operationDetails = new PanelDetailsOperation(psc, root);
      tablePanelOperationPending = new TablePanelOperationPending(psc, root);
      tablePanelOperationHistoryRecent = new TablePanelOperationHistoryRecent(psc, root);
      tablePanelOperationByBlock = new TablePanelOperationByBlock(psc, root);
      tablePanelOperationByAccount = new TablePanelOperationByAccount(psc, root, false);
      tablePanelOperationByExchanges = new TablePanelOperationByExchanges(psc, root);

      tablePanelOperationByKey = new TablePanelOperationByKey(psc, root);

      addMyTab(tablePanelOperationPending);
      addMyTab(tablePanelOperationHistoryRecent);
      addMyTab(tablePanelOperationByBlock);
      addMyTab(tablePanelOperationByAccount);
      addMyTab(tablePanelOperationByExchanges);
      //addMyTab(tablePanelOperationByKey);
      //addMyTab(operationDetails);
   }

   public void pingDisconnect() {
   }

   public void pingError() {
   }

   public void pingNewBlock(Integer newBlock, long millis) {
      setPendingCount(0);
   }

   public void pingNewPendingCount(Integer count, Integer oldCount) {
      setPendingCount(count);
   }

   public void pingNoBlock(long millis) {
   }

   public void setPendingCount(Integer count) {
      if (tablePanelOperationPending != null) {
         String newTitle = tablePanelOperationPending.getTabTitle() + " #" + count;
         TabPosition pos = tablePanelOperationPending.getTabPosition();
         if (pos.isFramed()) {
            pos.getFrame().setTitle(newTitle);
         } else {
            int index = pos.getIndex();
            jtabbePane.setTitleAt(index, newTitle);
         }
         //only update the table if it is the user active tab of its parent
         if (isUserActive() || isSelected(tablePanelOperationPending)) {
            tablePanelOperationPending.cmdTableRefresh();
         }
         tablePanelOperationPending.repaint();
      }
   }

   /**
    * Make tab showing block data active
    * @param ac
    */
   public void showBlock(Block ac) {
      initCheck();
      tablePanelOperationByBlock.showBlock(ac);
      showTab(tablePanelOperationByBlock);
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