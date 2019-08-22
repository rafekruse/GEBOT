package main.java.Display;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawImage extends JPanel {

    List<DrawableImage> images;
    
    public DrawImage(DrawableImage image){
        images = new ArrayList<DrawableImage>();
        images.add(image);
    }
    public DrawImage(List<DrawableImage> images){        
        this.images = images;
    }
    public void AddImage(DrawableImage image){
        images.add(image);
    }
    public void paintComponent(Graphics g) {
        for (int i = 0; i < images.size(); i++) {
            DrawableImage b = images.get(i);
            g.drawImage(b.image, b.xLoc, b.yLoc, null);
        }
    }
}

