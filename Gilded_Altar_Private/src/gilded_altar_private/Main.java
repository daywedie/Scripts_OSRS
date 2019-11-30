package gilded_altar_private;

import java.awt.Color;
import java.awt.Graphics;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
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
@ScriptManifest(author = "T7emon", name = "Gilded_Altar_Private", version = 1.0, description = "Does Gilded Altar at Rimmington", category = Category.PRAYER)

public class Main extends AbstractScript {
    
    private Timer timer;
               
    public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.PRAYER);
               log("Initialized");
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Gilded Altar Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Prayer levels!.");
        }
        
            @Override
	public void onExit() {
		log("Stopping Gilded Altar Script...");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("")) {
        }
}

        /*
         * Is in area? wether true or false
         */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }return false;
}
       
        	private enum State {
                 UN_NOTE_BONES, ENTER, INTERACT_ALTAR, LEAVE, SLEEP
	};
                
        private State getState() {
               GameObject Portal = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Portal") && gameObject.hasAction("Enter"));
               GameObject Altar = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Altar") && gameObject.hasAction("Pray"));  
            if (!getInventory().contains(Constants.bones) && getInventory().contains(Constants.noted_bones) && Portal.exists() && Altar == null) {
                log("un-note Bones...");
                return State.UN_NOTE_BONES;
            }
              
               if (getInventory().contains(Constants.bones) && Altar == null) {
                   getWalking().walkExact(new Tile(2954, 3223, 0));
                   sleep(Calculations.random(1200, 1400));
                   log("Enter Friend's house...");
                   return State.ENTER;
               }
                            
              if (Altar.exists() && getInventory().contains(Constants.bones)) {
                  log("Interact with Altar...");
                  return State.INTERACT_ALTAR;
              }
              
              if (Altar.exists() && !getInventory().contains(Constants.bones)) {
                  log("Leave House...");
                  return State.LEAVE;
              }
            
            return State.SLEEP;
        }
        
	@Override
	public int onLoop() {
          GameObject Portal = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Portal") && gameObject.hasAction("Enter"));
          GameObject Altar = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Altar") && gameObject.hasAction("Pray")); 
            switch (getState()) {
               case UN_NOTE_BONES:
                  NPC Phials = getNpcs().closest("Phials");
                   getWalking().walkExact(new Tile(2950, 3214, 0));
                   sleepUntil(() -> getLocalPlayer().distance(Phials) <= 3, 10000);
                   getInventory().get(Constants.noted_bones).useOn(Phials);
                    sleepUntil(() -> getDialogues().inDialogue(), 5000);
                    getDialogues().chooseOption(3);
                break;
                case ENTER:
                    if (Portal.interactForceRight("Friend's house")) {
                    sleepUntil(() -> getDialogues().inDialogue(), 10000);
                    }
                    sleepUntil(() -> getWidgets().getWidgetChild(162, 40, 0) != null, 10000);
                    getWidgets().getWidgetChild(162, 40, 0).interact();
                    sleepUntil(() -> Altar != null, 3000);
                break;
                case INTERACT_ALTAR:                   
                    if (getInventory().get(Constants.bones).useOn(Altar)) {
                    sleepUntil(() -> !getInventory().contains(Constants.bones), 75000);
                 }
                break;
                case LEAVE:
                   if (Portal.interact()) {
                   sleepUntil(() -> Altar == null, 5000);
                   }
                break;
                case SLEEP:
                Calculations.random(1150, 2877);
                break;
        }
        return Calculations.random(977, 1177);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        //g.drawString("Kills: " + npc_kills, 10, 50);
                        //g.drawString("Used food: " + used_food, 10, 65);
                        //g.drawString("Food left in bank: " + food_in_bank, 10, 80);
                        g.drawString("Prayer exp (p/h): " + getSkillTracker().getGainedExperience(Skill.PRAYER) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.PRAYER) + ")", 10, 50);
        }}

