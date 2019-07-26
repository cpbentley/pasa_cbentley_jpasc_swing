/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IMyLookUp;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;

/**
 * Re-usable panel for selecting a number of blocks in the past
 * @author Charles Bentley
 *
 */
public class PanelHelperBlockPast extends PanelPascal implements ActionListener {

   private JButton        butRefresh;

   private JComboBox      jcomboDays;

   private JLabel         labBlockInPast;

   private IMyLookUp      owner;

   public String          PAST_1_DAY   = "1 Day";

   public String          PAST_1_MONTH = "1 Month";

   public String          PAST_1_WEEK  = "1 Week";

   public String          PAST_2_DAY   = "2 Day";

   public String          PAST_2_WEEK  = "2 Weeks";

   public String          PAST_3_DAY   = "3 Day";

   public String          PAST_3_MONTH = "3 Months";

   private JTextField     textBlockInPast;


   public PanelHelperBlockPast(PascalSwingCtx psc, IMyLookUp owner) {
      super(psc);
      setLayout(new FlowLayout());
      this.owner = owner;
      String[] array = new String[] { PAST_1_DAY, PAST_2_DAY, PAST_3_DAY, PAST_1_WEEK, PAST_2_WEEK, PAST_1_MONTH };
      jcomboDays = new JComboBox<>(array);
      jcomboDays.addActionListener(this);
      labBlockInPast = new JLabel("#Block in the past");
      textBlockInPast = new JTextField(10);
      textBlockInPast.setText("280");

      psc.setIntFilter(textBlockInPast);

      butRefresh = new JButton("Refresh");
      butRefresh.addActionListener(this);

      add(jcomboDays);
      add(labBlockInPast);
      add(textBlockInPast);
      add(butRefresh);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == jcomboDays) {
         String str = (String) jcomboDays.getSelectedItem();
         if (str == PAST_3_MONTH) {
            textBlockInPast.setText("24360");
         } else if (str == PAST_1_DAY) {
            textBlockInPast.setText("290");
         } else if (str == PAST_2_DAY) {
            textBlockInPast.setText("580");
         } else if (str == PAST_3_DAY) {
            textBlockInPast.setText("870");
         } else if (str == PAST_1_WEEK) {
            textBlockInPast.setText("2030");
         } else if (str == PAST_2_WEEK) {
            textBlockInPast.setText("4060");
         } else if (str == PAST_1_MONTH) {
            textBlockInPast.setText("8120");
         }
         owner.updateTable();
      } else if (e.getSource() == butRefresh) {
         owner.updateTable();
      }
   }

   public Integer getBlockPastInt() {
      return psc.getIntegerFromTextField(textBlockInPast);
   }

}