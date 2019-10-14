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
 * Validates for Integers
 * @author Charles Bentley
 *
 */
public class PanelHelperMinMaxInteger extends PanelHelperMinMaxAbstract implements ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = 8988153616935369520L;

   public PanelHelperMinMaxInteger(PascalSwingCtx psc, ICommandableRefresh refresh, String keyRoot, IStringPrefIDable id) {
      super(psc, refresh, keyRoot, id);
   }

   /**
    * NULL if ""
    */
   public Integer getMaxInteger() {
      String val = textMax.getText();
      if (val == null || val.equals("")) {
         if (isNullIfEmpty()) {
            return null;
         } else {
            return 0;
         }
      }
      return getReturn(val);
   }

   /**
    * null when no defined
    */
   public Integer getMinInteger() {
      String val = textMin.getText();
      if (val == null || val.equals("")) {
         if (isNullIfEmpty()) {
            return null;
         } else {
            return 0;
         }
      }
      return getReturn(val);
   }

   private Integer getReturn(String val) {
      try {
         Integer num = Integer.valueOf(val);
         return num;
      } catch (NumberFormatException e) {
         e.printStackTrace();
         return getReturnError();
      }
   }

   private Integer getReturnError() {
      if (isNullIfEmpty()) {
         return null;
      } else {
         return 0;
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelHelperMinMaxInteger");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelHelperMinMaxInteger");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
