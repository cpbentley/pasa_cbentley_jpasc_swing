/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.getstarted;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IWizardNoob;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.jpasc.swing.panels.trade.BuyPascalCoinPanel;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.layout.RiverScrollable;
import pasa.cbentley.swing.widgets.DropcapLabel;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.slice9.NineSliceScalingButton;
import pasa.dekholm.riverlayout.RiverLayout;

public class PanelGetStartedIntroduction extends PanelTabAbstractPascal implements IMyTab, ActionListener, IMyGui {

   public static final String     ID               = "getstarted";

   /**
    * 
    */
   private static final long      serialVersionUID = 7113600912635986250L;

   private BButton                butBuyCoins;

   private BButton                butContinue;

   private BButton                butGetFirstPasa;

   private NineSliceScalingButton butReadContents;

   private List<BButton>          butWizards;

   private BLabel                 labIntroTitle;

   private JTextPane              stateText;

   private JTextPane              textFirstPASA;

   public PanelGetStartedIntroduction(PascalSwingCtx psc) {
      super(psc, ID);
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      int index = -1;
      if (src == butBuyCoins) {
         //select the page
         psc.selectPage(psc.getPages().getPageBuyCoins());
      } else if (src == butReadContents) {
         this.psc.getCmds().cmdShowContents();
      } else if (src == butGetFirstPasa) {
         //we don't know where it is
         psc.selectTabPage(TabsGetFirstPasa.ID);
      } else if ((index = butWizards.indexOf(src)) != -1) {
         IWizardNoob wizard = psc.getWizards().get(index);
         wizard.cmdShowNoobWizard(this);
      }
   }

   public void disposeTab() {

   }

   public List<Component> getListButtons() {
      ArrayList<Component> bbs = new ArrayList<Component>();

      int buttonsIconSize = IconFamily.ICON_SIZE_2_MEDIUM;

      //iterate over noob wizards

      List<IWizardNoob> wizards = psc.getWizards();
      butWizards = new ArrayList<>(wizards.size());

      for (IWizardNoob wizard : wizards) {
         BButton butNoobWizardStart = new BButton(sc, this, wizard.getStringKeyTitle());
         butNoobWizardStart.setIcon(wizard.getID(), "tab", buttonsIconSize);
         butWizards.add(butNoobWizardStart);
      }

      butGetFirstPasa = new BButton(sc, this, "but.introstart.firstpasa");
      butGetFirstPasa.setIcon(TabsGetFirstPasa.ID, "tab", buttonsIconSize);

      butBuyCoins = new BButton(sc, this, "but.introstart.buycoins");
      butBuyCoins.setIcon(BuyPascalCoinPanel.ID, "tab", buttonsIconSize);

      BufferedImage bi = null;
      try {
         bi = ImageIO.read(getClass().getResourceAsStream("/icons/any/bluebutton.png"));
      } catch (IOException ex) {
         ex.printStackTrace();
      }

      butReadContents = new NineSliceScalingButton(sc, this, "but.introstart.help", bi);
      butReadContents.setTBLR(8, 8, 8, 8);
      butReadContents.setIcon("system", "tab", buttonsIconSize);

      for (BButton butWizard : butWizards) {
         bbs.add(butWizard);
      }
      bbs.add(butGetFirstPasa);
      bbs.add(butBuyCoins);
      bbs.add(butReadContents);
      return bbs;
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

   public void initTab() {
      setLayout(new BorderLayout());

      RiverScrollable rs = new RiverScrollable(sc);

      DropcapLabel lab1 = new DropcapLabel(sc, "noob.entryticket");
      rs.raddBr(lab1);

      DropcapLabel lab2 = new DropcapLabel(sc, "noob.accountscoded");
      rs.raddBr(lab2);

      DropcapLabel lab3 = new DropcapLabel(sc, "noob.publickeys");
      rs.raddBr(lab3);

      JPanel left = new JPanel();
      BoxLayout boxLeft = new BoxLayout(left, BoxLayout.Y_AXIS);
      left.setLayout(boxLeft);

      List<Component> listButtons = getListButtons();
      for (Component button : listButtons) {
         left.add(button);
      }

      this.add(rs, BorderLayout.CENTER);
      this.add(left, BorderLayout.WEST);
   }

   public void initTabOld() {
      setLayout(new BorderLayout());

      JPanel container = new JPanel();
      JScrollPane sp = new JScrollPane(container);

      RiverLayout rl = new RiverLayout();
      container.setLayout(rl);

      EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));

      labIntroTitle = new BLabel(sc, "intro.title");

      textFirstPASA = new JTextPane();
      textFirstPASA.setBorder(eb);
      //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      textFirstPASA.setMargin(new Insets(5, 5, 5, 5));

      psc.appendToPane(textFirstPASA, "To get the entry ticket to the Pascal blockchain, you need a Pascal Account or PASA", Color.BLACK);
      psc.appendToPane(textFirstPASA, "\n", Color.BLACK);
      psc.appendToPane(textFirstPASA, "Why?", Color.RED);
      psc.appendToPane(textFirstPASA, " ", Color.BLACK);
      psc.appendToPane(textFirstPASA, "Because Pascal accounts are hardcoded in the blockchain similar to EOS accounts", Color.BLACK);
      psc.appendToPane(textFirstPASA, "\n", Color.BLACK);
      psc.appendToPane(textFirstPASA, "How? ", Color.RED);
      psc.appendToPane(textFirstPASA, "\n", Color.BLACK);
      psc.appendToPane(textFirstPASA, "1) Buy a few Pascal Coin and send them to getpasa.com. It's a special exchange to buy PASAs with Pascal coins. ", Color.BLACK);
      psc.appendToPane(textFirstPASA, "\n", Color.BLACK);
      psc.appendToPane(textFirstPASA, "2)Join the discord or telegram channel and ask the bot for a free pasa ", Color.BLACK);
      psc.appendToPane(textFirstPASA, "\n", Color.BLACK);
      psc.appendToPane(textFirstPASA, "\n", Color.BLACK);
      psc.appendToPane(textFirstPASA, "In all cases, you need to provide a public key given by the wallet", Color.BLACK);
      psc.appendToPane(textFirstPASA, "\n", Color.BLACK);
      psc.appendToPane(textFirstPASA, "Your wallet may contain several public keys. Click on the buttons below", Color.BLACK);

      stateText = new JTextPane();
      stateText.setBorder(eb);
      //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      stateText.setMargin(new Insets(5, 5, 5, 5));
      //https://alvinalexander.com/java/java-uimanager-color-keys-list
      Color panelBg = UIManager.getColor("Panel.background");
      Color panelFg = UIManager.getColor("TextPane.foreground");
      psc.appendToPane(stateText, "This application helps noobs getting started and gives experts the tools to navigate the Pascal Blockchain: \n ", panelFg);

      container.add("br", stateText);

      container.add("br", textFirstPASA);

      for (BButton butWizard : butWizards) {
         container.add("br", butWizard);
      }
      container.add("br", butGetFirstPasa);
      container.add("br", butReadContents);
      container.add("br", butBuyCoins);

      //add the scrollpane and the container
      this.add(sp, BorderLayout.CENTER);
   }

   public void tabGainFocus() {
      this.psc.getAudio().playAudio(PascalAudio.SOUNDS_DOIT[2]);
   }

   public void tabLostFocus() {

   }
}