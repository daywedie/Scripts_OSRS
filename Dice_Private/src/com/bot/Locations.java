/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bot;

import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.map.Area;
/**
 *
 * @author t7emon
 */
public class Locations {
    
    public static Area Area;
    public static Tile CenterTile;
    
     /*
    * The area enum
    */
    public enum Areas {
      castleWarsArea(new Area(2446, 3082, 2438, 3097, 0));
      
      /*
      * area variable
      */
     private Area area;
     
     /*
     * The area setter.
     */
      private Areas(Area area) {
          this.area = area;
      }
      /*
      * The area getter.
      */
      public Area area() {
          return area;
      }
    }
    /*
    * CenterTiles
    */
    public enum centerTile {
        castleWarsCenterTile(new Tile(2441, 3087, 0));
    
    private Tile tile;
    
    private centerTile(Tile tile) {
        tile = tile;
    }
    //Tile castleWarsCenterTile = new Tile(2441, 3087, 0);
    
    public Tile tile() {
        return tile;
    }}}
