/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thieving_private;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Thieving_Private", version = 1.0, description = "Thieving Private", category = Category.THIEVING)

public class Main extends AbstractScript {
    

    private Timer timer;
    boolean thieving;
    //private PricedItem crate = null;
     //private Tile myLocation = getLocalPlayer().getTile();
    private boolean banking;
    
            public void init() {
               timer = new Timer();
               //crate = new PricedItem("Supply crate",getClient().getMethodContext(), false);
               getSkillTracker().start(Skill.THIEVING);
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
    * Handle Bank method here
    */
    private void bank() {
          banking = true;
          getWalking().walk(getBank().getClosestBankLocation().DRAYNOR.getCenter());
          getBank().open(BankLocation.DRAYNOR);
          GameObject bank = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Bank"));
          bank.interactForceRight("Bank");
          log("STATUS = BANK");
           sleepUntil(() -> getBank().open(), 5000);
          //sleep(Calculations.random(2176, 2347));
          //getBank().depositAllExcept(Constants.KNIFE);
   
          if(getBank().isOpen()) {
          sleep(900);
          getBank().depositAllItems();
          //getBank().depositAllExcept(item -> item != null && item.getName().equals("Tinderbox") || item.getName().equals("Dragon axe") || item.getName().equals("Knife"));
          //getBank().depositAllExcept(item -> item != null && item.getName().toLowerCase().contains("tinderbox") && item.getName().toLowerCase().contains("axe") && item.getName().toLowerCase().contains("knife"));
          //sleep(Calculations.random(1100, 1230));
          sleepUntil(() -> getInventory().emptySlotCount() == 25, 5000);
          getBank().withdraw(Constants.food, Constants.food_amount);
          sleep(Calculations.random(1400, 1900));
          }
          if (getInventory().count(Constants.food) == Constants.food_amount) {
          getBank().close();
          banking = false;
}}
 
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
		log("Welcome to Thieving Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
	}
        

	private enum State {
               THIEF, DROP, BANK, SLEEP
	};

	private State getState() {
           /*
            * Eat food here..
            */
            if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 40) {
                getInventory().interact(item -> item != null && item.getName().contains("Shark"), "Eat");
                //if (cutting) {
                    //return State.CUT;
                //} 
            }
            if (!getInventory().contains(Constants.food)) { 
                return State.BANK;
            }
        if (getInventory().isFull()) {    //if (getInventory().contains(mining_private.Constants.ore)) {
        return State.DROP;
        } else {
            NPC master_farmer = getNpcs().closest(3257);
      if (master_farmer.hasAction("Pickpocket")) { //MASTER FARMER
     return State.THIEF;
        }}
                
          return State.SLEEP;
        }
        
        
	@Override
	public int onLoop() {
      /*****************************************************************************************************************************/
		switch (getState()) {
               case THIEF:
               NPC master_farmer = getNpcs().closest(3257);
               if(master_farmer != null){
               master_farmer.interact("Pickpocket");
                sleepUntil(() -> !getLocalPlayer().isAnimating(), 5000);
               
                   //sleep(3000);
               }
                 //sleepUntil(()-> getLocalPlayer().isHealthBarVisible(), 5000);
                break;
               case DROP:
                   getInventory().dropAllExcept(item -> item != null && item.getName().equals("Toadflax seed") || item.getName().equals("Kwuarm seed") || item.getName().equals("Ranarr seed") || item.getName().equals("Dwarf weed seed") || item.getName().equals("Snapdragon seed") || item.getName().equals("Yew seed") || item.getName().equals("Magic seed") || item.getName().equals("Shark") || item.getName().equals("Torstol seed"));
                   break;
               case BANK:
                   bank();
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
                        g1.drawString("Thieving exp (p/h): " + getSkillTracker().getGainedExperience(Skill.THIEVING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.THIEVING) + ")", 10, 65); //65
                
                     //g1.drawString("Ores gained (p/h): " + ore_count + "(" + timer.getHourlyRate(ore_count) + ")", 10, 80);
                        //g1.drawString("Lost Games (p/h): " + loseStreak + "(" + timer.getHourlyRate(loseStreak) + ")", 10, 250);
                                            
                       
}}



