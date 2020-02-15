/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.utilities.Timer;

/**
 *
 * @author t7emon
 */
public class Constants {

    /*
    * Settings
   */
         private int minBetAmount = 100000; //the minimum bet amount coins 100k
         private int chanceAmount = 55; //chance amount for player to win. Above this number is payout x2 
         
         private boolean hotRollEnabled = true; //Hot Roll enabled true or false
         private int hotRollNumber = 88; //Hot Roll number for payout x3
         
         private String[] moderators = {"tunnellord", "gfjdfdvd", "gdgrffdf"}; //Moderators nearby
         
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
