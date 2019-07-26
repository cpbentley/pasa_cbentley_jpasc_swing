/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.thread.AbstractBRunnable;
import pasa.cbentley.core.src4.thread.IBRunnable;
import pasa.cbentley.core.src4.thread.IBRunnableListener;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.workers.WorkerWalletAssetStats;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.widgets.b.BLabel;

/**
 * 
 * @author Charles Bentley
 *
 */
public class WalletTotalAssets extends JPanel implements IMyGui, IEventsPascalSwing, IEventConsumer, MouseListener, IBRunnableListener {

   private BLabel         labKey;

   private BLabel         labKeyText;

   private BLabel         labPASA;

   private BLabel         labPASAText;

   private BLabel         labPASC;

   private BLabel         labPASCText;

   private PascalSwingCtx psc;

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

      this.addMouseListener(this);

      //if we are connected
      if (psc.getPCtx().getRPCConnection().isConnected()) {
         cmdComputeTotalAssets();
      }
   }

   /**
    * called in the GUI thread. Creates a worker.
    */
   public void cmdComputeTotalAssets() {
      WorkerWalletAssetStats worker = new WorkerWalletAssetStats(psc);
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
      }
   }

   private void cmdResetAssets() {
      labPASA.setText("0");
      labKey.setText("0");
      labPASC.setText(psc.getPCtx().getPU().getPrettyPascBalance(0, ","));

   }

   public void fillResult(WorkerWalletAssetStats.AssetStatResult res) {
      final int accountNum = res.pasa;
      final int keyNum = res.pks;
      final double pasc = res.pasc.getDouble();
      labPASA.setText(String.valueOf(accountNum));
      labKey.setText(String.valueOf(keyNum));
      labPASC.setText(psc.getPCtx().getPU().getPrettyPascBalance(pasc, ","));
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

      labPASA.setFont(df);
      labPASC.setFont(df);
      labKey.setFont(df);

      labPASAText.setFont(dftext);
      labPASCText.setFont(dftext);
      labKeyText.setFont(dftext);
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
      toDLog().pFlow("newState=" + AbstractBRunnable.toStringState(newState), this, WalletTotalAssets.class, "runnerNewState", ITechLvl.LVL_05_FINE, true);
      //we are in the worker thread here
      WorkerWalletAssetStats worker = (WorkerWalletAssetStats) runner;
      final WorkerWalletAssetStats.AssetStatResult res = worker.getAssetStatResultImmutable();
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
