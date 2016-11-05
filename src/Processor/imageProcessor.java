package Processor;

import constants.imageConfiguration;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class imageProcessor {
    public static final int bias = 128;
    public static final int stride = 1;
    public static int padd_opt = imageConfiguration._BLACK;
    public imageProcessor() {
        
    }
    
    public BufferedImage convertMatToBM(ArrayList<ArrayList<Double>> input){
        BufferedImage result = new BufferedImage(input.size(), input.get(0).size(), BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i< input.size(); i++){
            for (int j = 0; j<input.get(i).size(); j++) {
                int gray = (int)Math.round(input.get(i).get(j));
                Color c = new Color(gray, gray, gray);
                result.setRGB(i, j, c.getRGB());
            } 
        }
        return result;
    }
    public BufferedImage convertMatToBMRGB(ArrayList<ArrayList<ArrayList<Double>>> input){
        BufferedImage result = new BufferedImage(input.get(0).size(), input.get(0).get(0).size(), BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i< input.get(0).size(); i++){
                for (int j = 0; j<input.get(0).get(i).size(); j++) {
                    int red = (int)Math.round(input.get(0).get(i).get(j));
                    int green = (int)Math.round(input.get(1).get(i).get(j));
                    int blue = (int)Math.round(input.get(2).get(i).get(j));
                    Color c = new Color(red, green, blue);
                    result.setRGB(i, j, c.getRGB());
                } 
            }
        
        return result;
    }
    
    //color convolution
    public ArrayList<ArrayList<ArrayList<Double>>> ConvolveRGB(ArrayList<ArrayList<ArrayList<Double>>> input, int size, int method)
    {
        ArrayList<ArrayList<ArrayList<Double>>> result = new ArrayList<ArrayList<ArrayList<Double>>>();
	int kernel_size = size - 1;
	for (int c = 0; c < 3; c++) {
            ArrayList<ArrayList<Double>> temp_mat = new ArrayList<ArrayList<Double>>();
            for (int i = kernel_size; i < (input.get(c).size() - (this.stride - 1)); i += this.stride) { //loop vertically through image
                ArrayList<Double> temp_row = new ArrayList<Double>(); //storing temporary value of each convolved row
                for (int j = kernel_size; j < (input.get(c).get(i).size() - (this.stride - 1)); j += this.stride) { // loop horizontally through image
                    ArrayList<Double> temp_subResult = new ArrayList<Double>();
                    for (int k = (i - kernel_size); k <= (i); k++) { 
                        for (int l = (j - kernel_size); l <= (j); l++) {
                            temp_subResult.add(input.get(c).get(k).get(l));
                        }
                    }
                    double temp_value = 0.0;
                    //method here 
                    switch(method){
                        case imageConfiguration._AVERAGE:
                            temp_value = this.conv_average(temp_subResult);
                            break;
                        case imageConfiguration._MEDIAN:
                            temp_value = this.conv_median(temp_subResult);
                            break;
                        case imageConfiguration._MAX:
                            temp_value = this.conv_max(temp_subResult);
                            break;
                        case imageConfiguration._MIN:
                            temp_value = this.conv_min(temp_subResult);
                            break;
                    }
                    temp_row.add(temp_value);
                }
                temp_mat.add(temp_row);
            }
            result.add(temp_mat);
        }
        int padd_num = kernel_size;
    	return this.PaddRGB(input, result, padd_num);
    }
    
    public ArrayList<ArrayList<Double>> Convolve(ArrayList<ArrayList<Double>> input, int size, int method)
    {
        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
	
	int kernel_size = size - 1;
	for (int i = kernel_size; i < (input.size() - (this.stride - 1)); i += this.stride) { //loop vertically through image
            ArrayList<Double> temp_row = new ArrayList<Double>(); //storing temporary value of each convolved row
            for (int j = kernel_size; j < (input.get(i).size() - (this.stride - 1)); j += this.stride) { // loop horizontally through image
		ArrayList<Double> temp_subResult = new ArrayList<Double>();
                for (int k = (i - kernel_size); k <= (i); k++) { 
                    for (int l = (j - kernel_size); l <= (j); l++) {
			temp_subResult.add(input.get(k).get(l));
                    }
		}
                double temp_value = 0.0;
                //method here 
                switch(method){
                    case imageConfiguration._AVERAGE:
                        temp_value = this.conv_average(temp_subResult);
                        break;
                    case imageConfiguration._MEDIAN:
                        temp_value = this.conv_median(temp_subResult);
                        break;
                    case imageConfiguration._MAX:
                        temp_value = this.conv_max(temp_subResult);
                        break;
                    case imageConfiguration._MIN:
                        temp_value = this.conv_min(temp_subResult);
                        break;
                }
                temp_row.add(temp_value);
            }
            result.add(temp_row);
        }
        int padd_num = kernel_size;
    	return this.Padd(input, result, padd_num);
    }
    
    private double conv_average (ArrayList<Double> input) {
       double result = 0.0;
        for(int i =0; i<input.size(); i++){
            result += input.get(i);
        }
        return result / input.size();
    }
    private double conv_median (ArrayList<Double> input) {
        double result = 0.0;
        Collections.sort(input);
        int middle = (int)Math.floor(input.size() / 2);
        result = input.get(middle);
        return result;
    }
    private double conv_max (ArrayList<Double> input) {
        double result = 0.0;
        Collections.sort(input);
        result = input.get(input.size() - 1);
        return result;
    }
    private double conv_min (ArrayList<Double> input) {
        double result = 0.0;
        Collections.sort(input);
        result = input.get(0);
        return result;
    }
    
    public ArrayList<ArrayList<ArrayList<Double>>> PaddRGB(ArrayList<ArrayList<ArrayList<Double>>> original, 
                                                ArrayList<ArrayList<ArrayList<Double>>> input, int n_pad) {
        ArrayList<ArrayList<ArrayList<Double>>> result = new ArrayList<ArrayList<ArrayList<Double>>>();
        int size = n_pad + input.get(0).size();
        ArrayList<Double> zero_row = new ArrayList<Double>();
        if(this.padd_opt == imageConfiguration._BLACK || this.padd_opt == imageConfiguration._WHITE) {
            if(this.padd_opt == imageConfiguration._BLACK) {
                for (int i = 0; i<size; i++){
                    zero_row.add(0.0);
                }
            }else { //white
                for (int i = 0; i<size; i++){
                    zero_row.add(255.0);
                }
            }
            //start
            for (int c = 0; c < 3; c++) {
                ArrayList<ArrayList<Double>> temp_mat = new ArrayList<ArrayList<Double>>();
                for (int i = 0; i< size ; i++) {
                    if(i < (n_pad/2) || i >= input.get(0).size() + (n_pad / 2)) {
                        //add zero pad row on top and bottom row (condition based on n_pad value)
                        temp_mat.add(zero_row);
                    }else {
                        ArrayList<Double> temp_row = new ArrayList<Double>(); //row for storing temporary values
                        for (int j = 0; j < size; j++) { //considering image are scaled to square shape
                            if(j < (n_pad/2) || j >= input.get(c).size() + (n_pad/2)){
                                if(this.padd_opt == imageConfiguration._BLACK) {
                                   temp_row.add(0.0);
                                }else {
                                    temp_row.add(255.0);
                                }
                            }else {
                                temp_row.add(input.get(c).get(i-(n_pad/2)).get(j - (n_pad/2)));
                            }
                        }
                        temp_mat.add(temp_row);
                    }
                }
                result.add(temp_mat);
            }
        } else { //original
            for (int c = 0; c < 3; c++) {
                ArrayList<ArrayList<Double>> temp_mat = new ArrayList<ArrayList<Double>>();
                for (int i = 0; i< size ; i++) {
                    if(i < (n_pad/2) || i >= input.get(c).size() + (n_pad / 2)) {
                        temp_mat.add(original.get(c).get(i));
                    }else {
                        ArrayList<Double> temp_row = new ArrayList<Double>(); //row for storing temporary values
                        for (int j = 0; j < size; j++) { //considering image are scaled to square shape
                            if(j < (n_pad/2) || j >= input.get(c).size() + (n_pad/2)){
                                temp_row.add(original.get(c).get(i).get(j));
                            }else {
                                temp_row.add(input.get(c).get(i-(n_pad/2)).get(j - (n_pad/2)));
                            }
                        }
                        temp_mat.add(temp_row);
                    }
                }
                result.add(temp_mat);
            }
        }
        
        //end of initializing
        
        return result;
    }
    
    public ArrayList<ArrayList<Double>> Padd(ArrayList<ArrayList<Double>> original, ArrayList<ArrayList<Double>> input, int n_pad) {
        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        int size = n_pad + input.size();
        ArrayList<Double> zero_row = new ArrayList<Double>();
        if(this.padd_opt == imageConfiguration._BLACK || this.padd_opt == imageConfiguration._WHITE) {
            if(this.padd_opt == imageConfiguration._BLACK) {
                for (int i = 0; i<size; i++){
                    zero_row.add(0.0);
                }
            }else { //white
                for (int i = 0; i<size; i++){
                    zero_row.add(255.0);
                }
            }
            //start
            for (int i = 0; i< size ; i++) {
                if(i < (n_pad/2) || i >= input.size() + (n_pad / 2)) {
                    //add zero pad row on top and bottom row (condition based on n_pad value)
                    result.add(zero_row);
                }else {
                    ArrayList<Double> temp_row = new ArrayList<Double>(); //row for storing temporary values
                    for (int j = 0; j < size; j++) { //considering image are scaled to square shape
                        if(j < (n_pad/2) || j >= input.size() + (n_pad/2)){
                            if(this.padd_opt == imageConfiguration._BLACK) {
                               temp_row.add(0.0);
                            }else {
                                temp_row.add(255.0);
                            }
                        }else {
                            temp_row.add(input.get(i-(n_pad/2)).get(j - (n_pad/2)));
                        }
                    }
                    result.add(temp_row);
                }
            }
        } else { //original
            for (int i = 0; i< size ; i++) {
                if(i < (n_pad/2) || i >= input.size() + (n_pad / 2)) {
                    result.add(original.get(i));
                }else {
                    ArrayList<Double> temp_row = new ArrayList<Double>(); //row for storing temporary values
                    for (int j = 0; j < size; j++) { //considering image are scaled to square shape
                        if(j < (n_pad/2) || j >= input.size() + (n_pad/2)){
                            temp_row.add(original.get(i).get(j));
                        }else {
                            temp_row.add(input.get(i-(n_pad/2)).get(j - (n_pad/2)));
                        }
                    }
                    result.add(temp_row);
                }
            }
        }
        
        //end of initializing
        
        return result;
    }
    public static ArrayList<ArrayList<ArrayList<Double>>> getPixelArrayListRGB(BufferedImage input)
    {
	ArrayList<ArrayList<ArrayList<Double>>> result = new ArrayList<ArrayList<ArrayList<Double>>>();
	for (int c = 0; c < 3; c++) {
            ArrayList<ArrayList<Double>> sub_res = new ArrayList<ArrayList<Double>>();
            for(int i = 0; i< input.getHeight(); i++) {
                ArrayList<Double> temp_row = new ArrayList<Double>();
                for (int j = 0; j < input.getWidth(); j++) {
                    Color col = new Color(input.getRGB(i, j));
                    switch(c) {
                        case 0: 
                            temp_row.add((double)col.getRed());
                            break;
                        case 1:
                            temp_row.add((double)col.getGreen());
                            break;
                        case 2:
                            temp_row.add((double)col.getBlue());
                            break;
                    }
                }
                sub_res.add(temp_row);
            }
            result.add(sub_res);
        }
	return result;
    }
    public static ArrayList<ArrayList<Double>> getPixelArrayList(BufferedImage input)
    {
	ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
	for(int i = 0; i< input.getHeight(); i++) {
            ArrayList<Double> temp_row = new ArrayList<Double>();
            for (int j = 0; j < input.getWidth(); j++) {
                Color c = new Color(input.getRGB(i, j));
                int red = (int)(c.getRed() * 0.299);
                int green = (int)(c.getGreen() * 0.587);
                int blue = (int)(c.getBlue() *0.114);
                int gray = red + green + blue;
                temp_row.add((double)gray); //retrieve gray scale pixel value of the image
            }
            result.add(temp_row);
	}
	return result;
    }
    public static BufferedImage scale(BufferedImage src, int w, int h)
    {
	  BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	  int x, y;
	  int ww = src.getWidth();
	  int hh = src.getHeight();
	  for (x = 0; x < w; x++) {
	    for (y = 0; y < h; y++) {
	      int col = src.getRGB(x * ww / w, y * hh / h);
	      img.setRGB(x, y, col);
	    }
	  }
	  return img;
    }
}
