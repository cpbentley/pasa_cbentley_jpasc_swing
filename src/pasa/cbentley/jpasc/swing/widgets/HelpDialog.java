/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.widgets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.CompositeBluer;
import pasa.cbentley.swing.widgets.MouseOverIcon;
import pasa.cbentley.swing.widgets.b.BButton;

/**
 * 
 * @author Charles Bentley
 *
 */
public class HelpDialog extends JDialog implements HyperlinkListener, ActionListener, TreeSelectionListener {
   /**
   * 
   */
   private static final long serialVersionUID = -6071314073359458190L;

   private final String      HELP_PATH        = "/help/";

   private final String      INDEX_PROPERTIES = HELP_PATH + "index.properties";

   private final String      HOME_PAGE        = HELP_PATH + "home.html";

   private BButton           butPrev          = null;

   private BButton           butNext          = null;

   private BButton           butHome          = null;

   private BButton           butExit          = null;

   private JToolBar          toolBar          = null;

   private JTree             indexTree        = null;

   private JEditorPane       helpPane         = null;

   private Vector<TreePath>  helpStack        = null;

   private int               helpStackIndex   = 0;

   private boolean           blockHelpStack   = false;

   private SwingCtx          sc;

   public HelpDialog(SwingCtx sc, Component parent) {
      super(JOptionPane.getFrameForComponent(parent), "Help", false);
      this.sc = sc;
      init();
   }

   private void init() {
      helpStack = new Vector<TreePath>();
      helpStackIndex = 0;

      JPanel contentPanel = new JPanel(new BorderLayout());
      initToolBar();
      initIndexTree();
      initHelpPane();
      JPanel indexPanel = new JPanel(new BorderLayout());
      indexPanel.add(toolBar, BorderLayout.NORTH);
      indexPanel.add(new JScrollPane(indexTree), BorderLayout.CENTER);
      
      JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, indexPanel, new JScrollPane(helpPane));
      splitPane.setContinuousLayout(true);
      contentPanel.add(splitPane, BorderLayout.CENTER);
      
