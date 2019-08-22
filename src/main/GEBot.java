package main;

import com.sun.jna.platform.win32.WinDef;
import main.java.Display.DrawImage;
import main.java.Display.DrawableImage;
import main.java.Display.Visualizer;
import main.java.Input.Inputer;
import main.java.Tools.PatternMatching;
import main.java.Tools.PixelCalculation;
import main.java.Tools.WindowPositionUpdater;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GEBot {
    public static void main(String[] args) throws AWTException {
        try {
            StartWindowPositionUpdate();
            InitializeConstants();

            Thread.sleep(2000);

         //   GEInterface.getInstance().Initialize();
         //   Visualizer.getInstance().Initialize();
          //  Inventory.getInstance().Initialize();

            Thread.sleep(1000);
            GEInterface.getInstance().Initialize();
            Visualizer.getInstance().Initialize();
            Inputer.getInstance().Initialize(250, 250, 2);
            Inventory.getInstance().Initialize();

            Rectangle inventorySlotRect = PixelCalculation.RelateToWindowGlobal(Constants.firstChatLineRect);
            BufferedImage image2 = new Robot().createScreenCapture(inventorySlotRect);


            DrawableImage di = new DrawableImage(image2, 250, 400);
            DrawImage d = new DrawImage(di);
            Visualizer.getInstance().AddImageToFrame(d);


            //System.out.println("Text "  + PatternMatching.TextFromBufferedImage(inventorySlotRect) + "|");


            //Interface, Visualizer, Data, Invent

            //Create Interface
            //Create Visualizer
            //Search Inventory, populate items
            //Search Items


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static void InitializeConstants() {
        try {

            WinDef.HWND tes = new WinDef.HWND();



            Constants.coinsImage = ImageIO.read(new File("src/main/resources/coinsRef.png"));
            ReadInCharacterPatterns();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static void ReadInCharacterPatterns(){
        try{

            List<int[][]> characterList = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(Constants.charPatternFile));
            String temp = "";
            List<List<Integer>> currentCharacter = new ArrayList<>();
            while((temp = br.readLine()) != null){

                String[] stringArr = temp.split(",");
                List<Integer> intRow = new ArrayList<>();
                for(String value : stringArr){
                    intRow.add(Integer.parseInt(value));
                }
                if(intRow.get(0) == -1){

                    for (int i = intRow.size() - 1; i >= 0; i--) {
                        boolean allZeros= true;
                        for (int j = 0; j < currentCharacter.size(); j++) {
                            if(currentCharacter.get(j).get(i) != 0){
                                allZeros = false;
                            }
                        }
                        if(allZeros){
                            for (List<Integer> list: currentCharacter
                                 ) {
                                if(list.size() > 2){
                                list.remove(list.size() - 1);
                            }}
                        }
                    }
                    Integer[][] converted = currentCharacter.stream().map(u -> u.toArray(new Integer[0])).toArray(Integer[][]::new);
                    int[][] converted2 = new int[converted.length][converted[0].length];
                    for (int i = 0; i < converted.length; i++) {
                        for (int j = 0; j < converted[0].length; j++) {
                            converted2[i][j] = converted[i][j].intValue();
                        }
                    }
                    characterList.add(converted2);
                    currentCharacter = new ArrayList<>();
                }
                else{
                    currentCharacter.add(intRow);
                }

            }

            Constants.characterPatterns = characterList.toArray(new int[0][0][0]);
            for (int i = 0; i < Constants.characterPatterns.length; i++) {
                for (int j = 0; j < Constants.characterPatterns[i].length; j++) {
                    for (int k = 0; k < Constants.characterPatterns[i][j].length; k++) {
                        System.out.print(Constants.characterPatterns[i][j][k]);
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }



    private static void StartWindowPositionUpdate() {
        Runnable runnable = new WindowPositionUpdater(Constants.windowName);
        Thread thread = new Thread(runnable);
        thread.start();
    }
}

/*
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;
            robot.mouseMove(w, 500);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseMove(x,y);
            Thread.sleep(100);

        }

            JFrame the = new JFrame("the Test");
            the.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            the.setAlwaysOnTop(true);
            the.setSize(500, 500);
            the.setVisible(true);
            the.setLocation(200,200);
            JButton button = new JButton("Click me");
            the.add(button);

            Thread.sleep(1000);





            BufferedImage tested = GDI32Util.getScreenshot(test.foundwindow);
            DrawableImage di = new DrawableImage(tested, 250, 400);
            DrawImage d = new DrawImage(di);
                JFrame the = new JFrame();
                the.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                the.setExtendedState(JFrame.MAXIMIZED_BOTH);
                the.setUndecorated(true);
                the.setAlwaysOnTop(true);
                the.setSize(500, 50);
                the.setVisible(true);the.add(d);
            the.revalidate();
            the.repaint();



            Thread.sleep(1000);
            GEInterface.getInstance().Initialize();
            Visualizer.getInstance().Initialize();
            ExchangeDataPullUtil.getInstance().Initialize();

            Rectangle inventorySlotRect = new Rectangle(Constants.programOriginPosition.x +20, Constants.programOriginPosition.y + 500, 475, 14);
            BufferedImage image2 = new Robot().createScreenCapture(inventorySlotRect);


            DrawableImage di = new DrawableImage(image2, 250, 400);
            DrawImage d = new DrawImage(di);
            Visualizer.getInstance().AddImageToFrame(d);


            System.out.println("Text "  + PatternMatching.TextFromBufferedImage(inventorySlotRect));




        Inventory.getInstance().Initialize();


                Trader trader = new Trader(50000, 400, 800, 3);
                trader.StartBot();


                Rectangle temp2 = PixelCalculation.GetScreenAreaOfRepeatingFormat(0, 0, Constants.inventorySlotOne, Constants.inventoryHoriTilingGap, Constants.inventoryVertTilingGap);
                BufferedImage image2 = new Robot().createScreenCapture(PixelCalculation.RelateToWindowGlobal(temp2));




                Rectangle inventorySlotRect = PixelCalculation.GetScreenAreaOfRepeatingFormat(2, 0, Constants.inventorySlotOne, Constants.inventoryHoriTilingGap, Constants.inventoryVertTilingGap);

                BufferedImage inventorySlotImage = new Robot().createScreenCapture(PixelCalculation.RelateToWindowGlobal(inventorySlotRect));
                BufferedImage inventorySlotCropped = inventorySlotImage.getSubimage(Constants.cropItemSpriteRect.x, Constants.cropItemSpriteRect.y, Constants.cropItemSpriteRect.width, Constants.cropItemSpriteRect.height);

                DrawableImage di = new DrawableImage(inventorySlotCropped, 500, 500);
                DrawImage d = new DrawImage(di);
                Visualizer.getInstance().AddImageToFrame(d);

                for (ItemExchangeInfo item : ExchangeDataPullUtil.getInstance().allItems.values()) {

                if (item.getSprite() != null && item.getId() == 1387) {
                BufferedImage croppedItemImage = item.getSprite().getSubimage(Constants.cropItemSpriteRect.x, Constants.cropItemSpriteRect.y, Constants.cropItemSpriteRect.width, Constants.cropItemSpriteRect.height);
                DrawableImage di2 = new DrawableImage(croppedItemImage, 400, 400);
                DrawImage d2 = new DrawImage(di2);
                Visualizer.getInstance().AddImageToFrame(d2);
                if (PixelCalculation.bufferedImageEqualOutline(croppedItemImage, inventorySlotCropped)) {
                System.out.println("Worked");
                }
                }
                }




                ExchangeDataPullUtil.getInstance().Initialize();
                System.out.println("GEInterface Initialized");

                ExchangeDataPullUtil.getInstance().CalculateOptimalItems(10000000,8,200000);
                System.out.println("GEInterface Initialized");

                TradingItem[] array = ExchangeDataPullUtil.getInstance().optimalItems.toArray(new TradingItem[0]);

                System.out.println("test " + array.length);
                for (int i = 0; i <array.length; i++) {
        System.out.println(array[i].toString());
        }


        for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 7; j++) {
        Rectangle temp2 = PixelCalculation.GetScreenAreaOfRepeatingFormat(j, i, Constants.inventorySlotOne, Constants.inventoryHoriTilingGap, Constants.inventoryVertTilingGap);

        BufferedImage image2 = new Robot().createScreenCapture(PixelCalculation.LocalToGlobalRectangle(PixelCalculation.RelateToWindowGlobal(temp2), Constants.coinRelativeToSlot));

        if(HelperFunctions.bufferedImagesEqual(Constants.coinsImage, image2)){
        System.out.println("Found in Slot : (" +i + "," + j + ")");
        }
        }
        }







        BufferedImage readin = ImageIO.read(new File("src/main/resources/617.png")).getSubimage(0,9,31,22);
        System.out.println(readin.getWidth() + " " + readin.getHeight() + " | " + image2.getWidth() + " " + image2.getHeight());
        System.out.println(HelperFunctions.bufferedImageWithAlphaEqual(readin, image2.getSubimage(0,9,31,22)));
        DrawableImage di = new DrawableImage(image2, 500, 500);
        DrawImage d = new DrawImage(di);
        Visualizer.getInstance().AddImageToFrame(d);


        //     JFrame frame = CreateFrame(1000, 1000);
        //   frame.addKeyListener(new MKeyListener());

        //       frame.setVisible(true);
        //     frame.add(d);




        try {
        DataParser createData = new DataParser("https://rsbuddy.com/exchange/summary.json", 10000000);
        createData.assignTradabilities(4, 4000000, 50000);
        createData.sortItems();
        createData.print();



        System.out.println();
        System.out.println("Finished");
        } catch (Exception E) {
        System.out.println("error was caught" + E);
        E.printStackTrace();
        }*/