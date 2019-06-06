/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agility.falador;

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
@ScriptManifest(author = "T7emon", name = "Agility_Falador", version = 1.0, description = "Agility Falador", category = Category.AGILITY)

public class Main_Falador extends AbstractScript {
    

    private Timer timer;
    boolean banking;
    private int laps = 0;
    private int marks = 0;
    //private GroundItem mark = getGroundItems().closest("Mark of grace");
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
        if (inArea(Constants.ONE_ROOF) 
                || inArea(Constants.TWO_ROOF) 
                || inArea(Constants.THREE_ROOF) 
                || inArea(Constants.FOUR_ROOF) 
                || inArea(Constants.FIVE_ROOF)
                || inArea(Constants.SIX_ROOF)
                 || inArea(Constants.SEVEN_ROOF)
                 || inArea(Constants.AIGHT_ROOF)
                 || inArea(Constants.NINE_ROOF)
                 || inArea(Constants.TEN_ROOF)
                 || inArea(Constants.ELEVEN_ROOF)
                 || inArea(Constants.TWELF_ROOF)) {
            return true;
        }return false;
        }

   /*
    * Handle Bank method here
    */
    private void bank() {
          banking = true;
          getWalking().walk(getBank().getClosestBankLocation().FALADOR_EAST.getCenter());
          getBank().open(BankLocation.FALADOR_EAST);
          GameObject bank = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Bank"));
          bank.interactForceRight("Bank");
          log("STATUS = BANK");
           sleepUntil(() -> getBank().open(), 5000);
          //sleep(Calculations.random(2176, 2347));
          //getBank().depositAllExcept(Constants.KNIFE);
   
          if(getBank().isOpen()) {
          sleep(900);
          //getBank().depositAllExcept(item -> item != null && item.getName().equals("Tinderbox") || item.getName().equals("Dragon axe") || item.getName().equals("Knife"));
          //getBank().depositAllExcept(item -> item != null && item.getName().toLowerCase().contains("tinderbox") && item.getName().toLowerCase().contains("axe") && item.getName().toLowerCase().contains("knife"));
          //sleep(Calculations.random(1100, 1230));
         // sleepUntil(() -> getInventory().emptySlotCount() == 25, 5000);
          getBank().withdraw(Constants.food, 7);
          sleep(4000);
          getBank().withdraw(Item -> Item.getName().contains("Stamina"), 2);
          sleep(Calculations.random(1400, 1900));
          }
          if (getInventory().count(Constants.food) == 7) {
          getBank().close();
          banking = false;
}}
   /*
    * Handles Game Messages recieved
    */
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You climb up the wall...")) {
            //entered = true;
            //fletching = true;
        }
        
}

