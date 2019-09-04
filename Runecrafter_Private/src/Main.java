
import java.awt.Graphics;
import javafx.scene.paint.Color;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author t7emon
 */

@ScriptManifest(author = "T7emon", name = "RC_Bot", version = 1.0, description = "Fire altar", category = Category.RUNECRAFTING)

public class Main extends AbstractScript {
    
    private Timer timer;
    private boolean banking;
    private boolean walking;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.RUNECRAFTING);
               log("Initialized");
            
        }
            
             /*
            * Is in area? wether true or false
            */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }return false;
}
    
        /*
    * Is teleported to duel arena?
    */
    private boolean inDuel() {
        if (inArea(Constants.Duel_Area)) {  
            log("In Duel Area");
            return true;
        }return false;
        }
    /*
    * did you enter mysterious ruins?
    */
        private boolean inRuins() {
        if (inArea(Constants.Altar_Area)) {      
            log("In Altar Area");
            return true;
        }return false;
        }
            
       public boolean Ring_of_dueling() {
        if (getInventory().contains((item -> item != null && item.getName().contains("Ring of dueling")))) {
        return true;
        } else {
        return false;
        }
}

    @Override
	public void onStart() {
            init();
		log("Welcome to Rc Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Runecrafting levels!.");
        }
        // getInventory().interact(item -> item != null && item.getName().contains("Overload"), "Drink");
    private void bank() {
          banking = true;
         getBank().open(BankLocation.CASTLE_WARS);
          sleepUntil(()-> getBank().isOpen() , 2500);
          if (!Ring_of_dueling()) {
             getBank().withdraw(Constants.ring_of_duelling8, 1);
             sleepUntil(()-> getInventory().contains(Constants.ring_of_duelling8) , 2500);
          }
          getBank().withdraw(Constants.rune_to_craft, 28);
          sleepUntil(()-> getInventory().contains(Constants.rune_to_craft) , 2500);
          getBank().close();
          sleepUntil(()-> !getBank().isOpen() , 2500);
          banking = false;
          //sleep(1000);
}
   
            	private enum State {
               BANK, TELEPORT, WALK, ENTER, USE_ALTAR, SLEEP
	};
                
                  private State getState() {
                 GameObject mysterious_ruins = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Mysterious ruins"));
                 GameObject altar = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Altar"));
                 
               if (!getInventory().contains(Constants.rune_to_craft) || !Ring_of_dueling()) {
               return State.BANK;
               } else {
                   if (getInventory().contains(Constants.rune_to_craft) && !inDuel() && Ring_of_dueling() && !inRuins()) {
                   return State.TELEPORT;
               }}      

                 if (inDuel() && !inRuins()) {
                    getWalking().walkExact(Constants.mysterious_ruins_tile);
                    mysterious_ruins.interact();
                    sleep(1000);
                 }
               
               if (inRuins() && getInventory().contains(Constants.rune_to_craft)) {
                   getWalking().walk(Constants.altar_tile);
                   altar.interact();
                   sleepUntil(()-> !getInventory().contains(Constants.rune_to_craft) , 1500);
                   sleep(2000);
                   
               }
               
               if (inRuins() && !getInventory().contains(Constants.rune_to_craft)) {
                   return State.TELEPORT;
               }
               
        return State.SLEEP; 
                  }
                
	@Override
	public int onLoop() {
              switch (getState()) {
                  
                  case BANK:
                      bank();
                      break;
                  
                  case TELEPORT:
                if (!inDuel() && !inRuins()) {
                getInventory().interact(item -> item != null && item.getName().contains("Ring of dueling"), "Rub");
                sleep(Calculations.random(1120, 1330));
                getDialogues().clickOption(1); //Duel Arena option on ring of dueling
                sleepUntil(()-> inDuel() , 4500);
                //TELEPORT TO DUEL ARENA
              } else {
              if (inRuins()) {
                getInventory().interact(item -> item != null && item.getName().contains("Ring of dueling"), "Rub");   
                sleep(Calculations.random(1120, 1368));
                getDialogues().clickOption(2); //cw
                 sleepUntil(()-> !inRuins() , 2500);
                 //TELEPORT TO CASTLE WARS
              }}
              break;          
            }
		return Calculations.random(10, 11);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
                        g.setColor(java.awt.Color.orange);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                         g.drawString("Runecrafting exp (p/h): " + getSkillTracker().getGainedExperience(Skill.RUNECRAFTING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.RUNECRAFTING) + ")", 10, 65); //65
                        //g.drawString("Runecrafting exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.RUNECRAFTING) , 10, 65);
                       
                                            
                       
}}


