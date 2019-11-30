package hunter_private.bird_catcher;


import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
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
        name = "Hunter-Bird_Catcher", 
        version = 1.0, 
        description = "Catch Birds", 
        category = Category.HUNTING)

public class Main extends AbstractScript {
    
    private Timer timer;
    private int catch_count = 0;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.HUNTER);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Bird catch Script by T7emon.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("The bird snare that you laid has fallen over.")) {
          Constants.Fallen_bird_snare = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Bird snare") && gameObject.hasAction("Take") && gameObject.hasAction("Lay"));
          Constants.Fallen_bird_snare.interact("Lay");
        }
}
       
        	private enum State {
               LAY_TRAP, CHECK_FULL_TRAP, CHECK_BROKEN_TRAP, DROP, SLEEP
	};
        /*
            * Is in area? wether true or false
            */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }return false;
}
                
        private State getState() {
            if (!inArea(Constants.Feldip_Hills_Area)) {
                getWalking().walk(new Tile(2589, 2884, 0));
            }
            
            Constants.Full_bird_snare = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Bird snare") && gameObject.hasAction("Check"));
            Constants.Broken_bird_snare = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Bird snare") && gameObject.hasAction("Dismantle") && !gameObject.hasAction("Investigate"));
            
            if (getInventory().contains(Constants.Bird_snare) && inArea(Constants.Feldip_Hills_Area)) {
             return State.LAY_TRAP;
            }
            
            if (Constants.Full_bird_snare != null && getLocalPlayer().distance(Constants.Full_bird_snare) <= 2) {
              return State.CHECK_FULL_TRAP;
            }
            
            if (Constants.Broken_bird_snare != null && getLocalPlayer().distance(Constants.Broken_bird_snare) <= 2) {
              return State.CHECK_BROKEN_TRAP;
            }
            if (getInventory().isFull()) {
                getInventory().dropAll(Constants.Raw_bird_meat, Constants.bones);
            }
              return State.SLEEP;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case LAY_TRAP:
                    getInventory().get(Constants.Bird_snare).interact();
                break;
                case CHECK_FULL_TRAP:
                    Constants.Full_bird_snare.interact("Check");
                    catch_count++;
                break;
                case CHECK_BROKEN_TRAP:
                    Constants.Broken_bird_snare.interact("Dismantle");
                    break;
                case SLEEP:
                    Calculations.random(337, 477);
                    break;
                    
        }
        return Calculations.random(950, 1050);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Hunter exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FISHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING) + ")", 10, 65); //65
                        g.drawString("Birds Catched (p/h): " + catch_count + "(" + timer.getHourlyRate(catch_count) + ")", 10, 80);
                                            
                       
}}

