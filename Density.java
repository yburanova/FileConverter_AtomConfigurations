package kirklandfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Density {
    
    int i=0, j=0;
    double mask=8.1;
    private int N=2260352;
    private double[][] massive;
    private double[] density;
    private String text;
    double max = 9.6;
    double min = 1.5;
    double x;
    private double min1,max1;
    double V=1;
    
    File fileInput(){
        File fileInp = new File("Al_boundary_RealGB.ini"); //Исходный файл
        return fileInp;
    }
    
    File fileOutputDensity(){
        File fileOut = new File("Al_boundary_RealGB_Density.txt");//Конечный файл
        return fileOut;
    }
    
    void Massive(){
        try{
        Scanner scan = new Scanner(fileInput());
        //N = Integer.valueOf(scan.nextLine());
        massive = new double[6][N];
        Map<Double, Integer> map = new TreeMap<Double, Integer>();
        
        scan.nextLine();
        scan.nextLine();
        for (int k = 0; k < N; k++)
            {//Считываем файл, записываем в массив
                text = scan.nextLine();
                String[] str =text.split(" ");
                    if (map.containsKey(Double.valueOf(str[2])))
                        map.put(Double.valueOf(str[2]), map.get(Double.valueOf(str[2])) + 1);
                    else
                        map.put(Double.valueOf(str[2]), 1);
                
                System.out.println("Вы ввели текст\n"+text);
                j++;     
            }   
            scan.close();
               
        for(Map.Entry<Double, Integer> entry: map.entrySet())
        {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        
    }catch (IOException error) {
            System.out.println("Низзя!");
        }
    }
    
    void Sort(){
        double[] temp = new double[6];
        for (j=0; j<N; j++){
            for (i=0; i<N-j-1; i++){
                if (massive[2][i]>massive[2][i+1]) {
                    
                    temp[0]=massive[0][i];
                    massive[0][i]=massive[0][i+1];
                    massive[0][i+1]=temp[0];
                    
                    temp[1]=massive[1][i];
                    massive[1][i]=massive[1][i+1];
                    massive[1][i+1]=temp[1];
                    
                    temp[2]=massive[2][i];
                    massive[2][i]=massive[2][i+1];
                    massive[2][i+1]=temp[2];
                    
                    temp[3]=massive[3][i];
                    massive[3][i]=massive[3][i+1];
                    massive[3][i+1]=temp[3];
                    
                }
            }
        }
    }
    
    void plotDensity(){
        density = new double[512];
        max = 123.1026;
        double step = max / 512;
        double mask = 8.1;
        j = 0;
        
        for (int i = 0; i < density.length; i++)
        {
            if ((massive[2][j] > (i*step - mask/2))&&(massive[2][j] < (i*step + mask/2)))
            {
                density[i] ++;
                if (massive[2][j] > (i*step + mask/2))
                    break;
            }
            j++;
            System.out.println(i);
        }
        
    }
    
    
    public static void main(String[] args) {
      Density plot = new Density();
      plot.Massive();
      //plot.Sort();
      
          
      /*for (int i=0; i<plot.N; i++){
             System.out.println(plot.massive[0][i]+" "+plot.massive[1][i]+" "+plot.massive[2][i]+" "+plot.massive[3][i]);     
            }*/
      //plot.plotDensity();
    }
      
}
