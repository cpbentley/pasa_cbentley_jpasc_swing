/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.jpasc.pcore.rpc.model.KeyType;
import pasa.cbentley.jpasc.pcore.rpc.model.PublicKey;
import pasa.cbentley.jpasc.swing.ctx.IEventsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;

public class PanelHelperKeyCreator extends PanelPascal implements ActionListener, IEventsPascalSwing {

   private JComboBox<KeyType>     jcomboKeyType;

   private JTextField             textKeyName;

   private BLabel                 labKeyName;

   private BButton                butCreateNewKey;

   public PanelHelperKeyCreator(PascalSwingCtx psc) {
      super(psc);
      init(this);
   }

   
   public void init(JPanel panel) {
      butCreateNewKey = new BButton(psc.getSwingCtx(), this, "but.newkey");
      labKeyName = new BLabel(psc.getSwingCtx(), "label.newkey");
      textKeyName = new JTextField(25);

      KeyType[] ar = new KeyType[] { KeyType.SECP256K1, KeyType.SECP283K1, KeyType.SECP384R1, KeyType.SECP521R1 };
      jcomboKeyType = new JComboBox<KeyType>(ar);
      jcomboKeyType.setSelectedIndex(0);

      panel.add(labKeyName);
      panel.add(textKeyName);
      panel.add(jcomboKeyType);
      panel.add(butCreateNewKey);
   }

   private void cmdCreateNewKey() {
      KeyType ktype = (KeyType) jcomboKeyType.getSelectedItem();
      String txt = textKeyName.getText();
      if (txt == null || txt.equals("")) {
         Object message = "A unused name for the new key is required";
         psc.showMessageErrorForUI(textKeyName, message);
      } else {
         //check if name is not used already
         PublicKey pk = psc.getPascalClient().addNewKey(ktype, txt);
         if (pk != null) {
            psc.getLog().consoleLogGreen(txt +" created (" + pk.getName() + ") = " + pk.getEncPubKey());
            //send event a new key has been created
            BusEvent createEvent = psc.getEventBusPascal().createEvent(PID_6_KEY_LOCAL_OPERATION, EID_6_KEY_LOCAL_OPERATION_1_CREATE, this);
            psc.getEventBusPascal().putOnBus(createEvent);
         }
      }
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butCreateNewKey) {
         cmdCreateNewKey();
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PanelKeyCreator");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelKeyCreator");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
