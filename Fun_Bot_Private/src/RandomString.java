
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author t7emon
 */
public class RandomString {
    
    static String getAlphaNumericString(int n) 
    { 
  
        // lower limit for LowerCase Letters 
        int lowerLimit = 97; 
  
        // lower limit for LowerCase Letters 
        int upperLimit = 122; 
  
        Random random = new Random(); 
  
        // Create a StringBuffer to store the result 
        StringBuffer r = new StringBuffer(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // take a random value between 97 and 122 
            int nextRandomChar = lowerLimit 
                                 + (int)(random.nextFloat() 
                                         * (upperLimit - lowerLimit + 1)); 
  
            // append a character at the end of bs 
            r.append((char)nextRandomChar); 
        } 
  
        // return the resultant string 
        return r.toString(); 
    } 
    
}
