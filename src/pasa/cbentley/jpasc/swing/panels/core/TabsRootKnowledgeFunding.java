/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.funding.FundingItem;
import pasa.cbentley.jpasc.swing.panels.funding.FundingTab;

/**
 * Manage the tabs to the main wallet.
 * 
 * @author Charles Bentley
 *
 */
public class TabsRootKnowledgeFunding extends FundingTab  {

   public TabsRootKnowledgeFunding(PascalSwingCtx psc) {
      super(psc, TabsRootKnowledge.ID);

      FundingItem fi = new FundingItem(psc, new Integer(560117),"funding.knowledge.desc");
      this.setFundingItem(fi);
   }


}