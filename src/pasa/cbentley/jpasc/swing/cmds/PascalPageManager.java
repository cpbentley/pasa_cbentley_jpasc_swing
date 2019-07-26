/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.core.TabsRootRPC;
import pasa.cbentley.jpasc.swing.panels.getstarted.PanelGetStartedIntroduction;
import pasa.cbentley.swing.imytab.PageStrings;

/**
 * Knows the factory positions of tabs
 * @author Charles Bentley
 *
 */
public class PascalPageManager {

   private PascalSwingCtx psc;

   public PascalPageManager(PascalSwingCtx psc) {
      this.psc = psc;
   }

   public PageStrings getPageBuyCoins() {
      PageStrings pageStrings = new PageStrings(psc.getSwingCtx(), new String[] { "", "" });
      return pageStrings;
   }

   public PageStrings getPageFirstPASA() {
      String[] ar = new String[] { TabsRootRPC.ID, PanelGetStartedIntroduction.ID };
      PageStrings pageStrings = new PageStrings(psc.getSwingCtx(), ar);
      return pageStrings;
   }

}
