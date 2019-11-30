package pest_control_private;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
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
@ScriptManifest(author = "T7emon", name = "Pest_Control_Private", version = 1.0, description = "Do the Pest Control minigame", category = Category.COMBAT)

public class Main extends AbstractScript {
    
    private Timer timer;
    private String Points = "";
    private int wonGames = 0;
    private int lostGames = 0;
    
    private boolean gameStarted;
    
            public void init() {
               timer = new Timer();
               getSkillTracker().start(Skill.HITPOINTS);
               getSkillTracker().start(Skill.STRENGTH);
               getSkillTracker().start(Skill.ATTACK);
               getSkillTracker().start(Skill.DEFENCE);
               getSkillTracker().start(Skill.MAGIC);
               getSkillTracker().start(Skill.RANGED);
               log("Initialized");
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to Pest Control Bot by T7emon.");
		log("If you experience any issues while running this script please report them to me on the forums.");
		log("Enjoy the script, gain some Combat levels!.");
        }
        
            @Override
public void onMessage(Message msg) {
	if (msg.getMessage().contains("There is no ammo left in your quiver.")) {
            log("There is no ammo left in your quiver.");
            getWalking().walkExact(new Tile(3212, 3438, 0));
            sleep(5000);
           this.stop();
        }
}

        /*
         * Is in area? wether true or false
            */
    private boolean inArea(Area area){ 
    if(area.contains(getLocalPlayer().getTile())){
        return true;
    }return false;
}
       
        	private enum State {
               ENTER, WALK, FIGHT, SLEEP
	};
                
        private State getState() {
            
            if (getDialogues().toString().contains("Congratulations!")) {
                wonGames++;
            } else {
               // if (getDialogues().getNPCDialogue().equalsIgnoreCase(Points))
                if (getDialogues().toString().equalsIgnoreCase("The knights noticed your lack of zeal in that battle and have not presented you with any points."))
                lostGames++;
            }
              if (getDialogues().inDialogue()) {
                     getDialogues().clickContinue();
               }
                  
               if (getWidgets().getWidget(408) == null) {
                   gameStarted = false;
               } else {
                   gameStarted = true;
                   log("gameStarted = true");
               }
               
               if (!gameStarted && !inArea(Constants.Boat_Area)) {
                   return State.ENTER;
               }
            if (gameStarted && getGameObjects().closest("Lander boat").distance(getLocalPlayer()) <= 6) {
                return State.WALK;
             }
               
              if (gameStarted && !getLocalPlayer().isInCombat()) {
                  return State.FIGHT;
              }
            
            return State.SLEEP;
        }
        
	@Override
	public int onLoop() {
            switch (getState()) {
                case ENTER:
                    GameObject Gangplank = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Gangplank"));
                    Gangplank.interact();
                    sleep(2000);
                    Points = getWidgets().getWidget(407).getChild(6).getText();
                    if (inArea(Constants.Boat_Area)) {
                        log("In Boat Area...");
                    }
                    break;
                case WALK:                
                    getWalking().walk(getLocalPlayer().getTile().getRandomizedTile(30));
                    log("Walking to Randomized Tile...");
                    //sleep(Calculations.random(2217, 4117));
                break;
                case FIGHT:
                    log("State = FIGHT");
             List<NPC> NPCS = getNpcs().all();
             for (NPC n : NPCS) {
            if (n.getName().contains("Shifter")
                    || n.getName().contains("Torcher") 
                    || n.getName().contains("Defiler")
                    || n.getName().contains("Brawler")
                    || n.getName().contains("Splatter")
                    || n.getName().contains("Ravager")) {
                 int random = new Random().nextInt(1000);
                 log("Random Number: " + random);
                   if (random <= 800 && n.interact()) {
                    log("Attacking " + n.getName());
                    sleepUntil(() -> !getLocalPlayer().isInteracting(n), 10000);
                   //sleep(Calculations.random(950, 1050));
                   } else {
                    if (random > 900 && n.interactForceRight("Attack")) {
                    log("Attacking " + n.getName());
                    //sleep(Calculations.random(900, 1100));
                    sleepUntil(() -> !getLocalPlayer().isInteracting(n), 10000);
                    // sleepUntil(() -> !n.isDrawMinimapDot(), 10000);
            }}}}
                break;
                case SLEEP:
                Calculations.random(907, 1007);
                break;
        }
        return Calculations.random(977, 1177);
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.WHITE);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        g.drawString(Points, 10, 50);
                        g.drawString("Won Games: " + wonGames, 10, 65);
                        g.drawString("Lost Games: " + lostGames, 10, 80);
                        g.drawString("Hitpoints (p/h): " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS) + ")", 10, 95);
                        g.drawString("Strength exp (p/h): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 10, 110);
                        g.drawString("Attack exp (p/h): " + getSkillTracker().getGainedExperience(Skill.ATTACK) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK) + ")", 10, 125);
                        g.drawString("Defence exp (p/h): " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE) + ")", 10, 140);
                        g.drawString("Magic exp (p/h): " + getSkillTracker().getGainedExperience(Skill.MAGIC) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC) + ")", 10, 155);
                        g.drawString("Ranged exp (p/h): " + getSkillTracker().getGainedExperience(Skill.RANGED) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) + ")", 10, 170);
        }}

