/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cellrenderers;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableAccountRangePrices;
import pasa.cbentley.swing.ctx.IEventsSwing;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerStringHSLPasc;

public class CellRendereManager implements IEventConsumer, IStringable, PropertyChangeListener {

   private CellRendererAccount                cellRendererAccount;

   private CellRendererAccountAge             cellRendererAccountAge;

   private CellRendererAccountContiguous      cellRendererAccountContiguous;

   private CellRendererAccountContiguousCount cellRendererAccountContiguousCount;

   private CellRendererAccountOpCount         cellRendererAccountOpCount;

   private CellRendererOpAccount              cellRendererAccountOperation;

   private CellRendererAccountSoldContiguous  cellRendererAccountSoldContiguous;

   private CellRendererBalance                cellRendererBalanceInteger;

   private TableCellRenderer                  cellRendererBlock;

   private CellRendererBlockMiner             cellRendererBlockMiner;

   private CellRendererBlockOp                cellRendererBlockOp;

   private CellRendererRedGreenDouble         cellRendererDouble;

   private CellRendererKeyAccountNum          cellRendererKeyAccountNum;

   private CellRendererKeyName                cellRendererKeyName;

   private CellRendererOpCount                cellRendererOpCount;

   private CellRendererOpType                 cellRendererOpType;

   private CellRendererOpTypeSub              cellRendererOpTypeSub;

   private CellRendererPasaPrice              cellRendererPasaPrice;

   private CellRendererTime                   cellRendererTime;

   private boolean                            isDarkTheme;

   private PascalSwingCtx                     psc;

   private List<PascalTableCellRenderer>      renderers;

   public CellRendereManager(PascalSwingCtx psc) {
      this.psc = psc;

      renderers = new ArrayList<PascalTableCellRenderer>();

      UIManager.addPropertyChangeListener(this);

      psc.getSwingCtx().getEventBusSwing().addConsumer(this, IEventsSwing.PID_02_UI, IEventsSwing.EID_02_UI_01_CHANGE);
   }

   public void consumeEvent(BusEvent e) {
      if (e.getEventID() == IEventsSwing.PID_02_UI) {
         eventUpdate();
      }
   }

   private void eventUpdate() {

      Color labelFg = psc.getCellRendereManager().getLabelForeground();
      //analyse color of label
      int r = labelFg.getRed();
      int g = labelFg.getGreen();
      int b = labelFg.getBlue();
      int add = r + g + b;
      if (add > 600) {
         //foreground color is bright.. meaning background is dark
         isDarkTheme = true;
      } else {
         isDarkTheme = false;
      }
      for (PascalTableCellRenderer renderer : renderers) {
         renderer.dataUpdate();
      }
      //#debug
      psc.toDLog().pFlow("isDarkTheme=" + isDarkTheme, this, CellRendereManager.class, "update", ITechLvl.LVL_04_FINER, true);

   }

   /**
    * 
    * @return
    */
   public TableCellRenderer getCellRendererBlockInteger() {
      if (cellRendererBlock == null) {
         cellRendererBlock = new CellRendererBlockRGB(psc);
         //cellRendererBlock = new CellRendererBlockRandom(psc);
         //cellRendererBlock = new CellRendererBlockTest(this);
      }
      return cellRendererBlock;
   }

   public TableCellRenderer getCellKeyAccountNum() {
      if (cellRendererKeyAccountNum == null) {
         cellRendererKeyAccountNum = new CellRendererKeyAccountNum(psc);
         registerRenderer(cellRendererKeyAccountNum);
      }
      return cellRendererKeyAccountNum;
   }

   private CellRendererBlockTime            cellRendererBlockTime;

   private CellRendererIntegerStringHSLPasc cellRendererIntegerStringHSLPasc;

   private CellRendererAccountPascal        cellRendererAccountPascal;

   public CellRendererBlockTime getCellRendererBlockTime() {
      if (cellRendererBlockTime == null) {
         cellRendererBlockTime = new CellRendererBlockTime(psc);
      }
      return cellRendererBlockTime;
   }

   public CellRendererAccount getCellRendererAccount() {
      if (cellRendererAccount == null) {
         cellRendererAccount = new CellRendererAccount(psc);
         registerRenderer(cellRendererAccount);
      }
      return cellRendererAccount;
   }

   public CellRendererIntegerStringHSLPasc getCellRendererAccountBentley() {
      if (cellRendererIntegerStringHSLPasc == null) {
         cellRendererIntegerStringHSLPasc = new CellRendererIntegerStringHSLPasc(psc.getSwingCtx());
         //registerRenderer(cellRendererIntegerStringHSLPasc);
      }
      return cellRendererIntegerStringHSLPasc;
   }

   public TableCellRenderer getCellRendererAccountAge() {
      if (cellRendererAccountAge == null) {
         cellRendererAccountAge = new CellRendererAccountAge(psc);
         registerRenderer(cellRendererAccountAge);
      }
      return cellRendererAccountAge;
   }

   public CellRendererAccountContiguous getCellRendererAccountContiguous() {
      if (cellRendererAccountContiguous == null) {
         cellRendererAccountContiguous = new CellRendererAccountContiguous(psc);
         registerRenderer(cellRendererAccountContiguous);
      }
      return cellRendererAccountContiguous;
   }

   public CellRendererAccountContiguousCount getCellRendererAccountContiguousCount() {
      if (cellRendererAccountContiguousCount == null) {
         cellRendererAccountContiguousCount = new CellRendererAccountContiguousCount(psc);
         registerRenderer(cellRendererAccountContiguousCount);
      }
      return cellRendererAccountContiguousCount;
   }

   private CellRendererAccountPackCount cellRendererAccountPackCount;

