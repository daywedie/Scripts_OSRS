package agility.pollnivneach;

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
         public static int food = 361; //385 = Shark, 379 = Lobster, 361 = Tuna
         public static int stamina = 343;
         public static Tile BASKET_LOCATION = new Tile(3351, 2961, 0);
         
   /*
    * The roofs area enum.
    */
    public enum roofs {
      ROOF_1(new Area(3351, 2964, 3346, 2968, 1)),
      ROOF_2(new Area(3352, 2973, 3355, 2976, 1)),
      ROOF_3(new Area(3360, 2977, 3362, 2979, 1)),
      ROOF_4(new Area(3366, 2976, 3369, 2974, 1)),     
      ROOF_5(new Area(3368, 2982, 3365, 2986, 1)),
      ROOF_6(new Area(3365, 2983, 3357, 2980, 2)),
      ROOF_7(new Area(3358, 2991, 3370, 2995, 2)),
      ROOF_8(new Area(3356, 3000, 3362, 3004, 2));
      
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