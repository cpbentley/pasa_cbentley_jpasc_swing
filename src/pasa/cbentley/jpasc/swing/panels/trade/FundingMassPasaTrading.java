/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.trade;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.funding.FundingTab;

/**
 * 
 *
 * @author Charles Bentley
 *
 */
public class FundingMassPasaTrading extends FundingTab {

   public static final String ID = "poloniex_public_api";

   public FundingMassPasaTrading(PascalSwingCtx psc) {
      super(psc, ID);
      setFundingItem(psc.getFundingManager().getPoloPublic());
   }

}
