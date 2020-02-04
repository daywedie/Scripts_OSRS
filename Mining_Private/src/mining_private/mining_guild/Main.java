/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mining_private.mining_guild;

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
@ScriptManifest(author = "T7emon", name = "Mining_Guild_Private", version = 1.0, description = "Mining Guild Private", category = Category.MINING)

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
	if (msg.getMessage().contains("")) {
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
            
        if (getInventory().count(mining_private.mining_guild.Constants.ore) > new Random().nextInt(7 + 1) + 10 || getInventory().isFull()) {
        return State.DROP;
        } else {
      if (mining_private.mining_guild.Constants.Iron_Rocks.distance(getLocalPlayer().getTile()) < 2) {
     return State.MINE;
        }}
                
          return State.SLEEP;
}
        
        
	@Override
	public int onLoop() {
      /*****************************************************************************************************************************/
		switch (getState()) {                  
            case MINE:
            GameObject Left_Rock;
            GameObject Middle_Rock;
            GameObject Right_Rock;
           // int random = new Random().nextInt(5000);
            //log("Random Number = " + random);
          GameObject Rock1 = getGameObjects().getTopObjectOnTile(new Tile(3028, 9720, 0)); //7455 //7468
          GameObject Rock2 = getGameObjects().getTopObjectOnTile(new Tile(3029, 9721, 0)); //7455 
          GameObject Rock3 = getGameObjects().getTopObjectOnTile(new Tile(3030, 9720, 0)); //7455
              
           if (Rock1 != null && Rock1.getID() == 11364) { 
                Rock1.interact();
              sleepUntil(()-> Rock1.getID() == 11390, 1200);
              ore_count++;
            } else {
                if (Rock1.getID() == 11390 && Rock2 != null && Rock2.getID() == 11364) {
                    Rock2.interact();
                   sleepUntil(()-> Rock2.getID() == 11390, 1200);
                   ore_count++;               
               } else {
                    if (Rock2.getID() == 11390 && Rock3 != null && Rock3.getID() == 11364) {
                   // if (random >= 4500)
                        Rock3.interact();
                        sleepUntil(()-> Rock3.getID() == 11390, 1200);
                        ore_count++;
                }}
           }
                break;
               case DROP:
                   getInventory().dropAll(mining_private.mining_guild.Constants.ore);
               break;
                case SLEEP:
                    sleep(Calculations.random(0, 0));
                   //sleep(Calculations.random(117, 457));
                 break;
                }
                return Calculations.random(0, 0);
		//return Calculations.random(127, 777);
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


