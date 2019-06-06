/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cooking_private;

import java.awt.Graphics;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.WidgetChild;

/**
 *
 * @author t7emon
 */

@ScriptManifest(author = "T7emon", name = "Cooking_Bot", version = 1.0, description = "Wine Maker", category = Category.COOKING)

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
		log("Enjoy the script, gain some Herblore levels!.");
        }
        
    private void bank() {
          banking = true;
          //getBank().open(BankLocation.CASTLE_WARS);
          getBank().openClosest();
          sleepUntil(()-> getBank().isOpen(), 1500);
          if (!getBank().contains(Constants.grapes) || !getBank().contains(Constants.jug_of_water)) {
             this.stop();
              }
          getBank().depositAllItems();
          sleepUntil(()-> getInventory().getEmptySlots() > 20, 1500);
          getBank().withdraw(Constants.jug_of_water, 14);
          sleepUntil(()-> getInventory().count(Constants.jug_of_water) == 14, 1500);
          getBank().withdraw(Constants.grapes, 14);
          sleepUntil(()-> getInventory().count(Constants.grapes) == 14, 1500);
          //sleep(Calculations.random(622, 788));
          getBank().close();
          banking = false;
          cooking = true;
          //sleep(1000);
}
    
    private void Widthdraw() {
        
    }
        
        
	@Override
	public int onLoop() {
            
            
		     if (cooking && !banking) {
                    getInventory().getRandom(Constants.jug_of_water).useOn(Constants.grapes);
                     sleep(Calculations.random(1120, 1368));
                     WidgetChild Dialog = getWidgets().getWidgetChild(270, 14, 38);
                     Dialog.interact();
                     sleep(Calculations.random(4030, 5099));
                    //getWidgets().getWidget(WIDGET_ID).interact();
                      //getInventory().get(Constants.iron_ore).interact();
                     sleepUntil(()-> !getLocalPlayer().isAnimating(),1500);
                     cooking = false;
                      }
                     if (!getInventory().contains(Constants.grapes) || !getInventory().contains(Constants.jug_of_water)) {                 
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


