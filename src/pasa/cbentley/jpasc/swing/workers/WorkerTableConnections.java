/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import java.util.Iterator;
import java.util.List;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.client.IPascalCoinClient;
import pasa.cbentley.jpasc.pcore.rpc.model.Connection;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.ModelTableConnections;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

public class WorkerTableConnections extends PanelSwingWorker<ModelTableConnections, Connection> {

   private IPascalCoinClient            pmodelclient;

   private final ModelTableConnections tableModel;

   private PascalSwingCtx psc;

   public WorkerTableConnections(PascalSwingCtx psc,ModelTableConnections tableModel, IWorkerPanel wp) {
      super(psc.getSwingCtx(),wp);
      this.psc = psc;
      this.tableModel = tableModel;
      this.pmodelclient = psc.getPascalClient();
   }

   @Override
   protected ModelTableConnections doInBackground() {
      List<Connection> list = pmodelclient.getConnections();
      if (list != null) {
         Iterator<Connection> it = list.iterator();
         while (it.hasNext()) {
            Connection a = it.next();
            publish(a);
            Thread.yield();
         }
      }
      return tableModel;
   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   @Override
   protected void process(List<Connection> chunks) {
      tableModel.addRows(chunks);
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "WorkerTableConnections");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerTableConnections");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}