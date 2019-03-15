/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agility_private.pollnivneach;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

/**
 *
 * @author t7emon
 */
public class Constants {
    
         public static int food = 385; //Food to take from bank || 385 = Shark, 379 = Lobster
        //public static int food_amount = 5; //Amout of food to take from bank
         public static int stamina = 343;
         public static Tile BASKET_LOCATION = new Tile(3351, 2961, 0);
         public static Area FIRST_ROOF = new Area(3351, 2964, 3346, 2968, 1); //x1, y2, x2, y2, z
         public static Area SECOND_ROOF = new Area(3352, 2973, 3355, 2976, 1); //x1, y2, x2, y2
         public static Area THIRD_ROOF = new Area(3360, 2977, 3362, 2979, 1); //x1, y2, x2, y2
         public static Area FOURTH_ROOF = new Area(3366, 2976, 3369, 2974, 1); //x1, y2, x2, y2
         public static Area FIFTH_ROOF = new Area(3368, 2982, 3365, 2986, 1); //x1, y2, x2, y2
         public static Area SIXTH_ROOF = new Area(3365, 2983, 3357, 2980, 2); //x1, y2, x2, y2
         public static Area SEVENTH_ROOF = new Area(3358, 2991, 3370, 2995, 2); //x1, y2, x2, y2
         public static Area FINAL_ROOF = new Area(3356, 3000, 3362, 3004, 2); //x1, y2, x2, y2
}