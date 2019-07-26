package pasa.cbentley.jpasc.swing.workers.table.key;
//package pasa.cbentley.pascalcoin.swing.workers.task;
//
//import pasa.cbentley.core.src4.logging.Dctx;
//import pasa.cbentley.pascalcoin.javacore.actions.lookup.ListTask;
//import pasa.cbentley.pascalcoin.javacore.actions.lookup.ListTaskPublicKeyJavaWalletCanUse;
//import pasa.cbentley.pascalcoin.javacore.domain.java.PublicKeyJava;
//import pasa.cbentley.pascalcoin.swing.ctx.PascalSwingCtx;
//import pasa.cbentley.pascalcoin.swing.tablemodels.TableModelPublicKeysJavaBase;
//import pasa.cbentley.swing.threads.IWorkerPanel;
//
//public class WorkerTablePublicKeyJavaCanUse extends WorkerTablePublicKeyJavaAbstract {
//
//   public WorkerTablePublicKeyJavaCanUse(PascalSwingCtx psc, TableModelPublicKeysJavaBase tableModel, IWorkerPanel wp) {
//      super(psc, wp, tableModel);
//   }
//
//   protected ListTask<PublicKeyJava> createTask() {
//      //use task of the core framework
//      ListTaskPublicKeyJavaWalletCanUse task = new ListTaskPublicKeyJavaWalletCanUse(psc.getPCtx(), this);
//      task.setComputeNumAccounts(true);
//      return task;
//   }
//
//   //#mdebug
//   public void toString(Dctx dc) {
//      dc.root(this, "WorkerTablePublicKeyJavaCanUse");
//      super.toString(dc.sup());
//   }
//
//   public void toString1Line(Dctx dc) {
//      dc.root1Line(this, "WorkerTablePublicKeyJavaCanUse");
//      super.toString1Line(dc.sup1Line());
//   }
//   //#enddebug
//
//}