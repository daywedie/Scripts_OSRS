package com.bot.constants;

/**
 *
 * @author T7emon, Tear x, Null x, T7x
 */
public class Constants {

    /*
    * Settings
   */
         private int minBetAmount = 100000; //the minimum bet amount coins 100k
         private int maxBetAmount = 100000000; //the maximum bet amount coins 100M
         private int chanceAmount = 55; //chance amount for player to win. Above this number is payout x2 
         private boolean hotRollEnabled = true; //Hot Roll enabled true or false
         private int hotRollNumber = 88; //Hot Roll number for payout x3
         private String[] moderators = {"tunnellord", "shawtyblitz", "shawtyblits", "hiren"}; //Moderators nearby
     
         /*
         * Getters
       */
         /*
         * minBetAmount
        */
      public int minBetAmount() {
          return minBetAmount;
    }
      
     /*
     * minBetAmount as String
    */
      public String minBetAmountStr() {
      String coinsAmountStr = minBetAmount+"";
      if (minBetAmount >= 1000 && minBetAmount < 1000000) {
         coinsAmountStr = minBetAmount / 1000 + "K";
      } else {
          if (minBetAmount >= 1000000) {
          coinsAmountStr = minBetAmount / 1000000 + "M";
      }
      } 
      return coinsAmountStr;
}
      
         /*
         * maxBetAmount;
        */
      public int maxBetAmount() {
          return maxBetAmount;
    }
      
     /*
     * maxBetAmount as String
    */
      public String maxBetAmountStr() {
      String coinsAmountStr = maxBetAmount+"";
      if (maxBetAmount >= 1000 && maxBetAmount < 1000000) {
         coinsAmountStr = maxBetAmount / 1000 + "K";
      } else {
          if (maxBetAmount >= 1000000) {
          coinsAmountStr = maxBetAmount / 1000000 + "M";
      }
      } 
      return coinsAmountStr;
}
       /*
       * chanceAmount;
      */
      public int chanceAmount() {
       return chanceAmount;
}
      /*
      * hotRollEnabled;
     */
     public boolean hotRollEnabled() {
      return hotRollEnabled;
}
     /*
     * hotRollNumber;
    */
     public int hotRollNumber() {
         return hotRollNumber;
     }
     
     /*
     * moderators;
    */
     public String[] moderators() {
         return moderators;
     }
         
}
