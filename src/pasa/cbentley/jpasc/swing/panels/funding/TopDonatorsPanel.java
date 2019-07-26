/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.funding;

import java.util.ArrayList;
import java.util.List;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.WorkerTopAccountDonators;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.layout.RiverPanel;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BLabel;

/**
 * Returns top funding accounts by looking at operations history
 * @author Charles Bentley
 *
 */
public class TopDonatorsPanel extends RiverPanel implements IWorkerPanel, IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = 6114969844633152062L;

   private FundingItem       fi;

   private ArrayList<BLabel> names;

   private List<BLabel>      nums;

   private ArrayList<BLabel> pascs;

   private PascalSwingCtx    psc;

   private BLabel            title;

   public TopDonatorsPanel(PascalSwingCtx psc, FundingItem fi, int num) {
      super(psc.getSwingCtx());
      this.psc = psc;
      this.fi = fi;

      //fetch

      title = new BLabel(sc, "funding.top3.title");

      this.raddBrHfill(title);

      nums = new ArrayList<BLabel>(num);
      names = new ArrayList<BLabel>(num);
      pascs = new ArrayList<BLabel>(num);

      for (int i = 0; i < num; i++) {
         BLabel lab = new BLabel(sc, "number" + (i + 1));
         BLabel name = new BLabel(sc);
         BLabel pasc = new BLabel(sc);

         nums.add(lab);
         names.add(name);
         pascs.add(pasc);

         this.raddBr(lab);
         this.raddTab(pasc);
         this.raddBrHfill(name);
      }

   }

   public void doUpdateDonatorList() {
      WorkerTopAccountDonators worker = null;

      worker.execute();
   }

   public void guiUpdate() {
      sc.guiUpdateOnChildren(this);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "TopDonatorsPanel");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TopDonatorsPanel");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

   public void panelSwingWorkerCancelled(PanelSwingWorker worker) {
      // TODO Auto-generated method stub

   }
 public void panelSwingWorkerStarted(PanelSwingWorker worker) {
      
   }
   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      // TODO Auto-generated method stub

   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
      // TODO Auto-generated method stub

   }

}
