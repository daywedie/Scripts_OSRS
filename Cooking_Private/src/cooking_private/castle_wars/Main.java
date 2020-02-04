/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cooking_private.castle_wars;

import cooking_private.*;
import java.awt.Graphics;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.WidgetChild;

/**
 *
 * @author t7emon
 */

@ScriptManifest(author = "T7emon", 
name = "Cooking_Bot_CW", 
version = 1.0, 
description = "Makes Fire and uses food on fire at Castle Wars", 
category = Category.COOKING)

public class Main extends AbstractScript {
    
    private Timer timer;
    private boolean cooking = false;
    private boolean banking;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.COOKING);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Cooking Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Cooking levels!.");
        }
        
    private void bank() {
          banking = true;
          //getBank().open(BankLocation.CASTLE_WARS);
          getBank().openClosest();
          sleepUntil(()-> getBank().isOpen(), 1500);
          if (!getBank().contains(Constants.logs) || !getBank().contains(Constants.food)) {
             this.stop();
              }
          getBank().depositAllExcept(Item -> Item.getName().contains("Tinderbox"));
          sleepUntil(()-> getInventory().getEmptySlots() == 27, 1500);
          getBank().withdraw(Constants.logs, 1);
          sleepUntil(()-> getInventory().count(Constants.logs) == 1, 1500);
          getBank().withdraw(Constants.food, 26);
          sleepUntil(()-> getInventory().count(Constants.food) == 26, 1500);
          //getBank().close();
          getMap().interact(new Tile(3000, 3000, 0));
          banking = false;
          cooking = true;
          //sleep(1000);
}
    
    private void Widthdraw() {
        
    }
        
        
	@Override
	public int onLoop() {
            
            
		     if (cooking && !banking) {
                         if (getInventory().contains(Constants.logs)) {
                    getInventory().get(Item -> Item.getName().contains("Tinderbox")).useOn(Constants.logs);
                     sleepUntil(()-> !getInventory().contains(Constants.logs), 1500);
                         }
                    GroundItem logs = getGroundItems().closest("logs");
                     getInventory().get(Constants.food).useOn(logs);
                      sleepUntil(()-> !getInventory().contains(Constants.food), 7000);
                     cooking = false;
                      }
                     if (!getInventory().contains(Constants.food)) {                 
                            bank();
                      }
                     if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
                     cooking = true;
                      }
		return Calculations.random(466, 676);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Cooking exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.COOKING) , 10, 65);
                       
                                            
                       
}}


