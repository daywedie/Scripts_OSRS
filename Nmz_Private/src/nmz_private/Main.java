package nmz_private;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.bank.BankType;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.filter.impl.NameFilter;
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
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(author = "T7emon", name = "NMZ_Bot", version = 1.0, description = "Nightmare-Zone Afker", category = Category.MINIGAME)

public class Main extends AbstractScript {
    
    private Timer timer;
    private String status;
    private int drink;
    boolean entered = false;
    boolean dreamCreated = false;
    boolean banking = false;
    
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
           banking = true;
           getBank().open(BankLocation.YANILLE);
           sleepUntil(()-> getBank().isOpen() , 2500);
          if (getBank().isOpen()) { 
           getBank().depositAllItems();
           sleepUntil(()-> getInventory().emptySlotCount() == 28 , 2500);
           getBank().withdraw(Constants.Prayer_potion_4, 18);
           sleepUntil(()-> getInventory().count(Constants.Prayer_potion_4) == 18 , 2500);
           getBank().withdraw(Constants.Overload_4, 10);
           sleepUntil(()-> getInventory().count(Constants.Overload_4) == 10 , 2500);
           getBank().close();
           sleepUntil(()-> !getBank().isOpen() , 2500);
           banking = false;
    }}
   
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You wake up feeling refreshed.")) {
            log("NMZ GAME ENDED");
            banking = true;
this.dreamCreated = false;
this.entered = false;
this.banking = true;
bank();
log("Should Bank...");
	}
}
    private void createDream() {
        if (!entered && !dreamCreated && !banking) {
        getWalking().walk(Constants.NMZ_LOCATION.getRandomizedTile());
        sleep(7000); //lag?
        NPC Dominic = getNpcs().closest("Dominic Onion");
                            Dominic.interact("Dream");
                            sleep(Calculations.random(6000, 7500));
                            getDialogues().clickOption(4);
                            sleep(Calculations.random(1900, 2700));
                            getDialogues().clickContinue();
                            sleep(Calculations.random(2000, 3100));
                            getDialogues().clickOption(1);
                            sleep(Calculations.random(2200, 3200));
                            getDialogues().clickContinue();
                            dreamCreated = true;
                            log("Created Dream...");
                            sleep(Calculations.random(2100, 3400));
        }}
        
private void enterDream() {
    GameObject vial = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Drink"));
    if(vial.interact()){
         sleep(Calculations.random(3000, 7000));
        getWidgets().getWidgetChild(129, 6, 9).interact();
	entered = true;
        log("Entered Dream...");
        
    }
}
    
    @Override
	public void onStart() {
            init();
		log("Welcome to NMZ Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Combat levels!.");
	}

	private enum State {
               BANK, DREAM, ENTER, DRINK_OVERLOAD, PROTECT_FROM_MELEE, DRINK_PRAYER_POT, SLEEP
	};

	private State getState() {
            //if (getSkillTracker().getGainedExperience(Skill.STRENGTH) > 6500000) {
                   //if (getSkills().getRealLevel(Skill.HITPOINTS) == 99 || timer.formatTime().toLowerCase().contains("04:00:15")) {
                    if (getSkills().getExperience(Skill.HITPOINTS) >= 13000020 || timer.formatTime().toLowerCase().contains("04:00:15")) {
                    log("Time to Stop...");
                sleep(3000);
                this.stop();
            }
            if (getLocalPlayer().isInCombat()) {
                dreamCreated = true;
                entered = true;
                banking = false;
            }
            //int amount = getInventory().count(Constants.Prayer_potion_4);
            //if ((amount < 1)) {
            if (!getInventory().contains(Constants.Prayer_potion_4)) {
                log("State = BANK");
                return State.BANK;
            }
            if (getLocalPlayer().getHealthPercent() == 0) {
                log("State = BANK");
                log("0 Hp");
                return State.BANK;
            }
        if (!dreamCreated && !banking) {
                log("State = DREAM");
           return State.DREAM;
        }
        if (!entered && dreamCreated && !banking) {
                log("State = ENTER");
                return State.ENTER;
            }
         if (entered && dreamCreated && !banking && getSkills().getBoostedLevels(Skill.HITPOINTS) > 90) {
             sleep(Calculations.random(3000, 7000));
             log("State = DRINK_OVERLOAD");
             return State.DRINK_OVERLOAD;
         }
         
          if (entered && dreamCreated && !banking) {
          if (!getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
              log("State = PROTECT_FROM_MELEE");
                return State.PROTECT_FROM_MELEE;
}}
                  if (entered && dreamCreated && !banking && getSkills().getBoostedLevels(Skill.PRAYER) < 13) {
                      sleep(Calculations.random(3000, 10000));
                      log("State = DRINK_PRAYER_POT");
                      return State.DRINK_PRAYER_POT;
                  }
          return State.SLEEP;
}
        
        
	@Override
	public int onLoop() {
		switch (getState()) {
                    case BANK:
                        bank();
                        break;
          		case DREAM:
                        createDream();
                            break;
		case ENTER:
                enterDream();
                break;
		case DRINK_OVERLOAD:
                    getInventory().interact(item -> item != null && item.getName().contains("Overload"), "Drink");
                    sleep(3000);
	        break;
		case PROTECT_FROM_MELEE:
                    if (!getTabs().isOpen(Tab.PRAYER)) {
                    getTabs().open(Tab.PRAYER);
			getPrayer().toggle(true, Prayer.PROTECT_FROM_MELEE);
                    }
			break;
                case DRINK_PRAYER_POT:
                    Calculations.random(3000, 7500);
                    getInventory().interact(item -> item != null && item.getName().contains("Prayer potion"), "Drink");
                    sleep(3000);
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
