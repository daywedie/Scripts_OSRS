package nmz_private;


import java.awt.Color;
import java.awt.Graphics2D;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.Category;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(author = "T7emon", name = "NMZ_Bot", version = 2.0, description = "Nightmare-Zone Afker", category = Category.MINIGAME)

public class Main extends AbstractScript {
    
    /*
    *@TODO FIX THIS MESS LAZY FUCKER
    */
    
    private Timer timer;
    private boolean createdDream;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.STRENGTH);
               getSkillTracker().start(Skill.ATTACK);
               getSkillTracker().start(Skill.DEFENCE);
               getSkillTracker().start(Skill.RANGED);
               getSkillTracker().start(Skill.HITPOINTS);
               log("Initialized");
            
        }

     private void bank() {
          getBank().open(BankLocation.YANILLE);
          getNpcs().closest("Banker").interactForceRight("Bank");
           sleepUntil(()-> getBank().isOpen() , 2500);
          if (getBank().isOpen()) { 
           getBank().depositAllItems();
           sleepUntil(()-> getInventory().emptySlotCount() == 28 , 2500);
           getBank().withdraw(Constants.Prayer_potion_4, Constants.Prayer_potion_4_count);
           sleepUntil(()-> getInventory().count(Constants.Prayer_potion_4) == Constants.Prayer_potion_4_count , 2500);
           getBank().withdraw(Constants.Overload_4, Constants.Prayer_potion_4_count);
           sleepUntil(()-> getInventory().count(Constants.Overload_4) == Constants.Prayer_potion_4_count , 2500);
           getBank().close();
           sleepUntil(()-> !getBank().isOpen() , 2500);
    }}
   
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You wake up feeling refreshed.")) {
bank();
log("Should Bank...");
	}
}

    private void Dream() {
        NPC Dominic = getNpcs().closest("Dominic Onion");
                            Dominic.interact("Dream");
                            sleep(Calculations.random(2000, 4000));
                            getDialogues().clickOption(4);
                            sleep(Calculations.random(1900, 2700));
                            getDialogues().clickContinue();
                            sleep(Calculations.random(2000, 3100));
                            getDialogues().clickOption(1);
                            log("Created Dream...");
                            createdDream = true;
                            sleep(Calculations.random(2100, 3400)); 
    GameObject vial = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Drink"));
    vial.interact();
         sleep(Calculations.random(3000, 7000));
        getWidgets().getWidgetChild(129, 6, 9).interact();
        log("Entered Dream...");
        createdDream = false;
    }
    
    @Override
	public void onStart() {
            init();
		log("Welcome to NMZ Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Combat levels!.");
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
               BANK, WALK, DREAM, PRAYER, DRINK_POT, SLEEP
	};

	private State getState() {
            //if (getSkillTracker().getGainedExperience(Skill.STRENGTH) > 6500000) {
                   //if (getSkills().getRealLevel(Skill.HITPOINTS) == 99 || timer.formatTime().toLowerCase().contains("04:00:15")) {
                   // if (getSkills().getExperience(Skill.HITPOINTS) >= 13000020 || timer.formatTime().toLowerCase().contains("04:00:15")) {
                    //log("Time to Stop...");
                //sleep(3000);
                //this.stop();
            //}
            if (!getInventory().contains(Constants.Prayer_potion_4) || !getInventory().contains(Constants.Overload_4)) {
                log("State = BANK");
                return State.BANK;
            }
            
            if (!inArea(Constants.NMZ_Dream_Area) && !inArea(Constants.NMZ_Yanille_Area) 
                && getInventory().count(Constants.Overload_4) == Constants.Overload_4_count && getInventory().count(Constants.Prayer_potion_4) == Constants.Prayer_potion_4_count) {
                log("State = WALK");
            return State.WALK;
        }
            
        if (inArea(Constants.NMZ_Yanille_Area) && getInventory().count(Constants.Overload_4) > 5 || getInventory().count(Constants.Prayer_potion_4) >= 10 && !createdDream) {
                log("State = DREAM");
           return State.DREAM;
        }
        
          if (!getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) && inArea(Constants.NMZ_Dream_Area)) {
              log("State = PROTECT_FROM_MELEE");
                return State.PRAYER;
}
                  if (getSkills().getBoostedLevels(Skill.PRAYER) < 13 || getSkills().getBoostedLevels(Skill.HITPOINTS) > 90) {
                      sleep(Calculations.random(3000, 10000));
                      log("State = DRINK_POT");
                      return State.DRINK_POT;
                  }
          return State.SLEEP;
}
        
        
	@Override
	public int onLoop() {
		switch (getState()) {
                    case BANK:
                        bank();
                        break;
                    case WALK:
                        getWalking().walk((Constants.NMZ_Location_Tile).getRandomizedTile(3));
                        sleepUntil(() -> inArea(Constants.NMZ_Yanille_Area), 2700);
                        break;
          		case DREAM:
                        Dream();
                            break;
                            
                case PRAYER:
                if (!getTabs().isOpen(Tab.PRAYER)) {
                getTabs().open(Tab.PRAYER);
                getPrayer().toggle(true, Prayer.PROTECT_FROM_MELEE);
                }
	        break;
		case DRINK_POT:
                    if (getSkills().getBoostedLevels(Skill.HITPOINTS) > 90) {
                    getInventory().interact(item -> item != null && item.getName().contains("Overload"), "Drink");
                    //sleep(3000);
                    }
                    if (getSkills().getBoostedLevels(Skill.PRAYER) < 13) {
                    Calculations.random(3000, 7500);
                    getInventory().interact(item -> item != null && item.getName().contains("Prayer potion"), "Drink");
                    //sleep(3000);
                    }
	        break;
                case SLEEP:
                   sleep(Calculations.random(313, 1549));
                 break;
                }
		return Calculations.random(300, 700);
        }
        
@Override
	public void onPaint(Graphics2D g1){
             //public void onPaint(Graphics g){
            //Graphics2﻿D g = (Graphics2D) g1;
                        g1.setColor(Color.RED);
			//g.drawString("State: " + getState().toString(), 10, 35);
			//g.drawString("State: " + getState().toString(), 10, 35);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g1.drawString("Hitpoints exp (p/h): " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS) + ")", 10, 65);
                        g1.drawString("Attack exp (p/h): " + getSkillTracker().getGainedExperience(Skill.ATTACK) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK) + ")", 10, 80);
                        g1.drawString("Strength exp (p/h): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 10, 95);
                        g1.drawString("Defence exp (p/h): " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE) + ")", 10, 110);
                        g1.drawString("Ranged exp (p/h): " + getSkillTracker().getGainedExperience(Skill.RANGED) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) + ")", 10, 125);
                                            
                       
}}
