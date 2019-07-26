/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.funding;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class FundingItem implements IStringable {

   private Integer account;

   public Integer getAccount() {
      return account;
   }

   private String  key;

   private PascalSwingCtx psc;

   
   public FundingItem(PascalSwingCtx psc, Integer account, String keyDescription) {
      this.psc = psc;
      this.account = account;
      this.key = keyDescription;
   }
   
   
   public String getKeyFeatureDescription() {
      return key;
   }

   
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "FundingItem");
      toStringPrivate(dc);
   }


   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("account", account);
      dc.appendVarWithSpace("key", key);
      dc.appendVarWithSpace("text", psc.getSwingCtx().getResString(key));
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
    
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FundingItem");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug
   

}
