
package kirklandfile;
   import java.io.*;
   import java.util.Scanner;
   import java.text.*;

public class JEMS {
    
    private int i,j; 
    private int N;
    private double[][] massive;
    private String text;
    private double[] min,max;
    
    File fileInput(){
        File fileInp = new File("Al_boundary.4layers.xyz"); //Исходный файл
        return fileInp;
    }
    
    File fileOutputJEMS(){
        File fileOut = new File("S5(Al)4layers.txt");//Конечный файл
        return fileOut;
    }
    
    void Massive(int N){
        this.N=N;
        massive = new double[4][N];
        try{
        Scanner scan = new Scanner(fileInput());
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
    
    void MinMax(int N){
        min= new double[4];
        max= new double[4];
        for(i=0;i<4;i++){//Находим минимумы и максимумы
                for(j=0;j<N;j++){
                    if(min[i]>massive[i][j]) min[i]=massive[i][j];
                    if(max[i]<massive[i][j]) max[i]=massive[i][j];
                }
            }
    }
    void WriterJEMS(int N){
            DecimalFormatSymbols s = new DecimalFormatSymbols();
            s.setDecimalSeparator('.');
            DecimalFormat f = new DecimalFormat("#,#####0.00000", s);
        try{
           FileWriter fw = new FileWriter(fileOutputJEMS());
           
           String[][] kirk = new String[6][N];//Создаем и заполняем новый массив
            
                for (j=0;j<N;j++){
                    kirk[0][j]="atom|"+j+"|Al,a,";
                    kirk[1][j]=f.format((massive[1][j]-min[1])/(max[1]-min[1]))+",";
                    kirk[2][j]=f.format((massive[2][j]-min[2])/(max[2]-min[2]))+",";
                    kirk[3][j]=f.format((massive[3][j]-min[3])/(max[3]-min[3]))+"";
                    kirk[4][j]=",0.005,";
                    kirk[5][j]="1,0.034,Def,0 ";
                }
            
                fw.write("file|G:\\Al-sigma5\\Al_boundary_JEMS.xyz\n" +
"name|Al_boundary_JEMS\n" +
"creator|admin\n" +
"date|Mon Sep 16 15:31:38 CEST 2013\n" +
"system|triclinic\n" +
"superCell|false\n" +
"HMSymbol|1|1|0|0|0| P  1      \n" +
"rps|0|x    ,   y    ,   z\n" +
"lattice|0|4.050\n" +
"lattice|1|4.050\n" +
"lattice|2|4.050\n" +
"lattice|3|90.0\n" +
"lattice|4|90.0\n" +
"lattice|5|90.0\n");
                
                for(j=0;j<N; j++){//Выводим массив в файл
                   for(i=0;i<6;i++){
                       fw.write(kirk[i][j]);
                }
                   fw.write("\n");
            }    
            fw.write("aff|0|Al|2.276,72.322,2.428,19.773,0.858,3.08,0.317,0.408|Doyle - Turner   Acta Cryst. A24 (1968), 390\n" +
"aff|0|Al|0.77963454,2.7240589,0.42617497,0.09113759,0.057230096,79.763580.03273507,0.17969826,0.7859108,8.646112,0.1137047,0.45077428|Earl J. Kirkland, Advanced Computing in Electron Microscopy\n" +
"nsl|0|Al|0.345\n" +
"aff|0|Al|0.1165,0.1295,0.5504,1.2619,1.0179,6.8242,2.6295,28.4577,1.5711,88.475|L. Peng et al., Acta Cryst. A52 (1996) 257-276::Def\n" +
"aff|0|Al|6.4202,3.0387,1.9002,0.7426,1.5936,31.5472,1.9646,85.0886,1.1151|XRay:: RHF::Def");
            fw.close();
            
        }catch(IOException error) {
            System.out.println("Низзя!");
        }
    }
    
    public static void main(String[] args) {
        
            JEMS obj = new JEMS();
            
            int N=2544;
             
            obj.Massive(N);
            obj.MinMax(N);
            obj.WriterJEMS(N);
                       
            System.out.println("Текст записан");            
        
    }

}
