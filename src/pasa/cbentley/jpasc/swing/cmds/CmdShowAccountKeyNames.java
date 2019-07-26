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
public class CmdShowAccountKeyNames extends CmdShowAbstract<ICommandableAccount> {

   protected final String keyBase    = "cmd.key.accounts";

   protected final String keyBaseTip = "cmd.key.accounts.tip";

   public CmdShowAccountKeyNames(PascalCmdManager pcm, int type) {
      super(pcm, type);
   }

   protected String getKeyBase() {
      return keyBase;
   }

   protected String getKeyBaseTip() {
      return keyBaseTip;
   }

   protected void executeWithTab(ICommandableAccount t) {
      t.cmdShowSelectedAccountKeyNames();
   }

   protected void executeWithWin(ICommandableAccount t) {
      t.cmdShowSelectedAccountKeyNamesNewWindow();
   }
}
