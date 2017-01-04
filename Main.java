
package kirklandfile.FileConverter_AtomConfigurations;
   import com.google.common.primitives.Doubles;
   import kirklandfile.FileConverter_AtomConfigurations.readers.ReaderCommand;
   import kirklandfile.FileConverter_AtomConfigurations.readers.XYZReader;
   import kirklandfile.FileConverter_AtomConfigurations.writers.KirklandWriter;
   import kirklandfile.FileConverter_AtomConfigurations.writers.WriterCommand;


   import java.io.*;
   import java.util.*;
   import java.text.*;

public class Main {

    private ReaderCommand readerCommand;
    private WriterCommand writerCommand;

    private double X;
    private double Y;
    private double Z;

    private int i,j; 
    private int N;
    private double[][] massive;
    private String text;
    private double[] min,max;
    
    File fileInput(){
        File fileInp = new File("Al3Sc_110_8.170Angstrom_30Proz.xyz"); //Исходный файл
        return fileInp;
    }
    
    File fileOutputKirkland(){
        File fileOut = new File("Al3Sc_110_8.170Angstrom_30Proz_Kirkland.xyz");//Конечный файл
        return fileOut;
    }
    
    File fileOutputDrProbe(){
        File fileOut = new File("(081)_final_5deg_DrProbe.txt");//Конечный файл
        return fileOut;
    }
    
    File fileOutputJEMS(){
        File fileOut = new File("(081)_5grad_toJEMS.txt");//Конечный файл
        return fileOut;
    }
    
    File fileOutputCFG(){
        File fileOut = new File("Al_Melt_FINAL_CFG.cfg");//Конечный файл
        return fileOut;
    }
    
    void Massive(){
        try
        {
            readerCommand = new XYZReader();
            massive = readerCommand.read(fileInput()); // massive contains absolute coordinates!!
            N = massive[0].length;
        }
        catch (IOException error)
        {
            System.out.println("Problem with the reading of the file");
        }
    }
    
    void MinMax(){

        min = new double[4];
        max = new double[4];

        for (int i = 0; i < 4; i++)
        {
            min[i] = Doubles.min(massive[i]);
            max[i] = Doubles.max(massive[i]);
        }

    }
    
    public void WriterKirkland()
    {
        try {
            writerCommand = new KirklandWriter();
            writerCommand.write(fileOutputKirkland(), massive);
        }
        catch (IOException e)
        {
            System.out.println("Problem with the writing of the file");
        }

                    
                    //Блок для поворота образца на нужный угол
                    /*int angle = 0;
                    double X = (massive[1][j]-min[1]);
                    double Z = (massive[3][j]-min[3]);
                    
                    kirk[1][j] = f.format(X*Math.cos(Math.toRadians(angle))-Z*Math.sin(Math.toRadians(angle))) + " ";
                    kirk[2][j] = f.format(massive[2][j]-min[2])+" ";
                    kirk[3][j] = f.format(X*Math.sin(Math.toRadians(angle))+Z*Math.cos(Math.toRadians(angle))) + " ";*/


    }
    
