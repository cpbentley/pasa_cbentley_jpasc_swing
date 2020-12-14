/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyWalletNoWildcards;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountChainPrice;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletBalance;
import pasa.cbentley.jpasc.swing.utils.PasaBuySelection;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.dekholm.riverlayout.RiverLayout;

/**
 * Simple Buy is for total noobs. By default account is set to key is buying account
 * 
 * Just one account that must be typed.
 * Destination Key is Account
 * Fee is 0,0001 by default if age of account is 0.
 * Wallet keeps track if a free txs is currently pending for a given account.
 * If a free tx is pending.. account is blocked from making a transaction.
 * 
 * 
 * @author Charles Bentley
 *
 */
public class WalletPasaSimpleBuy extends AbstractMyTab implements IMyTab, ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = 336285001589148239L;

   private JButton                  butBuyAccounts;

   private PanelHelperKeyWalletNoWildcards        dropDownPublicKey;

   private JLabel                   labFee;

   private JLabel                   labFundTransfer;

   private JLabel                   labAccountBuying;

   private TablePanelAccountWalletBalance listAccounts;

   private TablePanelAccountChainPrice       listPrices;

   private IRootTabPane             rootRPC;

   private IRootTabPane             rootMyAssets;

   private JTextField               textFee;

   private JTextField               textFundTransfer;

   private PascalSwingCtx           psc;

   private JButton                  butRefreshPriceList;

   private JTextField               textAccount;

   private JButton                  butTextFundMax;

   public WalletPasaSimpleBuy(PascalSwingCtx psc, IRootTabPane rootRPC, IRootTabPane rootPrivate) {
      super(psc.getSwingCtx(), "wallet_pasa_buy");
      this.psc = psc;
      this.setLayout(new BorderLayout());
      if (rootRPC == null) {
         throw new NullPointerException();
      }
      this.rootRPC = rootRPC;
      if (rootPrivate == null)
         throw new NullPointerException();
      this.rootMyAssets = rootPrivate;
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == dropDownPublicKey) {

      } else if (e.getSource() == butBuyAccounts) {
         cmdBuyAccounts();
      } else if (e.getSource() == butRefreshPriceList) {
         listPrices.tabGainFocus();
      }
   }

   private void cmdBuyAccounts() {
      PCoreCtx pc = this.psc.getPCtx();
      if (pc.getRPCConnection().isLocked()) {
         boolean isSuccess = psc.askToUnlock("Need to unlock wallet to buy accounts", "", this);
         if (!isSuccess) {
            return;
         }
      }

      PasaBuySelection pbs = new PasaBuySelection(psc);
      pbs.compute(listPrices.getJTable(), listPrices.getTableModel(), getFee());
      pbs.sortByPrice();

      Account buyer = getAccount();
      Double funding = getFunding().getDouble();

      //do a simulation. each record of tx is shown in a table.
      //if OK. accounts are bought. 

      String message = "Total Price is " + pbs.getPriceTotal();
      String extraFunding = "Extra balance sent to Account = " + funding;
      int r = JOptionPane.showConfirmDialog(this, message, "Buying ", JOptionPane.OK_CANCEL_OPTION);
      if (r == JOptionPane.OK_OPTION) {
         //
         Double feeZero = 0.0;
         Double fee = getFee().getDouble();
         Double feeToUse = feeZero;
         Account actoBuy = pbs.getList().get(0);
         Double fundsToSend = funding + actoBuy.getPrice();
         
         boolean suc = psc.buyAccount(buyer.getEncPubkey(), buyer, actoBuy, fundsToSend, feeToUse);
         //prepare for next
         if (suc) {
            //remove from list
            listPrices.removeAccountFromModel(actoBuy);
            listPrices.repaint();
         }
      }
   }

   private Account getAccount() {
      return null;
   }

   public void disposeTab() {

   }

   public PascalCoinValue getFunding() {
      String val = textFundTransfer.getText();
      if (val == null || val.equals("")) {
         return psc.getPCtx().getZero();
      }
      Double num = Double.valueOf(val);
      return new PascalCoinValue(psc.getPCtx(), num.doubleValue());
   }

   /**
    * 
    * @return {@link PCoreCtx#getZero()} if null
    */
   public PascalCoinValue getFee() {
      String val = textFee.getText();
      if (val == null || val.equals("")) {
         return psc.getPCtx().getZero();
      }
      Double num = Double.valueOf(val);
      return new PascalCoinValue(psc.getPCtx(), num.doubleValue());
   }

   public void initTab() {
      //two different roots.. one RPC other RPC private
      listPrices = new TablePanelAccountChainPrice(psc, rootRPC);
      listPrices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      listPrices.initCheck();
      
      this.add(listPrices, BorderLayout.CENTER);

      JPanel north = new JPanel();
      north.setLayout(new BorderLayout());

      JPanel centerOfNorthBorder = new JPanel();
      centerOfNorthBorder.setLayout(new RiverLayout());

      labAccountBuying = new JLabel("Buying Account");
      textAccount = new JTextField(15);

      labFee = new JLabel("Fee");
      labFee.setToolTipText("Fee to be paid when accounts in excess of the free tx per block");
      textFee = new JTextField(10);
      psc.setDoubleFilter(textFee);
      textFee.setText("0.0000");

      labFundTransfer = new JLabel("Extra Balance Transfer to bought account");
      labFundTransfer.setToolTipText("Pascal Coins moved over from buying account to bought account");
      textFundTransfer = new JTextField(7);
      textFundTransfer.setText("0.0000");
      textFundTransfer.setToolTipText("If not enough on the account, funding will be assumed to be 0.0");
      psc.setDoubleFilter(textFundTransfer);
      butTextFundMax = new JButton("Max");

      centerOfNorthBorder.add("", labFundTransfer);
      centerOfNorthBorder.add("tab", textFundTransfer);
      centerOfNorthBorder.add("br", labFee);
      centerOfNorthBorder.add("tab", textFee);

      butRefreshPriceList = new JButton("Refresh Prices");
      butRefreshPriceList.addActionListener(this);
      north.add(butRefreshPriceList);

      this.add(north, BorderLayout.NORTH);

      JPanel south = new JPanel();

      butBuyAccounts = new JButton("Buy Account");
      butBuyAccounts.addActionListener(this);

      south.add(butBuyAccounts);

      this.add(south, BorderLayout.SOUTH);

   }

   public void tabGainFocus() {
      //we never update. must be refreshed manually because its heavy
      //if a new block with sell or 
      //listPrices.tabGainFocus();
   }

   public void tabLostFocus() {
   }

}