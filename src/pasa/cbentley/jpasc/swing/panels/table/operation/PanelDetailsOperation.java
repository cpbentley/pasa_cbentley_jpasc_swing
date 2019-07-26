/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.table.operation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Operation;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.table.TableBentley;

public class PanelDetailsOperation extends AbstractMyTab implements DocumentListener, IMyTab, IMyGui, ActionListener {

   /**
    * 
    */
   private static final long        serialVersionUID = -1733914857369658375L;

   private Account                  account;

   private Icon                     accountIcon;

   private Integer                  accountNumber;

   private TablePanelOperationByAccount accountOperations;

   private JButton                  butClear;

   private JButton                  butFindAccount;

   private JCheckBox                cbIsPrivate;

   private JLabel                   labAccount;

   private JLabel                   labBalance;

   private JLabel                   labBlocks;

   private JLabel                   labChecksum;

   private JLabel                   labEncPubKey;

   private JLabel                   labLastBlock;

   private JLabel                   labLastOpTime;

   private JLabel                   labLockBlock;

   private JLabel                   labMolina;

   private JLabel                   labName;

   private JLabel                   labNumOperation;

   private JLabel                   labPrice;

   private JLabel                   labPublicKey;

   private JLabel                   labSeller;

   private JLabel                   labType;

   private JPanel                   operationsAccountPanel;

   private PascalSwingCtx           psc;

   private IRootTabPane             root;

   private TableBentley             tableBen;

   private JTable                   tableOperations;

   private JTextField               textAccount;

   private JTextArea                textAreaEncPubKey;

   private JTextArea                textAreaPubKey;

   private JTextField               textBalance;

   private JTextField               textCheckSum;

   private JTextField               textLastBlock;

   private JTextField               textLastOpTime;

   private JTextField               textLockBlock;

   private JTextField               textMolina;

   private JTextField               textName;

   private JTextField               textNumOperations;

   private JTextComponent           textPrice;

   private JTextField               textSeller;

   private JTextField               textType;

   public PanelDetailsOperation(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), "operation_details");
      this.psc = psc;
      this.root = root;
   }

   public void actionPerformed(ActionEvent e) {
   }

   /**
    * 
    */
   public void changedUpdate(DocumentEvent e) {
      //System.out.println("changedUpdate");
      updateAccount();
   }

   public void disposeTab() {

   }

   public void guiUpdate() {

   }

   public void initTab() {
      this.setLayout(new BorderLayout());

      JPanel container = new JPanel();

      JScrollPane scrollPaneAll = new JScrollPane(container);
      this.add(scrollPaneAll, BorderLayout.CENTER);
   }

   public void insertUpdate(DocumentEvent e) {
      //System.out.println("insertUpdate");
      updateAccount();
   }


   public void removeUpdate(DocumentEvent e) {
      //System.out.println("removeUpdate");
      updateAccount();
   }

   public void setOperation(Operation op) {
      initCheck();
   }


   public void tabGainFocus() {
   }

   public void tabLostFocus() {
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "OperationDetailsPanel");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "OperationDetailsPanel");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

   /**
    * Wait a few microseconds to make sure the user has finished typing.
    * 
    */
   public void updateAccount() {
   }

}