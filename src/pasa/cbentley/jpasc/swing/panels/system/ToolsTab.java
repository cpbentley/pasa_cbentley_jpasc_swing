/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.system;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;

public class ToolsTab extends PanelTabAbstractPascal implements ActionListener {

   public static final String ID = "tools";


   public ToolsTab(PascalSwingCtx psc) {
      super(psc, ID);
   }

   public void tabLostFocus() {

   }

   public void tabGainFocus() {

   }

   public void disposeTab() {

   }

   public void initTab() {
      this.setLayout(new BorderLayout());
   }


   public void actionPerformed(ActionEvent e) {

   }

}
