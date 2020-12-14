/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.whalewatch;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTextArea;

import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * Supersede accounts with Key.
 * 
 * Given a key, list active accounts with operations
 * 
 * @author Charles Bentley
 *
 */
public class PanelWhaleWatchKey extends PanelWhaleWatchAbstract implements IMyTab, ActionListener {

   private JButton   butChooseKey;

   private Icon      iconWhaleKeys;

   private JTextArea jtextArea;

   private PublicKey pk;

   public PanelWhaleWatchKey( PascalSwingCtx psc, IRootTabPane root) {
      super( psc, "whale_key", root);
      iconWhaleKeys = psc.createImageIcon("/icons/pasc_whale_pinkblue_16.png", "");

   }

   public void disposeTab() {
      super.disposeTab();
   }

   public Icon getTabIcon(int size) {
      return iconWhaleKeys;
   }

   public String getTabTip() {
      return "Lists pasas of public keys that made any operations during the last X Blocks";
   }

   public String getTabTitle() {
      return "Public Key Watch";
   }

   public void initTab() {
      super.initTab();
      //public key chooser. popup 
      jtextArea = new JTextArea(3, 100);
      addNorth(jtextArea);
      butChooseKey = new JButton("Choose Key");
      addNorth(butChooseKey);
   }

   public void setPublicKey(PublicKey pk) {
      this.pk = pk;
   }

   protected WorkerTableOperationAbstract createWorker() {
      // TODO Auto-generated method stub
      return null;
   }


}