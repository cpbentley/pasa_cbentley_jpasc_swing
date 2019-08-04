/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.account;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.github.davidbolet.jpascalcoin.api.model.Account;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.AccountJava;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.cellrenderers.CellRendereManager;
import pasa.cbentley.jpasc.swing.cmds.BCMenuItem;
import pasa.cbentley.jpasc.swing.cmds.ICommandableAccount;
import pasa.cbentley.jpasc.swing.cmds.ICommandableKey;
import pasa.cbentley.jpasc.swing.cmds.PascalCmdManager;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.account.PanelAccountDetails;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperRefresh;
import pasa.cbentley.jpasc.swing.panels.table.abstrakt.TablePanelAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountFullData;
import pasa.cbentley.jpasc.swing.workers.table.account.WorkerTableAccountAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BMenuItem;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * 
 * Provide services 
 * 
 * <li> Specific popup manager for account rows
 * 
 * implementation populate menu with required items
 * 
 * 
 * <b>Interfaces</b>
 * <li> {@link ICommandableAccount} Panel provides stub implementation
 * <li> {@link ICommandableKey} Panel provides stub implementation for interacting with the key of a selected account.
 * 
 * @author Charles Bentley
 *
 */
public abstract class TablePanelAccountAbstract extends TablePanelAbstract<Account> implements IMyTab, IMyGui, ICommandableAccount, ICommandableKey {

   /**
    * 
    */
   private static final long serialVersionUID          = -4966660489388988361L;

   public static final int   STAT_INDEX_0_BALANCE      = 0;

   public static final int   STAT_INDEX_1_NUM_ACCOUNTS = 1;

   public static final int   STAT_INDEX_2_NUM_SOLD     = 2;

   public static final int   STAT_INDEX_3_NUM_PRIVATE  = 3;

   public static final int   STAT_INDEX_4_NUM_SELLERS  = 4;

   public static final int   STAT_INDEX_5_TOTAL_PRICE  = 5;

   protected IRootTabPane    root;

   /**
    * 
    * @param psc
    * @param id
    * @param root {@link IRootTabPane} from which to get the {@link IDataAccess} when querying for the accounts
    * @param job
    */
   public TablePanelAccountAbstract(PascalSwingCtx psc, String id, IRootTabPane root) {
      super(psc, id);
      if (root == null) {
         throw new NullPointerException();
      }
      isTableUpdatingDisabled = false;
      this.root = root;
      setLayout(new BorderLayout());
   }

