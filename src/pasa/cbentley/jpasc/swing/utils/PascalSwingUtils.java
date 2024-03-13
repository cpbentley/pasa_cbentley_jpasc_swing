package pasa.cbentley.jpasc.swing.utils;

import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.utils.DateUtils;
import pasa.cbentley.jpasc.pcore.ctx.ITechPasc;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;
import pasa.cbentley.swing.ctx.SwingCtx;

public class PascalSwingUtils implements IEventConsumer {

   private boolean                isInited = false;

   protected final PascalSwingCtx psc;

   private String                 strAgoMinutes;

   public PascalSwingUtils(PascalSwingCtx psc) {
      this.psc = psc;
   }

   /**
    * Approximative time of the block age
    * 
    * @param blockAge
    * @return
    */
   public String computeTimeFromBlockAgePascalTime(int blockAge) {
      //lazy initialize for the first time. (quicker initial load times and trivial cost)
      if (!isInited) {
         init();
      }
      SwingCtx sc = psc.getSwingCtx();
      if (blockAge < 0) {
         return sc.getResString("text.blockfuture");
      } else if (blockAge == 0) {
         return sc.getResString("text.blocknow");
      }
      //else check other cases
      if (blockAge < ITechPasc.BLOCKS_HOUR) {
         return sc.getResString("text.block1hour");
      } else if (blockAge < ITechPasc.BLOCKS_DAY) {
         int hours = blockAge / ITechPasc.BLOCKS_HOUR;
         if (hours == 1) {
            return sc.getResString("text.block1hour");
         } else {
            return sc.getResString("text.blockhours", hours);
         }
      } else if (blockAge < ITechPasc.BLOCKS_MONTH) {
         int days = blockAge / ITechPasc.BLOCKS_DAY;
         if (days == 1) {
            return sc.getResString("text.block1day");
         } else {
            return sc.getResString("text.blockdays", days);
         }
      } else if (blockAge < ITechPasc.BLOCKS_YEAR) {
         //we are less than a month. show months
         int months = blockAge / ITechPasc.BLOCKS_MONTH;
         if (months == 1) {
            return sc.getResString("text.block1month");
         } else {
            return sc.getResString("text.blockmonths", months);
         }
      } else if (blockAge < ITechPasc.BLOCKS_2_YEARS) {
         //we need this for singular
         int monthsInYear = blockAge - ITechPasc.BLOCKS_YEAR;
         int month = monthsInYear / ITechPasc.BLOCKS_MONTH;
         if (month < 1) {
            return sc.getResString("text.block1year");
         } else {
            return sc.getResString("text.block1yearmonths", month);
         }
      } else {
         int years = blockAge / ITechPasc.BLOCKS_YEAR;
         int blocksMonthsInYear = blockAge - (years * ITechPasc.BLOCKS_YEAR);
         int months = blocksMonthsInYear / ITechPasc.BLOCKS_MONTH;
         if (months == 0) {
            //compute days
            int days = blocksMonthsInYear / ITechPasc.BLOCKS_DAY;
            if (days <= 1) {
               //ignore 0 or 1 days
               return sc.getResString("text.blockyears", years);
            } else {
               return sc.getResString("text.blockyearsdays", years, days);
            }
         } else {
            return sc.getResString("text.blockyearsmonths", years, months);
         }
      }

   }

   /**
    * Approximative time of the block age when using minutes from {@link DateUtils}
    * 
    * @param blockAge
    * @return
    */
   public String computeTimeFromBlockAge(int blockAge) {
      //lazy initialize for the first time. (quicker initial load times and trivial cost)
      if (!isInited) {
         init();
      }
      SwingCtx sc = psc.getSwingCtx();
      if (blockAge < 0) {
         return sc.getResString("text.blockfuture");
      } else if (blockAge == 0) {
         return sc.getResString("text.blocknow");
      }

      int minutes = blockAge * 5;
      if (minutes < 60) {
         return sc.getResString("text.blockminutes", minutes);
      } else if (minutes < DateUtils.MINUTES_IN_A_DAY) {
         int hours = minutes / 60;
         if (hours == 1) {
            return sc.getResString("text.block1hour");
         } else {
            return sc.getResString("text.blockhours", hours);
         }
      } else if (minutes < DateUtils.MINUTES_IN_A_MONTH) {
         int days = minutes / DateUtils.MINUTES_IN_A_DAY;
         if (days == 1) {
            return sc.getResString("text.block1day");
         } else {
            return sc.getResString("text.blockdays", days);
         }
      } else if (minutes < DateUtils.MINUTES_IN_A_YEAR) {
         //months
         int months = minutes / DateUtils.MINUTES_IN_A_MONTH;
         if (months == 1) {
            return sc.getResString("text.block1month");
         } else {
            return sc.getResString("text.blockmonths", months);
         }
      } else if (minutes < DateUtils.MINUTES_IN_A_YEAR_2) {
         int monthsInYear = minutes - DateUtils.MINUTES_IN_A_YEAR;
         int month = monthsInYear / DateUtils.MINUTES_IN_A_MONTH;
         if (month < 2) {
            return sc.getResString("text.block1year");
         } else {
            return sc.getResString("text.block1yearmonths", month);
         }
         //yearly
      } else {
         int years = minutes / DateUtils.MINUTES_IN_A_YEAR;
         int minutesLeftForMonth = minutes - (years * DateUtils.MINUTES_IN_A_YEAR);
         int month = minutesLeftForMonth / DateUtils.MINUTES_IN_A_MONTH;
         if (month < 2) {
            return sc.getResString("text.blockyears", years);
         } else {
            return sc.getResString("text.blockyearsmonths", years, month);
         }

      }
   }

   /**
    * 
    * @param diff
    * @return
    */
   public String computeTimeFromBlockDiffPascalTime(int diff) {
      // TODO Auto-generated method stub
      return null;
   }

   public void computeStringValues() {
      strAgoMinutes = psc.getSwingCtx().getResString("text.minutes.ago");
   }

   public void consumeEvent(BusEvent e) {
      //we only registered for language change.. update values
      computeStringValues();
   }

   private void init() {
      isInited = true;
      //we only need to register for changes when it is required
      //we want to be notified when a language change occurs
      psc.getUC().getEventBusRoot().addConsumer(this, IEventsCore.PID_01_FRAMEWORK, IEventsCore.PID_01_FRAMEWORK_2_LANGUAGE_CHANGED);

      //at construction time, the bundle of string is not yet available.
      computeStringValues();
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PascalSwingUtils");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PascalSwingUtils");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return psc.getUC();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
