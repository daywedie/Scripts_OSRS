package agility.seers;

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
@ScriptManifest(author = "T7emon", name = "Agility_Seers", version = 1.0, description = "Agility Seers", category = Category.AGILITY)

public class Main extends AbstractScript {
    

    private Timer timer;
    private int marks = 0;
    private int laps = 0;
    
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
               if (inArea(Constants.roofs.ROOF_1.getArea()) 
                || inArea(Constants.roofs.ROOF_2.getArea()) 
                || inArea(Constants.roofs.ROOF_3.getArea()) 
                || inArea(Constants.roofs.ROOF_4.getArea())
                || inArea(Constants.roofs.ROOF_5.getArea())) {
            return true;
        }return false;
        }

   /*
    * Handle Bank method here
    */
    /*private void bank() {
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
}}*/
    
   /*
    * Handles Game Messages recieved
    */
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("")) {
        }
        
}
        
        	private enum State {
               BANK, ENTER, ROOFS, SLEEP
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
              case BANK:
              //bank();
              break;
              case ENTER:
            log("STATUS = ENTER");
            GameObject wall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Wall"));
        getWalking().walk(wall.getTile());
             if  (wall.isOnScreen() && wall.interactForceRight("Climb-up")) {
                            sleepUntil(() -> inArea(Constants.roofs.ROOF_1.getArea()), 3500);
    }
              break;
              case ROOFS:
          GroundItem mark = getGroundItems().closest(GroundItem -> GroundItem != null && GroundItem.getName().equalsIgnoreCase("Mark of grace"));
           /*
            * Rooftop_1
            */
           if (inArea(Constants.roofs.ROOF_1.getArea())) {
           log("In ROOF_1 Area.");
          if (Constants.roofs.ROOF_1.getArea().contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
          GameObject gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 2720 && gameObject.getY() == 3492);
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.roofs.ROOF_2.getArea()), 3500);
    }}
           /*
           * Rooftop_2
           */
        if (inArea(Constants.roofs.ROOF_2.getArea())) {
            log("In ROOF_2 Area.");
             if (Constants.roofs.ROOF_2.getArea().contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
    GameObject rope = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 2710 && gameObject.getY() == 3489);
    if(rope.interact()){
        sleepUntil(() -> inArea(Constants.roofs.ROOF_3.getArea()), 3500);
    }}
   /*
    * Rooftop_3
    */
    if (inArea(Constants.roofs.ROOF_3.getArea())) {
        log ("In ROOF_3 Area.");
               if (Constants.roofs.ROOF_3.getArea().contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
     GameObject gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getX() == 2710 && gameObject.getY() == 3476);
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.roofs.ROOF_4.getArea()), 3500);
    }}
    
    /*
    * Rooftop_4
    */
        if (inArea(Constants.roofs.ROOF_4.getArea())) {
        log ("In ROOF_4 Area.");
                       if (Constants.roofs.ROOF_4.getArea().contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
        GameObject gap = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Jump"));
    if(gap.interact()){
        sleepUntil(() -> inArea(Constants.roofs.ROOF_5.getArea()), 3500);
    }}   
       /*
        * Rooftop_5
        */
       if (inArea(Constants.roofs.ROOF_5.getArea())) {
        log ("In ROOF_5 Area.");
                       if (Constants.roofs.ROOF_5.getArea().contains(mark)) {
               mark.interactForceRight("Take");
               sleepUntil(() -> !mark.exists(), 3500);
               marks++;
           }
     GameObject edge = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Jump"));
    if(edge.interact()){
        sleepUntil(() -> !inArea(Constants.roofs.ROOF_5.getArea()), 3500);
        laps++;
        getWalking().walk(new Tile(2724, 3474).getRandomizedTile());
    }}
              break;
              case SLEEP:
              sleep(Calculations.random(625, 880));
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
                        g1.drawString("Marks (p/h): " + marks + "(" + timer.getHourlyRate(marks) + ")", 10, 95);  
                                            
                       
}}


