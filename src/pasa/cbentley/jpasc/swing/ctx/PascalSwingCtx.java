/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.ctx;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.github.davidbolet.jpascalcoin.api.client.PascalCoinClient;
import com.github.davidbolet.jpascalcoin.api.model.Account;
import com.github.davidbolet.jpascalcoin.api.model.Operation;
import com.github.davidbolet.jpascalcoin.api.model.PayLoadEncryptionMethod;
import com.github.davidbolet.jpascalcoin.api.model.PublicKey;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.core.src4.logging.PreferencesSpyLogger;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.jpasc.pcore.ctx.PCoreCtx;
import pasa.cbentley.jpasc.pcore.interfaces.IBlockListener;
import pasa.cbentley.jpasc.pcore.utils.PascalCoinValue;
import pasa.cbentley.jpasc.pcore.utils.PascalUtils;
import pasa.cbentley.jpasc.swing.audio.PascalAudio;
import pasa.cbentley.jpasc.swing.cellrenderers.CellRendereManager;
import pasa.cbentley.jpasc.swing.cellrenderers.PascalTableCellRenderer;
import pasa.cbentley.jpasc.swing.cmds.PascalBPopupMenuFactory;
import pasa.cbentley.jpasc.swing.cmds.PascalCmdManager;
import pasa.cbentley.jpasc.swing.cmds.PascalPageManager;
import pasa.cbentley.jpasc.swing.interfaces.IHelpManager;
import pasa.cbentley.jpasc.swing.interfaces.ILogin;
import pasa.cbentley.jpasc.swing.interfaces.IPrefsPascalSwing;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.interfaces.ITechUserMode;
import pasa.cbentley.jpasc.swing.interfaces.IWizardNoob;
import pasa.cbentley.jpasc.swing.menu.MenuBarPascalAbstract;
import pasa.cbentley.jpasc.swing.models.ModelProviderPublicJavaKey;
import pasa.cbentley.jpasc.swing.models.ModelProviderPublicJavaKeyPrivate;
import pasa.cbentley.jpasc.swing.models.ModelProviderPublicJavaKeyPublic;
import pasa.cbentley.jpasc.swing.others.PascalSkinManager;
import pasa.cbentley.jpasc.swing.others.PascalValueDefault;
import pasa.cbentley.jpasc.swing.panels.funding.FundingManager;
import pasa.cbentley.jpasc.swing.panels.helpers.PanelHelperChangeKeyName;
import pasa.cbentley.jpasc.swing.tablemodels.bentley.ModelTableOperationAbstract;
import pasa.cbentley.jpasc.swing.utils.PascalSwingUtils;
import pasa.cbentley.jpasc.swing.utils.WalletKeyMapper;
import pasa.cbentley.jpasc.swing.widgets.PasswordDialog;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.color.IntToColor;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.filter.FilterDoubleOrEmpty;
import pasa.cbentley.swing.filter.FilterIntOrEmpty;
import pasa.cbentley.swing.gif.ctx.SwingGifCtx;
import pasa.cbentley.swing.images.ctx.ImgCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.imytab.BackForwardTabPage;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.PageStrings;
import pasa.cbentley.swing.imytab.RootPageManager;
import pasa.cbentley.swing.imytab.TabIconSettings;
import pasa.cbentley.swing.imytab.TabPage;
import pasa.cbentley.swing.skin.main.SwingSkinManager;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * We don't use JascalCtx because we want to decouple branding from the core ui functionalities
 * 
 * <br>
 * PascalSwing is the umbrella under which Jascal and other rebrands of PascalSwing come in the future.
 * 
 * @see UCtx
 * @author Charles Bentley
 *
 */
public class PascalSwingCtx extends ACtx implements ICtx, IEventsPascalSwing {

   private PascalAudio                       audio;

   /**
    * Never null
    */
   private BackForwardTabPage                backForwardManager;

   private StringBBuilder                    blockTimeBuilder;

   private DecimalFormat                     blockTimesFormat = new DecimalFormat("##.##");

   private CellRendereManager                cellRendereManager;

   private final Color                       colorGreen       = new Color(0x228b22);

   private final Color                       colorRed         = new Color(0x8b0000);

   private Color[]                           colorsAccountAge;

   private Color[]                           colorsAccountContiguous;

   private int                               currentMode;

   private PascalValueDefault                defValues;

   private DateFormat                        df;

   private DecimalFormat                     dfCoins;

   private IEventBus                         eventBusPascal;

   private FilterDoubleOrEmpty               filterDouble;

   private FilterIntOrEmpty                  filterInteger;

   private FundingManager                    funders;

   protected final SwingGifCtx               gifc;

   private IHelpManager                      helpManager;

   private Icon                              iconErrorDialog;

   protected final ImgCtx                    imgCtx;

   private IntToColor                        intToColor;

   private boolean                           isPrivateCtx;

   private ILogin                            login;

   private ModelProviderPublicJavaKeyPrivate modelProviderPublicJavaKeyPrivate;

   private PascalPageManager                 pageManager;

   private PanelHelperChangeKeyName          panelHelperChangeKeyName;

   private PascalBPopupMenuFactory           pascalBPopupMenuFactory;

   private PascalCmdManager                  pascalCmdManager;

   private MenuBarPascalAbstract             pascalMenuBar;

   /**
    * Could be null inside this class.
    */
   private SwingSkinManager                  swingSkinManager;

   private PascalSwingUtils                  pascalSwingUtils;

   /**
    * Never null
    */
   private final PCoreCtx                    pc;

   /**
    * Pascal related objects.
    * This file is different than {@link SwingCtx#getPrefs()}
    */
   private IPrefs                            prefs;

   Random                                    r                = new Random();

   private RootPageManager                   rootPageManager;

   private IRootTabPane                      rootPanePrivateAssets;

   private IRootTabPane                      rootRPC;

   /**
    * Should not be null. Must be initialized with {@link PascalSwingCtx}
    */
   private final SwingCtx                    sc;

   private java.io.FileFilter                ssIOFileFilter;

