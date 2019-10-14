package pasa.cbentley.jpasc.swing.workers.cache;

import pasa.cbentley.core.src4.structs.synch.MutexSignal;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.pcore.listlisteners.IListListener;
import pasa.cbentley.jpasc.pcore.task.ListTaskPageCustomRun;

/**
 * {@link ListTaskPageCustomRun} because we are not a regular listtask while still keeping the type signature
 */
public class ListTaskFromWorkerCache extends ListTaskPageCustomRun<PublicKeyJava> {

   private MutexSignal mutex;

   public ListTaskFromWorkerCache(PCoreCtx pc, IListListener<PublicKeyJava> listener, MutexSignal mutex) {
      super(pc, listener);
      this.mutex = mutex;
   }

   /**
    * We are not a listtask. we return early or wait for worker to finish
    */
   public void runAbstractCustom() {
      try {
         mutex.acquire();
      } catch (InterruptedException e) {
         //if task is interrupted?
         e.printStackTrace();
      }
   }

}
