/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yewchopper_private;

import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.sleep;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;

/**
 *
 * @author cyborg
 */
@ScriptManifest(author = "Nex", name = "Nex Tree Assasin", version = 0.1, description = "Harrasing tree's", category = Category.WOODCUTTING)
public class Main 
extends AbstractScript {
    
public boolean banking = false;
    private void bank() {
            banking = true;
          getBank().open(BankLocation.SEERS);
          sleep(Calculations.random(500, 1000));
          getBank().depositAllExcept("Dragon axe");
          sleep(Calculations.random(1000, 2500));
          getBank().close();
          banking = false;
          sleep(3000);
}
	@Override
    public void onStart() {
		log("Starting!");	 // This will display in the CMD or logger on dreambot
    }
 
    @Override
    public int onLoop() {
    	if (getInventory().isFull()) {
            bank();
    		sleep(300,500); 
    	} else {
        	GameObject tree = getGameObjects().closest("Yew");
                if(!tree.isOnScreen()) {
                 getWalking().walkExact(Constants.Yews);   
        } else {
        	if (tree != null) { 
    			tree.interact("Chop down");
    			sleep(500,600); 
    			sleepUntil(() -> !getLocalPlayer().isAnimating() , 15000); 
            }
    	}}
    	return 300;
    }
       
    @Override
    public void onExit() {
    	log("Stopping!"); 
    }
}