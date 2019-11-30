/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mining_private.volcanic_sulphur;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Volcanic_sulphur_Miner", version = 1.0, description = "Volcanic_sulphur miner", category = Category.MINING)

public class Main extends AbstractScript {
    

    private Timer timer;
    boolean mining;
    private int ore_count = 0;
    
    private boolean banking = false;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.MINING);
               log("Initialized");
            
        }
            
    public void bank() {
            banking = true;
             GameObject bank = getGameObjects().closest(28594); //7455 //7468
            sleepUntil(() -> bank.interact(), 5000);
           sleepUntil(() -> getBank().isOpen(), 5000);
           getBank().depositAllItems();
           getBank().withdraw(Constants.food, 5);
           sleep(1000);
            getBank().close();
            banking = false;
            getWalking().walkExact(new Tile(1447, 3863, 0));

    }
         
@Override
	public void onStart() {
            init();
		log("Welcome to Volcanic sulphur mining Bot by T7emon.");
		log("This is to get Volcanic sulphur for lovakengj favor.");
	}
        

	private enum State {
               MINE, BANK, SLEEP
	};

	private State getState() {
            
            if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 40) {
                getInventory().interact(item -> item != null && item.getName().contains("Shark"), "Eat");
            }
            
        if (getInventory().isFull() || !getInventory().contains(Constants.food)) {
        return State.BANK;
        }
                
          return State.MINE;
}
        
        
	@Override
	public int onLoop() {
		switch (getState()) {                  
            case MINE:
            GameObject ore = getGameObjects().getTopObjectOnTile(new Tile(1445, 3863, 0)); //7455 //7468
            if (ore != null && ore.getID() == 28498 || ore.getID() == 28497 || ore.getID() == 28496) { 
              sleepUntil(()-> ore.interact(), 5000);
             sleep(Calculations.random(900, 1050));
               sleepUntil(()-> !getLocalPlayer().isAnimating(), 5000);
              sleepUntil(()-> ore.getID() != 28498 || ore.getID() != 28497 || ore.getID() != 28496, 5000);
              ore_count++;
            }
                break;
               case BANK:
                  bank();
               break;
                case SLEEP:
                   sleep(Calculations.random(100, 200));
                 break;
                }
		return Calculations.random(50, 100);
        }

@Override
	public void onPaint(Graphics2D g1){
          g1.setColor(Color.RED);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35); //35
                        g1.drawString("Mining exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MINING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MINING) + ")", 10, 65); //65
                        g1.drawString("sulphur gained (p/h): " + ore_count + "(" + timer.getHourlyRate(ore_count) + ")", 10, 80);
                                            
                       
}}


