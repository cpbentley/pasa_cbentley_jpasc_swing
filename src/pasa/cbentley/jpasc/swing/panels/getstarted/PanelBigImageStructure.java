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

public class PanelBigImageStructure extends PanelBigImageAbstract implements IMyTab, ActionListener, IMyGui {
   /**
    * 
    */
   private static final long serialVersionUID = 1642672077820486934L;

   public PanelBigImageStructure(PascalSwingCtx psc) {
      super(psc, "getstarted_structure");
   }

   public Icon getBigIcon() {
      return psc.createImageIcon("/bigimages/pasc_structure.png", "");
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "GetStartedPascalStructure");
      super.toString(dc.sup());
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "GetStartedPascalStructure");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}