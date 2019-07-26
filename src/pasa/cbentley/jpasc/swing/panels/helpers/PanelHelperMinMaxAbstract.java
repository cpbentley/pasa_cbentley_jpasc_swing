/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.utils.DocRefresher;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BTextField;

public abstract class PanelHelperMinMaxAbstract extends PanelPascal implements ActionListener {

   protected BButton           clear;

   private DocRefresher        docRefresher;

   /**
    * return null if empty, otherwise returns 0
    */
   private boolean             isNullIfEmpty;

   private String              keyFor;

   protected BLabel            labMax;

   protected BLabel            labMin;

   private ICommandableRefresh refresh;

   protected BTextField        textMax;

   protected BTextField        textMin;

   public PanelHelperMinMaxAbstract(PascalSwingCtx psc, ICommandableRefresh refresh, String keyFor) {
      super(psc);
      this.refresh = refresh;
      docRefresher = new DocRefresher(sc, refresh);
      this.keyFor = keyFor;

      labMin = new BLabel(sc, "text.min");
      labMin.setKeyTip("text.min.tip." + keyFor);

      labMax = new BLabel(sc, "text.max");
      labMin.setKeyTip("text.max.tip." + keyFor);

      this.setLayout(new FlowLayout(FlowLayout.LEADING));

      textMin = new BTextField(sc, 6, (ActionListener) this, docRefresher);
      psc.setDoubleFilter(textMin);

      this.add(labMin);
      this.add(textMin);

      textMax = new BTextField(sc, 8, (ActionListener) this, docRefresher);
      psc.setDoubleFilter(textMax);

      this.add(labMax);
      this.add(textMax);

      clear = new BButton(sc, this, "but.clear");

      this.add(clear);
      this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
   }

   public String getDefaultMin() {
      return psc.getPascPrefs().get(getPrefKeyMin(), "");
   }

   private String getPrefKeyMin() {
      return keyFor + ".min";
   }

   public String getDefaultMax() {
      return psc.getPascPrefs().get(getPrefKeyMax(), "");
   }

   private String getPrefKeyMax() {
      return keyFor + ".max";
   }

   public void setNullIfEmpty() {
      isNullIfEmpty = true;
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == clear) {
         textMax.setText("");
         textMin.setText("");
         cmdRefresh();
      } else if (e.getSource() == textMax || e.getSource() == textMin) {
         cmdRefresh();
      }
   }

   private void cmdRefresh() {
      psc.getPascPrefs().put(getPrefKeyMax(), textMax.getText());
      psc.getPascPrefs().put(getPrefKeyMin(), textMin.getText());
      if (refresh != null) {
         refresh.cmdRefresh(this);
      }
   }

   public boolean isNullIfEmpty() {
      return isNullIfEmpty;
   }

   /**
    * Generates a refresh event.
    * @param value
    */
   public void setMax(String value) {
      textMax.setText(value);
   }

   /**
    * Generates a refresh event.
    * @param value
    */
   public void setMin(String value) {
      textMin.setText(value);
   }

   public void setNullIfEmpty(boolean isNullIfEmpty) {
      this.isNullIfEmpty = isNullIfEmpty;
   }

}
