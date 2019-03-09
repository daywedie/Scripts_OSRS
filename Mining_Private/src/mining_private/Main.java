/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mining_private;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Constants;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;
/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Mining_Beta", version = 1.0, description = "Mining Beta", category = Category.MINING)

public class Main extends AbstractScript {
    

    private Timer timer;
    boolean mining;
    private int ore_count = 0;
    //private PricedItem crate = null;
     //private Tile myLocation = getLocalPlayer().getTile();
    
            public void init() {
               timer = new Timer();
               //crate = new PricedItem("Supply crate",getClient().getMethodContext(), false);
               getSkillTracker().start(Skill.MINING);
               log("Initialized");
            
        }
            
           /*
            * Is in area? wether true or false
            */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }return false;
}
 
   /*
    * Handles Game Messages recieved
    */
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("Your inventory is too full to hold any more roots.")) {
            //log("STATUS = FLETCH");
            //fletching = true;
        }
}
    
@Override
	public void onStart() {
            init();
		log("Welcome to Mining Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
	}
        

	private enum State {
               MINE, DROP, SLEEP
	};

	private State getState() {
        //if (mining_private.Constants.ironOre.distance(getLocalPlayer().getTile()) == 1) {
        //mining = true;  
   // }
            
        if (getInventory().count(mining_private.Constants.ore) > new Random().nextInt(6 + 1) + 10 || getInventory().isFull()) {
              //if (getInventory().contains(mining_private.Constants.ore)) {
        return State.DROP;
        } else {
      if (mining_private.Constants.ironOre.distance(getLocalPlayer().getTile()) < 2) {
     return State.MINE;
        }}
                
          return State.SLEEP;
}
        
        
	@Override
	public int onLoop() {
      /*****************************************************************************************************************************/
		switch (getState()) {
               case MINE:
            GameObject ore1 = getGameObjects().getTopObjectOnTile(new Tile(3028, 9720, 0)); //7455 //7468
            GameObject ore2 = getGameObjects().getTopObjectOnTile(new Tile(3029, 9721, 0)); //7455 
            GameObject ore3 = getGameObjects().getTopObjectOnTile(new Tile(3030, 9720, 0)); //7455
            if (ore1 != null && ore1.getID() == 7455) {
                ore1.interact();
              sleepUntil(()-> ore1.getID() == 7468, 1200);
              ore_count++;
            } else {
                if (ore1.getID() == 7468 && ore2 != null && ore2.getID() == 7455) {
                    ore2.interact();
                   sleepUntil(()-> ore2.getID() == 7468, 1200);
                   ore_count++;
               } else {
                    if (ore2.getID() == 7468 && ore3 != null && ore3.getID() == 7455) {
                        ore3.interact();
                        sleepUntil(()-> ore3.getID() == 7468, 1200);
                        ore_count++;
                    }
                }}
                break;
                 /*   case MINE:
            GameObject ore1 = getGameObjects().getTopObjectOnTile(new Tile(3028, 9720, 0)); //7455 //7468
            GameObject ore2 = getGameObjects().getTopObjectOnTile(new Tile(3029, 9721, 0)); //7455 
            GameObject ore3 = getGameObjects().getTopObjectOnTile(new Tile(3030, 9720, 0)); //7455
            ore1.interact();
             sleepUntil(()-> ore1.getID() == 7468, 1670);
            //sleep(1100);
            ore2.interact();
             sleepUntil(()-> ore2.getID() == 7468, 1670);
            ore3.interact();
             sleepUntil(()-> ore3.getID() == 7468, 1670);
                        break;*/
               case DROP:
                   getInventory().dropAll(mining_private.Constants.ore);
               break;
                case SLEEP:
                   sleep(Calculations.random(0, 0));
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
                        g1.drawString("Mining exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MINING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MINING) + ")", 10, 65); //65
                
                      g1.drawString("Ores gained (p/h): " + ore_count + "(" + timer.getHourlyRate(ore_count) + ")", 10, 80);
                        //g1.drawString("Lost Games (p/h): " + loseStreak + "(" + timer.getHourlyRate(loseStreak) + ")", 10, 250);
                                            
                       
}}


