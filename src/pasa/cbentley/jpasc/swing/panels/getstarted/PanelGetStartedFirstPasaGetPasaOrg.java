/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.getstarted;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import pasa.cbentley.core.src4.logging.ITechConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ITechHelpKeys;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTablePublicKeyJavaSimple;
import pasa.cbentley.jpasc.swing.workers.table.key.WorkerTableKeyWalletPrivate;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BLabelHTML;
import pasa.dekholm.riverlayout.RiverLayout;

public class PanelGetStartedFirstPasaGetPasaOrg extends PanelTabAbstractPascal implements IMyTab, IWorkerPanel, ActionListener, MouseListener {

   /**
    * 
    */
   private static final long serialVersionUID = 272892122172128510L;

   public static final String               ID = "getpasa_org";

   private BButton                          butCopyToClip;

   private BButton                          butRefresh;

   private BButton                          butShowKeyHelp;

   private JTable                           jtableKeys;

   private BLabel                           labExplainGIFNewKey;

   private BLabel                           labExplainKeyTable;

   private BLabel                           labTextGetPasaOrg;

   private JPanel                           riverlayoutPanel;

   private ModelTablePublicKeyJavaSimple simpleModel;

   private BLabelHTML                       websitePascal;

   public PanelGetStartedFirstPasaGetPasaOrg(PascalSwingCtx psc) {
      super(psc, ID);
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butRefresh) {
         cmdRefresh();
      } else if (src == butCopyToClip) {
         cmdCopyToClip();
      } else if (src == butShowKeyHelp) {
         cmdShowKeyHelp();
      }
   }

   private void cmdCopyToClip() {
      int selRow = jtableKeys.getSelectedRow();
      if (selRow != -1) {
         int rowModel = jtableKeys.convertRowIndexToModel(selRow);
         String key = (String) simpleModel.getValueAt(rowModel, 1);
         psc.getLog().consoleLogGreen("Copy to clipboard " + key);
         psc.getSwingCtx().copyStringToClipboard(key);
      }
   }

   private void cmdRefresh() {
      refreshTable();
   }

   private void cmdShowKeyHelp() {
      psc.showHelpFor(ITechHelpKeys.HELP_KEY_FIRST);
   }

   public void disposeTab() {

   }

   public void guiUpdate() {
      //#debug
      toDLog().pFlow("", this, PanelGetStartedFirstPasaGetPasaOrg.class, "guiUpdate", ITechLvl.LVL_05_FINE, true);

      super.guiUpdate();
   }

   public void initTab() {
      this.setLayout(new BorderLayout());

      SwingCtx sc = psc.getSwingCtx();

      riverlayoutPanel = new JPanel();
      RiverLayout rl = new RiverLayout();
      riverlayoutPanel.setLayout(rl);

      labTextGetPasaOrg = new BLabel(sc);
      labTextGetPasaOrg.setKey("getpasa_org.labtitle");

      websitePascal = new BLabelHTML(sc);
      websitePascal.setTextKey("getpasa_org.labtitle.html");
      websitePascal.setCursor(new Cursor(Cursor.HAND_CURSOR));
      websitePascal.addMouseListener(this);

      labExplainGIFNewKey = new BLabel(sc);
      labExplainGIFNewKey.setKey("getpasa_org.newkeytitle");

      butShowKeyHelp = new BButton(sc, this);
      butShowKeyHelp.setTextKey("but.help.ref.keys");
      //display visible keys
      labExplainKeyTable = new BLabel(sc);
      labExplainKeyTable.setKey("getpasa_org.keytabletitle");

      butRefresh = new BButton(sc, this);
      butRefresh.setTextKey("but.refresh");
      butRefresh.setIcon("refresh", "action", sc.getIconSizeActionDefault());

      butCopyToClip = new BButton(sc, this);
      butCopyToClip.setTextKey("but.copybase58.selected");
      butCopyToClip.setIcon("copy", "action", sc.getIconSizeActionDefault());

      //
      simpleModel = new ModelTablePublicKeyJavaSimple(psc);
      jtableKeys = new JTable(simpleModel);

      riverlayoutPanel.add("", labTextGetPasaOrg);
      riverlayoutPanel.add("<br>", websitePascal);

      riverlayoutPanel.add("<br>", labExplainGIFNewKey);
      riverlayoutPanel.add("tab", butShowKeyHelp);

      riverlayoutPanel.add("<br>", labExplainKeyTable);
      riverlayoutPanel.add("<br>", butCopyToClip);
      riverlayoutPanel.add("tab", butRefresh);

      riverlayoutPanel.add("p", jtableKeys.getTableHeader());
      riverlayoutPanel.add("br", jtableKeys);

      JScrollPane sp = new JScrollPane(riverlayoutPanel);
      this.add(sp, BorderLayout.CENTER);
   }

   public void mouseClicked(MouseEvent e) {
      if (e.getSource() == websitePascal) {
         psc.browseURLDesktop("https://freepasa.org/");
      }
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void panelSwingWorkerCancelled(PanelSwingWorker tableBlockSwingWorker) {
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {
      psc.resizeColumnWidthNoMax(jtableKeys);
      this.revalidate();
      this.repaint();
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {

   }

   public void refreshTable() {
      //#debug
      toDLog().pEvent("", this, PanelGetStartedFirstPasaGetPasaOrg.class, "refreshTable", ITechLvl.LVL_04_FINER, ITechConfig.FORMAT_FLAG_03_STACK);

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

}