/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.core.src4.logging.BaseAppender;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BButton;

/**
 * Panel that shows application logging info.
 * <br>
 * Can also be used as {@link BaseAppender} in the Bentley logging framework.
 * <br>
 * Its position can be 
 * @author Charles Bentley
 *
 */
public class PanelTabConsoleAlone extends PanelTabConsoleAbstract implements ActionListener, IMyTab, IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = 1544273715290863726L;

   private BButton           butConsoleClear;

   public PanelTabConsoleAlone(PascalSwingCtx psc) {
      super(psc);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butConsoleClear) {
         consoleText.setText("");
      } else {

      }
   }

   public void initTab() {
      super.initTab();
      butConsoleClear = new BButton(sc, this, "but.cons.clear");
      buttonPanel.add(butConsoleClear);
   }

}