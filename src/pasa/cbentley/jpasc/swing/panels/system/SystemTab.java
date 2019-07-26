/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.system;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;

public class SystemTab extends AbstractMyTab implements ActionListener {

   private TextArea textArea;
   private JButton butRefresh;

   public SystemTab(SwingCtx sc) {
      super(sc, "system");
   }

   public void tabLostFocus() {

   }

   public void tabGainFocus() {

   }

   public void disposeTab() {

   }

   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == butRefresh) {
         fillProps();
      }
   }

   private void fillProps() {
      textArea.setText( System.getProperties().entrySet().stream()
            .map(Object::toString)
            //.map(e -> String.format("\"%s\" = %s", e.getKey(), e.getValue()))
            .collect(Collectors.joining("\n")));
        
   }
   
   public void initTab() {
      this.setLayout(new BorderLayout());

      JPanel north = new JPanel();

      butRefresh = new JButton("Refresh");
      butRefresh.addActionListener(this);
      north.add(butRefresh);

      textArea = new TextArea();
      fillProps();

      this.add(textArea, BorderLayout.CENTER);
      this.add(north, BorderLayout.NORTH);
      
   }

}
