/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.run;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.jpasc.swing.others.PascalSkinManager;

public class RunPascalSkinManager extends RunPascalSwingAbstract {

   /**
    * Simple test method
    * @param args
    */
   public static void main(String[] args) {
      RunPascalSkinManager runner = new RunPascalSkinManager();
      runner.initUIThreadOutside();
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            runner.initUIThreadInside();
         }
      });
   }

   protected void addI18nPascal(List<String> list) {
   }

   protected void initForPrefsPascal(IPrefs prefs) {
   }

   protected void initUIThreadInsideSwing() {
      final PascalSkinManager lfm = new PascalSkinManager(psc);
      lfm.setIconSelected(new Icon() {
         public int getIconHeight() {
            return 16;
         }

         public int getIconWidth() {
            return 16;
         }

         public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.blue);
            g.fillRect(x, y, 16, 16);
         }
      });
      final JFrame jf = new JFrame("Look and Feel Frame");
      jf.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            lfm.prefsSave();
            System.exit(0);
         }
      });
      JMenuBar mb = new JMenuBar();
      mb.add(lfm.getRootMenu());
      JMenu jm = new JMenu("Options");
      final JRadioButtonMenuItem jiUndecorated = new JRadioButtonMenuItem("Undecorated");
      jiUndecorated.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            boolean isDecorated = !jf.isUndecorated();
            boolean isDecoSupport = JFrame.isDefaultLookAndFeelDecorated();
            System.out.println("isDecorated=" + isDecorated + " isDecoSupport=" + isDecoSupport);
            jf.dispose();
            jf.setUndecorated(true);
            jf.setVisible(true);
         }
      });
      final JRadioButtonMenuItem jiDecorated = new JRadioButtonMenuItem("Decorated");
      jiDecorated.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            boolean isUndecorated = jf.isUndecorated();
            if (isUndecorated) {
               jf.dispose();
               jf.setUndecorated(false);
               jf.setVisible(true);
            }
         }
      });
      ButtonGroup group = new ButtonGroup();
      group.add(jiDecorated);
      group.add(jiUndecorated);
      jm.add(jiDecorated);
      jm.add(jiUndecorated);

      mb.add(jm);
      jf.setJMenuBar(mb);

      JButton but = new JButton("Hello World!");
      jf.getContentPane().add(but);
      jf.pack();
      jf.setSize(300, 200);
      jf.setLocation(400, 400);
      jf.setVisible(true);

      if (jf.isUndecorated()) {
         jiUndecorated.setSelected(true);
      } else {
         jiDecorated.setSelected(true);
      }
   }

}
