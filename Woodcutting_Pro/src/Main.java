import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import org.dreambot.api.methods.Calculations;
import static org.dreambot.api.methods.MethodProvider.sleep;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.emotes.Emote;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

/**
 *
 * @author t7emon
 */

@ScriptManifest(
        author = "T7emon", 
        name = "Woodcutting Pro", 
        version = 0.1, 
        description = "Woodcutting Pro", 
        category = Category.WOODCUTTING)

public class Main extends AbstractScript {
    
   /*
    * Variables
    */
    private Timer Timer;
    private Timer antibanTimer;
    private int logsCount;
    private int birdnestCount;
    
    /*
    * On start
   */
	@Override
        public void onStart() {
            log("Welcome to " + Constants.getName());
        //Start the Frame..
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame().setVisible(true);
            }
        });  
        //Wait for Start button to be clicked..
        if (!Constants.getStart()) {
            sleepUntil(() -> Constants.getStart(), 15000000); 
        }
        log("Tree selected : " + Constants.getTree());
        //Finally Start..
        Timer = new Timer();
        antibanTimer = new Timer();
        getSkillTracker().start(Skill.WOODCUTTING);
        Constants.setStatus("Start");
    }
        /*
        * Message received
        */
        @Override
        public void onMessage(Message msg) {
            if (msg.getMessage().contains("You get some")) {
                logsCount++;
            }
             if (msg.getMessage().contains("A bird's nest falls out of the tree.")) {
                birdnestCount++;
            }
        }
         /*
        * State enum
        */
    	private enum State {
                DROP, CUT
	};
        
        /*
        * Add info here and return what to do.. l0l <- humor xD
        */
        private State getState() {
            /*
            * ANTIBAN
            */
             if (getDialogues().inDialogue()) {
               sleep(Calculations.random(4000, 9000));
               getWalking().walk(getLocalPlayer().getTile().getRandomizedTile());
               sleep(Calculations.random(3000, 9000));
               getTabs().open(Tab.SKILLS);
               sleep(Calculations.random(2000, 5000));
               getSkills().hoverSkill(Skill.WOODCUTTING);
                return State.CUT;
              }
             if (antibanTimer.elapsed() >= Calculations.random(240000, 800000)) {
                Random random = new Random(180);
                log("Antiban");
                Constants.setStatus("Antiban");
                if (random.nextInt() <= 100) {
               getTabs().open(Tab.SKILLS);
               sleepUntil(() -> getTabs().isOpen(Tab.SKILLS), 5000); 
               getSkills().hoverSkill(Skill.WOODCUTTING);
               sleep(Calculations.random(2000, 4000));
               antibanTimer.reset();
                } else {
              if (random.nextInt() > 100 && random.nextInt() < 150) {
               getTabs().open(Tab.EMOTES);
               sleepUntil(() -> getTabs().isOpen(Tab.EMOTES), 5000); 
               getEmotes().doEmote(Emote.DANCE);
               antibanTimer.reset();
              } else {
                if (random.nextInt() >= 150) {
               sleep(Calculations.random(120000, 240000));
               antibanTimer.reset();
              }
             }
             }}
            /*
             * Take Bird nest from the ground (Don't worry no eggs in there)
             */
            for (GroundItem item : getGroundItems().all())
                if (item != null && item.getName().toLowerCase().contains("bird nest")) {
                    log("Found a Bird nest");
                    Constants.setStatus("Take Bird nest");
                    item.interact();
                }
            /*
            * Drop else Cut..
            */
              if (getInventory().count(Item -> Item != null && Item.getName().toLowerCase().contains("logs")) > new Random().nextInt(6 + 1) + 10 || getInventory().isFull()  ) {       
                  return State.DROP;
        } else {
                  return State.CUT;
        }}
        /*
        * the mighty loop hole? <- l0ld smoke weed everyday
        */
    @Override
    public int onLoop() {
        switch (getState()) {
            case DROP:  
          for (Item item : getInventory().all()) {
              if (item != null && item.getName().toLowerCase().contains("log")) {
                  Constants.setStatus("Drop logs");
                  getInventory().dropAll(Item -> Item != null && Item.getName().toLowerCase().contains("log"));
          }}
             break;
            case CUT:
                for (GameObject Tree : getGameObjects().all()) {
                     // Tree = getGameObjects().closest(GameObject -> GameObject != null && GameObject.getName().equals(Constants.getTree()));
                    if (Tree != null 
                            && Tree.getName().equals(Constants.getTree()) 
                            && !getLocalPlayer().isAnimating()) {
                        sleep(Calculations.random(350, 2300));
                        Constants.setStatus("Chop " + Constants.getTree());
                        if (Tree.interact()) {
                        sleep(3000);
                        sleepUntil(() -> !getLocalPlayer().isAnimating() || getLocalPlayer().distance(Tree) > 2 || Tree == null, 60000); 
                    }}
                         
                    
                    //TO DROP INVENTORY WHEN NOT CUTTING THE TREE. BAD WAY TO DO THIS LOL
                    if (!getLocalPlayer().isAnimating()) {
                                 for (Item item : getInventory().all()) {
              if (item != null && item.getName().toLowerCase().contains("log")) {
                  Constants.setStatus("Drop logs");
                  getInventory().dropAll(Item -> Item != null && Item.getName().toLowerCase().contains("log"));
          }
                                 }
                    }
                }
            break;
        }
       return Calculations.random(1000, 1600);
}
    
    @Override
	public void onPaint(Graphics2D g1){
          g1.setColor(Color.GREEN);
			g1.drawString("Runtime: " + Timer.formatTime(), 10, 35);
                        g1.drawString("Status: " + Constants.getStatus(), 10, 65);
                        g1.drawString("Woodcutting exp (p/h): " + getSkillTracker().getGainedExperience(Skill.WOODCUTTING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.WOODCUTTING) + ")", 10, 80);
                        g1.drawString("Logs chopped (p/h): " + logsCount + "(" + Timer.getHourlyRate(logsCount) + ")", 10, 95);
                        g1.drawString("Bird nest gained (p/h): " + birdnestCount + "(" + Timer.getHourlyRate(birdnestCount) + ")", 10, 110);
                                            
                       
}
        
    @Override
    public void onExit() {
    	log("Stopping!"); 
    }}