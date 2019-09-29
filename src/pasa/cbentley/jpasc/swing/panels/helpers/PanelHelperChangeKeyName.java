package pasa.cbentley.jpasc.swing.panels.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.widgets.PanelPascal;
import pasa.cbentley.swing.layout.RiverPanel;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BTextField;

/**
 * Panel for authoring the private/public names of a public key
 * @author Charles Bentley
 *
 */
public class PanelHelperChangeKeyName extends PanelPascal implements ActionListener {

   private String     encPubKey;

   private BLabel     labKeyPrivate;

   private BLabel     labKeyPublic;

   private BTextField textKeyPrivate;

   private BTextField textKeyPublic;

   private BButton    butPrivateToPub;

   private BButton    butPubToPrivate;

   private BButton    butClear;

   public PanelHelperChangeKeyName(PascalSwingCtx psc) {
      super(psc);

      labKeyPrivate = new BLabel(sc, "text.keynameprivate");
      labKeyPublic = new BLabel(sc, "text.keynamepublic");

      butPrivateToPub = new BButton(sc, this, "but.privatetopub");
      butPubToPrivate = new BButton(sc, this, "but.pubtoprivate");
      butClear = new BButton(sc, this, "but.clear");

      textKeyPrivate = new BTextField(sc, 50);
      textKeyPublic = new BTextField(sc, 50);

      RiverPanel riverCenter = new RiverPanel(sc);

      riverCenter.raddBr(labKeyPrivate);
      riverCenter.raddTab(textKeyPrivate);
      
      riverCenter.raddBr(butClear);
      riverCenter.raddTab(butPrivateToPub);
      riverCenter.raddTab(butPubToPrivate);
      
      riverCenter.raddBr(labKeyPublic);
      riverCenter.raddTab(textKeyPublic);

      this.setLayout(new BorderLayout());

      this.add(riverCenter, BorderLayout.CENTER);

   }

   public String getKeyPublic() {
      return textKeyPublic.getText();
   }

   public String getKeyPrivate() {
      return textKeyPrivate.getText();
   }

   public void setKeyPublic(String str) {
      textKeyPublic.setText(str);
   }

   public void setKeyPrivate(String str) {
      textKeyPrivate.setText(str);
   }

   public void setupForKey(String encPubKey) {
      this.encPubKey = encPubKey;
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butClear) {
         textKeyPrivate.setText("");
         textKeyPublic.setText("");
      } else if (src == butPrivateToPub) {
         textKeyPublic.setText(textKeyPrivate.getText());
      } else if (src == butPubToPrivate) {
         textKeyPrivate.setText(textKeyPublic.getText());
      }
   }

}
