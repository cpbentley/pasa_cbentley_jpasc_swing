/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.pcore.filter.IFilterPublicKey;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * Encapsulates with different 
 * @author Charles Bentley
 *
 */
public class WorkerKeyFilter implements IStringable {

   protected final PascalSwingCtx psc;

   
   public WorkerKeyFilter(PascalSwingCtx psc) {
      this.psc = psc;
   }

   
   public void addFilter(IFilterPublicKey filter) {
      
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "WorkerKeyFilter");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerKeyFilter");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }

   //#enddebug
   

}
