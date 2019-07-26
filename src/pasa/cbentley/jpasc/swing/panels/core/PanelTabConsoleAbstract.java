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
public abstract class PanelTabConsoleAbstract extends PanelTabAbstractPascal implements ActionListener, IMyTab, IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -240824720297118825L;


   protected BTextPane         consoleText;


   protected JPanel buttonPanel;


   public PanelTabConsoleAbstract(PascalSwingCtx psc) {
      super(psc, "console_pasa");

      this.setLayout(new BorderLayout());

      EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
      consoleText = new BTextPane(sc);
      //consoleText.setLineWrap(false); //problem with wrap is that it does not expand in center of borderlayout
      consoleText.setBorder(eb);
      //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      consoleText.setMargin(new Insets(5, 5, 5, 5));
      //jt.setPreferredSize(new Dimension(400, 400));
      JScrollPane jsp = new JScrollPane(consoleText);
      jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      jsp.setPreferredSize(new Dimension(400, 200));

      this.add(jsp, BorderLayout.CENTER);

   }

   protected TabbedBentleyPanel getParentBentley() {
      return getTabPosition().getParent();
   }

   public void appendToPaneAsLine(String msg, Color c) {
      psc.appendToPane(consoleText, msg + "\n", c);
   }

   public void appendToPaneNoLine(String msg, Color c) {
      psc.appendToPane(consoleText, msg, c);
   }

   public void disposeTab() {

   }


   public void initTab() {
      buttonPanel = new JPanel();
      int numCol = 1;
      int numRows = 3;
      buttonPanel.setLayout(new GridLayout(numRows, numCol));
      this.add(buttonPanel, BorderLayout.EAST);
   }

   public void tabGainFocus() {

   }

   public void tabLostFocus() {

   }

}