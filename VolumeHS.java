package kirklandfile;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class VolumeHS {
    private int i,j; 
    private int N;
    private double[][] massive;
    private String text;
    private double[] min,max;
    private double V, Vx, Vfinal, boxVolume;
    private final double R = 1.43;
    private double xmax=80;
    private double xmin=30;
    private double ymax=4.1;
    private double ymin=-4.1;
    private double zmax=4.05;
    private double zmin=-4.05;
    private int M;//количество атомов в участке
    
    File fileInput(){
        File fileInp = new File("(081)_final.xyz"); //Исходный файл
        return fileInp;
    }
    
    void Massive(){
        try{
        Scanner scan = new Scanner(fileInput());
        N = Integer.valueOf(scan.nextLine());
        massive = new double[4][N];
        scan.nextLine();
        while (scan.hasNextLine()){//Считываем файл, записываем в массив
                text = scan.nextLine();
                String[] str =text.split(" ");
                for (i=0; i<4; i++){
                massive[i][j] = Double.valueOf(str[i]);   
            }
                System.out.println("Вы ввели текст\n"+text);
                j++;
                      
            }   
            scan.close();
        
    }catch (IOException error) {
            System.out.println("Низзя!");
        }
    }
    
    double sphereLayer(double r, double h){
        Vx = (Math.PI*h/6)*(3*r*r+h*h);
        return Vx;
    }
    
    double boxVolume(){
        boxVolume = (xmax-xmin)*(ymax-ymin)*(zmax-zmin);
        return boxVolume;
    }
    
    void volume(){
        boxVolume();
        for (i=0;i<N;i++){
          V = 4*(Math.PI)*R*R*R/3;  
          if (((massive[1][i]-R)<=xmax)&&((massive[1][i]+R)>=xmin)&&((massive[2][i]-R)<=ymax)&&((massive[2][i]+R)>=ymin)&&((massive[3][i]-R)<=zmax)&&((massive[3][i]+R)>=zmin)){
              if ((massive[1][i]<=xmax)&&((massive[1][i]+R)>=xmax)) {V-=sphereLayer(Math.sqrt(R*R-(xmax-massive[1][i])*(xmax-massive[1][i])),(massive[1][i]+R-xmax));}
              if ((massive[1][i]>=xmin)&&((massive[1][i]-R)<=xmin)) {V-=sphereLayer(Math.sqrt(R*R-(-xmin+massive[1][i])*(-xmin+massive[1][i])),(xmin-massive[1][i]+R));}
              if ((massive[2][i]<=ymax)&&((massive[2][i]+R)>=ymax)) {V-=sphereLayer(Math.sqrt(R*R-(ymax-massive[2][i])*(ymax-massive[2][i])),(massive[2][i]+R-ymax));}
              if ((massive[2][i]>=ymin)&&((massive[2][i]-R)<=ymin)) {V-=sphereLayer(Math.sqrt(R*R-(-ymin+massive[2][i])*(-ymin+massive[2][i])),(ymin-massive[2][i]+R));}
              if ((massive[3][i]<=zmax)&&((massive[3][i]+R)>=zmax)) {V-=sphereLayer(Math.sqrt(R*R-(zmax-massive[3][i])*(zmax-massive[3][i])),(massive[3][i]+R-zmax));}
              if ((massive[3][i]>=zmin)&&((massive[3][i]-R)<=zmin)) {V-=sphereLayer(Math.sqrt(R*R-(-zmin+massive[3][i])*(-zmin+massive[3][i])),(zmin-massive[3][i]+R));}
              if (massive[1][i]>=xmax) {V=sphereLayer(Math.sqrt(R*R-(xmax-massive[1][i])*(xmax-massive[1][i])),(xmax-massive[1][i]+R));}
              if (massive[1][i]<=xmin) {V=sphereLayer(Math.sqrt(R*R-(xmin-massive[1][i])*(xmin-massive[1][i])),(massive[1][i]-xmin+R));}
              if (massive[2][i]>=ymax) {V=sphereLayer(Math.sqrt(R*R-(ymax-massive[2][i])*(ymax-massive[2][i])),(ymax-massive[2][i]+R));}
              if (massive[2][i]<=ymin) {V=sphereLayer(Math.sqrt(R*R-(ymin-massive[2][i])*(ymin-massive[2][i])),(massive[2][i]-ymin+R));}
              if (massive[3][i]>=zmax) {V=sphereLayer(Math.sqrt(R*R-(zmax-massive[3][i])*(zmax-massive[3][i])),(zmax-massive[3][i]+R));}
              if (massive[3][i]<=zmin) {V=sphereLayer(Math.sqrt(R*R-(zmin-massive[3][i])*(zmin-massive[3][i])),(massive[3][i]-zmin+R));}
              
              Vfinal+=V;
              M++;
          }  
        }
    }
    
    public static void main(String[] args) {
        
        
        
        
        
        VolumeHS obj = new VolumeHS();
            
            //int N=2544;
             
            obj.Massive();
            obj.volume();
            //obj.MinMax();
            //obj.WriterKirkland();
            //obj.WriterJEMS();
            //obj.Sort();
            
            for (int i=0; i<obj.N; i++){
                    System.out.println(obj.massive[0][i]+" "+obj.massive[1][i]+" "+obj.massive[2][i]+" "+obj.massive[3][i]);     
            }
            System.out.println("Количество атомов в объеме " + obj.M);
            System.out.println("число пи " + Math.PI);
            System.out.println("Радиус шара " + obj.R);
            System.out.println("Объем одного шара " + obj.V);
            System.out.println("Занимаемый объем " + obj.Vfinal);
            System.out.println("Объем ячейки " + obj.boxVolume);
            //obj.Density();
            //System.out.println("Текст записан");
    }
}
