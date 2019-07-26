/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.snapshot;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.funding.FundingItem;
import pasa.cbentley.jpasc.swing.panels.funding.FundingTab;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * Displays on the top the current snapshot.
 * <br>
 * 
 * Manage the creation/deletion/loading of snapshots.
 * Creation of a snapshot reads all the accounts and store it in a file.
 * <br>
 * We need to pause the node when creating a snapshot.
 * <br>
 * When a snapshot is active, all queries are made to this snapshot
 * <br>
 * @author Charles Bentley
 *
 */
public class TabsSnapshotFunding extends FundingTab implements IMyTab {

   //implementation will use the same ID
   public static final String ID = "root_snapshot";
   /**
    * 
    */
   private static final long serialVersionUID = -8920741811784142010L;

   public TabsSnapshotFunding(PascalSwingCtx psc) {
      super(psc, TabsSnapshotFunding.ID);

      FundingItem fi = new FundingItem(psc, new Integer(560115),"funding.snapshot.desc");
      this.setFundingItem(fi);
   }

   public void disposeTab() {

   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void initTab() {
      super.initTab();
   }

}