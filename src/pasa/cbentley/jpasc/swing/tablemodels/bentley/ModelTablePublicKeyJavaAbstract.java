/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelTableBAbstractWithColModel;

public abstract class ModelTablePublicKeyJavaAbstract extends ModelTableBAbstractWithColModel<PublicKeyJava> {

   /**
    * 
    */
   private static final long      serialVersionUID = 3046232769379632229L;

   protected final PascalSwingCtx psc;

   public ModelTablePublicKeyJavaAbstract(PascalSwingCtx psc) {
      super(psc.getSwingCtx());
      this.psc = psc;
   }

   public ModelTablePublicKeyJavaAbstract(PascalSwingCtx psc, int numCols) {
      super(psc.getSwingCtx(), numCols);
      this.psc = psc;
   }

   /**
    * The index of the column for key name
    * @return -1 if no such column for this model
    */
   public abstract int getColumnIndexKeyName();

   /**
    * The index of the column for number of accounts
    * @return -1 if no such column for this model
    */
   public abstract int getColumnIndexNumAccount();

   public abstract int getNumAccounts();

   public abstract int getNumKeys();

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "ModelTablePublicKeyJavaAbstract");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ModelTablePublicKeyJavaAbstract");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}