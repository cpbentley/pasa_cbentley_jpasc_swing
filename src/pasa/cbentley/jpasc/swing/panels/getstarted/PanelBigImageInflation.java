/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.getstarted;

import java.awt.event.ActionListener;

import javax.swing.Icon;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;

public class PanelBigImageInflation extends PanelBigImageAbstract implements IMyTab, ActionListener, IMyGui {
   /**
    * 
    */
   private static final long serialVersionUID = 1642672077820486934L;

   public PanelBigImageInflation(PascalSwingCtx psc) {
      super(psc, "getstarted_inflation");
   }

   public Icon getBigIcon() {
      return psc.createImageIcon("/bigimages/PascalCoinIssuanceV4.png", "");
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "GetStartedPascalInflation");
      super.toString(dc.sup());
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "GetStartedPascalInflation");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}