package com.bot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author t7emon
 */
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.emotes.Emote;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.api.wrappers.widgets.message.Message;
/**
 *
 * @author T7emon, Tear x, Null x, T7x
 */

@ScriptManifest(
        author = "T7emon", 
        name = "Dicer Pro", 
        version = 1.0, 
        description = "Dicer!?", 
        category = Category.MONEYMAKING)

public class Main extends AbstractScript implements InventoryListener { 
    
    private Timer scriptTimer;
    private Timer inTradeTimer;
    private Timer payoutTimer;
    private int random;
    private Bot bot;
    private Constants Settings;

  /*
  * Start
 */
    @Override
	public void onStart() {
            scriptTimer = new Timer();
            bot = new Bot();
            Settings = new Constants();
            log("Initialized");
	    log("Welcome to Dice Bot by T7emon");
        }
        
        /*
        * Exit
        */
    @Override
     public void onExit() {
        //@TODO
    }
     
    /*
    * Message received
   */       
         @Override
        public void onMessage(Message msg) {
       if (msg.getMessage().contains("wishes to trade with you.")) {
            log("Trader Detected : " + msg.getUsername() + " @"+bot.getCurrentTimeString());
            if (bot.trader() == null) {
           bot.setTrader(msg.getUsername());
            }
        }
        if (msg.getMessage().contains("Other player declined trade.")) {
            log(bot.trader()+": " + "Declined trade " + "@"+bot.getCurrentTimeString());
            bot.setRoll(false);
            bot.setDeclinedTrade(true);
            bot.setTrader(null);
            inTradeTimer.reset();
        }}
        
    /*
   * Send message
  */      
void sendMessage(String message) {
    if (!modNearby()) {
     getMouse().getMouseSettings().setWordsPerMinute(400);
     getKeyboard().type(message);
}}

