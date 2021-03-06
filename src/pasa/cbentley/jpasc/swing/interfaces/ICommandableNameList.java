/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.interfaces;

import pasa.cbentley.swing.cmd.ICommandableRefresh;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface ICommandableNameList extends ICommandableRefresh {

   public void cmdGoToEdit();
}
