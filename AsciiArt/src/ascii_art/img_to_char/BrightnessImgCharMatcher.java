package ascii_art.img_to_char;

import image.Image;

import java.awt.*;

public class BrightnessImgCharMatcher {

    public static final int PIXELS = 16;
    public static final int BRIGHTNESS_RANGE = 255;
    private static String fontName;
    private final Image img;

    public BrightnessImgCharMatcher(Image img, String font) {
        this.img = img;
        fontName = font;
    }

    private static double[] BrightnessValue(char[] characters)
    {
        boolean[][] renderer;
        int sum;
        double[] result = new double[characters.length];
        for (int i = 0; i < result.length; i++)
        {
            sum = 0;
            renderer = CharRenderer.getImg(characters[i], PIXELS, fontName);
            for (boolean[] row : renderer)
            {
                for (boolean col : row)
                {
                    if (col)
                        sum += 1;
                }
            }
            result[i] = (double)sum / ( PIXELS * PIXELS);
        }
        return result;
    }

    private static double[] linearStretch(double[] arr)
    {
        double[] result = new double[arr.length];
        double min = 1d;
        double max = 0d;
        for (double v : arr) {
            if (v <= min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }
        if (max == min)
            return arr;
        for (int i = 0; i < arr.length; i++)
        {
            result[i] = (arr[i] - min) / (max - min);
        }
        return result;
    }

    private static double averageBrightness(Image img)
    {
        int numOfPixels = 0;
        int sum = 0;
        for (Color pixel : img.pixels()) {
            sum +=
                    pixel.getRed() * 0.2126
                    + pixel.getGreen() * 0.7152
                    + pixel.getBlue() * 0.0722;
            numOfPixels += 1;
        }
        return ((double) sum / numOfPixels) / BRIGHTNESS_RANGE;
    }

    private char[][] convertImageToAscii(double[] brightnessChar, char[] chars,
                     int numCharsInRow)
    {
        int pixels = img.getWidth() / numCharsInRow;
        double[] brightnessSubImages = new double[(img.getHeight()/pixels)
                 * (img.getWidth()/pixels)];
        int i = 0;
        for(Image subImage : img.squareSubImagesOfSize(pixels)) {
            brightnessSubImages[i] = averageBrightness(subImage);
            i += 1;
        }
        char[][] asciiArt =
                new char[img.getHeight()/pixels][img.getWidth()/pixels];
        i = 0;
        for (int row = 0; row < img.getHeight()/pixels; row++) {
            for (int col = 0; col < img.getWidth() / pixels; col++) {
                asciiArt[row][col] = chars[findTheClosestValue(brightnessChar,
                        brightnessSubImages[i])];
                i++;
            }
        }
        return asciiArt;

    }

    private static int findTheClosestValue(double[] brightnessChar,
                                           double brightnessSubImage) {
         double brightnessDiff = 1;
         int ind = 0;
         for (int i = 0; i < brightnessChar.length; i++)
         {
             if (Math.abs(brightnessChar[i] - brightnessSubImage) < brightnessDiff)
             {
                 brightnessDiff = Math.abs(brightnessChar[i] - brightnessSubImage);
                 ind = i;
             }
         }
         return ind;
    }

    public char[][] chooseChars(int numCharsInRow, Character[] charSet)
    {
        char[] chars = new char[charSet.length];
        for (int i = 0; i < charSet.length; i++) {
            chars[i] = charSet[i].charValue();
        }
        double[] brightnessChars =
                BrightnessImgCharMatcher.BrightnessValue(chars);
        brightnessChars = BrightnessImgCharMatcher.linearStretch(brightnessChars);
        return convertImageToAscii(brightnessChars, chars, numCharsInRow);
    }

}
