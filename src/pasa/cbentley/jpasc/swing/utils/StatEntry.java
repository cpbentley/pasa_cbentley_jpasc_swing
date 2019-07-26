/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.utils;

import javax.swing.JTextField;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BLabel;

public class StatEntry {

   private BLabel           label;

   private JTextField       textField;
   
   public BLabel getLabel() {
      return label;
   }

   public JTextField getTextField() {
      return textField;
   }

   public StatEntry(PascalSwingCtx psc, String labelKey, int fieldSize) {
      SwingCtx sc = psc.getSwingCtx();
      label = new BLabel(sc, labelKey);

      textField= new JTextField(fieldSize);
      textField.setEditable(false);

   }
}
