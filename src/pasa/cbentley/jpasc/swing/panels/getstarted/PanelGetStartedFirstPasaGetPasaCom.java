/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.getstarted;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import pasa.cbentley.core.src4.logging.ITechConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaSimple;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyWalletPrivate;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.darrylburke.renderer.ButtonColumn;
import pasa.dekholm.riverlayout.RiverLayout;

public class PanelGetStartedFirstPasaGetPasaCom extends PanelTabAbstractPascal implements IMyTab, IWorkerPanel {

   /**
    * 
    */
   private static final long                serialVersionUID = -5446488612783181118L;

   public static final String               ID               = "getpasa_com";

   private JButton                          butCopyToClip;

   private JTable                           jtableKeys;

   private JTable                           jtableSteps;

   private ModelTablePublicKeyJavaSimple simpleModel;

   private JTextPane                        stateText;

   private JTextPane                        stateText2;

   private JPanel                           riverlayoutPanel;

   public PanelGetStartedFirstPasaGetPasaCom(PascalSwingCtx psc) {
      super(psc, ID);
   }

   public void copyKeyToClip() {
      int selRow = jtableKeys.getSelectedRow();
      if (selRow != -1) {
         int rowModel = jtableKeys.convertRowIndexToModel(selRow);
         String key = (String) simpleModel.getValueAt(rowModel, 1);
         psc.getLog().consoleLogGreen("Copy to clipboard " + key);
         psc.getSwingCtx().copyStringToClipboard(key);
      }
   }

   public void disposeTab() {

   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {

   }

   public void initTab() {
      this.setLayout(new BorderLayout());
      riverlayoutPanel = new JPanel();
      JScrollPane sp = new JScrollPane(riverlayoutPanel);
      RiverLayout rl = new RiverLayout();
      riverlayoutPanel.setLayout(rl);

      EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
      stateText = new JTextPane();
      stateText.setBorder(eb);
      //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      stateText.setMargin(new Insets(5, 5, 5, 5));
      Color panelFg = UIManager.getColor("TextPane.foreground");
      psc.appendToPane(stateText, "How to obtain your first Pascal Account (PASA in short) \n ", panelFg);

      riverlayoutPanel.add("", stateText);
      //create a table of steps.. when the user select a row, a window appears
      // with the images describing whats needed to do

      //get a public key for owner here

      //using getpasa

      Object[] columnNames = new String[] { "#", "Description", "Just do it!", "Done" };
      DefaultTableModel tm = new DefaultTableModel(columnNames, 0) {
         public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 3)
               return Boolean.class;
            return Object.class;
         }
      };
      String justdoit1 = "Just do it!";
      tm.addRow(new Object[] { "1", "Buy pascal coin on an exchange Press ->", justdoit1, false });
      tm.addRow(new Object[] { "2", "Right-click a public key below and copy it", justdoit1, false });
      tm.addRow(new Object[] { "3", "Create an account on getpasa.com", justdoit1, false });
      tm.addRow(new Object[] { "4", "Send a few pascal coins to your getpasa.com account", justdoit1, false });
      tm.addRow(new Object[] { "5", "When sending from Poloniex, please use payload", justdoit1, false });
      tm.addRow(new Object[] { "6", "Relax and wait for a few blocks to be mined. Hear the sounds?", justdoit1, false });
      tm.addRow(new Object[] { "7", "The account will show in your Accounts' Assets Tab", justdoit1, false });

      jtableSteps = new JTable(tm);
      //jtableSteps.setRowSelectionAllowed(true);
      //jtableSteps.setDefaultEditor(Object.class, null);

      Action abActions = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            switch (modelRow) {
               case 0:
                  psc.browseURLDesktop("https://www.poloniex.com/");
                  break;
               case 1:
                  copyKeyToClip();
                  break;
               case 2:
                  psc.browseURLDesktop("https://getpasa.com/");
                  break;
               case 3:
                  JOptionPane.showMessageDialog(psc.getFrameRoot(), "This will depends on the prices you are willing to pay", "Price", JOptionPane.INFORMATION_MESSAGE);
                  break;
               case 4:
                  //
                  break;
               case 5:
                  //waiting for blocks.. play a funny sounds with waves

                  break;
               case 6:
                  //go to page assets

                  break;
               default:
                  break;
            }
            psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_DOIT);
            table.getModel().setValueAt(Boolean.TRUE, modelRow, 3);
         }
      };

      ButtonColumn buttonColumn = new ButtonColumn(jtableSteps, abActions, 2);

      psc.resizeColumnWidth(jtableSteps);
      riverlayoutPanel.add("p", jtableSteps.getTableHeader());
      riverlayoutPanel.add("br", jtableSteps);

      butCopyToClip = new JButton("Copy Base58 Key to Clipboard Press Here!");
      butCopyToClip.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            copyKeyToClip();
         }
      });

      stateText2 = new JTextPane();
      stateText2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      stateText2.setMargin(new Insets(5, 5, 5, 5));
      psc.appendToPane(stateText2, "", panelFg);

      simpleModel = new ModelTablePublicKeyJavaSimple(psc);

      jtableKeys = new JTable(simpleModel);

      jtableKeys.setPreferredSize(new Dimension(900, 500));
      //add a copy public to clip board menu action

      riverlayoutPanel.add("p", jtableKeys.getTableHeader());
      riverlayoutPanel.add("br", jtableKeys);

      //         JScrollPane spT = new JScrollPane(jtableSteps);
      //         this.add(spT, BorderLayout.CENTER);

      this.add(sp, BorderLayout.NORTH);
   }

   public void refreshTable() {
      //#debug
      toDLog().pEvent("", this, PanelGetStartedFirstPasaGetPasaCom.class, "refreshTable", ITechLvl.LVL_04_FINER, ITechConfig.CONFIG_FLAG_3_STACK);

      simpleModel = new ModelTablePublicKeyJavaSimple(psc);
      jtableKeys.setModel(simpleModel);
      WorkerTableKeyWalletPrivate worker = new WorkerTableKeyWalletPrivate(psc, this, simpleModel);
      worker.execute();

   }

   public void tabGainFocus() {
      //update the list of public keys
      refreshTable();
   }

   public void tabLostFocus() {

   }

   public void panelSwingWorkerCancelled(PanelSwingWorker tableBlockSwingWorker) {
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      psc.resizeColumnWidthNoMax(jtableKeys);
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

}