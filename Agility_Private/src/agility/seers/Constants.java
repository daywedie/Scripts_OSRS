package agility.seers;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

/**
 *
 * @author T7emon, Tear x, Null x, T7x
 */
public class Constants {
    /*
    * Variables
   */
        public static int food = 361; //Food to take from bank || 385 = Shark, 379 = Lobster, 361 = Tuna
        public static int food_amount = 5; //Amout of food to take from bank
        public static int stamina = 343;
        public static Tile WALL_LOCATION = new Tile(2729, 3488, 0);
         
 /*
    * The roofs area enum.
    */
    public enum roofs {
      ROOF_1(new Area(2730, 3497, 2722, 3490, 3)),  //x1, y2, x2, y2, z
      ROOF_2(new Area(2713, 3493, 2705, 3496, 2)),
      ROOF_3(new Area(2716, 3482, 2710, 3477, 2)),
      ROOF_4(new Area(2714, 3472, 2700, 3469, 3)),
      ROOF_5(new Area(2703, 3466, 2698, 3460, 2));
      
      /*
      * Area variable
      */
     private Area area;
     
     /*
     * The roof area setter.
     */
      private roofs(Area area) {
          this.area = area;
      }
      /*
      * The roof area getter.
      */
      public Area getArea() {
          return area;
      }
    }}