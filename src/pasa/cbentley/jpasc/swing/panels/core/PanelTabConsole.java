/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import pasa.cbentley.core.src4.logging.BaseAppender;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BTextPane;

/**
 * Panel that shows application logging info.
 * <br>
 * Can also be used as {@link BaseAppender} in the Bentley logging framework.
 * <br>
 * Its position can be 
 * @author Charles Bentley
 *
 */
public class PanelTabConsole extends PanelTabConsoleAbstract implements ActionListener, IMyTab, IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -240824720297118825L;

   private BButton           butConsoleToCenter;

   private BButton           butConsoleToFrame;

   private BButton           butConsoleToSouth;

   private BButton           butGoToEvenTab;

   private BButton           butConsoleClear;

   public PanelTabConsole(PascalSwingCtx psc) {
      super(psc);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butConsoleClear) {
         consoleText.setText("");
      } else {
         TabbedBentleyPanel parent = getParentBentley();
         if (parent == null) {
            psc.getLog().consoleLogError("Console not initialized correctly");
         }
         if (e.getSource() == butConsoleToFrame) {
            parent.cmdTabToFrame(this);
         } else if (e.getSource() == butConsoleToCenter) {
            parent.cmdTabToCenter(this);
         } else if (e.getSource() == butConsoleToSouth) {
            parent.cmdTabToBottom(this);
         }
      }
   }

   public void guiUpdate() {
      //its automatic
//      butConsoleToFrame.setText(sc.getResString("but.frame"));
//      butConsoleToCenter.setText(sc.getResString("but.tab"));
//      butConsoleClear.setText(sc.getResString("but.clear"));
//      butConsoleToSouth.setText(sc.getResString("but.south"));
//      butGoToEvenTab.setText(sc.getResString("but.jump"));

   }


   public void initTab() {
      super.initTab();
      
      butConsoleToFrame = new BButton(sc, this, "but.cons.frame");
      butConsoleToCenter = new BButton(sc, this, "but.cons.center");
      butConsoleToSouth = new BButton(sc, this, "but.cons.south");
      butConsoleClear = new BButton(sc, this, "but.cons.clear");
      butGoToEvenTab = new BButton(sc, this, "but.cons.jump");

      butGoToEvenTab.setToolTipText("Go to tab relevant to the last message");

      buttonPanel.add(butConsoleClear);
      buttonPanel.add(butConsoleToFrame);
      buttonPanel.add(butConsoleToCenter);
      buttonPanel.add(butConsoleToSouth);
      buttonPanel.add(butGoToEvenTab);

   }

}