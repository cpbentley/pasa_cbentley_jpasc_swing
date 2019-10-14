/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.helpers;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.ComboModelMapPublicKeyJava;
import pasa.cbentley.jpasc.swing.panels.table.abstrakt.TablePanelAbstract;
import pasa.cbentley.swing.cmd.ICommandableRefresh;

/**
 * Panel that displays a Drop down combo box of keys
 * @author Charles Bentley
 *
 */
public class PanelHelperKeyChain extends PanelHelperKeyAbstract {


   public PanelHelperKeyChain(PascalSwingCtx psc, TablePanelAbstract owner, ICommandableRefresh refresh) {
      super(psc,owner,refresh);
   }


   public void buildUI() {
      super.buildUI();
      this.add(labPublicKey);
      //TODO we add several layers of drop down to select keys from the potentially enormous amount
      if(comboKeys != null) {
         this.add(comboKeys);
      }
   }

   /**
    * Favorite keys on chain to watch
    * @return
    */
   protected ComboModelMapPublicKeyJava createModel() {
      return psc.getModelProviderPublicJavaKeyPrivate().createModelPublicKeyJavaWildcards();
   }


}
