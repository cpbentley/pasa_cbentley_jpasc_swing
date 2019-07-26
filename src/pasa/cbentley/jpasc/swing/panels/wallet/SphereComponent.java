/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.panels.wallet;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.image.SphereColors;
import pasa.cbentley.swing.image.SphereFactory;

/**
 * Component to control east and west panel size
 */
public class SphereComponent extends JComponent {

   private SphereColors  colors;

   private SwingCtx      sc;

   private SphereFactory sf;

   public SphereComponent(SwingCtx sc, SphereColors colors) {
      this.sc = sc;
      this.colors = colors;
      sf = new SphereFactory();
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(120, 120);
   }

   @Override
   protected void paintComponent(Graphics g) {
      //DO NOT DO THIS.. It creates a never ending loop of repaints!
      //setFont(getFont().deriveFont(70.f).deriveFont(Font.BOLD));
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      sf.drawSphere(g2, getWidth(), getHeight(), colors);
      
   }
}
