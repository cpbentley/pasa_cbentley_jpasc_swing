/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.getstarted;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

public class TabsGetStarted extends TabbedBentleyPanel implements IMyTab, ActionListener, IMyGui {
   public static final String        ID               = "root_getstarted";

   /**
    * 
    */
   private static final long         serialVersionUID = -7396946292632297820L;

   private PanelBigImageInflation getStartedPascalInflation;

   private PanelBigImageStructure getStartedPascalStructure;

   private PanelGetStartedIntroduction    getStartPanel;

   private PascalSwingCtx            psc;

   private TabsGetFirstPasa          tabsGetFirstPasa;

   public TabsGetStarted(PascalSwingCtx psc) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
   }

   public void actionPerformed(ActionEvent e) {
   }

   public void disposeTab() {
      if (isInitialized()) {
         getStartPanel = null;
         tabsGetFirstPasa = null;
         getStartedPascalInflation = null;
         getStartedPascalStructure = null;
         removeAlltabs();
      }
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void initTabs() {
      getStartPanel = new PanelGetStartedIntroduction(psc);
      tabsGetFirstPasa = new TabsGetFirstPasa(psc);
      getStartedPascalStructure = new PanelBigImageStructure(psc);
      getStartedPascalInflation = new PanelBigImageInflation(psc);
      
      addMyTab(getStartPanel);
      addMyTab(tabsGetFirstPasa);
      addMyTab(getStartedPascalStructure);
      addMyTab(getStartedPascalInflation);
   }

   public void tabGainFocus() {
      super.tabGainFocus();
   }

   public void tabLostFocus() {
      super.tabLostFocus();
   }

}