   private FileFilter                        ssSwingFileFilter;

   private SwingBlockEventAdapter            swingBlockEvent;

   /**
    * Fast access value for {@link PascalTableCellRenderer}s
    * 
    * When zero, no coloring effects
    */
   private int                               themeCellEffect  = 1;

   private Object                            walletKeyMapper;

   private JLabel                            websitePascal;

   private ModelProviderPublicJavaKeyPublic  modelProviderPublicJavaKeyPublic;

   /**
    * 
    * @param pc cannot be null
    * @param sc cannot be null
    */
   public PascalSwingCtx(PCoreCtx pc, SwingCtx sc, SwingGifCtx gifc) {
      super(pc.getUCtx());
      this.gifc = gifc;
      this.imgCtx = gifc.getImgCtx();
      this.pc = pc;
      this.sc = sc;

      pascalSwingUtils = new PascalSwingUtils(this);

      blockTimeBuilder = new StringBBuilder(uc);

      setFilterInteger(new FilterIntOrEmpty(sc));
      setFilterDouble(new FilterDoubleOrEmpty(sc));

      pascalCmdManager = new PascalCmdManager(this);
      pascalBPopupMenuFactory = new PascalBPopupMenuFactory(this);

      pageManager = new PascalPageManager(this);

      int[] pascalEventsTopology = new int[PID_ZZ_NUM];
      pascalEventsTopology[PID_2_BLOCK] = EID_2_ZZ_NUM;
      pascalEventsTopology[PID_3_USER_OPERATION] = EID_3_ZZ_NUM;
      pascalEventsTopology[PID_4_WALLET_LOCK] = EID_4_ZZ_NUM;
      pascalEventsTopology[PID_5_CONNECTIONS] = EID_5_ZZ_NUM;
      pascalEventsTopology[PID_6_KEY_LOCAL_OPERATION] = EID_6_ZZ_NUM;
      pascalEventsTopology[PID_7_PRIVACY_CHANGES] = EID_7_ZZ_NUM;
      eventBusPascal = new EventBusArray(getUCtx(), this, pascalEventsTopology);

      eventBusPascal.setExecutor(sc.getSwingExecutor());
      //same for our core pascal context who can't set itself since it doesn not its GUI context
      pc.getEventBusPCore().setExecutor(sc.getSwingExecutor());

      //setup the linkage for handling block events
      swingBlockEvent = new SwingBlockEventAdapter(this);
      pc.getRPCConnection().addBlockListener(swingBlockEvent);
      cellRendereManager = new CellRendereManager(this);
      //build the Navigation System
      rootPageManager = new RootPageManager(sc);

      backForwardManager = new BackForwardTabPage(sc, rootPageManager);

      sc.setIBackForwardable(backForwardManager);

      setIconErrorDialog(createImageIcon("/icons/pasc_icon64.png", ""));

      defValues = new PascalValueDefault(this);

      walletKeyMapper = new WalletKeyMapper(this);

      //
      isPrivateCtx = true;
   }

   /**
    * Adds an {@link IBlockListener} from the AWT Thread
    * @param listener
    */
   public void addBlockListener(IBlockListener listener) {
      swingBlockEvent.addBlockListener(listener);
   }

   public void addI18NKey(List<String> list) {
      list.add("i18nPascalSwing");
   }

   public void appendToPane(JTextPane tp, String msg, Color c) {
      StyleContext sc = StyleContext.getDefaultStyleContext();
      AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

      aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
      aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

      int len = tp.getDocument().getLength();
      tp.setCaretPosition(len);
      tp.setCharacterAttributes(aset, false);
      tp.replaceSelection(msg);
   }

   /**
    * Does not call guiupdate
    * @param themeID
    */
   public void applyIconSettings(int themeID) {
      if (themeID == IPrefsPascalSwing.PREF_TAB_ICONS_0_NONE) {
         sc.setTabIcons(null);
      } else {
         String title = "classic";
         if (themeID == IPrefsPascalSwing.PREF_TAB_ICONS_2_GEMS) {
            title = "gems";
         }
         String[] suffix = new String[6];
         suffix[IconFamily.ICON_SIZE_0_SMALLEST] = "16";
         suffix[IconFamily.ICON_SIZE_1_SMALL] = "32";
         suffix[IconFamily.ICON_SIZE_2_MEDIUM] = "64";
         suffix[IconFamily.ICON_SIZE_3_LARGE] = "128";
         suffix[IconFamily.ICON_SIZE_4_HUGE] = "256";
         suffix[IconFamily.ICON_SIZE_5_BIGGEST] = "512";
         TabIconSettings pascalIcons = new TabIconSettings(sc, title, suffix);
         sc.setTabIcons(pascalIcons);
      }
   }

   public void applyPrefs(IPrefs prefs) {
      int soundMode = prefs.getInt(IPrefsPascalSwing.PREF_PLAY_SOUND, IPrefsPascalSwing.PREF_PLAY_SOUND_1_CLASSIC);
      if (soundMode != IPrefsPascalSwing.PREF_PLAY_SOUND_0_NONE) {
         getAudio().setEnableAudio(true);
      } else {
         getAudio().setEnableAudio(false);
      }

      int tabIconTheme = prefs.getInt(IPrefsPascalSwing.PREF_TAB_ICONS, IPrefsPascalSwing.PREF_TAB_ICONS_1_CLASSIC);
      applyIconSettings(tabIconTheme);

      int themeCellEffect = prefs.getInt(IPrefsPascalSwing.PREFS_CELL_EFFECT, 1);
      this.themeCellEffect = themeCellEffect;

      //saved to prefs when changed
      this.isPrivateCtx = prefs.getBoolean(IPrefsPascalSwing.PREFS_PRIVATE_CTX, true);
   }

   /**
    * 
    * @param dialogTitle
    * @param string
    * @param c determines in which frame/window the dialog is shown
    * @return
    */
   public boolean askToUnlock(String dialogTitle, String string, Component c) {
      PasswordDialog unlock = new PasswordDialog(this);
      return unlock.show(dialogTitle, c);
   }

