package pasa.cbentley.jpasc.swing.utils;

import java.util.Comparator;

import pasa.cbentley.jpasc.pcore.rpc.model.OperationType;

public class ComparatorOpType implements Comparator<OperationType> {

   public int compare(OperationType o1, OperationType o2) {
      if(o1.getValue() > o2.getValue()) {
         return 1;
      } else if(o1.getValue() == o2.getValue()) {
         return 0;
      }
      return -1;
   }

}
