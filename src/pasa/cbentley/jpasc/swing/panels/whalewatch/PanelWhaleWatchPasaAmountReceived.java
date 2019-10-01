/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.whalewatch;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pasa.cbentley.jpasc.pcore.task.list.dbolet.block.ListTaskBlockAbstract;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.workers.WorkerStatOperations;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationsPastAmount;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * List pasas that send value
 * 
 * @author Charles Bentley
 *
 */
public class PanelWhaleWatchPasaAmountReceived extends PanelWhaleWatchAbstract implements IMyTab {

   private JLabel     labPriceWhale;

   private JTextField textPriceWhale;

   private JButton    butIcon;

   public PanelWhaleWatchPasaAmountReceived(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "whale_account", root);
   }

   /**
    * Used be specific account watchers
    * @param psc
    * @param id
    * @param root
    */
   public PanelWhaleWatchPasaAmountReceived(PascalSwingCtx psc, String id, IRootTabPane root) {
      super(psc, id, root);
   }

   public void disposeTab() {
      super.disposeTab();
   }

   /**
    * NULL if ""
    */
   public Double getMinAmount() {
      String val = textPriceWhale.getText();
      if (val == null || val.equals("")) {
         return null;
      }
      Double num = Double.valueOf(val);
      //save to preferences
      getSwingCtx().getPrefs().putDouble(IPrefsPascalSwing.UI_WHALE_PRICE, num.doubleValue());
      return num;
   }

   public void initTab() {
      super.initTab();

      JPanel north = new JPanel();
      butIcon = new JButton("Icon", this.getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM));
      north.add(butIcon, 0);

      labPriceWhale = new JLabel("Price Minimum");
      textPriceWhale = new JTextField(10);

      double d = getSwingCtx().getPrefs().getDouble(IPrefsPascalSwing.UI_WHALE_PRICE, 100.0d);
      textPriceWhale.setText(String.valueOf(d));

      north.add(labPriceWhale);
      north.add(textPriceWhale);

      super.addNorth(north);
   }

   public void tabLostFocus() {
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
      super.panelSwingWorkerProcessed(worker, entryCount);

      WorkerStatOperations wt = (WorkerStatOperations) worker.getWorkerStat();

   }

   protected WorkerTableOperationAbstract createWorker() {
      double min = getMinAmount();
      ListTaskBlockAbstract listBlocks = getBlockTaskList();
      WorkerTableOperationsPastAmount worker = new WorkerTableOperationsPastAmount(psc, getTableModel(), this, min, listBlocks);
      return worker;
   }
}