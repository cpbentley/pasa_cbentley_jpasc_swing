/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.funding;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.layout.RiverPanel;
import pasa.cbentley.swing.widgets.b.BLabel;

public class FundingPrinciples extends RiverPanel implements IMyGui {

   private BLabel labNumber1Title;

   private BLabel labPrincipleTitle;

   private BLabel labNumber1Principle;

   private BLabel labNumber2Title;

   private BLabel labNumber2Principle;

   private BLabel labNumber3Title;

   private BLabel labNumber4Title;

   private BLabel labNumber4Principle;

   private BLabel labNumber3Principle;

   private BLabel labNumber5Title;

   private BLabel labNumber5Principle;

   private BLabel labNumber6Title;

   private BLabel labNumber6Principle;

   public FundingPrinciples(PascalSwingCtx psc) {
      super(psc.getSwingCtx());

      //derive the font
      labPrincipleTitle = new BLabel(sc, "funding.principles.title");
      
      labPrincipleTitle.setStyleBold(4);
      //set a style that will be set bby
      
      labNumber1Title = new BLabel(sc, "number1");
      labNumber1Principle = new BLabel(sc, "funding.principles.1");

      labNumber2Title = new BLabel(sc, "number2");
      labNumber2Principle = new BLabel(sc, "funding.principles.2");
      
      labNumber3Title = new BLabel(sc, "number3");
      labNumber3Principle = new BLabel(sc, "funding.principles.3");
      
      labNumber4Title = new BLabel(sc, "number4");
      labNumber4Principle = new BLabel(sc, "funding.principles.4");
      
      labNumber5Title = new BLabel(sc, "number5");
      labNumber5Principle = new BLabel(sc, "funding.principles.5");
      
      labNumber6Title = new BLabel(sc, "number6");
      labNumber6Principle = new BLabel(sc, "funding.principles.6");
      
      
      this.raddBrHfill(labPrincipleTitle);
      
      this.raddBr(labNumber1Title);
      this.raddTab(labNumber1Principle);
      
      this.raddBr(labNumber2Title);
      this.raddTab(labNumber2Principle);

      this.raddBr(labNumber3Title);
      this.raddTab(labNumber3Principle);

      this.raddBr(labNumber4Title);
      this.raddTab(labNumber4Principle);
      
      this.raddBr(labNumber5Title);
      this.raddTab(labNumber5Principle);
      
      this.raddBr(labNumber6Title);
      this.raddTab(labNumber6Principle);

   }

 

   public void guiUpdate() {
      
      //update font
      
      sc.guiUpdateOnChildren(this);
      
   }
   
   
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "FundingPrinciples");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FundingPrinciples");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug
   

   
}
