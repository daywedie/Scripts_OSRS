/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank_dropper.pkgprivate;

import java.awt.Color;
import java.awt.Graphics2D;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author t7emon
 */
@ScriptManifest(author = "T7emon", name = "Bank_Dropper", version = 1.0, description = "Drop item from bank", category = Category.UTILITY)

public class Main extends AbstractScript {
    
    private boolean banking = false;
    private Timer timer;
            public void init() {
               log("Initialized");
            
        }
            
               /*
    * Bank method..
    */
    public void bank() {
            banking = true;
             GameObject bank = getGameObjects().closest(28594); //7455 //7468
            //bank.interact();
            sleepUntil(() -> bank.interact(), 5000);
           sleepUntil(() -> getBank().isOpen(), 5000);
           getBank().withdraw(Constants.sulphur, 28);
            sleepUntil(() -> getInventory().count(Constants.sulphur) == 28, 5000);
            getBank().close();
            banking = false;

    }
            
           /*
            * Is in area? wether true or false
            */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }return false;
}
 
   /*
    * Handles Game Messages recieved
    */
@Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("Your inventory is too full to hold any more roots.")) {
            //log("STATUS = FLETCH");
            //fletching = true;
        }
}
    
@Override
	public void onStart() {
            new Timer();
            init();
		log("Welcome to Bank Dropper Bot by T7emon.");
	}
        

	private enum State {
            BANK, DROP, SLEEP
	};

	private State getState() {
            
         if (!getInventory().contains(Constants.sulphur)) {
              //if (getInventory().contains(mining_private.Constants.ore)) {
        return State.BANK;
        }
                
          return State.DROP;
}
        
        
	@Override
	public int onLoop() {
      /*****************************************************************************************************************************/
		switch (getState()) {                  
               case BANK:
                  bank();
               break;
               case DROP:
                   getInventory().dropAll(Constants.sulphur);
               break;
                case SLEEP:
                   sleep(Calculations.random(100, 200));
                 break;
                }
		return Calculations.random(50, 100);
        }

@Override
	public void onPaint(Graphics2D g1){
          g1.setColor(Color.RED);
			g1.drawString("Runtime: " + timer.formatTime(), 10, 35); //35
                                            
                       
}}
