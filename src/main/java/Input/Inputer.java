package main.java.Input;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class Inputer {

    private static Inputer instance;
    private int minWait;
    private int maxWait;
    private int padding;
    private Robot robot;

    public void Initialize(int minWait_, int maxWait_, int padding_) {
        padding = padding_;
        minWait = minWait_;
        maxWait = maxWait_;
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Inputer Initialized");

    }

    private Inputer() {
    }

    public static Inputer getInstance() {
        if (instance == null) {
            instance = new Inputer();
        }
        return instance;
    }

    public void ClickInRectAndPause(Rectangle screenArea, int event) {
        ClickInRect(screenArea, event);
        RandomSleep();
    }

    public void ClickAtPosition(Point point, int event) {
        robot.mouseMove(point.x, point.y);
        RandomSleep();
        robot.mousePress(event);
        RandomSleep();
        robot.mouseRelease(event);
    }

    public void ClickInRect(Rectangle screenArea, int event) {
        Point randomPoint = RandomPointInRect(screenArea, padding);
        ClickAtPosition(randomPoint, event);
    }

    public static Point RandomPointInRect(Rectangle screenArea, int padding) {
        if (padding * 2 < screenArea.width
                && padding * 2 < screenArea.height) {
            int outputX = new Random()
                    .nextInt(screenArea.width - (padding * 2) + 1)
                    + (screenArea.x);
            int outputY = new Random()
                    .nextInt(screenArea.height - (padding * 2) + 1)
                    + (screenArea.y);
            return new Point(outputX, outputY);
        } else
            return null;
    }


    public void RandomSleep() {
        SleepInRange(minWait, maxWait);
    }

    public void SleepInRange(int min, int max) {
        try {
            Thread.sleep(RandomInRange(min, max));
        } catch (InterruptedException e) {
            System.out.println("Wasn't able to Random Sleep");
        }
    }

    public int RandomInRange(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}