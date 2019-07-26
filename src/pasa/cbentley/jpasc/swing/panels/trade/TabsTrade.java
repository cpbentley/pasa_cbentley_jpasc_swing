/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.trade;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

public class TabsTrade extends TabbedBentleyPanel implements IMyTab {
   /**
    * 
    */
   private static final long      serialVersionUID = -4715098177249299721L;

   private FundingMassPasaTrading blockChainPasaPanel;

   private BuyPascalCoinPanel     buyPascalCoinPanel;

   private PascalSwingCtx         psc;

   private IRootTabPane           root;

   private IRootTabPane           rootPrivate;

   private TabsPoloniex           tabsPoloniex;

   public TabsTrade(PascalSwingCtx psc, IRootTabPane root, IRootTabPane rootPrivate) {
      super(psc.getSwingCtx(), "root_trade");
      this.psc = psc;
      if (root == null) {
         throw new NullPointerException();
      }
      this.root = root;
      if (rootPrivate == null) {
         throw new NullPointerException();
      }
      this.rootPrivate = rootPrivate;
   }

   public void disposeTab() {

   }

   public void guiUpdate() {
   }

   public void initTabs() {
      buyPascalCoinPanel = new BuyPascalCoinPanel(psc);
      blockChainPasaPanel = new FundingMassPasaTrading(psc);
      tabsPoloniex = new TabsPoloniex(psc);

      addMyTab(buyPascalCoinPanel);
      addMyTab(blockChainPasaPanel);
      addMyTab(tabsPoloniex);
   }

   public void tabGainFocus() {
      //play a sound
      psc.getAudio().playAudio("/sounds/coin_glass1.wav");

      //select the blockchain tab if wallet already has public key with account
   }

   public void tabLostFocus() {
   }

}