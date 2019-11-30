/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic_private.superheater;


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

@ScriptManifest(author = "T7emon", name = "Magic_Bot", version = 1.0, description = "Superheat iron ore", category = Category.MAGIC)

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
          //getBank().openClosest();
          GameObject bank = getGameObjects().closest(gameObject -> gameObject != null && gameObject.hasAction("Bank"));
          //bank.interactForceRight("Bank");
          bank.interact();
          //etBank().open(BankLocation.GRAND_EXCHANGE);
          sleepUntil(() -> getBank().isOpen(), 3000);
          //getBank().deposit(Constants.iron_bar, 27);
          getBank().depositAllExcept(Constants.nature_rune);
          sleepUntil(() -> getInventory().emptySlotCount() >= 26, 3000);
          getBank().withdraw(Constants.iron_ore, 27);
           sleepUntil(() -> getInventory().count(Constants.iron_ore) >= 27, 1500);
          //getBank().withdrawAll(Constants.iron_ore);
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
                    Item item = getInventory().get("Steel platelegs");
                    item.interact();
                    sleepUntil(() -> !getLocalPlayer().isAnimating(),3000);
                }
            
		     if (superheat) {
                     if (!getInventory().contains(Constants.nature_rune)) {
                       this.stop();
                     }
                     //if (!getMagic().canCast(Normal.SUPERHEAT_ITEM)) {
                         //log("You need 43 Magic & 15 smithing to cast this spell.");
                         //this.stop();
                   // }
                     if (!getInventory().contains(Constants.iron_ore)) {
                     if (getMagic().isSpellSelected()) {
                             getMouse().click();
                         } else {
                             //sleep(Calculations.random(425, 750));
                             bank();
                     }}
                     if (!banking && !getMagic().isSpellSelected() && getInventory().contains(Constants.iron_ore)) {
                                                   // sleepUntil(()-> !getLocalPlayer().isAnimating(), 1500);
                         //sleep(Calculations.random(100, 110));
                    getMagic().castSpell(Normal.SUPERHEAT_ITEM);
                    Item item = getInventory().get(Constants.iron_ore);
                      if (getMagic().isSpellSelected()) {
                          //getInventory().getRandom(Constants.iron_ore).interact();
                          item.interact();
                         // sleepUntil(()-> item.interact(), 1500);
                      }}
                     }
                    /* if (!banking && !getMagic().isSpellSelected() && getInventory().contains(Constants.iron_ore)) {
                    sleep(Calculations.random(350, 425));
                    getMagic().castSpell(Normal.SUPERHEAT_ITEM);
                    Item item = getInventory().getItemInSlot(10);
                    if (item.getID() == Constants.iron_ore) {
                   if (getMagic().isSpellSelected()) {
                    item.interact();
                    sleepUntil(()-> !getLocalPlayer().isAnimating(), 1500);
                   }
                     } else {
                    getInventory().interact(Constants.iron_ore, "Cast");
                    //getInventory().getRandom(Constants.iron_ore).interact();
                      //getInventory().get(Constants.iron_ore).interact();
                     sleepUntil(()-> !getLocalPlayer().isAnimating(), 1500);
                      }}}*/
		return Calculations.random(0, 0);
        }
        
@Override
	public void onPaint(Graphics g){
			//g.drawString("State: " + getState().toString(), 10, 35);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        //g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) , 10, 65);
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MAGIC) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) + ")", 10, 65); //65
                        g.drawString("Smithing exp (p/h): " + getSkillTracker().getGainedExperience(Skill.SMITHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.SMITHING) + ")", 10, 75); //75
                        //g.drawString("Smithing exp (p/h): " + getSkillTracker().getGainedExperiencePerHour(Skill.SMITHING) , 10, 75);
                                            
                       
}}
