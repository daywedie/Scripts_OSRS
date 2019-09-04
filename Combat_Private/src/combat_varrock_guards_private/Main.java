package combat_varrock_guards_private;


import combat_cow_killer_private.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Varrock_Guards_Killer", version = 1.0, description = "Kill Varrock Guards at Varrock palace.", category = Category.COMBAT)

public class Main extends AbstractScript {
    
    private Timer timer;
    private int kills = 0;
    private int ate_food = 0;
    private int food_in_bank = 0;
    boolean banking = false;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.HITPOINTS);
               getSkillTracker().start(Skill.STRENGTH);
               getSkillTracker().start(Skill.ATTACK);
               getSkillTracker().start(Skill.DEFENCE);
               getSkillTracker().start(Skill.MAGIC);
               getSkillTracker().start(Skill.RANGED);
               log("Initialized");
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Varrock Guards Killer Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Combat levels!.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("There is no ammo left in your quiver.")) {
            log("There is no ammo left in your quiver.");
            getWalking().walkExact(new Tile(3212, 3438, 0));
            sleep(5000);
           this.stop();
        }
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
               BANK, WALK, EAT, FIGHT, SLEEP
	};
                
        private State getState() {
            
            if (!getInventory().contains(Constants.food) && Constants.food_enabled) {
                log("You ran out of food.. Walking to bank...");
                banking = true;
                return State.BANK;
            }
            
              if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
               }
              
               if (!inArea(Constants.Varrock_palace) && !banking){
                   return State.WALK;
               }
                            
              if (getLocalPlayer().getHealthPercent() < 40) {
                  return State.EAT;
              }
              
              if (inArea(Constants.Varrock_palace)) {
                  //log("In Varrock palace area!");
              if (getNpcs().closest(Constants.NPC).isOnScreen() && !getNpcs().closest(Constants.NPC).isInCombat() && !getLocalPlayer().isInCombat() && getLocalPlayer().getHealthPercent() >= 40) {
                  sleep(Calculations.random(1017, 2117));
                  return State.FIGHT;
              }}
            
            return State.SLEEP;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                
                case BANK:
                    getBank().open(BankLocation.VARROCK_WEST);
                     sleepUntil(() -> getBank().isOpen(), Calculations.random(2807, 3917));
                    if (getBank().isOpen() && !getBank().contains(Constants.food) ) {
                    log("No food in bank! Stopping...");
                    this.stop();
                    } else {
                     if (getBank().isOpen()) {
                         getBank().depositAllItems();
                         sleepUntil(() -> getInventory().fullSlotCount() == 0, 5000);
                     getBank().withdraw(Constants.food, Constants.food_amount);
                     sleepUntil(() -> getInventory().count(Constants.food) == Constants.food_amount, 5000);
                     food_in_bank = getBank().count(Constants.food);
                     getBank().close();                
                banking = false;
                 }}
                break;
                case WALK:
                    getWalking().walk(new Tile(3212, 3460, 0));
                    log("Walking to Varrock palace...");
                    sleep(Calculations.random(2217, 4117));
                break;
                case FIGHT:
                NPC npc = getNpcs().closest(Constants.NPC);
                
                 int random = new Random().nextInt(1000);
                 log("Random Number: " + random);
                 
                if (random <= 800 && npc.interact()) {
                    log("Attacking " + npc.getName());
                    sleepUntil(() -> !npc.isDrawMinimapDot(), 10000);
                    kills++;
                } else {
                    if (random > 800 && npc.interactForceRight("Attack")) {
                    log("Attacking " + npc.getName());
                    sleepUntil(() -> !npc.isDrawMinimapDot(), 10000);
                    kills++;
                    }
                }
                break;
                case EAT:
                    getInventory().getRandom(Constants.food).interact();
                    log("Eating: " + Constants.food);
                    ate_food++;
                    sleep(Calculations.random(1277, 1407));
                break;
                case SLEEP:
                Calculations.random(1717, 5017);
                break;
        }
        return Calculations.random(977, 1177);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.WHITE);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Kills: " + kills, 10, 50);
                        g.drawString("Ate Food: " + ate_food, 10, 65);
                        g.drawString("Food left in bank: " + food_in_bank, 10, 80);
                        g.drawString("Hitpoints (p/h): " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS) + ")", 10, 95);
                        g.drawString("Strength exp (p/h): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 10, 110);
                        g.drawString("Attack exp (p/h): " + getSkillTracker().getGainedExperience(Skill.ATTACK) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK) + ")", 10, 125);
                        g.drawString("Defence exp (p/h): " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE) + ")", 10, 140);
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MAGIC) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) + ")", 10, 155);
                        g.drawString("Ranged exp (p/h): " + getSkillTracker().getGainedExperience(Skill.RANGED) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) + ")", 10, 170);
        }}

