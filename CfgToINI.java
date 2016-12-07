package kirklandfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * Created by buranova on 20.09.2016.
 */
public class CfgToINI {
    File fileInput(){
        File fileInp = new File("Al_mono.vacancy-6Proz.cfg"); //Исходный файл
        return fileInp;
    }

    File fileOutput(){
        File fileOut = new File("Al_mono.vacancy-6Proz_cfg_to_Kirkland.xyz");//Конечный файл
        return fileOut;
    }

    public static void main(String[] args) throws IOException
    {
        CfgToINI cfgToINI = new CfgToINI();
        Scanner scan = new Scanner(cfgToINI.fileInput());

        int sum = 0;
        int angle = 3;

        FileWriter fw = new FileWriter(cfgToINI.fileOutput());

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat("#,#####0.00000", s);

        fw.write("INI file\n");
        fw.write("101.25 101.25 101.25\n");

        while(scan.hasNext())
        {
            String line = scan.nextLine();

            if (line.startsWith("26."))
            {
                scan.nextLine(); // line with "Al"

                line = scan.nextLine();
                String[] numbers = line.split(" ");
                try{
                    double x = Double.parseDouble(numbers[0]);
                    double y = Double.parseDouble(numbers[1]);
                    double z = Double.parseDouble(numbers[2]);

                    double newX = x * 101.25;
                    double newY = y * 101.25;
                    double newZ = z * 101.25;

                    String formattedString = "13 " + f.format(newX) + " " + f.format(newY) + " " + f.format(newZ) + " 1 0.078\n";

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
