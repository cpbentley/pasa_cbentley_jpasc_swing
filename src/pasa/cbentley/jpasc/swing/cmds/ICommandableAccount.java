/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.jpasc.pcore.domain.java.AccountJava;

public interface ICommandableAccount extends ICommandable {
   /**
    * 
    */
   public void cmdShowSelectedAccountDetails();

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
   public void cmdShowSelectedAccountDetailsNewWindow();

   /**
    * 
    */
   public void cmdShowSelectedAccountOwner();

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
   public void cmdShowSelectedAccountOwnerNewWindow();

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

}
