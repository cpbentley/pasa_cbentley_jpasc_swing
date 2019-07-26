/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Timer;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.utils.DateUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.others.AutoLock;

public class PasswordDialog extends JOptionPane implements ComponentListener {

   private PascalSwingCtx psc;

   private JPasswordField jpassword;

   private ButtonGroup buttongroup;

   private JRadioButton radioLock60;

   private JRadioButton radioLock5Min;

   private JRadioButton radioLock1Hour;

   private JRadioButton radioLockNever;

   private JPanel lockOptionsPanel;

   private JLabel labSecurity;

   private Timer timer;

   public PasswordDialog(PascalSwingCtx psc) {
      this.psc = psc;
      this.addComponentListener(this);

      JLabel lab = new JLabel(new Icon() {
         public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
         }

         public int getIconWidth() {
            return 450;
         }

         public int getIconHeight() {
            return 20;
         }
      });
      
      //security reminder
      labSecurity = new JLabel("Choose wisely");
      
      jpassword = new JPasswordField(100);
      buttongroup = new ButtonGroup();
      radioLock60 = new JRadioButton("Lock wallet after 60 seconds");
      radioLock5Min = new JRadioButton("Lock wallet after 5 minutes");
      radioLock1Hour = new JRadioButton("Lock wallet after 1 hour");
      radioLockNever = new JRadioButton("Keep wallet unlocked");
      
      radioLock60.setSelected(true);
      
      
      buttongroup.add(radioLock60);
      buttongroup.add(radioLock5Min);
      buttongroup.add(radioLock1Hour);
      buttongroup.add(radioLockNever);
      
      
      lockOptionsPanel = new JPanel();
      lockOptionsPanel.add(radioLock60);
      lockOptionsPanel.add(radioLock5Min);
      lockOptionsPanel.add(radioLock1Hour);
      lockOptionsPanel.add(radioLockNever);
      

      Object[] data = new Object[] { "Enter password to unlock: ", jpassword, lockOptionsPanel, lab };
      this.setMessage(data);
      this.setMessageType(JOptionPane.QUESTION_MESSAGE);
      this.setOptionType(JOptionPane.OK_CANCEL_OPTION);
      this.setIcon(psc.createImageIcon("/icons/any/lock_red_128.png", ""));
   }

   /**
    * Stop the GUI thread until it returns
    * @param customMessage
    * @param c
    */
   public boolean show(String customMessage, Component c) {
      
      JDialog dialog = this.createDialog(c, customMessage);
      dialog.addComponentListener(this);
      dialog.setVisible(true);
      //continue here when the dialog is closed

      Object value = this.getValue();
      //null when the X is pressed
      if (value == null) {
         return false;
      }
      int val = ((Integer) value).intValue();
      if (val == JOptionPane.OK_OPTION) {
         boolean isSuccess = doOK();
         if(!isSuccess) {
            //ask again until user cancels
            return show("Wrong Password. Failed to Unlock", c);
         }
         return true;
      } else if (val == JOptionPane.CANCEL_OPTION) {
         doCancel();
         return false;
      } else {
         throw new IllegalStateException("Unexpected Option");
      }
   }

   public void componentShown(ComponentEvent e) {
   }

   public void componentResized(ComponentEvent e) {
   }

   public void componentMoved(ComponentEvent e) {
   }

   public void componentHidden(ComponentEvent e) {
 
   }

   private void doCancel() {
      //#debug
      psc.toDLog().pUI("Password dialog canceled", null, PasswordDialog.class, "componentHidden", ITechLvl.LVL_05_FINE, true);
   }

   private boolean doOK() {
      char[] ar = jpassword.getPassword();
      boolean b = psc.unlock(ar);
      for (int i = 0; i < ar.length; i++) {
         ar[i] = ' ';
      }
      jpassword.setText(""); //clear password asap
      if (b) {
         //check if 
         
         if (!radioLockNever.isSelected()) {
            int times = 1;
            if(radioLock5Min.isSelected()) {
               times = 5;
            } else if(radioLock1Hour.isSelected()) {
               times = 60;
            }
            if(timer != null) {
               timer.cancel();
            }
            timer = new Timer();
            timer.schedule(new AutoLock(psc), times * DateUtils.MILLIS_IN_A_MINUTE);
         }
      }
      return b;
   }

}
