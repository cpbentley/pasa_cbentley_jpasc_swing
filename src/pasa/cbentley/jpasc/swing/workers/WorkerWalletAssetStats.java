/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import java.util.Iterator;
import java.util.List;

import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.thread.AbstractBRunnable;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
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

   public class AssetStatResult {
      public PascalCoinValue pasc;

      public int             pasa;

      public int             pks = 0;
   }

   private PascalSwingCtx  psc;

   /**
    * Volatile makes sure variable to be visible when it is read
    */
   private volatile AssetStatResult asr;

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
      AssetStatResult asr = new AssetStatResult();
      PCoreCtx pc = psc.getPCtx();
      asr.pasc = pc.getZero();
      Integer start = null; //0 by default
      Integer end = null; //100
      //adapt listing to the speed of the data provider. measure the time needed to get 100 accounts
      List<PublicKey> list = psc.getPascalClient().getWalletPubKeys(start, end);
      if (list != null) {
         Iterator<PublicKey> it = list.iterator();
         while (it.hasNext()) {
            PublicKey pk = it.next();
            if (pk.getCanUse()) {
               asr.pks++;
               int numAccounts = psc.getPascalClient().getWalletAccountsCount(pk.getEncPubKey(), null);
               asr.pasa += numAccounts;
               Double numCoinsPk = psc.getPascalClient().getWalletCoins(pk.getEncPubKey(), null);
               //immutable remember!
               asr.pasc = asr.pasc.add(pc.create(numCoinsPk));
               //#debug
               //psc.toDLog().pFlow("numCoinsPk="+numCoinsPk + " total="+asr.pasc.getDouble(), null, WorkerTableWalletAssetStats.class, "runAbstract", IDLog.LVL_05_FINE, true);
               
            }
         }
      }
      //publish result
      this.asr = asr;
   }
}