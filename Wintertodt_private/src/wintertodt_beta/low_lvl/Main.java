/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wintertodt_beta.low_lvl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.bank.BankType;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.Category;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;
import wintertodt_beta.low_lvl.Constants;
import wintertodt_private.utils.PricedItem;

@ScriptManifest(author = "T7emon", name = "Wintertodt_Beta", version = 1.0, description = "Wintertodt Beta", category = Category.MINIGAME)

public class Main extends AbstractScript {
    

    private Timer timer;
    boolean banking;
    boolean cutting;
    private boolean gameEnd;
    private boolean gameStarted;
    private boolean feed;
    private boolean fletching;
    private int winStreak = 0; //COUNT WON GAMES
    private int loseStreak = 0; //COUNT LOST GAMES
    private int amount_fletched;
    //private PricedItem crate = null;
     //private Tile myLocation = getLocalPlayer().getTile();
    
            public void init() {
               timer = new Timer();
               //crate = new PricedItem("Supply crate",getClient().getMethodContext(), false);
               getSkillTracker().start(Skill.FIREMAKING);
               getSkillTracker().start(Skill.WOODCUTTING);
               getSkillTracker().start(Skill.FLETCHING);
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
          getWalking().walk(getBank().getClosestBankLocation().WINTERTODT.getCenter());
          getBank().open(BankLocation.WINTERTODT);
          GameObject bank = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Bank"));
          bank.interactForceRight("Bank");
          log("STATUS = BANK");
           sleepUntil(() -> getBank().open(), Calculations.random(4500, 5000));
          //sleep(Calculations.random(2176, 2347));
          //getBank().depositAllExcept(Constants.KNIFE);
   
          if(getBank().isOpen()) {
          sleep(Calculations.random(800, 950));
          getBank().depositAllExcept(item -> item != null && item.getName().equals("Tinderbox") || item.getName().toLowerCase().endsWith("axe") || item.getName().equals("Knife"));
          //getBank().depositAllExcept(item -> item != null && item.getName().toLowerCase().contains("tinderbox") && item.getName().toLowerCase().contains("axe") && item.getName().toLowerCase().contains("knife"));
          //sleep(Calculations.random(1100, 1230));
          sleepUntil(() -> getInventory().emptySlotCount() == 25, 5000);
          getBank().withdraw(Constants.food, Constants.food_amount);
          sleep(Calculations.random(1390, 1895));
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
    	if (msg.getMessage().contains("You have helped enough to earn a supply crate.")) {
            //Constants.roots_amount = 10;
            //Constants.kindling_amount = 10;
            //log("STATUS = FLETCH");
            //fletching = true;
        }
        
	if (msg.getMessage().contains("Your inventory is too full to hold any more roots.")) {
            //log("STATUS = FLETCH");
            //fletching = true;
        }
        
        if (msg.getMessage().contains("You did not earn enough points to be worthy of a gift from the citizens of Kourend this time.")) {
            //log("STATUS = gameEnded without enough points");
           //gameEnd = true;
           loseStreak++;
        }
        
             if (msg.getMessage().contains("You have gained a supply crate!")) {
            //log("STATUS = gameEnded" + "Supply crate = true");
           //gameEnd = true;
        }
        
         if (msg.getMessage().contains("The cold of the Wintertodt seeps into your bones.") && cutting) {
            //log("Attempt to cut again");
          //cut(); //ugly way to do this but fuckit im lazy
         }
        
        if (msg.getMessage().contains("The freezing cold attack of the Wintertodt's magic hits you.")) {
           //feed = true;
        }
}
/*
* Enter Wintertodt area method
*/
    private void enterWintertodt() {
            log("STATUS = ENTER");
        getWalking().walk(Constants.DOOR_LOCATION.getRandomizedTile());
            GameObject door = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Enter"));
                    sleepUntil(() -> door.isOnScreen(), Calculations.random(3470, 3530));
    if(door.interact()){
     sleepUntil(() -> inArea(Constants.Wintertodt), Calculations.random(900, 1100));
                            sleep(Calculations.random(2534, 3046));
                            getWalking().walk(Constants.TREE_LOCATION.getRandomizedTile()); //WALK TO TREE
                            sleep(Calculations.random(2951, 3258));
                            if (inArea(Constants.Wintertodt)) {
                            log("Entered Wintertodt Area");
        }}}
    /*
    * Leave wintertodt area method
    */
    private void leaveWintertodt() {
            log("STATUS = LEAVE");
            sleep(Calculations.random(980, 1320));
        getWalking().walk(Constants.LEAVE_LOCATION.getRandomizedTile());
            GameObject door = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Enter"));
    sleepUntil(() -> door.isOnScreen(), Calculations.random(3509, 3544));
    if(door.interact()){
    sleepUntil(() -> !inArea(Constants.Wintertodt), Calculations.random(4998, 5119)); //5000
    if (!inArea(Constants.Wintertodt)) {
        log("Succesfully left Wintertodt Area");
    }
        }}
    
    /*
    * Cuts the tree inside wintertodt area method
    */
    private void cut() {
        if (!getLocalPlayer().isAnimating()) {
                GameObject tree = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Chop"));
                if(tree.interact()){
                    log("STATUS = CUTTING");
                    sleepUntil(() -> !getLocalPlayer().isAnimating(), Calculations.random(13900, 13950));
                 //sleep(Calculations.random(4000, 5000));
                  }
    }}
    /*
    * //Fletching method
    */
    private void fletch() {
        getInventory().get(Constants.knife).useOn(Constants.roots);
            sleep(Calculations.random(3502, 4008));
            log("STATUS = FLETCHING");
         sleepUntil(() -> !getLocalPlayer().isAnimating(), Calculations.random(12040, 13011));
    }
    
@Override
	public void onStart() {
            init();
		log("Welcome to Wintertodt Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
	}
        

	private enum State {
               BANK, ENTER, CUT, FLETCH, FEED, LEAVE, SLEEP
	};

	private State getState() {
             //if (getSkills().getExperience(Skill.FIREMAKING) >= 13000000) {
              //   leaveWintertodt();
                 //  sleep(Calculations.random(4500, 5000));
                 //   this.stop();
            // }
           /*
            * Make Sure we dont die..
            */
            if (!getInventory().contains(Constants.food) && inArea(Constants.Wintertodt) && (getSkills().getBoostedLevels(Skill.HITPOINTS) <= 2)) {
                leaveWintertodt();
                   sleep(Calculations.random(4500, 5000));
                    this.stop();
           }
            
           /*
            * Eat food here..
            */
            if (getSkills().getBoostedLevels(Skill.HITPOINTS) <= 4) {
                getInventory().interact(item -> item != null && item.getName().contains("Tuna"), "Eat"); //Shark
                //if (cutting) {
                    //return State.CUT;
                //} 
            }
            
            
           /*
            * do this for banking
            */
            if (getInventory().count(Constants.food) < Constants.food_amount && !inArea(Constants.Wintertodt) && !getBank().isOpen() || !inArea(Constants.Wintertodt) && getInventory().emptySlotCount() < 18) {
                return State.BANK;
            }
            
        /*
         * Check if you are in wintertodt area, if not enter after banking.
         */        
       if(!inArea(Constants.Wintertodt)){
                log("Not in Area");
                 cutting = false;
                 fletching = false;
                 //feed = false;        
                if (!banking) {
                return State.ENTER;
                }}
       
           /*
            * Check if game has started..
            */
                if (inArea(Constants.Wintertodt) && !getWidgets().getWidgetChild(396,3).getText().contains("The Wintertodt returns")) {
                    log("gameStarted = true");
                  gameStarted = true;
                } else {
                   gameStarted = false;
                }
                /*
                * Handle Stuff inside wintertodt
                * DO YOU NEED CUTTING? OR DO YOU NEED FLETCHING? IF NONE PASS ON TO FEEDING BRAZIER CASE
                */
           if (inArea(Constants.Wintertodt) && gameStarted) {
                log("In Area");
              if (!fletching && !getInventory().contains(Constants.kindling) && getInventory().count(Constants.roots) <= Constants.roots_amount) {
                    cutting = true;
              } else {
                 if (getInventory().count(Constants.roots) >= Constants.roots_amount) { //8
                     cutting = false;
                     fletching = true;
                 } else {
                 if (getInventory().count(Constants.kindling) >= Constants.kindling_amount) {
                  fletching = false;
                }}}
                 
                 if (cutting) {
                     return State.CUT;
                 }
                 
                 if (fletching) {
                     return State.FLETCH;
                 }
                 
              if (!cutting && !fletching && getInventory().contains(Constants.kindling))
                     //feed = true;
                     return State.FEED;
        }
           /*
           * Add this for more points
           */
           //if (getWidgets().getWidgetChild(396,21).getText().contains("Wintertodt's Energy: 14%")) {
            //   cutting = false;
            //   fletching = false;
             //  return State.FEED;
           //}
           
           /*
            * Check if game has Ended & Leave
            */
                if (inArea(Constants.Wintertodt) && getWidgets().getWidgetChild(396,3).getText().contains("The Wintertodt returns") && getInventory().count(Constants.food) < Constants.food_amount
                || inArea(Constants.Wintertodt) && getWidgets().getWidgetChild(396,3).getText().contains("The Wintertodt returns") && getInventory().contains(Constants.rewardbox)) {
                return State.LEAVE;
                }
                
          return State.SLEEP;
}
        
        
	@Override
	public int onLoop() {
      /*****************************************************************************************************************************/
		switch (getState()) {
                    case BANK:
                   bank();
                    break;
                    case ENTER:
                        enterWintertodt();
                    break;
		    case CUT:
                   cut();
	            break;
		    case FLETCH:
                    fletch();
	            break;
                 case FEED:
                 GameObject brazier = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Feed"));
                 if(brazier.interact()){
                     log("Feed brazier");
                     sleepUntil(() -> !getInventory().contains(Constants.kindling), Calculations.random(5300, 5500));
                 GameObject unlitbrazier = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Light"));
                 if(unlitbrazier.interact()){
                     log("Light brazier");
                     sleepUntil(() -> !getLocalPlayer().isAnimating(), 777);
                 }
                 }
                break;
                case LEAVE:
                if (getInventory().contains(Constants.rewardbox)) {
                    winStreak++;
                    //crate.update();
                    //sleep(1200);
                    getInventory().get(Constants.rewardbox).interact("Open");
                    sleepUntil(() -> !getInventory().contains(Constants.rewardbox), Calculations.random(1900, 2100));
                    //sleep(1500);
                }
                leaveWintertodt();
                break;
                case SLEEP:
                   sleep(Calculations.random(276, 398));
                 break;
                }
		return Calculations.random(244, 439);
        }

@Override
	public void onPaint(Graphics2D g1){

            //public void onPaint(Graphics g){
            //Graphics2﻿D g = (Graphics2D) g1;
          g1.setColor(Color.RED);
			//g.drawString("State: " + getState().toString(), 10, 35);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 175); //35
                                                g1.drawString("Won Games (p/h): " + winStreak + "(" + timer.getHourlyRate(winStreak) + ")", 10, 190);
                        g1.drawString("Lost Games (p/h): " + loseStreak + "(" + timer.getHourlyRate(loseStreak) + ")", 10, 205);
                        g1.drawString("Firemaking exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FIREMAKING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FIREMAKING) + ")", 10, 220); //65
                        g1.drawString("Woodcutting exp (p/h): " + getSkillTracker().getGainedExperience(Skill.WOODCUTTING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.WOODCUTTING) + ")", 10, 235); //80
                        g1.drawString("Fletching exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FLETCHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FLETCHING) + ")", 10, 250); //95
                        //g1.drawString("Won Games (p/h): " + winStreak + "(" + timer.getHourlyRate(winStreak) + ")", 10, 235);
                        //g1.drawString("Lost Games (p/h): " + loseStreak + "(" + timer.getHourlyRate(loseStreak) + ")", 10, 250);
                                            
                       
}}

