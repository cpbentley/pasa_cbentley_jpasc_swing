/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.operation;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Operation;

import pasa.cbentley.jpasc.swing.cellrenderers.CellRendereManager;
import pasa.cbentley.jpasc.swing.cellrenderers.CellRendererOpAccount;
import pasa.cbentley.jpasc.swing.cmds.ICommandableOperation;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperRefresh;
import pasa.cbentley.jpasc.swing.panels.table.abstrakt.TablePanelAbstract;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainKey;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.widgets.PascalProgressBar;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * 
 * @author Charles Bentley
 *
 */
public abstract class TablePanelOperationAbstract extends TablePanelAbstract<Operation> implements ICommandableOperation {

   /**
    * 
    */
   private static final long serialVersionUID                   = 4192065369095126596L;

   private static final int  STAT_INDEX_0_NUM_OPS               = 0;

   private static final int  STAT_INDEX_1_NUM_ACCOUNTS          = 1;

   private static final int  STAT_INDEX_2_NUM_ACCOUNTS_RECEIVER = 2;

   private static final int  STAT_INDEX_3_FEE                   = 3;

   private static final int  STAT_INDEX_4_VOLUME                = 4;

   private static final int  STAT_INDEX_5_PAYLOAD               = 5;

   IRootTabPane              root;

   public TablePanelOperationAbstract(PascalSwingCtx psc, String id, IRootTabPane root) {
      super(psc, id);
      this.root = root;
      this.setLayout(new BorderLayout());
   }

   public void cmdOperationAccountDetails() {
      Operation op = psc.getSelectedOperation(getJTable(), getTableModel());
      if (op != null) {
         Integer account = op.getAccount();
         //request to show the tab for account details in the current ctx
         root.showAccountDetails(account);
      }
   }

   public void cmdOperationAccountOwner() {
      Operation op = psc.getSelectedOperation(getJTable(), getTableModel());
      if (op != null) {
         Integer account = op.getAccount();
         root.showAccountOwner(account);
      }
   }

   public void cmdOperationAccountOwnerNewWindow() {
      Operation op = psc.getSelectedOperation(getJTable(), getTableModel());
      if (op != null) {
         Integer account = op.getAccount();
         TablePanelAccountChainKey lao = new TablePanelAccountChainKey(psc, root);
         Account ac = psc.getPascalClient().getAccount(account);
         if (ac != null) {
            lao.setPublicKeyEnc(ac.getEncPubkey());
            psc.showInNewFrameRelToFrameRoot(lao);
         }
      }
   }

   public void cmdTableRefresh() {
      super.cmdTableRefresh();
   }

   /**
    * Returns the worker for populating the Table
    * Cannot be null. 
    * @return
    */
   protected abstract WorkerTableOperationAbstract createWorker();

   private int getColumnIndexAccount() {
      return getTableModel().getColumnIndexAccount();
   }

   private int getColumnIndexAccountReceiver() {
      return getTableModel().getColumnIndexAccountReceiver();
   }

   private int getColumnIndexAccountSender() {
      return getTableModel().getColumnIndexAccountSender();
   }

   private int getColumnIndexAccountSigner() {
      return getTableModel().getColumnIndexAccountSigner();
   }

   protected int getColumnIndexBlock() {
      return getTableModel().getColumnIndexBlock();
   }

   private int getColumnIndexOpCount() {
      return getTableModel().getColumnIndexOpCount();
   }

   protected int getColumnIndexTime() {
      return getTableModel().getColumnIndexTime();
   }

   private int getColumnIndexType() {
      return getTableModel().getColumnIndexType();
   }

   private int getColumnIndexTypeSub() {
      return getTableModel().getColumnIndexTypeSub();
   }

   /**
    * By default sort operations with time
    */
   protected int getDefSortColumnIndex() {
      return getColumnIndexBlock();
   }

   protected ModelTableOperationAbstract getTableModel() {
      return (ModelTableOperationAbstract) getBenTable().getModel();
   }

