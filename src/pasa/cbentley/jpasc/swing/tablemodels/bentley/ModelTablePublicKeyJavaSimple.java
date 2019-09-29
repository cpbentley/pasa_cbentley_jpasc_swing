/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

/**
 * Rendere
 * @author Charles Bentley
 *
 */
public class ModelTablePublicKeyJavaSimple extends ModelTablePublicKeyJavaAbstract {

   public static final int   INDEX_BASE58     = 1;

   public static final int   INDEX_NAME       = 0;

   public static final int   NUM_COLUMNS      = 2;

   /**
    * 
    */
   private static final long serialVersionUID = -3430965429764296292L;

   public ModelTablePublicKeyJavaSimple(PascalSwingCtx psc) {
      super(psc, NUM_COLUMNS);

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.key.coltitle.");
      columnModel.setKeyPrefixTip("table.key.coltip.");

      columnModel.setString(INDEX_NAME, "name");
      columnModel.setString(INDEX_BASE58, "base58value");

   }

   protected void computeStats(PublicKeyJava a, int row) {

   }

   public int getColumnIndexKeyName() {
      return INDEX_NAME;
   }

   public int getColumnIndexNumAccount() {
      return -1;
   }

   public int getNumKeys() {
      return 0;
   }

   public int getNumAccounts() {
      return 0;
   }

   public Object getValueAt(int row, int col) {
      PublicKeyJava a = getRow(row);
      if (a == null) {
         return null;
      }
      switch (col) {
         case INDEX_NAME:
            return a.getName();
         case INDEX_BASE58:
            return a.getBase58PubKey();
         default:
            break;
      }
      return null;
   }
}