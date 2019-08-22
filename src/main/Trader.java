package main;

import main.java.DataManagement.ExchangeDataPullUtil;
import main.java.DataStructures.BuyCooldown;
import main.java.DataStructures.TradingItem;
import main.java.DataStructures.WindowState;
import main.java.Input.Inputer;

import java.util.List;


public class Trader{

    private Inventory inventory;
    private List<BuyCooldown> buyCooldowns;

    private long maximumTimeBetweenTrades;
    private int gold;

    public Trader(int minProfit, int minWait, int maxWait, int padding){
        //Find Coin Quantity and Position
       // inventory = new Inventory(startingGold, coinsPosition);
        Inputer.getInstance().Initialize(minWait, maxWait, padding);
    }


    public void StartBot(){
        System.out.println("Bot Started");

        // while(true){






        //Collect Everything
        //Cancel Buy/Repost Sale
            //If nothing bought and is no longer the best cancel buy
            //If something has purchased
                //15 minutes no activity
                //No longer an items in viable items
            //If It hasn't sold for 30 minutes, recalculate margin and resell
        //Collect Everything
        //Add to Running Sells
        //New Sell(prioritize the no longer buying
        //New Buys
        //Sell(currently Buying)




       // }



        //Assess All data, update shit

        //1. Cancel trades
        /*    List<DataStructures.TradeWindow> activeWindows = tradeWindows.getWindowsInState(WindowState.Buy, WindowState.Sell);
            for (int i = 0; i < activeWindows.size(); i++) {
                if(System.currentTimeMillis() - activeWindows.get(i).getLastTraded() > maximumTimeBetweenTrades){
                    activeWindows.get(i).CancelTrade();
                }
            }


        //2. Collect all trades that are complete or cancelled

        List<DataStructures.TradeWindow> readyToBeCollected = tradeWindows.getWindowsInState(WindowState.Cancelled, WindowState.Complete);
        for(int i = 0; i < readyToBeCollected.size(); i++){
            readyToBeCollected.get(i).CollectItems();
            
        }*/

        //Make sure to collect on one item if no coins to avoid error
            //Also collect in progress
        //3. Sell Items in invent but not currently buying(only in invent)
            //Check is sell is already going on, if so sell together
            //Sell at trade window price
        //4. Buy if We have the money for it
            //Starting with most tradable items keep price checking until viable good item
        //5 Sell 
            //follow previous logic of selling done items first but if out of those
            //sell something being currently traded.
            
    }























/*
   
    
 
  

    public static void CheckHistoryForMargin(ItemExchangeInfo item, boolean buy) throws InterruptedException{
        ClickInRect(PixelLocations.historyButton, GlobalVariables.buySellPixelPadding);
        RandomMethods.RandomSleep(1500, 3000);
        int value = PatternMatching.NumberFromScreenArea(PixelLocations.singleItemHistory, GlobalVariables.historyBarColor);
        if(buy){
            item.realSellPrice = value - 1;
        }
        else item.realBuyPrice = value - 1;
        RandomMethods.RandomSleep(500, 1000);
        ClickInRect(PixelLocations.singleItemHistory, 0);
        RandomMethods.RandomSleep(2500, 3000);
        ClickInRect(PixelLocations.tradeCollectionBack, GlobalVariables.buySellPixelPadding);
    }
    public static void TestMargins(int windowNumber, ItemExchangeInfo item) throws AWTException, InterruptedException{
        double multiplier = 1.05;

        while(true){
            MakeBuyOffer(windowNumber, item, 1, (int)(multiplier * item.averageBuyPrice));
            RandomMethods.RandomSleep(2000, 3000);
            float progress = PixelCalculation.CalculateTradeProgress(PixelLocations.GetTradeProgressBar(windowNumber));
            if(progress == 2){
                CollectCompleteOffer(windowNumber);
                RandomMethods.RandomSleep(1500, 2000);
                CheckHistoryForMargin(item, true);
                multiplier = 1.05;
                break;
            }
            else{
                CancelAndCollect(windowNumber);

            }
            multiplier += .05f;
        }
        RandomMethods.RandomSleep(2000, 3000);
        while(true){
            int inventLocation = InventoryOperations.FindSlotOfItemInInventory(item);
            System.out.println(inventLocation + " in the command");
            MakeSaleOffer(windowNumber, inventLocation, 1, (int)(item.averageSellPrice / multiplier)); 
            RandomMethods.RandomSleep(2000, 3000);
            float progress = PixelCalculation.CalculateTradeProgress(PixelLocations.GetTradeProgressBar(windowNumber));
            if(progress == 2){
                CollectCompleteOffer(windowNumber);
                RandomMethods.RandomSleep(1500, 2000);
                CheckHistoryForMargin(item, false); 
                break;
            }
            else{
                CancelAndCollect(windowNumber);
            }
            multiplier += .05f; 
        }
        System.out.println(item.realBuyPrice + " " + item.realSellPrice);
        
        //Item ExchangeINfo needs data for real buy sell and real profit 

        //return real margin
    }
    public static void SelectItemForSale(int inventPosition) throws InterruptedException{
        ScreenArea itemLoc = PixelLocations.GetScreenAreaOfInventorySlot(inventPosition);
        ClickInRect(itemLoc, GlobalVariables.buySellPixelPadding);
    }
*/
}