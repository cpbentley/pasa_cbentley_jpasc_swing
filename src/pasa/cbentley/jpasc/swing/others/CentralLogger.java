/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.others;

import java.awt.Color;
import java.util.Date;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.UserLogJournal;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabConsole;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabConsoleAbstract;

/**
 * {@link IUserLog} wrapper around a {@link PanelTabConsole}
 * @author Charles Bentley
 *
 */
public class CentralLogger implements IUserLog {

   private PanelTabConsoleAbstract consolePanel;

   private PascalSwingCtx          psc;

   public CentralLogger(PascalSwingCtx psc, PanelTabConsoleAbstract cp) {
      this.psc = psc;
      this.consolePanel = cp;

   }

   public void consoleLog(String str) {
      consolePanel.appendToPaneAsLine(str, Color.BLACK);
   }

   public void consoleLogDate(String str) {
      Date d = new Date(System.currentTimeMillis());
      consolePanel.appendToPaneNoLine(psc.getFormatDateTime().format(d) + " : ", Color.ORANGE);
      consoleLog(str);
   }

   public void consoleLogDateGreen(String str) {
      Date d = new Date(System.currentTimeMillis());
      consolePanel.appendToPaneNoLine(psc.getFormatDateTime().format(d) + " : ", Color.ORANGE);
      consoleLogGreen(str);
   }

   public void consoleLogDateRed(String str) {
      Date d = new Date(System.currentTimeMillis());
      consolePanel.appendToPaneNoLine(psc.getFormatDateTime().format(d) + " : ", Color.ORANGE);
      consoleLogError(str);
   }

   public void consoleLogError(String str) {
      consolePanel.appendToPaneAsLine(str, psc.getRed());
   }

   public void consoleLogGreen(String str) {
      consolePanel.appendToPaneAsLine(str, psc.getGreen());
   }

   public void processOld(IUserLog log) {
      if (log instanceof UserLogJournal) {
         UserLogJournal userLog = (UserLogJournal) log;
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CentralLogger");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CentralLogger");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }

   //#enddebug

}
