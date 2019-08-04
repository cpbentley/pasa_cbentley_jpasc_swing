/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BTextField;

/**
 * Gives 
 * @author Charles Bentley
 *
 */
public class PanelHelperGoToAccount extends PanelPascal implements ActionListener, DocumentListener {

   /**
    * 
    */
   private static final long   serialVersionUID = -8109049922504841190L;

   private BButton             butGoTo;

   private BLabel              labAccount;

   private ICommandableRefresh com;

   private BTextField          textFieldAccount;

   public PanelHelperGoToAccount(PascalSwingCtx psc, ICommandableRefresh com) {
      super(psc);
      this.com = com;
      labAccount = new BLabel(sc, "text.gotoaccount");
      textFieldAccount = new BTextField(sc, 8, this, this);
      butGoTo = new BButton(sc, this, "but.go");
      butGoTo.setIcon("goto", "action", IconFamily.ICON_SIZE_0_SMALLEST);

      this.add(labAccount);
      this.add(textFieldAccount);
      this.add(butGoTo);
   }

   public void actionPerformed(ActionEvent e) {
      com.cmdRefresh(this);
   }

   public void insertUpdate(DocumentEvent e) {

   }

   public void removeUpdate(DocumentEvent e) {

   }

   public void changedUpdate(DocumentEvent e) {

   }

}
