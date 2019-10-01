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

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Operation;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationFullData;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationByAccounts;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.model.ModelTableBAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * List all the operations found in a block range
 * @author Charles Bentley
 *
 */
public class TablePanelOperationByAccount extends TablePanelOperationAbstract implements IMyTab, IWorkerPanel, ActionListener {
   /**
    * 
    */
   private static final long serialVersionUID = -5249987081728161288L;

   private JButton           butNext;

   private JButton           butPrevious;

   private JButton           butRefresh;

   private boolean           embedded;

   private JLabel            labAccount;

   private Integer oldAccount;

   /**
    * Never null once initialized
    */
   private JTextField        textAccount;

   public TablePanelOperationByAccount(PascalSwingCtx psc, IRootTabPane root, boolean embedded) {
      super(psc, "operations_account", root);
      this.embedded = embedded;
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butRefresh) {
         cmdTableRefresh();
      } else if (e.getSource() == butNext) {
         //from current block.. find next block with operations and list them
         Integer account = psc.getIntegerFromTextField(textAccount);
         textAccount.setText(String.valueOf(account + 1));
         cmdTableRefresh();
      }
   }

   /**
    * Stops any running worker and set it to null
    */
   public void clear() {
      textAccount.setText("");
   }

   protected WorkerTableOperationAbstract createWorker() {
      Integer account = psc.getIntegerFromTextField(textAccount);
      if (account == null) {
         psc.getLog().consoleLogError("Account is invalid. Using previous account instead");
         Integer oldAccount = psc.getSwingCtx().getPrefs().getInt(IPrefsPascalSwing.UI_ACCOUNT_NUMBER, 1000);
         account = oldAccount;
      }
      psc.getSwingCtx().getPrefs().putInt(IPrefsPascalSwing.UI_ACCOUNT_NUMBER, account.intValue());
      return new WorkerTableOperationByAccounts(psc, getTableModel(), this, account);
   }

   public void disposeTab() {

   }

   public void initTab() {
      super.initTab();
      //even in embeded. this field is used a place
      textAccount = new JTextField(10);

      if (!embedded) {
         JPanel north1 = new JPanel();
         north1.setLayout(new FlowLayout(FlowLayout.LEADING));

         labAccount = new JLabel("Account");

         int block = psc.getSwingCtx().getPrefs().getInt(IPrefsPascalSwing.UI_ACCOUNT_NUMBER, 0);
         textAccount.setText(String.valueOf(block));

         butRefresh = new JButton("Refresh");
         butRefresh.addActionListener(this);

         butNext = new JButton("Next");
         butNext.addActionListener(this);

         butPrevious = new JButton("Previous");
         butPrevious.addActionListener(this);

         psc.setIntFilter(textAccount);

         north1.add(labAccount);
         north1.add(textAccount);
         north1.add(butRefresh);
         north1.add(butNext);
         north1.add(butPrevious);

         this.add(north1, BorderLayout.NORTH);
      }
   }

   /**
    * Sets the block parameter to the given block
    * @param ac
    */
   public void showAccount(Account ac) {
      textAccount.setText(ac.getAccount() + "");
      tabGainFocus();
   }

   public void tabGainFocus() {
      Integer account = psc.getIntegerFromTextField(textAccount);
      //
      if (account != oldAccount) {
         cmdTableRefresh();
      }
   }

   public void tabLostFocus() {
      oldAccount = psc.getIntegerFromTextField(textAccount);
   }

   protected ModelTableOperationAbstract createTableModel() {
      return new ModelTableOperationFullData(psc);
   }

}