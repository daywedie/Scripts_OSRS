/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandcrabs_private;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;

@ScriptManifest(author = "T7emon", name = "SandCrabs_Private", version = 1.0, description = "Sand-Crabs Afker", category = Category.COMBAT)
public class Main extends AbstractScript {

public boolean banking = false;
public boolean walking = false;
public boolean walk_spot_1 = false;
public boolean walk_spot_2 = false;
public boolean walk_spot_3 = false;
public boolean fighting = false;
public boolean aggro = false;
private Timer aggroTimer;


public void onStart() {
log("Starting");
aggroTimer = new Timer();
}

private enum State {
BANK, AGGRO, SLEEP
};

 public boolean playersNearby() {
        return getPlayers().all(player -> player != null && !getLocalPlayer().equals(player) && player.distance() < 1).size() > 0;
    }

private State getState() {
    NPC Crab = getNpcs().closest("Sand Crab");
    getWalking().walkExact(Constants.SPOT_1);
    /*
        if (getNpcs().closest("Sandy rocks").distance(Constants.SPOT_2) < 5) {
    return State.AGGRO;
    }*/       
        
        if(getLocalPlayer().canAttack() && !getLocalPlayer().isInCombat() && Crab != null && Crab.canAttack() && Crab.getInteractingCharacter() == null 
        && Crab.getHealthPercent() == 100 && getMap().canReach(Crab) && Crab.isOnScreen()) {
            Crab.interact();
            sleep(3000);
       } else {
            walk_spot_2 = true;
            if (getLocalPlayer().getTile().equals(Constants.SPOT_1) && playersNearby()) {
            getWalking().walkExact(Constants.SPOT_2);
            sleep(3000);
            walk_spot_2 = false;
            } else {
                walk_spot_3 = true;
                 if (getLocalPlayer().getTile().equals(Constants.SPOT_2) && playersNearby()) {
                 getWalking().walkExact(Constants.SPOT_3);
                sleep(3000);
                walk_spot_3 = false;
                 } else {
                     return State.AGGRO;
                 }
            }}
            
        
   

if (banking) {
return State.BANK;
}

return State.SLEEP;
}

@Override
public int onLoop() {
switch (getState()) {

case BANK:
if (banking) {
//do bank
}
break;

case AGGRO:
    if (aggro) {
        walking = false;
        if (!getLocalPlayer().getTile().equals(Constants.AGGRO)) {
       getWalking().walkExact(Constants.AGGRO);
sleep(5000);
aggroTimer.reset();
aggro = false;
walking = true;
}}
break;

case SLEEP:
Calculations.random(300, 700);
break;
}   
return Calculations.random(300, 700);
}}