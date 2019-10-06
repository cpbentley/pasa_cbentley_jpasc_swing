/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.system;

import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;
import pasa.cbentley.swing.panels.PreferenceTab;

public class TabsNodeCenter extends TabbedBentleyPanel {
   public static final String   ID               = "root_node";

   /**
    * 
    */
   private static final long    serialVersionUID = -3523191063614218767L;

   private PanelTabNodeCenter      nodeCenter;

   private ListConnectionsPanel nodeConnections;

   private IRootTabPane         root;

   private PascalSwingCtx       psc;

   private SystemTab            systemTab;

   private SoundTest            soundTest;

   private ImageTest            imageTest;

   private PreferenceTab        preferencesTab;

   private SettingsTab          settingsTab;

   private ToolsTab             toolsTab;

   public TabsNodeCenter(PascalSwingCtx psc, IRootTabPane root) {
      super(psc.getSwingCtx(), ID);
      this.psc = psc;
      this.root = root;
   }

   public void disposeTab() {
      nodeCenter = null;
      nodeConnections = null;
   }

   public void initTabs() {
      nodeCenter = new PanelTabNodeCenter(psc);
      nodeConnections = new ListConnectionsPanel(psc);
      settingsTab = new SettingsTab(psc);
      preferencesTab = new PreferenceTab(psc.getSwingCtx());
      systemTab = new SystemTab(psc.getSwingCtx());
      toolsTab = new ToolsTab(psc);
      soundTest = new SoundTest(psc);
      imageTest = new ImageTest(psc);

      addMyTab(nodeCenter);
      addMyTab(nodeConnections);
      addMyTab(toolsTab);
      addMyTab(settingsTab);
      addMyTab(preferencesTab);
      addMyTab(systemTab);
      addMyTab(imageTest);
      addMyTab(soundTest);

   }

   public void tabGainFocus() {
      super.tabGainFocus();
      psc.getAudio().playAudioRandom(PascalAudio.SOUNDS_NODE);
   }
}