   public void browseURLDesktop(String url) {
      try {
         Desktop.getDesktop().browse(new URI(url));
      } catch (IOException ex) {
         ex.printStackTrace();
      } catch (URISyntaxException ex) {
         ex.printStackTrace();
      }
   }

   public boolean buyAccount(PublicKey pkNew, Account buyer, Account bought, Double priceAndFunding, Double fee) {
      //#debug
      toDLog().pTest(toString(pkNew, buyer, bought, fee), null, PascalSwingCtx.class, "buyAccount", ITechLvl.LVL_08_INFO, true);

      String newEncPubKey = pkNew.getEncPubKey();
      String newB58PubKey = null;
      Integer buyerAccount = buyer.getAccount();
      byte[] payload = null;
      PayLoadEncryptionMethod payloadMethod = null;
      String pwd = null;
      Double amount = priceAndFunding;
      Account acToBuy = bought;
      Double price = acToBuy.getPrice();
      Integer sellerAccount = acToBuy.getSellerAccount();
      Integer accountToPurchase = acToBuy.getAccount();
      Operation op = getPascalClient().buyAccount(buyerAccount, accountToPurchase, price, sellerAccount, newB58PubKey, newEncPubKey, amount, fee, payload, payloadMethod, pwd);
      if (op != null) {
         getLog().consoleLogGreen("Buy transaction of " + acToBuy.getAccount() + " sent successfull " + op.getTypeDescriptor());
         return true;
      } else {
         getLog().consoleLogGreen("Buy tx failed to be sent");
         return false;
      }
   }

   public boolean buyAccount(String encKey, Account buyer, Account bought, Double priceAndFunding, Double fee) {
      //#debug
      toDLog().pTest(toString(encKey, buyer, bought, fee), null, PascalSwingCtx.class, "buyAccount", ITechLvl.LVL_08_INFO, true);

      String newB58PubKey = null;
      Integer buyerAccount = buyer.getAccount();
      byte[] payload = null;
      PayLoadEncryptionMethod payloadMethod = null;
      String pwd = null;
      Double amount = priceAndFunding;
      Account acToBuy = bought;
      Double price = acToBuy.getPrice();
      Integer sellerAccount = acToBuy.getSellerAccount();
      Integer accountToPurchase = acToBuy.getAccount();
      Operation op = getPascalClient().buyAccount(buyerAccount, accountToPurchase, price, sellerAccount, newB58PubKey, encKey, amount, fee, payload, payloadMethod, pwd);
      if (op != null) {
         getLog().consoleLogGreen("Buy transaction of " + acToBuy.getAccount() + " sent successfull " + op.getTypeDescriptor());
         return true;
      } else {
         getLog().consoleLogGreen("Buy tx failed to be sent");
         return false;
      }
   }

   /**
    * Process exit cmd for this class
    */
   public void cmdExit() {

      if (swingSkinManager != null) {
         swingSkinManager.prefsSave();
      }
      //store current page
      if (backForwardManager.getCurrentTab() != null) {
         TabPage currentPage = backForwardManager.getCurrentTab();

         String[] data = currentPage.getStrings();
         String pref = "";
         for (int i = 0; i < data.length; i++) {
            pref += data[i];
            pref += ';';
         }
         getUIPref().put(IPrefsPascalSwing.PREF_PAGE_ROOT, pref);
      }

   }

   public void copyToClipboard(String value, String title) {
      getLog().consoleLogGreen(title + " copied to clipboard : " + value);
      sc.copyStringToClipboard(value);
   }

   public Image createImage(String path, String description) {
      ImageIcon ii = this.createImageIcon(path, description);
      return sc.getUtils().iconToImage(ii);
   }

   public ImageIcon createImageIcon(String path, String description) {
      return sc.createImageIcon(path, description);
   }

   public Color getAccountAgeColorDark(int age) {
      if (age <= 0) {
         return new Color(ColorUtils.FR_ROUGE_Coquelicot);
      } else if (age == 1) {
         return new Color(ColorUtils.FR_SAUMON_Incarnat);
      } else if (age == 2) {
         return new Color(ColorUtils.FR_ORANGE_Blond);
      } else if (age == 3) {
         return new Color(ColorUtils.FR_ORANGE_Brulee);
      } else if (age == 4) {
         return new Color(ColorUtils.FR_VERT_Amande);
      } else if (age == 5) {
         return new Color(ColorUtils.FR_VERT_Blanc_menthe);
      } else if (age < PascalUtils.BLOCKS_3_YEAR) {
         return new Color(ColorUtils.FR_VERT_Blanc_menthe);
      } else if (age < PascalUtils.BLOCKS_3_YEAR) {
         return new Color(ColorUtils.FR_VERT_Avocat);
      } else if (age < PascalUtils.BLOCKS_3_YEARS_9_MONTH) {
         return new Color(ColorUtils.FR_CYAN_Bleu_azur);
      } else if (age < PascalUtils.BLOCKS_3_YEARS_11_MONTH) {
         return new Color(ColorUtils.FR_CYAN_Bleu_canard);
      } else {
         return new Color(ColorUtils.FR_CYAN_Aigue_marine);
      }
   }

   public Color getAccountAgeColorLight(int age) {
      if (age <= 0) {
         return getColorsAccountAge()[0];
      } else if (age == 1) {
         return getColorsAccountAge()[1];
      } else if (age == 2) {
         return getColorsAccountAge()[2];
      } else if (age == 3) {
         return getColorsAccountAge()[3];
      } else if (age == 4) {
         return getColorsAccountAge()[4];
      } else if (age == 5) {
         return getColorsAccountAge()[5];
      } else if (age < 20) {
         return getColorsAccountAge()[6];
      } else if (age < PascalUtils.BLOCKS_3_YEAR) {
         //colors most of the block
         return getColorsAccountAge()[7];
      } else if (age < PascalUtils.BLOCKS_3_YEARS_9_MONTH) {
         return getColorsAccountAge()[8];
      } else if (age < PascalUtils.BLOCKS_3_YEARS_11_MONTH) {
         return getColorsAccountAge()[9]; //old nearly done
      } else {
         return getColorsAccountAge()[10];
      }
   }

