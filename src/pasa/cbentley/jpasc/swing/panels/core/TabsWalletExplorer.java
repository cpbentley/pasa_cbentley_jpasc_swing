/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletAge;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletAll;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletBalance;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletKey;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletName;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletPrice;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

public class TabsWalletExplorer extends TabbedBentleyPanel {

   /**
    * 
    */
   private static final long              serialVersionUID = 4132103729450456302L;

   private PascalSwingCtx                 psc;

   private IRootTabPane                   root;

   private TablePanelAccountWalletAll     tablePanelAccountWalletAll;

   private TablePanelAccountWalletAge     tablePanelAccountWalletAge;

   private TablePanelAccountWalletKey     tablePanelAccountWalletKey;

   private TablePanelAccountWalletName    tablePanelAccountWalletName;

   private TablePanelAccountWalletPrice   tablePanelAccountWalletPrice;

   private TablePanelAccountWalletBalance tablePanelAccountWalletBalance;

   public TabsWalletExplorer(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), "root_wallet_explorer");
      this.psc = psc;
      this.root = root;
   }

   public void disposeTab() {
      tablePanelAccountWalletAge = null;
      tablePanelAccountWalletAll = null;
   }

   public void initTabs() {
      tablePanelAccountWalletAll = new TablePanelAccountWalletAll(psc, root);
      tablePanelAccountWalletAge = new TablePanelAccountWalletAge(psc, root);
      tablePanelAccountWalletBalance = new TablePanelAccountWalletBalance(psc, root);
      tablePanelAccountWalletKey= new TablePanelAccountWalletKey(psc, root);
      tablePanelAccountWalletName= new TablePanelAccountWalletName(psc, root);
      tablePanelAccountWalletPrice= new TablePanelAccountWalletPrice(psc, root);
      
      addMyTab(tablePanelAccountWalletAll);
      addMyTab(tablePanelAccountWalletKey);
      addMyTab(tablePanelAccountWalletAge);
      addMyTab(tablePanelAccountWalletBalance);
      addMyTab(tablePanelAccountWalletName);
      addMyTab(tablePanelAccountWalletPrice);

   }

}