   /**
    * Called by a sub class when it does not have a custom pop up menu of commands for keys.
    * 
    * This method is not called from here. It is made final because you are not supposed to redefine
    * the defaults.
    * 
    * Use {@link TablePanelAccountAbstract#subPopulatePopMenu(BPopupMenu)}
    * 
    * @param menu
    */
   public final void addDefaultAccountMenuItems(BPopupMenu menu) {
      SwingCtx sc = psc.getSwingCtx();
      PascalCmdManager pcm = psc.getCmds();

      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdShowAccountInInspectorTab()));
      menu.addSeparator();
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountShowKey()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountShowKeyWin()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountShowKeyNames()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountShowKeyNamesWin()));
      menu.addSeparator();
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountSendTo()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountSendToWin()));
      menu.addSeparator();
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountSendFrom()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountSendFromWin()));

   }

   public final void addDefaultAccountMenuItemsNoSend(BPopupMenu menu) {
      SwingCtx sc = psc.getSwingCtx();
      PascalCmdManager pcm = psc.getCmds();

      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdShowAccountInInspectorTab()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountShowKey()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountShowKeyNames()));
      menu.addSeparator();
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdShowAccountInInspectorWin()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountShowKeyWin()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, this, pcm.getCmdAccountShowKeyNamesWin()));
      menu.addSeparator();
      menu.add(new BCMenuItem<ICommandableKey>(sc, this, pcm.getCmdKeyChangeName()));

   }

   /**
    * Changes the name of the key.. if the key belongs to wallet, user has to go through
    * reference wallet.
    * 
    * otherwise
    */
   public void cmdChangeKeyName() {
      Account ac = this.getSelectedAccount();
      if (ac != null) {
         String encodedPk = ac.getEncPubkey();
         String name = JOptionPane.showInputDialog(psc.getFrameRoot(), "Local Name Database: What's the new name?");
         //name is null if canceled
         if (name != null) {
            psc.getPCtx().getPkNameStore().setPkName(encodedPk, name);
         }
         //invalidate table model cache
         this.getTableModel().fireTableDataChanged();
         //request to show the tab for account details in the current ctx
      }
   }

   public void cmdSendFromAccount() {
      Account ac = getSelectedAccount();
   }

   public void cmdSendToAccount() {
      Account ac = getSelectedAccount();
   }

   /**
    * Shows the accounts with names of the selected key
    */
   public void cmdShowKeyAccountNames() {

   }

   /**
    * Same as {@link ICommandableKey#cmdShowKeyAccountNames()} but in a new window
    */
   public void cmdShowKeyAccountNamesNewWindow() {

   }

   /**
    * Shows the accounts of selected key
    */
   public void cmdShowKeyAccounts() {

   }

   /**
    * Same as {@link ICommandableKey#cmdShowKeyAccounts()} but in a new window
    */
   public void cmdShowKeyAccountsNewWindow() {

   }

   public void cmdShowSelectedAccountDetails() {
      //#debug
      toDLog().pFlow("", this, TablePanelAccountAbstract.class, "cmdShowSelectedAccountDetails", LVL_05_FINE, true);

      Account ac = this.getSelectedAccount();
      if (ac != null) {
         //request to show the tab for account details in the current ctx
         root.showAccountDetails(ac);
      }
   }

   public void cmdShowSelectedAccountDetailsNewWindow() {
      //#debug
      toDLog().pFlow("", this, TablePanelAccountAbstract.class, "cmdShowSelectedAccountDetailsNewWindow", LVL_05_FINE, true);

      Account account = getSelectedAccount();
      if (account != null) {
         PanelAccountDetails details = new PanelAccountDetails(psc, root);
         details.setAccount(account);
         psc.getSwingCtx().showInNewFrame(details);
      }
   }

   public void cmdShowSelectedAccountKeyNames() {
      // TODO Auto-generated method stub

   }

   public void cmdShowSelectedAccountKeyNamesNewWindow() {
      // TODO Auto-generated method stub

   }

   public void cmdShowSelectedAccountOwner() {
      Account ac = this.getSelectedAccount();
      if (ac != null) {
         //request to show the tab for account details in the current ctx
         root.showAccountOwner(ac);
      }
   }

   public void cmdShowSelectedAccountOwnerNewWindow() {
      Account ac = this.getSelectedAccount();
      if (ac != null) {
         //request to show the tab for account details in the current ctx
         root.showAccountOwner(ac);
      }
   }

   /**
    * 
    */
   public void cmdShowSelectedAccountSellerDetails() {
      //#debug
      toDLog().pFlow("", this, TablePanelAccountAbstract.class, "cmdShowSelectedAccountSellerDetails", LVL_05_FINE, true);

      Account ac = this.getSelectedAccount();
      if (ac != null) {
         //request to show the tab for account details in the current ctx
         Integer seller = ac.getSellerAccount();
         if (seller != null) {
            root.showAccountDetails(seller);
         } else {
            psc.getLog().consoleLogDateRed("Selected account does not have a seller account");
         }
      }
   }

   /**
    * 
    */
   public void cmdShowSelectedAccountSellerDetailsNewWindow() {
      //#debug
      toDLog().pFlow("", this, TablePanelAccountAbstract.class, "cmdShowSelectedAccountSellerDetails", LVL_05_FINE, true);
      Account ac = this.getSelectedAccount();
      if (ac != null) {
         //request to show the tab for account details in the current ctx
         Integer seller = ac.getSellerAccount();
         if (seller != null) {
            PanelAccountDetails details = new PanelAccountDetails(psc, root);
            details.setAccount(seller);
            psc.getSwingCtx().showInNewFrame(details);
         } else {
            psc.getLog().consoleLogDateRed("Selected account does not have a seller account");
         }
      }
   }

   public void cmdShowSelectedAccountSendBalanceFrom() {
      // TODO Auto-generated method stub

   }

   public void cmdShowSelectedAccountSendBalanceFromNewWindow() {
      // TODO Auto-generated method stub

   }

   public void cmdShowSelectedAccountSendBalanceTo() {
      // TODO Auto-generated method stub

   }

   public void cmdShowSelectedAccountSendBalanceToNewWindow() {
      // TODO Auto-generated method stub

   }

   public void cmdShowSelectedAccountSendKeyTo() {
      // TODO Auto-generated method stub

   }

   public void cmdShowSelectedAccountSendKeyToNewWindow() {
      // TODO Auto-generated method stub

   }

   public void cmdTableRefresh() {
      super.cmdTableRefresh();
   }

   /**
    * Override for an another kind of 
    */
   protected ModelTableAccountAbstract createTableModel() {
      return new ModelTableAccountFullData(psc);
   }

   protected abstract WorkerTableAccountAbstract createWorker();

   public void disposeTab() {
      super.disposeTab();
   }

   /**
    * 
    * @return
    */
   protected int getColumnIndexAccount() {
      return getTableModel().getColumnIndexAccount();
   }

   protected int getColumnIndexAccountName() {
      return getTableModel().getColumnIndexAccount();
   }

   protected int getColumnIndexAge() {
      return getTableModel().getColumnIndexAge();
   }

   protected int getColumnIndexKey() {
      return getTableModel().getColumnIndexKey();
   }

   protected int getColumnIndexOps() {
      return getTableModel().getColumnIndexOps();
   }

   protected int getColumnIndexPrice() {
      return getTableModel().getColumnIndexAge();
   }

   protected int getDefSortColumnIndex() {
      return getColumnIndexAccount();
   }

   public IRootTabPane getRoot() {
      return root;
   }

   public Account getSelectedAccount() {
      Account ac = null;
      int selectedRow = this.getJTable().getSelectedRow();
      if (selectedRow >= 0) {
         selectedRow = this.getJTable().convertRowIndexToModel(selectedRow);
         ac = this.getTableModel().getRow(selectedRow);
      }
      return ac;
   }

   public AccountJava getSelectedAccountJava() {
      // TODO Auto-generated method stub
      return null;
   }

   public ModelTableAccountAbstract getTableModel() {
      return (ModelTableAccountAbstract) getBenTable().getModel();
   }

   public WorkerTableAccountAbstract getWorkerTableAccountAbstract() {
      return (WorkerTableAccountAbstract) workerTable;
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
   }

   /**
    * Worker panel may update progress data 
    * @param worker
    * @param entryCount
    */
   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
      super.panelSwingWorkerProcessed(worker, entryCount);

      super.resizeTableColumnDefault();
   }

   /**
    * 
    * @param ac
    * @return
    */
   public boolean removeAccountFromModel(Account ac) {
      if (isInitialized()) {
         return getBenTable().getModel().removeRow(ac);
      }
      return false;
   }

   /**
    * Override this if using a different model than {@link TableModelAccounts}
    */
   public void setColumnRenderers() {
      CellRendereManager rm = psc.getCellRendereManager();
      getBenTable().setColumnRenderer(getColumnIndexAccount(), rm.getCellRendererAccount());
      getBenTable().setColumnRenderer(getColumnIndexOps(), rm.getCellRendererAccountOpCount());
      getBenTable().setColumnRenderer(getColumnIndexKey(), rm.getCellRendererKeyName());
      getBenTable().setColumnRenderer(getColumnIndexAge(), rm.getCellRendererAccountAge());
      subSetColumnRenderers();
   }

   /**
    * Adds {@link PanelHelperRefresh}
    */
   protected void subInitPanelNorth(JPanel north) {

   }

   /**
    * Default south panel for stats
    */
   protected void subInitPanelSouth(JPanel south) {
      //we want min and max filter
      panelHelperRefresh = new PanelHelperRefresh(psc, this);
      //TODO disable stat computing option for slow computers
      panelHelperLoadingStat.setCompProgressBefore(panelHelperRefresh);
      panelHelperLoadingStat.resetToSize(4);
      int[] vals = new int[] { 0, 0, 0, 0 };
      if (isFixedStatTextField) {
         vals = new int[] { 18, 15, 10, 3 };
      }
      panelHelperLoadingStat.set(STAT_INDEX_0_BALANCE, "stat.balance", vals[0]);
      panelHelperLoadingStat.set(STAT_INDEX_1_NUM_ACCOUNTS, "stat.account", vals[1]);
      panelHelperLoadingStat.set(STAT_INDEX_2_NUM_SOLD, "stat.forsale", vals[2]);
      panelHelperLoadingStat.set(STAT_INDEX_3_NUM_PRIVATE, "stat.private", vals[3]);
      panelHelperLoadingStat.addToPanelSerially(south);
      south.add(panelHelperLoadingStat);
   }

   /**
    * Add {@link BMenuItem} that fit the class implementation design.
    * 
    * Overrides must not call super.
    * @param menu
    */
   protected abstract void subPopulatePopMenu(BPopupMenu menu);

   /**
    * Override this for setting column renderers in addition to the default ones from 
    * {@link TablePanelAccountAbstract#setColumnRenderers()}
    */
   protected void subSetColumnRenderers() {

   }

   /**
    * Default stats for Accounts
    */
   protected void subUpdateStatPanel() {
      ModelTableAccountAbstract model = getTableModel();
      panelHelperLoadingStat.setStat(STAT_INDEX_0_BALANCE, psc.getFormatDecimalCoins().format(model.getTotalBalanceCount()));
      panelHelperLoadingStat.setStat(STAT_INDEX_1_NUM_ACCOUNTS, model.getNumAccounts());
      panelHelperLoadingStat.setStat(STAT_INDEX_2_NUM_SOLD, model.getNumSales());
      panelHelperLoadingStat.setStat(STAT_INDEX_3_NUM_PRIVATE, model.getNumPrivates());
   }

   protected void subWillRefreshTable() {
      psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_BELLS);

   }

   public void tabGainFocus() {
      super.tabGainFocus();
   }

   /**
    * Calls the default lost focus {@link TablePanelAbstract#tabLostFocus()}
    */
   public void tabLostFocus() {
      super.tabLostFocus();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelAccountWalletPrice");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelAccountWalletPrice");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}