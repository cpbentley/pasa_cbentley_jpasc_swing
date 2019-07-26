/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.block;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class TablePanelBlockLast24h extends TablePanelBlockLastAbstract {
   /**
    * 
    */
   private static final long  serialVersionUID = 3938048866952414360L;

   public static final String ID               = "blocks_24h";

   public TablePanelBlockLast24h(PascalSwingCtx psc) {
      super(psc, ID, 288);
   }

}
