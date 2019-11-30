package crafting_private.f2p;


import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Crafting_f2p", version = 1.0, description = "Craft Hard leather", category = Category.CRAFTING)

public class Main extends AbstractScript {
    
    private Timer timer;
    private int Crafted_count = 0;
    
            public void init() {
               timer = new Timer();
                getSkillTracker().start(Skill.CRAFTING);
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to F2p Crafting Bot by T7emon.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("You make a hard leather body.")) {
            Crafted_count++;
        }
}

    private void bank() {
          getBank().open(BankLocation.GRAND_EXCHANGE);
          sleepUntil(() -> getBank().isOpen(), 3000);
          if (getBank().isOpen()) {
          if (!getBank().contains(Constants.Hard_leather)) {
              log("You Don't have any Hard leather left in bank..");
              log("Stopping...");
              this.stop();
          }
          getBank().depositAll(Constants.Hardleather_body);
          sleepUntil(() -> getInventory().emptySlotCount() >= 26, 3000);
          getBank().withdraw(Constants.Hard_leather, 26);
           sleepUntil(() -> getInventory().count(Constants.Hard_leather) >= 26, 3000);
          getBank().close();
}}
       
        	private enum State {
               BANK, CRAFT
	};
                
        private State getState() {
            
            if (!getInventory().contains(Constants.Needle) || !getInventory().contains(Constants.Thread)) {
                log("You need a Needle and Thread in inventory to run this script..");
                this.stop();
            }
              if (getInventory().contains(Constants.Hard_leather)) {
              return State.CRAFT;
              }
            return State.BANK;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case BANK:
                bank();
                break;
                case CRAFT:
        if (getInventory().get(Constants.Needle).useOn(Constants.Hard_leather)) {
         sleep(Calculations.random(1100, 1150));
         sleepUntil(() -> getWidgets().getWidgetChild(270, 14, 38) != null, 5000);
        getWidgets().getWidgetChild(270, 14, 38).interact();
          sleepUntil(() -> !getInventory().contains(Constants.Hard_leather), 45000);
        }
                break;
        }
        return Calculations.random(950, 1050);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.white);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                            g.drawString("Crafting exp (p/h): " + getSkillTracker().getGainedExperience(Skill.CRAFTING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.CRAFTING) + ")", 10, 50);
                            g.drawString("Amount Crafted (p/h): " + Crafted_count + "(" + timer.getHourlyRate(Crafted_count) + ")", 10, 65);
                                            
                       
}}

