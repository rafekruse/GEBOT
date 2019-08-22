package main.java.Tools;

import main.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HelperFunctions {

    private HelperFunctions(){}

    public static boolean bufferedImagesEqual(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() == image2.getWidth() && image1.getHeight() == image2.getHeight()) {
            for (int x = 0; x < image1.getWidth(); x++) {
                for (int y = 0; y < image1.getHeight(); y++) {
                    if (image1.getRGB(x, y) != image2.getRGB(x, y)){
                      //  printPixelARGB(image1.getRGB(x,y)); printPixelARGB(image2.getRGB(x,y));
                       // System.out.println(x+ "   " + y);
                      //  System.out.println("not lining up");
                        return false;}
                }
            }
        } else {
            System.out.println("sizes");
            return false;
        }
        return true;
    }

    public static void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }

}
