package pest_control_private;

import java.util.List;
import org.dreambot.api.methods.map.Area;
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
    
    public static boolean food_enabled = true;
    public static int food = 379; //361 = tuna, 379 = lobster
    public static int food_amount = 28;
    
    public static Area Boat_Area = new Area(2660, 2638, 2663, 2643, 0); //x1, y2, x2, y2, z
    public static Area Pest_Control_Area = new Area(11875, 3382, 3218, 3467, 0); //x1, y2, x2, y2, z
    public static String NPC = "Shifter"; //Torcher //Defiler //Brawler //Ravager //Splatter
}
