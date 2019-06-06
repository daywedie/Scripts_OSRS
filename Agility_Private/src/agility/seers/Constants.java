/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agility.seers;

import org.dreambot.api.Client;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.items.GroundItem;

/**
 *
 * @author t7emon
 */
public class Constants {
    
        public static int food = 379; //Food to take from bank || 385 = Shark, 379 = Lobster
        public static int food_amount = 5; //Amout of food to take from bank
        public static int stamina = 343;
         public static Tile WALL_LOCATION = new Tile(2729, 3489, 0);
         public static Area FIRST_ROOF = new Area(2730, 3497, 2722, 3490, 3); //x1, y2, x2, y2, z
         public static Area SECOND_ROOF = new Area(2713, 3493, 2705, 3496, 2); //x1, y2, x2, y2
         public static Area THIRD_ROOF = new Area(2716, 3482, 2710, 3477, 2); //x1, y2, x2, y2
         public static Area FOURTH_ROOF = new Area(2714, 3472, 2700, 3469, 3); //x1, y2, x2, y2
         public static Area FINAL_ROOF = new Area(2703, 3466, 2698, 3460, 2); //x1, y2, x2, y2
         
        // public static enum Roofs{
        // FIRST_ROOF(new Area(1612, 3998, 1648, 3968)),
         //SECOND_ROOF(new Area(1612, 3998, 1648, 3968)),
        // THIRD_ROOF(new Area(1612, 3998, 1648, 3968)),
         //FINAL_ROOF(new Area(1612, 3998, 1648, 3968));

   //Roofs(Area roofArea) {
} //}}