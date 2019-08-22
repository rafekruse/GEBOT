package main.java.Tools;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import main.Constants;
import main.Inventory;
import main.java.DataManagement.ExchangeDataPullUtil;

import java.awt.*;

public class ExchangeDataUpdater implements Runnable {

    private String windowName;

    public ExchangeDataUpdater(String name) {
        windowName = name;
    }

    public void run() {
        while (true) {
            try {
                ExchangeDataPullUtil.getInstance().ParseFromURLToMap();
                Thread.sleep(5000);
                ExchangeDataPullUtil.getInstance().FilterAllForViable(Inventory.getInstance().getCoins(), Constants.minProfit);
                Thread.sleep(5 * 60 * 1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
