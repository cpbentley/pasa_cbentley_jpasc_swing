/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.getstarted;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.swing.ctx.IStringsPascalSwing;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BButtonToggle;

public abstract class PanelBigImageAbstract extends PanelTabAbstractPascal implements IMyTab, ActionListener, IMyGui {
   /**
    * 
    */
   private static final long serialVersionUID = 1642672077820486934L;

   private BButtonToggle     butShowFullScreen;

   private JLabel            labBigImage;

   public PanelBigImageAbstract(PascalSwingCtx psc, String id) {
      super(psc, id);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == butShowFullScreen) {
         //create a new frame with this tab in fullscreen over this one
         if (butShowFullScreen.isSelected()) {
            //
            getTabPosition().getParent().cmdTabToBackHistory(this);
            butShowFullScreen.setSelected(false);
         } else {
            getTabPosition().getParent().cmdTabToFrame(this, true);
            butShowFullScreen.setSelected(true);
         }
      }
   }

   public void disposeTab() {

   }

   public abstract Icon getBigIcon();

   /**
    * Called automatically after a Tab has been initialized
    * <br>
    * Whenever 
    */
   public void guiUpdate() {
      //#debug
      toDLog().pFlow("", this, PanelBigImageAbstract.class, "guiUpdate", ITechLvl.LVL_05_FINE, true);

      super.guiUpdate(); //super class already calls on all sub components
      //we don't know the state of the label and its key so when updating w 
      //butShowFullScreen.updateGuiState();
   }

   public void initTab() {
      //#debug
      toDLog().pFlow("", null, PanelBigImageAbstract.class, "initTab", ITechLvl.LVL_05_FINE, true);

      setLayout(new BorderLayout());

      Icon icon = getBigIcon();
      labBigImage = new JLabel(icon);

      JScrollPane sp = new JScrollPane(labBigImage);
      this.add(sp, BorderLayout.CENTER);

      butShowFullScreen = new BButtonToggle(sc);
      butShowFullScreen.setTextKeys(IStringsPascalSwing.FULLSCREEN_GO, IStringsPascalSwing.FULLSCREEN_CANCEL);
      butShowFullScreen.setIcon("fullscreen", "action", IconFamily.ICON_SIZE_1_SMALL);
      butShowFullScreen.addActionListener(this);

      //breaks guiUpdate recursivity
      JPanel north = new JPanel();
      north.setLayout(new FlowLayout());
      north.add(butShowFullScreen);
      north.add(psc.getNewOfficialClickableLabel());
      this.add(north, BorderLayout.NORTH);

   }

   public void tabGainFocus() {
   }

   public void tabLostFocus() {
   }
}