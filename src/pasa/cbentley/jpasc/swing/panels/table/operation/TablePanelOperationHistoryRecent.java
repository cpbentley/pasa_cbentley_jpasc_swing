/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.operation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationFullData;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * Displays the recent history. Everytime a block is mined,
 * Pinger sends NewBlock and this block's operations are appended in
 * this table at the top
 * <br>
 * Table can be cleared. Useful in a session to keep track of incoming txs
 * @author Charles Bentley
 *
 */
public class TablePanelOperationHistoryRecent extends TablePanelOperationAbstract implements IMyTab, ActionListener {
   /**
    * 
    */
   private static final long serialVersionUID = -2535271381632584761L;

   private JButton           butClear;

   private Icon              iconPendingOp;

   private JButton           labTitle;

   public TablePanelOperationHistoryRecent(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "operations_history", root);
      iconPendingOp = psc.createImageIcon("/icons/ops/pasc_p2_redblack_32.png", "");
      //sort on table
      
      //TODO register on blocks on tab init
      //once a block has been found.. get its list of operations
      //TODO centralized in a cache for each block so you don't have to query the wallet 
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butClear) {
         getBenTable().clearTableModel();
      }
   }

   protected ModelTableOperationAbstract createTableModel() {
      return new ModelTableOperationFullData(psc);
   }

   protected WorkerTableOperationAbstract createWorker() {
      //TODO
      return null;
   }

   public void disposeTab() {

   }

   public void initTab() {
      super.initTab();

      //we will use a special block renderer since blocks closer..

      JPanel north = new JPanel();
      north.setLayout(new FlowLayout(FlowLayout.LEADING));
      labTitle = new JButton("List of operations that were mined recently");

      butClear = new JButton("Clear History");
      butClear.addActionListener(this);

      north.add(labTitle);
      north.add(butClear);

      this.add(north, BorderLayout.NORTH);
   }

   /**
    * 
    * @param newBlock
    */
   public void updateNewBlock(Integer newBlock) {
      // TODO Auto-generated method stub

   }

}