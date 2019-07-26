/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.ICommandableRefresh;

/**
 * @author Charles Bentley
 *
 */
public class PanelHelperMinMaxDouble extends PanelHelperMinMaxAbstract implements ActionListener {

   public PanelHelperMinMaxDouble(PascalSwingCtx psc, ICommandableRefresh refresh, String keyFor) {
      super(psc, refresh, keyFor);
   }

   /**
    * NULL if ""
    */
   public Double getMaxDouble() {
      String val = textMax.getText();
      if (val == null || val.equals("")) {
         if (isNullIfEmpty()) {
            return null;
         } else {
            return 0.0d;
         }
      }
      Double num = Double.valueOf(val);
      return num;
   }

   /**
    * null when no defined
    */
   public Double getMinDouble() {
      String val = textMin.getText();
      if (val == null || val.equals("")) {
         if (isNullIfEmpty()) {
            return null;
         } else {
            return 0.0d;
         }
      }
      Double num = Double.valueOf(val);
      return num;
   }

}
