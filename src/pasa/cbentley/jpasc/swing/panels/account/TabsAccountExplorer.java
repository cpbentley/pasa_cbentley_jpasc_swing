/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Block;
import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.pcore.access.AccessPascalRPC;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.interfaces.IAccessPascal;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainAll;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainBalance;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainName;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainPrice;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

public class TabsAccountExplorer extends TabbedBentleyPanel implements IMyTab, ActionListener, IRootTabPane {
   public static final String            ID               = "root_account";

   /**
    * 
    */
   private static final long             serialVersionUID = 9151853322825121491L;

   private PanelAccountDetails           accountExplorerPanel;

   private TabsAccountExplorerDivider    listAccount;

   private TablePanelAccountChainName    listName;

   private PascalSwingCtx                psc;

   private IRootTabPane                  rootRPC;

   private TablePanelAccountChainPrice   listAccountPrices;

   private TablePanelAccountChainBalance listAccountRich;

   public TabsAccountExplorer(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      this.rootRPC = root;
   }

   public void actionPerformed(ActionEvent e) {
   }

   public void disposeTab() {
      listName = null;
      listAccount = null;
      accountExplorerPanel = null;

      removeAlltabs();
   }

   public Integer getAccountNext(Integer account) {
      return psc.getAccountNext(account);
   }

   public Integer getAccountPrev(Integer account) {
      return psc.getAccountPrev(account);
   }

   public IAccessPascal getAccessPascal() {
      return new AccessPascalRPC(psc.getPCtx());
   }

   public void initTabs() {

      //init default position
      listName = new TablePanelAccountChainName(psc, this);
      listAccount = new TabsAccountExplorerDivider(psc, this);
      listAccountPrices = new TablePanelAccountChainPrice(psc, this);
      listAccountRich = new TablePanelAccountChainBalance(psc, this);
      listAccountRich.setDoubleMinNoRefresh("20000.0");
      accountExplorerPanel = new PanelAccountDetails(psc, this);

      //deal with ordering of the tabs? TODO and what if there framed tab
      String state = psc.getPascPrefs().get(ID + "state", "");
      if (!state.equals("")) {
         String[] order = state.split(":");
         for (int i = 0; i < order.length; i++) {
            IMyTab tab = getTab(order[i]);
            if (tab != null) {
               addMyTab(tab);
            } else {
               psc.getLog().consoleLogError("Error Loading Tab " + order[i] + " in " + ID);
            }
         }
      } else {
         //factory order
         addMyTab(accountExplorerPanel);
         addMyTab(listAccount);
         addMyTab(listName);
         addMyTab(listAccountRich);
         addMyTab(listAccountPrices);
      }
   }

   public void showAccountDetails(Account ac) {
      initCheck();
      accountExplorerPanel.setAccount(ac);
      showTab(accountExplorerPanel);
   }

   public void showAccountDetails(Integer ac) {
      initCheck();
      accountExplorerPanel.setAccount(ac);
      showTab(accountExplorerPanel);
   }

   public void showAccountOwner(Account ac) {
      //look it up in the snapshot
      psc.getLog().consoleLogError("Load Snapshot");

   }

   /**
    * 
    */
   public void showAccountOwner(Integer ac) {
      // TODO Auto-generated method stub

   }

   public void showBlock(Block ac) {
      initCheck();
      //we don't support block UI at this level.. we must use parent
      rootRPC.showBlock(ac);
   }

   public void showPublicKeyJavaAccountNames(PublicKeyJava pk) {
      // TODO Auto-generated method stub

   }

   public void showPublicKeyJavaAccounts(PublicKeyJava pk) {

   }

   public void showPublicKeyAccounts(PublicKey pk) {
      // TODO Auto-generated method stub

   }

}