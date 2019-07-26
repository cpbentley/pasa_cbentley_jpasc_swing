/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyTab;

public class PrintableTab implements Printable {

   private SwingCtx sc;
   private Component c;

   public PrintableTab(SwingCtx sc, IMyTab tab) {
      this.sc = sc;
      if(tab instanceof Component) {
         c = (Component) tab;
      } else {
         throw new IllegalArgumentException();
      }
   }

   /**
    * TODO multiple page print. You need programmatically scroll the 
    */
   public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
      if (pageIndex > 0) { 
         /* We have only one page, and 'page' is zero-based */
         return NO_SUCH_PAGE;
      }

      /* User (0,0) is typically outside the imageable area, so we must
       * translate by the X and Y values in the PageFormat to avoid clipping
       */
      Graphics2D g2d = (Graphics2D) graphics;
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

      /* Now print the window and its visible contents */
      c.printAll(g2d);

      /* tell the caller that this page is part of the printed document */
      return PAGE_EXISTS;
   }
}
