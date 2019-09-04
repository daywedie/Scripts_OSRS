
import static java.rmi.Naming.list;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.List;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;

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
    
      public static Area Duel_Area = new Area(3323, 3230, 3305, 3268, 0); //x1, y2, x2, y2, z
      public static Area Altar_Area = new Area(2569, 4853, 2594, 4823, 0); //x1, y2, x2, y2, z
   
    public static int ring_of_duelling8 = 2552; //ring of duelling 8
    public static int ring_of_duelling7 = 2554; //ring of duelling 7
    public static int ring_of_duelling6 = 2556; //ring of duelling 6
    public static int ring_of_duelling5 = 2558; //ring of duelling 5
    public static int ring_of_duelling4 = 2560; //ring of duelling 4
    public static int ring_of_duelling3 = 2562; //ring of duelling 3
    public static int ring_of_duelling2 = 2564; //ring of duelling 2
    public static int ring_of_duelling1 = 2566; //ring of duelling 1 to remove and take another for bank if needed
    
    public static int rune_to_craft = 7936;  //Pure essence
     
    public static Tile mysterious_ruins_tile = new Tile(3312, 3252, 0);
    public static Tile altar_tile = new Tile(2583, 4841, 0);
}
