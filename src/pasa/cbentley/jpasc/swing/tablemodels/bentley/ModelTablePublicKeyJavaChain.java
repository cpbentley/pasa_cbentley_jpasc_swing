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
public class ModelTablePublicKeyJavaChain extends ModelTablePublicKeyJavaAbstract {

   public static final int   INDEX_BASE58     = 3;

   public static final int   INDEX_NAME       = 0;

   public static final int   INDEX_NUM        = 1;

   public static final int   INDEX_TYPE       = 2;

   public static final int   NUM_COLUMNS      = 4;

   /**
    * 
    */
   private static final long serialVersionUID = -6342532470248234931L;

   private int               numAccounts;

   private int               numKeys;

   public ModelTablePublicKeyJavaChain(PascalSwingCtx psc) {
      super(psc, NUM_COLUMNS);

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.key.coltitle.");
      columnModel.setKeyPrefixTip("table.key.coltip.");

      columnModel.setString(INDEX_NAME, "name");
      columnModel.setString(INDEX_TYPE, "type");
      columnModel.setInteger(INDEX_NUM, "numaccount");
      columnModel.setString(INDEX_BASE58, "base58value");

   }
   public int getColumnIndexNumAccount() {
      return INDEX_NUM;
   }
   public int getColumnIndexKeyName() {
      return INDEX_NAME;
   }

   protected void computeStats(PublicKeyJava a, int row) {
      if (a != null) {
         numKeys++;
         numAccounts += a.getNumAccounts();
      }
   }

   public void clear() {
      super.clear();
      numAccounts = 0;
      numKeys = 0;
   }

   public int getNumAccounts() {
      return numAccounts;
   }

   public int getNumKeys() {
      return numKeys;
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
         case INDEX_NUM:
            return a.getNumAccounts();
         case INDEX_TYPE:
            return a.getKeyType();
         default:
            break;
      }
      return null;
   }
}