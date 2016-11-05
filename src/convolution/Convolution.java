package convolution;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Processor.imageProcessor;
import views.main_view;

public class Convolution {

    public static void main(String[] args) throws IOException
    {
        JFrame jf = new JFrame();
        main_view mv = new main_view();
        jf.add(mv);
        jf.setBounds(0, 0, 1200, 400);
        jf.setVisible(true);
//        String test_path = "C:/Users/Andy/Thesis/Yunita/IMG_20160923_181729.jpg";
//        String test_path = "C:/Users/Andy/Desktop/Portfolio resources/view1.jpg";
//        File input = new File(test_path);
//        BufferedImage image = ImageIO.read(input);
//        imageProcessor ip = new imageProcessor();
//        image = ip.scale(image, 200, 200);
//        BufferedImage image_blur = new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_GRAY);
//        BufferedImage image_sharpen = new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_GRAY);
//        BufferedImage image_pre_h = new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_GRAY);
//        BufferedImage image_pre_v = new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_GRAY);
//        //matrix initialization
//        ArrayList<ArrayList<Double>> display_image_prewit_h = ip.getPixelArrayList(image);
//        ArrayList<ArrayList<Double>> display_image_prewit_v = ip.getPixelArrayList(image);
//        ArrayList<ArrayList<Double>> display_image_blur = ip.getPixelArrayList(image);
//        ArrayList<ArrayList<Double>> display_image_sharpen = ip.getPixelArrayList(image);
//        //processing
//        display_image_prewit_h = ip.Convolve(display_image_prewit_h, fixedFilters.EDGE_LINEAR_HORIZONTAL_PREWITT);
//        display_image_prewit_v = ip.Convolve(display_image_prewit_v, fixedFilters.EDGE_LINEAR_VERTICAL_PREWITT);
//        display_image_blur = ip.Convolve(display_image_blur, fixedFilters.BLUR_3_x_3);
//        display_image_sharpen = ip.Convolve(display_image_sharpen, fixedFilters.SHARPEN);
//        
//        image_blur = ip.convertMatToBM(display_image_blur);
//        image_pre_h = ip.convertMatToBM(display_image_prewit_h);
//        image_pre_v= ip.convertMatToBM(display_image_prewit_v);
//        image_sharpen = ip.convertMatToBM(display_image_sharpen);
//        
//        JFrame frame = new JFrame();
//	frame.getContentPane().setLayout(new FlowLayout());
//	frame.getContentPane().add(new JLabel(new ImageIcon(image_blur)));
//        frame.getContentPane().add(new JLabel(new ImageIcon(image_pre_h)));
//        frame.getContentPane().add(new JLabel(new ImageIcon(image_pre_v)));
//        frame.getContentPane().add(new JLabel(new ImageIcon(image_sharpen)));
//	frame.pack();
//	frame.setVisible(true);
//        
    }
}
