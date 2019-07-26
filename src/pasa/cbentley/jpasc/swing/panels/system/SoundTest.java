/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.system;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.account.TabsAccountExplorer;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.jpasc.swing.panels.getstarted.TabsGetStarted;
import pasa.cbentley.jpasc.swing.panels.trade.TabsPoloniex;
import pasa.cbentley.jpasc.swing.panels.wallet.TabsMyAssets;
import pasa.cbentley.jpasc.swing.widgets.SoundTabButton;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.widgets.b.BButton;

public class SoundTest extends PanelTabAbstractPascal implements ActionListener {

   public static final String ID = "soundtest";

   private SoundTabButton     butPloufs;

   private SoundTabButton     butInst;

   private SoundTabButton     butDoit;

   private SoundTabButton     butBye;

   private SoundTabButton     butBells;

   private BButton            butChangeVolume;

   public SoundTest(PascalSwingCtx psc) {
      super(psc, ID);
   }

   public void tabLostFocus() {

   }

   public void tabGainFocus() {

   }

   public void disposeTab() {

   }

   public void initTab() {
      this.setLayout(new BorderLayout());
      //list all sounds as a table
      addRandoms();
   }

   private void addRandoms() {

      
      JPanel south = new JPanel();

      butChangeVolume = new BButton(getSwingCtx(), this);
      butChangeVolume.setIcon("soundtest", "tab", IconFamily.ICON_SIZE_2_MEDIUM);
      butChangeVolume.setTextKey("menu.sound.changevol");
      
      south.add(butChangeVolume);

      JPanel north = new JPanel();

      butPloufs = new SoundTabButton(psc, TabsAccountExplorer.ID, PascalAudio.SOUNDS_PLOUFS);
      butInst = new SoundTabButton(psc, TabsGetStarted.ID, PascalAudio.SOUNDS_INSTRUMENTS);
      butDoit = new SoundTabButton(psc, TabsNodeCenter.ID, PascalAudio.SOUNDS_DOIT);
      butBye = new SoundTabButton(psc, TabsMyAssets.ID, PascalAudio.SOUNDS_BYE);
      butBells = new SoundTabButton(psc, TabsNodeCenter.ID, PascalAudio.SOUNDS_BELLS);

      north.add(butBells);
      north.add(butBye);
      north.add(butDoit);
      north.add(butInst);
      north.add(butPloufs);

      this.add(north, BorderLayout.NORTH);
      this.add(south, BorderLayout.SOUTH);
   }

   public void actionPerformed(ActionEvent e) {

   }

}
