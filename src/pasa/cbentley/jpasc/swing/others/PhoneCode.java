/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.jpasc.swing.others;

public class PhoneCode {

   private final String countryCode;

   private final int    telCode;

   private final String country;

   public String getCountryCode() {
      return countryCode;
   }

   public int getTelCode() {
      return telCode;
   }

   public String getCountry() {
      return country;
   }

   public PhoneCode(String countryCode, int telCode, String country) {
      super();
      this.countryCode = countryCode;
      this.telCode = telCode;
      this.country = country;
   }

}
