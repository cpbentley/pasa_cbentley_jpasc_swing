/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.interfaces.ICommandable;

public interface ICommandableConnect extends ICommandable {

   /**
    * Show the ui for a successful connection
    */
   public void showUISuccess();

   /**
    * Show the ui for a failed connection
    */
   public void showUIFailure();
}
