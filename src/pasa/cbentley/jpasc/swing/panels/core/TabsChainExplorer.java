/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.Color;

import javax.swing.UIManager;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Block;
import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.pcore.access.AccessPascalRPC;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.interfaces.IAccessAccountDBolet;
import pasa.cbentley.jpasc.pcore.interfaces.IAccessPascal;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.account.TabsAccountExplorer;
import pasa.cbentley.jpasc.swing.panels.block.TabsBlocks;
import pasa.cbentley.jpasc.swing.panels.operation.TabsOperations;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * 
 * @author Charles Bentley
 *
 */
public class TabsChainExplorer extends TabbedBentleyPanel implements IRootTabPane {

   /**
    * 
    */
   private static final long   serialVersionUID = -2070986904157267209L;

   private TabsAccountExplorer accountExplorerPanel;

   private TabsBlocks          blockPanel;

   private TabsKeysExplorer    keysExplorer;

   private TabsOperations      operationsPanel;

   private PascalSwingCtx      psc;

   private IRootTabPane        rootRPC;

   public TabsChainExplorer(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), "root_chain");
      this.psc = psc;
      this.rootRPC = root;
   }

   public void disposeTab() {

   }

   public Integer getAccountNext(Integer account) {
      return psc.getAccountNext(account);
   }

   public Integer getAccountPrev(Integer account) {
      return psc.getAccountPrev(account);
   }

   /**
    * {@link IAccessPascal}
    */
   public IAccessPascal getAccessPascal() {
      return new AccessPascalRPC(psc.getPCtx());
   }

   public void initTabs() {
      operationsPanel = new TabsOperations(psc, rootRPC);
      //typically user may want to create a duplicate in a Frame
      accountExplorerPanel = new TabsAccountExplorer(psc, rootRPC);
      blockPanel = new TabsBlocks(psc, rootRPC);
      keysExplorer = new TabsKeysExplorer(psc, rootRPC);

      this.addMyTab(accountExplorerPanel);
      this.addMyTab(operationsPanel);
      this.addMyTab(blockPanel);
      this.addMyTab(keysExplorer);

   }

   public void setNewPendingCount(Integer count) {
      int index = jtabbePane.indexOfComponent(operationsPanel);

      if (index < 0) {
         // The tab could not be found
      } else {
         jtabbePane.setTitleAt(index, operationsPanel.getTabTitle() + " #" + count);
         if (count.intValue() == 0) {
            //https://alvinalexander.com/java/java-uimanager-color-keys-list
            Color panelBg = UIManager.getColor("Panel.background");
            Color panelFg = UIManager.getColor("TabbedPane.foreground");
            jtabbePane.setBackgroundAt(index, panelBg);
            jtabbePane.setForegroundAt(index, panelFg);
         } else {
            //this.setBackgroundAt(index, Color.magenta);
            jtabbePane.setForegroundAt(index, Color.red);
         }
      }
      this.repaint();
      operationsPanel.setPendingCount(count);
   }

   public void showAccountDetails(Account ac) {
      //make sure operationpanel is init
      initCheck();
      accountExplorerPanel.showAccountDetails(ac);
      showTab(accountExplorerPanel);
   }

   public void showAccountDetails(Integer ac) {
      Account account = psc.getPascalClient().getAccount(ac);
      if (account != null) {
         showAccountDetails(account);
      }
   }

   public void showAccountOwner(Account acc) {
      if (acc != null) {
         String pubKeyEnc = acc.getEncPubkey();
         psc.getLog().consoleLogError("No RPC call to get PublicKey if no in wallet " + pubKeyEnc);
         //            PublicKey pk = pclient.get
         //            accountOwner.setOwner(pk);
         //            this.setSelectedComponent(accountOwner);
      }
   }

   public void showAccountOwner(Integer ac) {
      Account acc = psc.getPascalClient().getAccount(ac);
      showAccountOwner(acc);
   }

   /**
    * @see IRootTabPane#showBlock(Block)
    */
   public void showBlock(Block ac) {
      //make sure operationpanel is init
      initCheck();
      operationsPanel.showBlock(ac);
      showTab(operationsPanel);
   }

   public void showPublicKeyJavaAccountNames(PublicKeyJava pk) {

   }

   public void showPublicKeyJavaAccounts(PublicKeyJava pk) {
      initCheck();
      keysExplorer.showPublicKeyJavaAccounts(pk);
      showTab(keysExplorer);
   }

   public void showPublicKeyAccounts(PublicKey pk) {
      initCheck();
      accountExplorerPanel.showPublicKeyAccounts(pk);
   }

}
