/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.core.src4.interfaces.ICommandable;

public interface ICommandableBlock extends ICommandable {

   public void cmdShowSelectedBlockOperationsTabHome();

   public void cmdShowSelectedBlockOperationsNewWindow();

   public void cmdShowSelectedBlockDetails();

   public void cmdShowSelectedBlockDetailsNewWindow();
   
   
}
