package main.java.Input;

import main.Constants;
import main.java.Display.Visualizer;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class PlayerKeyListener extends KeyAdapter {

    private final Set<Integer> pressed = new HashSet<>();


    private boolean PressingKeyCombo(int... keys){
        for (int key: keys) {
            boolean isPressed = false;
            for (Integer down: pressed) {
                if(key == down){
                    isPressed = true;
                }
            }
            if(!isPressed){
                return false;
            }
        }
        return true;
    }

    @Override
    public void keyPressed(KeyEvent event) {

        pressed.add(event.getKeyCode());

        if(PressingKeyCombo(KeyEvent.VK_CONTROL, KeyEvent.VK_O)){
            Visualizer.getInstance().ToggleOverlay();
        }


        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;

        try {
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                new Robot().mouseMove(x, y - 1);
            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                new Robot().mouseMove(x + 1, y);
            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                new Robot().mouseMove(x - 1, y );
            } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                new Robot().mouseMove(x, y + 1);
            }
            if(event.getKeyCode() == KeyEvent.VK_SPACE){
                System.out.println("(" + (x - (Constants.programOriginPosition.x + Constants.programOriginPosition.width / 2)) + "," + (y-Constants.programOriginPosition.y) + ")");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void keyReleased(KeyEvent event){
        pressed.remove(event.getKeyCode());
    }
}
