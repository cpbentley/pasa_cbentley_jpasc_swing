/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.trade;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * 
 * https://www.poloniex.com/support/api/
 * @author Charles Bentley
 *
 */
public class TabsPoloniex extends TabbedBentleyPanel {

   public static final String            ID = "root_poloniex";

   private PascalSwingCtx                psc;

   private TabsPoloniexPublicAPIFunding  tabsPoloniexPublicAPIFunding;

   private TabsPoloniexPrivateAPIFunding tabsPoloniexPrivateAPIFunding;

   public TabsPoloniex(PascalSwingCtx psc) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
   }

   public void disposeTab() {

   }

   public void initTabs() {
      tabsPoloniexPublicAPIFunding = new TabsPoloniexPublicAPIFunding(psc);
      tabsPoloniexPrivateAPIFunding = new TabsPoloniexPrivateAPIFunding(psc);

      addMyTab(tabsPoloniexPublicAPIFunding);
      addMyTab(tabsPoloniexPrivateAPIFunding);

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TabsPoloniex");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabsPoloniex");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}
