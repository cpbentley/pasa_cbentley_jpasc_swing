/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.ICallBack;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.jpasc.pcore.interfaces.IBlockListener;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class PascalAudio implements IBlockListener {

   private boolean              isPlaySounds;

   private PascalSwingCtx       psc;

   public static final String[] SOUNDS_BELLS       = new String[] { "/sounds/bells/Cow bell 6 13899.wav", "/sounds/bells/Cow bell 4 13897.wav", "/sounds/bells/Cow bell 17 13910.wav", "/sounds/bells/Cow bell 10 13903.wav", "/sounds/bells/Cow bell 9 13902.wav", };

   public static final String[] SOUNDS_BYE         = new String[] { "/sounds/tchao.ogg", "/sounds/a_demain.ogg" };

   public static final String[] SOUNDS_DOIT        = new String[] { "/sounds/justdoit/do_it.ogg", "/sounds/justdoit/jingle_do_it.ogg", "/sounds/justdoit/just_do_it_2.ogg", "/sounds/justdoit/just_do_it.ogg", "/sounds/justdoit/yes_you_can.ogg" };

   public static final String[] SOUNDS_INSTRUMENTS = new String[] { "/sounds/bells/Flute de pan 8 17323.wav", "/sounds/bells/Flute de pan 47 17362.wav", "/sounds/bells/Harmonica 12 18652.wav" };

   public static final String[] SOUNDS_PLOUFS      = new String[] { "/sounds/plouf1.ogg", "/sounds/plouf2.ogg", "/sounds/plouf3.ogg" };

   public static final String[] SOUNDS_NODE        = new String[] { "/sounds/welcome/eartheater.ogg" };

   public static final String   PREF_SOUND_VOLUME  = "sound_volume";

   public static final String   LETSROCK           = "/sounds/dev/positive/letsrock189.ogg";

   public PascalAudio(PascalSwingCtx psc) {
      this.psc = psc;
      psc.getPCtx().getRPCConnection().addBlockListener(this);
   }

   public boolean isPlaySounds() {
      return isPlaySounds;
   }

   public void setClipVolume(Clip clip) {
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      float gainSlider = psc.getUIPref().getInt(PascalAudio.PREF_SOUND_VOLUME, 60);
      float max = 100;
      float volume = gainSlider / max; //the desired volume in float (0.0f means no sound, 1.0f means full audio) 
      float range = gainControl.getMaximum() - gainControl.getMinimum();
      float gain = (range * volume) + gainControl.getMinimum();
      gainControl.setValue(gain);
   }

   public void setClipVolume(OggClipPascal clip) {
      float gainSlider = psc.getUIPref().getInt(PascalAudio.PREF_SOUND_VOLUME, 60);
      float max = 100;
      float volume = gainSlider / max; //the desired volume in float (0.0f means no sound, 1.0f means full audio) 
      clip.setGain(volume);
   }

   public void playAudioRandom(String[] paths) {
      this.playAudioRandom(paths, null);
   }

   public void playAudioRandom(String[] paths, ICallBack cb) {
      int index = psc.getRandom().nextInt(paths.length);
      playAudio(paths[index], cb);
   }

   public void setEnableAudio(boolean b) {
      isPlaySounds = b;
   }

   /**
    * Look up the key for an audio file.
    * What if sound theme is midi music?
    * 
    * What if sound file is parametrized by state?
    * @param key
    */
   public void playAudioKey(String key) {
      String str = psc.getSwingCtx().getResSound(key);
      if (str != null) {
         playAudio(str);
      }
   }

   /**
    * Statically defined audioID. ala Android R.id
    * @param audioID
    */
   public void playAudioID(int audioID) {
      String str = psc.getSwingCtx().getResSound("");
      if (str != null) {
         playAudio(str);
      }
   }

   public void playAudio(String fileName) {
      this.playAudio(fileName, null);
   }

   /**
    * Callback when file is finished playing or if any exception is thrown
    * @param fileName
    * @param cb
    */
   public void playAudio(String fileName, ICallBack cb) {
      //#debug
      toDLog().pSound("fileName=" + fileName, this, PascalAudio.class, "playAudio", ITechLvl.LVL_04_FINER, true);
      if (!isPlaySounds) {
         if (cb != null) {
            cb.callBack(this);
         }
         return;
      }
      if (fileName.endsWith(".ogg")) {
         playAudioOgg(fileName, cb);
      } else {
         //assume wav for now
         InputStream is = this.getClass().getResourceAsStream(fileName);
         try {
            AudioInputStream aff = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            manageCloseClip(clip, cb, fileName);

            clip.open(aff);
            setClipVolume(clip);
            clip.start();
         } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
      }
   }

   private void manageCloseClip(final OggClipPascal clip, final ICallBack cb, final String name) {
      SoundPlay sp = new SoundPlay(psc, name, clip, cb);
      clip.setLineListener(sp);
   }

   /**
    * 
    * @param clip
    * @param cb
    * @param name
    */
   private void manageCloseClip(final Clip clip, final ICallBack cb, final String name) {
      SoundPlay sp = new SoundPlay(psc, name, clip, cb);
      clip.addLineListener(sp);
   }

   /**
    * Called back when audio file has finished playing (closed)
    * <br>
    * Used by exit sounds running while the frame is no more visible
    */
   public void playAudioOgg(String fileName) {
      playAudioOgg(fileName, null);
   }

   public void playAudioOgg(String fileName, ICallBack cb) {
      try {
         InputStream is = this.getClass().getResourceAsStream(fileName);
         OggClipPascal oggclip = new OggClipPascal(is);
         setClipVolume(oggclip);
         manageCloseClip(oggclip, cb, fileName);
         oggclip.play();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void pingNewBlock(Integer newBlock, long millis) {
      //TODO sounds depends on time to found it. after long period sound is
      //if very fast, runs CarPassing By
      if (millis < 60000) {
         //run a sound clip
         this.playAudio("/sounds/CarPassingBy.wav");
      } else if (millis < 120000) {
         this.playAudio("/sounds/bipbip.wav");
      } else {
         this.playAudio("/sounds/crash_cymbal_5.wav");
      }
   }

   public void pingNoBlock(long millis) {

   }

   public void playAudioRandom(String str) {
      if (str.equals("@BELLS")) {
         playAudioRandom(SOUNDS_BELLS);
      } else if (str.equals("@BYE")) {
         playAudioRandom(SOUNDS_BYE);
      } else if (str.equals("@INSTRUMENTS")) {
         playAudioRandom(SOUNDS_INSTRUMENTS);
      } else if (str.equals("@PLOUFS")) {
         playAudioRandom(SOUNDS_PLOUFS);
      } else {
         //#debug
         toDLog().pSound(str + " not found", null, PascalAudio.class, "playAudioRandom", ITechLvl.LVL_05_FINE, true);
      }

   }

   /**
    * Plays music based
    */
   public void pingNewPendingCount(Integer count, Integer oldCount) {
      if (count != 0) {
         //play a sound depending on the number of pending
         if (count < 5) {
            psc.getAudio().playAudio("/sounds/coin_glass1.wav");
         } else if (count < 10) {
            psc.getAudio().playAudio("/sounds/coin_glass1.wav");
         } else if (count < 10) {
            psc.getAudio().playAudio("/sounds/coin_glass1.wav");
         } else if (count > 60) {
            psc.getAudio().playAudio("/sounds/ohohohoh.ogg");
         }
      }
   }

   public void pingDisconnect() {

   }

   public void pingError() {

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalAudio");
      dc.appendVarWithSpace("isPlaySounds", isPlaySounds);
      dc.appendVarWithSpace("Volume", psc.getUIPref().getInt(PascalAudio.PREF_SOUND_VOLUME, 60));
   }

   public IDLog toDLog() {
      return psc.toDLog();
   }

   public UCtx toStringGetUCtx() {
      return psc.getUCtx();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalAudio");
      dc.appendVarWithSpace("isPlaySounds", isPlaySounds);
      dc.appendVarWithSpace("Volume", psc.getUIPref().getInt(PascalAudio.PREF_SOUND_VOLUME, 60));
   }
   //#enddebug

}
