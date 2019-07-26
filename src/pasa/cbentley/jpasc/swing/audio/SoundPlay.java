/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.audio;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import pasa.cbentley.core.src4.interfaces.ICallBack;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class SoundPlay implements LineListener {

   private String         name;

   private AutoCloseable  clip;

   private PascalSwingCtx psc;

   private ICallBack      cb;

   public SoundPlay(PascalSwingCtx psc, String name, AutoCloseable clip, ICallBack cb) {
      this.psc = psc;
      this.name = name;
      this.clip = clip;
      this.cb = cb;

   }

   public String getName() {
      return name;
   }

   public void update(LineEvent event) {
      //#debug
      //toDLog().pSound(event + " " + name + " " + cb, null, PascalAudio.class, "manageCloseClip", IDLog.LVL_05_FINE, false);

      if (event.getType() == LineEvent.Type.STOP) {
         //#debug
         //toDLog().pSound("Stopping and Closing sound stream for " + name, null, PascalAudio.class, "manageCloseClip", IDLog.LVL_05_FINE, true);
         try {
            clip.close();
         } catch (Exception e) {
            e.printStackTrace();
         }
         if (cb != null) {
            cb.callBack(this);
         }
      }
   }
}
