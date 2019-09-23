/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.interfaces;

import pasa.cbentley.core.src4.interfaces.ITech;

public interface ITechPrefsPascalSwing extends ITech {

   /**
    * Use this and concat the id
    */
   public static final String PREF_FRAME_                   = "page_frame_x";

   /**
    * Number of frames containing
    */
   public static final String PREF_FRAME_NUM                = "page_frame_num";

   public static final String PREF_NUM_LAUNCHES             = "num_launches";

   public static final String PREF_PAGE_CHILD               = "pagechild";

   public static final String PREF_PAGE_ROOT                = "pageroot";

   /**
    * return id, 0 for no sounds. 1 for classic sound, 2 for other set
    */
   public static final String PREF_PLAY_SOUND               = "settings.playsound";

   public static final int    PREF_PLAY_SOUND_0_NONE        = 0;

   public static final int    PREF_PLAY_SOUND_1_CLASSIC     = 1;

   public static final int    PREF_PLAY_SOUND_2_MIDI        = 2;

   /**
    * Each tab has a given instrument sound for entry and exit
    */
   public static final int    PREF_PLAY_SOUND_2_INSTRUMENTS = 2;

   // key name of the preference
   public static final String PREF_SS_DIR                   = "ss_dir";

   public static final String PREF_TAB_ICONS                = "settings.tab_icons";

   public static final String PREF_EFFECTS                  = "settings.effects";

   public static final String PREF_MODE                     = "settings.mode";

   public static final String PREF_                         = "settings.mode";

   public static final int    PREF_EFFECTS_0_NONE           = 0;

   public static final int    PREF_EFFECTS_1_ORIGINAL       = 1;

   public static final int    PREF_TAB_ICONS_0_NONE         = 0;

   public static final int    PREF_TAB_ICONS_1_CLASSIC      = 1;

   public static final int    PREF_TAB_ICONS_2_GEMS         = 2;

   public static final String UI_BLOCK_NUMBER               = "ui.block";

   public static final String UI_WHALE_PRICE                = "ui.whale.price";

   public static final String PREFS_CELL_EFFECT             = "celleffects";

   public static final String PREFS_PRIVATE_CTX             = "private.ctx";

   public static final String UI_ACCOUNT_NUMBER             = "ui.ops.account";

   public static final String UI_PK_STRING                  = "ui.pk.search";

   public static final String UI_EXPLORER_ACCOUNT           = "ui.accountexplorer";

   public static final String PREF_DEBUG_TRANSLATION        = "lang.debug";

   /**
    * Auto refresh when table is not empty
    */
   public static final String PREF_GLOBAL_MANUAL_REFRESH    = "global.manual.refresh";

}
