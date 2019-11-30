package combat_private.varrock_guards;


import static combat_private.varrock_guards.Constants.Bones;
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
import org.dreambot.api.wrappers.items.GroundItem;
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
@ScriptManifest(author = "T7emon", name = "Varrock_Guards_Killer", version = 1.0, description = "Kills Varrock Guards at Varrock Castle.", category = Category.COMBAT)

public class Main extends AbstractScript {
    
    private Timer timer;
    private int npc_kills = 0;
    private int used_food = 0;
    private int food_in_bank = 0;
    private int bones_buried = 0;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.HITPOINTS);
               getSkillTracker().start(Skill.STRENGTH);
               getSkillTracker().start(Skill.ATTACK);
               getSkillTracker().start(Skill.DEFENCE);
               getSkillTracker().start(Skill.MAGIC);
               getSkillTracker().start(Skill.RANGED);
               getSkillTracker().start(Skill.PRAYER); 
               log("Initialized");
        }

    @Override
	public void onStart() {
      java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Jframe().setVisible(true);
            }
        });
            init();
		log("Welcome to Varrock Guards Killer Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Combat levels!.");
        }
        
            @Override
	public void onExit() {
		log("Stopping Varrock Guards Killer Script...");
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
               
            if (Constants.bury_bones && getInventory().contains(Item -> Item != null && Item.getName().contains("Bones"))) {
                getInventory().interact("Bones", "Bury");
                bones_buried++;
            }
            
            if (!getInventory().contains(Constants.food) && Constants.food_enabled) {
                log("You ran out of food.. Going to bank...");
                return State.BANK;
            }
            
              if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
               }
              
               if (!inArea(Constants.Varrock_Castle) && getInventory().contains(Constants.food)) {
                   return State.WALK;
               }
                            
              if (getLocalPlayer().getHealthPercent() < 40) {
                  return State.EAT;
              }
              
              if (inArea(Constants.Varrock_Castle)) {
                  //log("In Varrock Castle Area!");
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
                     sleepUntil(() -> getBank().isOpen(), Calculations.random(3207, 4017));
                    if (getBank().isOpen() && !getBank().contains(Constants.food) ) {
                    log("No food in bank! Stopping...");
                    this.stop();
                    } else {
                     if (getBank().isOpen()) {
                         //getBank().depositAllItems();
                         //sleepUntil(() -> getInventory().fullSlotCount() == 0, 5000);
                     getBank().withdraw(Constants.food, Constants.food_amount);
                     //sleepUntil(() -> getInventory().count(Constants.food) == Constants.food_amount, 5000);
                     sleepUntil(() -> getInventory().contains(Constants.food), 5000);
                     food_in_bank = getBank().count(Constants.food);
                    // getBank().close();                
                 }}
                break;
                case WALK:
                    getWalking().walk(new Tile(3212, 3460, 0).getRandomizedTile(1));
                    log("Walking to Varrock Castle...");
                    sleep(Calculations.random(1777, 3477));
                break;
                case FIGHT:
                Bones = getGroundItems().closest(Item -> Item != null && Item.getName().contains("Bones"));
                if (Constants.bury_bones && Bones != null && getLocalPlayer().getTile().distance(Bones) <= 2) {
                Bones.interactForceRight("Take");
                sleepUntil(() -> Bones == null, 5000);
        }
                NPC npc = getNpcs().closest(Constants.NPC);
                 int random = new Random().nextInt(1000);
                 log("Random Number: " + random);
                if (random <= 950 && npc.interact()) {
                    log("Left-Click Attacking " + npc.getName());
                    sleepUntil(() -> !npc.isInteractedWith(), 10000);
                    npc_kills++;
                    sleep(Calculations.random(897, 1377));
                } else {
                    if (random > 950 && npc.interactForceRight("Attack")) {
                    log("Right-Click Attacking " + npc.getName());
                    sleepUntil(() -> !npc.isInteractedWith(), 10000);
                    npc_kills++;
                    sleep(Calculations.random(997, 1777));
                    }
                }
                break;
                case EAT:
                    if (getInventory().getRandom(Constants.food).interact()) {
                    log("Eating: " + Constants.food);
                    used_food++;
                    sleep(Calculations.random(1177, 1307));
                    }
                break;
                case SLEEP:
                Calculations.random(277, 577);
                break;
        }
        return Calculations.random(777, 2777);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.WHITE);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Kills: " + npc_kills, 10, 50);
                        g.drawString("Used food: " + used_food, 10, 65);
                        g.drawString("Food left in bank: " + food_in_bank, 10, 80);
                        g.drawString("Hitpoints exp (p/h): " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS) + ")", 10, 95);
                        g.drawString("Strength exp (p/h): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 10, 110);
                        g.drawString("Attack exp (p/h): " + getSkillTracker().getGainedExperience(Skill.ATTACK) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK) + ")", 10, 125);
                        g.drawString("Defence exp (p/h): " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE) + ")", 10, 140);
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MAGIC) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) + ")", 10, 155);
                        g.drawString("Ranged exp (p/h): " + getSkillTracker().getGainedExperience(Skill.RANGED) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) + ")", 10, 170);
                        if (Constants.bury_bones) {
                        g.drawString("Prayer exp (p/h): " + getSkillTracker().getGainedExperience(Skill.PRAYER) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.PRAYER) + ")", 10, 185);
                        g.drawString("Bones Buried: " + bones_buried, 10, 200);
        }}}

