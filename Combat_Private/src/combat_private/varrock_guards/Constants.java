package combat_private.varrock_guards;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.wrappers.items.GroundItem;

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
    
    public static boolean bury_bones = false;
    public static GroundItem Bones;
    
    public static boolean food_enabled = true;
    public static int food = 361; //361 = tuna, 379 = lobster
    public static int food_amount = 28;
    
    public static Area Varrock_Castle = new Area(3206, 3458, 3218, 3467, 0); //x1, y2, x2, y2, z
    public static String NPC = "Guard";
}
