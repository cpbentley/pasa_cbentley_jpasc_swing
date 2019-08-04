/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.key;

import javax.swing.JPanel;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperRefresh;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletKey;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletName;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaChain;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaAbstract;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyAbstract;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyChainAll;
import pasa.cbentley.swing.widgets.b.BPopupMenu;


/**
 * Base class for Listing Keys from the chain.
 * 
 * This class does not provide a widget to create keys.
 * 
 * You are not supposed to modify anything, except changing cosmetics such as key names.
 * 
 * @author Charles Bentley
 *
 */
public abstract class TablePanelPublicKeyJavaChainAbstract extends TablePanelPublicKeyJavaAbstract {
   
   

   /**
    * 
    */
   private static final long  serialVersionUID          = 1L;

   private static final int   STAT_INDEX_0_NUM_KEYS     = 0;

   private static final int   STAT_INDEX_1_NUM_ACCOUNTS = 1;


   public TablePanelPublicKeyJavaChainAbstract(PascalSwingCtx psc, String id, IRootTabPane root) {
      super(psc, id, root);
   }

   public void cmdChangeKeyName() {
      psc.getLog().consoleLogDateRed("Change account name in reference wallet.");
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
   
   /**
    * We override for {@link TableModelPublicKeyJavaMyAssets}
    */
   protected ModelTablePublicKeyJavaAbstract createTableModel() {
      return new ModelTablePublicKeyJavaChain(psc);
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
    * Worker for getting the keys
    */
   protected WorkerTableKeyAbstract createWorker() {
      WorkerTableKeyChainAll worker = new WorkerTableKeyChainAll(psc, this, getTableModel());
      return worker;
   }

  

   /**
    * Override because here we know the type of the model
    */
   public ModelTablePublicKeyJavaChain getTableModel() {
      return (ModelTablePublicKeyJavaChain) getBenTable().getModel();
   }


   protected void subInitPanelNorth(JPanel north) {
   }

   protected void subInitPanelSouth(JPanel south) {
      panelHelperRefresh = new PanelHelperRefresh(psc, this);
      panelHelperLoadingStat.setCompProgressBefore(panelHelperRefresh);
      panelHelperLoadingStat.resetToSize(2); //2 stats here
      panelHelperLoadingStat.set(STAT_INDEX_0_NUM_KEYS, "stat.keys", 4);
      panelHelperLoadingStat.set(STAT_INDEX_1_NUM_ACCOUNTS, "stat.account", 6);
      panelHelperLoadingStat.addToPanelSerially(south); //boilerplate for linking widgets
      south.add(panelHelperLoadingStat);
   }

   protected void subPopulatePopMenu(BPopupMenu menu) {
      super.addDefaultKeyMenuItems(menu);
   }

   protected void subUpdateStatPanel() {
      ModelTablePublicKeyJavaChain model = getTableModel();
      panelHelperLoadingStat.setStat(STAT_INDEX_0_NUM_KEYS, model.getNumKeys());
      panelHelperLoadingStat.setStat(STAT_INDEX_1_NUM_ACCOUNTS, model.getNumAccounts());
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TablePanelPublicKeyJavaChainAbstract");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TablePanelPublicKeyJavaChainAbstract");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
