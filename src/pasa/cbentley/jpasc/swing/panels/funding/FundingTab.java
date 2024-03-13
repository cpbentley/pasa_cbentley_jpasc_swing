/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.funding;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.jpasc.pcore.rpc.model.Account;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.panels.core.PanelTabAbstractPascal;
import pasa.cbentley.swing.layout.RiverPanel;
import pasa.cbentley.swing.layout.RiverScrollable;
import pasa.cbentley.swing.widgets.CurvesPanel;
import pasa.cbentley.swing.widgets.StackLayout;
import pasa.cbentley.swing.widgets.b.BButton;
import pasa.cbentley.swing.widgets.b.BLabel;
import pasa.cbentley.swing.widgets.b.BPanelGradient;
import pasa.cbentley.swing.widgets.b.BTextArea;

/**
 * Panel that will be shown by tabs not yet implemented.
 * For crowd funded features.
 * 
 * Each feature will have a top 3 PASA that will be advertised by the wallet (including their
 * names) Advertisement possibilities.
 * 
 * <br>
 * Other generous donators will forever be remembered by the historical Pascal chain.
 * <br>
 * <br>
 * The amount required for the feature to be developped and/or released is appended as a suffix in the
 * destination PASA name. The code will be released as open source along side current code.
 * 
 * If you want to contribute please contact Lead Develop to make sure you do not develop a feature that
 * has already been developped.
 * <br>
 * <br>
 * A funding tab is located inside an existing tab
 * @author Charles Bentley
 *
 */
public class FundingTab extends PanelTabAbstractPascal implements ActionListener {

   private Integer     account;

   private String      msg;

   private String      text;

   private BLabel      labFeatureDscr;

   private BButton     butSeeAllFundingRequests;

   private BLabel      labFeatureID;

   private BLabel      labFeatureIDValue;

   private BLabel      labFeatureName;

   private BLabel      labFeatureNameValue;

   private BLabel      labFeatureBlockValue;

   private BLabel      labFeatureMode;

   private BLabel      labFeatureFundingRequired;

   private BLabel      labFeatureFundingRequiredValue;

   private BLabel      labFeatureAccountName;

   private BTextArea   textAreaFeatureDscr;

   private FundingItem fundingItem;

   private CurvesPanel curves;

   public FundingTab(PascalSwingCtx psc, String id) {
      super(psc, id);
   }

   public void setFundingItem(FundingItem fi) {
      this.fundingItem = fi;
   }

   public void setFundingAccount(Integer account) {
      this.account = account;

   }

   public void setMessage(String msg) {
      this.msg = msg;

   }

   public void tabLostFocus() {

   }

   public void setFeatureText(String text) {
      this.text = text;

   }

   public void tabGainFocus() {
      applyModeltoView();
   }

   public void disposeTab() {
   }

   public void actionPerformed(ActionEvent e) {
   }

   public void initTab() {

      this.setLayout(new BorderLayout());

      labFeatureDscr = new BLabel(sc, "funding.featuredscr");
      labFeatureID = new BLabel(sc, "funding.featureid");
      labFeatureIDValue = new BLabel(sc);
      labFeatureName = new BLabel(sc, "funding.featurename");
      labFeatureNameValue = new BLabel(sc);

      labFeatureFundingRequired = new BLabel(sc, "funding.featurerequire");
      labFeatureFundingRequiredValue = new BLabel(sc);

      labFeatureAccountName = new BLabel(sc);
      labFeatureBlockValue = new BLabel(sc);
      labFeatureMode = new BLabel(sc);

      textAreaFeatureDscr = new BTextArea(sc, 6, 40);

      butSeeAllFundingRequests = new BButton(sc, this, "but.funding.seeall", null, null);

      RiverPanel rs = new RiverPanel(sc);

      rs.raddHfill(labFeatureDscr);
      rs.raddBr(labFeatureName);
      rs.raddTab(labFeatureNameValue);
      rs.raddBr(labFeatureID);
      rs.raddTab(labFeatureIDValue);
      rs.raddBr(labFeatureFundingRequired);
      rs.raddTab(labFeatureFundingRequiredValue);
      rs.raddBr(labFeatureMode);
      rs.raddBr(labFeatureBlockValue);

      RiverPanel rsMain = new RiverPanel(sc);

      rsMain.radd(rs);
      rsMain.raddTab(textAreaFeatureDscr);
      rsMain.raddBrHfill(labFeatureAccountName);

      TopDonatorsPanel top = new TopDonatorsPanel(psc, fundingItem, 3);

      JPanel pane = new JPanel();
      StackLayout principleStack = new StackLayout();
      pane.setLayout(principleStack);

      FundingPrinciples principles = new FundingPrinciples(psc);
      principles.setOpaque(false);
      
      BPanelGradient gradient = new BPanelGradient();
      curves = new CurvesPanel();
      pane.add(gradient, StackLayout.TOP);
      pane.add(principles, StackLayout.TOP);
      pane.add(curves, StackLayout.TOP);

      RiverScrollable rsMain2 = new RiverScrollable(sc);

      rsMain2.radd(rsMain);
      rsMain2.raddTab(top);
      rsMain2.raddBrHfill(pane);

      this.add(rsMain2, BorderLayout.CENTER);
      startAnimation();
   }

   private void startAnimation() {
      Timer timer = new Timer(50, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            curves.animate();
            curves.repaint();
         }
      });
      timer.start();
   }

   public void applyModeltoView() {
      //#debug
      toDLog().pFlow("", fundingItem, FundingTab.class, "applyModeltoView", ITechLvl.LVL_05_FINE, true);

      initCheck();

      if (fundingItem != null) {
         textAreaFeatureDscr.setTextKey(fundingItem.getKeyFeatureDescription());

         Account ac = psc.getPCtx().getPClient().getAccount(fundingItem.getAccount());
         if (ac != null) {

            String name = ac.getName();
            labFeatureAccountName.setText(name);
            StringUtils su = sc.getUC().getStrU();

            String id = su.getSubstring(name, '_', '_');
            if (id != null) {
               labFeatureIDValue.setText(id);
            }
            String funding = su.getSubstring(name, '[', ']');
            if (funding != null) {
               labFeatureFundingRequiredValue.setText(funding);
            }
            String block = su.getSubstring(name, '<', '>');
            if (block != null) {
               labFeatureBlockValue.setText(block);
            }
            //
            String mode = su.getSubstring(name, '(', ')');
            if (mode != null) {
               labFeatureMode.setText(mode);
            }
         } else {

            sc.getLog().consoleLogError("Could not find funding account " + fundingItem.getAccount());
         }
      }
   }

}
