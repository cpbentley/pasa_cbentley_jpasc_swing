/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.others;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.jpasc.swing.interfaces.IPhoneCodes;

/**
 * This class should be disposable when memory is cleared.
 * 
 * @author Charles Bentley
 *
 */
public class PhoneCodesMapping {

   private UCtx                 uc;

   private ArrayList<PhoneCode> list;

   public PhoneCodesMapping(UCtx uc) {
      this.uc = uc;
   }

   /**
    * Immutable list of codes
    * @return
    * @throws IllegalAccessException 
    * @throws IllegalArgumentException 
    */
   public List<PhoneCode> getPhoneCodeList() throws IllegalArgumentException, IllegalAccessException {
      if (list == null) {
         Field[] fs = IPhoneCodes.class.getFields();
         list = new ArrayList<PhoneCode>(fs.length);
         StringUtils stru = uc.getStrU();
         for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            String data = (String) f.get(null);
            String countryCode = f.getName();
            String codeStr = stru.getSubstring(data, '+', ')');
            int code = Integer.valueOf(codeStr);
            String country = stru.getSubstringStartToString(data, " (");
            PhoneCode pc = new PhoneCode(countryCode, code, country);
            list.add(pc);
         }
      }
      return list;
   }

   public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
      Field[] fs = IPhoneCodes.class.getFields();

      UCtx uc = new UCtx();
      StringUtils stru = uc.getStrU();
      for (int i = 0; i < fs.length; i++) {
         Field f = fs[i];
         String data = (String) f.get(null);
         String code = stru.getSubstring(data, '+', ')');
         String country = stru.getSubstringStartToString(data, " (");
         System.out.println(f.getName() + " " + country + " " + code);
      }
   }
}
