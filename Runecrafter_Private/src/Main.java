
import java.awt.Graphics;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;

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
    private boolean runecrafting = false;
    private boolean banking;
    private boolean teleported_to_duel;
    private boolean enter;
    private boolean entered;
    private boolean teleport_to_castle;
    private boolean teleport;
    private boolean walking;
    //private final boolean Ring_of_dueling = getInventory().contains((item -> item != null && item.getName().contains("Ring of dueling")));
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.RUNECRAFTING);
               log("Initialized");
            
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
          sleep(Calculations.random(1020, 1222));
          if (!Ring_of_dueling()) {
             getBank().withdraw(Constants.ring_of_duelling8, 1);
          }
          sleep(Calculations.random(797, 937));
          getBank().withdraw(Constants.rune_to_craft, 28);
          sleep(Calculations.random(862, 959));
          getBank().close();
          banking = false;
          runecrafting = true;
          teleport = true;
          //sleep(1000);
}
        
	@Override
	public int onLoop() {
            //if (getInventory().contains(Constants.ring_of_duelling1)) {
               // getInventory().get(Constants.ring_of_duelling1).interact();
                //sleep(Calculations.random(980, 1090));
                //getInventory().drop(Constants.ring_of_duelling1);
           // }
            if (!getInventory().contains(Constants.rune_to_craft) || banking || !Ring_of_dueling()) {
                bank();
            }
            
	   // if (!banking && !getInventory().contains(Ring_of_dueling) && getInventory().contains(Constants.ring_of_duelling8)) {//after banking
                    //getInventory().get(Constants.ring_of_duelling8).interact(); //wear ring of duelling 8
                     //sleep(Calculations.random(1120, 1368));
                    // }
            if (Ring_of_dueling() && runecrafting && !banking && teleport && getInventory().contains(Constants.rune_to_craft)) {
                //getTabs().open(Tab.EQUIPMENT);
                sleep(Calculations.random(1250, 1376));
                getInventory().interact(item -> item != null && item.getName().contains("Ring of dueling"), "Rub");
                //getInventory().get("Ring_of_dueling").interact("Rub");
                sleep(Calculations.random(1120, 1330));
                getDialogues().clickOption(1); //Duel Arena option on ring of dueling
                sleep(Calculations.random(900, 1066));
                teleport = false;
                teleported_to_duel = true;
                //getTabs().open(Tab.INVENTORY);
            }
            if (teleported_to_duel) {
                sleep(2200);
                getWalking().walkExact(Constants.mysterious_ruins_tile);
                //sleep(Calculations.random(3200, 3600));
                if (getGameObjects().closest(Constants.mysterious_ruins_id).isOnScreen() && getInventory().contains(Constants.rune_to_craft)) {
                    enter = true;
                    teleported_to_duel = false;
                }
            }
            if (enter) {
           getInventory().interact(Constants.talisman, "Use");	
           //sleep(Calculations.random(760, 830));
          getGameObjects().closest(Constants.mysterious_ruins_id).interact();
           //sleepUntil(()-> !getLocalPlayer().isAnimating(),1500);
           entered = true;
           sleep(Calculations.random(1800, 1900));
           walking = true;
           enter = false;
            }
            if (getInventory().contains(Constants.rune_to_craft) && entered && walking) {
                //sleep(2000);
                getWalking().walk(Constants.altar_tile);
                sleep(Calculations.random(900, 1100));
                walking = false;
                entered = false;
            }
                if (getGameObjects().closest(Constants.altar_id).isOnScreen() && getInventory().contains(Constants.rune_to_craft) && !walking && !entered) {
               getInventory().interact(Constants.rune_to_craft, "Use");	
                getGameObjects().closest(Constants.altar_id).interact();
                sleep(Calculations.random(1120, 1368));
                 sleepUntil(()-> !getLocalPlayer().isAnimating(),1500);
               //GameObject altar = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Craft-rune"));
               //if (getGameObjects().closest(Constants.altar_id).isOnScreen() && !getInventory().contains(Constants.rune_to_craft)) {
            //if(altar.interact()){
        //sleep(Calculations.random(1600, 1740));
        teleport_to_castle = true;
        //entered = false;
    }//}
            if (teleport_to_castle && !getInventory().contains(Constants.rune_to_craft)) {
            //getTabs().open(Tab.EQUIPMENT);
                //sleep(Calculations.random(1250, 1376));
                getInventory().interact(item -> item != null && item.getName().contains("Ring of dueling"), "Rub");
                //getInventory().get("Ring of dueling").interact("Rub");              
                sleep(Calculations.random(1120, 1368));
                getDialogues().clickOption(2); //cw
                 sleep(Calculations.random(920, 1034));
                teleport_to_castle = false;
                banking = true;
                //getTabs().open(Tab.INVENTORY);
   }
                    //getWidgets().getWidget(WIDGET_ID).interact();
                      //getInventory().get(Constants.iron_ore).interact();
                     //sleepUntil(()-> !getLocalPlayer().isAnimating(),1500);
                    // fletching = false;
                     if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
                     runecrafting = true;
                      }
		return Calculations.random(400, 620);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Runecrafting exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.RUNECRAFTING) , 10, 65);
                       
                                            
                       
}}