   public Color getAccountColorLight2(int account) {

      int white = 0xFFFFFF;
      return new Color(white - account);
   }

   public Color getAccountContiguousColorLight(int streakCount) {
      if (streakCount <= 1) {
         return getColorsAccountContiguous()[0];
      } else if (streakCount <= 10) {
         return getColorsAccountContiguous()[1];
      } else if (streakCount <= 30) {
         return getColorsAccountContiguous()[2];
      } else if (streakCount <= 50) {
         return getColorsAccountContiguous()[3];
      } else if (streakCount <= 70) {
         return getColorsAccountContiguous()[4];
      } else {
         //70+
         return getColorsAccountContiguous()[5];
      }
   }

   public Integer getAccountNext(Integer account) {
      if (account == null) {
         return 0;
      }
      return account + 1;
   }

   public Integer getAccountPrev(Integer account) {
      if (account == null) {
         return 0;
      }
      if (account != 0) {
         return account - 1;
      }
      return account;
   }

   public PascalCoinValue getAmount(JTextField textAmount) {
      String val = textAmount.getText();
      return getPCtx().getPascValue(val);
   }

   public PascalAudio getAudio() {
      if (audio == null) {
         audio = new PascalAudio(this);
      }
      return audio;
   }

   /**
    * 
    * @return
    */
   public BackForwardTabPage getBackForwardTabPage() {
      return backForwardManager;
   }

