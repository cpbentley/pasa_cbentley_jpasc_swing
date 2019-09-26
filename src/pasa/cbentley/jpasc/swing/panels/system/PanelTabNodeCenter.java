/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.github.davidbolet.jpascalcoin.api.client.PascalCoinClient;
import com.github.davidbolet.jpascalcoin.api.model.NetProtocol;
import com.github.davidbolet.jpascalcoin.api.model.NetStats;
import com.github.davidbolet.jpascalcoin.api.model.NodeServer;
import com.github.davidbolet.jpascalcoin.api.model.NodeStatus;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.dekholm.riverlayout.RiverLayout;

/**
 * Displays data about the Pascal Wallet Node.
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class PanelTabNodeCenter extends AbstractMyTab implements ActionListener, IMyTab, IMyGui {

   /**
    * 
    */
   private static final long   serialVersionUID = -3272426259882714792L;

   private static final String ID               = "daemon";

   private JButton             butIcon1;

   private JButton             butIcon2;

   private JButton             butStartNode;

   private JButton             butStopNode;

   private JLabel              labBlockCount;

   private JLabel              labIsLocked;

   private JLabel              labIsLockedTitle;

   private JLabel              labIsNodeReady;

   private JLabel              labLocked;

   private JLabel              labNetP;

   private JLabel              labNetPAvailable;

   private JLabel              labNetworkStats;

   private JLabel              labNodeTime;

   private JLabel              labNodeVersion;

   private JLabel              labReadyStr;

   private JLabel              labServerPort;

   private JLabel              labSSL;

   private JLabel              labStatsActive;

   private JLabel              labStatsClients;

   private JLabel              labStatsDataRec;

   private JLabel              labStatsSent;

   private JLabel              labStatsServers;

   private JLabel              labStatsServers_t;

   private JLabel              labStatsTotal;

   private JLabel              labStatsTotalClients;

   private JLabel              labStatsTotalServers;

   private JLabel              labStatusStr;

   private JTextPane           stateText;

   private JTextField          textBlockCount;

   private JTextField          textLocked;

   private JTextField          textNetP;

   private JTextField          textNetPAvailable;

   private JTextField          textNodeTime;

   private JTextField          textNodeVersion;

   private JTextField          textPendingCount;

   private JTextField          textReadyStr;

   private JTextField          textServerPort;

   private JTextField          textSSL;

   private JTextField          textStatsActive;

   private JTextField          textStatsClients;

   private JTextField          textStatsDataRec;

   private JTextField          textStatsDataSent;

   private JTextField          textStatsServers;

   private JTextField          textStatsServers_t;

   private JTextField          textStatsTotal;

   private JTextField          textStatsTotalClients;

   private JTextField          textStatsTotalServers;

   private JTextField          textStatusStr;

   private PascalSwingCtx      psc;

   private JButton             butOpenNodeDataFolder;

   public PanelTabNodeCenter(PascalSwingCtx psc) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      setLayout(new BorderLayout());

   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butStartNode) {
         Boolean b = getClient().startNode();
         if (b) {
            psc.getLog().consoleLogGreen("Node started");
         } else {
            psc.getLog().consoleLog("Node could not be started");
         }
      } else if (e.getSource() == butStopNode) {
         Boolean b = getClient().stopNode();
         if (b) {
            psc.getLog().consoleLogGreen("Node stopped");
         } else {
            psc.getLog().consoleLog("Node could not be stopped");
         }
      } else if (e.getSource() == butOpenNodeDataFolder) {
         try {
            Desktop desktop = Desktop.getDesktop();
            String dir = System.getProperty("user.home");
            dir += "\\AppData\\Roaming\\PascalCoin";

            //#debug
            toDLog().pCmd("Opening folder " + dir, null, PanelTabNodeCenter.class, "actionPerformed", ITechLvl.LVL_08_INFO, true);

            desktop.open(new File(dir));
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      }

   }

   public void disposeTab() {
   }

   /**
    * 
    * @return never null. 
    */
   public PascalCoinClient getClient() {
      return psc.getPascalClient();
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void initTab() {
      EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
      stateText = new JTextPane();
      stateText.setBorder(eb);
      //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      stateText.setMargin(new Insets(5, 5, 5, 5));

      JPanel riverlayoutPanel = new JPanel();
      JScrollPane sp = new JScrollPane(riverlayoutPanel);
      RiverLayout rl = new RiverLayout();
      riverlayoutPanel.setLayout(rl);

      add(sp, BorderLayout.CENTER);

      butIcon1 = new JButton(psc.createImageIcon("/icons/s64/pasc_p_gold_64.png", "/sounds/CarPassingBy.wav"));
      butIcon1.addActionListener(this);
      butIcon2 = new JButton(psc.createImageIcon("/icons/s64/pasc_node_64.png", "/sounds/CarPassingBy.wav"));
      butIcon2.addActionListener(this);

      butOpenNodeDataFolder = new JButton("Open Node Data Folder");
      butOpenNodeDataFolder.addActionListener(this);

      riverlayoutPanel.add("", butIcon1);
      riverlayoutPanel.add("tab", butIcon2);
      riverlayoutPanel.add("tab", butOpenNodeDataFolder);

      labIsLockedTitle = new JLabel("Wallet State:");
      labIsLocked = new JLabel("Locked");
      labIsLocked.setForeground(Color.RED);

      labLocked = new JLabel("IsLocked");
      textLocked = new JTextField();
      textLocked.setEnabled(false);

      riverlayoutPanel.add("br", labLocked);
      riverlayoutPanel.add("tab", textLocked);

      labBlockCount = new JLabel("Block#");
      textBlockCount = new JTextField(6);
      textBlockCount.setEnabled(false);

      riverlayoutPanel.add("br", labBlockCount);
      riverlayoutPanel.add("tab", textBlockCount);

      labNodeVersion = new JLabel("Node Version");
      textNodeVersion = new JTextField(10);
      textNodeVersion.setEnabled(false);

      riverlayoutPanel.add("br", labNodeVersion);
      riverlayoutPanel.add("tab", textNodeVersion);

      labNodeTime = new JLabel("Node Timestamp");
      textNodeTime = new JTextField(20);
      textNodeTime.setEnabled(false);

      riverlayoutPanel.add("tab", labNodeTime);
      riverlayoutPanel.add("tab", textNodeTime);

      labIsNodeReady = new JLabel("Is Node Ready to Process Transaction?");
      textPendingCount = new JTextField();
      textPendingCount.setEnabled(false);

      labReadyStr = new JLabel("Ready:");
      textReadyStr = new JTextField();
      textReadyStr.setEnabled(false);

      riverlayoutPanel.add("br", labReadyStr);
      riverlayoutPanel.add("tab", textReadyStr);

      labStatusStr = new JLabel("Status:");
      textStatusStr = new JTextField(30);
      textStatusStr.setEnabled(false);

      riverlayoutPanel.add("br", labStatusStr);
      riverlayoutPanel.add("tab", textStatusStr);

      labServerPort = new JLabel("Server Port");
      textServerPort = new JTextField(5);
      textServerPort.setEnabled(false);

      riverlayoutPanel.add("br", labStatusStr);
      riverlayoutPanel.add("tab", textStatusStr);

      labSSL = new JLabel("SSL Version");
      textSSL = new JTextField(15);
      textSSL.setEnabled(false);

      riverlayoutPanel.add("tab", labSSL);
      riverlayoutPanel.add("tab", textSSL);

      labNetP = new JLabel("Net Protocol");
      textNetP = new JTextField(10);
      textNetP.setEnabled(false);

      riverlayoutPanel.add("br", labNetP);
      riverlayoutPanel.add("tab", textNetP);

      labNetPAvailable = new JLabel("Net Protocol Available");
      textNetPAvailable = new JTextField(10);
      textNetPAvailable.setEnabled(false);

      riverlayoutPanel.add("tab", labNetPAvailable);
      riverlayoutPanel.add("tab", textNetPAvailable);

      labNetworkStats = new JLabel("Network Statistics");

      labStatsDataRec = new JLabel("Data Recieved");
      textStatsDataRec = new JTextField(15);
      textStatsDataRec.setEnabled(false);

      riverlayoutPanel.add("br", labStatsDataRec);
      riverlayoutPanel.add("tab", textStatsDataRec);

      labStatsSent = new JLabel("Data Sent");
      textStatsDataSent = new JTextField(15);
      textStatsDataSent.setEnabled(false);

      riverlayoutPanel.add("br", labStatsSent);
      riverlayoutPanel.add("tab", textStatsDataSent);

      labStatsActive = new JLabel("Active");
      textStatsActive = new JTextField(5);
      textStatsActive.setEnabled(false);

      riverlayoutPanel.add("br", labStatsActive);
      riverlayoutPanel.add("tab", textStatsActive);

      labStatsClients = new JLabel("Clients");
      textStatsClients = new JTextField(5);
      textStatsClients.setEnabled(false);

      riverlayoutPanel.add("br", labStatsClients);
      riverlayoutPanel.add("tab", textStatsClients);

      labStatsServers = new JLabel("Servers");
      textStatsServers = new JTextField(5);
      textStatsServers.setEnabled(false);

      riverlayoutPanel.add("br", labStatsServers);
      riverlayoutPanel.add("tab", textStatsServers);

      labStatsServers_t = new JLabel("Servers_t");
      textStatsServers_t = new JTextField(5);
      textStatsServers_t.setEnabled(false);

      riverlayoutPanel.add("br", labStatsServers_t);
      riverlayoutPanel.add("tab", textStatsServers_t);

      labStatsTotalClients = new JLabel("Total Clients");
      textStatsTotalClients = new JTextField(5);
      textStatsTotalClients.setEnabled(false);

      riverlayoutPanel.add("br", labStatsTotalClients);
      riverlayoutPanel.add("tab", textStatsTotalClients);

      labStatsTotalServers = new JLabel("Total Servers");
      textStatsTotalServers = new JTextField(5);
      textStatsTotalServers.setEnabled(false);

      riverlayoutPanel.add("br", labStatsTotalServers);
      riverlayoutPanel.add("tab", textStatsTotalServers);

      labStatsTotal = new JLabel("Total");
      textStatsTotal = new JTextField(5);
      textStatsTotal.setEnabled(false);

      riverlayoutPanel.add("br", labStatsTotal);
      riverlayoutPanel.add("tab", textStatsTotal);

      butStartNode = new JButton("Start Node");
      butStopNode = new JButton("Stop Node");
      JPanel south = new JPanel();
      south.setLayout(new FlowLayout());
      south.add(butStartNode);
      south.add(butStopNode);

      add(south, BorderLayout.SOUTH);

      butStartNode.addActionListener(this);
      butStopNode.addActionListener(this);

   }

   public void tabGainFocus() {
      this.updateState();
   }

   public void tabLostFocus() {
      
   }

   public void updateState() {
      PascalCoinClient pclient = getClient();
      Integer bc = pclient.getBlockCount(); //null if not connected
      Integer pendingC = pclient.getPendingsCount();
      String blockCountStr = (bc == null) ? "null" : bc.toString();
      String pendingCStr = (pendingC == null) ? "null" : pendingC.toString();

      textBlockCount.setText(blockCountStr);
      textPendingCount.setText(pendingCStr);

      NodeStatus status = pclient.getNodeStatus();
      if (status != null) {
         String version = status.getVersion();
         textNodeVersion.setText(version);
         Boolean isLocked = status.getLocked();
         String str = "";
         if (isLocked == null) {
            textLocked.setText("null");
         } else {
            if (isLocked) {
               textLocked.setForeground(Color.RED);
               textLocked.setText("Wallet is password locked");
            } else {
               textLocked.setForeground(Color.GREEN);
               textLocked.setText("Wallet is unlocked");
            }
            str = String.valueOf(isLocked.booleanValue());
         }

         textReadyStr.setText(status.getReadyDescriptor());
         textStatusStr.setText(status.getStatusDescriptor());

         textSSL.setText(status.getOpenssl());
         textServerPort.setText("" + status.getPort());

         Long timest = status.getTimestamp();
         String strT = "";
         if (timest == null) {
            strT = "null";
         } else {
            Date d = new Date(timest.longValue() * 1000);
            str = psc.getFormatDateTime().format(d);
         }
         textNodeTime.setText(strT);

         NetProtocol netp = status.getNetProtocol();
         if (netp != null) {
            Integer versionProtocol = netp.getVersion();
            if (versionProtocol != null) {
               textNetP.setText(versionProtocol.toString());
            }
            Integer versionAv = netp.getAvailableVersion();
            if (versionProtocol != null) {
               textNetP.setText(versionAv.toString());
            }
         }

         NetStats nets = status.getNetStats();
         if (nets != null) {
            Integer clients = nets.getClients();
            String clientStr = (clients == null) ? "null" : clients.toString();
            textStatsClients.setText(clientStr);
            Integer servers = nets.getServers();
            String serversStr = (servers == null) ? "null" : servers.toString();
            textStatsServers.setText(serversStr);
            textStatsDataRec.setText(psc.getPrettyBytes(nets.getBytesReceived()));
            textStatsDataSent.setText(psc.getPrettyBytes(nets.getBytesSent()));
            textStatsActive.setText("" + nets.getActive());
            textStatsServers_t.setText("" + nets.getServersT());
            textStatsTotalClients.setText("" + nets.getTotalClients());
            textStatsTotalServers.setText("" + nets.getTotalServers());
            textStatsTotal.setText("" + nets.getTotal());
         }
      }
   }

   /**
    * Text based data so it can be copy pasted easily
    */
   public void updateStateText() {
      PascalCoinClient pclient = getClient();
      stateText.setText("");
      Integer bc = pclient.getBlockCount(); //null if not connected
      String blockCountStr = (bc == null) ? "null" : bc.toString();
      psc.appendToPane(stateText, "BlockCount=", Color.BLACK);
      psc.appendToPane(stateText, blockCountStr, Color.RED);

      Integer pendingC = pclient.getPendingsCount();
      String pendingCStr = (pendingC == null) ? "null" : pendingC.toString();

      psc.appendToPane(stateText, "\n", Color.BLACK);
      psc.appendToPane(stateText, "PendingCount=", Color.BLACK);
      psc.appendToPane(stateText, pendingCStr, Color.RED);

      NodeStatus status = pclient.getNodeStatus();
      if (status != null) {
         String version = status.getVersion();
         psc.appendToPane(stateText, "\n", Color.BLACK);
         psc.appendToPane(stateText, "Version=", Color.BLACK);
         psc.appendToPane(stateText, version, Color.GREEN);
         Boolean isLocked = status.getLocked();
         String str = "";
         if (isLocked == null) {
            str = "null";
         } else {
            str = String.valueOf(isLocked.booleanValue());
         }
         psc.appendToPane(stateText, "\n", Color.BLACK);
         psc.appendToPane(stateText, "isLocked=", Color.BLACK);
         psc.appendToPane(stateText, str, Color.GREEN);

         psc.appendToPane(stateText, "\n", Color.BLACK);
         psc.appendToPane(stateText, "Ready=", Color.BLACK);
         psc.appendToPane(stateText, status.getReadyDescriptor(), Color.BLACK);

         psc.appendToPane(stateText, "\n", Color.BLACK);
         psc.appendToPane(stateText, "Status=", Color.BLACK);
         psc.appendToPane(stateText, status.getStatusDescriptor(), Color.BLACK);

         psc.appendToPane(stateText, "\n", Color.BLACK);
         psc.appendToPane(stateText, "Openssl=", Color.BLACK);
         psc.appendToPane(stateText, status.getOpenssl(), Color.BLACK);

         psc.appendToPane(stateText, "\n", Color.BLACK);
         psc.appendToPane(stateText, "Port=", Color.BLACK);
         psc.appendToPane(stateText, "" + status.getPort().shortValue(), Color.BLACK);

         Long timest = status.getTimestamp();
         String strT = "";
         if (timest == null) {
            strT = "null";
         } else {
            Date d = new Date(timest.longValue());
         }
         psc.appendToPane(stateText, "\n", Color.BLACK);
         psc.appendToPane(stateText, "Openssl=", Color.BLACK);
         psc.appendToPane(stateText, strT, Color.BLACK);

         NetProtocol netp = status.getNetProtocol();
         if (netp != null) {
            Integer versionProtocol = netp.getVersion();
            if (versionProtocol != null) {
               psc.appendToPane(stateText, "\n", Color.BLACK);
               psc.appendToPane(stateText, "netprotocolversion=", Color.BLACK);
               psc.appendToPane(stateText, versionProtocol.toString(), Color.BLACK);
            }
            Integer versionAv = netp.getAvailableVersion();
            if (versionProtocol != null) {
               psc.appendToPane(stateText, "\n", Color.BLACK);
               psc.appendToPane(stateText, "netprotocolversionAvailable=", Color.BLACK);
               psc.appendToPane(stateText, versionAv.toString(), Color.BLACK);
            }
         }

         NetStats nets = status.getNetStats();
         if (nets != null) {

            Integer clients = nets.getClients();
            String clientStr = (clients == null) ? "null" : clients.toString();
            psc.appendToPane(stateText, "\n", Color.BLACK);
            psc.appendToPane(stateText, "#clients=", Color.BLACK);
            psc.appendToPane(stateText, clientStr, Color.BLACK);

            Integer servers = nets.getServers();
            String serversStr = (servers == null) ? "null" : servers.toString();
            psc.appendToPane(stateText, " #clients=", Color.BLACK);
            psc.appendToPane(stateText, serversStr, Color.BLACK);

            psc.appendToPane(stateText, "bytes sent=", Color.BLACK);
            psc.appendToPane(stateText, psc.getPrettyBytes(nets.getBytesSent()), Color.BLACK);
            psc.appendToPane(stateText, " bytes received=", Color.BLACK);
            psc.appendToPane(stateText, psc.getPrettyBytes(nets.getBytesReceived()), Color.BLACK);

         }
         List<NodeServer> nodes = status.getNodeServers();
         Iterator<NodeServer> it = nodes.iterator();
         while (it.hasNext()) {
            NodeServer ns = (NodeServer) it.next();
         }
      }
   }
}