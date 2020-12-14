/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels;

import pasa.cbentley.jpasc.pcore.rpc.model.Connection;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelTableBAbstractArray;

/**
 * Uses old data model. no urgent need to change
 * 
 * @author Charles Bentley
 *
 */
public class ModelTableConnections extends ModelTableBAbstractArray<Connection> {

   /**
    * 
    */
   private static final long serialVersionUID = 2397736089329464958L;

   private static final int  NUM_COLUMNS      = 8;

   private static final int  INDEX_IP         = 0;

   private static final int  INDEX_PORT       = 1;

   private static final int  INDEX_DATA_SENT  = 2;

   private static final int  INDEX_DATA_RCVD  = 3;

   private static final int  INDEX_VERSION    = 4;

   private static final int  INDEX_ACTIVE     = 5;

   private static final int  INDEX_DURATION   = 6;

   private static final int  INDEX_TIME_DIFF  = 7;

   private PascalSwingCtx    psc;

   public ModelTableConnections(PascalSwingCtx psc) {
      super(psc.getSwingCtx());
      this.psc = psc;

      this.classes = new Class[NUM_COLUMNS];
      this.colNames = new String[NUM_COLUMNS];

      classes[INDEX_IP] = String.class;
      colNames[INDEX_IP] = "IP";

      classes[INDEX_PORT] = Integer.class;
      colNames[INDEX_PORT] = "Port";

      classes[INDEX_DATA_SENT] = String.class;
      colNames[INDEX_DATA_SENT] = "Data Sent";

      classes[INDEX_DATA_RCVD] = String.class;
      colNames[INDEX_DATA_RCVD] = "Data Recieved";

      classes[INDEX_VERSION] = String.class;
      colNames[INDEX_VERSION] = "Version";

      classes[INDEX_ACTIVE] = String.class;
      colNames[INDEX_ACTIVE] = "Active";

      classes[INDEX_DURATION] = String.class;
      colNames[INDEX_DURATION] = "Duration";

      classes[INDEX_TIME_DIFF] = String.class;
      colNames[INDEX_TIME_DIFF] = "Time Diff";

   }

   protected void computeStats(Connection a, int row) {
   }

   public Object getValueAt(int row, int col) {
      Connection a = getRow(row);
      if (a == null) {
         return null;
      }
      switch (col) {
         case INDEX_IP:
            return a.getIP();
         case INDEX_PORT:
            return a.getPort();
         case INDEX_DATA_RCVD:
            return psc.getPrettyBytes(a.getBytesReceived());
         case INDEX_DATA_SENT:
            return psc.getPrettyBytes(a.getBytesSent());
         case INDEX_VERSION:
            return a.getAppVersion();
         case INDEX_ACTIVE:
            return a.getIsServer();
         case INDEX_DURATION:
            return a.getConnectedDurationSec();
         case INDEX_TIME_DIFF:
            return a.getTimeDiff();
         default:
            break;
      }
      return null;
   }

}