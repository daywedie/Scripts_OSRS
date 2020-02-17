package com.bot;

import com.bot.constants.Locations;
import com.bot.constants.Constants;
import com.bot.utils.ImageUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
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
    private int tradeCount = 0;
    private int rollCount = 0;
    private int profitMade = 0;
    private int paidOut = 0;
    public BufferedImage backgroundImage = ImageUtils.getImage("https://i.pinimg.com/originals/d5/45/17/d5451747b53b00feb99e2bcc2bbb7ec7.png");
    Queue<String> traderQueue = new LinkedList<>(); 
    //List<String> payouList = new ArrayList<String>();

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
           int maxSize = 3;
           if (traderQueue.size() <= maxSize && !traderQueue.contains(msg.getUsername())) {
           traderQueue.add(msg.getUsername());
           log("tradeList : " + traderQueue);
            } else {
            log("Trade Que got " + "("+traderQueue.size()+")" + " traders");
           //sendMessage("red:Trade Que already got (2) traders, please wait.");
          }
        }
        if (msg.getMessage().contains("Other player declined trade.")) {
            log(bot.trader()+": " + "Declined trade " + "@"+bot.getCurrentTimeString());
            bot.setRoll(false);
            bot.setDeclinedTrade(true);
            traderQueue.remove(bot.trader());
            bot.setTrader(null);
            inTradeTimer.reset();
        }}
        
    /*
   * Send message
  */      