         /*
         * In area boolean
        */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }
    return false;
}
    
     /*
     * Player accepted trade
    */
   private boolean traderAcceptedTrade() {
        if(!getTrade().isOpen()){
          return false;
        }
        WidgetChild w = null;
        if (getTrade().isOpen(1)) {
            w = getWidgets().getWidgetChild(335, 30);
        } else {
            w = getWidgets().getWidgetChild(334, 4);
        }

        return w != null && w.isVisible() && w.getText().toLowerCase().contains("player has accepted");
    }
   
   /*
   * Check if trade has been modified
   */
   private boolean tradeModified() {
    if (getTrade().isOpen(1)) {
                WidgetChild w = getWidgets().getWidgetChild(335,29);
                    if (w != null){
                        if (w.getText().toLowerCase().contains("modified")) {
                       return true;
                        }
                    }}
          return false;
}
   
     /*
     * Check for coins in second trade screen
    */
   private boolean coinsInSecondTradeScreen() {
        if(!getTrade().isOpen()){
          return false;
        }
        WidgetChild w = null;
        if (getTrade().isOpen(2)) {
            w = getWidgets().getWidgetChild(334, 29, 0);
            if (w.getText().contains("Coins")) {
                return true;
        }}
        return false;
        }
   
       /*
        * Inventory item changed to verify coins from trader in inventory
        */
        @Override
    public void onItemChange(Item[] arg0) {
        for (Item item : arg0) {
            //log(item.getName() + " : " + item.getAmount());
            if (item.getName().toLowerCase().equals("coins") && item.getAmount() == bot.coinsAmount()) {
                bot.setverifiedTrade(true);
                log(item.getName() + " : " + item.getAmount());
            }
        }
    }
   /*
   * Check if moderators are nearby
   */
    private boolean modNearby() {
        //String[] moderators = {"tunnellord", "ljfdfd", "gdgrfdf"};
           List<Player> playerList = getPlayers().all();
           for (Player player : playerList) {
           for (String modName : Settings.moderators()) {
               if (player.getName().toLowerCase().contains(modName)) {
                   log("WARNING: Moderator : " + modName + " nearby");
                   return true;
               }
           }}
        return false;
    }
    

    
    /*
    * State enum
   */
               private enum State {
               ADVERTISE,
               TRADE,
               ROLL,
               PAY,
               SLEEP
	};
     /*
     * State getter
    */           
        private State getState() {
             /*
             * Move to choosen locaiton..
            */
             //Tile CenterTile = new Tile(2441, 3087, 0);
             Locations.CenterTile = Locations.centerTile.castleWarsCenterTile.tile();
            if (getLocalPlayer().getTile().distance(Locations.CenterTile) >= 5 || !inArea(Locations.Areas.castleWarsArea.area())) {
                getWalking().walk(Locations.CenterTile);
            }
            
             /*
             * Trade
            */
            Player player = getPlayers().closest(Player -> Player.getName().equals(bot.trader()));
                   if (bot.trader() != null 
                   && !bot.roll() 
                   && !bot.payout() 
                   && getLocalPlayer().getTile().distance(player.getTile()) < 5 
                   && inArea(Locations.Areas.castleWarsArea.castleWarsArea.area()) || getTrade().isOpen()) {            
            return State.TRADE;
           }
                   
                   
                
         /*
         * Roll
       */
              if (bot.roll()) {
              return State.ROLL;
          }
          
           /*
           * Payout
          */
          if (bot.payout()) {
              return State.PAY;
          }
           /*
           * Advertise
          */
           if (bot.trader() == null && !bot.roll() && !bot.payout()) {
               return State.ADVERTISE;
           }
           /*
           * Sleep
           */
        return State.SLEEP;
        }
        
        /*
        * Loop
        */
	@Override
	public int onLoop() {
            switch (getState()) {
                
                case ADVERTISE:
                log("State = ADVERTISE");
               Tile cwCenterTile = new Tile(2441, 3087, 0);
               if (!getLocalPlayer().getTile().equals(cwCenterTile)) {
                getWalking().walk(cwCenterTile);
            }
                sendMessage("glow2:shake:"+"Verified host | " + Settings.minBetAmountStr() + " min | " + " Roll " + Settings.chanceAmount() + "+ " + " | " + "("+getLocalPlayer().getName()+")" + " | " + "@ "+bot.getCurrentTimeString());
                sendMessage("glow1:shake:"+"Play responsible | " + Settings.minBetAmountStr() + " min | " + " Roll " + Settings.chanceAmount() + "+ " + " | " + "("+getLocalPlayer().getName()+")" + " | " + "@ "+bot.getCurrentTimeString());
                sendMessage("glow3:shake:"+"Hot roll : " + "("+Settings.hotRollNumber() +")" + " x3 " + "| " + Settings.minBetAmountStr() + " min " + "| " + "("+getLocalPlayer().getName()+")" + " | " + "@ "+bot.getCurrentTimeString());
                break;
            
                case TRADE:
                log("State = TRADE");  
              /*
              * Start inTradeTimer
             */
             if (getTrade().isOpen() && inTradeTimer == null) {
               inTradeTimer = new Timer();
             }
             /*
             * Stop inTradeTimer
             */
             if (!getTrade().isOpen()) {
                 if (inTradeTimer != null) {
                 inTradeTimer.reset();
             }}
                /*
                * Decline trade after tradeTimer elapsed..
               */
                if (getTrade().isOpen(1) && inTradeTimer.elapsed() >= 15000) {
                getTrade().declineTrade();
                sendMessage("red:shake:" + bot.trader() + " took too long.. declined trade...");
                log(bot.trader() + " took too long..");
                bot.setTrader(null);
                inTradeTimer.reset();
                } else {
                    if (getTrade().isOpen(2) && inTradeTimer.elapsed() >= 15000) {
                    getTrade().declineTrade();
                    sendMessage("red:shake:" + bot.trader() + " took too long.. declined trade...");
                    log(bot.trader() + " took too long..");
                   bot.setTrader(null);
                   inTradeTimer.reset();
                    }
               }
                
                 /*
                 * Trade with player
                */
                if (!getTrade().isOpen() && bot.trader() != null && !bot.roll() && !bot.payout()) {
               getTrade().tradeWithPlayer(bot.trader());
               sleepUntil(() -> getTrade().isOpen(1), 5000);
               }
                
                /*
                * First trade screen
               */
               if (getTrade().isOpen(1)) {
                Item[] traderItems = getTrade().getTheirItems();
                /*
                * Decline first screen trade
                */
                if(traderAcceptedTrade() && traderItems == null || tradeModified()) {
                    sleep(500);
                     sendMessage("red:Minimum bet amount : " + Settings.minBetAmountStr());
                       getTrade().declineTrade();
                       sleepUntil(() -> !getTrade().isOpen(), 5000);
                       bot.setTrader(null);
                }
                /*
                * Accept first screen trade
                */
                if (traderItems != null) {
                for (Item item : traderItems) {
                    sleepUntil(() -> traderAcceptedTrade(), 5000);
                    if (traderAcceptedTrade() && item.getName().toLowerCase().equals("coins") && item.getAmount() >= Settings.minBetAmount()) {
                       getTrade().acceptTrade(1);
                       sleepUntil(() -> getTrade().isOpen(2), 5000);
                       bot.setCoinsAmount(item.getAmount());
                      }
                            /*
                            * Second trade screen
                            */
                            if (getTrade().isOpen(2)) {
                                /*
                                * Decline second trade screen
                                */
                            if (!coinsInSecondTradeScreen()) {
                              sendMessage("red:Minimum bet amount : " + Settings.minBetAmountStr());
                                 getTrade().declineTrade();
                       sleepUntil(() -> !getTrade().isOpen(), 5000);
                       bot.setTrader(null);
                       log("Something went wrong!");
                                }
                            /*
                            * Accept second trade screen
                            */
                            sleepUntil(() -> coinsInSecondTradeScreen(), 5000);
                              if (coinsInSecondTradeScreen()) {
                              sleepUntil(() -> traderAcceptedTrade(), 10000);
                              if (traderAcceptedTrade()) {
                                getTrade().acceptTrade(2);
                                sleepUntil(() -> !getTrade().isOpen() && bot.verifiedTrade(), 5000);
                                if (!getTrade().isOpen() && bot.trader() != null && !bot.declinedTrade() && bot.verifiedTrade()) {
                                getKeyboard().type("green:shake:"+bot.trader() + " has placed a bet of " + "("+bot.getCoinsAmountStr()+")" + " @ " + bot.getCurrentTimeString());
                                bot.setRoll(true);
                                bot.setverifiedTrade(false);
                               sleep(1000);
                     }
                         }}}}
                            }}
                break;
                
                
                case ROLL:
                log("State = Roll");
                sendMessage("Rolling..." + " @ " + bot.getCurrentTimeString());
                sleep(Calculations.random(900, 1000));
                random = new Random().nextInt(100);
                log("Rolled : " + random);
                if (random == Settings.chanceAmount()) {
                     random = new Random().nextInt(100);
                }
                if (random < Settings.chanceAmount()) {
                  sendMessage("red:shake:"+bot.trader() + " has LOST with a roll of ("+random+")" + " @ " + bot.getCurrentTimeString());
                  sleep(Calculations.random(900, 1000));
                    bot.setPayout(false);
                    bot.setRoll(false);
                    bot.setTrader(null);
                    } else {
                        if (random > Settings.chanceAmount()) {
                           sendMessage("green:shake:"+bot.trader() + " has WON with a roll of ("+random+")" + " @ " + bot.getCurrentTimeString());
                            bot.setCoinsAmount(bot.coinsAmount() * 2);
                            if (random == Settings.hotRollNumber() && Settings.hotRollEnabled()) {
                                bot.setCoinsAmount(bot.coinsAmount() * 3);
                                sendMessage("glow2:Congratulations " +bot.trader() + " has rolled the Hot number : " + "("+Settings.hotRollNumber()+")");
                            }
                            sleep(Calculations.random(900, 1000));
                            bot.setPayout(true);
                            bot.setRoll(false);
                            payoutTimer =  new Timer();
                        }
                    }
                break;
                
                case PAY:
                    log("State = PAY");
                    
                    /*
                    * Payout took too long
                    */
                   if (payoutTimer.elapsed() >= 30000) {
                        sendMessage("red:"+bot.trader() + " Didn't accept payout.");
                        bot.setPayout(false);
                        bot.setTrader(null);
                        payoutTimer.reset();
                    }
                   /*
                   * Trade with player
                   */
                    if (getTrade().tradeWithPlayer(bot.trader())) {
                    sleepUntil(() -> getTrade().isOpen(1), 10000);
                   }
                    /*
                    * First trade screen
                    */
                    if (getTrade().isOpen(1)) {
                      getTrade().addItem(995, bot.coinsAmount());
                      sleepUntil(() -> getTrade().getMyItems() != null, 5000);
                    }
               Item[] myItems = getTrade().getMyItems();
                if (myItems != null) {
                     sleepUntil(() -> traderAcceptedTrade(), 10000);
                for (Item item : myItems) {
                    bot.setCoinsAmount(item.getAmount());
                if (traderAcceptedTrade()) {
                        getTrade().acceptTrade(1);
                        sleepUntil(() -> getTrade().isOpen(2), 5000);
                        
                        /*
                        * Second trade screen
                        */
                        if (getTrade().isOpen(2)) {
                             sleepUntil(() -> traderAcceptedTrade(), 10000);
                             if (traderAcceptedTrade()) {
                            getTrade().acceptTrade(2);
                             sleepUntil(() -> !getTrade().isOpen(2), 5000);
                             /*
                             * Payout finished
                            */
                             if (!getTrade().isOpen(2)) {
                            sendMessage("green:"+bot.trader() + " has been paid " + "("+bot.getCoinsAmountStr()+")" + " @ " + bot.getCurrentTimeString());
                            getTabs().open(Tab.EMOTES);
                            getEmotes().doEmote(Emote.BOW);
                            getTabs().open(Tab.INVENTORY);
                            bot.setPayout(false);
                            bot.setTrader(null);
                            payoutTimer.reset(); 
                             }
                        }}}}}
                break;
                case SLEEP:
                    Calculations.random(77, 177);
            }
        return Calculations.random(1000, 1700);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + scriptTimer.formatTime(), 10, 35);
                        //g.drawString("Games exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FISHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING) + ")", 10, 65); //65
                                            
                       
}
}


