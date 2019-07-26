/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Block;
import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.interfaces.IAccessPascal;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.account.PanelAccountDetails;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletAge;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletAll;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletBalance;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletKey;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletName;
import pasa.cbentley.jpasc.swing.panels.table.account.TablePanelAccountWalletPrice;
import pasa.cbentley.jpasc.swing.panels.table.key.TablePanelPublicKeyJavaMyAssets;
import pasa.cbentley.jpasc.swing.panels.tools.RegisterNewName;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * Root tabs for accounts in the private keys list
 * 
 * Worker lists Pks keys
 * 
 * uses {@link PascalAccessPrivateRPC} for data access.
 * 
 * @author Charles Bentley
 *
 */
public class TabsMyAssets extends TabbedBentleyPanel implements IMyTab, IRootTabPane {

   /**
    * 
    */
   private static final long              serialVersionUID = 6723662454248226691L;

   public static final String ID = "root_assets";

   private PanelAccountDetails            accountInspector;

   private PascalSwingCtx                 psc;

   private RegisterNewName                registerName;

   private IRootTabPane                   rootRPC;

   private TablePanelAccountWalletAge     tablePanelAccountWalletAge;

   private TablePanelAccountWalletAll     tablePanelAccountWalletAll;

   private TablePanelAccountWalletBalance tablePanelAccountWalletBalance;

   private TablePanelAccountWalletKey     tablePanelAccountWalletKey;

   private TablePanelAccountWalletName    tablePanelAccountWalletName;

   private TablePanelAccountWalletPrice   tablePanelAccountWalletPrice;

   private TablePanelPublicKeyJavaMyAssets      tablePanelKeyJavaMyAssets;

   /**
    * {@link IRootTabPane} from which we belong.
    * This class is itself a {@link IRootTabPane}, but we specialize in accounts
    * that are owned by the wallet or locally.
    *  
    * @param psc
    * @param rootRPC
    */
   public TabsMyAssets(PascalSwingCtx psc, IRootTabPane rootRPC) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      this.rootRPC = rootRPC;
   }

   public void disposeTab() {

   }

   /**
    * Next private account
    */
   public Integer getAccountNext(Integer account) {
      return psc.getAccountNext(account);
   }

   public Integer getAccountPrev(Integer account) {
      return psc.getAccountPrev(account);
   }

   /**
    * The {@link IDataAccess} to be used by its children.
    */
   public IAccessPascal getAccessPascal() {
      return psc.getPCtx().getAccessPascalPrivate();
   }

   public void guiUpdate() {
      super.guiUpdate();
      //swing components are automatically updated
   }

   /**
    * Defined as abstract in {@link AbstractMyTab}
    * 
    */
   public void initTabs() {
      //uses the root

      IRootTabPane rootThis = this;

      //TODO custom tab order
      //fetched by their id

      //use this as DataAccess which is 
      tablePanelKeyJavaMyAssets = new TablePanelPublicKeyJavaMyAssets(psc, rootThis);
      tablePanelAccountWalletAll = new TablePanelAccountWalletAll(psc, rootThis);
      tablePanelAccountWalletAge = new TablePanelAccountWalletAge(psc, rootThis);
      tablePanelAccountWalletName = new TablePanelAccountWalletName(psc, rootThis);

      tablePanelAccountWalletBalance = new TablePanelAccountWalletBalance(psc, rootThis);
      tablePanelAccountWalletPrice = new TablePanelAccountWalletPrice(psc, rootThis);
      //tablePanelAccountWalletPrice.setPriceMinNoRefresh("0.0001");

      tablePanelAccountWalletKey = new TablePanelAccountWalletKey(psc, rootThis);

      accountInspector = new PanelAccountDetails(psc, rootThis, "my_account_details");
      registerName = new RegisterNewName(psc, rootThis, rootRPC);

      //selects the first tab, but no event because we are in initTabs
      //after finishing here, caller will select tab from preference
      addMyTab(tablePanelKeyJavaMyAssets);
      addMyTab(tablePanelAccountWalletAll);
      addMyTab(tablePanelAccountWalletName);
      addMyTab(tablePanelAccountWalletAge);
      addMyTab(tablePanelAccountWalletPrice);
      addMyTab(tablePanelAccountWalletBalance);
      addMyTab(tablePanelAccountWalletKey);
      addMyTab(accountInspector);
      addMyTab(registerName);

   }

   public void showAccountDetails(Account account) {
      accountInspector.setAccount(account);
      this.showTab(accountInspector);
   }

   public void showAccountDetails(Integer ac) {
      accountInspector.setAccount(ac);
      this.showTab(accountInspector);
   }

   public void showAccountOwner(Account ac) {
      //action of clicking and showing does not overlap

      tablePanelAccountWalletKey.setPublicKeyEnc(ac.getEncPubkey());
      showAccountWalletKey();
   }

   public void showAccountOwner(Integer ac) {
      //convert back to acc
      Account account = rootRPC.getAccessPascal().getAccessAccountDBolet().getAccount(ac);
      tablePanelAccountWalletKey.setPublicKeyEnc(account.getEncPubkey());
      showAccountWalletKey();
   }

   private void showAccountWalletKey() {
      //disable refresh of data for the next event
      this.showTab(tablePanelAccountWalletKey);
   }

   public void showBlock(Block ac) {
      //we don't have block data here
      rootRPC.showBlock(ac);
   }

   public void showPublicKeyJavaAccounts(PublicKeyJava pk) {
      tablePanelAccountWalletKey.setPublicKey(pk);
      showAccountWalletKey();
   }

   public void showPublicKeyAccounts(PublicKey pk) {
      //get a java key here
      throw new RuntimeException();
      //tablePanelAccountWalletKey.setPublicKey(pk);
      //showAccountWalletKey();
   }

   public void tabGainFocus() {
      super.tabGainFocus();
      psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_INSTRUMENTS);
   }

   public void showPublicKeyJavaAccountNames(PublicKeyJava pk) {
      tablePanelAccountWalletName.setPublicKey(pk);
      this.showTab(tablePanelAccountWalletName);
      this.showTab(tablePanelAccountWalletName);
   }

   public void tabLostFocus() {
      super.tabLostFocus();
   }

}