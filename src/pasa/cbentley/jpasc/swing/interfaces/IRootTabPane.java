/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.interfaces;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Block;
import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.interfaces.IAccessPascal;

/**
 * Interface to a GUI panel managing an independant business function
 * 
 * <li> Snapshot
 * <li> Blockchain
 * <li> User assets
 * 
 * @author Charles Bentley
 *
 */
public interface IRootTabPane {

   /**
    * In the context, what's the next available account.
    * Does not change GUI visual state.
    * TODO move this to {@link IDataAccess}
    * @param account
    * @return
    */
   public Integer getAccountNext(Integer account);

   /**
    * Null if none available
    * @param account
    * @return
    */
   public Integer getAccountPrev(Integer account);

   /**
    * Interface to the blockchain data, might be RPC, local, etc
    * @return
    */
   public IAccessPascal getAccessPascal();

   /**
    * Show account details visually
    * @param ac
    */
   public void showAccountDetails(Account ac);

   /**
    * Fetch the correct {@link Account} from the {@link IDataAccess} of this {@link IRootTabPane}
    * and make the tab active to the user
    * @param ac
    */
   public void showAccountDetails(Integer ac);

   /**
    * Extract {@link PublicKey} and show accounts of that key in the right tab
    * for this {@link IRootTabPane} context.
    * <br>
    * <br>
    * In Wallet context
    * 
    * @param ac
    */
   public void showAccountOwner(Account ac);

   /**
    * Tries to look up the list of accouns controlled by the Public Key owning the account
    * @param ac
    */
   public void showAccountOwner(Integer ac);

   /**
    * In the context of the {@link IDataAccess}, show the block.
    * 
    * If there is no Block, the {@link IRootTabPane} falls back to its parent Access.
    * 
    * @param ac
    */
   public void showBlock(Block ac);

   /**
    * 
    * @param pk
    */
   public void showPublicKeyAccounts(PublicKey pk);

   /**
    * 
    * @param pk
    */
   public void showPublicKeyJavaAccountNames(PublicKeyJava pk);

   /**
    * Let the root pane decide where to forward the {@link PublicKeyJava} to.
    * <br>
    * <br>
    * The GUI tab in which it shown might depend on the key type
    * 
    * <li>If the key is a Private, it might be forwarded to Asset safe area
    * <li>If the key is a Public, it might be forwarded
    * <li>If the key is a PublicIndexed PublicKeyA, it might be forwarded
    * <li>If the key is a PublicNamed PublicKeyA
    * 
    * @param pk
    */
   public void showPublicKeyJavaAccounts(PublicKeyJava pk);

}
