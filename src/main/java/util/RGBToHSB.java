package util;

import java.awt.*;

/**
 * Created by jose.renato on 28/10/2016.
 */
public class RGBToHSB {

    public static void main(String[] args) {
         int red = 222;
        int green = 83;
        int blue = 37;
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];
        System.out.println("RGB [" + red + "," + green + "," + blue + "] converted to HSB [" + hue + "," + saturation + "," + brightness + "]" );
        int rgb = Color.HSBtoRGB(hue, saturation, brightness);
        red = (rgb>>16)&0xFF;
        green = (rgb>>8)&0xFF;
        blue = rgb&0xFF;
        System.out.println("HSB [" + hue + "," + saturation + "," + brightness + "] converted to RGB [" + red + "," + green + "," + blue + "]" );
    }

}
