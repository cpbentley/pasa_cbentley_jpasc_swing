/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

public class CmdShowBlockInInspector extends CmdShowAbstract<ICommandableBlock> {

   protected final String keyBase    = "cmd.block.show.inspector";

   protected final String keyBaseTip = "cmd.block.show.inspector.tip";

   public CmdShowBlockInInspector(PascalCmdManager pcm, int type) {
      super(pcm, type);
   }

   protected void executeWithTab(ICommandableBlock t) {
      t.cmdShowSelectedBlockDetails();
   }

   protected void executeWithWin(ICommandableBlock t) {
      t.cmdShowSelectedBlockDetailsNewWindow();
   }

   protected String getKeyBase() {
      return keyBase;
   }

   protected String getKeyBaseTip() {
      return keyBaseTip;
   }

}
