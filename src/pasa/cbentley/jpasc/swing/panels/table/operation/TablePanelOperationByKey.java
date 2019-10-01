/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperKeyGlobal;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationFullData;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationAbstract;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationByKey;
import pasa.cbentley.jpasc.swing.workers.table.operation.WorkerTableOperationPending;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.IWorkerPanel;

/**
 * List all the operations found in a block range.
 * <br>
 * 
 * When block range is All, the whole history is checked, and those operations
 * are saved in a file.
 * 
 * @author Charles Bentley
 *
 */
public class TablePanelOperationByKey extends TablePanelOperationAbstract implements IMyTab, IWorkerPanel, ActionListener {
   /**
    * 
    */
   private static final long serialVersionUID = -5249987081728161288L;

   private JButton           butNext;

   private JButton           butPrevious;

   private JButton           butRefresh;

   private JButton           butSelectKey;

   private JLabel            labAccount;

   /**
    * This field is set to null if a block with 
    */
   private PublicKey         lastWorkedKey;

   /**
    * Never null once initialized
    */
   private JTextField        textKey;

   private int               type;

   private PanelHelperKeyGlobal helper;

   /**
    * Defaults with all options
    * @param psc
    * @param root
    */
   public TablePanelOperationByKey(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "operations_key", root);
   }

   public TablePanelOperationByKey(PascalSwingCtx psc, IRootTabPane root, String id, int type) {
      super(psc, id, root);
      this.type = type;
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butRefresh) {
         cmdTableRefresh();
      }
   }

   public void clear() {
      textKey.setText("");
      getBenTable().clearTableModel();
   }

   protected ModelTableOperationAbstract createTableModel() {
      return new ModelTableOperationFullData(psc);
   }

   protected WorkerTableOperationAbstract createWorker() {
      PublicKey pk = getSelectedKey();
      if (pk != null) {
         psc.getSwingCtx().getPrefs().put(IPrefsPascalSwing.UI_PK_STRING, pk.getEncPubKey());
      } else {
         psc.getLog().consoleLogError("BlocPublicKey is null. Listing Pending instead");
         return new WorkerTableOperationPending(psc, getTableModel(), this);
      }
      WorkerTableOperationByKey worker = new WorkerTableOperationByKey(psc, getTableModel(), this, pk);
      return worker;
   }

   public void disposeTab() {

   }

   public PublicKey getSelectedKey() {
      if(helper != null) {
         return helper.getSelectedKeyPublicKey();
      }
      return null;
   }

   /**
    * Add a key selector to north panel
    */
   protected void subInitPanelNorth(JPanel north) {
      super.subInitPanelNorth(north);
      helper = new PanelHelperKeyGlobal(psc, this, this);
      north.add(helper);
   }

   /**
    * Sets the block parameter to the given block
    * @param ac
    */
   public void showKey(PublicKey ac) {
      textKey.setText(ac.getEncPubKey());
      tabGainFocus();
   }


   public void tabLostFocus() {
      //store 
      lastWorkedKey = getSelectedKey();
   }

}