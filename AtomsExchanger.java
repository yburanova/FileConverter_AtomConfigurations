package kirklandfile.FileConverter_AtomConfigurations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by buranova on 10.01.2017.
 */
public class AtomsExchanger {

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(new File("Al3Sc_110_8.170Angstrom_10Proz_Kirkland.xyz"));
        FileWriter fw = new FileWriter("Al3Sc_110_8_ExchangedAtoms.xyz");

        fw.write(scanner.nextLine() + "\n"); // line with comments
        fw.write(scanner.nextLine() + "\n"); // line with parameters

        while(scanner.hasNext())
        {
            String text = scanner.nextLine();
            if(text.startsWith("-1"))
            {
                fw.write("-1");
                break;
            }
            else
            {
                String[] str = text.split(" "); // atom coordinates

                int atomType = Integer.parseInt(str[0]);

                if(atomType == 21 || atomType == 40)
                {
                    double x = Double.parseDouble(str[1]);
                    double y = Double.parseDouble(str[2]);
                    double z = Double.parseDouble(str[3]);

                    double distance = Math.sqrt(x*x + y*y + z*z);

                    if(distance < 400 && distance > 350)
                        atomType = 40;
                    else
                        atomType = 21;

                    fw.write(atomType + " " + x + " " + y + " " + z + "1 0.078 \n");
                }
                else
                    fw.write(text + "\n");

            }
        }
    }
}
