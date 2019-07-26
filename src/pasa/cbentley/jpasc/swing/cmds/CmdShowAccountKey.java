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
public class CmdShowAccountKey extends CmdShowAbstract<ICommandableAccount> {

   protected final String keyBase = "cmd.account.show.key";
   protected final String keyBaseTip = "cmd.account.show.key.tip";

   
   public CmdShowAccountKey(PascalCmdManager pcm, int type) {
      super(pcm, type);
   }


   protected String getKeyBase() {
      return keyBase;
   }

   protected String getKeyBaseTip() {
      return keyBaseTip;
   }

   protected void executeWithTab(ICommandableAccount t) {
      t.cmdShowSelectedAccountOwner();
   }

   protected void executeWithWin(ICommandableAccount t) {
      t.cmdShowSelectedAccountOwnerNewWindow();      
   }
}
