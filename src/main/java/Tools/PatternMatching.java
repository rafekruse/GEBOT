package main.java.Tools;

import main.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;


public class PatternMatching {

    private PatternMatching() {
    }

    public static String TextFromBufferedImage(Rectangle rect) {
        try {
            BufferedImage image = new Robot().createScreenCapture(rect);
            Color[] colors = new Color[]{Constants.chatBlue, Constants.chatBlack, Constants.chatRed};
            int[][] imageIntMap = IntMapFromImage(image, colors);
            int pixelsCovered = 0;
            String output = "";
            int pixelsSinceMatch = 0;
            while (pixelsCovered < imageIntMap[0].length) {
                for (int i = 0; i < Constants.characterPatterns.length; i++) {
                    if (MultiDemensionalArrayEquals(GetArrayPortion(imageIntMap, pixelsCovered, 0, Constants.characterPatterns[i][0].length,
                            14), Constants.characterPatterns[i])) {
                        output += Constants.characterCodes.charAt(i);
                        pixelsCovered += Constants.characterPatterns[i][0].length;
                        pixelsSinceMatch = 0;
                        //System.out.println();
                    }
                }
                pixelsSinceMatch++;
                pixelsCovered++;
                if(pixelsSinceMatch > 2){
                    output += " ";
                    pixelsSinceMatch = 0;
                }
            }
            return output.trim();
        } catch (AWTException exception) {
            System.out.println("Failed to get Number from Screen Area : " + rect);
            exception.printStackTrace();
            return "";
        }
    }

    private static int[][] IntMapFromImage(BufferedImage image, Color[] searchColors) {
        int[][] output = new int[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color pixelColor = new Color(image.getRGB(j, i));
                output[i][j] = 0;
                for (Color color : searchColors) {
                    if (pixelColor.equals(color)) {
                        output[i][j] = 1;
                    }
                }

            }
        }
        return output;
    }

    private static int[][] GetArrayPortion(int[][] largeArray, int xStart, int yStart, int width, int height) {
        int[][] output = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (largeArray[0].length - 1 < xStart + j) {
                    output[i][j] = 0;
                } else {
                    output[i][j] = largeArray[yStart + i][xStart + j];
                }
            }
        }

        return output;
    }

    private static boolean MultiDemensionalArrayEquals(int[][] portion, int[][] pattern) {
        for (int i = 0; i < portion.length; i++) {
            for (int j = 0; j < portion[0].length; j++) {
                if (i >= pattern.length && portion[i][j] != 0) {
                    return false;
                } else if (i >= pattern.length) {
                    continue;
                }
                if (portion[i][j] != pattern[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}