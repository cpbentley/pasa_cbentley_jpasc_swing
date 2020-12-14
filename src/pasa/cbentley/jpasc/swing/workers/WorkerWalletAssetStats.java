/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import java.util.Iterator;
import java.util.List;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.thread.AbstractBRunnable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.pcore.utils.AssetStatResult;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

/**
 * Worker computing wallet assets
 * <li>total coins
 * <li>total pasas
 * <li>total keys
 * @author Charles Bentley
 *
 */
public class WorkerWalletAssetStats extends AbstractBRunnable {



   private PascalSwingCtx           psc;

   /**
    * Volatile makes sure variable to be visible when it is read
    */
   private volatile AssetStatResult asr;

   private boolean isCanUse = true;
   
   public AssetStatResult getAssetStatResultImmutable() {
      return asr;
   }

   public WorkerWalletAssetStats(PascalSwingCtx psc) {
      super(psc.getUCtx());
      this.psc = psc;
   }

   
   public void runAbstract() {

      //#debug
      psc.toDLog().pFlow("", null, WorkerWalletAssetStats.class, "runAbstract", ITechLvl.LVL_05_FINE, true);

      //show interval of blocks
      AssetStatResult asr = new AssetStatResult(psc.getPCtx());
      asr.setCanUse(isCanUse);
      Integer start = null; //0 by default
      Integer end = null; //100
      //adapt listing to the speed of the data provider. measure the time needed to get 100 accounts
      List<PublicKey> list = psc.getPascalClient().getWalletPubKeys(start, end);
      if (list != null) {
         Iterator<PublicKey> it = list.iterator();
         while (it.hasNext()) {
            PublicKey pk = it.next();
            boolean isKeyValid = false;
            if(isCanUse) {
               isKeyValid = pk.getCanUse();
            } else {
               isKeyValid = !pk.getCanUse();
            }
            if (isKeyValid) {
               asr.incrementNumKeys();;
               int numAccounts = psc.getPascalClient().getWalletAccountsCount(pk.getEncPubKey(), null);
               asr.incrementNumPasas(numAccounts);
               Double numCoinsPk = psc.getPascalClient().getWalletCoins(pk.getEncPubKey(), null);
               //immutable remember!
               asr.addCoins(numCoinsPk);
               //#debug
               //psc.toDLog().pFlow("numCoinsPk="+numCoinsPk + " total="+asr.pasc.getDouble(), null, WorkerTableWalletAssetStats.class, "runAbstract", IDLog.LVL_05_FINE, true);

            }
         }
      }
      asr.setBlock(psc.getPascalClient().getBlockCount() - 1);
      //publish result
      this.asr = asr;
   }

   public boolean isCanUse() {
      return isCanUse;
   }

   public void setCanUse(boolean isCanUse) {
      this.isCanUse = isCanUse;
   }
}