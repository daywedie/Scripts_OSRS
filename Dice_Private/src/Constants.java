

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.dreambot.api.Client.getClient;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author t7emon
 */
public class Constants {
    
         public static int min_bet_amount = 100000; //K
         public static int chance_amount = 55; //x2
         
             public static String getCurrentTimeString() {
	        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	        return dateFormat.format(new Date());
	    }
         
         /*public static void sendMessage(final String message) {
        Canvas canvas = getClient().getInstance().getCanvas();
        for (char c : message.toCharArray()) {
            canvas.dispatchEvent(new KeyEvent(canvas, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, c));
        }
        canvas.dispatchEvent(new KeyEvent(canvas, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
        canvas.dispatchEvent(new KeyEvent(canvas, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
}*/
         
}
