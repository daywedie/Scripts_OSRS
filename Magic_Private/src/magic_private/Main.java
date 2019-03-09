/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic_private;


import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.bank.BankType;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.filter.impl.NameFilter;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.Category;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(author = "T7emon", name = "Magic_Bot", version = 1.0, description = "Teleporter", category = Category.MAGIC)

public class Main extends AbstractScript {
    
    private Timer timer;
    private boolean superheat = false;
    private boolean alching = false;
    private boolean banking;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.MAGIC);
               getSkillTracker().start(Skill.SMITHING);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Magic Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Magic levels!.");
        }
        
    private void bank() {
          banking = true;
          getBank().open(BankLocation.GRAND_EXCHANGE);
          sleep(Calculations.random(1833, 1995));
          //getBank().deposit(Constants.iron_bar, 27);
          getBank().depositAllExcept(Constants.nature_rune);
          sleep(Calculations.random(897, 1037));
          getBank().withdrawAll(Constants.iron_ore);
          //getBank().withdraw(Constants.iron_ore, 27);
          getBank().close();
          banking = false;
          //sleep(1000);
}
        
        
	@Override
	public int onLoop() {
            superheat = true;
            alching = false;
            
            if (alching) {
                    getMagic().castSpell(Normal.HIGH_LEVEL_ALCHEMY);
                    getInventory().get("Mithril platebody").interact();
                    sleepUntil(() -> !getLocalPlayer().isAnimating(),3000);
                }
            
		     if (superheat) {
                     if (!banking && !getMagic().isSpellSelected() && getInventory().contains(Constants.iron_ore)) {
                    getMagic().castSpell(Normal.SUPERHEAT_ITEM);
                    getInventory().getRandom(Constants.iron_ore).interact();
                      //getInventory().get(Constants.iron_ore).interact();
                     sleepUntil(()-> !getLocalPlayer().isAnimating(),1500);
                      }
                      if (!getInventory().contains(Constants.iron_ore)) {
                             sleep(Calculations.random(1256, 1467));
                             bank();
                      } 
                      if (!getInventory().contains(Constants.nature_rune)) {
                          this.stop();
}}
		return Calculations.random(247, 676);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) , 10, 65);
                        g.drawString("Smithing exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.SMITHING) , 10, 75);
                                            
                       
}}
