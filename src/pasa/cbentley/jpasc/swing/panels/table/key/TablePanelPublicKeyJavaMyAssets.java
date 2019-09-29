/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.key;

import javax.swing.JPanel;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.cmds.CmdCopyKeyBase58;
import pasa.cbentley.jpasc.swing.cmds.CmdCopyKeyEncoded;
import pasa.cbentley.jpasc.swing.cmds.CmdKeyChangeName;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyCreator;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperRefresh;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletKey;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletName;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaMyAssets;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyAbstract;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyWalletPrivate;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public class TablePanelPublicKeyJavaMyAssets extends TablePanelPublicKeyJavaAbstract {

   public static final String    KEY                       = "list_my_keys";

   /**
    * 
    */
   private static final long     serialVersionUID          = 1L;

   private static final int      STAT_INDEX_0_NUM_KEYS     = 0;

   private static final int      STAT_INDEX_1_NUM_ACCOUNTS = 1;

   private PanelHelperKeyCreator keyCreatorPanel;

   public TablePanelPublicKeyJavaMyAssets(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, KEY, root);
   }

   public void cmdChangeKeyName(CmdKeyChangeName cmd) {
      psc.getLog().consoleLogDateRed("Change account name in reference wallet.");
   }

   public void cmdCopyKeyBase58(CmdCopyKeyBase58 cmd) {
      PublicKeyJava pk = this.getSelectedPublicKeyA();
      cmd.executeWith(pk);
   }

   public void cmdCopyKeyEncoded(CmdCopyKeyEncoded cmd) {
      PublicKeyJava pk = this.getSelectedPublicKeyA();
      cmd.executeWith(pk);
   }

   public void cmdShowKeyAccountNames() {
      PublicKeyJava pk = this.getSelectedPublicKeyA();
      if (pk != null) {
         root.showPublicKeyJavaAccountNames(pk);
      }
   }

   public void cmdShowKeyAccountNamesNewWindow() {
      PublicKeyJava pk = this.getSelectedPublicKeyA();
      if (pk != null) {
         TablePanelAccountWalletName tab = new TablePanelAccountWalletName(psc, root);
         tab.setPublicKey(pk);
         psc.showInNewFrameRelToFrameRoot(tab);

      }
   }

   public void cmdShowKeyAccounts() {
      PublicKeyJava pk = this.getSelectedPublicKeyA();
      if (pk != null) {
         root.showPublicKeyJavaAccounts(pk);
      }
   }

   public void cmdShowKeyAccountsNewWindow() {
      PublicKeyJava pk = this.getSelectedPublicKeyA();
      TablePanelAccountWalletKey tab = new TablePanelAccountWalletKey(psc, root);
      tab.setPublicKey(pk);
      psc.showInNewFrameRelToFrameRoot(tab);
   }

   /**
    * We override for {@link TableModelPublicKeyJavaMyAssets}
    */
   protected ModelTablePublicKeyJavaMyAssets createTableModel() {
      return new ModelTablePublicKeyJavaMyAssets(psc);
   }

   /**
    * Worker for getting the keys
    */
   protected WorkerTableKeyAbstract createWorker() {
      WorkerTableKeyWalletPrivate worker = new WorkerTableKeyWalletPrivate(psc, this, getTableModel());
      return worker;
   }

   /**
    * Override because here we know the type of the model
    */
   public ModelTablePublicKeyJavaMyAssets getTableModel() {
      return (ModelTablePublicKeyJavaMyAssets) getBenTable().getModel();
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   protected void subInitPanelNorth(JPanel north) {
      keyCreatorPanel = new PanelHelperKeyCreator(psc);
      north.add(keyCreatorPanel);
   }

   protected void subInitPanelSouth(JPanel south) {
      panelHelperRefresh = new PanelHelperRefresh(psc, this);
      panelHelperLoadingStat.setCompProgressBefore(panelHelperRefresh);
      panelHelperLoadingStat.resetToSize(2); //2 stats here
      panelHelperLoadingStat.set(0, "stat.keys", 4);
      panelHelperLoadingStat.set(1, "stat.account", 6);
      panelHelperLoadingStat.addToPanelSerially(south); //boilerplate for linking widgets
      south.add(panelHelperLoadingStat);
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultKeyMenuItems(menu);
   }

   protected void subUpdateStatPanel() {
      ModelTablePublicKeyJavaMyAssets model = getTableModel();
      panelHelperLoadingStat.setStat(STAT_INDEX_0_NUM_KEYS, model.getNumKeys());
      panelHelperLoadingStat.setStat(STAT_INDEX_1_NUM_ACCOUNTS, model.getNumAccounts());
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelKeyJavaMyAssets");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelKeyJavaMyAssets");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
