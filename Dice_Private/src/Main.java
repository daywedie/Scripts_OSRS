
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import static org.dreambot.api.Client.getClient;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.AdvancedMessageListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;
/**
 *
 * @author t7emon
 */
@ScriptManifest(
        author = "T7emon", 
        name = "Dice Bot", 
        version = 1.0, 
        description = "Dicer!?", 
        category = Category.MONEYMAKING)

public class Main extends AbstractScript {

    private Timer timer;
    private Timer tradeTimer;
    private String trader;
    private int random;
    private boolean payout;
    private boolean roll;
    private int coinsAmount;
    //List<String> traderList = new ArrayList<String>();

    @Override
	public void onStart() {
           timer = new Timer();
            log("Initialized");
	    log("Welcome to Dice Bot by Null x.");
        }
        
         @Override
        public void onMessage(Message msg) {
       if (msg.getMessage().contains("wishes to trade with you.")) {
            log("Trader Detected");
             trader = msg.getUsername();
             tradeTimer = new Timer();
        }}
        
void sendMessage(String message) {
    getKeyboard().type(message);
 getMouse().getMouseSettings().setWordsPerMinute(300);
}
               private enum State {
               ADVERTISE,
               TRADE,
               ROLL,
               PAY,
	};
                
        private State getState() {
            Tile cwCenterTile = new Tile(2441, 3087, 0);
            if (!getLocalPlayer().getTile().equals(cwCenterTile)) {
                getWalking().walk(cwCenterTile);
            }
                while (trader != null && !roll && !payout) {
                return State.TRADE;
            }
                    
          if (roll) {
              return State.ROLL;
          }
          if (payout) {
              return State.PAY;
          }
            return State.ADVERTISE;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case ADVERTISE:
                log("State = Advertising");
                sendMessage("red:shake:"+"Verified host | " + "100k min | " + " Roll " + Constants.chance_amount + "+ " + " | " + "("+getLocalPlayer().getName()+")" + " " + Constants.getCurrentTimeString());
                 //sendMessage("red:shake:"+"Verified host | " + "100k min | " + "Roll " + Constants.chance_amount + "+ " + " | " + "("+getLocalPlayer().getName()+")" + " " + Constants.getCurrentTimeString());
                //getKeyboard().type("red:wave:"+"Verified host | " + "100k min | " + "Roll " + Constants.chance_amount + "+ " + " | " + getLocalPlayer().getName() + " " +Constants.getCurrentTimeString(), true);
                sleep(Calculations.random(1300, 1700));
                break;
                case TRADE:
                log("State = TRADE");
               getTrade().tradeWithPlayer(trader);
               sleepUntil(() -> getTrade().isOpen(1), 5000);
                    if (getTrade().isOpen() && tradeTimer.elapsed() >= 15000) {
                getTrade().declineTrade(1);
                trader = null;
                tradeTimer.reset();
                sendMessage("red:shake:" + trader + " took too long.. decling...");
                log("Trader took too long..");
            } else {
               if (getTrade().isOpen(1)) {
                Item[] traderItems = getTrade().getTheirItems();
                if (traderItems != null) {
                for (Item item : traderItems) {
                    //sleepUntil(() -> item.getName().equals("coins") && item.getAmount() >= Constants.min_bet_amount, 10000);
                    if (item.getName().equalsIgnoreCase("coins") && item.getAmount() >= Constants.min_bet_amount) {
                        getTrade().acceptTrade(1);
                       sleepUntil(() -> getTrade().isOpen(2), 5000);
                         if (getTrade().isOpen(2)) {
                        coinsAmount = item.getAmount();
                        getTrade().acceptTrade(2);
                       getKeyboard().type("green:shake:"+trader + " Has placed a bet of " + coinsAmount+"K " + Constants.getCurrentTimeString());
                       getMouse().getMouseSettings().setWordsPerMinute(300);
                        sleep(Calculations.random(1000, 1200));
                        if (!getTrade().isOpen(2)) {       
                        roll = true;
                    } else {
                        if (!getTrade().isOpen()) {
                        trader = null;
                         } else {
                        if (!item.getName().equals("coins") && item.getAmount() < Constants.min_bet_amount) {
                        log("Decline trade..");
                        getTrade().declineTrade();
                        trader = null;
                }}}}}}
                }}}
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
                            sleep(Calculations.random(1000, 1200));
                            payout = true;
                            roll = false;
                        }
                    }
                }
                //sendMessage(currentTrader + "Rolled " + random + "("+"win: "+won+")");
                break;
                case PAY:
                    log("State = PAY");
                    getTrade().tradeWithPlayer(trader);
                    sleepUntil(() -> getTrade().isOpen(), 10000);
                    if (getTrade().isOpen(1)) {
                        getTrade().addItem(995, coinsAmount * 2);
                        getTrade().acceptTrade(1);
                        sleepUntil(() -> getTrade().isOpen(2), 5000);
                        if (getTrade().isOpen(2)) {
                            getTrade().acceptTrade(2);
                            sendMessage("green:"+trader + " has been paid " + coinsAmount);
                            payout = false;
                            trader = null;
                        }
                    }
                break;
                /*case SLEEP:
                log("State = SLEEP");
                sleep(Calculations.random(977, 1477));
                break;*/
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

