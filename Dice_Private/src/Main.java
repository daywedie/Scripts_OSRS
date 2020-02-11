
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
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

public class Main extends AbstractScript implements AdvancedMessageListener {

    private Timer timer;
    private String trader;
    private boolean won;
    private int random;
    private boolean payOut;
    private boolean roll;
    private String currentTrader;
    private int coinsAmount;
    private Item item;
     List<String> traderList = new ArrayList<String>();

    @Override
	public void onStart() {
           timer = new Timer();
            log("Initialized");
	    log("Welcome to Dice Bot by Null x.");
        }
            @Override
public void onMessage(Message msg) {
}
         @Override
        public void onTradeMessage(Message msg) {
        if (msg.getMessage().contains("wishes to trade with you."))
             trader = msg.getUsername();
        getTrade().tradeWithPlayer(trader);
      for (int i = 0; i < 51; i++) {
           traderList.add(trader + i);
}}

public void sendMessage(String msg) {
    getKeyboard().type(msg, true);
}

               private enum State {
               ADVERTISE,
               TRADE,
               ROLL,
               PAY,
	};
                
        private State getState() {
            
          if (getTrade().isOpen(1)) {
          return State.TRADE;
            }
          
           
          if (roll) {
              return State.ROLL;
          }
          if (payOut) {
              return State.PAY;
          }
            
            return State.ADVERTISE;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case ADVERTISE:
                log("State = Advertising");
                //getKeyboard().type("Bet "+ Constants.chance_amount+"x2", true);
                sleep(Calculations.random(1200, 1700));
                break;
                case TRADE:
                log("State = TRADE");
                Item[] traderItems = getTrade().getTheirItems();
                if (traderItems != null) {
                for (Item item : traderItems) {
                    if (!item.getName().equals("coins") && item.getAmount() < Constants.mini_bet_amount) {
                        log("Decline trade..");
                        getTrade().declineTrade();
                    } else {
                    if (item.getName().equalsIgnoreCase("coins") && item.getAmount() >= Constants.mini_bet_amount) {
                        getTrade().acceptTrade(1);
                        sleep(Calculations.random(3000, 5000));
                         if (getTrade().isOpen(2)) {
                        currentTrader = getTrade().getTradingWith();
                        coinsAmount = item.getAmount();
                        getTrade().acceptTrade(2);
                    }
                }}
                }}
                break;
                case ROLL:
                log("State = Roll");
                //sendMessage("Rolling...");
                random = new Random().nextInt(100);
                if (random < Constants.chance_amount) {
                    won = false;
                } else {
                    if (random == Constants.chance_amount) {
                     random = new Random().nextInt(100);
                    } else {
                        if (random > Constants.chance_amount) {
                            won = true;
                        }
                    }
                }
                //sendMessage(currentTrader + "Rolled " + random + "("+"win: "+won+")");
                if (won) {
                    payOut = true;
                }
                break;
                case PAY:
                    log("State = PAY");
                    getTrade().tradeWithPlayer(currentTrader);
                    sleepUntil(() -> getTrade().isOpen(), 10000);
                    if (getTrade().isOpen(1)) {
                        getTrade().addItem(995, coinsAmount * 2);
                        getTrade().acceptTrade(1);
                        if (getTrade().isOpen(2)) {
                            getTrade().acceptTrade(2);
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

    @Override
    public void onAutoMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrivateInfoMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onClanMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onGameMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPlayerMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrivateInMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPrivateOutMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

