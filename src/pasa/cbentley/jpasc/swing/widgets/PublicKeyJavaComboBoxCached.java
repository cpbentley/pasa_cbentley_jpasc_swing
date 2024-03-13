/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.widgets;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.jpasc.pcore.domain.java.PublicKeyJava;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.ComboModelMapPublicKeyJava;

/**
 * 
 * @author Charles Bentley
 *
 */
public class PublicKeyJavaComboBoxCached extends JComboBox<String> implements IStringable {

   /**
    * 
    */
   private static final long          serialVersionUID = -5076647978402643903L;

   private ComboModelMapPublicKeyJava comboModel;

   private boolean                    isFireEvents     = true;

   private PascalSwingCtx             psc;

   public PublicKeyJavaComboBoxCached(PascalSwingCtx psc, ActionListener al, ComboModelMapPublicKeyJava model) {
      this.psc = psc;
      this.addActionListener(al);
      setModelCombo(model);
   }

   protected void fireActionEvent() {
      if (isFireEvents) {
         super.fireActionEvent();
      }
   }

   public void setModelCombo(ComboModelMapPublicKeyJava model) {
      this.comboModel = model;
      this.setModel(model);
   }

   public ComboModelMapPublicKeyJava getModelKey() {
      return comboModel;
   }

   /**
    * The model is be empty when Jascal is disconnected.
    * 
    * null if no selection
    * @return
    */
   public PublicKeyJava getSelectedKeyJava() {
      PublicKeyJava publicKeyJava = null;
      if (comboModel != null) {
         publicKeyJava = comboModel.getSelectedPublicKeyJava();
      }
      return publicKeyJava;
   }

   public String getSelectedKeyString() {
      if (comboModel != null) {
         return (String) comboModel.getSelectedItem();
      }
      return null;
   }

   public void setNumKeyVisible(int count) {
      this.setMaximumRowCount(count);
   }

   public void setMaxVisible() {
      //strategy
      //see position on screen
      //compute size of one row
      this.setMaximumRowCount(25);
   }

   public boolean setSelectedEncPubKey(String encPubKey) {
      PublicKeyJava pk = comboModel.getKeyFromEncPubKey(encPubKey);
      if (pk != null) {
         setSelectedKeyWithEvent(pk.getName());
         return true;
      }
      return false;
   }

   public boolean setSelectedEncPubKeyNoEvent(String encPubKey) {
      PublicKeyJava pk = comboModel.getKeyFromEncPubKey(encPubKey);
      if (pk != null) {
         setSelectedKeyNoEvent(pk.getName());
         return true;
      }
      return false;
   }

   /**
    * True if selection was made
    * @param publicKeyName
    * @return
    */
   public boolean setSelectedKeyNoEvent(String publicKeyName) {
      int selectedIndex = getSelectedIndex();
      isFireEvents = false;
      setSelectedItem(publicKeyName);
      isFireEvents = true;
      if (selectedIndex != getSelectedIndex()) {
         return true;
      }
      return false;
   }

   public void setSelectedKeyIndexWithEvent(int index) {
      setSelectedIndex(index);
   }

   public void setSelectedKeyWithEvent(String publicKeyName) {
      setSelectedItem(publicKeyName);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PublicKeyJavaComboBox");
      toStringPrivate(dc);
      dc.nlLvl(comboModel, "comboModel");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isFireEvents", isFireEvents);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PublicKeyJavaComboBox");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUC();
   }

   //#enddebug

}
