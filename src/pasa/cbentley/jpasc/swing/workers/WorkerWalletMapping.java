/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.davidbolet.jpascalcoin.api.client.PascalCoinClient;
import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

public class WorkerWalletMapping extends PanelSwingWorker<Map<String, String>, Void> {


   private PascalSwingCtx psc;

   public WorkerWalletMapping(PascalSwingCtx psc, IWorkerPanel wp) {
      super(psc.getSwingCtx(),wp);
      this.psc = psc;
   }

   protected Map<String, String> doInBackground() throws Exception {
      PascalCoinClient pclient = psc.getPascalClient();
      Map<String, String> ownerWallet = new HashMap<String, String>();
      List<PublicKey> list = pclient.getWalletPubKeys(0, 1000);
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
         PublicKey pk = (PublicKey) iterator.next();
         ownerWallet.put(pk.getEncPubKey(), pk.getName());
      }
      return ownerWallet;
   }
   
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

}