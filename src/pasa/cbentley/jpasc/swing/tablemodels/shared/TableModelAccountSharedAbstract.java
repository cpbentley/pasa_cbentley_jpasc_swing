package pasa.cbentley.jpasc.swing.tablemodels.shared;

import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.jpasc.swing.models.AccountSwingShared;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.model.ModelTableBAbstract;

/**
 * Table Model for listing accounts from a shared list of accounts.
 * 
 * When a table request an account, it is instantaneously requested and returned.
 * 
 * Network data is fetched and the Table is updated once the data is there.
 * 
 * Data is partially available.
 * 
 * This tableModel should be a singleton and used in a composition pattern, not inheritance.
 * 
 * A table model for price will use this model to query data
 * 
 * {@link AccountSwingShared} also contains UI elements such as a Color pointer so as to avoid
 * generating new Color object all the time.
 * 
 * @author Charles Bentley
 *
 */
public abstract class TableModelAccountSharedAbstract extends ModelTableBAbstract<AccountSwingShared> {

   public TableModelAccountSharedAbstract(PascalSwingCtx psc) {
      super(psc.getSwingCtx());
   }

   public int getColumnCount() {
      // TODO Auto-generated method stub
      return 0;
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      // TODO Auto-generated method stub
      return null;
   }

   protected void computeStats(AccountSwingShared a, int row) {
      // TODO Auto-generated method stub
      
   }

   public String getToolTips(int col) {
      // TODO Auto-generated method stub
      return null;
   }

}
