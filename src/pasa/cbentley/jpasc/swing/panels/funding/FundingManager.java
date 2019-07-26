/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.funding;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class FundingManager {

   private PascalSwingCtx psc;

   private FundingItem    poloPublic;

   private FundingItem    poloPrivate;

   private FundingItem    snapshot;

   private FundingItem    knowledge;

   private FundingItem    tradePasa;

   private FundingItem    whaleWatch;

   public FundingItem getPoloPublic() {
      return poloPublic;
   }

   public FundingItem getPoloPrivate() {
      return poloPrivate;
   }
   
   /**
    * Return the Account advertising which Exchange is sponsor
    * 
    * jascal_recommends_exchange_[yourexchangehere] ... Name will be visible
    * 
    * 
    * @return
    */
   public int getAccountBuy() {
      return 560150;
   }

   /**
    * The first account where an exchange name is located. Voting works
    * until a name change occurs with a special command
    * @return
    */
   public int getExchangeVotingStart() {
      return 560201;
   }
   
   public FundingManager(PascalSwingCtx psc) {
      this.psc = psc;

      //init funding items
      snapshot = new FundingItem(psc, 560106, "funding.snapshot.desc");

      snapshot = new FundingItem(psc, 560106, "funding.snapshot.desc");
      knowledge = new FundingItem(psc, 560108, "funding.knowledge.desc");
      poloPublic = new FundingItem(psc, 560120, "funding.polopublic.desc");
      poloPrivate = new FundingItem(psc, 560122, "funding.poloprivate.desc");

      tradePasa = new FundingItem(psc, 560122, "funding.tradepasa.desc");
      whaleWatch = new FundingItem(psc, 560122, "funding.whalewatch.desc");
   }

}
