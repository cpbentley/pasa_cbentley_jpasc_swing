/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.workers;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.pcore.interfaces.IAccessAccountDBolet;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * Search for an exact name 
 * @author Charles Bentley
 *
 */
public class WorkerSearchExactName extends PanelSwingWorker<Account, Boolean> {

   private IRootTabPane root;

   private String       name;

   public String getName() {
      return name;
   }

   private Account      account;

   public Account getAccount() {
      return account;
   }
   public String getNameForUser() {
      return this.getClass().getSimpleName();
   }

   public WorkerSearchExactName(SwingCtx sc, IWorkerPanel wp, IRootTabPane root, String name) {
      super(sc, wp);
      this.root = root;
      this.name = name;
   }

   protected Account doInBackground() throws Exception {
      IAccessAccountDBolet da = root.getAccessPascal().getAccessAccountDBolet();
      Account account = da.getAccountWithName(name);
      //#debug
      toDLog().pTest("Searched for "+ name + " found "+ account, null, WorkerSearchExactName.class, "doInBackground", ITechLvl.LVL_05_FINE, true);
      return account;
   }
   
   protected void processResult(Account k) {
      this.account = k;
   }

}
