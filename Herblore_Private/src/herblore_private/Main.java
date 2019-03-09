/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herblore_private;

import java.awt.Graphics;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;

/**
 *
 * @author t7emon
 */

@ScriptManifest(author = "T7emon", name = "Herblore_Bot", version = 1.0, description = "Make Strength Pots", category = Category.HERBLORE)

public class Main extends AbstractScript {
    
    private Timer timer;
    private boolean herblore = false;
    private boolean banking;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.HERBLORE);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Herblore Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Herblore levels!.");
        }
        
    private void bank() {
          banking = true;
          //getBank().open(BankLocation.CASTLE_WARS);
          getBank().openClosest();
          sleep(Calculations.random(1020, 1222));
          if (!getBank().contains(Constants.kwuarm_unf) || !getBank().contains(Constants.limpwurt_root)) {
             this.stop();
              }
          //getBank().depositAll(Constants.longbow);
          getBank().depositAllItems();
          sleep(Calculations.random(797, 937));
          getBank().withdraw(Constants.limpwurt_root, 14);
          sleep(Calculations.random(862, 959));
          getBank().withdraw(Constants.kwuarm_unf, 14);
          sleep(Calculations.random(622, 788));
          getBank().close();
          banking = false;
          herblore = true;
          //sleep(1000);
}
    
    private void Widthdraw() {
        
    }
        
        
	@Override
	public int onLoop() {
            
            
		     if (herblore && !banking) {
                    getInventory().getRandom(Constants.limpwurt_root).useOn(Constants.kwuarm_unf);
                     sleep(Calculations.random(1120, 1368));
                     getWidgets().getWidgetChild(270, 14, 38).interact();
                     sleep(Calculations.random(4030, 5099));
                    //getWidgets().getWidget(WIDGET_ID).interact();
                      //getInventory().get(Constants.iron_ore).interact();
                     sleepUntil(()-> !getLocalPlayer().isAnimating(),1500);
                     herblore = false;
                      }
                     if (!getInventory().contains(Constants.kwuarm_unf) || !getInventory().contains(Constants.limpwurt_root)) {                 
                            bank();
                      }
                     if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
                     herblore = true;
                      }
		return Calculations.random(466, 676);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Herblore exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.HERBLORE) , 10, 65);
                       
                                            
                       
}}


