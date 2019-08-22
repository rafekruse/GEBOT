package main;

import main.java.DataManagement.ExchangeDataPullUtil;
import main.java.DataStructures.InventoryItem;
import main.java.DataStructures.ItemExchangeInfo;
import main.java.DataStructures.TradeWindow;
import main.java.Input.Inputer;
import main.java.Tools.HelperFunctions;
import main.java.Tools.PatternMatching;
import main.java.Tools.PixelCalculation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Inventory {

    private static Inventory instance;

    private Inventory() {
    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }


    private InventoryItem[][] inventoryItems;

    public void Initialize() {
        inventoryItems = new InventoryItem[7][4];
        for (int i = 0; i < inventoryItems.length; i++) {
            for (int j = 0; j < inventoryItems[i].length; j++) {

                System.out.println("Found : " + ExamineForItemName(GetScreenAreaOfInventorySlot(i, j)));
              //  inventoryItems[i][j] =
            }
        }
        System.out.println("Inventory Initialized");
    }

    public String ExamineForItemName(Rectangle initial){
        String startChatLine = PatternMatching.TextFromBufferedImage(PixelCalculation.RelateToWindowGlobal(Constants.firstChatLineRect));
        Inputer.getInstance().ClickAtPosition(new Point(initial.x, initial.y), Constants.rightClick);
        Inputer.getInstance().ClickAtPosition(new Point(initial.x + Constants.inventoryExamineXOffset, initial.y + Constants.inventoryExamineYOffset), Constants.leftClick);
        String firstExamineChatLine = PatternMatching.TextFromBufferedImage(PixelCalculation.RelateToWindowGlobal(Constants.firstChatLineRect));
        if(!startChatLine.equals(firstExamineChatLine)){
            String firstExamineSecondChatLine = PatternMatching.TextFromBufferedImage(PixelCalculation.RelateToWindowGlobal(Constants.secondChatLineRect));
            if(firstExamineChatLine.contains("Item:")){
                return firstExamineChatLine;
            }
            else if(firstExamineSecondChatLine.contains("Item:")){
                return firstExamineSecondChatLine;
            }
        }
        else {
            Inputer.getInstance().ClickAtPosition(new Point(initial.x, initial.y), Constants.rightClick);
            Inputer.getInstance().ClickAtPosition(new Point(initial.x+ Constants.inventoryExamineXOffset, initial.y + Constants.inventoryExamineYOffset - 15), Constants.leftClick);
            String secondExamineChatLine = PatternMatching.TextFromBufferedImage(PixelCalculation.RelateToWindowGlobal(Constants.firstChatLineRect));
            Inputer.getInstance().RandomSleep();
            if(!startChatLine.equals(secondExamineChatLine)){
                String secondExamineSecondChatLine = PatternMatching.TextFromBufferedImage(PixelCalculation.RelateToWindowGlobal(Constants.secondChatLineRect));
                if(secondExamineChatLine.contains("Item:")){
                    return secondExamineChatLine;
                }
                else if(secondExamineSecondChatLine.contains("Item:")){
                    return secondExamineSecondChatLine;
                }
            }
            else return "";
        }
        System.out.println("EXAMINE IN BROKEN");
        return "";
    }

    public int getCoins() {
        return getItem("Coins").getInventoryQuantity();
    }


    public void setCoins(int coins) {
        getItem("Coins").setInventoryQuantity(coins);
    }

    public void addItem(InventoryItem item) {
        for (int i = 0; i < inventoryItems.length; i++) {
            for (int j = 0; j < inventoryItems[i].length; j++) {
                if (inventoryItems[i][j].getName() == item.getName()) {
                    inventoryItems[i][j].addInventoryQuantity(item.getInventoryQuantity());
                    return;
                }
            }
        }
        for (int i = 0; i < inventoryItems.length; i++) {
            for (int j = 0; j < inventoryItems[i].length; j++) {
                if (inventoryItems[i][j] == null) {
                    inventoryItems[i][j] = item;
                    return;
                }
            }
        }
    }

    public void setItem(InventoryItem item, int inventoryLocation) {
        int row = (inventoryLocation - 1) / 4;
        int column = (inventoryLocation - 1) % 4;
        inventoryItems[row][column] = item;
    }

    public InventoryItem getItem(String name) {
        for (int i = 0; i < inventoryItems.length; i++) {
            for (int j = 0; j < inventoryItems[i].length; j++) {
                if (inventoryItems[i][j].getName() == name) {
                    return inventoryItems[i][j];
                }
            }
        }
        return null;
    }

    public int getItemPosition(String name) {
        for (int i = 0; i < inventoryItems.length; i++) {
            for (int j = 0; j < inventoryItems[i].length; j++) {
                if (inventoryItems[i][j].getName() == name) {
                    return (i * j) + j + 1;
                }
            }
        }
        return -1;
    }


    public static Rectangle GetScreenAreaOfInventorySlot(int row, int col) {
        return PixelCalculation.RelateToWindowGlobal(PixelCalculation.GetScreenAreaOfRepeatingFormat(row, col, Constants.inventorySlotOne, Constants.inventoryHoriTilingGap, Constants.inventoryVertTilingGap));
    }

    public static Rectangle GetScreenAreaOfInventoryQuantities(int slot) {
        int row = (slot - 1) / 7;
        int column = (slot - 1) % 4;
        return PixelCalculation.GetScreenAreaOfRepeatingFormat(row, column, Constants.inventorySlotOne, Constants.inventoryHoriTilingGap, Constants.inventoryVertTilingGap);
    }


}