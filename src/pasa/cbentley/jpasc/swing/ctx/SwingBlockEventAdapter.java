/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.ctx;

import java.util.ArrayList;
import java.util.List;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.interfaces.IBlockListener;

/**
 * Runs in the GUI thread.
 * @author Charles Bentley
 *
 */
public class SwingBlockEventAdapter implements IBlockListener, IEventsPascalSwing {

   private List<IBlockListener> listListeners = new ArrayList<>();

   private PascalSwingCtx       psc;

   public SwingBlockEventAdapter(PascalSwingCtx psc) {
      this.psc = psc;
   }

   /**
    * Safe to call from AWT Thread.
    * @param b
    */
   public void addBlockListener(IBlockListener b) {
      listListeners.add(b);
   }

   public void pingDisconnect() {
      psc.getSwingCtx().execute(new Runnable() {
         public void run() {
            //first send to 1st class listeners
            for (IBlockListener b : listListeners) {
               b.pingDisconnect();
            }
            //then send to the block
            BusEvent be = psc.getEventBusPascal().createEvent(PID_5_CONNECTIONS, EID_5_CONNECTIONS_2_DISCONNECTED, null);
            psc.getEventBusPascal().putOnBus(be);
         }
      });
   }

   public void pingError() {
      psc.getSwingCtx().execute(new Runnable() {
         public void run() {

            for (IBlockListener b : listListeners) {
               b.pingError();
            }

         }
      });
   }

   /**
    * Callable from any thread
    */
   public void pingNewBlock(Integer newBlock, long millis) {
      psc.getSwingCtx().execute(new Runnable() {
         public void run() {
            //first send to 1st class listeners
            for (IBlockListener b : listListeners) {
               b.pingNewBlock(newBlock, millis);
            }
            //then send to the block
            BusEvent be = psc.getEventBusPascal().createEvent(PID_2_BLOCK, EID_2_BLOCK_1_NEW_BLOCK, null);
            psc.getEventBusPascal().putOnBus(be);
         }
      });
   }

   public void pingNewPendingCount(Integer count, Integer oldCount) {
      psc.getSwingCtx().execute(new Runnable() {
         public void run() {
            for (IBlockListener b : listListeners) {
               b.pingNewPendingCount(count, oldCount);
            }
         }
      });
   }

   public void pingNoBlock(long millis) {
      psc.getSwingCtx().execute(new Runnable() {
         public void run() {
            for (IBlockListener b : listListeners) {
               b.pingNoBlock(millis);
            }
         }
      });
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "SwingBlockEventAdapter");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SwingBlockEventAdapter");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUC();
   }
   //#enddebug

}
