/**
 *
 * @author t7emon
 */
public class Constants {
    
    /*
    * Variables
    */
        private static String Name = "Woodcutter Pro";
        private static String Status;
        private static String Tree;
        private static boolean Start;
    
    /*
    * Getters & Setters
    */
    public static String getName() {
        return Name;
    }
    
    public static String setStatus(String status) {
        return Status = status;
    }
    
    public static String getStatus() {
        return Status;
    }
    
    public static String setTree(String tree) {
        return Tree = tree;
        
    }
    public static String getTree() {
        return Tree;
    }
    
    public static boolean setStart(boolean value) {
        return Start = value;
        
    }
    public static boolean getStart() {
        return Start;
    }
    
}
