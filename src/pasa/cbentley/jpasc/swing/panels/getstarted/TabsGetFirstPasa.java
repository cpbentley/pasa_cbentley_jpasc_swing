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

public class TabsGetFirstPasa extends TabbedBentleyPanel implements IMyTab, ActionListener, IMyGui {
   public static final String ID               = "root_firstpasa";

   /**
    * 
    */
   private static final long  serialVersionUID = -7396946292632297820L;

   private PanelGetStartedFirstPasaGetPasaCom getFirstPasaGetPasaCom;

   private PanelGetStartedFirstPasaGetPasaOrg getFirstPasaGetPasaOrg;

   private PascalSwingCtx     psc;

   public TabsGetFirstPasa(PascalSwingCtx psc) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
   }

   public void actionPerformed(ActionEvent e) {
   }

   public void disposeTab() {
      if (isInitialized()) {
         getFirstPasaGetPasaOrg = null;
         getFirstPasaGetPasaCom = null;
         removeAlltabs();
      }
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void initTabs() {
      getFirstPasaGetPasaOrg = new PanelGetStartedFirstPasaGetPasaOrg(psc);
      getFirstPasaGetPasaCom = new PanelGetStartedFirstPasaGetPasaCom(psc);

      addMyTab(getFirstPasaGetPasaOrg);
      addMyTab(getFirstPasaGetPasaCom);
   }

   public void tabGainFocus() {
   }

   public void tabLostFocus() {
   }

}