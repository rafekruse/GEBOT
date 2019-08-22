package main.java.Tools;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import main.Constants;

import java.awt.*;

public class WindowPositionUpdater implements Runnable {

    private String windowName;

    public WindowPositionUpdater(String name) {
        windowName = name;
    }

    public void run() {
        while (true) {
            for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
                if (desktopWindow.getTitle().contains(windowName)) {
                    Constants.programOriginPosition = desktopWindow.getLocAndSize();

                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
