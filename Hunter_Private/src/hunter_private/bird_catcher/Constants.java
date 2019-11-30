package hunter_private.bird_catcher;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

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
    
    public static Area Feldip_Hills_Area = new Area(2590, 2887, 2581, 2887, 0);
        NPC Crimson_swift;
        public static int Bird_snare = 10006; //9344 = broken , 9345 = set bird snare, 9373 = catched bird
        
        public static GameObject Fallen_bird_snare;
        public static GameObject Broken_bird_snare;
        public static GameObject Full_bird_snare;
        
        public static int feather = 10088;
        public static int Raw_bird_meat = 9978;
        public static int bones = 526;
        
}
