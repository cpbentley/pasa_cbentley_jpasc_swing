/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

/**
 * Shows the seller account in inspector
 * @author Charles Bentley
 *
 */
public class CmdShowAccountSellerInInspector extends CmdShowAbstract<ICommandableAccount> implements ITechShow {

   protected final String keyBase    = "cmd.account.seller.show.inspector";

   protected final String keyBaseTip = "cmd.account.seller.show.inspector.tip";

   public CmdShowAccountSellerInInspector(PascalCmdManager pcm, int type) {
      super(pcm, type);
   }

   protected String getKeyBase() {
      return keyBase;
   }

   protected String getKeyBaseTip() {
      return keyBaseTip;
   }

   protected void executeWithTab(ICommandableAccount t) {
      t.cmdShowSelectedAccountSellerDetails();
   }

   protected void executeWithWin(ICommandableAccount t) {
      t.cmdShowSelectedAccountSellerDetailsNewWindow();
   }

}
