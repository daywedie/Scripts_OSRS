package tanner_private;


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
@ScriptManifest(author = "T7emon", name = "Tanner_Private", version = 1.0, description = "Tan Green D'hides at Al kharid", category = Category.MONEYMAKING)

public class Main extends AbstractScript {
    
    private Timer timer;
    private int tanned_count = 0;
    
            public void init() {
               timer = new Timer();
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Green D'hide Tanner Bot by T7emon.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("The tanner tans 27 green dragonhides for you.")) {
            tanned_count += 27;
        }
}

    private void bank() {
          getBank().open(BankLocation.AL_KHARID);
          sleepUntil(() -> getBank().isOpen(), 3000);
          if (getBank().isOpen()) {
          if (!getBank().contains(Constants.Green_dragonhide)) {
              log("You Don't have any green D'hide left in bank..");
              log("Stopping...");
              this.stop();
          }
          getBank().depositAll(Constants.Green_dragonleather);
          sleepUntil(() -> getInventory().emptySlotCount() >= 27, 3000);
          getBank().withdraw(Constants.Green_dragonhide, 27);
           sleepUntil(() -> getInventory().count(Constants.Green_dragonhide) >= 27, 3000);
          getBank().close();
}}
       
        	private enum State {
               BANK, TAN
	};
                
        private State getState() {
            
            if (!getInventory().contains(Constants.coins)) {
                log("You need coins in inventory to run this script..");
                this.stop();
            }
              if (getInventory().contains(Constants.Green_dragonhide)) {
              return State.TAN;
              }
            return State.BANK;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case BANK:
                bank();
                break;
                case TAN:
                getWalking().walk(Constants.tan_Location);
                sleepUntil(() -> getLocalPlayer().getTile().distance(Constants.tan_Location) <= 7, 3000);
                if (getLocalPlayer().getTile().distance(Constants.tan_Location) <= 7) {
                Constants.Ellis = getNpcs().closest("Ellis");
                if (Constants.Ellis.isOnScreen() && getWidgets().getWidget(324) == null) {
                    Constants.Ellis.interactForceRight("Trade");
                    sleep(Calculations.random(1400, 1650)); //CAN BE REMOVED but not sure if it affects ban or not.
                    sleepUntil(() -> getWidgets().getWidgetChild(324, 112) != null, 5000);
                    getWidgets().getWidgetChild(324, 112).interact("Tan All");
                    sleepUntil(() -> !getInventory().contains(Constants.Green_dragonhide), 5000);
                }}
                break;
        }
        return Calculations.random(950, 1050);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.green);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString("Amount Tanned (p/h): " + tanned_count + "(" + timer.getHourlyRate(tanned_count) + ")", 10, 50);
                                            
                       
}}

