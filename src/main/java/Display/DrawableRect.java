package main.java.Display;
import java.awt.Color;
import java.awt.Rectangle;

public class DrawableRect{
    Rectangle rect;
    boolean fill;
    Color color;
    public DrawableRect(Rectangle rect, boolean fill, Color color){
        this.rect = rect;
        this.fill = fill;
        this.color = color;
    }
}