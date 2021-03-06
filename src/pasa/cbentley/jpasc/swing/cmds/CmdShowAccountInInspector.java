/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CmdShowAccountInInspector extends CmdShowAbstract<ICommandableAccount> implements ITechShow {

   protected final String keyBase    = "cmd.account.show.inspector";

   protected final String keyBaseTip = "cmd.account.show.inspector.tip";

   public CmdShowAccountInInspector(PascalCmdManager pcm, int type) {
      super(pcm, type);
   }

   protected String getKeyBase() {
      return keyBase;
   }

   protected String getKeyBaseTip() {
      return keyBaseTip;
   }

   protected void executeWithTab(ICommandableAccount t) {
      t.cmdShowSelectedAccountDetails();
   }

   protected void executeWithWin(ICommandableAccount t) {
      t.cmdShowSelectedAccountDetailsNewWindow();
   }

}
