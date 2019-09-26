package pasa.cbentley.jpasc.swing.cmds;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.interfaces.IMenuSwing;
import pasa.cbentley.swing.widgets.b.BCMenuItem;
import pasa.cbentley.swing.widgets.b.BMenu;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public class PascalBPopupMenuFactory {

   protected final PascalSwingCtx   psc;

   protected final PascalCmdManager pcm;

   protected final SwingCtx         sc;

   public PascalBPopupMenuFactory(PascalSwingCtx psc) {
      this.psc = psc;
      this.pcm = psc.getCmds();
      this.sc = psc.getSwingCtx();
   }

   public void addKeyMenuItems(IMenuSwing popupMenu, ICommandableKey keyActor) {
      popupMenu.add(new BCMenuItem<ICommandableKey>(sc, keyActor, pcm.getCmdKeyChangeName()));
      popupMenu.addSeparator();
      popupMenu.add(new BCMenuItem<ICommandableKey>(sc, keyActor, pcm.getCmdCopyKeyBase58()));
      popupMenu.add(new BCMenuItem<ICommandableKey>(sc, keyActor, pcm.getCmdCopyKeyEncoded()));
   }

   public void addKeyMenuItemsDefault(IMenuSwing popupMenu, ICommandableKey keyActor) {
      popupMenu.add(new BCMenuItem<ICommandableKey>(sc, keyActor, pcm.getCmdKeyChangeName()));
      popupMenu.addSeparator();
      popupMenu.add(new BCMenuItem<ICommandableKey>(sc, keyActor, pcm.getCmdShowKeyAccounts()));
      popupMenu.add(new BCMenuItem<ICommandableKey>(sc, keyActor, pcm.getCmdShowKeyAccountsWin()));
      popupMenu.add(new BCMenuItem<ICommandableKey>(sc, keyActor, pcm.getCmdShowKeyNames()));
      popupMenu.add(new BCMenuItem<ICommandableKey>(sc, keyActor, pcm.getCmdShowKeyNamesWin()));
   }

   /**
    * We define 
    * <li> Show Account Being Sold in Inspector
    * <li> Show Seller Account in Inspector
    */
   public void addAccountSaleMenuItems(IMenuSwing menu, ICommandableAccount accountActor, ICommandableKey keyActor) {

      menu.add(new BCMenuItem<ICommandableAccount>(sc, accountActor, pcm.getCmdShowAccountInInspectorTab()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, accountActor, pcm.getCmdShowAccountSellerInInspectorTab()));
      menu.addSeparator();
      menu.add(new BCMenuItem<ICommandableAccount>(sc, accountActor, pcm.getCmdShowAccountInInspectorWin()));
      menu.add(new BCMenuItem<ICommandableAccount>(sc, accountActor, pcm.getCmdShowAccountSellerInInspectorWin()));
      menu.addSeparator();
      BMenu keyMenu = new BMenu(sc, "menu.account.key");
      addKeyMenuItems(keyMenu, keyActor);
      menu.add(keyMenu);
   }

}