/*
* Enter roofs area method
*/
    private void enterRoof() {
       
            log("STATUS = ENTER");
        getWalking().walkExact(Constants.START_LOCATION);
        sleepUntil(() -> getLocalPlayer().getTile().equals(Constants.START_LOCATION), 3500);
            GameObject wall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Climb")); //FIRST
    if(wall.interactForceRight("Climb")){
                            sleep(Calculations.random(3000, 3500));
                           entered = true;
    }
    }
    
    private void Roofs() {
          GroundItem mark = getGroundItems().closest(11849);
           /*
            * FIRST ROOF
            */
           if (inArea(Constants.ONE_ROOF)) {
           log("In FIRST_ROOF AREA");
                      if (Constants.ONE_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
            GameObject rope = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3040 && gameObject.getY() == 3343);
    if(rope.interact()){
        sleepUntil(() -> inArea(Constants.TWO_ROOF), 3500);
    }}
           /*
           * SECOND ROOF //unf
           */
     if (inArea(Constants.TWO_ROOF)) {
    log("In SECOND_ROOF AREA");
               if (Constants.TWO_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
      GameObject handhold = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3050 && gameObject.getY() == 3350);
    if(handhold.interact()){
        sleepUntil(() -> inArea(Constants.THREE_ROOF), 3500);
                   
    }}
   /*
    * THIRD ROOF
    */
    if (inArea(Constants.THREE_ROOF)) {
        log ("In THIRD_ROOF AREA");
                   if (Constants.THREE_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
         GameObject gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3048 && gameObject.getY() == 3359);
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.FOUR_ROOF), 3500);
    }}
    
        /*
    * FOURTH_ROOF
    */
        if (inArea(Constants.FOUR_ROOF)) {
        log ("In FOURTH_ROOF AREA");
                   if (Constants.FOUR_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
           GameObject gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3044 && gameObject.getY() == 3361);
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.FIVE_ROOF), 3500);
    }}   
        
                /*
    * FIFTH_ROOF 
    */
        if (inArea(Constants.FIVE_ROOF)) {
        log ("In FIFTH_ROOF AREA");
                   if (Constants.FIVE_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
            GameObject rope = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3034 && gameObject.getY() == 3361);
    if(rope.interact()){
        sleepUntil(() -> inArea(Constants.SIX_ROOF), 3500);
    }} 
    
   /*
    * SIXTH_ROOF
    */
        if (inArea(Constants.SIX_ROOF)) {
        log ("In SIXTH_ROOF AREA");
                   if (Constants.SIX_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
         GameObject rope = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3026 && gameObject.getY() == 3353);
    if(rope.interact()){
        sleepUntil(() -> inArea(Constants.SEVEN_ROOF), 3500);
    }}   
        
           /*
    * SEVEN_ROOF
    */
        if (inArea(Constants.SEVEN_ROOF)) {
        log ("In SEVENTH_ROOF AREA");
                   if (Constants.SEVEN_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
         GameObject gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3016 && gameObject.getY() == 3352);
             //getWalking().walkExact(new Tile(3016, 3346, 3));
    //sleepUntil(() -> getLocalPlayer().getTile() == new Tile(3016, 3346, 3), 3000);
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.AIGHT_ROOF), 3500);
    }}   
        
   /*
    * AIGHT_ROOF
    */
        if (inArea(Constants.AIGHT_ROOF)) {
        log ("In AIGTH_ROOF AREA");
                   if (Constants.AIGHT_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
           GameObject ledge = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3015 && gameObject.getY() == 3345);
    if (ledge.interact()){
        sleepUntil(() -> inArea(Constants.NINE_ROOF), 3500);
    }} 
           /*
    * NINE_ROOF
    */
        if (inArea(Constants.NINE_ROOF)) {
        log ("In NINETH_ROOF AREA");
                   if (Constants.NINE_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
                   sleep(900);
          GameObject ledge = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3011 && gameObject.getY() == 3343);
    if(ledge.interact()){
        sleepUntil(() -> inArea(Constants.TEN_ROOF), 3500);
    }} 
    
           /*
    * TEN_ROOF
    */
        if (inArea(Constants.TEN_ROOF)) {
        log ("In TENTH_ROOF AREA");
                   if (Constants.TEN_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
         GameObject ledge = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3014 && gameObject.getY() == 3335);
    if(ledge.interact()){
        sleepUntil(() -> inArea(Constants.ELEVEN_ROOF), 3500);
    }} 
    /*
    * ELEVEN_ROOF
    */
        if (inArea(Constants.ELEVEN_ROOF)) {
        log ("In ELEVENTH_ROOF AREA");
                   if (Constants.ELEVEN_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
         GameObject ledge = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3018 && gameObject.getY() == 3332);
    if(ledge.interact()){
        sleepUntil(() -> inArea(Constants.TWELF_ROOF), 3500);
    }}
            /*
    * TWELF_ROOF
    */
        if (inArea(Constants.TWELF_ROOF)) {
        log ("In TWELF_ROOF AREA");
               if (Constants.TWELF_ROOF.contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
         GameObject edge = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 3025 && gameObject.getY() == 3332);
    if(edge.interact()){
        sleepUntil(() -> !inArea(Constants.TWELF_ROOF), 3500);
        laps++;
        entered = false;
    }}}
    
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
                if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 8) {
                    this.stop();
                }
                if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 10) {
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
                 }}
                    
                      return State.SLEEP;
                }
       
        
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
			//g.drawString("State: " + getState().toString(), 10, 35);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35); //35
                        g1.drawString("Agility exp (p/h): " + getSkillTracker().getGainedExperience(Skill.AGILITY) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.AGILITY) + ")", 10, 65); //65
                        g1.drawString("Laps (p/h): " + laps + "(" + timer.getHourlyRate(laps) + ")", 10, 80);
                        g1.drawString("Marks (p/h): " + marks + "(" + timer.getHourlyRate(marks) + ")", 10, 95);
                        //g1.drawString("Lost Games (p/h): " + loseStreak + "(" + timer.getHourlyRate(loseStreak) + ")", 10, 250);
                                            
                       
}}



