package com.bot.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author T7emon
 */
public class Logger {
    
        public BufferedWriter Writer;
        
        public void Log(String Message) {
             try {
         /*
         * File writer
         */
            Writer = new BufferedWriter(new FileWriter(""));
            Writer.append(Message + "\n");
            Writer.flush();
             } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }      
        }
}
