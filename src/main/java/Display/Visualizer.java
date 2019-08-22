package main.java.Display;

import main.Constants;
import main.GEInterface;
import main.java.DataStructures.TradeWindow;
import main.java.DataStructures.WindowState;
import main.java.Input.PlayerKeyListener;
import main.java.Tools.PixelCalculation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Visualizer {


    private static Visualizer instance;
    private Visualizer(){}
    public static Visualizer getInstance() {
        if (instance == null) {
            instance = new Visualizer();
        }
        return instance;
    }

    private static int goldMade;
    private static int currentNetWorth;
    public static JFrame window;
    private static DrawRectangles rectDrawer;


    public void Initialize(){
        CreateFrame();
        window.addKeyListener(new PlayerKeyListener());
        rectDrawer = new DrawRectangles();
        ToggleOverlay();
        Runnable runnable = new GUIUpdater(this);
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("Visualize Initialized");
    }

    private void CreateFrame() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setAlwaysOnTop(true);
        window.setSize(Constants.programOriginPosition.width, Constants.programOriginPosition.height);
        window.setBackground(new Color(0, true));
        window.setVisible(true);

    }

    public void AddImageToFrame(Component drawImage){
        window.add(drawImage);
        window.revalidate();
        window.repaint();
    }

    public void updateWindowOutlines() {
        List<DrawableRect> rects = new ArrayList<>();


        for (int i = 0; i < 8; i++) {
            Color chosenColor = Constants.idleWindowColor;
            TradeWindow window = GEInterface.getInstance().getWindows()[i];
            if (window.getState() == WindowState.Buy) {
                chosenColor = Constants.buyingWindowColor;
            } else if (window.getState() == WindowState.Sell) {
                chosenColor = Constants.sellingWindowColor;
            } else if (window.getState() == WindowState.Cancelled) {
                chosenColor = Constants.cancelledWindowColor;
            } else if (window.getState() == WindowState.Complete) {
                chosenColor = Constants.completeWindowColor;
            }
            Rectangle rect = RelateToWindowLocal(GEInterface.getInstance().GetTradeWindowScreenArea(window.getWindowNumber()));
            rects.add(new DrawableRect(rect, window.getState() == WindowState.InvalidWindow, chosenColor));
        }
        rectDrawer.ClearRectangels();
        rectDrawer.addRectangles(rects);
    }

    public void ToggleOverlay() {
        if (rectDrawer.isShowing()) {
            System.out.println("Removing Overlay");
            window.remove(rectDrawer);
        } else {
            System.out.println("Drawing Overlay");


            AddImageToFrame(rectDrawer);
        }
    }private Rectangle RelateToWindowLocal(Rectangle rect) {
        return new Rectangle((Constants.programOriginPosition.width / 2) + rect.x, rect.y, rect.width, rect.height);
    }
}

class GUIUpdater implements Runnable{

    public GUIUpdater(Visualizer visualizer){}

    public void run(){
        while(true){
        try {
            Visualizer.window.setLocation(new Point(Constants.programOriginPosition.x, Constants.programOriginPosition.y));
            Visualizer.window.setSize(Constants.programOriginPosition.width,Constants.programOriginPosition.height);
            Visualizer.getInstance().updateWindowOutlines();
            Visualizer.window.revalidate();
            Visualizer.window.repaint();
            Thread.sleep(100);
        }catch(Exception exception){
            exception.printStackTrace();
        }}
    }


}
