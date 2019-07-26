/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

/**
 * 
 * Contextual message  "Show '45612' account key in tab"
 * @author Charles Bentley
 *
 */
public class CmdShowKeyAccountNames extends CmdShowAbstract<ICommandableKey> {

   protected final String keyBase    = "cmd.key.account.names";

   protected final String keyBaseTip = "cmd.key.account.names.tip";

   public CmdShowKeyAccountNames(PascalCmdManager pcm, int type) {
      super(pcm, type);
   }

   protected String getKeyBase() {
      return keyBase;
   }

   protected String getKeyBaseTip() {
      return keyBaseTip;
   }

   protected void executeWithTab(ICommandableKey t) {
      t.cmdShowKeyAccountNames();
   }

   protected void executeWithWin(ICommandableKey t) {
      t.cmdShowKeyAccountNamesNewWindow();
   }
}
