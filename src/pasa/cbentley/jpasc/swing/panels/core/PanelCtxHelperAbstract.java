package pasa.cbentley.jpasc.swing.panels.core;

import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.widgets.b.BButton;

public abstract class PanelCtxHelperAbstract extends PanelPascal implements ActionListener {
   /**
    * 
    */
   private static final long serialVersionUID = 8148149588776760301L;

   /**
    * Contextual help button. First occurence
    */
   protected BButton           buttonHelpAbout;

   public PanelCtxHelperAbstract(PascalSwingCtx psc) {
      super(psc);

      //the text and icon of this button depends on the context
      buttonHelpAbout = new BButton(sc, this, "");

      this.add(buttonHelpAbout);

   }
   
   public void init() {
      initHelper();
   }
   
   protected abstract void initHelper();

}