      setContentPane(contentPanel);
   }

   public void hyperlinkUpdate(HyperlinkEvent e) {
      if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
         JEditorPane pane = (JEditorPane) e.getSource();
         try {
            pane.setPage(e.getURL());
         } catch (Throwable t) {
            t.printStackTrace();
         }
      }
   }

   private void cmdNext() {
      if (helpStackIndex < (helpStack.size() - 1)) {
         blockHelpStack = true;
         helpStackIndex++;
         TreePath path = (TreePath) helpStack.get(helpStackIndex);
         indexTree.setSelectionPath(path);
         blockHelpStack = false;
      }
   }

   private void cmdPrev() {
      if (helpStackIndex > 0) {
         blockHelpStack = true;
         helpStackIndex--;
         TreePath path = (TreePath) helpStack.get(helpStackIndex);
         indexTree.setSelectionPath(path);
         blockHelpStack = false;
      }
   }

   private void cmdHome() {
      indexTree.setSelectionRow(0);
   }

   private void cmdExitHelp() {
      dispose();
   }

   private void initToolBar() {
      toolBar = new JToolBar();
      toolBar.setFloatable(false);

      Composite c = new CompositeBluer();

      Icon prevIcon = sc.getResIcon("arrow_back", "help", IconFamily.ICON_SIZE_1_SMALL);
      butPrev = new BButton(sc, this);
      butPrev.setIconNormal(prevIcon);
      butPrev.setFocusPainted(false);
      butPrev.setRolloverEnabled(true);
      butPrev.setRolloverIcon(new MouseOverIcon(sc, prevIcon, c));
      butPrev.setToolTipText("Previous page");

      Icon nextIcon = sc.getResIcon("arrow_forward", "help", IconFamily.ICON_SIZE_1_SMALL);
      butNext = new BButton(sc, this);
      butNext.setIconNormal(nextIcon);
      butNext.setFocusPainted(false);
      butNext.setRolloverEnabled(true);
      butNext.setRolloverIcon(new MouseOverIcon(sc, nextIcon, c));
      butNext.setToolTipText("next page");

      Icon homeIcon = sc.getResIcon("home", "help", IconFamily.ICON_SIZE_1_SMALL);
      butHome = new BButton(sc, this);
      butHome.setIconNormal(homeIcon);
      butHome.setFocusPainted(false);
      butHome.setRolloverEnabled(true);
      butHome.setRolloverIcon(new MouseOverIcon(sc, homeIcon, c));
      butHome.setToolTipText("home page");

      Icon exitIcon = sc.getResIcon("exit", "help", IconFamily.ICON_SIZE_1_SMALL);
      butExit = new BButton(sc, this);
      butExit.setIconNormal(exitIcon);
      butExit.setFocusPainted(false);
      butExit.setRolloverEnabled(true);
      butExit.setRolloverIcon(new MouseOverIcon(sc, exitIcon, c));
      butExit.setToolTipText("quit help");

      toolBar.add(butPrev);
      toolBar.add(butNext);
      toolBar.add(butHome);
      toolBar.addSeparator(new Dimension(150, 8));
      toolBar.add(butExit);
   }

   private void initIndexTree() {
      //future root node of the tree
      DefaultMutableTreeNode home = buildTree();

      //init the tree with the root node
      indexTree = new JTree(home);
      indexTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      indexTree.requestFocus();
      indexTree.setSelectionRow(0);
      helpStack.add(indexTree.getSelectionPath());
      updateHelpStack();
      indexTree.addTreeSelectionListener(this);
   }

   public void valueChanged(TreeSelectionEvent e) {
      TreePath selPath = indexTree.getSelectionPath();
      if (selPath != null) {
         DefaultMutableTreeNode selPage = (DefaultMutableTreeNode) selPath.getLastPathComponent();
         TreeNodeHelpData nodeData = (TreeNodeHelpData) selPage.getUserObject();
         try {
            helpPane.setPage(getClass().getResource(nodeData.getURL()));
            if (!blockHelpStack) {
               helpStack.add(selPath);
               helpStackIndex = helpStack.size() - 1;
            }
            updateHelpStack();
         } catch (Exception ex) {
            helpPane.setText("<html><h2>ERROR: " + nodeData.getURL() + "</h2></html>");
         }
         helpPane.validate();
      }
   }

   private DefaultMutableTreeNode buildTree() {
      String fileName = HELP_PATH + "home.html";
      DefaultMutableTreeNode home = new DefaultMutableTreeNode(new TreeNodeHelpData("Home", fileName));
      DefaultMutableTreeNode nodeI = null;
      try {
         URL url = getClass().getResource(INDEX_PROPERTIES);
         BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
         String s = null;
         while ((s = reader.readLine()) != null) {
            s = s.trim();
            if ((s.length() > 0) && (s.charAt(0) != '#')) {
               int posCategory = s.indexOf('\t');
               int posFileName = s.indexOf('=');
               String category = s.substring(0, posCategory);
               String displayString = s.substring(posCategory + 1, posFileName);
               fileName = HELP_PATH + s.substring(posFileName + 1);
               if (category.equals("I")) {
                  nodeI = new DefaultMutableTreeNode(new TreeNodeHelpData(displayString, fileName));
                  home.add(nodeI);
               } else if (category.equals("P")) {
                  //simple child
                  home.add(new DefaultMutableTreeNode(new TreeNodeHelpData(displayString, fileName)));
               } else if (category.equals("IP")) {
                  //child to current node
                  nodeI.add(new DefaultMutableTreeNode(new TreeNodeHelpData(displayString, fileName)));
               }
            }
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      return home;
   }

   /**
    * Called from tabs with argument being the page to display
    * @param page
    */
   public void displayPage(String page) {
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) indexTree.getModel().getRoot();
      //TODO search a tree
   }

   /**
    * 
    */
   private void initHelpPane() {
      try {
         URL url = getClass().getResource(HOME_PAGE);
         helpPane = new JEditorPane(url) {

            private static final long serialVersionUID = -2047193152841961162L;

            public void paint(Graphics g) {
               Graphics2D g2D = (Graphics2D) g;
               g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
               super.paint(g);
            }
         };
         helpPane.setEditable(false);
         helpPane.addHyperlinkListener(this);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   private void updateHelpStack() {
      butPrev.setEnabled((helpStack.size() > 1) && (helpStackIndex > 0));
      butNext.setEnabled((helpStack.size() > 1) && (helpStackIndex < (helpStack.size() - 1)));
   }

   public class TreeNodeHelpData {

      private String displayString = null;

      private String url           = null;

      public TreeNodeHelpData(String aDisplayString, String fileName) {
         displayString = aDisplayString;
         url = fileName;
      }

      public String getURL() {
         return url;
      }

      public String toString() {
         return displayString;
      }
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butPrev) {
         cmdPrev();
      } else if (src == butNext) {
         cmdNext();
      } else if (src == butHome) {
         cmdHome();
      } else if (src == butExit) {
         cmdExitHelp();
      }
   }
}
