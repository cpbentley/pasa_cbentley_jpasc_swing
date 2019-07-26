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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.davidbolet.jpascalcoin.api.model.Block;

import pasa.cbentley.jpasc.pcore.filter.predicates.BlockPredicate;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.ITechPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationFullData;
import pasa.cbentley.jpasc.swing.workers.WorkerBlockFinder;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationByBlock;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationByPredicate;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BButton;

/**
 * List all the operations found in a block range
 * @author Charles Bentley
 *
 */
public class TablePanelOperationByBlock extends TablePanelOperationAbstract implements IMyTab, IWorkerPanel, ActionListener {
   public static final String ID               = "operations_block";

   /**
    * 
    */
   private static final long  serialVersionUID = -5249987081728161288L;

   private PanelSwingWorker   blockFinder;

   private JButton            butClear;

   private JButton            butNext;

   private JButton            butPrevious;

   private JButton            butRefresh;

   private JLabel             labBlock;

   private JLabel             labTxMin;

   private JTextField         textBlock;

   private JTextField         textTxMin;

   public TablePanelOperationByBlock(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, ID, root);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butRefresh) {
         cmdTableRefresh();
      } else if (e.getSource() == butNext) {
         cmdBlockNext();
      } else if (e.getSource() == butPrevious) {
         cmdBlockPrevious();
      } else if (e.getSource() == butClear) {
         cmdClear();
      }
   }

   /**
    * Find the next block with a given minimum of txs
    */
   private void cmdBlockNext() {
      //from current block.. find next block with operations and list them
      Integer block = psc.getIntegerFromTextField(textBlock);
      if (block != null) {
         textBlock.setText("" + (block + 1));
         //find the next block with enough txs
         Integer minTx = psc.getIntegerFromTextField(textTxMin);
         //first job is to find the next block..
         BlockPredicate blockPredicate = new BlockPredicate(psc.getPCtx());
         blockPredicate.setUp(true);
         blockPredicate.setStartingBlock(block);
         blockPredicate.setMinimumOperations(minTx);
         //TODO wrong.. wont'work
         blockFinder = new WorkerBlockFinder(psc, blockPredicate, this);
         cmdTableRefresh();
         getBenTable().clearTableModel();
         WorkerTableOperationByPredicate worker = new WorkerTableOperationByPredicate(psc, getTableModel(), this, blockPredicate);
         worker.execute();
      }
   }

   private void cmdBlockPrevious() {
      Integer block = psc.getIntegerFromTextField(textBlock);
      if (block != null) {
         getBenTable().clearTableModel();
         BlockPredicate blockPredicate = new BlockPredicate(psc.getPCtx());
         Integer minTx = psc.getIntegerFromTextField(textTxMin);
         blockPredicate.setUp(false);
         blockPredicate.setStartingBlock(block);
         blockPredicate.setMinimumOperations(minTx);
         WorkerTableOperationByPredicate worker = new WorkerTableOperationByPredicate(psc, getTableModel(), this, blockPredicate);
         worker.execute();
      }
   }

   protected ModelTableOperationAbstract createTableModel() {
      return new ModelTableOperationFullData(psc);
   }

   protected WorkerTableOperationByBlock createWorker() {
      Integer block = psc.getIntegerFromTextField(textBlock);
      if (block == null) {
         //this should never happens.. check must be made on top
         psc.getLog().consoleLogError("Block field '" + textBlock.getText() + "'. It is not a valid block number. Going with a default");
         int blockInt = psc.getSwingCtx().getPrefs().getInt(ITechPrefsPascalSwing.UI_BLOCK_NUMBER, 0);
         block = new Integer(blockInt);
      }
      psc.getSwingCtx().getPrefs().putInt(ITechPrefsPascalSwing.UI_BLOCK_NUMBER, block.intValue());
      WorkerTableOperationByBlock worker = new WorkerTableOperationByBlock(psc, getTableModel(), block, this);
      return worker;
   }

   public void disposeTab() {

   }

   public void initTab() {
      super.initTab();

      JPanel north1 = new JPanel();
      north1.setLayout(new FlowLayout(FlowLayout.LEADING));

      labBlock = new JLabel("Block");
      textBlock = new JTextField(10);

      int block = psc.getSwingCtx().getPrefs().getInt(ITechPrefsPascalSwing.UI_BLOCK_NUMBER, 0);
      textBlock.setText(String.valueOf(block));

      butRefresh = new BButton(sc, this, "but.refresh");
      butClear = new BButton(sc, this, "but.clear");

      butNext = new BButton(sc, this, "but.block.next.1");
      butPrevious = new BButton(sc, this, "but.block.previous.1");

      labTxMin = new JLabel("Min# of Ops:");
      textTxMin = new JTextField(6);

      psc.setIntFilter(textTxMin);
      psc.setIntFilter(textBlock);

      north1.add(labBlock);
      north1.add(textBlock);
      north1.add(butRefresh);
      north1.add(butClear);
      north1.add(butPrevious);
      north1.add(butNext);
      north1.add(labTxMin);
      north1.add(textTxMin);

      this.add(north1, BorderLayout.NORTH);
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      if (worker == blockFinder) {
         //if its the blockFinder
      } else {
         super.panelSwingWorkerDone(worker);
      }
   }

   /**
    * Sets the block parameter to the given block
    * @param ac
    */
   public void showBlock(Block ac) {
      initCheck();
      textBlock.setText(ac.getBlock() + "");
      cmdTableRefresh();
   }

}