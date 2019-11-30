
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private String trader;
    private String won_or_lost;
    private int random;
    
            public void init() {
               timer = new Timer();
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
	    log("Welcome to Dice Bot by Null x.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You catch a leaping trout.") || msg.getMessage().contains("You catch a leaping salmon.") || msg.getMessage().contains("You catch a leaping sturgeon.")) {
           //game_count++;
        }
}
       public void onTradeMessage(Message msg) {
         if (msg.getMessage().contains("wishes to trade")) {
             getTrade().tradeWithPlayer("trader");
         }
       }
               private enum State {
               ADVERTISE,
               TRADE,
               ROLL_DICE,
               PAY_OUT
	};
                
        private State getState() {
          if (getLocalPlayer()
            
            return State.ADVERTISE;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case ADVERTISE:
                log("State = Advertising");
                getKeyboard().type("Bet "+ Constants.chance_amount+"x2", true);
                sleep(Calculations.random(1200, 1700));
                break;
                case TRADE:
                log("State = TRADE");
                break;
                case ROLL_DICE:
                log("State = Roll_DICE");
                random = new Random().nextInt(100);
                if (random < Constants.chance_amount) {
                    won_or_lost = "Lost";
                } else {
                    if (random == Constants.chance_amount) {
                     random = new Random().nextInt(100);
                    } else {
                        if (random > Constants.chance_amount) {
                            won_or_lost = "Won";
                        }
                    }
                }
                getKeyboard().type(trader + "Rolled " + random + "("+won_or_lost+")", true);
                if (won_or_lost.equalsIgnoreCase("Won")) {
                    //Pay-out
                }
                break;
                case PAY_OUT:
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
                                            
                       
}}

