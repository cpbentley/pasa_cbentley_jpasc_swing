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

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.interfaces.IStringPrefIDable;
import pasa.cbentley.swing.utils.DocRefresher;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BTextField;

public abstract class PanelHelperMinMaxAbstract extends PanelPascal implements ActionListener, ICommandableRefresh {

   /**
    * 
    */
   private static final long   serialVersionUID = 4738072195145775615L;

   protected BButton           clear;

   private DocRefresher        docRefresher;

   /**
    * return null if empty, otherwise returns 0
    */
   private boolean             isNullIfEmpty;

   private String              keyRoot;

   protected BLabel            labMax;

   protected BLabel            labMin;

   private ICommandableRefresh refresh;

   protected BTextField        textMax;

   protected BTextField        textMin;

   private IStringPrefIDable   idable;

   public PanelHelperMinMaxAbstract(PascalSwingCtx psc, ICommandableRefresh refresh, String keyRoot, IStringPrefIDable idable) {
      super(psc);
      this.refresh = refresh;
      this.idable = idable;
      this.keyRoot = keyRoot;
      docRefresher = new DocRefresher(sc, this);

      labMin = new BLabel(sc, "text.min");
      labMin.setKeyTip("text.min.tip." + keyRoot);

      labMax = new BLabel(sc, "text.max");
      labMax.setKeyTip("text.max.tip." + keyRoot);

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

      String prefKeyMax = getPrefKeyMax();
      String prefKeyMin = getPrefKeyMin();

      String maxText = psc.getPascPrefs().get(prefKeyMax, "");
      String minText = psc.getPascPrefs().get(prefKeyMin, "");

      textMax.setText(maxText);
      textMin.setText(minText);
   }

   public String getDefaultMin() {
      return psc.getPascPrefs().get(getPrefKeyMin(), "");
   }

   private String getPrefKeyMin() {
      return sc.buildStringUISerial(idable.getSelectorKeyPrefID(), ".", keyRoot, ".min");
   }

   public String getDefaultMax() {
      return psc.getPascPrefs().get(getPrefKeyMax(), "");
   }

   private String getPrefKeyMax() {
      return sc.buildStringUISerial(idable.getSelectorKeyPrefID(), ".", keyRoot, ".max");
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

   /**
    * The source has new content. Refresh
    * @param source
    */
   public void cmdRefresh(Object source) {
      cmdRefresh();
   }

   private void cmdRefresh() {
      String prefKeyMax = getPrefKeyMax();
      String maxText = textMax.getText();
      String minText = textMin.getText();
      String prefKeyMin = getPrefKeyMin();

      psc.getPascPrefs().put(prefKeyMax, maxText);
      psc.getPascPrefs().put(prefKeyMin, minText);
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

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelHelperMinMaxAbstract");
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nl();
      dc.appendVarWithSpace("isNullIfEmpty", isNullIfEmpty);
      dc.appendVarWithSpace("keyFor", keyRoot);
      String prefKeyMin = getPrefKeyMin();
      String prefKeyMax = getPrefKeyMax();
      dc.appendVarWithSpace("prefKeyMin", prefKeyMin);
      dc.appendVarWithSpace("prefKeyMax", prefKeyMax);

      dc.nlLvl(refresh, "ICommandableRefresh");
      dc.nlLvl(docRefresher, "docRefresher");

   }

   private void toStringPrivate(Dctx dc) {
      String maxText = textMax.getText();
      String minText = textMin.getText();
      dc.appendVarWithSpace("minText", minText);
      dc.appendVarWithSpace("maxText", maxText);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelHelperMinMaxAbstract");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
