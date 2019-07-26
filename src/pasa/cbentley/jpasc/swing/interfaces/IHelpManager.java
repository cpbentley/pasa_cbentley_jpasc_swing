/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.interfaces;

import java.util.List;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.logging.IStringable;

public interface IHelpManager extends IStringable {

   /**
    * 
    * @param string
    * @param ctx
    * @return TODO
    */
   public boolean showHelpFor(String string, ICtx ctx);

   
   /**
    * List of {@link IWizardNoob} that this help manager provides.
    * Cannot be null. Empty if none.
    * @return
    */
   public List<IWizardNoob> getWizards();
   
}
