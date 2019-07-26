package pasa.cbentley.jpasc.swing.workers.table.key;
//package pasa.cbentley.pascalcoin.swing.workers.task;
//
//import java.util.Iterator;
//import java.util.List;
//
//import com.github.davidbolet.jpascalcoin.api.model.Account;
//import com.github.davidbolet.jpascalcoin.api.model.Operation;
//
//import pasa.cbentley.core.src4.logging.Dctx;
//import pasa.cbentley.pascalcoin.javacore.actions.IListListener;
//import pasa.cbentley.pascalcoin.javacore.actions.lookup.ListTask;
//import pasa.cbentley.pascalcoin.javacore.domain.java.PublicKeyJava;
//import pasa.cbentley.pascalcoin.swing.ctx.PascalSwingCtx;
//import pasa.cbentley.pascalcoin.swing.tablemodels.TableModelAccountsBase;
//import pasa.cbentley.pascalcoin.swing.tablemodels.TableModelOperation;
//import pasa.cbentley.pascalcoin.swing.tablemodels.TableModelPublicKeysJavaBase;
//import pasa.cbentley.pascalcoin.swing.workers.core.ListTaskWorker;
//import pasa.cbentley.swing.threads.IWorkerPanel;
//
//public abstract class WorkerTablePublicKeyJavaAbstract extends ListTaskWorker<TableModelPublicKeysJavaBase, PublicKeyJava> implements IListListener<PublicKeyJava> {
//
//   protected final TableModelPublicKeysJavaBase tableModel;
//
//   protected final PascalSwingCtx         psc;
//
//   public WorkerTablePublicKeyJavaAbstract(PascalSwingCtx psc, IWorkerPanel wp, TableModelPublicKeysJavaBase tableModel) {
//      super(psc.getSwingCtx(), wp);
//      this.psc = psc;
//      this.tableModel = tableModel;
//   }
//
//   /**
//    * 
//    */
//   protected abstract ListTask<PublicKeyJava> createTask();
//
//   protected TableModelPublicKeysJavaBase getModel() {
//      return tableModel;
//   }
//
//   protected void process(List<PublicKeyJava> chunks) {
//      tableModel.addRows(chunks);
//      wp.panelSwingWorkerProcessed(this, chunks.size());
//   }
//
//
//   public void newDataAvailable(List<PublicKeyJava> list) {
//      publicList(list);
//   }
//
//   //#mdebug
//   public void toString(Dctx dc) {
//      dc.root(this, "WorkerTableOperation");
//      super.toString(dc.sup());
//   }
//
//   public void toString1Line(Dctx dc) {
//      dc.root1Line(this, "WorkerTableOperation");
//      super.toString1Line(dc.sup1Line());
//   }
//   //#enddebug
//
//}