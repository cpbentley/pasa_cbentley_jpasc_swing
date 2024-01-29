/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import pasa.cbentley.core.src4.ctx.ToStringStaticUc;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.thread.IBRunnable;
import pasa.cbentley.core.src4.thread.IBRunnableListener;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.jpasc.pcore.utils.AssetStatResult;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.WorkerWalletAssetStats;
import pasa.cbentley.swing.actions.BActionRefresh;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.widgets.b.BCMenuItem;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BMenuItem;
import pasa.cbentley.swing.widgets.b.BMenuItemToggle;
import pasa.cbentley.swing.widgets.b.BRadioButtonMenuItem;

/**
 * Widget that displays
 * <li> the block number
 * <li> private pasc
 * <li> private pasa
 * <li> private # keys
 * 
 * By right clicking with mouse, the user may hide private data
 * 
 * Popup menu also provides a refresh action.
 * 
 * @author Charles Bentley
 *
 */
public class WalletTotalAssets extends JPanel implements IMyGui, ICommandable, IEventsPascalSwing, IEventConsumer, MouseListener, IBRunnableListener, ICommandableRefresh, ActionListener {

   private BLabel         labKey;

   private BLabel         labKeyText;

   private BLabel         labPASA;

   private BLabel         labPASAText;

   private BLabel         labPASC;

   private BLabel         labPASCText;

   private PascalSwingCtx psc;

   private BLabel         labBlockText;

   private BLabel         labBlock;

   private JPopupMenu     popupMenu;

   private BActionRefresh actionRefresh;

   private JMenuItem      actionRefreshItem;

   private BCMenuItem     itemTogglePrivacyCtx;

   public WalletTotalAssets(PascalSwingCtx psc) {
      this.psc = psc;
      SwingCtx sc = psc.getSwingCtx();
      labPASC = new BLabel(sc);
      labPASC.setText("0.0");

      labPASCText = new BLabel(sc, "widget.totalasset.pasc");
      labPASCText.setFont(new Font("Serif", Font.PLAIN, 12));

      labPASA = new BLabel(sc);
      labPASA.setText("0");
      labPASA.setFont(new Font("Serif", Font.PLAIN, 16));

      labPASAText = new BLabel(sc, "widget.totalasset.pasa");
      labPASAText.setFont(new Font("Serif", Font.PLAIN, 12));

      labKey = new BLabel(sc);
      labKey.setText("0");
      labKey.setFont(new Font("Serif", Font.PLAIN, 16));

      //label could also use an icon! set options to diplay icon/ text / icon+text
      labKeyText = new BLabel(sc, "widget.totalasset.key");
      labKeyText.setFont(new Font("Serif", Font.PLAIN, 12));

      labBlockText = new BLabel(sc, "text.block");
      labBlockText.setFont(new Font("Serif", Font.PLAIN, 14));

      labBlock = new BLabel(sc);
      labBlock.setText("0");
      labBlock.setFont(new Font("Serif", Font.PLAIN, 15));

      this.add(labBlock);
      this.add(labBlockText);
      this.add(labPASC);
      this.add(labPASCText);
      this.add(labPASA);
      this.add(labPASAText);
      this.add(labKey);
      this.add(labKeyText);
      //listens for all 

      //we connect to core events throught the instance of PascalSwing ctx event bus
      psc.getEventBusPascal().addConsumer(this, PID_2_BLOCK, EID_2_BLOCK_1_NEW_BLOCK);
      psc.getEventBusPascal().addConsumer(this, PID_3_USER_OPERATION, EID_3_USER_OPERATION_0_ANY);
      psc.getEventBusPascal().addConsumer(this, PID_5_CONNECTIONS, EID_5_CONNECTIONS_1_CONNECTED);
      psc.getEventBusPascal().addConsumer(this, PID_5_CONNECTIONS, EID_5_CONNECTIONS_2_DISCONNECTED);

      this.addMouseListener(this);

      //if we are connected
      if (psc.getPCtx().getRPCConnection().isConnected()) {
         cmdComputeTotalAssets();
      }

      popupMenu = new JPopupMenu();
      actionRefresh = new BActionRefresh(psc.getSwingCtx(), this);
      actionRefreshItem = popupMenu.add(actionRefresh);

      itemTogglePrivacyCtx = new BCMenuItem<ICommandable>(sc, this, psc.getCmds().getCmdTogglePrivacyCtx());

      popupMenu.add(itemTogglePrivacyCtx);

      this.setComponentPopupMenu(popupMenu);

      psc.getEventBusPascal().addConsumer(this, PID_7_PRIVACY_CHANGES, EID_7_PRIVACY_1_CTX);
   }

