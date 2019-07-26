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
 * https://www.poloniex.com/support/api/
 * @author Charles Bentley
 *
 */
public class TabsPoloniexPrivateAPIFunding extends FundingTab {

   public static final String ID = "poloniex_private_api";

   public TabsPoloniexPrivateAPIFunding(PascalSwingCtx psc) {
      super(psc, ID);
      setFundingItem(psc.getFundingManager().getPoloPrivate());
   }

}
