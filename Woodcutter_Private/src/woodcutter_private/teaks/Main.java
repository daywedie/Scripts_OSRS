/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woodcutter_private.teaks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

/**
 *
 * @author cyborg
 */
@ScriptManifest(author = "T7emon", name = "Woodcutting_Private", version = 0.1, description = "Woodcutting Private", category = Category.WOODCUTTING)
public class Main 
extends AbstractScript {
    
public boolean banking = false;
    private Timer timer;

    public void bank() {
            banking = true;
            getBank().open(BankLocation.DRAYNOR);
            getBank().depositAll("Willow logs");
            sleep(3000);
            getBank().close();
            banking = false;
            getWalking().walkExact(new Tile(3085, 3238));
            sleep(2000);
    }
    
	@Override
    public void onStart() {
        timer = new Timer();
        getSkillTracker().start(Skill.WOODCUTTING);
        log("Starting!");
    }
    
    	private enum State {
               CUT, DROP, BANK, SLEEP
	};
        
        private State getState() {
              if (getInventory().count(Constants.teak_logs) > new Random().nextInt(6 + 1) + 10 || getInventory().isFull()) {       
                  return State.DROP;
        } else {
                  return State.CUT;
              }
        }
    @Override
    public int onLoop() {
        switch (getState()) {
            case DROP:
            getInventory().dropAll(Constants.teak_logs); 
             break;
            case CUT:
            GameObject tree = getGameObjects().closest(Constants.tree);
            if (tree.isOnScreen()) {
            if (tree.interact()) { 
            //tree.interactForceRight("Chop down");
            sleep(3000, 4000);
            sleepUntil(() -> !getLocalPlayer().isAnimating(), 15000); 
          // sleepUntil(() -> tree == null, 15000); 
            }}
            break;
        }
       return Calculations.random(0, 0);
}
    
    @Override
	public void onPaint(Graphics2D g1){

            //public void onPaint(Graphics g){
            //Graphics2﻿D g = (Graphics2D) g1;
          g1.setColor(Color.RED);
			//g.drawString("State: " + getState().toString(), 10, 35);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35); //35
                                         //       g1.drawString("Won Games (p/h): " + winStreak + "(" + timer.getHourlyRate(winStreak) + ")", 10, 190);
                        //g1.drawString("Lost Games (p/h): " + loseStreak + "(" + timer.getHourlyRate(loseStreak) + ")", 10, 205);
                        g1.drawString("Woodcutting exp (p/h): " + getSkillTracker().getGainedExperience(Skill.WOODCUTTING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.WOODCUTTING) + ")", 10, 65); //65
                
                      //g1.drawString("Logs gained (p/h): " + ore_count + "(" + timer.getHourlyRate(ore_count) + ")", 10, 80);
                        //g1.drawString("Lost Games (p/h): " + loseStreak + "(" + timer.getHourlyRate(loseStreak) + ")", 10, 250);
                                            
                       
}

    @Override
    public void onExit() {
    	log("Stopping!"); 
    }}