   /**
    * called in the GUI thread. Creates a worker.
    */
   public void cmdComputeTotalAssets() {
      WorkerWalletAssetStats worker = new WorkerWalletAssetStats(psc);
      //read variable from
      worker.setCanUse(psc.isPrivateCtx());
      worker.addListener(this);
      psc.getPCtx().getExecutorService().execute(worker);
   }

   public void consumeEvent(BusEvent e) {
      //update balance
      if (e.getProducerID() == PID_3_USER_OPERATION) {
         if (e.getEventID() == EID_3_USER_OPERATION_2_AFTER) {
            cmdComputeTotalAssets();
         }
      } else if (e.getProducerID() == PID_2_BLOCK) {
         cmdComputeTotalAssets();
      } else if (e.getProducerID() == PID_5_CONNECTIONS) {
         if (e.getEventID() == EID_5_CONNECTIONS_1_CONNECTED) {
            cmdComputeTotalAssets();
         } else if (e.getEventID() == EID_5_CONNECTIONS_2_DISCONNECTED) {
            cmdResetAssets();
         }
      } else if (e.getProducerID() == PID_7_PRIVACY_CHANGES) {
         //update strings of menus? done with guiUpdate
         cmdComputeTotalAssets();
      }
   }

   private void cmdResetAssets() {
      labPASA.setText("0");
      labKey.setText("0");
      labPASC.setText(psc.getPCtx().getPU().getPrettyPascBalance(0, ","));

   }

   public void fillResult(AssetStatResult res) {
      final double pasc = res.getPascalCoinValue().getDouble();
      labPASC.setText(psc.getPCtx().getPU().getPrettyPascBalance(pasc, ","));

      labBlock.setText(res.getBlockStr());
      labPASA.setText(res.getNumPasaStr());
      labKey.setText(res.getNumPublicKeysStr());
   }

   public void guiUpdate() {
      //#debug
      //toDLog().pFlow("", this, WalletTotalAssets.class, "guiUpdate", IDLog.LVL_05_FINE, true);

      SwingCtx sc = psc.getSwingCtx();
      sc.guiUpdateOnChildren(this);

      Font f = sc.getUIData().getFontLabel();

      Font df = f.deriveFont(Font.BOLD, f.getSize2D() + 4);
      Font dftext = f.deriveFont(Font.ITALIC, f.getSize2D() - 1);

      //depending on color them
      labPASA.setForeground(new Color(ColorUtils.FR_BORDEAUX_Sang_de_boeuf));
      labPASC.setForeground(new Color(ColorUtils.FR_BORDEAUX_Bourgogne));
      labKey.setForeground(new Color(ColorUtils.FR_BORDEAUX_Sanguine));
      labBlock.setForeground(new Color(ColorUtils.FR_VERT_Bouteille));

      labPASA.setFont(df);
      labPASC.setFont(df);
      labKey.setFont(df);
      labBlock.setFont(df);

      labPASAText.setFont(dftext);
      labPASCText.setFont(dftext);
      labKeyText.setFont(dftext);
      labBlockText.setFont(dftext);

      sc.guiUpdateOnChildrenMenuPopup(popupMenu);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == itemTogglePrivacyCtx) {

      }
   }

   public void cmdRefresh(Object source) {
      AssetStatResult empty = new AssetStatResult(psc.getPCtx());
      fillResult(empty);
      cmdComputeTotalAssets();
   }

   public void mouseClicked(MouseEvent e) {

   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
      //#debug
      toDLog().pFlow("" + psc.getSwingCtx().toSD().d1(e), this, WalletTotalAssets.class, "mouseReleased", ITechLvl.LVL_05_FINE, true);
      cmdComputeTotalAssets();
   }

   public void runnerException(IBRunnable runner, Throwable e) {
      e.printStackTrace();
   }

   public void runnerNewState(IBRunnable runner, int newState) {
      //#debug
      toDLog().pFlow("newState=" + ToStringStaticUc.toStringState(newState), this, WalletTotalAssets.class, "runnerNewState", ITechLvl.LVL_05_FINE, true);
      //we are in the worker thread here
      WorkerWalletAssetStats worker = (WorkerWalletAssetStats) runner;
      final AssetStatResult res = worker.getAssetStatResultImmutable();
      if (res != null) {
         psc.getSwingCtx().execute(new Runnable() {

            public void run() {
               fillResult(res);
            }
         });
      }

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "WalletTotalAssets");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public IDLog toDLog() {
      return psc.toDLog();
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WalletTotalAssets");
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

}
