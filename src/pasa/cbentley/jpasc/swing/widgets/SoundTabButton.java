/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.widgets.b.BButton;

public class SoundTabButton extends BButton implements ActionListener {

   private PascalSwingCtx psc;

   private String         iconID;

   private String[]       sounds;

   private String         sound;

   private boolean        isRandom;

   private int            arrayIndex;

   public SoundTabButton(PascalSwingCtx psc, String iconID, String[] sounds) {
      super(psc.getSwingCtx());
      this.psc = psc;
      this.iconID = iconID;
      if (sounds == null || sounds.length == 0) {
         throw new IllegalArgumentException();
      }
      Icon icon = sc.getResIcon(iconID, "tab", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_0_DEFAULT);
      Icon iconSel = sc.getResIcon(iconID, "tab", IconFamily.ICON_SIZE_2_MEDIUM, IconFamily.ICON_MODE_1_SELECTED);
      normal = icon;
      selected = iconSel;
      this.sounds = sounds;
      this.setIcon(normal);
      this.setSelectedIcon(selected);
      super.addActionListener(this);
   }

   public void setRandomPlay(boolean rand) {
      isRandom = rand;
   }

   public SoundTabButton(PascalSwingCtx psc, String iconID, String sound) {
      super(psc.getSwingCtx());
      if (sound == null) {
         throw new IllegalArgumentException();
      }
      this.psc = psc;
      this.iconID = iconID;
      this.sound = sound;
      super.addActionListener(this);
   }

   public void actionPerformed(ActionEvent e) {
      if (sounds != null) {
         if (isRandom) {
            psc.getAudio().playAudioRandom(sounds);
         } else {
            psc.getAudio().playAudio(sounds[arrayIndex++]);
            if (arrayIndex >= sounds.length) {
               arrayIndex = 0;
            }
         }
      }

      if (sound != null) {
         psc.getAudio().playAudio(sound);
      }
   }

}
