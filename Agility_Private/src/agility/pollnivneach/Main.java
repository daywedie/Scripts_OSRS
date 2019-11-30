/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agility.pollnivneach;

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
@ScriptManifest(author = "T7emon", name = "Agility_Pollnivneach", version = 1.0, description = "Agility Pollnivneach", category = Category.AGILITY)

public class Main extends AbstractScript {
    

    private Timer timer;
    boolean banking;
    private int laps = 0;
    private int marks = 0;
    private boolean entered;
    
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
        if (inArea(agility.pollnivneach.Constants.FIRST_ROOF) 
                || inArea(agility.pollnivneach.Constants.SECOND_ROOF) 
                || inArea(agility.pollnivneach.Constants.THIRD_ROOF) 
                || inArea(agility.pollnivneach.Constants.FOURTH_ROOF) 
                || inArea(agility.pollnivneach.Constants.FIFTH_ROOF) 
                || inArea(agility.pollnivneach.Constants.SIXTH_ROOF) 
                || inArea(agility.pollnivneach.Constants.SEVENTH_ROOF) 
                || inArea(agility.pollnivneach.Constants.FINAL_ROOF)) {
            return true;
        }return false;
        }
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
    
    
@Override
	public void onStart() {
            init();
		log("Welcome to Agility Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
	}
        
        	private enum State {
               CLIMB, ROOFS, SLEEP
	};
                
                private State getState() {
                    
                GroundItem mark = getGroundItems().closest(11849);
               /**
                * Make sure we don't die..
                */
                if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 7) { //10
                    this.stop();
                }
               /**
                * Eats food when needed..
                */
                if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 10) { //50
                    getInventory().get(agility.pollnivneach.Constants.food).interact("Eat");
                }
               /**
                * Enter roofs again when fallen from a roof.
                */
                if (getLocalPlayer().isHealthBarVisible()) {
                    entered = false;
                }
               /**
                * Check if we entered yes or no..
                */
                if (inRoof()) {
                    entered = true;
                } else {
                    entered = false;
                }
               /**
                * Drink stamina potion...
                */
                if (getWalking().getRunEnergy() <= 15 && getInventory().contains(item -> item != null && item.getName().contains("Stamina"))) {
                    getInventory().interact(item -> item != null && item.getName().contains("Stamina"), "Drink");
                }
               /**
                * Enter/Climb when not entered, banking, Animating or moving..
                */
               if (!entered && !banking && !getLocalPlayer().isAnimating() && !getLocalPlayer().isMoving() && !inRoof()) {
                 return State.CLIMB;
               }
              /**
               * Take mark of grace if one is in distance range..
               * otherwise continue doing roofs.
               */
                   if (mark != null && mark.distance(getLocalPlayer().getTile()) < 6) {
                       mark.interact("Take");
                       sleep(3000);
                       marks++;
                   } else {
                  if (inRoof()) {
                      return State.ROOFS;
                  }
                   }
               
                    
       return State.SLEEP;
}
        
@Override
	public int onLoop() {
            switch (getState()) {
                case CLIMB:
                   GameObject Basket = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Basket") && gameObject.hasAction("Climb-on"));
                    if (Basket.isOnScreen()) {
                        Basket.interactForceRight("Climb-on");
                        entered = true;
                    } else {
                        getWalking().walkExact(Basket.getTile());
                        sleepUntil(() -> Basket.isOnScreen(), 4000);
                    }
                break;
                case ROOFS:
                    if (inArea(Constants.FIRST_ROOF)) {
                         log ("In FIRST_ROOF AREA");
                         GameObject Market_stall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Market stall") && gameObject.hasAction("Jump-on"));
                         Market_stall.interact();
                          sleepUntil(() -> inArea(Constants.SECOND_ROOF), 5000);
                    }
                    if (inArea(Constants.SECOND_ROOF)) {
                        log ("In SECOND_ROOF AREA");
                        sleep(550, 600);
                        getWalking().walk(new Tile(3354, 2975, 1));
                        GameObject Banner = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Banner") && gameObject.hasAction("Grab"));
                        if (Banner.isOnScreen()) {               
                        Banner.interactForceRight("Grab");
                        sleepUntil(() -> inArea(Constants.THIRD_ROOF), 3000);
                    }}
                    if (inArea(Constants.THIRD_ROOF)) {
                        log ("In THIRD_ROOF AREA");
                        GameObject Gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Gap") && gameObject.hasAction("Leap"));
                        Gap.interact();
                        sleepUntil(() -> inArea(Constants.FOURTH_ROOF), 3000);
                    }
                    if (inArea(Constants.FOURTH_ROOF)) {
                        log ("In FOURTH_ROOF AREA");
                        GameObject Tree = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Tree") && gameObject.hasAction("Jump-to"));
                        Tree.interact();
                        sleepUntil(() -> inArea(Constants.FIFTH_ROOF), 3000);
                    }
                    if (inArea(Constants.FIFTH_ROOF)) {
                        log ("In FIFTH_ROOF AREA");
                        GameObject Rough_wall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Rough wall") && gameObject.hasAction("Climb"));
                        Rough_wall.interact();
                        sleepUntil(() -> inArea(Constants.SIXTH_ROOF), 5000);
                    }
                    if (inArea(Constants.SIXTH_ROOF)) {
                       log ("In SIXTH_ROOF AREA");
                        GameObject Monkeybars = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Monkeybars") && gameObject.hasAction("Cross"));
                        Monkeybars.interactForceRight("Cross");
                        sleepUntil(() -> inArea(Constants.SEVENTH_ROOF), 3000);
                    }
                    if (inArea(Constants.SEVENTH_ROOF)) {
                        log ("In SEVENTH_ROOF AREA");
                        GameObject Tree = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Tree") && gameObject.hasAction("Jump-on"));
                        Tree.interact();
                        sleepUntil(() -> inArea(Constants.FINAL_ROOF), 3000);
                    }
                    if (inArea(Constants.FINAL_ROOF)) {
                       log ("In FINAL_ROOF AREA");
                        sleep(600);
                         GameObject Drying_line = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Drying line") && gameObject.hasAction("Jump-to"));
                         Drying_line.interact();
                         sleepUntil(() -> !inRoof(), 3000);
                         laps++;
                         //entered = false;
                    }
                break;
            }
    
    ///////////////////////////////////////////////////////////
		return Calculations.random(466, 676); //466, 676
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


