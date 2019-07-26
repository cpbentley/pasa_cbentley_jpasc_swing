/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.Component;

import javax.swing.event.ChangeListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * Manage the tabs to the main wallet.
 * 
 * @author Charles Bentley
 *
 */
public class TabsRootKnowledge extends TabbedBentleyPanel implements ChangeListener {

   public static final String ID = "root_know";

   private PascalSwingCtx     psc;

   public TabsRootKnowledge(PascalSwingCtx psc) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;

      //how do we make sure this field is correct and not null?

      //panels are always created. their content will be initialized later when needed.
      //a panel can be Framed outside... but at least one belongs

   }

   public void disposeTab() {
      Component c = jtabbePane.getSelectedComponent();
      int numTabs = jtabbePane.getTabCount();
      for (int i = 0; i < numTabs; i++) {
         IMyTab tab = (IMyTab) jtabbePane.getComponentAt(i);
         if (tab != null && tab != c) {
            tab.disposeTab();
            tab.setDisposed();
         }
      }
   }

   public void initTabs() {

   }

   public void tabGainFocus() {
   }

   public void tabLostFocus() {
   }

}