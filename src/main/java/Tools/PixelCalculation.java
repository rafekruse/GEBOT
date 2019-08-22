package main.java.Tools;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.Constants;
import main.java.Display.DrawableRect;

public class PixelCalculation {

    public static float CalculateTradeProgress(Rectangle progressBarArea) {
        try {
            int progressCount = 0;
            BufferedImage progressBar = new Robot().createScreenCapture(progressBarArea);
            for (int i = 0; i <= 104; i++) {
                Color pixel = new Color(progressBar.getRGB(i, progressBar.getHeight() / 2));
                if (pixel.getRed() != 0 && pixel.getBlue() == 0 && pixel.getGreen() == 0) {
                    return -1;
                } else if (pixel.getGreen() != 0 && pixel.getBlue() == 0 && pixel.getRed() == 0) {
                    return 2;
                } else if (pixel.getRed() > pixel.getBlue() * 3 && pixel.getGreen() > pixel.getBlue() * 2) {
                    progressCount++;
                } else {
                    break;
                }
            }
            return (float) progressCount / 104;
        } catch (AWTException exception) {
            System.out.println("Failed to Calculate Trade Progress.");
            exception.printStackTrace();
            return -1;
        }
    }

    public static Rectangle RelateToOtherRect(Rectangle pivot, int xOffset, int yOffset){
        return new Rectangle(pivot.x + xOffset, pivot.y + yOffset, pivot.width, pivot.height);
    }
    public static Rectangle RelateToOtherRect(Rectangle pivot, Rectangle relation){
        return new Rectangle(pivot.x + relation.x, pivot.y + relation.y, relation.width, relation.height);
    }

    public static Rectangle GetScreenAreaOfRepeatingFormat(int row, int col, Rectangle rect, int spacingX, int spacingY) {
        int x = rect.x + col * (spacingX + rect.width);
        int y = rect.y + row * (spacingY + rect.height);
        return new Rectangle(x, y, rect.width, rect.height);
    }



    public static Rectangle RelateToWindowGlobal(Rectangle rect) {
        return new Rectangle(Constants.programOriginPosition.x + (Constants.programOriginPosition.width / 2) + rect.x, Constants.programOriginPosition.y + rect.y, rect.width, rect.height);
    }


    public static Rectangle LocalToGlobalRectangle(Rectangle global, Rectangle relative) {
        return new Rectangle(global.x + relative.x, global.y + relative.y, relative.width, relative.height);
    }

    public static BufferedImage CropBuffered(BufferedImage image, Rectangle rect) {
        return image.getSubimage(rect.x, rect.y, rect.width, rect.height);
    }
}