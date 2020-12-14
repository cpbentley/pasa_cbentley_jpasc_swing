/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import pasa.cbentley.jpasc.pcore.ctx.ITechPascRPC;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ICommandableNameList;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.utils.DocRefresher;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;
import pasa.cbentley.swing.widgets.b.BLabel;

public abstract class PanelHelperName extends PanelPascal implements ActionListener, ITechPascRPC {

   /**
    * 
    */
   private static final long      serialVersionUID = -4294735464730217648L;

   protected BCheckBox            cbNameEdition;

   protected BCheckBox            cbNoNames;

   private boolean                defaultNoNames;

   protected BLabel               labFilter;

   protected JTextField           textFilter;

   protected BButton              butClear;

   protected ICommandableNameList refresh;

   private DocRefresher           docRefresher;

   private boolean                isEventDisabled;

   private JComboBox<String>      comboSearchType;

   private BButton                butSearch;

   public PanelHelperName(PascalSwingCtx psc, ICommandableNameList refresh) {
      super(psc);
      this.refresh = refresh;
      docRefresher = new DocRefresher(psc.getSwingCtx(), refresh);
      cbNameEdition = new BCheckBox(sc, this, "cb.nameediting");

      cbNoNames = new BCheckBox(sc, this, "cb.nonames");
      cbNoNames.setSelected(isDefaultNoNames());

      String[] array = new String[9];
      int offset = 0;
      array[offset++] = NAMESEARCHTYPE_CONTAINS;
      array[offset++] = NAMESEARCHTYPE_ANY;
      array[offset++] = NAMESEARCHTYPE_NONE;
      array[offset++] = NAMESEARCHTYPE_EXACT;
      array[offset++] = NAMESEARCHTYPE_NOT_CONTAINS;
      array[offset++] = NAMESEARCHTYPE_STARTSWITH;
      array[offset++] = NAMESEARCHTYPE_NOT_STARTSWITH;
      array[offset++] = NAMESEARCHTYPE_ENDS_WITH;
      array[offset++] = NAMESEARCHTYPE_NOT_ENDS_WITH;

      comboSearchType = new JComboBox<String>(array);

      int indexType = psc.getPascPrefs().getInt(IPrefsPascalSwing.UI_EXPLORER_NAME_TYPE_SEARCH, 0);
      indexType = Math.min(indexType, array.length - 1);
      comboSearchType.setSelectedIndex(indexType);

      labFilter = new BLabel(sc, "text.namecontains");

      textFilter = new JTextField(15);
      String nameSaved = psc.getPascPrefs().get(IPrefsPascalSwing.UI_EXPLORER_NAME_SEARCH, "");
      if(nameSaved.equals("")) {
         nameSaved = "pasc";
      }
      textFilter.setText(nameSaved);

      butClear = new BButton(sc, this, "but.clear");

      butSearch = new BButton(sc, this, "but.search");
   }

   public boolean isDefaultNoNames() {
      return defaultNoNames;
   }

   public void buildUI() {
      addWidgets();
   }

   /**
    * Default adds
    */
   public void addWidgets() {
      add(butClear);
      add(labFilter);
      add(textFilter);
      add(comboSearchType);
      add(butSearch);
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

   public String getNameSearchType() {
      return (String) comboSearchType.getSelectedItem();
   }

   public String getFilterNameString() {
      String filter = textFilter.getText();
      if (filter == null || filter.equals("")) {
         return null;
      } else {
         return filter;
      }
   }

   public void actionPerformed(ActionEvent e) {
      if (isEventDisabled) {
         return;
      }
      Object src = e.getSource();
      if (src == butClear) {
         textFilter.setText("");
         //do not update here because the DocumentListener will update 
      } else if (src == cbNoNames) {
         cmdRefresh();
      } else if (src == butSearch) {
         //will read the name parameters
         psc.getPascPrefs().put(IPrefsPascalSwing.UI_EXPLORER_NAME_SEARCH, textFilter.getText());
         refresh.cmdRefresh(this);
      }
   }

   protected void cmdRefresh() {
      if (refresh != null) {
         refresh.cmdRefresh(this);
      }
   }
}
