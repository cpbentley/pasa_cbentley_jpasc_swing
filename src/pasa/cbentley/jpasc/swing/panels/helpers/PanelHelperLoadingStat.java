/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.utils.StatEntry;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.jpasc.swing.widgets.PascalProgressBar;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.threads.WorkerStat;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public class PanelHelperLoadingStat extends PanelPascal {

   private JComponent          compAfterStats;

   private JComponent          compProgressAfter;

   private JComponent          compProgressBefore;

   private IntToObjects        entries;

   protected PascalProgressBar progressBar;

   public PanelHelperLoadingStat(PascalSwingCtx psc) {
      super(psc);

      progressBar = new PascalProgressBar(psc);
      progressBar.setStringPainted(true);

      entries = new IntToObjects(psc.getUCtx());
   }

   public void addToPanelSerially(JPanel panel) {
      if (compProgressBefore != null) {
         panel.add(compProgressBefore);
      }
      panel.add(progressBar);
      if (compProgressAfter != null) {
         panel.add(compProgressAfter);
      }
      for (int i = 0; i < entries.nextempty; i++) {
         StatEntry o = (StatEntry) entries.getObjectAtIndex(i);
         panel.add(o.getLabel());
         panel.add(o.getTextField());
      }
      if (compAfterStats != null) {
         panel.add(compAfterStats);
      }
   }

   public JComponent getCompAfterStats() {
      return compAfterStats;
   }

   private JTextField getTextField(int index) {
      return ((StatEntry) entries.getObjectAtIndex(index)).getTextField();
   }

   public void resetToSize(int size) {
      entries.clearNewTo(size);
   }

   public void set(int index, String labelKey, int fieldSize) {
      StatEntry statEntry = new StatEntry(psc, labelKey, fieldSize);
      //#mdebug
      //debug warning if already here
      if (entries.getObjectAtIndex(index) != null) {
         String message = "Object already set. Should not happen. index=" + index + " labelKey=" + labelKey;
         toDLog().pInit(message, this, PanelHelperLoadingStat.class, "set", LVL_09_WARNING, false);
      }
      //#enddebug

      entries.setObject(statEntry, index);
   }

   public void setCompAfterStats(JComponent compAfterStats) {
      this.compAfterStats = compAfterStats;
   }

   public void setCompProgressAfter(JComponent compProgressAfter) {
      this.compProgressAfter = compProgressAfter;
   }

   public void setCompProgressBefore(JComponent compProgressBefore) {
      this.compProgressBefore = compProgressBefore;
   }

   public void setProgressText(String text) {
      progressBar.setString(text);
   }

   public void setStat(int index, Double statDoubleValue) {
      getTextField(index).setText(String.valueOf(statDoubleValue));
   }

   public void setStat(int index, int statIntValue) {
      getTextField(index).setText("" + statIntValue);
   }

   public void setStat(int index, String statStringValue) {
      getTextField(index).setText(statStringValue);
   }

   public void setStateStat(WorkerStat ws) {

      progressBar.setMaximum(ws.getEntriesTotal());
      progressBar.setValue(ws.getEntriesDone());
   }

   public void setStateCount(int count) {
      progressBar.setValue(count);
   }

   /**
    * Clean the progress bar for good UX
    */
   public void setStateDone() {
      progressBar.setStateToDone();
   }

   public void setStateCanceled() {
      progressBar.setStateToCanceled();
   }

   public void setStateScanning() {
      progressBar.setStateToScanning();
   }

   public void setWorker(WorkerListTaskPage worker) {
      progressBar.setSwingTask(worker);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelHelperLoadingStat");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelHelperLoadingStat");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
