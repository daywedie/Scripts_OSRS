package castle_wars.sabotage;


import static castle_wars.sabotage.Constants.Castle_wars_lobby;
import static castle_wars.sabotage.Constants.Guthix_Portal;
import static castle_wars.sabotage.Constants.Saradomin_waiting_room;
import static castle_wars.sabotage.Constants.Zamorak_waiting_room;
import static castle_wars.sabotage.Constants.game_count;
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
        name = "Castle_Wars_Sabotager", 
        version = 1.0, 
        description = "Sabotage Castle Wars Games", 
        category = Category.MINIGAME)

public class Main extends AbstractScript {
    
    private Timer timer;
    
        /*
         * Is in area? wether true or false
         */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }return false;
}
            public void init() {
               timer = new Timer();
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Castle Wars Sabotager by T7emon.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You catch a leaping trout.") || msg.getMessage().contains("You catch a leaping salmon.") || msg.getMessage().contains("You catch a leaping sturgeon.")) {
           game_count++;
        }
}
       
        	private enum State {
               ENTER_WAIT_ROOM, 
               WAIT_UNTIL_GAME, 
               LEAVE_RESPAWN, 
               GRAB_BARRICADES, 
               SLEEP
	};
                
        private State getState() {
          
            
              if (inArea(Castle_wars_lobby)) {
                     log("In Castle Wars lobby Area");
                     return State.ENTER_WAIT_ROOM;
               }
              
              if (inArea(Zamorak_waiting_room)) {
                  log("In Zamorak Waiting room");
                  return State.WAIT_UNTIL_GAME;
              } else {
                  if (inArea(Saradomin_waiting_room)) {
                      log("In Saradomin Waiting room");
                      return State.WAIT_UNTIL_GAME;
                  }
                  
                if (inArea(Constants.Zamorak_respawn_room)) {
                    log("In Zamorak Respawn room");
                    return State.LEAVE_RESPAWN;
                } else {
                    if (inArea(Constants.Saradomin_respawn_room)) {
                        log("In Saradomin Respawn room");
                        return State.LEAVE_RESPAWN;
                    }
                }
              }
            return State.SLEEP;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case ENTER_WAIT_ROOM:
                    log("State = ENTER_WAIT_ROOM");
                    Guthix_Portal = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Guthix Portal") && gameObject.hasAction("Enter"));
                    Guthix_Portal.interact();
                    sleepUntil(() -> inArea(Constants.Zamorak_waiting_room) || inArea(Constants.Saradomin_waiting_room), 7000);
                break;
                case WAIT_UNTIL_GAME:
                    log("State = WAIT");
                     sleep(Calculations.random(150000, 199000));
                     getWalking().walk(getLocalPlayer().getTile().getRandomizedTile());
                break;
                case LEAVE_RESPAWN:
                Constants.Energy_Barrier = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Energy Barrier") && gameObject.hasAction("Pass"));
                Constants.Energy_Barrier.interact();
                sleepUntil(() -> inArea(Constants.Zamorak_first_floor) || inArea(Constants.Saradomin_first_floor), 7000);
                break;
                case GRAB_BARRICADES:
                break;
                case SLEEP:
                    log("State = SLEEP");
                     sleep(Calculations.random(977, 1477));
                break;
        }
        return Calculations.random(950, 1050);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        //g.drawString("Games exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FISHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING) + ")", 10, 65); //65
                                            
                       
}}

