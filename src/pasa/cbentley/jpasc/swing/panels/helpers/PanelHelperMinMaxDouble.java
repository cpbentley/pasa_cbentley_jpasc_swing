/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionListener;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.interfaces.IStringPrefIDable;

/**
 * @author Charles Bentley
 *
 */
public class PanelHelperMinMaxDouble extends PanelHelperMinMaxAbstract implements ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = -7980019462969959631L;

   public PanelHelperMinMaxDouble(PascalSwingCtx psc, ICommandableRefresh refresh, String keyRoot, IStringPrefIDable id) {
      super(psc, refresh, keyRoot, id);
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

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelHelperMinMaxDouble");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelHelperMinMaxDouble");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
