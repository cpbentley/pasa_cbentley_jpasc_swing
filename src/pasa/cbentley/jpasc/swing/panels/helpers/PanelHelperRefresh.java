/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;

/**
 * Track the data validity for block. if the data is loaded and block hasn't changed
 * data will not be automatically refresh unless forced with the button
 * @author Charles Bentley
 *
 */
public class PanelHelperRefresh extends PanelPascal implements ActionListener {

   private BButton             butRefresh;

   private BLabel              labBlock;

   private ICommandableRefresh com;

   private Integer block;

   public PanelHelperRefresh(PascalSwingCtx psc, ICommandableRefresh com) {
      super(psc);
      this.com = com;
      labBlock = new BLabel(sc, "text.validforblock");
      butRefresh = new BButton(sc, this, "but.refresh");
      butRefresh.setIcon("refresh", "action", IconFamily.ICON_SIZE_0_SMALLEST);
      
      this.add(labBlock);
      this.add(butRefresh);
   }

   public void actionPerformed(ActionEvent e) {
      psc.getPCtx().getRPCConnection().forceUpdateLastMinedBlock();
      com.cmdRefresh(this);
   }
   
   public void setRefreshBlock(Integer block) {
      this.block = block;
      labBlock.setKeyParam("{block}", String.valueOf(block));
   }

   public Integer getRefreshBlock() {
      return block;
   }
}
