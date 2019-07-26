/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.whalewatch;

import javax.swing.event.ChangeListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * Watch big transactions.
 * <br>
 * Balance Range of transaction Above 1000 PASC will be notified/search
 * <br>
 * As a bot, sends a message to a telegram channel
 * @author Charles Bentley
 *
 */
public class TabsWhaleWatch extends TabbedBentleyPanel implements IMyTab, ChangeListener {
   /**
    * 
    */
   private static final long             serialVersionUID = -1995598585458304310L;


   private IRootTabPane                  root;

   private PanelWhaleWatchKey        whaleListKeyPanel;

   private PanelWhaleWatchPasaActive       whaleListPasaPanel;

   private PanelWhaleWatchPasaBought        whalePasaBuyPanel;

   private PanelWhaleWatchPasaListedForSale whalePasaListingPanel;

   private PanelWhaleWatchKeyAmount          whalePricePanel;

   private PascalSwingCtx psc;

   public TabsWhaleWatch(PascalSwingCtx psc,IRootTabPane root) {
      super(psc.getSwingCtx(), "root_whale");
      this.psc = psc;
      this.root = root;

   }

   public void disposeTab() {
      whalePricePanel = null;
      whalePasaBuyPanel = null;
      whalePasaListingPanel = null;
      whaleListPasaPanel = null;
      whaleListKeyPanel = null;
      removeAlltabs();
   }

   public void initTabs() {
      whalePricePanel = new PanelWhaleWatchKeyAmount(psc,root);
      whalePasaBuyPanel = new PanelWhaleWatchPasaBought(psc,root);
      whalePasaListingPanel = new PanelWhaleWatchPasaListedForSale(psc,root);
      whaleListPasaPanel = new PanelWhaleWatchPasaActive(psc,root);
      whaleListKeyPanel = new PanelWhaleWatchKey(psc,root);

      this.addMyTab(whalePricePanel);
      this.addMyTab(whalePasaBuyPanel);
      this.addMyTab(whalePasaListingPanel);
      this.addMyTab(whaleListPasaPanel);
      this.addMyTab(whaleListKeyPanel);
   }

}