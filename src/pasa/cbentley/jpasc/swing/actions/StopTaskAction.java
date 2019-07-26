/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import pasa.cbentley.swing.actions.BAbstractAction;
import pasa.cbentley.swing.ctx.SwingCtx;

public class StopTaskAction extends BAbstractAction {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private IStoppable        stoppable;

   public StopTaskAction(SwingCtx sc, IStoppable stoppable) {
      super(sc);
      this.stoppable = stoppable;
   }

   public void guiUpdate() {
      this.putValue(Action.NAME, sc.getResString("action.stop.name")); //update with res bundle
   }

   public void actionPerformed(ActionEvent e) {
      stoppable.cmdStop();
   }

}
