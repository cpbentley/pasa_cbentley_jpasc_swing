/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.ICommandableNameList;
import pasa.cbentley.jpasc.swing.panels.tools.RegisterNewName;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;

public class PanelHelperNameWallet extends PanelHelperName {

   private BLabel  labEditWarning;

   private BButton butGoToEdit;

   private boolean isAddEditingLink = false;

   public PanelHelperNameWallet(PascalSwingCtx psc, ICommandableNameList refresh) {
      super(psc, refresh);

      labEditWarning = new BLabel(sc, "label.editnamewarning");
      butGoToEdit = new BButton(sc, this, "but.editnamewarning");
      butGoToEdit.setIcon(RegisterNewName.ID, "tab", IconFamily.ICON_SIZE_0_SMALLEST);

      addWidgets();
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butGoToEdit) {
         refresh.cmdGoToEdit();
      } else {
         super.actionPerformed(e);
      }
   }

   public void addWidgets() {
      add(labFilter);
      add(textFilter);
      add(butClear);
      add(cbNoNames);
      add(labEditWarning);
      add(butGoToEdit);
   }

   public boolean isAddEditingLink() {
      return isAddEditingLink;
   }

   public void setAddEditingLink(boolean isAddEditingLink) {
      this.isAddEditingLink = isAddEditingLink;
   }

}
