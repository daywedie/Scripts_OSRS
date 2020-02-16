package com.bot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author T7emon, Tear x, Null x, T7x
 */
public class Bot {
  /*
  * Variables
 */
    private String trader;
    private boolean verifiedTrade;
    private boolean declineTrade;
    private int random;
    private boolean roll;
    private boolean payout;
    public boolean verifiedPayout;
    private int coinsAmount;
    
   /*
   * Getter & Setters
  */
          /*
          * Trader username
         */
         public void setTrader(String name) {
             this.trader = name;
         }
       public String trader() {
           return trader;
       }
       
        /*
        * Verified trade
       */
       public void setverifiedTrade(boolean value) {
           this.verifiedTrade = value;
       }
       public boolean verifiedTrade() {
           return verifiedTrade;
       }
       
       /*
       * Trade Declined
      */
      public void setDeclinedTrade(boolean value) {
          this.declineTrade = value;
      }
      public boolean declinedTrade() {
          return declineTrade;
      }
        /*
        * Roll dice
       */
       public void setRoll(boolean value) {
           this.roll = value;
       }
       public boolean roll() {
           return roll;
       }
       /*
       * Random number int
       */
       public int random() {
           return random;
       }
        /*
        * Payout
       */
       public void setPayout(boolean value) {
           this.payout = value;
       }
       public boolean payout() {
           return payout;
       }
       
      /*
      * Coins amount from player
     */
       public void setCoinsAmount(int i) {
           this.coinsAmount = i;
       }
       public int coinsAmount() {
           return coinsAmount;
       }
       
   /*
   * Coins amount converter
   */ 
   public String getCoinsAmountStr() {
      String coinsAmountStr = coinsAmount+"";
      if (coinsAmount >= 1000 && coinsAmount < 1000000) {
         coinsAmountStr = coinsAmount / 1000 + "K";
      } else {
          if (coinsAmount >= 1000000) {
          coinsAmountStr = coinsAmount / 1000000 + "M";
      }
      } 
      return coinsAmountStr;
}
       
         /*
         * Current time String
        */
             public String getCurrentTimeString() {
	        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	        return dateFormat.format(new Date());
             }
    
}
