/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.tablemodels.bentley;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import pasa.cbentley.jpasc.pcore.rpc.model.Operation;
import pasa.cbentley.jpasc.pcore.rpc.model.OperationSubType;
import pasa.cbentley.jpasc.pcore.rpc.model.OperationType;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.model.ModelColumnBAbstract;

public class ModelTableOperationFullData extends ModelTableOperationAbstract {

   /**
    * 
    */
   private static final long serialVersionUID       = -26914458447579778L;

   public static final int   NUM_COLUMNS            = 17;

   public static final int   INDEX_TIME             = 0;

   public static final int   INDEX_OP               = 2;

   public static final int   INDEX_ACCOUNT          = 3;

   public static final int   INDEX_CKS              = 4;

   public static final int   INDEX_TYPE             = 5;

   public static final int   INDEX_SUBTYPE          = 6;

   public static final int   INDEX_OP_DESCR         = 7;

   public static final int   INDEX_AMOUNT           = 8;

   public static final int   INDEX_FEE              = 9;

   /**
    * shows the balance of the account making the txs
    */
   public static final int   INDEX_BALANCE          = 10;

   public static final int   INDEX_PAYLOAD          = 11;

   public static final int   INDEX_ACCOUNT_SENDER   = 12;

   public static final int   INDEX_ACCOUNT_RECEIVER = 15;

   public static final int   INDEX_PUB_KEY          = 17;

   public static final int   INDEX_BLOCK            = 1;

   public static final int   INDEX_MATURATION       = 16;

   public static final int   INDEX_ACCOUNT_SIGNER   = 14;

   public static final int   INDEX_OP_ACCOUNT       = 13;

   public ModelTableOperationFullData(PascalSwingCtx psc) {
      super(psc, NUM_COLUMNS);

      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.operation.coltitle.");
      columnModel.setKeyPrefixTip("table.operation.coltip.");

      columnModel.setDate(INDEX_TIME, "time");
      columnModel.setInteger(INDEX_BLOCK, "block");
      columnModel.setInteger(INDEX_OP, "op");
      columnModel.setInteger(INDEX_ACCOUNT, "account");
      columnModel.setInteger(INDEX_CKS, "cks");

      columnModel.setClass(INDEX_TYPE, "type", OperationType.class);
      columnModel.setString(INDEX_SUBTYPE, "subtype");
      columnModel.setString(INDEX_OP_DESCR, "descr");
      columnModel.setDouble(INDEX_AMOUNT, "amount");
      columnModel.setDouble(INDEX_FEE, "fee");
      columnModel.setDouble(INDEX_BALANCE, "balance");

      columnModel.setString(INDEX_PAYLOAD, "payload");
      columnModel.setInteger(INDEX_ACCOUNT_SIGNER, "signer");
      columnModel.setInteger(INDEX_ACCOUNT_SENDER, "sender");
      columnModel.setInteger(INDEX_OP_ACCOUNT, "numop");
      columnModel.setInteger(INDEX_ACCOUNT_RECEIVER, "receiver");
      columnModel.setInteger(INDEX_MATURATION, "maturation");

   }

   public Object getValueAt(int row, int col) {
      Operation op = getRow(row);
      if (op == null) {
         return null;
      }
      switch (col) {
         case INDEX_TIME:
            long time = op.getTime();
            if (time == 0) {
               return new Date(System.currentTimeMillis());
            } else {
               return psc.getDateUnit(time); //unix time
            }
         case INDEX_BLOCK:
            return op.getBlock();
         case INDEX_OP:
            return op.getOperationBlock();
         case INDEX_ACCOUNT:
            return op.getAccount();
         case INDEX_CKS:
            int intValue = op.getAccount();
            return psc.getPCtx().calculateChecksum(intValue);
         case INDEX_TYPE:
            OperationType ot = op.getType();
            return ot;
         //return PascalUtils.getOperationTypeUserString(ot);
         case INDEX_SUBTYPE:
            OperationSubType sub = op.getSubType();
            return sub;
         case INDEX_OP_DESCR:
            return op.getTypeDescriptor();
         case INDEX_AMOUNT:
            if (op.getAmount() != null) {
               return 0d - op.getAmount();
            }
            return 0d;
         case INDEX_FEE:
            //option as positive instead of negative
            if (op.getFee() != null) {
               return 0d - op.getFee();
            }
            return 0d;
         case INDEX_BALANCE:
            return op.getBalance();
         case INDEX_PAYLOAD:
            String hexString = op.getPayLoad();
            if (hexString == null) {
               return null;
            }
            byte[] bytes = psc.getPCtx().getPU().hexStringToByteArray(hexString);
            try {
               String str = new String(bytes, "UTF-8");
               if (str.contains("") || str.contains("�")) {
                  return "Encrypted Payload " + bytes.length + " bytes";
               }
               //#debug
               //toDLog().pTest("payload=" + str, null, TableModelOperation.class, "getValueAt", IDLog.LVL_03_FINEST, true);
               return str;
            } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
               return hexString;
            }
         case INDEX_ACCOUNT_RECEIVER:
            return op.getDestAccount();
         case INDEX_ACCOUNT_SENDER:
            return op.getSenderAccount();
         case INDEX_ACCOUNT_SIGNER:
            return op.getSignerAccount();
         case INDEX_OP_ACCOUNT:
            return op.getNOperation();
         case INDEX_MATURATION:
            return op.getMaturation();
         case INDEX_PUB_KEY:
            return op.getEncPubKey();
         default:
            return "UnknownCol " + col;
      }
   }

   public int getColumnIndexAccount() {
      return INDEX_ACCOUNT;
   }

   public int getColumnIndexAccountReceiver() {
      return INDEX_ACCOUNT_RECEIVER;
   }

   public int getColumnIndexAccountSender() {
      return INDEX_ACCOUNT_SENDER;
   }

   public int getColumnIndexAccountSigner() {
      return INDEX_ACCOUNT_SIGNER;
   }

   public int getColumnIndexBlock() {
      return INDEX_BLOCK;
   }

   public int getColumnIndexOpCount() {
      return INDEX_OP;
   }

   public int getColumnIndexTime() {
      return INDEX_TIME;
   }

   public int getColumnIndexType() {
      return INDEX_TYPE;
   }

   public int getColumnIndexTypeSub() {
      return INDEX_SUBTYPE;
   }

}