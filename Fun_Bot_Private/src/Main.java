
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.emotes.Emote;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
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
@ScriptManifest(
        author = "T7emon", 
        name = "Fun Bot", 
        version = 1.0, 
        description = "Make fun", 
        category = Category.MISC)

public class Main extends AbstractScript {
    
    private Timer timer;
 
            public void init() {
               timer = new Timer();
               log("Initialized");
            
        }

    @Override
	public void onStart() {
            init();
		log("Welcome to FUN BOT by T7emon.");
        }
       public int getcwTime() throws FileNotFoundException, IOException {
           int Time = -1;
           File file = new File("/opt/lampp/htdocs/cw/timer.txt"); 

                BufferedReader reader = new BufferedReader(new FileReader(file));
        //System.out.println(reader.readLine());
        reader.readLine();
        Time = Integer.parseInt(reader.readLine());
        reader.close();
        return Time;
}
            @Override
public void onMessage(Message msg) {
    sleep(3000);
    
          if (msg.getMessage().toLowerCase().equalsIgnoreCase("!commands")) {
          instantType("!usage, !rickoshay, !autobolter, !zamoraak, !braincells, !god, !noob, !retard,");
          sleep(3000);
          instantType("!dumb, !poor, !sorry, !mongol, !king, !stacker, !elvis, !echo");
          sleep(3000);
          instantType("!players, !player_count, !ttg, !harasses, !elysian");
        }   
          
         if (msg.getMessage().toLowerCase().equalsIgnoreCase("!usage")) {
          instantType("!command username");
        }
         
        if (msg.getMessage().toLowerCase().contains("!echo")) {
          instantType(msg.getMessage().toLowerCase().replace("!echo", ""));
        }
         
       if (msg.getMessage().toLowerCase().equalsIgnoreCase("!rickoshay")) {
          instantType("Rickoshay Cleared you and your father!");
        }
        if (msg.getMessage().toLowerCase().equalsIgnoreCase("!autobolter")) {
          instantType("And the king of all goblins is?");
          sleep(1700);
          instantType("Autobolter ofcourse!");
        }   
        if (msg.getMessage().toLowerCase().equalsIgnoreCase("!zamoraak")) {
          instantType("Being the goat isnt a baaa-d thing!");
          sleep(1700);
          //instantType("Being a pig is!");
        }
         if (msg.getMessage().toLowerCase().equalsIgnoreCase("!elvis")) {
          instantType("damn im elvis has left the building permanently!");
          getEmotes().doEmote(Emote.LAUGH);
        }  
        if (msg.getMessage().toLowerCase().equalsIgnoreCase("!harasses")) {
             instantType("Har-asses shares cucumber with elvis...");
             sleep(1500);
             instantType("Sometimes this cucumber got stuck inside..");
             sleep(5000);
             instantType("Now a days they use a spoon");
               getEmotes().doEmote(Emote.LAUGH);
      }
      
                if (msg.getMessage().toLowerCase().equalsIgnoreCase("!elysian")) {
             instantType("elysian cleans himself with creamy cheese sauce");
               getEmotes().doEmote(Emote.LAUGH);
      }
          
       if (msg.getMessage().toLowerCase().equalsIgnoreCase("!players")) {
          List<Player> playerList = getPlayers().all();
          playerList.forEach(p -> {
	//log(p.getName());
        sleep(5000);
        instantType(p.getName());
});
        }
              if (msg.getMessage().toLowerCase().equalsIgnoreCase("!reportall")) {
          List<Player> playerList = getPlayers().all();
          if (playerList.contains("elapsed x")) {
         playerList.remove("elapsed x");
       } else {
          playerList.forEach(p -> {
	//log(p.getName());
        sleep(5000);
        instantType("Reported -> " + p.getName());
});
        }}
      if (msg.getMessage().toLowerCase().equalsIgnoreCase("!player_count")) {
              List<Player> playerList = getPlayers().all();
              instantType("Amount of players nearby : " + playerList.size());
      }
           
       if (msg.getMessage().toLowerCase().equalsIgnoreCase("!ttg")) {
        try {
            getcwTime();
            instantType("Time until next cw game : " + getcwTime() + " Minutes");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
      } 
      
      
        String message = msg.getMessage(); // test arg1 arg2
       String args[] = message.split(" "); // args[1] = "arg1", args[2] = "arg2"
       String User = args[1];
        if (msg.getMessage().toLowerCase().equalsIgnoreCase("!braincells " + User)) {
            instantType(User + " has 0 brain cells left.");
            getEmotes().doEmote(Emote.THINK);
        }
           if (msg.getMessage().toLowerCase().equalsIgnoreCase("!god " + User)) {
           instantType(User + " is a fookin god!");
           getEmotes().doEmote(Emote.CHEER);
           }
        if (msg.getMessage().toLowerCase().equalsIgnoreCase("!noob " + User)) {
           instantType(User + " is a fookin big noobie!!");
           getEmotes().doEmote(Emote.YAWN);
        }
       if (msg.getMessage().toLowerCase().equalsIgnoreCase("!retard " + User)) {
           instantType(User + " is the biggest retard in this universe!");
           //getEmotes().openTab();
           getEmotes().doEmote(Emote.HEADBANG);
        }
        if (msg.getMessage().toLowerCase().equalsIgnoreCase("!dumb " + User)) {
           instantType(User + " is not so smart, what a dumb fuck!");
           getEmotes().doEmote(Emote.NO);
        }
       if (msg.getMessage().toLowerCase().equalsIgnoreCase("!poor " + User)) {
           instantType(User + " is soooo poor! he got nothing..");
           getEmotes().doEmote(Emote.LAUGH);
        }
           if (msg.getMessage().toLowerCase().equalsIgnoreCase("!sorry " + User)) {
           instantType("Sry >_< " + User);
           getEmotes().doEmote(Emote.CRY);
        }
       if (msg.getMessage().toLowerCase().equalsIgnoreCase("!mongol " + User)) {
           instantType("and who is the mongol today?");
           sleep(3000);
           instantType(User + " is the Mongoloid!");
           getEmotes().doEmote(Emote.RASPBERRY);
        }
      if (msg.getMessage().toLowerCase().equalsIgnoreCase("!king " + User)) {
           instantType("king of all kings " + User);
           getEmotes().doEmote(Emote.CHEER);
        }
     if (msg.getMessage().toLowerCase().equalsIgnoreCase("!stacker " + User)) {
          instantType(User + " is the biggest Stacking nooblet!");
          getEmotes().doEmote(Emote.BECKON);
        }
     
     
       /* String[] phrases = {
                "!braincell", "lolodkfjd457"
        };
        for (String p: phrases) {
            if (msg.getMessage().toLowerCase().equalsIgnoreCase(p)) {
                //getKeyboard().type(name + " Has 0 braincells left");
                instantType(User + " has 0 brain cells left.");
            }
        }*/
    }

private void instantType(final String message) {
        Canvas canvas = getClient().getInstance().getCanvas();
        for (char c : message.toCharArray()) {
            canvas.dispatchEvent(new KeyEvent(canvas, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, c));

        }
        canvas.dispatchEvent(new KeyEvent(canvas, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));

        canvas.dispatchEvent(new KeyEvent(canvas, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));

}
        
	@Override
	public int onLoop() {         
        return 500;
        }
        
@Override
	public void onPaint(Graphics g){
            g.setColor(Color.cyan);
			g.drawString("Runtime: " + timer.formatTime(), 10, 35);
                        //g.drawString("Games exp (p/h): " + getSkillTracker().getGainedExperience(Skill.FISHING) + "(" + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING) + ")", 10, 65); //65
                                            
                       
}}