   public void initTab() {
      super.initTab();// creates the table
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker tableBlockSwingWorker) {
      super.panelSwingWorkerCancelled(tableBlockSwingWorker);
   }

   /**
    * <li>Sets the {@link PascalProgressBar} to Work Done.
    * <li>Resize the table columns
    * <li>Sort the rows
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      super.panelSwingWorkerDone(worker);
      super.resizeTableColumns();
      //default sorting if not saved as a state
      super.sortTableColDescending(getColumnIndexTime());
   }

   /**
    * 
    */
   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
      super.panelSwingWorkerProcessed(worker, entryCount);
   }

   public void setColumnRenderers() {
      //then set specifics
      CellRendereManager crm = psc.getCellRendereManager();
      getBenTable().setColumnRenderer(getColumnIndexBlock(), crm.getCellRendererBlockInteger());
      getBenTable().setColumnRenderer(getColumnIndexOpCount(), crm.getCellRendererOpCount());
      getBenTable().setColumnRenderer(getColumnIndexType(), crm.getCellRendererOpType());
      getBenTable().setColumnRenderer(getColumnIndexTypeSub(), crm.getCellRendererOpTypeSub());

      CellRendererOpAccount accountRenderer = crm.getCellRendererAccountOperation();
      getBenTable().setColumnRenderer(getColumnIndexAccount(), accountRenderer);
      getBenTable().setColumnRenderer(getColumnIndexAccountSender(), accountRenderer);
      getBenTable().setColumnRenderer(getColumnIndexAccountReceiver(), accountRenderer);
      getBenTable().setColumnRenderer(getColumnIndexAccountSigner(), accountRenderer);
   }

   /**
    * Default south panel for stats
    */
   protected void subInitPanelSouth(JPanel south) {
      panelHelperRefresh = new PanelHelperRefresh(psc, this);
      panelHelperLoadingStat.setCompProgressBefore(panelHelperRefresh);
      panelHelperLoadingStat.resetToSize(6);
      int[] vals = new int[] { 0, 0, 0, 0 };
      if (isFixedStatTextField) {
         vals = new int[] { 6, 5, 5, 8, 10, 8 };
      }
      int index = 0;
      panelHelperLoadingStat.set(STAT_INDEX_0_NUM_OPS, "stat.numops", vals[index++]);
      panelHelperLoadingStat.set(STAT_INDEX_1_NUM_ACCOUNTS, "stat.account", vals[index++]);
      panelHelperLoadingStat.set(STAT_INDEX_2_NUM_ACCOUNTS_RECEIVER, "stat.account.receiver", vals[index++]);
      panelHelperLoadingStat.set(STAT_INDEX_3_FEE, "stat.fee", vals[index++]);
      panelHelperLoadingStat.set(STAT_INDEX_4_VOLUME, "stat.volume", vals[index++]);
      panelHelperLoadingStat.set(STAT_INDEX_5_PAYLOAD, "stat.bytespayload", vals[index++]);
      panelHelperLoadingStat.addToPanelSerially(south);
      south.add(panelHelperLoadingStat);
   }

   /**
    * Default stats for Accounts
    */
   protected void subUpdateStatPanel() {
      ModelTableOperationAbstract model = getTableModel();
      panelHelperLoadingStat.setStat(STAT_INDEX_0_NUM_OPS, model.getNumOperations());
      panelHelperLoadingStat.setStat(STAT_INDEX_1_NUM_ACCOUNTS, model.getNumAccounts());
      panelHelperLoadingStat.setStat(STAT_INDEX_2_NUM_ACCOUNTS_RECEIVER, model.getNumReceivers());
      panelHelperLoadingStat.setStat(STAT_INDEX_3_FEE, model.getTotalFees());
      panelHelperLoadingStat.setStat(STAT_INDEX_4_VOLUME, model.getAmountTotal());
      panelHelperLoadingStat.setStat(STAT_INDEX_5_PAYLOAD, model.getNumBytesPayload());
   }

}