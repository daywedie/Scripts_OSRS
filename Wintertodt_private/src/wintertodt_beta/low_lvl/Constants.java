/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wintertodt_beta.low_lvl;

import wintertodt_private.*;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

/**
 *
 * @author t7emon
 */
public class Constants {
    
    //public static int food = 385; //Food to take from bank = Shark
    //public static int food_amount = 7; //7
    public static int food = 361; //1891 = Cake, 361 = Tuna
    public static int food_amount = 7; //7 Amout of food to take from bank for mid lvl pure
    
    public static int roots_amount = 10; //Amount of roots & kindling to cut, fletch & feed //10
    public static int kindling_amount = 10;
    
    public static int tinderbox = 590;
    public static int axe = 6739; //dragon axe
    public static int knife = 946;
    public static int hammer = 2347;
    
    public static int roots = 20695;
    public static int kindling = 20696;
    
    public static int rewardbox = 20703; //Supply crate
    
    public static Area Wintertodt = new Area(1612, 3998, 1648, 3968); //x1, y2, x2, y2
            //private Area Varrock = new Area(3271,3519,3136,3381);
    public static Tile DOOR_LOCATION = new Tile(1630, 3962, 0);
    public static Tile LEAVE_LOCATION = new Tile(1631, 3969, 0); //leave at end of wintertodt to rebank
    public static Tile TREE_LOCATION = new Tile(1639, 3991, 0); //EAST
    
}
