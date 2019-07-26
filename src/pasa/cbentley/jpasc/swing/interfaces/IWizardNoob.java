/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.interfaces;

/**
 * Implements the 
 * @author Charles Bentley
 *
 */
public interface IWizardNoob {

   public void cmdShowNoobWizard(Object caller);
   
   public String getID();
   
   /**
    * The key to be used by menus/button for launching the wizard
    * @return
    */
   public String getStringKeyTitle();
}
