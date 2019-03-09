
import java.awt.Graphics;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Fletching_Bot", version = 1.0, description = "String Bows", category = Category.FLETCHING)

public class Main extends AbstractScript {
    
    private Timer timer;
    private boolean fletching = false;
    private boolean banking;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.FLETCHING);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Fletching Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Fletching levels!.");
        }
        
    private void bank() {
          banking = true;
          getBank().open(BankLocation.CASTLE_WARS);
          sleep(Calculations.random(1020, 1222));
          if (!getBank().contains(Constants.Longbow_unf) || !getBank().contains(Constants.bowstring)) {
             this.stop();
              }
          //getBank().depositAll(Constants.longbow);
          getBank().depositAllItems();
          sleep(Calculations.random(797, 937));
          getBank().withdraw(Constants.Longbow_unf, 14);
          sleep(Calculations.random(862, 959));
          getBank().withdraw(Constants.bowstring, 14);
          sleep(Calculations.random(622, 788));
          getBank().close();
          banking = false;
          fletching = true;
          //sleep(1000);
}
        
        
	@Override
	public int onLoop() {
            
            
		     if (fletching && !banking) {
                    getInventory().getRandom(Constants.Longbow_unf).useOn(Constants.bowstring);
                     sleep(Calculations.random(1120, 1368));
                     getWidgets().getWidgetChild(270, 14, 38).interact();
                     sleep(Calculations.random(4030, 5099));
                    //getWidgets().getWidget(WIDGET_ID).interact();
                      //getInventory().get(Constants.iron_ore).interact();
                     sleepUntil(()-> !getLocalPlayer().isAnimating(),1500);
                     fletching = false;
                      }
                     if (!getInventory().contains(Constants.Longbow_unf) || !getInventory().contains(Constants.bowstring)) {                 
                            bank();
                      }
                     if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
                     fletching = true;
                      }
		return Calculations.random(466, 676);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        //g.drawString("Fletching exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.FLETCHING) , 10, 65);
                         g.drawString("Fletching exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FLETCHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FLETCHING) + ")", 10, 65); //65
                       
                                            
                       
}}

