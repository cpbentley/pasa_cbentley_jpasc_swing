/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.wizard;

import java.util.ArrayList;
import java.util.List;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.jpasc.swing.interfaces.IHelpManager;
import pasa.cbentley.jpasc.swing.interfaces.IWizardNoob;
import pasa.cbentley.swing.ctx.SwingCtx;

public abstract class HelpManagerAbstract implements IHelpManager {

   
   
   protected final SwingCtx sc;
   public HelpManagerAbstract(SwingCtx sc) {
      this.sc = sc;
      wizards = new ArrayList<IWizardNoob>();
   }
   
   /**
    * never null. empty list if none
    * @return
    */
   public List<IWizardNoob> getWizards() {
      return wizards;
   }
   
   private final List<IWizardNoob> wizards;
   public void addWizard(IWizardNoob wizardNoob) {
      wizards.add(wizardNoob);
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "HelpManagerAbstract");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "HelpManagerAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   //#enddebug
   

}
