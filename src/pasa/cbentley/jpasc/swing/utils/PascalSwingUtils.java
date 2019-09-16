package pasa.cbentley.jpasc.swing.utils;

import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.utils.DateUtils;
import pasa.cbentley.jpasc.swing.ctx.PascalSwingCtx;

public class PascalSwingUtils implements IEventConsumer {

   private boolean                isInited = false;

   protected final PascalSwingCtx psc;

   private String                 strAgoMinutes;

   public PascalSwingUtils(PascalSwingCtx psc) {
      this.psc = psc;
   }

   /**
    * Approximative time of the block age
    * @param blockAge
    * @return
    */
   public String computeTimeFromBlockAge(int blockAge) {
      //lazy initialize for the first time. (quicker initial load times and trivial cost)
      if (!isInited) {
         init();
      }
      String msg = "";
      int minutes = blockAge * 5;
      if (blockAge < 7) {
         msg = minutes + this.strAgoMinutes;
      } else if (blockAge < DateUtils.MINUTES_IN_A_DAY) {
         int hours = minutes / 60;
         if (hours == 1) {
            return "1 hour ago";
         } else {
            return hours + " hours ago";
         }
      } else if (blockAge < DateUtils.MINUTES_IN_A_MONTH) {
         int days = minutes / DateUtils.MINUTES_IN_A_DAY;
         if (days == 1) {
            return "1 day ago";
         } else {
            return days + " month ago";
         }
      } else if (blockAge < DateUtils.MINUTES_IN_A_YEAR) {
         //months
         int months = minutes / DateUtils.MINUTES_IN_A_MONTH;
         if (months == 1) {
            return "1 month ago";
         } else {
            return months + " month ago";
         }
      } else if (blockAge < DateUtils.MINUTES_IN_A_YEAR_2) {
         int monthsInYear = minutes - DateUtils.MINUTES_IN_A_YEAR;
         int month = monthsInYear / DateUtils.MINUTES_IN_A_MONTH;
         if (month == 0) {
            return "1 year ago";
         } else {
            return "1 year and " + month + " months ago";
         }
         //yearly
      } else {
         int years = minutes / DateUtils.MINUTES_IN_A_YEAR;
         return years + " years ago";
      }

      return msg;
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
      psc.getUCtx().getEventBusRoot().addConsumer(this, IEventsCore.PID_1_FRAMEWORK, IEventsCore.EID_FRAMEWORK_2_LANGUAGE_CHANGED);

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
      return psc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
