package com.fishing.lumbridge;


import com.fishing.barbarian.*;
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
@ScriptManifest(author = "T7emon", name = "Fishing_Lumbridge", version = 1.0, description = "Fish Shrimps or Salmon", category = Category.FISHING)

public class Main extends AbstractScript {
    
    private Timer timer;
    private int fish_count = 0;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.FISHING);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Fishing Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Fishing levels!.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You catch a trout.") || msg.getMessage().contains("You catch a salmon") || msg.getMessage().contains("You catch some shrimps.")) {
           fish_count++;
        }
}
       
        	private enum State {
               FISH, DROP
	};
                
        private State getState() {
            
              if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
                     return State.FISH;
               }
              if (getInventory().count(Constants.Raw_trout) > new Random().nextInt(6 + 1) + 10 
                || getInventory().count(Constants.Raw_salmon) > new Random().nextInt(6 + 1) + 10 
                 || getInventory().count(Constants.Raw_shrimps) > new Random().nextInt(6 + 1) + 10 
                //|| getInventory().count(Constants.Leaping_sturgeon) > new Random().nextInt(6 + 1) + 10 
                || getInventory().isFull()) {
              return State.DROP;
              }
            return State.FISH;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case FISH:
                    NPC Fishing_spot = getNpcs().closest("Fishing spot");
                     if (!getLocalPlayer().isInteracting(Fishing_spot) && Fishing_spot.interactForceRight("Net")) {
                        sleepUntil(()-> !getLocalPlayer().isInteracting(Fishing_spot), 240000);
                    }
                break;
                case DROP:
                    getInventory().dropAllExcept(Constants.fly_fishing_rod, Constants.Feathers, Constants.Small_fishing_net);
                     sleepUntil(()-> !getInventory().contains(Constants.Raw_trout) || !getInventory().contains(Constants.Raw_salmon) || !getInventory().contains(Constants.Raw_shrimps) || !getInventory().contains(Constants.Raw_anchovies), 240000);
                break;
        }
        return Calculations.random(950, 1050);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Fishing exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FISHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING) + ")", 10, 65); //65
                        g.drawString("Fish gained (p/h): " + fish_count + "(" + timer.getHourlyRate(fish_count) + ")", 10, 110);
                                            
                       
}}

