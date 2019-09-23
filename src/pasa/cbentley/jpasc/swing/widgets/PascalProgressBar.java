/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.widgets;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.thread.ITechRunnable;
import pasa.cbentley.jpasc.swing.actions.IStoppable;
import pasa.cbentley.jpasc.swing.actions.BActionStopTask;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.actions.BActionRefresh;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public class PascalProgressBar extends JProgressBar implements IStringable, MouseListener, IStoppable, IMyGui {

   private PascalSwingCtx     psc;

   private WorkerListTaskPage worker;

   private JPopupMenu         popupMenu;

   private String             stringKeyDone;

   private String             stringKeyScanning;

   private String             stringKeyActive;

   private BActionStopTask    actionStop;

   private JMenuItem          actionStopItem;

   public PascalProgressBar(PascalSwingCtx psc) {
      this.psc = psc;

      stringKeyScanning = "progress.scanning";
      stringKeyDone = "progress.done";
      stringKeyActive = stringKeyDone;

      this.addMouseListener(this);
      JPopupMenu pmenu = new JPopupMenu();
      popupMenu = pmenu;
      actionStop = new BActionStopTask(psc.getSwingCtx(), this);
      actionStopItem = popupMenu.add(actionStop);
      //deal with actions in this popup
      //this.add(popupMenu);
      //add right support
      this.setComponentPopupMenu(popupMenu);
   }

   public void guiUpdate() {
      SwingCtx sc = psc.getSwingCtx();
      this.setString(sc.getResString(stringKeyActive));
      this.setToolTipText(sc.getResString("progressbar.tip"));
      sc.guiUpdateOnChildrenMenuPopup(popupMenu);
   }

   /**
    * Sets the worker for acting on it when actions are made
    * @param worker
    */
   public void setSwingTask(WorkerListTaskPage worker) {
      this.worker = worker;
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void setStateToScanning() {
      actionStopItem.setEnabled(true);
      stringKeyActive = stringKeyScanning;
      guiUpdate();
   }

   public void setStateToDone() {
      actionStopItem.setEnabled(false);
      this.setValue(this.getMaximum());
      stringKeyActive = stringKeyDone;
      guiUpdate();
   }

   public void mousePressed(MouseEvent e) {
      if (e.isAltDown()) {
         if (e.getButton() == MouseEvent.BUTTON1) {
            cmdStop();
         }
      }
   }

   public void cmdStop() {
      //#debug
      psc.toDLog().pCmd("", this, PascalProgressBar.class, "cmdStop", ITechLvl.LVL_05_FINE, true);

      if (worker != null) {
         worker.getTask().requestNewState(ITechRunnable.STATE_3_STOPPED);
      }
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalProgressBar");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalProgressBar");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

}
