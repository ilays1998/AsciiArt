package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_art.img_to_char.CharRenderer;
import ascii_output.AsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Driver {
    public static void main(String[] args){
//        boolean[][] cs = CharRenderer.getImg('c', 16, "Ariel");
//        CharRenderer.printBoolArr(cs);

//        double[] result =
//                BrightnessImgCharMatcher.BrightnessValue(new char[]{'a', 'b', 'd', 'c'});
//        System.out.println(Arrays.toString(result));

//        double[] ch =
//                BrightnessImgCharMatcher.BrightnessValue(new char[]{'m', 'o'});
//        ch = BrightnessImgCharMatcher.linearStretch(ch);
//        Image image = Image.fromFile(".idea/board.jpeg");

//        System.out.println(BrightnessImgCharMatcher.averageBrightness(image));
//        BrightnessImgCharMatcher brightnessImgCharMatcher =
//                new BrightnessImgCharMatcher(image, "Ariel");
//        char[][] result = brightnessImgCharMatcher.convertImageToAscii(
//                ch, new char[]{'m', 'o'}, 2);
//        for (char[] arr : result) {
//            for (char c : arr) {
//                System.out.print(c);
//            }
//            System.out.println();
//        }
//        Image img = Image.fromFile(".idea/board.jpeg");
//        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(
//                img, "Ariel");
//                var chars = charMatcher.chooseChars(
//                        2, new Character[]{'m', 'o'});
//        System.out.println(Arrays.deepToString(chars));

        Character[] charSet = new Character[126 -32 +1];
        int j = 0;
        for (int i = 32; i <= 126; i++) {
            charSet[j] = (char)i;
            j++;
        }
        Image img = Image.fromFile("man.png");
        BrightnessImgCharMatcher charMatcher =
                new BrightnessImgCharMatcher(img, "Ariel");
        AsciiOutput asciiOutput =
                new HtmlAsciiOutput("output.html", "Ariel");
        char[][] chars = charMatcher.chooseChars(64, charSet);
        asciiOutput.output(chars);
    }
}