/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ICommandableNameList;
import pasa.cbentley.jpasc.swing.utils.DocRefresher;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;
import pasa.cbentley.swing.widgets.b.BLabel;

public abstract class PanelHelperName extends PanelPascal implements ActionListener {

   protected BCheckBox            cbNameEdition;

   protected BCheckBox            cbNoNames;

   private boolean                defaultNoNames;

   protected BLabel               labFilter;

   protected JTextField           textFilter;

   protected BButton              clear;

   protected ICommandableNameList refresh;

   private DocRefresher docRefresher;

   private boolean isEventDisabled;

   public PanelHelperName(PascalSwingCtx psc, ICommandableNameList refresh) {
      super(psc);
      this.refresh = refresh;
      docRefresher = new DocRefresher(psc.getSwingCtx(), refresh);
      cbNameEdition = new BCheckBox(sc, this, "cb.nameediting");

      cbNoNames = new BCheckBox(sc, this, "cb.nonames");
      cbNoNames.setSelected(isDefaultNoNames());

      labFilter = new BLabel(sc, "text.namecontains");

      textFilter = new JTextField(15);
      textFilter.getDocument().addDocumentListener(docRefresher);

      clear = new BButton(sc, this, "but.clear");
   }

   public boolean isDefaultNoNames() {
      return defaultNoNames;
   }
   
   /**
    * Default adds
    */
   public void addWidgets() {
      add(labFilter);
      add(textFilter);
      add(clear);
      add(cbNoNames);
   }

   public void setOnlyEmptyNames(boolean b) {
      cbNoNames.setSelected(b);
   }

   public void setOnlyEmptyNamesNoEvent(boolean b) {
      isEventDisabled = true;
      cbNoNames.setSelected(b);
      isEventDisabled = false;
   }
   public boolean isOnlyEmptyNames() {
      return cbNoNames.isSelected();
   }
   
   public String getFilterNameString() {
      String filter = textFilter.getText();
      if(filter == null || filter.equals("")) {
         return null;
      } else {
         return filter;
      }
   }
   
   public void actionPerformed(ActionEvent e) {
      if(isEventDisabled) {
         return;
      }
      Object src = e.getSource();
      if (src == clear) {
         textFilter.setText("");
         //do not update here because the DocumentListener will update 
      } else if (src == cbNoNames) {
         cmdRefresh();
      }
   }

   protected void cmdRefresh() {
      if (refresh != null) {
         refresh.cmdRefresh(this);
      }
   }
}
