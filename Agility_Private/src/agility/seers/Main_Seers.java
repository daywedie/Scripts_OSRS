/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agility.seers;

import java.awt.Color;
import java.awt.Graphics2D;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
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
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Agility_Seers_Beta", version = 1.0, description = "Agility Seers_Beta", category = Category.AGILITY)

public class Main_Seers extends AbstractScript {
    

    private Timer timer;
    boolean banking;
    private int laps = 0;
    private int marks = 0;
    private boolean entered;
    private boolean roofs = true;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.AGILITY);
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
    * Is on roof?
    */
    private boolean inRoof() {
        if (inArea(Constants.FIRST_ROOF) 
                || inArea(Constants.SECOND_ROOF) 
                || inArea(Constants.THIRD_ROOF) 
                || inArea(Constants.FOURTH_ROOF) 
                || inArea(Constants.FINAL_ROOF)) {
            return true;
        }return false;
        }

   /*
    * Handle Bank method here
    */
    private void bank() {
          banking = true;
          getWalking().walk(getBank().getClosestBankLocation().SEERS.getCenter());
          getBank().open(BankLocation.SEERS);
          GameObject bank = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Bank"));
          bank.interactForceRight("Bank");
          log("STATUS = BANK");
           sleepUntil(() -> getBank().open(), 5000);
   
          if(getBank().isOpen()) {
          sleep(900);
          getBank().withdraw(Constants.food, Constants.food_amount);
          sleep(4000);
          getBank().withdraw(Item -> Item.getName().contains("Stamina"), 2);
          sleep(Calculations.random(1400, 1900));
          }
          if (getInventory().count(Constants.food) == Constants.food_amount) {
          getBank().close();
          banking = false;
}}
   /*
    * Handles Game Messages recieved
    */
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("")) {
        }
        
}



/*
* Enter roofs area method
*/
    private void enterRoof() {
       
            log("STATUS = ENTER");
        getWalking().walk(Constants.WALL_LOCATION.getRandomizedTile());
        sleepUntil(() -> getLocalPlayer().getTile().equals(Constants.WALL_LOCATION), 3500);
            GameObject wall = getGameObjects().closest(11373);
    if(wall.interactForceRight("Climb-up")){
                            sleep(Calculations.random(3000, 3500));
                           entered = true;
    }
                           //getWalking().walk(Constants.TREE_LOCATION.getRandomizedTile()); //WALK TO TREE
                            //Sleep(Calculations.random(3000, 3200));
    }
    
    private void Roofs() {
                GameObject gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Jump")); //FIRST
                GameObject rope = getGameObjects().closest(11378);
                GameObject edge = getGameObjects().closest(11377);
               GroundItem mark = getGroundItems().closest(11849);
           /*
            * FIRST ROOF
            */
           if (inArea(Constants.FIRST_ROOF)) {
           log("In FIRST_ROOF AREA");
          if (Constants.FIRST_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.SECOND_ROOF), 3500);
    }}
           /*
           * SECOND ROOF
           */
     if (inArea(Constants.SECOND_ROOF)) {
    log("In SECOND_ROOF AREA");
                   if (Constants.SECOND_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
    if(rope.interact()){
        sleepUntil(() -> inArea(Constants.THIRD_ROOF), 3500);
    }}
   /*
    * THIRD ROOF
    */
    if (inArea(Constants.THIRD_ROOF)) {
        log ("In THIRD_ROOF AREA");
                       if (Constants.THIRD_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.FOURTH_ROOF), 3500);
    }}
    
        /*
    * FOURTH_ROOF
    */
        if (inArea(Constants.FOURTH_ROOF)) {
        log ("In FOURTH_ROOF AREA");
                       if (Constants.FOURTH_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
        getWalking().walk(new Tile(2705, 3472, 3));
        sleepUntil(() -> getLocalPlayer().distance(gap.getTile()) <= 5, 3000);
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.FINAL_ROOF), 3500);
    }}   
       /*
        * FINAL ROOF
        */
       if (inArea(Constants.FINAL_ROOF)) {
        log ("In FINAL_ROOF AREA");
                       if (Constants.FINAL_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
    if(edge.interact()){
        sleepUntil(() -> !inArea(Constants.FINAL_ROOF), 3500);
        laps++;
        getWalking().walk(new Tile(2724, 3474).getRandomizedTile());
        entered = false;
    }}
    }
    
@Override
	public void onStart() {
            init();
		log("Welcome to Agility Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
	}
        
        	private enum State {
               BANK, ENTER, ROOFS, SLEEP
	};
                
                private State getState() {
                if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 10) {
                    this.stop();
                }
                if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 50) {
                    getInventory().get(Constants.food).interact("Eat");
                }
                if (getWalking().getRunEnergy() <= 15) {
                 getInventory().interact(item -> item != null && item.getName().contains("Stamina"), "Drink");
                }
                if (getLocalPlayer().isHealthBarVisible()) {
                    entered = false;
                }
                if(inRoof()) {
                    entered = true;
                }
                
               if (!entered && !banking && !getLocalPlayer().isAnimating() && !getLocalPlayer().isMoving() && !inRoof()) {
                  return State.ENTER;
              } else {
                   if (entered && roofs) {
                   return State.ROOFS;
               }
                    
                      return State.SLEEP;
                }}
       
        
@Override
	public int onLoop() {  
      switch (getState()) {
              case BANK:
              bank();
              break;
              case ENTER:
              enterRoof();
              break;
              case ROOFS:
              Roofs();
              break;
              case SLEEP:
              sleep(Calculations.random(625, 880));
             break;
      }       
    
    ///////////////////////////////////////////////////////////
		return Calculations.random(466, 676);
        }

@Override
	public void onPaint(Graphics2D g1){

            //public void onPaint(Graphics g){
            //Graphics2﻿D g = (Graphics2D) g1;
          g1.setColor(Color.MAGENTA);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35); //35
                        g1.drawString("Agility exp (p/h): " + getSkillTracker().getGainedExperience(Skill.AGILITY) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.AGILITY) + ")", 10, 65); //65
                        g1.drawString("Laps (p/h): " + laps + "(" + timer.getHourlyRate(laps) + ")", 10, 80);
                        g1.drawString("Marks (p/h): " + marks + "(" + timer.getHourlyRate(marks) + ")", 10, 95);
                                            
                       
}}


