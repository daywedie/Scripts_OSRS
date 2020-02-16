package com.bot.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author T7emon, Tear x, Null x, T7x
 */
public class ImageUtils {
    
 public static BufferedImage getImage(String url) {
 try {
 return ImageIO.read(new URL(url));
 }
 catch (IOException e) {
 }
 return null;
 }
}
