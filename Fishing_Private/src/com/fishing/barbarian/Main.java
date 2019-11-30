package com.fishing.barbarian;


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
@ScriptManifest(author = "T7emon", name = "Fishing_Private", version = 1.0, description = "Barbarian Fishing", category = Category.FISHING)

public class Main extends AbstractScript {
    
    private Timer timer;
    NPC Fishing_spot;
    private int fish_count = 0;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.FISHING);
               getSkillTracker().start(Skill.AGILITY);
               getSkillTracker().start(Skill.STRENGTH);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Barbarian Fishing Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Fishing levels!.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You catch a leaping trout.") || msg.getMessage().contains("You catch a leaping salmon.") || msg.getMessage().contains("You catch a leaping sturgeon.")) {
           fish_count++;
        }
}
       
        	private enum State {
               FISH, DROP
	};
                
        private State getState() {
            
            if (!getInventory().contains(Constants.Barbarian_rod) || !getInventory().contains(Constants.Feathers)) {
                log("You need a Barbarian rod & Feathers to use this script!");
                this.stop();
            }
            
              if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
                     return State.FISH;
               }
              if (getInventory().count(Constants.Leaping_trout) > new Random().nextInt(6 + 1) + 10 
                || getInventory().count(Constants.Leaping_salmon) > new Random().nextInt(6 + 1) + 10 
                || getInventory().count(Constants.Leaping_sturgeon) > new Random().nextInt(6 + 1) + 10 
                || getInventory().isFull()) {
              return State.DROP;
              }
            return State.FISH;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case FISH:
                     Fishing_spot = getNpcs().closest("Fishing spot");
                     if (!getLocalPlayer().isInteracting(Fishing_spot) && Fishing_spot.interactForceRight("Use-rod")) {
                        sleepUntil(()-> !getLocalPlayer().isInteracting(Fishing_spot), 240000);
                    }
                break;
                case DROP:
                   // getInventory().dropAllExcept(Constants.Barbarian_rod, Constants.Feathers);
                     getInventory().dropAll(Item -> Item != null && Item.getName().contains("Leaping"));
                     sleepUntil(()-> !getInventory().contains(Constants.Leaping_trout) || !getInventory().contains(Constants.Leaping_salmon) || !getInventory().contains(Constants.Leaping_sturgeon) , 240000);
                     sleep(Calculations.random(977, 1477));
                     getMouse().click(Fishing_spot);
                break;
        }
        return Calculations.random(950, 1050);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Fishing exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FISHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING) + ")", 10, 65); //65
                        g.drawString("Agility exp (p/h): " + getSkillTracker().getGainedExperience(Skill.AGILITY) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.AGILITY) + ")", 10, 80); //80
                        g.drawString("Strength exp (p/h): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 10, 95); //65
                        g.drawString("Fish gained (p/h): " + fish_count + "(" + timer.getHourlyRate(fish_count) + ")", 10, 110);
                                            
                       
}}

