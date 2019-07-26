/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.cmds;

public class CmdShowBlockOperations extends CmdShowAbstract<ICommandableBlock> {


   public CmdShowBlockOperations(PascalCmdManager pcm, int type) {
      super(pcm, type);
   }

   protected final String keyBase = "cmd.block.show.ops";
   protected final String keyBaseTip = "cmd.block.show.ops.tip";


   public void executeWith(ICommandableBlock t) {
     
   }

   protected String getKeyBase() {
      return keyBase;
   }

   protected String getKeyBaseTip() {
      return keyBaseTip;
   }

   protected void executeWithTab(ICommandableBlock t) {
      t.cmdShowSelectedBlockOperationsTabHome();      
   }

   protected void executeWithWin(ICommandableBlock t) {
      t.cmdShowSelectedBlockOperationsTabHome();      
   }

}