void sendMessage(String message) {
    if (!modNearby()) {
     getMouse().getMouseSettings().setWordsPerMinute(3000);
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
       if (!getTrade().isOpen()) {
           return false;
       }
       WidgetChild w = null;
    if (getTrade().isOpen(1)) {
             w = getWidgets().getWidgetChild(335, 29);
                        } else {
                    w = getWidgets().getWidgetChild(334, 31);
                       }
    return w != null && w.isVisible() && w.getText().toLowerCase().contains("modified");
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
    public void onItemChange(Item[] Items) {
        for (Item item : Items) {
            if (item.getName().toLowerCase().equals("coins") && item.getAmount() >= Settings.minBetAmount()) {
               log(item.getName() + " : " + item.getAmount());
                bot.setCoinsAmount(item.getAmount());
                bot.setverifiedTrade(true);
                profitMade += item.getAmount();
            }
        }
    }
   /*
   * Check if moderators are nearby
   */
    private boolean modNearby() {
        //String[] moderators = {"tunnellord", "ljfdfd", "gdgrfdf"};
           List<Player> playerList = getPlayers().all();
           for (String modName : Settings.moderators()) {
           for (Player player : playerList) {
               if (player.getName().toLowerCase().contains(modName)) {
                   log("WARNING: Moderator : " + "("+modName+")");
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
             *@TODO
             * Move to choosen location.. ONLY SUPPORTS CASTLEWARS ATM.
            */
             //Tile CenterTile = new Tile(2441, 3087, 0);
             Locations.CenterTile = Locations.centerTile.castleWarsCenterTile.tile();
            if (getLocalPlayer().getTile().distance(Locations.CenterTile) >= 7 || !inArea(Locations.Areas.castleWarsArea.area())) {
                getWalking().walk(Locations.CenterTile);
            }
            
           /*
           * Advertise
          */
           if (bot.trader() == null && traderQueue.isEmpty() && !getTrade().isOpen() && !bot.roll() && !bot.payout()) {
               return State.ADVERTISE;
           }
             /*
             * traderQueue Iteration & Set trader
            */
            if (bot.trader() == null) {
           for (String trader : traderQueue) {
               bot.setTrader(trader); 
           }}
                   /*
                   * Trade
                  */
                   if (bot.trader() != null && traderQueue.contains(bot.trader())) {
                   Player player = getPlayers().closest(Player -> Player.getName().equals(bot.trader()));
                   if (!bot.roll() 
                   && !bot.payout() 
                   && getLocalPlayer().getTile().distance(player.getTile()) <= 6
                   && Locations.Areas.castleWarsArea.area().contains(player)
                   && inArea(Locations.Areas.castleWarsArea.area()) || getTrade().isOpen()) {            
            return State.TRADE;
            }}
           /*
             * Remove trader from traderQueue
            */    
            if (getTrade().isOpen()) {
                 WidgetChild w = getWidgets().getWidgetChild(335, 31);
                 String trader = w.getText().replace("Trading With: ", "");
                 log("trading with = " + trader);
                 if (traderQueue.contains(trader)) {
                 traderQueue.remove(trader);
                 bot.setTrader(trader);
                 sleepUntil(() -> !traderQueue.contains(trader), 3000);
             }} 
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
                sendMessage("glow3:shake:"+"HotRoll: " + "("+Settings.hotRollNumber() +")" + " x3 " + "| " + Settings.minBetAmountStr() + " min " + "| " + "("+getLocalPlayer().getName()+")" + " | " + "@ "+bot.getCurrentTimeString());
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
                traderQueue.remove(bot.trader());
                bot.setTrader(null);
                inTradeTimer.reset();
                } else {
                    if (getTrade().isOpen(2) && inTradeTimer.elapsed() >= 15000) {
                    getTrade().declineTrade();
                    sendMessage("flash1:" + bot.trader() + " took too long.. declined trade...");
                    log(bot.trader() + " took too long..");
                    traderQueue.remove(bot.trader());
                   bot.setTrader(null);
                   inTradeTimer.reset();
                    }
               }
                        
                 /*
                 * Trade with player
                */
                if (!getTrade().isOpen() && bot.trader() != null) {
               getTrade().tradeWithPlayer(bot.trader());
               sleepUntil(() -> getTrade().isOpen(1), 10000);
               tradeCount++;
                }
                
                /*
                * First trade screen
               */
               while (getTrade().isOpen(1) && traderAcceptedTrade()) {
                Item[] traderItems = getTrade().getTheirItems();
                /*
                * Decline first trade screen if traderItems != null
                */
                if (traderItems == null) {
                 sendMessage("flash2:shake:This GAME only accepts Coins: " + "("+Settings.minBetAmountStr() + " to " + Settings.maxBetAmountStr()+")");
                       getTrade().declineTrade();
                       //sleepUntil(() -> !getTrade().isOpen(), 5000);
                       traderQueue.remove(bot.trader());
                       bot.setTrader(null);
                  }
                /*
                * Accept first trade screen
                */
                if (traderItems != null) {
                 for (Item item : traderItems) {
                    if (item.getName().toLowerCase().equals("coins") 
                     && item.getAmount() >= Settings.minBetAmount() 
                     && item.getAmount() <= Settings.maxBetAmount() && traderAcceptedTrade()) {
                       getTrade().acceptTrade(1);
                    } else {
                  /*
                * Decline first trade screen if not accepted   
                */     
                       sendMessage("flash2:shake:This GAME only accepts Coins: " + "("+Settings.minBetAmountStr() + " to " + Settings.maxBetAmountStr()+")");
                       getTrade().declineTrade();
                       sleepUntil(() -> !getTrade().isOpen(), 5000);
                       traderQueue.remove(bot.trader());
                       bot.setTrader(null);
                      }}}
                
                
                
                            /*
                            * Second trade screen
                            */
                            if (getTrade().isOpen(2)) {
                               sleep(1000);
                            if (coinsInSecondTradeScreen()) {
                                sleepUntil(() -> traderAcceptedTrade(), 10000);
                                if (traderAcceptedTrade()) {
                             /*
                            * Accept second trade screen
                            */
                                getTrade().acceptTrade(2);
                                sleepUntil(() -> !getTrade().isOpen() && bot.verifiedTrade(), 5000);
                            } else {
                                /*
                                * Decline second trade screen if not accepted
                               */
                      if (!bot.declinedTrade()) {
                       sendMessage("flash2:shake:This GAME only accepts Coins: " + "("+Settings.minBetAmountStr() + " to " + Settings.maxBetAmountStr()+")");
                       getTrade().declineTrade();
                       sleepUntil(() -> !getTrade().isOpen(), 5000);
                       traderQueue.remove(bot.trader());
                       bot.setTrader(null);  
                            }}}}
                              /*
                              * Placed bet succesfully
                              */
                                if (!getTrade().isOpen() && bot.trader() != null && bot.verifiedTrade()) {
                                getKeyboard().type("flash3:"+bot.trader() + " has placed a bet of " + "("+bot.getCoinsAmountStr()+")" + " @ " + bot.getCurrentTimeString());
                                bot.setRoll(true);
                                bot.setverifiedTrade(false);
                               
                            }}
                break;
                
                
                case ROLL:
                log("State = Roll");
                //sendMessage("white:slide:Rolling..." + " @ " + bot.getCurrentTimeString());
               // sleep(Calculations.random(700, 777));
                random = new Random().nextInt(100);
                log("Rolled : " + random);
                rollCount++;
                if (random == Settings.chanceAmount()) {
                     random = new Random().nextInt(100);
                }
                if (random < Settings.chanceAmount()) {
                  sendMessage("red:shake:"+bot.trader() + " has LOST with a roll of ("+random+")" + " @ " + bot.getCurrentTimeString());
                  getTabs().open(Tab.EMOTES);
                  getEmotes().doEmote(Emote.CRY);
                  getTabs().open(Tab.INVENTORY);
                    bot.setPayout(false);
                    bot.setRoll(false);
                    traderQueue.remove(bot.trader());
                    bot.setTrader(null);
                    } else {
                        if (random > Settings.chanceAmount()) {
                           sendMessage("flash3:"+bot.trader() + " has WON with a roll of ("+random+")" + " @ " + bot.getCurrentTimeString());
                            bot.setCoinsAmount(bot.coinsAmount() * 2);
                            if (random == Settings.hotRollNumber() && Settings.hotRollEnabled()) {
                                bot.setCoinsAmount(bot.coinsAmount() * 3);
                                sendMessage("flash3:Congratulations " +bot.trader() + " has rolled the Hot number : " + "("+Settings.hotRollNumber()+")");
                            }
                            getTabs().open(Tab.EMOTES);
                            getEmotes().doEmote(Emote.CHEER);
                            getTabs().open(Tab.INVENTORY);
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
                        traderQueue.remove(bot.trader());
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
                            sendMessage("flash3:"+bot.trader() + " has been paid " + "("+bot.getCoinsAmountStr()+")" + " @ " + bot.getCurrentTimeString());
                            getTabs().open(Tab.EMOTES);
                            getEmotes().doEmote(Emote.BOW);
                            getTabs().open(Tab.INVENTORY);
                            profitMade -= bot.coinsAmount();
                            paidOut = bot.coinsAmount();
                            bot.setPayout(false);
                            traderQueue.remove(bot.trader());
                            bot.setTrader(null);
                            payoutTimer.reset(); 
                             }
                        }}}}}
                break;
                case SLEEP:
                    Calculations.random(77, 177);
            }
        return Calculations.random(977, 1077);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.WHITE);
                      //g.drawImage(backgroundImage, 10, 50, null);
                       
			g.drawString("Runtime: " + scriptTimer.formatTime(), 10, 35);
                        g.drawString("State: " + getState(), 10, 50);
                        
                        g.drawString("Roll: " + "("+Settings.chanceAmount()+")" + " x2", 10, 80);
                        g.drawString("HotRollEnabled: " + "("+Settings.hotRollEnabled()+")", 10, 95);
                        g.drawString("HotRollNumber: " + "("+Settings.hotRollNumber()+")" + " x3", 10, 110);
                        g.drawString("MinimumBetAmount: " + "("+Settings.minBetAmountStr()+")", 10, 125);
                        
                        g.drawString("TradeCount: " + "("+tradeCount+")", 10, 145);
                        g.drawString("RollCount: " + "("+rollCount+")", 10, 160);
                        g.drawString("Profit: " + "("+profitMade / 1000 + "K"+")", 10, 175);           
                        g.drawString("Paid: " + "("+paidOut / 1000 + "K"+")", 10, 190);            
}
}


