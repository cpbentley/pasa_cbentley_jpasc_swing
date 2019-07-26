/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.whalewatch;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.ITechPrefsPascalSwing;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.imytab.IMyTab;

/**
 * Hardcoded to Poloniex Account
 * @author Charles Bentley
 *
 */
public class PanelWhaleWatchAmountPoloniex extends PanelWhaleWatchPasaAmountSent implements IMyTab {

   private JLabel     labPriceWhale;

   private JTextField textPriceWhale;

   private JButton    butIcon;

   public PanelWhaleWatchAmountPoloniex(PascalSwingCtx psc, IRootTabPane root) {
      super(psc, "whale_poloniex", root);
   }

   public void disposeTab() {
      super.disposeTab();
   }

   /**
    * NULL if ""
    */
   public Double getMinAmount() {
      String val = textPriceWhale.getText();
      if (val == null || val.equals("")) {
         return null;
      }
      Double num = Double.valueOf(val);
      //save to preferences
      getSwingCtx().getPrefs().putDouble(ITechPrefsPascalSwing.UI_WHALE_PRICE, num.doubleValue());
      return num;
   }


   public void initTab() {
      super.initTab();

      JPanel north = new JPanel();
      butIcon = new JButton("Icon", this.getTabIcon(IconFamily.ICON_SIZE_2_MEDIUM));
      north.add(butIcon, 0);

      labPriceWhale = new JLabel("Price Minimum");
      textPriceWhale = new JTextField(10);

      double d = getSwingCtx().getPrefs().getDouble(ITechPrefsPascalSwing.UI_WHALE_PRICE, 100.0d);
      textPriceWhale.setText(String.valueOf(d));

      north.add(labPriceWhale);
      north.add(textPriceWhale);

      super.addNorth(north);
   }

}