/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.imytab.AbstractMyTab;

/**
 * List incoming operations with money from PASA outside the wallet with the actual public key
 * <br>
 * 
 * Use a table with operations.
 * 
 * Filter with receiving key, min max and name, outside/inside (from accounts of the wallet keys)
 * 
 * 
 * 
 * @author Charles Bentley
 *
 */
public class WalletPaymentCoinPanel extends AbstractMyTab {

   private IRootTabPane   root;

   private PascalSwingCtx psc;

   public WalletPaymentCoinPanel(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), "wallet_coin_recieved");
      this.psc = psc;

      this.root = root;

   }

   public void disposeTab() {
   }

   public void initTab() {
   }

   public void tabGainFocus() {
   }

   public void tabLostFocus() {
   }
}