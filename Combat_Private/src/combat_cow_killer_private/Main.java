package combat_cow_killer_private;


import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
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
@ScriptManifest(author = "T7emon", name = "Cow_Killer_Private", version = 1.0, description = "Kill Cows", category = Category.COMBAT)

public class Main extends AbstractScript {
    
    private Timer timer;
    private int kills = 0;
    boolean Strength=false; boolean Attack=false; boolean Defence=false; boolean Magic=false; boolean Range=true;
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.HITPOINTS);
               if (Strength) {
               getSkillTracker().start(Skill.STRENGTH);
               } else {
               if (Attack) {
               getSkillTracker().start(Skill.ATTACK);
               } else {
               if (Defence) {
               getSkillTracker().start(Skill.DEFENCE);
               } else {
               if (Magic) {
               getSkillTracker().start(Skill.MAGIC);
               } else {
               if (Range) {
               getSkillTracker().start(Skill.RANGED);
               }
               log("Initialized");
        }}}}}

    @Override
	public void onStart() {
            init();
		log("Welcome to Cow Killer Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Combat levels!.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("There is no ammo left in your quiver.")) {
            log("There is no ammo left in your quiver.");
           this.stop();
        }
}
       
        	private enum State {
               BANK, FIGHT, EAT, SLEEP
	};
                
        private State getState() {
            
            if (!getInventory().contains(Constants.food) && Constants.food_enabled) {
                log("You need food We dont bank just stop");
                this.stop();
            }
            
              if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
                     return State.FIGHT;
               }
              
              if (getNpcs().closest(Constants.NPC).isOnScreen() && !getNpcs().closest(Constants.NPC).isInCombat() && !getLocalPlayer().isInCombat()) {
                  sleep(Calculations.random(1017, 2117));
                  return State.FIGHT;
              }
              
              if (getLocalPlayer().getHealthPercent() < 40) {
                  return State.EAT;
              }
            
            return State.SLEEP;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                
                case BANK:
                //TODO
                break;
                
                case FIGHT:
                NPC npc = getNpcs().closest(Constants.NPC);
                if (npc.interact()) {
                    sleepUntil(() -> !npc.isDrawMinimapDot(), 10000);
                    kills++;
                }
                break;
                case EAT:
                    getInventory().getRandom(Constants.food).interact();
                break;
                case SLEEP:
                Calculations.random(1717, 2017);
                    break;
        }
        return Calculations.random(977, 1177);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.WHITE);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Kills: " + kills, 10, 50);
                        g.drawString("Hitpoints (p/h): " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS) + ")", 10, 65);
                        if (Strength) {
                        g.drawString("Strength exp (p/h): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 10, 80);
                        } else {
                        if (Attack) {
                        g.drawString("Attack exp (p/h): " + getSkillTracker().getGainedExperience(Skill.ATTACK) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK) + ")", 10, 95);
                        } else {
                       if (Defence) {
                        g.drawString("Defence exp (p/h): " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE) + ")", 10, 110);
                            } else {
                        if (Magic) {
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MAGIC) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) + ")", 10, 125);
                        } else {
                        if (Range) {
                        g.drawString("Ranged exp (p/h): " + getSkillTracker().getGainedExperience(Skill.RANGED) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) + ")", 10, 140);
        }}}}}}}

