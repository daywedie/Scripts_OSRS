package castle_wars.sabotage;

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
    
        public static Area Castle_wars_lobby = new Area(2443, 3082, 2438, 3097, 0);
        public static GameObject Guthix_Portal;
        
         public static Area Zamorak_waiting_room = new Area(2429, 9515, 2412, 9518, 0);
         public static Area Saradomin_waiting_room = new Area(2391, 9483, 2370, 9491, 0);
         
         public static Area Saradomin_respawn_room = new Area(2429, 3074, 3423, 3080, 1);
         public static Area Saradomin_main_floor = new Area(0000, 0000, 0000, 0000, 0);
         public static Area Saradomin_first_floor = new Area(3420, 3072, 3422, 3072, 0);
         
         public static Area Zamorak_respawn_room = new Area(0000, 0000, 0000, 0000, 0);
         public static Area Zamorak_main_floor = new Area(0000, 0000, 0000, 0000, 0);
         public static Area Zamorak_first_floor = new Area(0000, 0000, 0000, 0000, 0);
         
         public static GameObject Energy_Barrier;
         public static NPC Barricade;
         public static Item Barricade_Inv;
         public static Tile Saradomin_spawn_tile = new Tile(2422, 3076, 1); //?? -> check area instead
         
         public static int game_count = 0;
}
