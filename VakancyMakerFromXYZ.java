package kirklandfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * Created by buranova on 31.08.2016.
 */
public class VakancyMakerFromXYZ {
    File fileInput(){
        File fileInp = new File("Al_Monocrystal_10nm.xyz"); //Исходный файл
        return fileInp;
    }

    File fileOutput(){
        File fileOut = new File("Al_Monocrystal_10nm_Vacancy.xyz");//Конечный файл
        return fileOut;
    }

    public static void main(String[] args) throws IOException
    {
        VakancyMakerFromXYZ vacancyMaker = new VakancyMakerFromXYZ();
        Scanner scan = new Scanner(vacancyMaker.fileInput());
        int sum = 0;

        FileWriter fw = new FileWriter(vacancyMaker.fileOutput());



        while(scan.hasNext())
        {
            String line = scan.nextLine();

            if (line.startsWith("1 "))
            {
                String[] numbers = line.split(" ");
                try{
                    double x = Double.parseDouble(numbers[1]);
                    double y = Double.parseDouble(numbers[2]);
                    double z = Double.parseDouble(numbers[3]);


                    if ( y == 50.625 && z < 60 && z > 40 ) {
                        sum++;
                    }
                    else
                    {
                        fw.write(line + "\n");
                    }
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

        System.out.println(sum);


        scan.close();
        fw.close();
    }
}
