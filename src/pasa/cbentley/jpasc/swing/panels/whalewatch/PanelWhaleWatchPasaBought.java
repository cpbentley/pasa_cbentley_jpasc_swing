/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.whalewatch;

import java.awt.event.ActionListener;

import javax.swing.Icon;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * 
 * @author Charles Bentley
 *
 */
public class PanelWhaleWatchPasaBought extends PanelWhaleWatchAbstract implements IMyTab, ActionListener {

   private Icon                   iconWhalePasa;

   public PanelWhaleWatchPasaBought(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "whale_pasa_buy", root);
      iconWhalePasa = psc.createImageIcon("/icons/pasc_whale_pinkblue_16.png", "");
   }

   public void disposeTab() {
      super.disposeTab();
   }

   public Icon getTabIcon(int size) {
      return iconWhalePasa;
   }

   public String getTabTip() {
      return "Monitor buys of PASA";
   }

   public String getTabTitle() {
      return "Pasa Sales Watch";
   }
   
   protected WorkerTableOperationAbstract createWorker() {
      // TODO Auto-generated method stub
      return null;
   }
   
   public void initTab() {
      super.initTab();
   }
}