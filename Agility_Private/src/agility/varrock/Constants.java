package agility.varrock;

import org.dreambot.api.methods.map.Area;

/**
 *
 * @author T7emon, Tear x, Null x, T7x
 */
public class Constants {
    /*
    * Variables
   */
       public static int food = 361; //Food to take from bank || 385 = Shark, 379 = Lobster, 361 = Tuna
       public static int stamina = 343;
       
    /*
    * The roofs area enum.
    */
    public enum roofs {
      ROOF_1(new Area(3219, 3411, 3214, 3419, 3)),
      ROOF_2(new Area(3209, 3414, 3202, 3417, 3)),
      ROOF_3(new Area(3197, 3416, 3194, 3416, 1)),
      ROOF_4(new Area(3192, 3406, 3198, 3402, 3)),
      ROOF_5(new Area(3188, 3398, 3208, 3391, 3)),
      ROOF_6(new Area(3218, 3402, 3232, 3393, 3)),
      ROOF_7(new Area(3236, 3403, 3240, 3408, 3)),
      ROOF_8(new Area(3236, 3410, 3240, 3414, 3));
      
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