/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.trade;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * List exchanges that sell.. How do you display Poloniex price ?
 * 
 * @author Charles Bentley
 *
 */
public class BuyPascalCoinPanel extends AbstractMyTab implements IMyTab, ActionListener {
   public static final String ID = "buy_pasc";

   /**
    * 
    */
   private static final long  serialVersionUID = -5317960909957796944L;

   private JButton            butLookUp;

   private JButton            butSelectAndGo;

   private Icon               iconBuyPascalCoin;

   private JLabel             labFee;

   private JLabel             labRangeEnd;

   private JLabel             labRangeStart;

   private JLabel             labTemp;


   private JTable             table;

   private JTextField         textFee;

   private JTextField         textRangeEnd;

   private JTextField         textRangeStart;

   private PascalSwingCtx psc;

   private JLabel labAttention;

   public BuyPascalCoinPanel(PascalSwingCtx psc) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      setLayout(new BorderLayout());
      iconBuyPascalCoin = psc.createImageIcon("/icons/pasc_yingyang_orangegreen_32.png", "");

      JPanel north = new JPanel();

      //         model = new TableModelAccounts();
      //         table = createTableAndLink(model, this);
      //         table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

      labTemp = new JLabel("TODO: Implement https://poloniex.com/support/api/");

      butSelectAndGo = new JButton("Select, Then Click Here To Go!");

      labAttention = new JLabel("Not Your keys, not your coins! Stay safe");
      
      north.add(butSelectAndGo);
      north.add(labTemp);

      this.add(north, BorderLayout.NORTH);

      String[] columnNames = new String[] { "Name", "Site", "Type" };
      DefaultTableModel model = new DefaultTableModel(columnNames, 0);

      model.addRow(new Object[] { "Poloniex", "http://www.poloniex.com", "High volume Exchange" });
      model.addRow(new Object[] { "GBTC", "https://www.myqbtc.com/trade", "High volume Exchange" });
      model.addRow(new Object[] { "BKEX", "https://www.bkex.com/", "High volume exchange with PASC/ETH, PASC/USDT trading pairs." });
      model.addRow(new Object[] { "Simple Swap", "https://simpleswap.io", "Exchange" });
      model.addRow(new Object[] { "Bitsquare / BISQ", "https://bisq.network/", "DEX p2p" });
      model.addRow(new Object[] { "CryptoMonster", "https://www.cryptomonster.co.uk/buy-pascal-coin", "purchase using bank-wires" });

      JTable table = new JTable(model);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane sp = new JScrollPane(table);
      table.setRowSelectionInterval(0, 0);

      this.add(sp, BorderLayout.CENTER);

      butSelectAndGo.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            String url = (String) model.getValueAt(row, 1);
            try {
               Desktop.getDesktop().browse(new URI(url));
            } catch (IOException ex) {
               ex.printStackTrace();
            } catch (URISyntaxException ex) {
               ex.printStackTrace();
            }
         }
      });

      JPanel south = new JPanel();

      JButton lab1 = new JButton(psc.createImageIcon("/icons/s64/pasc_p_gold_64.png", "/sounds/CarPassingBy.wav"));
      lab1.addActionListener(this);
      JButton lab2 = new JButton(psc.createImageIcon("/icons/s64/pasc_p_gradient_redwhite64.png", "PLOUF"));
      lab2.addActionListener(this);
      JButton lab3 = new JButton(psc.createImageIcon("/icons/s64/pasc_p2_keys_greenorange_64.png", "BELLS"));
      lab3.addActionListener(this);
      JButton lab4 = new JButton(psc.createImageIcon("/icons/s64/pasc_p2_keys_purplewhite_64.png", "/sounds/ohohohoh.ogg"));
      lab4.addActionListener(this);

      JButton lab5 = new JButton(psc.createImageIcon("/icons/s64/pasc_p2_lock_red_64.png", "JUSTDOIT"));
      lab5.addActionListener(this);

      JButton lab6 = new JButton(psc.createImageIcon("/icons/s64/pasc_yingyang_black_64.png", "INSTRUMENTS"));
      lab6.addActionListener(this);

      south.add(lab1);
      south.add(lab2);
      south.add(lab3);
      south.add(lab4);
      south.add(lab5);
      south.add(lab6);

      JScrollPane js = new JScrollPane(south);

      this.add(js, BorderLayout.SOUTH);

   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src instanceof JButton) {
         JButton b = (JButton) src;
         Icon ico = b.getIcon();
         if (ico instanceof ImageIcon) {
            ImageIcon ii = (ImageIcon) ico;
            String descr = ii.getDescription();
            if (descr == "PLOUF") {
               psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_PLOUFS);
            } else if (descr == "BELLS") {
               psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_BELLS);
            } else if (descr == "JUSTDOIT") {
               psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_DOIT);
            } else if (descr == "INSTRUMENTS") {
               psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_INSTRUMENTS);
            } else {
               psc.getAudio().playAudio(descr);
            }
         }
      }
   }

   public void disposeTab() {
      // TODO Auto-generated method stub

   }

   public Icon getTabIcon(int size) {
      return iconBuyPascalCoin;
   }

   public String getTabTip() {
      return "";
   }

   public String getTabTitle() {
      return "Buy Coins";
   }

   public void initTab() {
      // TODO Auto-generated method stub

   }

   public boolean isInitialized() {
      // TODO Auto-generated method stub
      return false;
   }

   public void tabGainFocus() {
      psc.getAudio().playAudio("/sounds/coin_glass2.wav");
   }

   public void tabLostFocus() {
      // TODO Auto-generated method stub

   }

}