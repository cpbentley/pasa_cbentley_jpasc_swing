/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.interfaces.ICommandable;

public interface ICommandableKey extends ICommandable {

   /**
    * Change the name of the key..
    * 
    * Depending on the kind of key.. if its a chain key, the cmd changes the key name alias
    * 
    */
   public void cmdChangeKeyName();

   /**
    * Shows the accounts of selected key
    */
   public void cmdShowKeyAccounts();

   /**
    * Same as {@link ICommandableKey#cmdShowKeyAccounts()} but in a new window
    */
   public void cmdShowKeyAccountsNewWindow();
   
   /**
    * Shows the accounts with names of the selected key
    */
   public void cmdShowKeyAccountNames();

   /**
    * Same as {@link ICommandableKey#cmdShowKeyAccountNames()} but in a new window
    */
   public void cmdShowKeyAccountNamesNewWindow();

}
