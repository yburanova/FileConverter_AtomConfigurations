package kirklandfile;

import java.io.*;
import java.util.Scanner;
import java.text.*;

public class ReaderDM {
    
    private int Number;
    private String name;
    int N=537;
    private double[][] massive = new double[91][N];
    private String text;
    int i=0,j=0;
    
    
    
    
    File fileInput(int k){
        
        name = k + ".s";
        
        File fileInp = new File(name); //Исходный файл
        return fileInp;
    }
    
    File fileOutput(){
        File fileOut = new File("I_Vac+Imp.txt");//Конечный файл
        return fileOut;
    }
    
    void Massive(){ 
        try{
            for (int k=60; k<=960; k+=10){
                
        Scanner scan = new Scanner(fileInput(k));
        //scan.nextLine();
        j=0;
        
        while (scan.hasNextLine()){//Считываем файл, записываем в массив
                text = scan.nextLine();
                String[] str =text.trim().split("[ ]+");
                System.out.println(str[0] + " " + str[1]);
                
                massive[i][j]=Double.valueOf(str[1]);
                j++;
                      
            }   
             scan.close();
             i++;
            } 
    }catch (IOException error) {
            System.out.println("Не открывается!");
        }
    }
    
    
    void Writer(){
            DecimalFormatSymbols s = new DecimalFormatSymbols();
            s.setDecimalSeparator(',');
            DecimalFormat f = new DecimalFormat("#,#####0.00000", s);
        try{
           FileWriter fw = new FileWriter(fileOutput());
                
                for(j=0;j<N; j++){//Выводим массив в файл
                   for(i=0;i<90;i++){
                       fw.write(f.format(massive[i][j]) + "    ");
                }
                   fw.write("\n");
            }    
            
            fw.close();
            
        }catch(IOException error) {
            System.out.println("Низзя!");
        }
    }
    
    public static void main(String[] args) {
        ReaderDM obj = new ReaderDM();
             
            obj.Massive();
            obj.Writer();
            for (int i=0; i<91; i++){
                for(int j=0; j<obj.N; j++){
                    System.out.print(obj.massive[i][j]+" ");     
            }
                System.out.println();
            }
    }
}
