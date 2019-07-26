/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * A panel with login on top and a console on the bottom.
 * 
 * Set the center component with 
 * @author Charles Bentley
 *
 */
public class PanelTabLoginConsole extends PanelTabAbstractPascal implements IMyGui, IStringable {
   /**
    * 
    */
   private static final long       serialVersionUID = 3677860224905923226L;

   private PanelTabLogin           panelLogin;

   private PanelTabConsoleAbstract panelConsole;

   private JPanel                  panelCenter;

   public PanelTabLogin getPanelLogin() {
      return panelLogin;
   }

   public PanelTabConsoleAbstract getPanelConsole() {
      return panelConsole;
   }

   /**
    * Depending on the card selected, The North Panel changes
    * @param consolePanel 
    * @param lfModule 
    */
   public PanelTabLoginConsole(PascalSwingCtx psc, PanelTabLogin login, PanelTabConsoleAbstract consolePanel) {
      super(psc, "main_panel");
      this.panelLogin = login;
      this.panelConsole = consolePanel;
      setLayout(new BorderLayout());
   }

   public void disposeTab() {

   }

   /**
    * Call this after the panel has been configured.
    */
   public void loginAuto() {
      panelLogin.initCheck();
      //try to connect to default paremeters
      psc.getCmds().cmdConnectInitJPascalCoin();
   }

   /**
    * Updates Gui and
    * Calls {@link IMyGui#guiUpdate()}
    * on all children that are {@link IMyGui}.
    */
   public void guiUpdate() {
      //first update tabs
      super.guiUpdate();
   }

   public void initTab() {
      this.add(panelLogin, BorderLayout.NORTH);
      this.add(panelConsole, BorderLayout.SOUTH);
   }

   /**
    * 
    * @param panel
    */
   public void setPanelCenter(JPanel panel) {
      panelCenter = panel;
      this.add(panel, BorderLayout.CENTER);
   }

   public void tabGainFocus() {
      //#debug
      toDLog().pFlow("", this, PanelTabLoginConsole.class, "tabGainFocus", LVL_05_FINE, true);
      if (panelLogin != null) {
         panelLogin.tabGainFocus();
      }
      if (panelConsole != null) {
         panelConsole.tabGainFocus();
      }
      if (panelCenter instanceof IMyTab) {
         ((IMyTab) panelCenter).tabGainFocus();
      }
   }

   public void tabLostFocus() {
      //#debug
      toDLog().pFlow("", this, PanelTabLoginConsole.class, "tabGainFocus", LVL_05_FINE, true);
      if (panelLogin != null) {
         panelLogin.tabLostFocus();
      }
      if (panelConsole != null) {
         panelConsole.tabLostFocus();
      }
      if (panelCenter instanceof IMyTab) {
         ((IMyTab) panelCenter).tabLostFocus();
      }
   }


}
