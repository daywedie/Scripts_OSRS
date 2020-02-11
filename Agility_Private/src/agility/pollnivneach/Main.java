package agility.pollnivneach;

import java.awt.Color;
import java.awt.Graphics2D;
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
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author T7emon, Tear x, Null x, T7x
 */
@ScriptManifest(author = "T7emon", name = "Agility_Pollnivneach", version = 1.0, description = "Agility Pollnivneach", category = Category.AGILITY)

public class Main extends AbstractScript {
    

    private Timer timer;
    private int laps = 0;
    private int marks = 0;
            
            @Override
	public void onStart() {
               timer = new Timer();
               getSkillTracker().start(Skill.AGILITY);
	       log("Welcome to Agility Bot by T7emon.");
	       log("If you experience any issues while running this script please report them to me on the forums.");
	}
            
           /*
            * Is in area? wether true or false.
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
               if (inArea(Constants.roofs.ROOF_1.getArea()) 
                || inArea(Constants.roofs.ROOF_2.getArea()) 
                || inArea(Constants.roofs.ROOF_3.getArea()) 
                || inArea(Constants.roofs.ROOF_4.getArea()) 
                || inArea(Constants.roofs.ROOF_5.getArea()) 
                || inArea(Constants.roofs.ROOF_6.getArea()) 
                || inArea(Constants.roofs.ROOF_7.getArea()) 
                || inArea(Constants.roofs.ROOF_8.getArea()))  {
            return true;
        }return false;
        }
   /*
    * Handles Game Messages recieved.
    */
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("")) {
        }
}

        	private enum State {
               CLIMB, ROOFS, SLEEP
	};
                
                private State getState() {   
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
                    getInventory().get(Constants.food).interact("Eat");
                }
               /**
                * Drink stamina potion...
                */
                if (getWalking().getRunEnergy() <= 15 && getInventory().contains(item -> item != null && item.getName().contains("Stamina"))) {
                    getInventory().interact(item -> item != null && item.getName().contains("Stamina"), "Drink");
                }
               /**
                * Enter/Climb when not inRoof & Animating & Moving | HealthBarVisible
                */
               if (!inRoof() && !getLocalPlayer().isMoving() && !getLocalPlayer().isAnimating() || getLocalPlayer().isHealthBarVisible()) {
                 return State.CLIMB;
               }
              /**
               * Take mark of grace if one is in distance range..
               * otherwise continue doing roofs.
               */
                   GroundItem mark = getGroundItems().closest(GroundItem -> GroundItem != null && GroundItem.getName().equalsIgnoreCase("Mark of grace"));
                   if (mark != null && mark.distance(getLocalPlayer().getTile()) < 6) {
                       mark.interact("Take");
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
                        sleep(Calculations.random(450, 670));
                    } else {
                        getWalking().walkExact(Basket.getTile());
                        sleepUntil(() -> Basket.isOnScreen(), 4000);
                    }
                break;
                case ROOFS:
                    /*
                    * Rooftop_1
                    */
                    if (inArea(Constants.roofs.ROOF_1.getArea())) {
                         log ("In ROOF_1 Area.");
                         GameObject Market_stall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Market stall") && gameObject.hasAction("Jump-on"));
                         Market_stall.interact();
                          sleepUntil(() -> inArea(Constants.roofs.ROOF_2.getArea()), 5000);
                    }
                    /*
                    * Rooftop_2
                    */
                    if (inArea(Constants.roofs.ROOF_2.getArea())) {
                        log ("In ROOF_2 Area.");
                        sleep(550, 600);
                        getWalking().walk(new Tile(3354, 2975, 1));
                        GameObject Banner = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Banner") && gameObject.hasAction("Grab"));
                        if (Banner.isOnScreen()) {               
                        Banner.interactForceRight("Grab");
                        sleepUntil(() -> inArea(Constants.roofs.ROOF_3.getArea()), 3000);
                    }}
                    /*
                    * Rooftop_3
                    */
                    if (inArea(Constants.roofs.ROOF_3.getArea())) {
                        log ("In ROOF_3 Area");
                        GameObject Gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Gap") && gameObject.hasAction("Leap"));
                        Gap.interact();
                        sleepUntil(() -> inArea(Constants.roofs.ROOF_4.getArea()), 3000);
                    }
                    /*
                    * Rooftop_4
                    */
                    if (inArea(Constants.roofs.ROOF_4.getArea())) {
                        log ("In ROOF_4 Area.");
                        GameObject Tree = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Tree") && gameObject.hasAction("Jump-to"));
                        Tree.interact();
                        sleepUntil(() -> inArea(Constants.roofs.ROOF_5.getArea()), 3000);
                    }
                    /*
                    * Rooftop_5
                    */
                    if (inArea(Constants.roofs.ROOF_5.getArea())) {
                        log ("In ROOF_5 Area.");
                        GameObject Rough_wall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Rough wall") && gameObject.hasAction("Climb"));
                        Rough_wall.interact();
                        sleepUntil(() -> inArea(Constants.roofs.ROOF_6.getArea()), 5000);
                    }
                    /*
                    * Rooftop_6
                    */
                    if (inArea(Constants.roofs.ROOF_6.getArea())) {
                       log ("In ROOF_6 Area.");
                        GameObject Monkeybars = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Monkeybars") && gameObject.hasAction("Cross"));
                        Monkeybars.interactForceRight("Cross");
                        sleepUntil(() -> inArea(Constants.roofs.ROOF_7.getArea()), 3000);
                    }
                    /*
                    * Rooftop_7
                    */
                    if (inArea(Constants.roofs.ROOF_7.getArea())) {
                        log ("In ROOF_7 Area.");
                        GameObject Tree = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Tree") && gameObject.hasAction("Jump-on"));
                        Tree.interact();
                        sleepUntil(() -> inArea(Constants.roofs.ROOF_8.getArea()), 3000);
                    }
                    /*
                    * Rooftop_8
                    */
                    if (inArea(Constants.roofs.ROOF_8.getArea())) {
                       log ("In ROOF_8 Area.");
                        sleep(600);
                         GameObject Drying_line = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Drying line") && gameObject.hasAction("Jump-to"));
                         Drying_line.interact();
                         sleepUntil(() -> !inRoof(), 3000);
                         laps++;
                    }
                break;
            }
    
		return Calculations.random(466, 676); //466, 676
        }

@Override
	public void onPaint(Graphics2D g1){

            //public void onPaint(Graphics g){
            //Graphics2﻿D g = (Graphics2D) g1;
          g1.setColor(Color.MAGENTA);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35); //35
                        g1.drawString("Agility exp (p/h): " + getSkillTracker().getGainedExperience(Skill.AGILITY) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.AGILITY) + ")", 10, 65); //65
                        g1.drawString("Laps (p/h): " + laps + "(" + timer.getHourlyRate(laps) + ")", 10, 80);
                        //g1.drawString("Marks (p/h): " + getInventory().count(Item -> Item != null && Item.getName().equalsIgnoreCase("Mark of grace")) + "(" + timer.getHourlyRate(getInventory().count(Item -> Item != null && Item.getName().equalsIgnoreCase("Mark of grace"))) + ")", 10, 95);
                        g1.drawString("Marks (p/h): " + marks + "(" + timer.getHourlyRate(marks) + ")", 10, 95);
                                            
                       
}}


