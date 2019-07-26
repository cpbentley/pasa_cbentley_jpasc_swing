/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.Component;

import javax.swing.event.ChangeListener;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Block;
import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.pcore.access.AccessPascalRPC;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.interfaces.IAccessPascal;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.getstarted.TabsGetStarted;
import pasa.cbentley.jpasc.swing.panels.snapshot.TabsSnapshotFunding;
import pasa.cbentley.jpasc.swing.panels.system.TabsNodeCenter;
import pasa.cbentley.jpasc.swing.panels.trade.TabsTrade;
import pasa.cbentley.jpasc.swing.panels.wallet.TabsMyAssets;
import pasa.cbentley.jpasc.swing.panels.wallet.TabsWallet;
import pasa.cbentley.jpasc.swing.panels.whalewatch.TabsWhaleWatch;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * Manage the tabs to the main wallet.
 * 
 * @author Charles Bentley
 *
 */
public class TabsRootRPC extends TabbedBentleyPanel implements ChangeListener, IRootTabPane {

   private TabsGetStarted           getStartedRootPanel;

   private TabsMyAssets             myAssetPanel;

   TabsNodeCenter                   nodeCenterPanel;

   TabsSnapshotFunding                     snapshotPanel;

   private TabsWallet               walletRootPanel;

   private TabsWhaleWatch           whaleWatchPanel;

   private PascalSwingCtx           psc;

   private TabsTrade                tradePanel;

   private TabsChainExplorer        rootChain;

   private TabsRootKnowledgeFunding rootKnowledge;

   public static final String       ID = "root_rpc";

   public TabsRootRPC(PascalSwingCtx psc) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;

      //how do we make sure this field is correct and not null?

      //panels are always created. their content will be initialized later when needed.
      //a panel can be Framed outside... but at least one belongs

   }

   public void disposeTab() {
      Component c = jtabbePane.getSelectedComponent();
      int numTabs = jtabbePane.getTabCount();
      for (int i = 0; i < numTabs; i++) {
         IMyTab tab = (IMyTab) jtabbePane.getComponentAt(i);
         if (tab != null && tab != c) {
            tab.disposeTab();
            tab.setDisposed();
         }
      }
   }

   public void initTabs() {
      
      //
      getStartedRootPanel = new TabsGetStarted(psc);
      myAssetPanel = new TabsMyAssets(psc, this);
      psc.setPrivateRoot(myAssetPanel);
      
      walletRootPanel = new TabsWallet(psc, this);
      nodeCenterPanel = new TabsNodeCenter(psc, this);
      rootKnowledge = new TabsRootKnowledgeFunding(psc);
      rootChain = new TabsChainExplorer(psc, this);
      whaleWatchPanel = new TabsWhaleWatch(psc, this);
      snapshotPanel = new TabsSnapshotFunding(psc);
      tradePanel = new TabsTrade(psc, this, myAssetPanel);

      this.addMyTab(getStartedRootPanel);
      this.addMyTab(myAssetPanel);
      this.addMyTab(walletRootPanel);
      this.addMyTab(nodeCenterPanel);
      this.addMyTab(rootKnowledge);
      this.addMyTab(tradePanel);
      this.addMyTab(rootChain);
      this.addMyTab(whaleWatchPanel);
      this.addMyTab(snapshotPanel);

   }

   public void tabGainFocus() {
   }

   public void tabLostFocus() {
   }

   public void showAccountDetails(Integer ac) {
      initCheck();
      rootChain.showAccountDetails(ac);
   }

   public void showAccountDetails(Account ac) {
      initCheck();
      rootChain.showAccountDetails(ac);
   }

   public void showAccountOwner(Account ac) {
      initCheck();
      rootChain.showAccountOwner(ac);
   }

   public void showAccountOwner(Integer ac) {
      initCheck();
      rootChain.showAccountOwner(ac);
   }

   public void showPublicKeyAccounts(PublicKey pk) {
      initCheck();
      rootChain.showPublicKeyAccounts(pk);
   }

   public void showPublicKeyJavaAccounts(PublicKeyJava pk) {
      initCheck();
      rootChain.showPublicKeyJavaAccounts(pk);
   }

   public void showBlock(Block ac) {
      initCheck();
      rootChain.showBlock(ac);
   }

   public Integer getAccountNext(Integer account) {
      initCheck();
      return rootChain.getAccountNext(account);
   }

   public Integer getAccountPrev(Integer account) {
      initCheck();
      return rootChain.getAccountPrev(account);

   }

   public void showPublicKeyJavaAccountNames(PublicKeyJava pk) {
      
   }

   public IAccessPascal getAccessPascal() {
      return new AccessPascalRPC(psc.getPCtx());
   }

}