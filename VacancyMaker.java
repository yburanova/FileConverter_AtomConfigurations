package kirklandfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * Created by buranova on 24.08.2016.
 */
public class VacancyMaker
{

    File fileInput(){
        File fileInp = new File("Al_mono.perfect.cfg"); //Исходный файл
        return fileInp;
    }

    File fileOutput(){
        File fileOut = new File("Al_mono.perfect.vacancy.cfg");//Конечный файл
        return fileOut;
    }

    public static void main(String[] args) throws IOException
    {
        VacancyMaker vacancyMaker = new VacancyMaker();
        Scanner scan = new Scanner(vacancyMaker.fileInput());
        int sum = 0; // deleted atoms
        int sumAll = 0; // all atoms in the layer

        FileWriter fw = new FileWriter(vacancyMaker.fileOutput());



        while(scan.hasNext())
        {
            String line = scan.nextLine();

            if (line.startsWith("26.982000"))
            {
                scan.nextLine(); // line with "Al"

                line = scan.nextLine();
                String[] numbers = line.split(" ");
                try{
                    double x = Double.parseDouble(numbers[0]);
                    double y = Double.parseDouble(numbers[1]);
                    double z = Double.parseDouble(numbers[2]);

                    if(y >= 0.5 && y <= 0.50001)
                        sumAll++;

                    if ( y >= 0.5 && y <= 0.50001 && z < 0.53 && z > 0.47 ) {
                        sum++;
                    }
                    else
                    {
                        fw.write("26.982000\n");
                        fw.write("Al\n");
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
        System.out.println(sumAll);


        scan.close();
        fw.close();
    }
}
