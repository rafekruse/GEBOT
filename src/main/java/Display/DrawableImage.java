package main.java.Display;

import java.awt.image.BufferedImage;

public class DrawableImage{
    BufferedImage image;
    int xLoc;
    int yLoc;
    
    public DrawableImage(BufferedImage image, int xLoc, int yLoc){
        this.image = image;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}