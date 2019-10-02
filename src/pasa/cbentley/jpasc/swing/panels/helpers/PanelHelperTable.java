package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.table.abstrakt.TablePanelAbstract;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BCheckBox;

public class PanelHelperTable extends PanelPascal implements ActionListener {

   protected BCheckBox                cbRowNumbers;

   protected BButton                  butFit;

   protected final TablePanelAbstract table;

   private BCheckBox                  cbTaskKeptFocusOut;

   public PanelHelperTable(PascalSwingCtx psc, TablePanelAbstract table) {
      super(psc);
      this.table = table;
      butFit = new BButton(sc, this, "but.fit");

      cbRowNumbers = new BCheckBox(sc, this, "cb.rownumber");

      cbTaskKeptFocusOut = new BCheckBox(sc, this, "cb.keeptaskwhentablosefocus");

      this.add(cbRowNumbers);
      this.add(butFit);
      this.add(cbTaskKeptFocusOut);
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == cbRowNumbers) {
         cmdToggleRow();
      } else if (src == butFit) {
         table.cmdFit();
      }
   }

   public boolean isTaskKeptWhenFocusOut() {
      return cbTaskKeptFocusOut.isSelected();
   }
   
   public void cmdToggleRow() {
      //            JOptionPane jop = new JOptionPane();
      //            jop.setMessageType(JOptionPane.PLAIN_MESSAGE);
      //            jop.setMessage("Please wait...");
      //            JDialog dialog = jop.createDialog(null, "Message");
      //            dialog.setVisible(true);

      table.cmdToggleRowHeader();

      //            dialog.dispose();

      //#debug
      toDLog().pFlow("dialog diposed ", null, TablePanelAbstract.class, "actionPerformed", LVL_05_FINE, true);

   }
}
