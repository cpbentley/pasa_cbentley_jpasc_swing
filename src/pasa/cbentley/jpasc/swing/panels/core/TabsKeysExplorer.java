/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainKey;
import pasa.cbentley.jpasc.swing.panels.table.key.TablePanelPublicKeyJavaChainAll;
import pasa.cbentley.jpasc.swing.panels.table.key.TablePanelPublicKeyJavaChainWalletPrivate;
import pasa.cbentley.jpasc.swing.panels.table.key.TablePanelPublicKeyJavaChainWalletPublic;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

public class TabsKeysExplorer extends TabbedBentleyPanel {
   /**
    * 
    */
   private static final long                         serialVersionUID = -3523191063614218767L;

   private PascalSwingCtx                            psc;

   private IRootTabPane                              root;

   private TablePanelPublicKeyJavaChainAll           tablePanelPublicKeyJavaChainAll;

   private TablePanelPublicKeyJavaChainWalletPrivate tablePanelPublicKeyJavaChainWalletPrivate;

   private TablePanelPublicKeyJavaChainWalletPublic  tablePanelPublicKeyJavaChainWalletPublic;

   public TabsKeysExplorer(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), "root_keys");
      this.psc = psc;
      this.root = root;
   }

   public void disposeTab() {
      tablePanelPublicKeyJavaChainWalletPrivate = null;
      tablePanelPublicKeyJavaChainWalletPublic = null;
      tablePanelPublicKeyJavaChainAll = null;
   }

   public void initTabs() {
      tablePanelPublicKeyJavaChainWalletPrivate = new TablePanelPublicKeyJavaChainWalletPrivate(psc, root);
      tablePanelPublicKeyJavaChainWalletPublic = new TablePanelPublicKeyJavaChainWalletPublic(psc, root);
      tablePanelPublicKeyJavaChainAll = new TablePanelPublicKeyJavaChainAll(psc, root);

      addMyTab(tablePanelPublicKeyJavaChainWalletPrivate);
      addMyTab(tablePanelPublicKeyJavaChainWalletPublic);
      addMyTab(tablePanelPublicKeyJavaChainAll);

   }

}