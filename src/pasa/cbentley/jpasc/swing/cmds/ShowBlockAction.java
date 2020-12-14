/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import pasa.cbentley.jpasc.pcore.rpc.model.Block;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.interfaces.IRootTabPane;
import pasa.cbentley.jpasc.swing.panels.table.operation.TablePanelOperationByBlock;

public class ShowBlockAction extends AbstractAction {

   private Block          block;

   private boolean        isNewFrame;

   private PascalSwingCtx psc;

   private IRootTabPane   root;

   public ShowBlockAction(PascalSwingCtx psc, Block block, IRootTabPane root, boolean isNewFrame) {
      this.psc = psc;
      this.block = block;
      this.root = root;
      this.isNewFrame = isNewFrame;
   }

   public void actionPerformed(ActionEvent e) {
      //a command show block
      if (isNewFrame) {
         //TODO this create a new navigation event
         TablePanelOperationByBlock olp = new TablePanelOperationByBlock(psc, root);
         olp.showBlock(block);
         psc.showInNewFrameRelToFrameRoot(olp);
      } else {
         //new navigation event
         root.showBlock(block);
         //showTabPage(listOperationPanel.getTabPage());
      }
   }    

}
