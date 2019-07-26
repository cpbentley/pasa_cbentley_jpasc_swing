/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers.combo;

import java.util.List;

import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.jpasc.pcore.task.ListTaskPage;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.abstrakt.WorkerListTaskPage;
import pasa.cbentley.swing.model.ModelComboSortedHashMapAbstract;
import pasa.cbentley.swing.model.ModelTableBAbstractArray;
import pasa.cbentley.swing.threads.IWorkerPanel;

public abstract class WorkerComboAbstract<T extends ModelComboSortedHashMapAbstract<INameable<V>,V>,V> extends WorkerListTaskPage<T, V> {

   private final T comboModel;

   protected final PascalSwingCtx                              psc;

   public WorkerComboAbstract(PascalSwingCtx psc, IWorkerPanel wp, T comboModel) {
      super(psc.getSwingCtx(), wp);
      this.psc = psc;
      this.comboModel = comboModel;
   }

   protected abstract ListTaskPage<V> createTaskPage();

   /**
    * Returns the {@link ModelTableBAbstractArray}
    */
   protected T getModel() {
      return comboModel;
   }

   protected abstract INameable<V> getNameable(V v);
   
   protected void process(List<V> chunks) {
      //additional step to wrap V in a INameable
      for (V v : chunks) {
         INameable<V> namer = getNameable(v);
         comboModel.addNamer(namer);
      }
      wp.panelSwingWorkerProcessed(this, chunks.size());
   }

}
