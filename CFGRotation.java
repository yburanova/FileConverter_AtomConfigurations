package kirklandfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * Created by buranova on 12.09.2016.
 */
public class CFGRotation {

    static double cellX = 522.563;
    static double cellY = 351.488;
    static double cellZ = 206.57434;

    File fileInput(){
        File fileInp = new File("Al_boundary_RealGB.cfg"); //Исходный файл
        return fileInp;
    }

    File fileOutput(){
        File fileOut = new File("Al_boundary_RealGB_3deg_rotation.cfg");//Конечный файл
        return fileOut;
    }

    public static void main(String[] args) throws IOException
    {
        CFGRotation cfgRotation = new CFGRotation();
        Scanner scan = new Scanner(cfgRotation.fileInput());
        int sum = 0;
        double angle = 3;

        FileWriter fw = new FileWriter(cfgRotation.fileOutput());

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat("#,#######0.0000000", s);

        double boxNewX = cellX * Math.cos(Math.toRadians(angle)) - cellZ * Math.sin(Math.toRadians(angle));
        double boxNexZ = cellX * Math.sin(Math.toRadians(angle)) + cellZ * Math.cos(Math.toRadians(angle));

        System.out.println(boxNewX);
        System.out.println(boxNexZ);

        while(scan.hasNext())
        {
            String line = scan.nextLine();

            if (line.startsWith("0."))
            {
                //scan.nextLine(); // line with "Al"

                //line = scan.nextLine();
                String[] numbers = line.split(" ");
                try{
                    double x = Double.parseDouble(numbers[0]);
                    double y = Double.parseDouble(numbers[1]);
                    double z = Double.parseDouble(numbers[2]);

                    double newX = x * Math.cos(Math.toRadians(angle)) - z * Math.sin(Math.toRadians(angle));
                    double newY = y;
                    double newZ = x * Math.sin(Math.toRadians(angle)) + z * Math.cos(Math.toRadians(angle));

                    String formattedString = f.format(newX) + " " + f.format(newY) + " " + f.format(newZ);

                    //fw.write("26.982000\n");
                    //fw.write("Al\n");
                    fw.write(formattedString + "\n");
                }
                catch (IllegalFormatException e)
                {

                }
            }
            else
            {
                fw.write(line + "\n");
            }
        }

        scan.close();
        fw.close();
    }
}
