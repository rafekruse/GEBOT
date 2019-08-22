package main.java.Display;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DrawRectangles extends JPanel {

    private List<DrawableRect> rectangles;
    
    public DrawRectangles(DrawableRect... rects){
        rectangles = new ArrayList<>(Arrays.asList(rects));
    }
    public DrawRectangles(List<DrawableRect> rects){        
        rectangles = rects;
    }
    public void addRectangles(DrawableRect... rects){
        rectangles.addAll(Arrays.asList(rects));
    }
    public void addRectangles(List<DrawableRect> rects){
        rectangles.addAll(rects);
    }
    public void ClearRectangels(){
        rectangles.clear();
    }
    public void paintComponent(Graphics g) {
        for (int i = 0; i < rectangles.size(); i++) {
            DrawableRect d = rectangles.get(i);
            g.setColor(d.color);
            if (d.fill) {            
                g.fillRect(d.rect.x, d.rect.y, d.rect.width, d.rect.height);
            } else {
                g.drawRect(d.rect.x, d.rect.y, d.rect.width, d.rect.height);
            }
        }
    }
}

