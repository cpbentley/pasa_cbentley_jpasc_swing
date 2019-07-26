/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.pcore.domain.java.PayloadJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.style.Style;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BRadioButton;
import pasa.cbentley.swing.widgets.b.BTextArea;

/**
 * A panel that allows the user to enter an account.
 * 
 * this panel is used by experts.
 * 
 * The other simpler panel avoid encryption altogether
 * @author Charles Bentley
 *
 */
public class PanelPayload extends JPanel implements DocumentListener, ActionListener {

   private PascalSwingCtx psc;

   private Style          stylePanel;

   private BRadioButton   rbPayloadPublic;

   private BRadioButton   rbPayloadEncryptSenderPublic;

   private BRadioButton   rbPayloadEncryptDestPublic;

   private BRadioButton   rbPayloadEncryptPassword;

   private ButtonGroup    groupButton;

   private BTextArea      textArea;

   private BLabel labMessage;

   private BLabel labCharactersLeftTitle;

   private BLabel labCharactersLeft;

   private int heightAnim;

   public PanelPayload(PascalSwingCtx psc) {
      this.psc = psc;
   }

   /**
    * The style of the panel. Dependant on the current style
    */
   public void setStylePanel(Style style) {
      this.stylePanel = style;
   }

   public void setHeightAnim(int height) {
      this.heightAnim = height;
      setHeight(height);
      this.revalidate();
      this.repaint();
   }
   
   public int getHeightAnim() {
      return getPreferredSize().height;
   }
   
   
   public void setHeight(int height) {
      
      //#debug
      psc.toDLog().pFlow("to = "+height, null, PanelPayload.class, "setHeight", ITechLvl.LVL_05_FINE, true);
      
      setPreferredSize(new Dimension(getWidth(), height));
   }

   public PayloadJava getPayloadJava() {
      PayloadJava payloadJava = new PayloadJava(psc.getPCtx());
      return payloadJava;
   }

   public void init() {
      SwingCtx sc = psc.getSwingCtx();

      JPanel panel = this;
      panel.setLayout(new GridBagLayout());
      if (stylePanel != null) {
         stylePanel.applyStyleTo(panel);
      }

      textArea = new BTextArea(sc);
      textArea.setWrapStyleWord(true);
      textArea.setLineWrap(true);
      textArea.getDocument().addDocumentListener(this);

      int r = GridBagConstraints.REMAINDER;

      labMessage = new BLabel(sc, "text.message");

      //contains number of characters left
      labCharactersLeftTitle = new BLabel(sc, "text.charsleft");
      labCharactersLeft = new BLabel(sc);
      
      
      rbPayloadPublic = new BRadioButton(sc,this,"but.encrypt.none");

      //by default set this
      rbPayloadPublic.setSelected(true);

      rbPayloadEncryptPassword = new BRadioButton(sc,this,"but.encrypt.password");
      rbPayloadEncryptDestPublic = new BRadioButton(sc,this,"but.encrypt.receiver");
      rbPayloadEncryptSenderPublic = new BRadioButton(sc,this,"but.encrypt.sender");


      groupButton = new ButtonGroup();
      groupButton.add(rbPayloadPublic);
      groupButton.add(rbPayloadEncryptDestPublic);
      groupButton.add(rbPayloadEncryptPassword);
      groupButton.add(rbPayloadEncryptSenderPublic);
      
      
      int rowTo = 0;
      double weightx = 0.0;

      //text area takes the whole height
      
      Insets insetsTitle = new Insets(10, 5, 5, 5);

      GridBagConstraints gbc0 = new GridBagConstraints(0, rowTo, r, 1, weightx, weightx, GridBagConstraints.CENTER, GridBagConstraints.NONE, insetsTitle, 0, 0);
      panel.add(labMessage, gbc0);

      
      int rowName = 1;
      GridBagConstraints gbcTextArea = new GridBagConstraints();
      Insets insetsTextField = new Insets(5, 5, 5, 5);
      gbcTextArea.gridx = 1;
      gbcTextArea.gridy = 0;
      
      GridBagConstraints gbc1 = new GridBagConstraints(0, rowName, 1, 1, weightx, weightx, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
      GridBagConstraints gbc2 = new GridBagConstraints(1, rowName, r, 1, 1.0, weightx, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insetsTextField, 0, 0);

      panel.add(textArea, gbcTextArea);

      int rowNumber = 2;

      GridBagConstraints gbc21 = new GridBagConstraints(0, rowNumber, 1, 1, weightx, weightx, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
      GridBagConstraints gbc22 = new GridBagConstraints(1, rowNumber, 1, 1, 1.0, weightx, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insetsTextField, 0, 0);
      GridBagConstraints gbc23 = new GridBagConstraints(2, rowNumber, 1, 1, weightx, weightx, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
      GridBagConstraints gbc24 = new GridBagConstraints(3, rowNumber, 1, 1, weightx, weightx, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

      panel.add(rbPayloadPublic, gbc21);
      panel.add(rbPayloadEncryptPassword, gbc22);
      panel.add(rbPayloadEncryptDestPublic, gbc23);
      panel.add(rbPayloadEncryptSenderPublic, gbc24);

      panel.setMinimumSize(new Dimension(320, 205));

   }

   public void insertUpdate(DocumentEvent e) {

   }

   public void removeUpdate(DocumentEvent e) {

   }

   public void changedUpdate(DocumentEvent e) {

   }

   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      
   }
}
