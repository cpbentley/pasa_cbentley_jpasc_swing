/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;

public class ImageTest extends PanelTabAbstractPascal implements ActionListener {

   public static final String ID = "imagetest";

   private JButton butReload;

   private JButton butDispose;

   public ImageTest(PascalSwingCtx psc) {
      super(psc, ID);
   }

   public void tabLostFocus() {

   }

   public void tabGainFocus() {

   }

   public void disposeTab() {

   }

   public void initTab() {
      //
      butDispose = new JButton("Clear to save memory");
      butReload = new JButton("Reload");
      
      //get all tab ids
      String[] allids = null;
      
      //list all images 16 as a table
   }


   public void actionPerformed(ActionEvent e) {

   }

}
