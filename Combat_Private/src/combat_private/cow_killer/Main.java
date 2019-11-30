package combat_private.cow_killer;


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
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.HITPOINTS);
               getSkillTracker().start(Skill.ATTACK);
               getSkillTracker().start(Skill.STRENGTH);
               getSkillTracker().start(Skill.DEFENCE);
               getSkillTracker().start(Skill.RANGED);
               getSkillTracker().start(Skill.MAGIC);
               log("Initialized");
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Cow Killer Bot by T7emon.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("There is no ammo left in your quiver.")) {
            log("There is no ammo left in your quiver.");
           this.stop();
        }
}
       
        	private enum State {
               BANK, FIGHT, EAT
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
              
              NPC npc = getNpcs().closest(NPC -> NPC.getName().contains(Constants.NPC));         
              if (npc != null && npc.isOnScreen() && !npc.isInCombat() && !getLocalPlayer().isInCombat() && !npc.isInteractedWith() && !npc.isInteracting(getLocalPlayer())) {
                 // sleep(Calculations.random(577, 877));
                  return State.FIGHT;
              }
              
              if (getLocalPlayer().getHealthPercent() < 40) {
                  return State.EAT;
              }
            
            return State.FIGHT;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                
                case BANK:
                //TODO
                break;
                
                case FIGHT:
                NPC npc = getNpcs().closest(NPC -> NPC.getName().contains(Constants.NPC));
                if (npc.interact()) {
                    sleepUntil(() -> !npc.isInteractedWith() && !npc.isDrawMinimapDot(), 15000); //!npc.drawnminimap
                    kills++;
                }
                break;
                case EAT:
                    getInventory().getRandom(Constants.food).interact();
                break;
        }
        return Calculations.random(100, 300);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.LIGHT_GRAY);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Kills: " + kills, 10, 50);
                        g.drawString("Hitpoints (p/h): " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS) + ")", 10, 65);
                        g.drawString("Attack exp (p/h): " + getSkillTracker().getGainedExperience(Skill.ATTACK) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK) + ")", 10, 80);
                        g.drawString("Strength exp (p/h): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 10, 95);
                        g.drawString("Defence exp (p/h): " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE) + ")", 10, 110);
                        g.drawString("Ranged exp (p/h): " + getSkillTracker().getGainedExperience(Skill.RANGED) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) + ")", 10, 125);
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MAGIC) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) + ")", 10, 140);
        }}

