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
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pasa.cbentley.jpasc.pcore.domain.java.AccountJava;
import pasa.cbentley.jpasc.pcore.interfaces.IAccountSelector;
import pasa.cbentley.jpasc.pcore.interfaces.IAccountValidator;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.image.SphereColors;
import pasa.cbentley.swing.image.SphereFactory;
import pasa.cbentley.swing.style.Style;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BTextField;

/**
 * A panel that allows the user to enter an account.
 * <br>
 * {@link PanelSelectAccount} gets a validator, it validates
 * <br>
 * @author Charles Bentley
 *
 */
public class PanelSelectAccount extends JPanel implements DocumentListener, IAccountSelector {

   private PascalSwingCtx    psc;

   private BLabel            labTo;

   private String            keyTitle;

   private BLabel            labToName;

   private BLabel            labToNumber;

   private BLabel            labToDashCk;

   private BLabel            labToCk;

   private BTextField        textToName;

   private BTextField        textToNumber;

   private Style             stylePanel;

   private Style             styleCheckSum;

   /**
    * Can be null
    */
   private IAccountValidator accountValidator;

   private BLabel labPadding;

   public IAccountValidator getAccountValidator() {
      return accountValidator;
   }

   public void setAccountValidator(IAccountValidator accountValidator) {
      this.accountValidator = accountValidator;
   }

   public PanelSelectAccount(PascalSwingCtx psc, String keyTitle) {
      this.psc = psc;
      this.keyTitle = keyTitle;
   }

   /**
    * The style of the panel. Dependant on the current style
    */
   public void setStylePanel(Style style) {
      this.stylePanel = style;
   }

   /**
    * Style of the label that will display the checksum of the account
    */
   public void setStyleCheckSum(Style style) {
      this.styleCheckSum = style;
   }

   public Account getAccount() {
      Integer ac = psc.getIntegerFromTextField(textToNumber);
      if (ac != null) {
         return psc.getPCtx().getPClient().getAccount(ac);
      }
      return null;
   }

   public void init() {
      SwingCtx sc = psc.getSwingCtx();

      JPanel panel = this;
      panel.setLayout(new GridBagLayout());
      if (stylePanel != null) {
         stylePanel.applyStyleTo(panel);
      }

      labTo = new BLabel(sc, keyTitle);

      SphereFactory sp = new SphereFactory();
      SphereColors colors = new SphereColors(sc);
      //colors.random(false);
      BufferedImage sphere = sp.getSphere(150, 100, colors);
      //JLabel sphereLabel = new JLabel(new ImageIcon(sphere)); 

      labToName = new BLabel(sc, "text.name");

      //labToName.setIcon(new ImageIcon(sphere));

      labToNumber = new BLabel(sc, "text.number");
      labToDashCk = new BLabel(sc, "text.ckdash");
      labToCk = new BLabel(sc);
      labToCk.setText("00");
      if (styleCheckSum != null) {
         styleCheckSum.applyStyleTo(labToCk);
      }

      textToName = new BTextField(sc);

      textToName.getDocument().addDocumentListener(this);

      textToNumber = new BTextField(sc);
      textToNumber.setColumns(9);
      textToNumber.getDocument().addDocumentListener(this);

      labPadding = new BLabel(sc);
      
      int r = GridBagConstraints.REMAINDER;

      int rowTo = 0;
      double weighZero = 0.0;

      Insets insetsTitle = new Insets(10, 5, 5, 5);

      GridBagConstraints gbc0 = new GridBagConstraints(0, rowTo, r, 1, weighZero, weighZero, GridBagConstraints.CENTER, GridBagConstraints.NONE, insetsTitle, 0, 0);
      panel.add(labTo, gbc0);

      int rowName = 1;
      Insets insetsTextField = new Insets(5, 5, 5, 5);
      Insets insetsLabel = new Insets(5, 5, 5, 5);
      Insets insetsZero = new Insets(0, 0, 0, 0);
      GridBagConstraints gbc1 = new GridBagConstraints(0, rowName, 1, 1, weighZero, weighZero, GridBagConstraints.LINE_END, GridBagConstraints.NONE, insetsLabel, 0, 0);
      GridBagConstraints gbc2 = new GridBagConstraints(1, rowName, r, 1, 1.0, weighZero, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insetsTextField, 0, 0);

      panel.add(labToName, gbc1);
      panel.add(textToName, gbc2);

      int rowNumber = 2;

      GridBagConstraints gbc21 = new GridBagConstraints(0, rowNumber, 1, 1, weighZero, weighZero, GridBagConstraints.LINE_END, GridBagConstraints.NONE, insetsZero, 0, 0);
      GridBagConstraints gbc22 = new GridBagConstraints(1, rowNumber, 1, 1, weighZero, weighZero, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insetsTextField, 0, 0);
      GridBagConstraints gbc23 = new GridBagConstraints(2, rowNumber, 1, 1, weighZero, weighZero, GridBagConstraints.CENTER, GridBagConstraints.NONE, insetsZero, 0, 0);
      GridBagConstraints gbc24 = new GridBagConstraints(3, rowNumber, 1, 1, weighZero, weighZero, GridBagConstraints.CENTER, GridBagConstraints.NONE, insetsTextField, 0, 0);
      GridBagConstraints gbc25 = new GridBagConstraints(3, rowNumber, 1, 1, 1.0, weighZero, GridBagConstraints.CENTER, GridBagConstraints.NONE, insetsZero, 0, 0);

      panel.add(labToNumber, gbc21);
      panel.add(textToNumber, gbc22);
      panel.add(labToDashCk, gbc23);
      panel.add(labToCk, gbc24);
      panel.add(labPadding, gbc25);

      JSeparator separator = new JSeparator();

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.gridheight = 1;
      gbc.weighty = 1.; //everything else is at zero.. so it will take the left over vertical space
      gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(30, 5, 0, 5);
      panel.add(separator, gbc);
      //      
      panel.setMinimumSize(new Dimension(320, 205));

   }
   private void processDocumentEvent(DocumentEvent e) {
      Object src = e.getDocument();
      if (src == textToName.getDocument()) {
         documentChangeName();
      } else if (src == textToNumber.getDocument()) {
         documentChangeNumber();
      }
   }
   private void documentChangeName() {
      if(accountValidator != null) {
         AccountJava accountJava = accountValidator.getAccount(textToName.getText());
         if(accountJava != null) {
            //set programmatically..disable events
            textToNumber.setText(accountJava.getAccountValue().toString());
         } else {
            showErrorName(textToName.getText());
         }
      }
   }

   private void showErrorName(String text) {
      //TODO inter.. pretty text in bold
      psc.getSwingCtx().getLog().consoleLogDateRed("Name " + text +" is not linked to a valid account");
   }

   private void documentChangeNumber() {
      if(accountValidator != null) {
         Integer account = psc.getIntegerFromTextField(textToNumber);
         AccountJava accountJava = accountValidator.getAccount(textToName.getText());
         if(accountJava != null) {
            //set programmatically..disable events
            textToNumber.setText(accountJava.getAccountValue().toString());
         } else {
            showErrorName(textToName.getText());
         }
      }      
   }

   public void insertUpdate(DocumentEvent e) {

   }

   public void removeUpdate(DocumentEvent e) {

   }

   public void changedUpdate(DocumentEvent e) {
      processDocumentEvent(e);
   }

   public String getAccountName() {
      return textToName.getText();
   }

   public Integer getAccountInteger() {
      return psc.getIntegerFromTextField(textToNumber);
   }
}
