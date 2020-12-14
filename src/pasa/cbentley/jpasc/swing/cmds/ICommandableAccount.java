/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.jpasc.pcore.domain.java.AccountJava;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;

public interface ICommandableAccount extends ICommandable {
   /**
    * 
    */
   public void cmdShowSelectedAccountDetails();

   /**
    * 
    */
   public void cmdShowSelectedAccountDetailsNewWindow();

   /**
    * Shows the names owned by the selected accounts key.
    * 
    * UI wise This is done in the current tab context
    */
   public void cmdShowSelectedAccountKeyNames();

   /**
    * {@link ICommandableAccount#cmdShowSelectedAccountKeyNames()}
    * but in a new window.
    */
   public void cmdShowSelectedAccountKeyNamesNewWindow();

   /**
    * 
    */
   public void cmdShowSelectedAccountOwner();

   /**
    * 
    */
   public void cmdShowSelectedAccountOwnerNewWindow();

   public void cmdShowSelectedAccountReceiverDetails();

   public void cmdShowSelectedAccountReceiverDetailsNewWindow();

   /**
    * 
    */
   public void cmdShowSelectedAccountSellerDetails();

   /**
    * 
    */
   public void cmdShowSelectedAccountSellerDetailsNewWindow();

   /**
    * 
    */
   public void cmdShowSelectedAccountSendBalanceFrom();

   /**
    * 
    */
   public void cmdShowSelectedAccountSendBalanceFromNewWindow();

   /**
    * 
    */
   public void cmdShowSelectedAccountSendBalanceTo();

   /**
    * 
    */
   public void cmdShowSelectedAccountSendBalanceToNewWindow();

   /**
    * 
    */
   public void cmdShowSelectedAccountSendKeyTo();

   /**
    * 
    */
   public void cmdShowSelectedAccountSendKeyToNewWindow();

   /**
    * Currently selected {@link AccountJava} for this {@link ICommandableAccount}.
    * <br>
    * Used to make better looking string describing the command
    * @return
    */
   public AccountJava getSelectedAccountJava();

   public Account getSelectedAccount();

}
