/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.BorderLayout;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.TabPosition;

/**
 * https://alvinalexander.com/java/java-uimanager-color-keys-list
 * @author Charles Bentley
 *
 */
public class PanelTabPascalSwing extends PanelTabAbstractPascal implements IMyGui, IStringable {
   /**
    * 
    */
   private static final long serialVersionUID = 3677860224905923226L;

   TabsRootRPC               rpcTabPanel;

   private PanelTabLogin        login;

   private PanelTabConsole      consolePanel;

   /**
    * Depending on the card selected, The North Panel changes
    * @param consolePanel 
    * @param lfModule 
    */
   public PanelTabPascalSwing(PascalSwingCtx psc, PanelTabLogin login, PanelTabConsole consolePanel) {
      super(psc, "main_panel");
      this.login = login;
      this.consolePanel = consolePanel;

      //never null but a dummy disconnect client

   }

   public void disposeTab() {

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
      setLayout(new BorderLayout());

      rpcTabPanel = new TabsRootRPC(psc);
      rpcTabPanel.addMyTab(login, TabPosition.POS_1_TOP);
      rpcTabPanel.addMyTab(consolePanel, TabPosition.POS_2_BOTTOM);

      this.add(rpcTabPanel, BorderLayout.CENTER);
      psc.getRootPageManager().setRoot(rpcTabPanel);
      rpcTabPanel.initCheck();

   }

   public void tabGainFocus() {

   }

   public void tabLostFocus() {

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalSwingPanel");

   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalSwingPanel");
   }

   public UCtx toStringGetUCtx() {
      return psc.getSwingCtx().getUCtx();
   }
   //#enddebug

}
