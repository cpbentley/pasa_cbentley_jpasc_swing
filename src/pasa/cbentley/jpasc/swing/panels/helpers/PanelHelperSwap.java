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
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.utils.DocRefresher;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;
import pasa.cbentley.swing.widgets.b.BLabel;

public class PanelHelperSwap extends PanelPascal implements ActionListener, ITechPascRPC {

   /**
    * 
    */
   private static final long     serialVersionUID = -4294735464730217648L;

   protected BLabel              labFilter;

   protected JTextField          textFilter;

   protected BButton             butClear;

   protected ICommandableRefresh refresh;

   private boolean               isEventDisabled;

   private JComboBox<String>     comboSearchType;

   private BButton               butSearch;

   public PanelHelperSwap(PascalSwingCtx psc, ICommandableRefresh refresh) {
      super(psc);
      this.refresh = refresh;
      String[] array = new String[4];
      int offset = 0;
      array[offset++] = STATUS_TYPE_FOR_SWAP;
      array[offset++] = STATUS_TYPE_FOR_SWAP_ACCOUNT;
      array[offset++] = STATUS_TYPE_FOR_SWAP_COIN;
      array[offset++] = STATUS_TYPE_FOR_SWAP_SALE;

      comboSearchType = new JComboBox<String>(array);
      comboSearchType.addActionListener(this);

      int indexType = psc.getPascPrefs().getInt(IPrefsPascalSwing.UI_EXPLORER_SWAP_TYPE_SEARCH, 0);
      indexType = Math.min(indexType, array.length - 1);
      comboSearchType.setSelectedIndex(indexType);

   }

   public void buildUI() {
      addWidgets();
   }

   /**
    * Default adds
    */
   public void addWidgets() {
      add(comboSearchType);
      add(butSearch);
   }

   public void setOnlyEmptyNamesNoEvent(boolean b) {
      isEventDisabled = true;
      isEventDisabled = false;
   }

   public String getSwapSearchType() {
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
      } else if (src == comboSearchType) {
         cmdRefresh();
      } else if (src == butSearch) {
         //will read the name parameters
         refresh.cmdRefresh(this);
      }
   }

   protected void cmdRefresh() {
      if (refresh != null) {
         refresh.cmdRefresh(this);
      }
   }
}