    void WriterJEMS(){
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
    
    void WriterDrProbe(){
            DecimalFormatSymbols s = new DecimalFormatSymbols();
            s.setDecimalSeparator('.');
            DecimalFormat f = new DecimalFormat("#,#####0.00000", s);
        try{
           FileWriter fw = new FileWriter(fileOutputDrProbe());
           
           String[][] kirk = new String[6][N];//Создаем и заполняем новый массив
                    
           for( int j = 0; j < N; j++)
           {
               int angle = 5;
                    double X = (massive[1][j] - min[1]);
                    double Z = (massive[3][j] - min[3]);
                    
                    massive[1][j] = X*Math.cos(Math.toRadians(angle))-Z*Math.sin(Math.toRadians(angle));
                    massive[3][j] = X*Math.sin(Math.toRadians(angle))+Z*Math.cos(Math.toRadians(angle));
           }
           
           MinMax();
           
           double sizeX = (max[1] - min[1]);
           double sizeY = (max[2] - min[2]);
           double sizeZ = (max[3] - min[3]);
            
                for (j=0;j<N;j++){
                    if((int)massive[0][j] == 7){
                        kirk[0][j]= 21 +" ";
                    } else { 
                        kirk[0][j]= "Al "; 
                    }                  
                    
                    kirk[1][j] = f.format(massive[1][j]/sizeX) + " ";
                    kirk[2][j] = f.format((massive[2][j]-min[2])/sizeY)+" ";
                    kirk[3][j] = f.format(massive[3][j]/sizeZ) + " ";
                    
                    /*kirk[1][j]=f.format(massive[1][j]-min[1])+" ";
                    kirk[2][j]=f.format(massive[2][j]-min[2])+" ";
                    kirk[3][j]=f.format(massive[3][j]-min[3])+" ";
                            */
                    kirk[4][j] = "1.000000 ";
                    kirk[5][j] = "0.008500 0.000000 0.000000 0.000000";
                }
                
                              
                fw.write("Al GB \n");
                fw.write("0 ");
                fw.write(sizeX/10 + " ");
                fw.write(sizeY/10 + " ");
                fw.write(sizeZ/10 + " 90 90 90"+ "\n");
                
                for(j=0;j<N; j++)
                {//Выводим массив в файл
                   for(i=0;i<6;i++){
                       fw.write(kirk[i][j]);
                }
                   fw.write("\n");
            }    
            
            fw.write("*");
            fw.close();
            
        }catch(IOException error) {
            System.out.println("Низзя!");
        }
    }
    
    void WriterCFG(){
            DecimalFormatSymbols s = new DecimalFormatSymbols();
            s.setDecimalSeparator('.');
            DecimalFormat f = new DecimalFormat("#,#######0.0000000", s);
            int sum = 0;
        try{
           FileWriter fw = new FileWriter(fileOutputCFG());
           
           String[][] kirk = new String[3][N];//Создаем и заполняем новый массив
            ArrayList<String[]> list = new ArrayList<String[]>();

           MinMax();
           
           /*for( int j = 0; j < N; j++)
           {
               int angle = 5;
                    double X = (massive[1][j] - min[1]);
                    double Z = (massive[3][j] - min[3]);
                    
                    massive[1][j] = X*Math.cos(Math.toRadians(angle))-Z*Math.sin(Math.toRadians(angle));
                    massive[3][j] = X*Math.sin(Math.toRadians(angle))+Z*Math.cos(Math.toRadians(angle));
           }
           
           MinMax();*/
           
           double sizeX = (max[1] - min[1]);
           double sizeY = (max[2] - min[2]);
           double sizeZ = (max[3] - min[3]);
            
                for (int j = 0; j < N; j++)
                {            
                    if (massive[2][j] < 155 && massive[2][j] > 145)
                        sum++;

                    kirk[0][j] = f.format((massive[1][j]-min[1])/sizeX) + " ";
                    kirk[1][j] = f.format((massive[2][j]-min[2])/sizeY) + " ";
                    kirk[2][j] = f.format((massive[3][j]-min[3])/sizeZ) + " ";
                    
                    /*kirk[1][j]=f.format(massive[1][j]-min[1])+" ";
                    kirk[2][j]=f.format(massive[2][j]-min[2])+" ";
                    kirk[3][j]=f.format(massive[3][j]-min[3])+" ";
                            */
                }
                
                double center = (max[3] - min[3])/2;
                double maxBorder = center + 20;
                double minBorder = center - 20;
                System.out.println(sum);
                              
                fw.write("Number of particles = " + N + "\n");
                fw.write("A = 1 Angstrom (basic length-scale)\n");
                fw.write("H0(1,1) = " + sizeX + " A\n");
                fw.write("H0(1,2) = 0 A \nH0(1,3) = 0 A \nH0(2,1) = 0 A \n");
                fw.write("H0(2,2) = " + sizeY + " A\n");
                fw.write("H0(2,3) = 0 A \n" + "H0(3,1) = 0 A \n" + "H0(3,2) = 0 A\n");
                fw.write("H0(3,3) = " + sizeZ + " A\n");
                fw.write(".NO_VELOCITY.\n" + "entry_count = 3\n" + "26.982000 \n" + "Al \n");

                int count = 0;
                
                for(j = 0; j < N; j++)
                {//Выводим массив в файл
                    System.out.println((155-min[2])/sizeY);
                    System.out.println((145-min[2])/sizeY);


                    if ((Double.parseDouble(kirk[1][j]) > (155-min[2])/sizeY) || (Double.parseDouble(kirk[1][j]) < (145-min[2])/sizeY))
                    {
                        for (i = 0; i < 3; i++)
                            fw.write(kirk[i][j] + " ");

                        fw.write("\n");
                    }
                    else
                    {
                        count++;
                        if(count%20 != 0)
                        {
                            for (i = 0; i < 3; i++)
                                fw.write(kirk[i][j] + " ");

                            fw.write("\n");
                        }
                    }

                   //fw.write("0.7806 1.0 ");

                }    
            fw.close();
            
        }catch(IOException error) {
            System.out.println("Низзя!");
        }
    }
    
    
    void Sort(){
        double[] temp = new double[4];
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
    
    public static void main(String[] args) {
        Main obj = new Main();
            
            //int N=2544;
             
            obj.Massive();
            obj.MinMax();
            obj.WriterKirkland();
            //obj.WriterCFG();
            //obj.WriterDrProbe();
            //obj.WriterJEMS();
            //obj.Sort();
            
            //for (int i=0; i<obj.N; i++){
            //        System.out.println(obj.massive[0][i]+" "+obj.massive[1][i]+" "+obj.massive[2][i]+" "+obj.massive[3][i]);     
            //}
            //System.out.println("Текст записан");
    }

}
