/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelTableBAbstractWithColModel;

public abstract class ModelTablePublicKeyJavaAbstract extends ModelTableBAbstractWithColModel<PublicKeyJava> {

   /**
    * 
    */
   private static final long serialVersionUID = 3046232769379632229L;

   public ModelTablePublicKeyJavaAbstract(PascalSwingCtx psc, int numCols) {
      super(psc.getSwingCtx(), numCols);
   }

   /**
    * 
    * @return
    */
   public abstract int getColumnIndexNumAccount();

   /**
    * 
    * @return
    */
   public abstract int getColumnIndexKeyName();
}