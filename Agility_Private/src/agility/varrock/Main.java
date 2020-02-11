package agility.varrock;

import agility.varrock.Constants;
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
@ScriptManifest(author = "T7emon", name = "Agility_Varrock", version = 1.0, description = "Agility Varrock", category = Category.AGILITY)

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
   return inArea(Constants.roofs.ROOF_1.getArea()) 
           || inArea(Constants.roofs.ROOF_2.getArea())
           || inArea(Constants.roofs.ROOF_3.getArea())
           || inArea(Constants.roofs.ROOF_4.getArea())
           || inArea(Constants.roofs.ROOF_5.getArea())
           || inArea(Constants.roofs.ROOF_6.getArea())
           || inArea(Constants.roofs.ROOF_7.getArea())
           || inArea(Constants.roofs.ROOF_8.getArea());
        }

   /*
    * Handles Game Messages recieved
    */
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("")) {
        }
        
}
        
        	private enum State {
                ENTER, ROOFS, SLEEP
	};
                
                private State getState() {
                  /*
                  * Stop before deing..
                */
                if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 4) {
                    this.stop();
                }
                /*
                * Eat food.
                */
                if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 7) {
                    getInventory().get(Constants.food).interact("Eat");
                }
                /*
                * Drink Stamina potion.
                */
                if (getWalking().getRunEnergy() <= 15 && getInventory().contains(item -> item != null && item.getName().contains("Stamina"))) {
                 getInventory().interact(item -> item != null && item.getName().contains("Stamina"), "Drink");
                }
                /*
                * Enter when not inRoof & Animating & Moving | HealthBarVisible.
                */
               if (!inRoof() && !getLocalPlayer().isMoving() && !getLocalPlayer().isAnimating() || getLocalPlayer().isHealthBarVisible()) {
                  return State.ENTER;
               }
               /**
               * Take mark of grace if one is in distance range..
               * otherwise continue doing roofs.
               */
               GroundItem mark = getGroundItems().closest(GroundItem -> GroundItem != null && GroundItem.getName().equalsIgnoreCase("Mark of grace"));
                   if (mark != null && mark.distance(getLocalPlayer().getTile()) <= 5) {
                       mark.interact("Take");
                       sleep(5000);
                       marks++;
                   } else {
                  if (inRoof()) {
                      return State.ROOFS;
                  }}
                   
          return State.SLEEP;
                }    
@Override
	public int onLoop() {  
      switch (getState()) {
              case ENTER:
             /*
              * Enter roofs
            */
            log("STATUS = ENTER");
            GameObject Rough_wall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Rough wall") && gameObject.hasAction("Climb"));
        getWalking().walk(new Tile(3222, 3414, 0));
         sleepUntil(() -> Rough_wall.distance(getLocalPlayer()) <= 3, 3500);
             if  (Rough_wall.isOnScreen() && Rough_wall.interactForceRight("Climb")) {
               sleepUntil(() -> inArea(Constants.roofs.ROOF_1.getArea()), 3500);
    }
              break;
              case ROOFS:
           /*
            * Rooftop_1
            */
           if (inArea(Constants.roofs.ROOF_1.getArea())) {
           log("In ROOF_1 Area");
           sleep(Calculations.random(700, 1000));
          GameObject Clothes_line = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Clothes line") && gameObject.hasAction("Cross"));
          if (Clothes_line.interact()) {
              sleepUntil(() -> inArea(Constants.roofs.ROOF_2.getArea()), 5000);
          }}
          /*
          * Rooftop_2
          */
          if (inArea(Constants.roofs.ROOF_2.getArea())) {
              log("In ROOF_2 Area");
              sleep(Calculations.random(700, 1000));
          GameObject Gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Gap") && gameObject.hasAction("Leap"));
          if (Gap.interact()) {
               sleepUntil(() -> inArea(Constants.roofs.ROOF_3.getArea()), 5000);
          }}
          /*
          * Rooftop_3
          */
          if (inArea(Constants.roofs.ROOF_3.getArea())) {
              log("In ROOF_3 Area");
              sleep(Calculations.random(1777, 2277));
           GameObject Wall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Wall") && gameObject.hasAction("Balance"));
           getWalking().walk(Wall.getTile());
           if (Wall.interact()) {
                sleepUntil(() -> inArea(Constants.roofs.ROOF_4.getArea()), 5000);
           }}
           /*
           * Rooftop_4
           */
           if (inArea(Constants.roofs.ROOF_4.getArea())) {
               log("In ROOF_4 Area");
               sleep(Calculations.random(700, 1000));
             GameObject Gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Gap") && gameObject.hasAction("Leap"));
           if (Gap.interact()) {
             sleepUntil(() -> inArea(Constants.roofs.ROOF_5.getArea()), 5000);  
           }}
           /*
           * Rooftop_5
           */
           if (inArea(Constants.roofs.ROOF_5.getArea())) {
               log("In ROOF_5 Area");
                sleep(Calculations.random(700, 1000));
           getWalking().walk(new Tile(3208, 3395, 3));
           sleep(Calculations.random(3000, 4000));
          GameObject Gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Gap") && gameObject.hasAction("Leap"));
           if (Gap.interact()) {
                sleepUntil(() -> inArea(Constants.roofs.ROOF_6.getArea()), 5000);
           }}
           /*
           * Rooftop_6
           */
           if (inArea(Constants.roofs.ROOF_6.getArea())) {
               log ("In ROOF_6 Area");
              sleep(Calculations.random(700, 1000));
           getWalking().walk(new Tile(3230, 3402, 3));
           sleep(Calculations.random(3000, 4000));
           GameObject Gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Gap") && gameObject.hasAction("Leap"));
           if (Gap.interact()) {
             sleepUntil(() -> inArea(Constants.roofs.ROOF_7.getArea()), 5000);  
           }}
           /*
           * Rooftop_7
           */
           if (inArea(Constants.roofs.ROOF_7.getArea())) {
               log ("In ROOF_7 Area");
              sleep(Calculations.random(700, 1000));
           GameObject Ledge = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Ledge") && gameObject.hasAction("Hurdle"));
           if (Ledge.interact()) {
             sleepUntil(() -> inArea(Constants.roofs.ROOF_8.getArea()), 5000);  
           }}
           /*
           * Rooftop_8
           */
           if (inArea(Constants.roofs.ROOF_8.getArea())) {
               log ("In ROOF_8 Area");
               sleep(Calculations.random(700, 1000));
           GameObject Edge = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Edge") && gameObject.hasAction("Jump-off"));
           if (Edge.interact()) {
             sleepUntil(() -> !inRoof(), 5000);  
           }}
              break;
              case SLEEP:
              break;
      }       
		return Calculations.random(466, 676);
        }

@Override
	public void onPaint(Graphics2D g1){
          g1.setColor(Color.MAGENTA);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35); //35
                        g1.drawString("Agility exp (p/h): " + getSkillTracker().getGainedExperience(Skill.AGILITY) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.AGILITY) + ")", 10, 65); //65
                        g1.drawString("Laps (p/h): " + laps + "(" + timer.getHourlyRate(laps) + ")", 10, 80);
                        //g1.drawString("Marks (p/h): " + getInventory().count(Item -> Item != null && Item.getName().equalsIgnoreCase("Mark of grace")) + "(" + timer.getHourlyRate(getInventory().count(Item -> Item != null && Item.getName().equalsIgnoreCase("Mark of grace"))) + ")", 10, 95);
                        g1.drawString("Marks (p/h): " + marks + "(" + timer.getHourlyRate(marks) + ")", 10, 95);    
                       
}}



