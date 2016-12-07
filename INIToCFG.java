package kirklandfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * Created by buranova on 14.10.2016.
 */
public class INIToCFG {
    File fileInput(){
        File fileInp = new File("Al_boundary_RealGB.xyz"); //Исходный файл
        return fileInp;
    }

    File fileOutput(){
        File fileOut = new File("Al_boundary_RealGB.cfg");//Конечный файл
        return fileOut;
    }

    public static void main(String[] args) throws IOException
    {
        INIToCFG iniToCFG = new INIToCFG();
        Scanner scan = new Scanner(iniToCFG.fileInput());

        int sum = 0;
        int angle = 3;

        FileWriter fw = new FileWriter(iniToCFG.fileOutput());

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat("#,#######0.0000000", s);

        double sizeX = 522.563;
        double sizeY = 351.488;
        double sizeZ = 206.57434;

        fw.write("Number of particles = 2260352\n" +
                "A = 1 Angstrom (basic length-scale)\n" +
                "H0(1,1) = " + sizeX + " A\n" +
                "H0(1,2) = 0 A \n" +
                "H0(1,3) = 0 A \n" +
                "H0(2,1) = 0 A \n" +
                "H0(2,2) = " + sizeY + " A\n" +
                "H0(2,3) = 0 A \n" +
                "H0(3,1) = 0 A \n" +
                "H0(3,2) = 0 A\n" +
                "H0(3,3) = " + sizeZ + " A\n" +
                ".NO_VELOCITY.\n" +
                "entry_count = 3\n" +
                "26.982000 \n" +
                "Al \n");

        while(scan.hasNext())
        {
            String line = scan.nextLine();

            if (line.startsWith("13 "))
            {
                String[] numbers = line.split(" ");
                try{
                    double x = Double.parseDouble(numbers[1]);
                    double y = Double.parseDouble(numbers[2]);
                    double z = Double.parseDouble(numbers[3]);

                    double newX = x / sizeX;
                    double newY = y / sizeY;
                    double newZ = z / sizeZ;

                    String formattedString = f.format(newX) + " " + f.format(newY) + " " + f.format(newZ) + "\n";

                    fw.write(formattedString);
                }
                catch (IllegalFormatException e)
                {

                }
            }
        }

        System.out.println(sum);


        scan.close();
        fw.close();
    }
}
