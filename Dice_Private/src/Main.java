import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
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
        name = "Dice Bot", 
        version = 1.0, 
        description = "Dicer!?", 
        category = Category.MONEYMAKING)

public class Main extends AbstractScript {
    
  /*
  * Variables
 */
    private Timer timer;
    private Timer tradeTimer;
    private String trader;
    private int random;
    private boolean payout;
    private boolean roll;
    private int coinsAmount;
    private String coinsStr;
    //List<String> traderList = new ArrayList<String>();
    private boolean declined;
    
  /*
  * Start
 */
    @Override
	public void onStart() {
           timer = new Timer();
            log("Initialized");
	    log("Welcome to Dice Bot by Null x.");
        }
    /*
    * Message received
   */       
         @Override
        public void onMessage(Message msg) {
       if (msg.getMessage().contains("wishes to trade with you.")) {
            log("Trader Detected");
             trader = msg.getUsername();
        }
        if (msg.getMessage().contains("Other player declined trade.")) {
            declined = true;
            trader = null;
            coinsAmount = 0;
        }
        }
    /*
   * Send message
  */      
void sendMessage(String message) {
     getMouse().getMouseSettings().setWordsPerMinute(300);
    getKeyboard().type(message);
}
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
     * Player accepted trade boolean
    */
   private boolean acceptedTrade() {
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
   * Coins amount converter string
   */ 
   private String getCoinsAmountStr() {
      String coinsAmountStr = coinsAmount+"";
      if (coinsAmount >= 100000 && coinsAmount < 1000000) {
         coinsAmountStr = coinsAmount / 1000 + "K";
      } else {
          if (coinsAmount >= 1000000) {
          coinsAmountStr = coinsAmount / 1000000 + "M";
      }
      } 
      return coinsAmountStr;
}
   /*
   * Check if moderators are nearby
   */
    private boolean modNearby() {
        //String[] moderators = {"tunnellord", "ljfdfd", "gdgrfdf"};
           List<Player> playerList = getPlayers().all();
           for (Player player : playerList) {
           for (String modName : Constants.moderators) {
               if (player.getName().toLowerCase().contains(modName)) {
                   log("WARNING: Moderator nearby");
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
               PAY
	};
     /*
     * State getter
    */           
        private State getState() {
             /*
             * Move to choosen locaiton..
            */
            Tile cwCenterTile = new Tile(2441, 3087, 0);
            if (getLocalPlayer().getTile().distance(cwCenterTile) >= 5 || !inArea(Constants.Castle_wars_lobby)) {
                getWalking().walk(cwCenterTile);
            }
            
             /*
             * Trade
            */
            Player player = getPlayers().closest(Player -> Player.getName().equals(trader)); //if (getLocalPlayer().getTile().distance(player.getTile()) >= 3) {
                while (trader != null && !roll && !payout && getLocalPlayer().getTile().distance(player.getTile()) < 5 && inArea(Constants.Castle_wars_lobby)) {
                return State.TRADE;
            }
                
           /*
           * Roll
          */      
          if (roll) {
              return State.ROLL;
          }
          
           /*
           * Payout
          */
          if (payout) {
              return State.PAY;
          }
           /*
           * Advertise
          */
           return State.ADVERTISE;
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
               if (!modNearby()) {
                sendMessage("glow2:shake:"+"Verified host | " + "100k min | " + " Roll " + Constants.chance_amount + "+ " + " | " + "("+getLocalPlayer().getName()+")" + " | " + Constants.getCurrentTimeString());
                sleep(Calculations.random(1300, 1700));
               }
                break;
                
                case TRADE:
                log("State = TRADE");  
                 /*
                 * Decline trade after tradeTimer elapsed..
                */
                if (getTrade().isOpen() && tradeTimer.elapsed() >= 30000) {
                getTrade().declineTrade();
                sendMessage("red:shake:" + trader + " took too long.. declined trade...");
                log(trader + " took too long..");
                trader = null;
                tradeTimer.reset();
               }
                 /*
                 * Decline if trade has been modified
                */
                if (getTrade().isOpen(1)) {
                WidgetChild w = getWidgets().getWidgetChild(335,29);
                    if (w != null){
                        if (w.getText().toLowerCase().contains("modified")) {
                         sendMessage("red:Minimum bet amount = 100k");
                         getTrade().declineTrade();
                         trader = null;
                        }
                    }
                }
                 /*
                 * Trade with player
                */
               if (getTrade().tradeWithPlayer(trader)) {
               sleepUntil(() -> getTrade().isOpen(1), 5000);
               }
                /*
                * Trade screen 1
               */
               if (getTrade().isOpen(1)) {
                 tradeTimer = new Timer();
                Item[] traderItems = getTrade().getTheirItems();
                if (traderItems != null) {
                for (Item item : traderItems) {
                    if (acceptedTrade() && item.getName().equalsIgnoreCase("coins") && item.getAmount() >= Constants.min_bet_amount) {
                        if (getTrade().acceptTrade(1)) {
                       sleepUntil(() -> getTrade().isOpen(2), 5000);
                       coinsAmount = item.getAmount();
                        }
                         /*
                         * Trade screen 2
                        */
                      if (getTrade().isOpen(2)) {
                          coinsStr = getWidgets().getWidgetChild(334,29,0).getText().replace("Coins<col=ffffff> x <col=ffffff>", "").substring(0, 4);
                          log("String Coins : " + coinsStr);
                            if (getTrade().acceptTrade(2)) {  
                            sleepUntil(() -> !getTrade().isOpen(), 5000);
                        }}
                       /*
                       * Roll dice?
                      */
                        if (!declined && !getTrade().isOpen()) {
                       getKeyboard().type("green:shake:"+trader + " has placed a bet of " + "("+getCoinsAmountStr()+")" + " | " + Constants.getCurrentTimeString());
                         roll = true;
                        sleep(Calculations.random(1000, 1200));
                        }}}}
                }
                break;
                
                case ROLL:
                log("State = Roll");
                sendMessage("Rolling...");
                sleep(Calculations.random(1000, 1200));
                random = new Random().nextInt(100);
                log("Rolled : " + random);
                if (random < Constants.chance_amount) {
                  sendMessage("red:shake:"+trader + " has LOST with a roll of ("+random+")");
                  sleep(Calculations.random(1000, 1200));
                    payout = false;
                    roll = false;
                    trader = null;
                } else {
                    if (random == Constants.chance_amount) {
                     random = new Random().nextInt(100);
                    } else {
                        if (random > Constants.chance_amount) {
                           sendMessage("green:shake:"+trader + " has WON with a roll of ("+random+")");
                            this.coinsAmount = coinsAmount * 2;
                            sleep(Calculations.random(1000, 1200));
                            payout = true;
                            roll = false;
                        }
                    }
                }
                break;
                
                case PAY:
                    log("State = PAY");
                    if (getTrade().tradeWithPlayer(trader)) {
                    sleepUntil(() -> getTrade().isOpen(1), 10000);
                   }
                    if (getTrade().isOpen(1)) {
                      getTrade().addItem(995, coinsAmount);
                      sleepUntil(() -> getTrade().getMyItems() != null, 5000);
                    }
               Item[] myItems = getTrade().getMyItems();
                if (myItems != null) {
                for (Item item : myItems) {
                        getTrade().acceptTrade(1);
                        sleepUntil(() -> getTrade().isOpen(2), 5000);
                        if (getTrade().isOpen(2)) {
                            getTrade().acceptTrade(2);
                            sendMessage("green:"+trader + " has been paid " + "("+getCoinsAmountStr()+")" + " | " + Constants.getCurrentTimeString());
                            payout = false;
                            trader = null;
                        }}}
                break;
        }
        return Calculations.random(950, 1050);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        //g.drawString("Games exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FISHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING) + ")", 10, 65); //65
                                            
                       
}
}

