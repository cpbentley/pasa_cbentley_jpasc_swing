/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.davidbolet.jpascalcoin.api.client.PascalCoinClient;
import com.github.davidbolet.jpascalcoin.api.client.PascalCoinClientImpl;
import com.github.davidbolet.jpascalcoin.api.constants.PascalCoinConstants;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.jpasc.pcore.client.PascalClientDummy;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.interfaces.IBlockListener;
import pasa.cbentley.jpasc.pcore.network.RPCConnection;
import pasa.cbentley.jpasc.pcore.ping.PingLogger;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ILogin;
import pasa.cbentley.jpasc.swing.widgets.WalletTotalAssets;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.panels.MemoryPanelProgressBar;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BButtonToggle;

/**
 * Login UI.
 * <br>
 * <br>
 * 2 Modes:
 * <li> Banner mode. When located at the top or bottom. It shows less info on a single line.
 * <li> Tab mode. Max data.
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class PanelTabLogin extends PanelTabAbstractPascal implements ActionListener, IBlockListener, ILogin, IEventConsumer, MouseListener {

   /**
    * 
    */
   private static final long serialVersionUID = -2208648402397333414L;

   private BButtonToggle     buttonConnect;

   private BButtonToggle     buttonLock;


   private JLabel            labWaitingTitle;

   private JLabel            loginBlockNumLab;

   private JTextField        loginIPField;

   private JLabel            loginLabIP;

   private JTextField        loginPortField;

   private JLabel            loginPortLab;

   private JLabel            loginTimingLab;

   private JLabel            loginTimingLabElapsed;

   private WalletTotalAssets walletTotalAssets;

   private PingLogger        pingerLogger;

   private PanelCtxHelperAbstract panelCtxHelperAbstract;
   
   public PanelTabLogin(PascalSwingCtx psc) {
      super(psc, "login_pasc");
      psc.setLogin(this);
      psc.addBlockListener(this);
      psc.getEventBusPascal().addConsumer(this, IEventsPascalSwing.EID_4_WALLET_LOCK_0_ANY, IEventsPascalSwing.PID_4_WALLET_LOCK);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == buttonConnect) {
         cmdToggleConnect();
      } else if (e.getSource() == buttonLock) {
         cmdLock();
         //the command is managed state by itself
      }
   }

   public void cmdConnect() {
      //TODO check if option with ip is enabled
      cmdConnectDefault();
   }

   /**
    * TODO avoid double connection
    */
   public void cmdConnectDefault() {
      PCoreCtx pc = psc.getPCtx();
      RPCConnection rpc = pc.getRPCConnection();
      if (!rpc.isConnected()) {
         boolean isSuccess = rpc.connectLocalhost();
         if (isSuccess) {
            BusEvent be = psc.getEventBusPascal().createEvent(IEventsPascalSwing.PID_5_CONNECTIONS, IEventsPascalSwing.EID_5_CONNECTIONS_1_CONNECTED, null);
            //be.setUserEvent(); we don't know here
            psc.getEventBusPascal().putOnBus(be);
            setButtonConnectStateAsConnected();

            buttonLock.setEnabled(true);
            boolean isLocked = rpc.isLocked();

            setButtonLockState(isLocked);
            rpc.addBlockListener(this);
         }
      } else {

      }

      if (pingerLogger == null) {
         pingerLogger = new PingLogger(pc);
      }
      rpc.startPinging();
   }

   public void cmdLockStateUpdate() {
      PCoreCtx pc = psc.getPCtx();
      RPCConnection rpc = pc.getRPCConnection();
      if (rpc.isConnected()) {
         boolean isLocked = rpc.isDaemonLocked();
         setButtonLockState(isLocked);
      }
   }

   private void setButtonLockState(boolean isLocked) {
      if (isLocked) {
         setButtonLockStateAsLocked();
      } else {
         setButtonLockStateAsUnlocked();
      }
   }

   /**
    * TODO avoid double connection
    */
   public void cmdConnectComplex() {
      String ip = this.loginIPField.getText();
      Short port = PascalCoinConstants.DEFAULT_MAINNET_RPC_PORT;
      try {
         port = Short.valueOf(this.loginPortField.getText());
      } catch (NumberFormatException e) {
         e.printStackTrace();
         //print message to console
      }
      PCoreCtx pc = psc.getPCtx();
      RPCConnection rpc = pc.getRPCConnection();

      //by default we do a localhost connection.

      //TODO connection through unified through CORE
      //construct a client to try to connect to
      //problem with exceptions.. is that its not friendly with user interface
      //we check for nulls anyways... So create a special function for just testing
      //connection. that function returns an exception
      //PascalCoinClient client = new PascalCoinClientImplEx(ip, port);
      PascalCoinClient client = new PascalCoinClientImpl(ip, port);
      try {
         Integer blockCount = client.getBlockCount();
         if (blockCount != null) {
            psc.getLog().consoleLog(ip + ":" + port + " Found Wallet Node with #" + blockCount + " blocks");
            this.loginBlockNumLab.setText("Block " + blockCount);
            setButtonConnectStateAsConnected();
            Color green = this.psc.getGreen();
            this.loginPortField.setForeground(green);
            this.loginIPField.setForeground(green);
            //starts a task the ping for connection checking the block count and playing a sound when a new block is found

            BusEvent be = psc.getEventBusPascal().createEvent(IEventsPascalSwing.PID_5_CONNECTIONS, IEventsPascalSwing.EID_5_CONNECTIONS_1_CONNECTED, null);
            //be.setUserEvent(); we don't know here
            psc.getEventBusPascal().putOnBus(be);

            psc.getAudio().playAudio("/sounds/CarPassingBy.wav");
         } else {
            Color red = this.psc.getRed();
            this.loginIPField.setForeground(red);
            this.loginPortField.setForeground(red);
            psc.getLog().consoleLogError(ip + ":" + port + " Connection failed");
            client = new PascalClientDummy(psc.getPCtx());
            psc.getAudio().playAudio("/sounds/boing.wav");
         }
      } catch (Exception e) {
         e.printStackTrace();
         psc.getLog().consoleLogError("Error while connecting");
      }

      boolean isLocked = pc.getRPCConnection().isLocked();
      buttonLock.setSelected(isLocked);

      pc.getRPCConnection().startPinging(); //start pinging the client

      //refresh all active tabs ?
      psc.getSwingCtx().updateAllVisibleTabs();

   }

   public void cmdDisconnect() {
      PCoreCtx pc = psc.getPCtx();
      RPCConnection rpc = pc.getRPCConnection();
      rpc.disconnect();
      setButtonConnectStateAsDisconnected();
      //set lock button in lock mode and disabled
      setButtonLockStateAsLocked();
      buttonLock.setEnabled(false);
   }

   private void cmdLock() {
      RPCConnection con = psc.getPCtx().getRPCConnection();
      if (con.isConnected()) {
         if (con.isLocked()) {
            boolean isUnlocked = psc.askToUnlock("Enter the Daemon Wallet Password", "", psc.getFrameRoot());

            //old method
            //            String pwd = JOptionPane.showInputDialog(psc.getFrameRoot(), "Enter the Wallet Password");
            //            boolean b = con.unlock(pwd);
            if (isUnlocked) {
               setButtonLockStateAsUnlocked();
               psc.getLog().consoleLogGreen("Wallet is unlocked");
            } else {
               psc.getLog().consoleLogGreen("Wallet fail to unlock! Probably wrong password");
            }
         } else {
            boolean b = con.lock();
            if (b) {
               setButtonLockStateAsLocked();
            } else {
               psc.getLog().consoleLogGreen("Wallet fail to lock!");
            }
         }
      } else {
         psc.getLog().consoleLogError("You must be connected in order to lock/unlock a wallet");
      }
   }

   public void cmdToggleConnect() {
      PCoreCtx pc = psc.getPCtx();
      RPCConnection rpc = pc.getRPCConnection();
      if (rpc.isConnected()) {
         cmdDisconnect();
      } else {
         cmdConnect();
      }
   }

   public void consumeEvent(BusEvent e) {
      //we must be in GUI thread
      if (e.getProducerID() == IEventsPascalSwing.PID_4_WALLET_LOCK) {
         int eid = e.getEventID();
         if (eid == IEventsPascalSwing.EID_4_WALLET_LOCK_1_LOCKED) {
            setButtonLockStateAsLocked();
            e.setActed();
         } else if (eid == IEventsPascalSwing.EID_4_WALLET_LOCK_2_UNLOCKED) {
            setButtonLockStateAsUnlocked();
            e.setActed();
         }
      }
   }

   public void disposeTab() {
   }

   public void guiUpdate() {
      //#debug
      //toDLog().pFlow("", this, LoginPanel.class, "guiUpdate", IDLog.LVL_05_FINE, true);
      //if the panel is not initialized.. you have to guiUpdate after the init because you are !
      super.guiUpdate();
   }

   public void initTab() {

      this.setLayout(new FlowLayout(FlowLayout.LEADING));

      SwingCtx sc = psc.getSwingCtx();

      loginLabIP = new JLabel("IP:");
      loginIPField = new JTextField("127.0.0.1", 17);

      loginPortLab = new JLabel("Port:");
      loginPortField = new JTextField("" + PascalCoinConstants.DEFAULT_MAINNET_RPC_PORT, 5);

      loginBlockNumLab = new JLabel("");
      loginTimingLab = new JLabel("");
      loginTimingLab.setForeground(this.psc.getGreen());
      loginTimingLab.setToolTipText("Time that was needed to find the last block");

      labWaitingTitle = new JLabel("Waiting for next");

      loginTimingLabElapsed = new JLabel();
      loginTimingLabElapsed.setForeground(new Color(ColorUtils.WEB_indianred));
      //loginTimingLabElapsed.setEnabled(false);
      loginTimingLabElapsed.setToolTipText("Time elapsed since last block was found");

      MemoryPanelProgressBar mem = new MemoryPanelProgressBar(psc.getSwingCtx());

      //remove them for now from the login
      //this.add(butBack);
      //this.add(butForward);

      walletTotalAssets = new WalletTotalAssets(psc);

      this.add(walletTotalAssets);
      if (psc.getPascPrefs().getBoolean("complex.login", false)) {
         this.add(loginLabIP);
         this.add(loginIPField);
         this.add(loginPortLab);
         this.add(loginPortField);
      }

      buttonConnect = new BButtonToggle(sc, this);
      buttonConnect.setTextKeys("but.connect", "but.disconnect");
      buttonConnect.setIcon("connect", "action", IconFamily.ICON_SIZE_1_SMALL);

      buttonLock = new BButtonToggle(sc, this);
      buttonLock.setTextKeys("but.lock", "but.unlock");
      buttonLock.setIcon("lock", "action", IconFamily.ICON_SIZE_1_SMALL);
      buttonLock.addMouseListener(this);

      
      this.add(buttonConnect);
      this.add(buttonLock);

      this.add(mem);

      if(panelCtxHelperAbstract != null) {
         this.add(panelCtxHelperAbstract);
         panelCtxHelperAbstract.init();
      }
      
      if (isConfigShowBlockInfo) {
         this.add(loginBlockNumLab);
         this.add(loginTimingLab);
         this.add(labWaitingTitle);
         this.add(loginTimingLabElapsed);
      }
   }

   private boolean isConfigShowBlockInfo;

   public void pingDisconnect() {
      setButtonConnectStateAsDisconnected();
      loginIPField.setForeground(psc.getRed());
      loginPortField.setForeground(psc.getRed());
      psc.getAudio().playAudio("/sounds/boing.wav");
   }

   public void pingError() {
      setButtonConnectStateAsDisconnected();
      loginIPField.setForeground(psc.getRed());
      loginPortField.setForeground(psc.getRed());
      psc.getAudio().playAudio("/sounds/tchao.ogg");
   }

   public void pingNewBlock(Integer newBlock, long millis) {
      long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
      String str = String.format("%d,%d min,sec", minutes, TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
      if (loginTimingLabElapsed != null) {
         loginTimingLabElapsed.setText("0");
         loginBlockNumLab.setText("Last Block is " + newBlock + " found after ");
         loginTimingLab.setText(str);
      }

   }

   public void pingNewPendingCount(Integer count, Integer oldCount) {
   }

   public void pingNoBlock(long millis) {
      long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
      String str = String.format("%d min, %d sec", minutes, TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
      //special widget for printing time elapsed
      //loginTimingLabElapsed.setText(str); //this call revalidate and repaint.. too expensive
   }

   private void setButtonConnectStateAsConnected() {
      buttonConnect.setSelected(true);
   }

   private void setButtonConnectStateAsDisconnected() {
      buttonConnect.setSelected(false);
   }

   /**
    * Apply GUI state for a lock state
    */
   private void setButtonLockStateAsLocked() {
      buttonLock.setSelected(true);
   }

   private void setButtonLockStateAsUnlocked() {
      buttonLock.setSelected(false);
   }

   public void tabGainFocus() {
   }

   public void tabLostFocus() {
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
      Object src = e.getSource();
      if (src == buttonLock) {
         if (e.getButton() == MouseEvent.BUTTON3) {
            cmdLockStateUpdate();
         }
      }
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public boolean isConfigShowBlockInfo() {
      return isConfigShowBlockInfo;
   }

   public void setConfigShowBlockInfo(boolean isConfigShowBlockInfo) {
      this.isConfigShowBlockInfo = isConfigShowBlockInfo;
   }

   public PanelCtxHelperAbstract getPanelCtxHelperAbstract() {
      return panelCtxHelperAbstract;
   }

   public void setPanelCtxHelperAbstract(PanelCtxHelperAbstract panelCtxHelperAbstract) {
      this.panelCtxHelperAbstract = panelCtxHelperAbstract;
   }
}