package com.fishing.f2p;


import com.fishing.barbarian.*;
import java.awt.Color;
import java.awt.Graphics;
import java.security.SecureRandom;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
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
@ScriptManifest(author = "T7emon", name = "Fishing_F2P", version = 1.0, description = "Fish Shrimps or Salmon", category = Category.FISHING)

public class Main extends AbstractScript {
    
    private Timer timer;
    public static boolean Shrimps_Fishing = false;
    public static boolean Salmon_Fishing = false;
    public static boolean start = false;
    private int fish_count = 0;
    
    NPC Shrimps_Fishing_spot;
    NPC Salmon_Fishing_spot;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.FISHING);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
               java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame().setVisible(true);
            }
        });
            if (!start) {
               sleepUntil(()-> start, 5000000);
            }
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
               sleep(Calculations.random(4000, 7000));
               getWalking().walk(getLocalPlayer().getTile().getRandomizedTile());
               sleep(Calculations.random(3000, 9000));
               getMouse().click(Shrimps_Fishing_spot);
               getTabs().open(Tab.SKILLS);
               sleep(Calculations.random(2000, 5000));
                getSkills().hoverSkill(Skill.FISHING);
               //   getDialogues().clickContinue();
                  //   return State.FISH;
              }
              if (getInventory().count(Constants.Raw_shrimps) > new Random().nextInt(6 + 2) + 10 
                || getInventory().count(Constants.Raw_trout) > new Random().nextInt(6 + 2) + 10 
                || getInventory().count(Constants.Raw_salmon) > new Random().nextInt(6 + 2) + 10 
                || getInventory().isFull()) {
              return State.DROP;
              }
            return State.FISH;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case FISH:
                        if (Shrimps_Fishing) {
                            if (getSkills().getRealLevel(Skill.FISHING) >= 30) {
                                log("Level 30 Reached Time to stop...");
                                this.stop();
                            }
                        Shrimps_Fishing_spot = getNpcs().closest("Fishing spot");
                     if (!getLocalPlayer().isInteracting(Shrimps_Fishing_spot)) {
                         int random = new SecureRandom().nextInt(100) + 1;
                         if (random >= 55 && Shrimps_Fishing_spot.interactForceRight("Net")) {
                        sleepUntil(()-> !getLocalPlayer().isInteracting(Shrimps_Fishing_spot), 240000);  
                         } else {
                             if (random < 55 && Shrimps_Fishing_spot.interact()) {
                             sleepUntil(()-> !getLocalPlayer().isInteracting(Shrimps_Fishing_spot), 240000);  
                             }
                         }}
                     } else {
                    if (Salmon_Fishing) {
                           if (getSkills().getRealLevel(Skill.FISHING) >= 48) {
                                log("Level 48 Reached Time to stop...");
                                this.stop();
                            }
                     Salmon_Fishing_spot = getNpcs().closest("Rod Fishing spot");
                     if (!getLocalPlayer().isInteracting(Salmon_Fishing_spot)) {
                         int random = new SecureRandom().nextInt(100) + 1;
                         if (random >= 55 && Salmon_Fishing_spot.interactForceRight("Lure")) {
                         sleepUntil(()-> !getLocalPlayer().isInteracting(Shrimps_Fishing_spot), 240000);  
                         } else {
                             if (random < 55 && Salmon_Fishing_spot.interact()) {
                        sleepUntil(()-> !getLocalPlayer().isInteracting(Salmon_Fishing_spot), 240000);
                         }}}}}
                break;
                case DROP:
                    getInventory().dropAllExcept(Constants.fly_fishing_rod, Constants.Feathers, Constants.Small_fishing_net);
                     sleepUntil(()-> !getInventory().contains(Constants.Raw_trout) || !getInventory().contains(Constants.Raw_salmon) || !getInventory().contains(Constants.Raw_shrimps) || !getInventory().contains(Constants.Raw_anchovies), 17000);
                     if (Shrimps_Fishing) {
                     getMouse().click(Shrimps_Fishing_spot);
                     } else {
                     if (Salmon_Fishing) {
                     getMouse().click(Salmon_Fishing_spot);
                     getMouse().moveMouseOutsideScreen();
                     }}
                break;
        }
        return Calculations.random(950, 1050);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Fishing exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FISHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING) + ")", 10, 65); //65
                        g.drawString("Fish gained (p/h): " + fish_count + "(" + timer.getHourlyRate(fish_count) + ")", 10, 80);
                                            
                       
}}

