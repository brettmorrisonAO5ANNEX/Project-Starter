package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Arrays;

import static java.lang.Math.*;

//represents a filter with name filterName that can be applied to an image
public class Filter implements Writable {
    private String filterName;

    //REQUIRES: filterType can only be one of: "negative", "mirror", "pixelate"
    //EFFECTS: instantiates a filter object
    public Filter(String filterType) {
        this.filterName = filterType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", filterName);
        return json;
    }

    //MODIFIES: img
    //EFFECTS: negates each pixel's RGB values in img
    public void negative(Image img) {
        for (int r = 0; r < img.getPixelArray().length; r++) {
            for (int c = 0; c < img.getPixelArray()[0].length; c++) {
                img.getPixelArray()[r][c] = 255 - img.getPixelArray()[r][c];
            }
        }
    }

    //REQUIRES: first mirror call MUST be with row = 0
    //MODIFIES: img
    //EFFECTS: creates mirror of image
    public void mirror(Image img) {
        int[][] mirroredArray = new int[img.getImageWidth() * img.getImageHeight()][3];
        for (int r = 0; r < img.getImageHeight(); r++) {
            int rowStart = r * img.getImageWidth();
            int rowEnd = img.getImageWidth() * (r + 1) - 1;
            for (int i = 0; i < img.getImageWidth(); i++) {
                mirroredArray[i + rowStart] = img.getPixelArray()[rowEnd - i];
            }
        }
        img.pixelArray = mirroredArray;
    }

    //TODO: update to account for direction of increment change
    //REQUIRES: input rgbComponent must be one of "red" "green" or "blue"
    //MODIFIES: img
    //EFFECTS: creates image with chosen rgb component decreasing in intensity with each row in pix array
    public void colorGradient(String rgbComponent, Image img) {
        int rgbIndex = getRGBindex(rgbComponent);
        int incrementAmount = round((255 / img.getImageHeight()));

        for (int c = 0; c < img.getImageWidth(); c++) {
            for (int r = 0; r < img.getImageHeight(); r++) {
                int pixIndex = c + (r * img.getImageWidth());
                int currCompIntensity = (r + 1) * incrementAmount;

                //if currCompIntensity exceeds 255 goes in reverse, if reaches 0 begins performing forward again
                if (currCompIntensity > 255) {
                    //decrement by incrementAmount starting from incrementAmount * img.getHeight()
                } else if (currCompIntensity < 0) {
                    //increment by incrementAmount starting from incrementAmount
                }
                img.getPixelArray()[pixIndex][rgbIndex] = (currCompIntensity);
            }
        }
    }

    //EFFECTS: returns position in pixel int[] corresponding to chosen color component
    private int getRGBindex(String rgbComponent) {
        if (rgbComponent.equals("red")) {
            return 0;
        } else if (rgbComponent.equals("green")) {
            return 1;
        } else {
            return 2;
        }
    }

    //MODIFIES: img
    //EFFECTS: creates standard pixelated image from img
    public void pixelate(Image img) {
        //stub
    }

    




    public String getFilterName() {
        return this.filterName;
    }
}
