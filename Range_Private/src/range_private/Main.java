/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package range_private;

import java.awt.Graphics;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author cyborg
 */
@ScriptManifest(author = "T7emon", name = "Range_Bot", version = 1.0, description = "Teleporter", category = Category.MAGIC)
public class Main extends AbstractScript {

    private Timer timer;
    private boolean superheat = false;
    private boolean alching = false;
    private boolean banking;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.RANGED);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Range Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Ranged levels!.");
        }
        
        @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("Your cannon has broken!")) {
            GameObject brokenCannon = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Repair"));
            brokenCannon.interact();
            log("Cannon Repaired");
	}
}
        
	@Override
	public int onLoop() {
             //GameObject Cannon = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Fire"));
             //GameObject brokenCannon = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Repair"));
            GameObject Cannon = getGameObjects().closest(6);
            if(getGameObjects().closest(6).exists()) {
                Cannon.interact();
                sleep(Calculations.random(10000, 50000));
                //brokenCannon.interact();
        }
            if(!getInventory().contains(2)) {
                Cannon.interact("Pick-up");
                this.stop();
            }
		return Calculations.random(10000, 20000);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) , 10, 65);
                                            
                       
}}

