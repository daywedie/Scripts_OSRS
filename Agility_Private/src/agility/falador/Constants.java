/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agility.falador;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

/**
 *
 * @author t7emon
 */
public class Constants {
    
    //roof count = 11
    
   
         public static int food = 385; //Food to take from bank || 385 = Shark, 379 = Lobster
        //public static int food_amount = 5; //Amout of food to take from bank
         public static int stamina = 343;
         public static Tile START_LOCATION = new Tile(3036, 3341, 0);
         public static Area ONE_ROOF = new Area(3036, 3342, 3040, 3342, 3); //x1, y2, x2, y2, z
         public static Area TWO_ROOF = new Area(3044, 3343, 3051, 3349, 3); //x1, y2, x2, y2, z
         public static Area THREE_ROOF = new Area(3050, 3357, 3049, 3358, 3); //x1, y2, x2, y2, z
         public static Area FOUR_ROOF = new Area(3048, 3361, 3045, 3367, 3); //x1, y2, x2, y2, z
         public static Area FIVE_ROOF = new Area(3041, 3364, 3035, 3361, 3); //x1, y2, x2, y2, z
         public static Area SIX_ROOF = new Area(3029, 3354, 3026, 3352, 3); //x1, y2, x2, y2, z //maybe redo     
         public static Area SEVEN_ROOF = new Area(3020, 3353, 3009, 3358, 3); //x1, y2, x2, y2, z
         
        public static Area AIGHT_ROOF = new Area(3018, 3349, 3022, 3343, 3); //x1, y2, x2, y2, z
        public static Area NINE_ROOF = new Area(3014, 3346, 3011, 3344, 3); //x1, y2, x2, y2, z
        public static Area TEN_ROOF = new Area(3009, 3342, 3013, 3336, 3); //x1, y2, x2, y2, z
        public static Area ELEVEN_ROOF = new Area(3012, 3333, 3017, 3334, 3); //x1, y2, x2, y2, z
       public static Area TWELF_ROOF = new Area(3019, 3332, 3024, 3335, 3); //x1, y2, x2, y2, z
    
}
