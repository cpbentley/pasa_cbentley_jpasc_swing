/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.pcore.access.AccessPascalRPC;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.interfaces.IAccessPascal;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.pcore.rpc.model.Block;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainAll;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainAllRange;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainBalance;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainName;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainPrice;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * Divides the Exploration of All Accounts into small blocks
 * 
 * <li> 0-9999
 * <li> 10 000-99 999
 * <li> 100 000-999 999
 * <li> 1 000 000 - 1 999 999
 * <li> 2 000 000 - 2 999 999
 * 
 * @author Charles Bentley
 *
 */
public class TabsAccountExplorerDivider extends TabbedBentleyPanel implements IMyTab, ActionListener, IRootTabPane {
   public static final String             ID               = "root_account";

   /**
    * 
    */
   private static final long              serialVersionUID = 9151853322825121491L;

   private PanelAccountDetails            accountExplorerPanelRange;

   private TablePanelAccountChainAllRange accountChainAllRange9999;

   private TablePanelAccountChainAllRange accountChainAllRange99999;

   private TablePanelAccountChainAllRange accountChainAllRange999999;

   private PascalSwingCtx                 psc;

   private IRootTabPane                   rootRPC;

   private TablePanelAccountChainAllRange accountChainAllRange1999999;

   private TablePanelAccountChainAllRange accountChainAllRange2999999;

   private TablePanelAccountChainAllRange accountChainAllRange500000;

   public TabsAccountExplorerDivider(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      this.rootRPC = root;
   }

   public void actionPerformed(ActionEvent e) {
   }

   public void disposeTab() {
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
      accountChainAllRange9999 = new TablePanelAccountChainAllRange(psc, this, 0, 9999);
      accountChainAllRange99999 = new TablePanelAccountChainAllRange(psc, this, 10000, 99999);
      accountChainAllRange500000 = new TablePanelAccountChainAllRange(psc, this, 100000, 500000);
      accountChainAllRange999999 = new TablePanelAccountChainAllRange(psc, this, 500000, 1000000);
      accountChainAllRange1999999 = new TablePanelAccountChainAllRange(psc, this, 1000000, 1999999);
      accountChainAllRange2999999 = new TablePanelAccountChainAllRange(psc, this, 2000000, 2999999);

      accountExplorerPanelRange = new PanelAccountDetails(psc, this, "account_details_range");

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
         addMyTab(accountExplorerPanelRange);

         addMyTab(accountChainAllRange9999);
         addMyTab(accountChainAllRange99999);
         addMyTab(accountChainAllRange500000);
         addMyTab(accountChainAllRange999999);
         addMyTab(accountChainAllRange1999999);
         addMyTab(accountChainAllRange2999999);
      }
   }

   public void showAccountDetails(Account ac) {
      initCheck();
      accountExplorerPanelRange.setAccount(ac);
      showTab(accountExplorerPanelRange);
   }

   public void showAccountDetails(Integer ac) {
      initCheck();
      accountExplorerPanelRange.setAccount(ac);
      showTab(accountExplorerPanelRange);
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
      rootRPC.showBlock(ac);
   }

   public void showBlockDetails(Block ac) {
      initCheck();
      rootRPC.showBlockDetails(ac);
   }

   public void showPublicKeyJavaAccountNames(PublicKeyJava pk) {
      // TODO Auto-generated method stub

   }

   public void showPublicKeyJavaAccounts(PublicKeyJava pk) {

   }

   public void showPublicKeyAccounts(PublicKey pk) {
      // TODO Auto-generated method stub

   }

   public Integer getAccountLast() {
      // TODO Auto-generated method stub
      return null;
   }

   public Integer getBlockNext(Integer block) {
      // TODO Auto-generated method stub
      return null;
   }

   public Integer getBlockPrev(Integer block) {
      // TODO Auto-generated method stub
      return null;
   }

   public Integer getBlockLast() {
      // TODO Auto-generated method stub
      return null;
   }

}