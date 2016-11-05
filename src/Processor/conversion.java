package Processor;

import constants.imageConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
*Tool for conversion
*like jlabel to image, bufferedImage to jlabel etc.
*/
public class conversion {
    public conversion() {
        
    }
    public JLabel conv_BufferedImageToJlabelImage(BufferedImage input) {
        ImageIcon ic = new ImageIcon(input);
        JLabel result = new JLabel(ic);
        return result;
    }
    public ImageIcon conv_BufferedImageToImageIcon(BufferedImage input) {
        ImageIcon ic = new ImageIcon(input);
        return ic;
    }
    public JLabel conv_StringPathToJLabelImage(String input) throws IOException {
        ImageIcon imageIcon = this.conv_StringPathToImageIcon(input);
        JLabel result = new JLabel(imageIcon);
        return result;
    }
    public ImageIcon conv_StringPathToImageIcon(String input) throws IOException {
        BufferedImage image = this.conv_StringPathToBufferedImage(input);
        ImageIcon result = new ImageIcon(image);
        return result;
    }
    public BufferedImage conv_StringPathToBufferedImage(String input) throws IOException {
        File path = new File(input);
        imageProcessor ic = new imageProcessor();
        BufferedImage image = ImageIO.read(path);
        
        return ic.scale(image, imageConfiguration.IMAGE_WIDTH, imageConfiguration.IMAGE_HEIGHT);
    }
}
