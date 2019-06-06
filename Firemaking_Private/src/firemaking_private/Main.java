/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firemaking_private;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author T7emon
 */
@ScriptManifest(author = "T7emon", name = "Firemaking_Private", version = 0.1, description = "Firemaking Private", category = Category.FIREMAKING)
public class Main extends AbstractScript {
    
public boolean banking = false;
    private Timer timer;
    private boolean lit;
    private boolean move;
    private boolean walking;
    
           /*
            * Is in area? wether true or false
            */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }return false;
}
    
    @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("The fire catches and the logs begin to burn.")) {
            lit = true;
        }
        if (msg.getMessage().contains("You can't light a fire here.")) {
            move = true;
            walking = true;
        }
}
    
   /*
    * Bank method..
    */
    public void bank() {
            banking = true;
            getBank().open(BankLocation.VARROCK_WEST);
            sleepUntil(() -> getBank().isOpen(), 5000);
            getBank().withdraw(Constants.willow_logs, 27);
            getBank().close();
            sleepUntil(() -> !getBank().isOpen(), 5000);
            banking = false;
            walking = true;

    }
    
	@Override
    public void onStart() {
        timer = new Timer();
        getSkillTracker().start(Skill.FIREMAKING);
        log("Starting!");
    }
    
    	private enum State {
              BANK, WALK, LIGHT, SLEEP
	};
        
        private State getState() {
              //if (getInventory().count(Constants.oak_logs) > new Random().nextInt(6 + 1) + 10 || getInventory().isFull()) {  
              if (!getInventory().contains(Constants.willow_logs)) {
                  return State.BANK;
        } else {
                  if (walking) {
                      return State.WALK;
                  } else {
                  if (!banking && !walking) {
                  return State.LIGHT;
                  }}
        }return State.SLEEP;
}
    @Override
    public int onLoop() {
        switch (getState()) {
            case BANK:
            bank();
             break;
            case LIGHT:
                getInventory().get(Constants.tinderbox).useOn(Constants.willow_logs);
                sleep(900);
                 lit = false;
                 sleepUntil(() -> move || lit, 15000);
            break;
            case WALK:
            getWalking().walk(Constants.varrock.getRandomizedTile());
            sleepUntil(() -> getLocalPlayer().getTile().distance(Constants.varrock) < 5, 5000);
            walking = false;
                break;
        }
       return Calculations.random(0, 0);
}
    
    @Override
	public void onPaint(Graphics2D g1){
          g1.setColor(Color.RED);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35); //35
                        g1.drawString("Firemaking exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FIREMAKING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FIREMAKING) + ")", 10, 65); //65
                                            
                       
}

    @Override
    public void onExit() {
    	log("Stopping!"); 
    }}
