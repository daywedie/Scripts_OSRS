/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wintertodt_private.utils;

/**
 *
 * @author t7emon
 */
    
public class RunTimer
{
    private long start;
       
    public RunTimer() {
        start = System.currentTimeMillis();
    }
 
    public long getElapsed() {
        return System.currentTimeMillis() - start;
    }
 
    public boolean isRunning() {
        return getElapsed() > 0;
    }
    
    public long getPerHour(long amt){
    	return (long)((amt*3600000D)/getElapsed());
    }
 
    public String format() {
    	long milliSeconds = getElapsed();
        long secs = milliSeconds / 1000L;
        return String.format("%02d:%02d:%02d", secs / 3600L, (secs % 3600L) / 60L, secs % 60L);
    }
}