   public CellRendererAccountPackCount getCellRendererAccountPackCount() {
      if (cellRendererAccountPackCount == null) {
         cellRendererAccountPackCount = new CellRendererAccountPackCount(psc);
      }
      return cellRendererAccountPackCount;
   }

   public CellRendererAccountOpCount getCellRendererAccountOpCount() {
      if (cellRendererAccountOpCount == null) {
         cellRendererAccountOpCount = new CellRendererAccountOpCount(psc);
         registerRenderer(cellRendererAccountOpCount);
      }
      return cellRendererAccountOpCount;
   }

   public CellRendererOpAccount getCellRendererAccountOperation() {
      if (cellRendererAccountOperation == null) {
         cellRendererAccountOperation = new CellRendererOpAccount(psc);
         registerRenderer(cellRendererAccountOperation);
      }
      return cellRendererAccountOperation;
   }

   /**
    * Using {@link ModelTableAccountRangePrices#INDEX_19_RANGE_COLOR} for coloring
    * @return
    */
   public CellRendererAccountSoldContiguous getCellRendererAccountSoldContiguous() {
      if (cellRendererAccountSoldContiguous == null) {
         cellRendererAccountSoldContiguous = new CellRendererAccountSoldContiguous(psc, ModelTableAccountRangePrices.INDEX_19_RANGE_COLOR);
         registerRenderer(cellRendererAccountSoldContiguous);
      }
      return cellRendererAccountSoldContiguous;
   }

   public TableCellRenderer getCellRendererBalanceInteger() {
      if (cellRendererBalanceInteger == null) {
         cellRendererBalanceInteger = new CellRendererBalance(psc);
      }
      return cellRendererBalanceInteger;
   }

   public CellRendererAccountPascal getCellRendererAccountPascal() {
      if (cellRendererAccountPascal == null) {
         cellRendererAccountPascal = new CellRendererAccountPascal(psc);
         registerRenderer(cellRendererAccountPascal);
      }
      return cellRendererAccountPascal;
   }

   public CellRendererBlockMiner getCellRendererBlockMiner() {
      if (cellRendererBlockMiner == null) {
         cellRendererBlockMiner = new CellRendererBlockMiner(psc);
      }
      return cellRendererBlockMiner;
   }

   public CellRendererBlockOp getCellRendererBlockOp() {
      if (cellRendererBlockOp == null) {
         cellRendererBlockOp = new CellRendererBlockOp(psc);
         registerRenderer(cellRendererBlockOp);
      }
      return cellRendererBlockOp;
   }

   public CellRendererRedGreenDouble getCellRendererDouble() {
      if (cellRendererDouble == null) {
         cellRendererDouble = new CellRendererRedGreenDouble(psc);
         registerRenderer(cellRendererDouble);
      }
      return cellRendererDouble;
   }

   public CellRendererKeyName getCellRendererKeyName() {
      if (cellRendererKeyName == null) {
         cellRendererKeyName = new CellRendererKeyName(psc);
      }
      return cellRendererKeyName;
   }

   public CellRendererOpCount getCellRendererOpCount() {
      if (cellRendererOpCount == null) {
         cellRendererOpCount = new CellRendererOpCount(psc);
         registerRenderer(cellRendererOpCount);
      }
      return cellRendererOpCount;
   }

   public CellRendererOpType getCellRendererOpType() {
      if (cellRendererOpType == null) {
         cellRendererOpType = new CellRendererOpType(psc);
         registerRenderer(cellRendererOpType);
      }
      return cellRendererOpType;
   }

   public CellRendererOpTypeSub getCellRendererOpTypeSub() {
      if (cellRendererOpTypeSub == null) {
         cellRendererOpTypeSub = new CellRendererOpTypeSub(psc);
         registerRenderer(cellRendererOpTypeSub);
      }
      return cellRendererOpTypeSub;
   }

   public TableCellRenderer getCellRendererPasaPrice() {
      if (cellRendererPasaPrice == null) {
         cellRendererPasaPrice = new CellRendererPasaPrice();
      }
      return cellRendererPasaPrice;
   }

   public CellRendererTime getCellRendererTime() {
      if (cellRendererTime == null) {
         cellRendererTime = new CellRendererTime(psc);
      }
      return cellRendererTime;
   }

   public Color getLabelForeground() {
      return UIManager.getColor("Label.foreground");
   }

   public Color getTableDropCellBackground() {
      return UIManager.getColor("Table.dropCellBackground");
   }

   public Color getTableDropCellForeground() {
      return UIManager.getColor("Table.dropCellBackground");
   }

   /**
    * 
    * @param color
    * @return
    */
   public Color getSelectedColor(Color color) {
      Color c = psc.getCellRendereManager().getUIBgColor();
      if (c == null) {
         //warning
         //#debug
         toDLog().pNull("getUIBgColor is null. using grey", null, CellRendereManager.class, "getSelectedColor", LVL_05_FINE, true);
         c = Color.gray;
      }
      //mix it
      int nr = (c.getRed() + color.getRed()) / 2;
      int ng = (c.getGreen() + color.getGreen()) / 2;
      int nb = (c.getBlue() + color.getBlue()) / 2;
      //TODO color caching?
      return new Color(nr, ng, nb);
   }

   /**
    * Could be null if no set
    * @return
    */
   public Color getUIBgColor() {
      return UIManager.getColor("Table.selectionBackground");
   }

   public boolean isDarkTheme() {
      return isDarkTheme;
   }

   public void propertyChange(PropertyChangeEvent e) {
      String name = e.getPropertyName();
      if (name.equals("lookAndFeel")) {
         //call them
         eventUpdate();
      }
   }

   private void registerRenderer(PascalTableCellRenderer renderer) {
      renderers.add(renderer);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CellRendereManager");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CellRendereManager");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }
   //#enddebug

}
