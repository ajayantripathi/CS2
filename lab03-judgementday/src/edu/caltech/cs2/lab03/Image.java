package edu.caltech.cs2.lab03;

import edu.caltech.cs2.libraries.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Image {
    private Pixel[][] pixels;

    public Image(File imageFile) throws IOException {
        BufferedImage img = ImageIO.read(imageFile);
        this.pixels = new Pixel[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                this.pixels[i][j] = Pixel.fromInt(img.getRGB(i, j));
            }
        }
    }

    private Image(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Image transpose() {
        Pixel[][] pixels2 = new Pixel[pixels[0].length][pixels.length];
        for (int i = 0; i < this.pixels.length; i++) {
            for (int j = 0; j < this.pixels[0].length; j++) {
                pixels2[j][i] = this.pixels[i][j];
            }
        }
        return new Image(pixels2);
    }

    public String decodeText() {
        ArrayList<Integer> bitArray = new ArrayList<>();
        for (int i = 0; i < this.pixels.length; i++) {
            for (int j = 0; j < this.pixels[0].length; j++) {
                bitArray.add(this.pixels[i][j].getLowestBitOfR()) ;
            }
        }
        String chars = "";
        int value = 0;
        for (int k = 0; k < bitArray.size(); k++) {
            value += bitArray.get(k) * Math.pow(2, k % 8);
            if (k % 8 == 7) {
                char c = ((char) value);
                if (value != 0) {
                    chars += c;
                }
                value = 0;
            }
        }
        return chars;
    }

    public Image hideText(String text) {
        int rowLength = this.pixels[0].length;
        Pixel[][] pixels3 = new Pixel[this.pixels.length][this.pixels[0].length];
        int position = 0;
        for (int k = 0; k < this.pixels.length; k++) {
            for (int m = 0; m < rowLength; m++) {
                pixels3[k][m] = this.pixels[k][m];
            }
        }
        String nums2 = "";
        for (int i = 0; i < text.length(); i++) {
            int num = text.charAt(i);
            String bin = Integer.toBinaryString(num);
            while (bin.length() < 8) {
                bin = "0" + bin;
            }
            StringBuilder build = new StringBuilder();
            build.append(bin);
            build = build.reverse();
            nums2 += build;
        }
        for (int i = 0; i < pixels3.length; i++){
            for (int j = 0; j < pixels3[0].length; j++){
                int inx = i * pixels3[0].length + j;
                if (inx >= nums2.length()){
                    pixels3[i][j] = pixels3[i][j].fixLowestBitOfR(0);
                } else {
                    int fix = Integer.parseInt("" + nums2.charAt(inx));
                    pixels3[i][j] = pixels3[i][j].fixLowestBitOfR(fix);
                }
            }
        }
        return new Image(pixels3);
    }

    public BufferedImage toBufferedImage() {
        BufferedImage b = new BufferedImage(this.pixels.length, this.pixels[0].length, BufferedImage.TYPE_4BYTE_ABGR);
        for (int i = 0; i < this.pixels.length; i++) {
            for (int j = 0; j < this.pixels[0].length; j++) {
                b.setRGB(i, j, this.pixels[i][j].toInt());
            }
        }
        return b;
    }

    public void save(String filename) {
        File out = new File(filename);
        try {
            ImageIO.write(this.toBufferedImage(), filename.substring(filename.lastIndexOf(".") + 1, filename.length()), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