   /**
    * Can only be called from the UI Thread.
    * 
    * @param diifUnixTime unit time diff in seconds
    * @return
    */
   public String getBlockTimeUIThread(long diffUnixTime) {
      long millis = diffUnixTime * 1000;
      int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(millis);
      int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes));
      //Formatter fr = new Formatter();
      blockTimeBuilder.append(minutes);
      blockTimeBuilder.append(',');
      int c = blockTimeBuilder.getCount();
      blockTimeBuilder.append(seconds);
      if (c + 1 == blockTimeBuilder.getCount()) {
         blockTimeBuilder.append('0');
      }
      String str = blockTimeBuilder.toString();
      blockTimeBuilder.reset();
      return str;
   }

   public C5Ctx getC5() {
      return sc.getC5();
   }

   public int getCellEffectTheme() {
      return themeCellEffect;
   }

   public CellRendereManager getCellRendereManager() {
      return cellRendereManager;
   }

   public PascalCmdManager getCmds() {
      return pascalCmdManager;
   }

   /**
    * 3 kinds of coloring. 3 roots, 3 aux
    * @param account
    * @return
    */
   public Color getColorDargBgAccount(int account) {
      return new Color(account);
   }

   private Color[] getColorsAccountAge() {
      if (colorsAccountAge == null) {
         colorsAccountAge = new Color[11];
         int index = 0;
         colorsAccountAge[index++] = new Color(ColorUtils.FR_ROUGE_Coquelicot); //0
         colorsAccountAge[index++] = new Color(ColorUtils.FR_SAUMON_Incarnat);
         colorsAccountAge[index++] = new Color(ColorUtils.FR_ORANGE_Blond);
         colorsAccountAge[index++] = new Color(ColorUtils.FR_ORANGE_Brulee); //3
         colorsAccountAge[index++] = new Color(ColorUtils.FR_VERT_Avocat);
         colorsAccountAge[index++] = new Color(ColorUtils.FR_VERT_Amande);
         colorsAccountAge[index++] = new Color(ColorUtils.FR_CYAN_Bleu_azur);
         colorsAccountAge[index++] = new Color(ColorUtils.FR_VERT_Blanc_menthe); // 7
         colorsAccountAge[index++] = new Color(ColorUtils.FR_CYAN_Bleu_canard); //8
         colorsAccountAge[index++] = new Color(ColorUtils.FR_CYAN_Aigue_marine); //9
         colorsAccountAge[index++] = new Color(ColorUtils.FR_BRUN_Alezan); //10
      }
      return colorsAccountAge;
   }

   private Color[] getColorsAccountContiguous() {
      if (colorsAccountContiguous == null) {
         colorsAccountContiguous = new Color[6];
         colorsAccountContiguous[0] = new Color(ColorUtils.FR_BEIGE_Blanc_neige);
         colorsAccountContiguous[1] = new Color(ColorUtils.FR_BEIGE_Amande);
         colorsAccountContiguous[2] = new Color(ColorUtils.FR_JAUNE_Ble);
         colorsAccountContiguous[3] = new Color(ColorUtils.FR_ROSE_Pelure_doignon);
         colorsAccountContiguous[4] = new Color(ColorUtils.FR_ROSE_Passion);
         colorsAccountContiguous[5] = new Color(ColorUtils.FR_VIOLET_Byzantin);
      }
      return colorsAccountContiguous;
   }

   public int getColumnHeaderWidth(JTable table, int column) {

      TableColumn tableColumn = table.getColumnModel().getColumn(column);
      Object value = tableColumn.getHeaderValue();
      TableCellRenderer renderer = tableColumn.getHeaderRenderer();
      if (renderer == null) {
         renderer = table.getTableHeader().getDefaultRenderer();
      }
      Component c = renderer.getTableCellRendererComponent(table, value, false, false, -1, column);
      return c.getPreferredSize().width;
   }

   /**
    * Creates a {@link Date} from a unix time long (seconds)
    * @param unixTime
    * @return
    */
   public Date getDateUnit(long unixTime) {
      return new Date(unixTime * 1000);
   }

   public PascalValueDefault getDefaults() {
      return defValues;
   }

   public Dimension getDimensionFileChooser() {
      Dimension d = new Dimension(800, 700);
      return d;
   }

   public IEventBus getEventBusPascal() {
      return eventBusPascal;
   }

   /**
    * Returns the Utility executor for non GUI tasks.
    * @return
    */
   public ExecutorService getExecutorService() {
      return pc.getExecutorService();
   }

   /**
    * 
    * @return {@link PCoreCtx#getZero()} if null
    */
   public PascalCoinValue getFee(JTextField textFee) {
      String val = textFee.getText();
      return getPCtx().getPascValue(val);
   }

   public FilterDoubleOrEmpty getFilterDouble() {
      return filterDouble;
   }

   public FilterIntOrEmpty getFilterInteger() {
      return filterInteger;
   }

   /**
    * "YYYY/MM/dd - HH:mm:ss" for formating timestamps
    * @return
    */
   public DateFormat getFormatDateTime() {
      if (df == null) {
         df = new SimpleDateFormat("YYYY/MM/dd - HH:mm:ss");
      }
      return df;
   }

   /**
    * "###,###,###.0000" for formating PASC amounts
    * @return
    */
   public DecimalFormat getFormatDecimalCoins() {
      if (dfCoins == null) {
         this.dfCoins = new DecimalFormat("###,###,###.0000");
      }
      return dfCoins;
   }

   public CBentleyFrame getFrameRoot() {
      return sc.getFrameMain();
   }

   public FundingManager getFundingManager() {
      if (funders == null) {
         funders = new FundingManager(this);
      }
      return funders;
   }

   public SwingGifCtx getGifContext() {
      return gifc;
   }

   public Color getGreen() {
      return colorGreen;
   }

   public IHelpManager getHelpManager() {
      return helpManager;
   }

   public Icon getIconErrorDialog() {
      return iconErrorDialog;
   }

   /**
    * Not used now.. maybe later
    * @return
    */
   public Properties getIconProps() {
      Properties propIcons = new Properties();
      InputStream input = null;

      try {
         InputStream is = getClass().getResourceAsStream("/icons/a_icons_classic.properties");
         // load a properties file
         propIcons.load(new InputStreamReader(is, Charset.forName("UTF-8")));

      } catch (IOException ex) {
         ex.printStackTrace();
      } finally {
         if (input != null) {
            try {
               input.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
      return propIcons;
   }

   public ImgCtx getImgCtx() {
      return imgCtx;
   }

   /**
    * Returns null if not a clear number
    * @param tf
    * @return
    */
   public Integer getIntegerFromTextField(JTextField tf) {
      String str = tf.getText();
      if (str != null && !str.equals("")) {
         try {
            return Integer.valueOf(str);
         } catch (NumberFormatException e) {

         }
      }
      return null;
   }

   public IntToColor getIntToColor() {
      return sc.getIntToColor();
   }

   public IUserLog getLog() {
      IUserLog log = sc.getLog();
      //#mdebug
      if (log == null) {
         throw new NullPointerException();
      }
      //#enddebug
      return log;
   }

   public ILogin getLogin() {
      if (login == null) {
         throw new NullPointerException();
      }
      return login;
   }

   public int getMode() {
      return currentMode;
   }

   public ModelProviderPublicJavaKeyPrivate getModelProviderPublicJavaKeyPrivate() {
      if (modelProviderPublicJavaKeyPrivate == null) {
         modelProviderPublicJavaKeyPrivate = new ModelProviderPublicJavaKeyPrivate(this);
      }
      return modelProviderPublicJavaKeyPrivate;
   }

   public ModelProviderPublicJavaKeyPublic getModelProviderPublicJavaKeyPublic() {
      if (modelProviderPublicJavaKeyPublic == null) {
         modelProviderPublicJavaKeyPublic = new ModelProviderPublicJavaKeyPublic(this);
      }
      return modelProviderPublicJavaKeyPublic;
   }

   public JLabel getNewOfficialClickableLabel() {
      BLabel websitePascal = new BLabel(sc);
      websitePascal.setText("<html> <font size=\"13\"> Official Site : <a href=\"\">http://www.pascalcoin.org/</a></font></html>");
      websitePascal.setCursor(new Cursor(Cursor.HAND_CURSOR));
      websitePascal.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            browseURLDesktop("http://www.pascalcoin.org/");
         }
      });
      return websitePascal;
   }

   public PascalPageManager getPages() {
      return pageManager;
   }

   /**
    * Look up the id and returns the first current PageStrings for it.
    * 
    * @param id
    */
   public TabPage getPageStringFirst(String id) {
      return rootPageManager.getPageStringFirst(id);
   }

   public PanelHelperChangeKeyName getPanelHelperChangeKeyName() {
      if (panelHelperChangeKeyName == null) {
         panelHelperChangeKeyName = new PanelHelperChangeKeyName(this);
      }
      return panelHelperChangeKeyName;
   }

   public PascalBPopupMenuFactory getPascalBPopupMenuFactory() {
      return pascalBPopupMenuFactory;
   }

   /**
    * Returns the {@link PascalCoinClient}
    * @return
    */
   public PascalCoinClient getPascalClient() {
      return pc.getPClient();
   }

   /**
    * Sets the selected Icon to /icons/yantra16.png TODO remove hard code
    * Must have been set with {@link PascalSwingCtx#setSwingSkinManager(PascalSkinManager)}
    * It must be created before the creation of any swing components
    * @return
    */
   public SwingSkinManager getPascalSkinManager() {
      return swingSkinManager;
   }

   public PascalSwingUtils getPascalSwingUtils() {
      return pascalSwingUtils;
   }

   public MenuBarPascalAbstract getPascMenuBar() {
      return pascalMenuBar;
   }

   public IPrefs getPascPrefs() {
      return prefs;
   }

   //   public Account getSelectedAccount(ListAccountBasePanel lpane) {
   //      Account ac = null;
   //      int selRow = lpane.getJTable().getSelectedRow();
   //      if (selRow >= 0) {
   //         selRow = lpane.getJTable().convertRowIndexToModel(selRow);
   //         ac = lpane.getTableModel().getRow(selRow);
   //      }
   //      return ac;
   //   }

   public PCoreCtx getPCtx() {
      return pc;
   }

   //   public Operation getSelectedOperation(JTable table, TableModelOperation tableModel) {
   //      int selRow = table.getSelectedRow();
   //      Operation op = null;
   //      if (selRow >= 0) {
   //         selRow = table.convertRowIndexToModel(selRow);
   //         op = tableModel.getRow(selRow);
   //      }
   //      return op;
   //   }
   //
   //   public PublicKey getSelectedPublicKey(ListWalletKeysBasePanel pane) {
   //      int selRow = pane.getJTable().getSelectedRow();
   //      PublicKey pk = null;
   //      if (selRow >= 0) {
   //         selRow = pane.getJTable().convertRowIndexToModel(selRow);
   //         pk = pane.getTableModel().getRow(selRow);
   //      }
   //      return pk;
   //   }
   //
   //   public PublicKeyJava getSelectedPublicKeyA(ListWalletKeyJavaBasePanel pane) {
   //      int selRow = pane.getJTable().getSelectedRow();
   //      PublicKeyJava pk = null;
   //      if (selRow >= 0) {
   //         selRow = pane.getJTable().convertRowIndexToModel(selRow);
   //         pk = pane.getTableModel().getRow(selRow);
   //      }
   //      return pk;
   //   }

   public String getPrettyBytes(long size) {
      String bytes = null;
      if (size > 1000000) {
         bytes = (size / 1000000) + "mb";
      } else if (size > 1000) {
         bytes = (size / 1000) + "kb";
      } else {
         bytes = size + " bytes";
      }
      return bytes;
   }

   public String getPrettyBytes(Long size) {
      if (size == null) {
         return "null";
      } else {
         return getPrettyBytes(size.longValue());
      }
   }

   /**
    * 
    * @return
    */
   public JPopupMenu getProgressPopupMenu() {
      JPopupMenu pmenu = new JPopupMenu();
      return pmenu;
   }

   public Random getRandom() {
      return r;
   }

   public Color getRed() {
      return colorRed;
   }

   public RootPageManager getRootPageManager() {
      return rootPageManager;
   }

   public IRootTabPane getRootPrivateAssets() {
      return rootPanePrivateAssets;
   }

   public IRootTabPane getRootRPC() {
      return rootRPC;
   }

   public Operation getSelectedOperation(JTable table, ModelTableOperationAbstract tableModel) {
      int selRow = table.getSelectedRow();
      Operation op = null;
      if (selRow >= 0) {
         selRow = table.convertRowIndexToModel(selRow);
         op = tableModel.getRow(selRow);
      }
      return op;
   }

   public synchronized java.io.FileFilter getSnapShotFileFilter() {
      if (ssIOFileFilter == null) {
         ssIOFileFilter = new java.io.FileFilter() {

            public boolean accept(File f) {
               if (f.getName().endsWith(".pasc")) {
                  //System.out.println("Found " + f.getAbsolutePath());
                  return true;
               }
               return false;
            }

            public String getDescription() {
               return "File with a .pasc extension";
            }
         };
      }
      return ssIOFileFilter;
   }

   public synchronized FileFilter getSSSwingFileFilter() {
      if (ssSwingFileFilter == null) {
         ssSwingFileFilter = new FileFilter() {

            public boolean accept(File f) {
               if (f.getName().endsWith(".pasc")) {
                  return true;
               }
               return false;
            }

            public String getDescription() {
               return "File with a .pasc extension";
            }
         };
      }
      return ssSwingFileFilter;
   }

   public SwingCtx getSwingCtx() {
      return sc;
   }

   public UCtx getUCtx() {
      return sc.getUCtx();
   }

   public IPrefs getUIPref() {
      return sc.getPrefs();
   }

   double getVersion() {
      String version = System.getProperty("java.version");
      int pos = 0, count = 0;
      for (; pos < version.length() && count < 2; pos++) {
         if (version.charAt(pos) == '.')
            count++;
      }
      String sub = version.substring(0, pos - 1);
      //#debug warn
      //System.out.println(sub);
      try {
         return Double.parseDouble(sub);
      } catch (NumberFormatException e) {
         e.printStackTrace();
      }
      return 0;
   }

   public List<IWizardNoob> getWizards() {
      if (helpManager != null) {
         return helpManager.getWizards();
      }
      return new ArrayList<IWizardNoob>(0);
   }

   public void guiUpdate() {
      sc.guiUpdate();

      if (swingSkinManager != null) {
         swingSkinManager.guiUpdate();
      }

      if (modelProviderPublicJavaKeyPrivate != null) {
         modelProviderPublicJavaKeyPrivate.guiUpdate();
      }
      if (modelProviderPublicJavaKeyPrivate != null) {
         modelProviderPublicJavaKeyPrivate.guiUpdate();
      }
      if (websitePascal != null) {
         //update language here
      }
   }

   public boolean isAudioEnabled() {
      return audio.isPlaySounds();
   }

   public boolean isIgnoreCellEffects() {
      return themeCellEffect == IPrefsPascalSwing.PREF_EFFECTS_0_NONE;
   }

   public boolean isModeAboveNormal() {
      int mode = getMode();
      if (mode == ITechUserMode.MODE_0_DEV || mode == ITechUserMode.MODE_3_EXPERT) {
         return true;
      }
      return false;
   }

   public boolean isModeAboveRookie() {
      int mode = getMode();
      if (mode != ITechUserMode.MODE_1_ROOKIE) {
         return true;
      }
      return false;
   }

   public boolean isModeBelowExpert() {
      return getMode() == ITechUserMode.MODE_1_ROOKIE || getMode() == ITechUserMode.MODE_2_NORMAL;
   }

   public boolean isModeDev() {
      return getMode() == ITechUserMode.MODE_0_DEV;
   }

   public boolean isModeRookie() {
      return getMode() == ITechUserMode.MODE_1_ROOKIE;
   }

   /**
    * Should the UI display private data?
    * 
    * Otherwise UI should hide or display public data
    * 
    * 
    * @return
    */
   public boolean isPrivateCtx() {
      return isPrivateCtx;
   }

   public void openFile(PascalSwingCtx psc, File folder) {
      String version = System.getProperty("java.version");
      double ver = getVersion() * 10;
      if (ver < 18) {

         //get the frame
         JFrame frame = psc.getSwingCtx().getFrameMain();
         JOptionPane.showMessageDialog(frame, "This feature does not work well with this Java version " + version, "", JOptionPane.ERROR_MESSAGE);
         return;
      }

      Runnable r = new Runnable() {

         @Override
         public void run() {
            Desktop desktop = Desktop.getDesktop();
            try {
               //#debug
               System.out.println("Trying to open folder " + folder.getAbsolutePath());
               //bug in JDK7
               desktop.open(folder);
            } catch (IllegalArgumentException iae) {
               //#debug
               System.out.println("File Not Found");
            } catch (IOException e) {
               //#debug
               e.printStackTrace();
            }
         }
      };
      psc.getExecutorService().execute(r);
   }

   public void playTabFocus(AbstractMyTab tab) {
      if (isAudioEnabled()) {
         String str = tab.getTabIntroSound();
         if (str != null && str.length() > 0) {
            char c = str.charAt(0);
            if (c == '@') {
               audio.playAudioRandom(str);
            } else {
               audio.playAudio(str);
            }
         }
      }
   }

   public void playTabFocusLost(AbstractMyTab tab) {
      if (isAudioEnabled()) {
         String str = tab.getTabExitSound();
         if (str != null && str.length() > 0) {
            char c = str.charAt(0);
            if (c == '@') {
               audio.playAudioRandom(str);
            } else {
               audio.playAudio(str);
            }
         }
      }
   }

   public void positionNewFrame(JDialog f) {
      f.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int w = 940;
      int h = 600;
      f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
      f.setSize(w, h);
   }

   /**
    * Creates a new Mapping, returns it.
    * <br>
    * Worker thread returns it
    */
   public Map<String, String> refreshWalletMapping() {
      Map<String, String> ownerWallet = new HashMap<String, String>();
      List<PublicKey> list = getPascalClient().getWalletPubKeys(0, 1000);
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
         PublicKey pk = (PublicKey) iterator.next();
         ownerWallet.put(pk.getEncPubKey(), pk.getName());
      }
      return ownerWallet;
   }

   public void resizeColumnWidth(JTable table) {
      for (int column = 0; column < table.getColumnCount(); column++) {
         resizeColumnWidth(table, column);
      }
   }

   public void resizeColumnWidth(JTable table, int column) {
      final TableColumnModel columnModel = table.getColumnModel();
      int width = getColumnHeaderWidth(table, column) + 4; // Min width
      for (int row = 0; row < table.getRowCount(); row++) {
         TableCellRenderer renderer = table.getCellRenderer(row, column);
         Component comp = table.prepareRenderer(renderer, row, column);
         width = Math.max(comp.getPreferredSize().width + 4, width);
      }
      if (width > 700)
         width = 700;
      columnModel.getColumn(column).setPreferredWidth(width);
   }

   public void resizeColumnWidthNoMax(JTable table) {
      for (int column = 0; column < table.getColumnCount(); column++) {
         resizeColumnWidthNoMax(table, column);
      }
   }

   public void resizeColumnWidthNoMax(JTable table, int column) {
      final TableColumnModel columnModel = table.getColumnModel();
      int width = getColumnHeaderWidth(table, column) + 4; // Min width
      for (int row = 0; row < table.getRowCount(); row++) {
         TableCellRenderer renderer = table.getCellRenderer(row, column);
         Component comp = table.prepareRenderer(renderer, row, column);
         width = Math.max(comp.getPreferredSize().width + 4, width);
      }
      columnModel.getColumn(column).setPreferredWidth(width);
   }

   public void selectPage(PageStrings page) {
      rootPageManager.showTabPageString(page);
   }

   public void selectTabPage(String id) {
      TabPage page = getPageStringFirst(id);
      if (page != null) {
         rootPageManager.showTabPage(page);
      } else {
         throw new IllegalArgumentException("" + id);
      }
   }

   public void setCellEffect(int familyID) {
      themeCellEffect = familyID;
   }

   public void setCellRendereManager(CellRendereManager cellRendereManager) {
      this.cellRendereManager = cellRendereManager;
   }

   public void setDefaultRenderers(JTable table) {
      table.setDefaultRenderer(Date.class, cellRendereManager.getCellRendererTime());
      table.setDefaultRenderer(Double.class, cellRendereManager.getCellRendererDouble());
   }

   public void setDoubleFilter(JTextField jt) {
      PlainDocument doc = (PlainDocument) jt.getDocument();
      doc.setDocumentFilter(getFilterDouble());
   }

   public void setFilterDouble(FilterDoubleOrEmpty filterDouble) {
      this.filterDouble = filterDouble;
   }

   public void setFilterInteger(FilterIntOrEmpty filterInteger) {
      this.filterInteger = filterInteger;
   }

   public void setHelpManager(IHelpManager helpManager) {
      this.helpManager = helpManager;
   }

   public void setIconErrorDialog(Icon iconErrorDialog) {
      this.iconErrorDialog = iconErrorDialog;
   }

   /**
    * 
    * @param jt
    */
   public void setIntFilter(JTextField jt) {
      PlainDocument doc = (PlainDocument) jt.getDocument();
      doc.setDocumentFilter(getFilterInteger());
   }

   public void setLogin(ILogin login) {
      this.login = login;
   }

   public void setMode(int mode) {
      currentMode = mode;
      getPascPrefs().putInt(IPrefsPascalSwing.PREF_MODE, mode);

      if (currentMode == ITechUserMode.MODE_1_ROOKIE) {
         getLog().consoleLog("Mode set to Rookie");
      } else if (currentMode == ITechUserMode.MODE_0_DEV) {
         getLog().consoleLog("Mode set to Developer");
      } else if (currentMode == ITechUserMode.MODE_2_NORMAL) {
         getLog().consoleLog("Mode set to Normal");
      } else if (currentMode == ITechUserMode.MODE_3_EXPERT) {
         getLog().consoleLog("Mode set to Expert");
      }
      //we have to refresh the whole GUI structurally

      //dispose all tabs

      //create new menus for all active frames    

      //apply
   }

   /**
    * Set a skin manager. otherwise get will lazy create one.
    * @param lfModule
    */
   public void setSwingSkinManager(SwingSkinManager lfModule) {
      this.swingSkinManager = lfModule;
   }

   public void setPrefs(IPrefs prefs) {
      this.prefs = prefs;
   }

   public void setPrivateCtx(boolean b) {
      isPrivateCtx = b;
   }

   public void setPrivateRoot(IRootTabPane privateAssets) {
      rootPanePrivateAssets = privateAssets;
   }

   public void setRootRPC(IRootTabPane pane) {
      rootRPC = pane;
   }

   public boolean showHelpFor(String string) {

      if (helpManager == null) {
         //#debug
         toDLog().pFlow("Help Manager is null. string" + string, this, PascalSwingCtx.class, "showHelpFor", LVL_05_FINE, true);
         return false;
      }

      return helpManager.showHelpFor(string, this);
   }

   /**
    * Relative to owner frame
    * @see SwingCtx#showInNewFrame(IMyTab)
    * @param tab
    */
   public void showInNewFrameRelToFrameRoot(IMyTab tab) {
      getSwingCtx().showInNewFrame(tab, getFrameRoot());
   }

   public void showMessageErrorForUI(JComponent c, Object message) {
      JOptionPane.showMessageDialog(c, message, "Error", JOptionPane.ERROR_MESSAGE, getIconErrorDialog());
   }

   //#mdebug
   public IDLog toDLog() {
      return sc.toDLog();
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalSwingCtx");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public String toString(PublicKey pkNew, Account buyer, Account bought, Double fee) {
      StringBBuilder sb = new StringBBuilder(sc.getUCtx());
      sb.append("Account ");
      sb.append(buyer.getAccount());
      sb.append(" buys ");
      sb.append(bought.getAccount());
      sb.append(" for ");
      sb.append(bought.getPrice());
      sb.append(" fee ");
      sb.append(fee.doubleValue());
      sb.append(" sent to ");
      sb.append(pkNew.getName());
      return sb.toString();
   }

   public String toString(String pkNew, Account buyer, Account bought, Double fee) {
      StringBBuilder sb = new StringBBuilder(uc);
      sb.append("Account ");
      sb.append(buyer.getAccount());
      sb.append(" buys ");
      sb.append(bought.getAccount());
      sb.append(" for ");
      sb.append(bought.getPrice());
      sb.append(" fee ");
      sb.append(fee.doubleValue());
      sb.append(" sent to ");
      sb.append(pkNew);
      return sb.toString();
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalSwingCtx");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   /**
    * A String for the whole application.
    * <br>
    * <br>
    * <li>
    * @return
    */
   public String toStringAll() {
      Dctx dc = new Dctx(uc);
      toStringAll(dc);
      sc.toString(dc);
      uc.toString(dc);
      dc.nlLvl(pascalMenuBar, "Main Menu Bar");
      dc.nlLvl(eventBusPascal);
      dc.nlLvl(pascalMenuBar);

      return dc.toString();
   }

   public void toStringAll(Dctx dc) {
      toString(dc);
   }

   public String toStringEventID(int pid, int eid) {
      switch (pid) {
         case IEventsPascalSwing.PID_2_BLOCK:
            switch (eid) {
               case EID_2_BLOCK_0_ANY:
                  return "Any";
               case EID_2_BLOCK_1_NEW_BLOCK:
                  return "NewBlock";
               default:
                  return "Unknonwn EID " + pid + ":" + eid;
            }
         case IEventsPascalSwing.PID_3_USER_OPERATION:
            switch (eid) {
               case EID_3_USER_OPERATION_0_ANY:
                  return "Any";
               case EID_3_USER_OPERATION_1_BEFORE:
                  return "Before";
               case EID_3_USER_OPERATION_2_AFTER:
                  return "After";
               default:
                  return "Unknonwn EID " + pid + ":" + eid;
            }
         case IEventsPascalSwing.PID_4_WALLET_LOCK:
            switch (eid) {
               case EID_4_WALLET_LOCK_0_ANY:
                  return "Any";
               case EID_4_WALLET_LOCK_1_LOCKED:
                  return "Locked";
               case EID_4_WALLET_LOCK_2_UNLOCKED:
                  return "Unlocked";
               default:
                  return "Unknonwn EID " + pid + ":" + eid;
            }
         case IEventsPascalSwing.PID_5_CONNECTIONS:
            switch (eid) {
               case EID_5_CONNECTIONS_0_ANY:
                  return "Any";
               case EID_5_CONNECTIONS_1_CONNECTED:
                  return "Connected";
               case EID_5_CONNECTIONS_2_DISCONNECTED:
                  return "Disconnected";
               default:
                  return "Unknonwn EID " + pid + ":" + eid;
            }
         default:
            return "Unknonwn PID " + pid + ":" + eid;
      }
   }

   private void toStringPrivate(Dctx dc) {

   }
   public int getCtxID() {
      return 433;
   }
   public String toStringProducerID(int pid) {
      switch (pid) {
         case IEventsPascalSwing.PID_2_BLOCK:
            return "Block";
         case IEventsPascalSwing.PID_3_USER_OPERATION:
            return "UserOperation";
         case IEventsPascalSwing.PID_4_WALLET_LOCK:
            return "WalletLock";
         case IEventsPascalSwing.PID_5_CONNECTIONS:
            return "Connections";
         case IEventsPascalSwing.PID_6_KEY_LOCAL_OPERATION:
            return "LocalKeyOp";
         default:
            return "UnknonwnPID:" + pid;
      }
   }
   //#enddebug

   public boolean unlock(char[] ar) {
      //string be will cleared
      boolean b = pc.getRPCConnection().unlock(new String(ar));
      if (b) {
         getLog().consoleLogGreen("Wallet is unlocked");
         return true;
      } else {
         getLog().consoleLogGreen("Wallet fail to unlock! Probably wrong password");
         return false;
      }
   }
}
