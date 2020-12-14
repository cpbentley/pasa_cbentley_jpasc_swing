package pasa.cbentley.jpasc.swing.utils;

import java.util.Comparator;

import pasa.cbentley.jpasc.pcore.rpc.model.OperationSubType;

public class ComparatorOpTypeSub implements Comparator<OperationSubType> {

   public int compare(OperationSubType o1, OperationSubType o2) {
      if (o1.getValue() > o2.getValue()) {
         return 1;
      } else if (o1.getValue() == o2.getValue()) {
         return 0;
      }
      return -1;
   }

}
