/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.ctx;

import pasa.cbentley.jpasc.pcore.ctx.IEventsPCore;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IEventsPascalSwing extends IEventsPCore {

   public static final int EID_2_BLOCK_0_ANY                        = 0;

   public static final int EID_2_BLOCK_1_NEW_BLOCK                  = 1;

   public static final int EID_2_ZZ_NUM                             = 2;

   public static final int EID_3_USER_OPERATION_0_ANY               = 0;

   public static final int EID_3_USER_OPERATION_1_BEFORE            = 1;

   public static final int EID_3_USER_OPERATION_2_AFTER             = 2;

   public static final int EID_3_ZZ_NUM                             = 4;

   public static final int EID_4_WALLET_LOCK_0_ANY                  = 0;

   public static final int EID_4_WALLET_LOCK_1_LOCKED               = 1;

   public static final int EID_4_WALLET_LOCK_2_UNLOCKED             = 2;

   public static final int EID_4_ZZ_NUM                             = 3;

   public static final int EID_5_CONNECTIONS_0_ANY                  = 0;

   public static final int EID_5_CONNECTIONS_1_CONNECTED            = 1;

   /**
    * Event sent after {@link RPCConnection} has closed
    */
   public static final int EID_5_CONNECTIONS_2_DISCONNECTED         = 2;

   /**
    * Event sent just before the {@link RPCConnection} will close
    */
   public static final int EID_5_CONNECTIONS_3_DISCONNECTED_WILL_BE = 3;

   public static final int EID_5_CONNECTIONS_4_CONNECT_WILL         = 4;

   public static final int EID_5_ZZ_NUM                             = 5;

   public static final int EID_6_KEY_LOCAL_OPERATION_0_ANY          = 0;

   public static final int EID_6_KEY_LOCAL_OPERATION_1_CREATE       = 1;

   public static final int EID_6_ZZ_NUM                             = 2;

   public static final int EID_0_ANY                                = 0;

   public static final int EID_7_PRIVACY_0_ANY                      = 0;

   /**
    * When user decided to switch from private/public
    */
   public static final int EID_7_PRIVACY_1_CTX                      = 1;

   public static final int EID_7_ZZ_NUM                             = 2;

   /**
    * Event class about blocks
    */
   public static final int PID_2_BLOCK                              = 2;

   /**
    * When a user blockchain operation is made, canceled
    */
   public static final int PID_3_USER_OPERATION                     = 3;

   public static final int PID_4_WALLET_LOCK                        = 4;

   public static final int PID_5_CONNECTIONS                        = 5;

   /**
    * 
    */
   public static final int PID_6_KEY_LOCAL_OPERATION                = 6;

   public static final int PID_7_PRIVACY_CHANGES                    = 7;

   /**
    * number of producers
    */
   public static final int PID_ZZ_NUM                               = 8;

}
