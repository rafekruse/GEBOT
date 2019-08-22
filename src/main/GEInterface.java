package main;

import main.java.DataStructures.TradeWindow;
import main.java.DataStructures.TradingItem;
import main.java.DataStructures.WindowState;
import main.java.Input.Inputer;
import main.java.Input.Typer;
import main.java.Tools.PixelCalculation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//This is meant to interact with the window, no Logic should happen here
public class GEInterface {

    private static GEInterface instance;
    private GEInterface(){}
    public static GEInterface getInstance() {
        if (instance == null) {
            instance = new GEInterface();
        }
        return instance;
    }



    private TradeWindow[] windows;

    public void Initialize(){
        windows = new TradeWindow[Constants.validWindows.length];
        for(int i = 0; i < Constants.validWindows.length; i ++){
            windows[i] = new TradeWindow(i + 1);
            if(Constants.validWindows[i] == 0){
                windows[i].setState(WindowState.InvalidWindow);
           }
        }
        System.out.println("GEInterface Initialized");
    }



    public boolean OpenWindows() {
        return getWindowStateCount(WindowState.Empty) > 0;
    }

    private void QuantityPriceConfirm(TradingItem item) {
        Inputer.getInstance().ClickInRectAndPause(Constants.itemQuantity, Constants.leftClick);
        Typer.getInstance().TypeAndPause(item.getQuantity() + "\n");

        Inputer.getInstance().ClickInRectAndPause(Constants.itemPrice, Constants.leftClick);
        Typer.getInstance().TypeAndPause(item.getBuyPrice() + "\n");

        Inputer.getInstance().ClickInRectAndPause(Constants.confirmTrade, Constants.leftClick);
    }

    public void MakeBuyOffer(TradingItem item) {
        int firstOpenIndex = getOpenWindowIndex();
        if (firstOpenIndex == -1) {
            System.out.println("No Available Open Window to Buy from.");
            return;
        }
        Inputer.getInstance().ClickInRectAndPause(GetBuyScreenArea(firstOpenIndex), Constants.leftClick);
        Typer.getInstance().TypeAndPause(item.getName());
        Typer.getInstance().TypeAndPause("\n");

        QuantityPriceConfirm(item);

        windows[firstOpenIndex].SetBuyData(item);
    }

    public void MakeSaleOffer(TradingItem item, int row, int col) {
        int firstOpenIndex = getOpenWindowIndex();
        if (firstOpenIndex == -1) {
            System.out.println("No Available Open Window to Sell from.");
            return;
        }
        Inputer.getInstance().ClickInRectAndPause(GetSellScreenArea(firstOpenIndex), Constants.leftClick);
        Inputer.getInstance().ClickInRectAndPause(Inventory.GetScreenAreaOfInventorySlot(row, col), Constants.leftClick);

        QuantityPriceConfirm(item);

        windows[firstOpenIndex].SetSellData(item);
    }

    public void CancelTrade(TradingItem item) {
        CancelTrade(GetWindowIndexByName(item.getName()));
    }

    public void CancelTrade(String itemName) {
        CancelTrade(GetWindowIndexByName(itemName));
    }

    public void CancelTrade(int windowIndex) {
        if (windowIndex == -1) {
            System.out.println("Window Can't be cancelled. In state " + windows[windowIndex].getState().toString());
            return;
        }

        Inputer.getInstance().ClickInRectAndPause(GetTradeWindowScreenArea(windowIndex), Constants.leftClick);
        Inputer.getInstance().ClickInRectAndPause(Constants.offerCancelButton, Constants.leftClick);

        Inputer.getInstance().ClickInRectAndPause(Constants.exitOfferButton, Constants.leftClick);

        windows[windowIndex].setState(WindowState.Cancelled);

    }


    public void CollectItem(TradingItem item) {
        CancelTrade(GetWindowIndexByName(item.getName()));
    }

    public void CollectItem(String itemName) {
        CancelTrade(GetWindowIndexByName(itemName));
    }

    public void CollectItem(int windowIndex) {
        if (windowIndex == -1) {
            System.out.println("Window Can't be cancelled. In state " + windows[windowIndex].getState().toString());
            return;
        }

        Inputer.getInstance().ClickInRectAndPause(GetTradeWindowScreenArea(windowIndex), Constants.leftClick);

        Inputer.getInstance().ClickInRectAndPause(Constants.tradeCollectionTwo, Constants.leftClick);
        Inputer.getInstance().ClickInRectAndPause(Constants.tradeCollectionOne, Constants.leftClick);

        //need to screen scan quanity collected
        // add to collect
        //need to return total - collected
        //set collected to total;
        //then have trader create an inventory item for it and add to invent

        //  InventoryItem i = new InventoryItem(name, alreadyTraded - collected);


        windows[windowIndex].setState(WindowState.Empty);


    }

    public int getWindowStateCount(WindowState win) {
        int count = 0;
        for (TradeWindow window : windows
        ) {
            if (window.getState() == win) {
                count++;
            }
        }
        return count;
    }

    private int getOpenWindowIndex() {
        for (int i = 0; i < windows.length; i++) {
            if (windows[i].getState() == WindowState.Empty) {
                return i;
            }
        }
        return -1;
    }

    public int GetWindowIndexByName(String itemName) {
        for (int i = 0; i < windows.length; i++) {
            if (windows[i].getItem().getName().equals(itemName)) {
                return i;
            }
        }
        return -1;
    }

    public TradeWindow[] getWindows(){
        return windows;
    }
    public TradeWindow[] getWindowsInState(WindowState... win) {
        List<TradeWindow> wins = new ArrayList<>();
        for (int i = 0; i < windows.length; i++) {
            for (int j = 0; j < win.length; j++) {
                if (windows[i].getState() == win[j]) {
                    wins.add(windows[i]);
                }
            }
        }
        return (TradeWindow[]) wins.toArray();
    }

    public Rectangle GetBuyScreenArea(int windowNumber) {
        int row = (windowNumber - 1) / 4;
        int col = (windowNumber - 1) % 4;
        return PixelCalculation.GetScreenAreaOfRepeatingFormat(row, col, Constants.buyButtonOne, Constants.buyButtonHoriTilingGap, Constants.buyButtonVertTilingGap);
    }

    public Rectangle GetSellScreenArea(int windowNumber) {
        int row = (windowNumber - 1) / 4;
        int col = (windowNumber - 1) % 4;
        return PixelCalculation.GetScreenAreaOfRepeatingFormat(row, col, Constants.sellButtonOne, Constants.sellButtonHoriTilingGap, Constants.sellButtonVertTilingGap);
    }

    public Rectangle GetTradeWindowScreenArea(int windowNumber) {
        int row = (windowNumber - 1) / 4;
        int col = (windowNumber - 1) % 4;
       // System.out.println("this " + PixelCalculation.GetScreenAreaOfRepeatingFormat(row, col, Constants.tradeProgressBarOne, Constants.tradeProgressHoriTilingGap, Constants.tradeProgressVertTilingGap));
        return PixelCalculation.GetScreenAreaOfRepeatingFormat(row, col, Constants.tradeWindowSquareOne, Constants.tradeWindowHoriTilingGap, Constants.tradeWindowVertTilingGap);
    }

    public Rectangle GetTradeProgressBar(int windowNumber) {
        int row = (windowNumber - 1) / 4;
        int col = (windowNumber - 1) % 4;
        return PixelCalculation.GetScreenAreaOfRepeatingFormat(row, col, Constants.tradeProgressBarOne, Constants.tradeProgressHoriTilingGap, Constants.tradeProgressVertTilingGap);
    }
}


   