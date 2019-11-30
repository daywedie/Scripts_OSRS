/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic_private.enchanter;


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

@ScriptManifest(author = "T7emon", name = "Magic_Ring_Enchanter_Private", version = 1.0, description = "Enchant Sapphire Rings", category = Category.MAGIC)

public class Main extends AbstractScript {
    
    private Timer timer;
    private int ring_count = 0;
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.MAGIC);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Ring enchanter bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Magic levels and profit!.");
        }
        
    private void bank() {
          GameObject bank = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Bank"));
          bank.interact();
          sleepUntil(() -> getBank().isOpen(), 3000);
          if (!getBank().contains(Constants.Sapphire_ring)) {
              log("You Don't have any Sapphire Ring's left in bank..");
              log("Stopping...");
              this.stop();
          }
          getBank().depositAllExcept(Constants.Cosmic_rune);
          sleepUntil(() -> getInventory().emptySlotCount() >= 27, 3000);
          getBank().withdraw(Constants.Sapphire_ring, 27);
           sleepUntil(() -> getInventory().count(Constants.Sapphire_ring) >= 27, 3000);
           ring_count = getBank().count(Constants.Recoil_ring);
          getBank().close();
}
    
                @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You catch a trout.") || msg.getMessage().contains("You catch a salmon") || msg.getMessage().contains("You catch some shrimps.")) {
        }
}
         private enum State {
               BANK, ENCHANT
	};
         
          private State getState() {
              if (!getInventory().contains(Constants.Cosmic_rune)) {
                  log("No Cosmic runes in Inventory!");
                  log("Stopping...");
                  this.stop();
              }
              
              if (getInventory().contains(Constants.Cosmic_rune) && getInventory().contains(Constants.Sapphire_ring)) {
        return State.ENCHANT;
          }
        return State.BANK;
       }
	@Override
	public int onLoop() {
           switch (getState()) {
               case BANK:
                   bank();
                   break;
               case ENCHANT:
                   if (getMagic().castSpell(Normal.LEVEL_1_ENCHANT)) {
                   getInventory().get(Constants.Sapphire_ring).interact();
                   sleepUntil(() -> !getInventory().contains(Constants.Sapphire_ring) || getDialogues().inDialogue() , 90000);
                   }
                   break;
                }
		return Calculations.random(1000, 3000);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        //g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) , 10, 65);
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MAGIC) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) + ")", 10, 65); //65
                        g.drawString("Rings Enchanted (p/h): " + ring_count + "(" + timer.getHourlyRate(ring_count) + ")", 10, 75);
                        //g.drawString("Smithing exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.SMITHING) , 10, 75);
                                            
                       
}}
