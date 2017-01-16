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
        // reading and writing files
        Scanner scanner = new Scanner(new File("Al3Sc_110_8.170Angstrom_10Proz.xyz"));
        FileWriter fw = new FileWriter("Al3Sc_110_8.170Angstrom_10Proz_ExchangedAtoms.xyz");

        int atomsNumber = Integer.parseInt(scanner.nextLine());
        fw.write(atomsNumber + "\n");

        fw.write(scanner.nextLine() + "\n"); // line with comments

        for(int i = 0; i < atomsNumber; i++)
        {
            String text = scanner.nextLine();

            // The particle is in [110] direction
            // We calculate random theta and phi as it would be for [100], then turn it to 45 grad

            String[] str = text.split(" "); // atom coordinates

            int atomType = Integer.parseInt(str[0]);
            double x = Double.parseDouble(str[1]);
            double y = Double.parseDouble(str[2]);
            double z = Double.parseDouble(str[3]);

            double thetaBasic = Math.toDegrees(Math.acos(1/Math.sqrt(3)));

            double theta;
            double phi;
            //System.out.println(dir111);


            if(atomType == 2 || atomType == 5)
            {

                double RCurrent = Math.sqrt(x*x + y*y + z*z);
                double thetaCurrent = Math.toDegrees(Math.atan(Math.sqrt(x*x + y*y) / z));
                double phiCurrent = Math.toDegrees(Math.atan(y / x));

                if(thetaCurrent < 90)
                {
                    theta = thetaBasic;

                    if ((phiCurrent >= 0 && phiCurrent <= 45)|| (phiCurrent <= 360 && phiCurrent >= 315))
                    {
                        phi = 0;
                    }
                    else if (phiCurrent >= 45 && phiCurrent <= 135)
                    {
                        phi = Math.PI / 2;
                    }
                    else if (phiCurrent >= 135 && phiCurrent <= 225)
                    {
                        phi = Math.PI;
                    }
                    else
                    {
                        phi = Math.PI * 3 / 2;
                    }
                }
                else
                {
                    theta = thetaBasic + 45;

                    if ((phiCurrent >= 0 && phiCurrent <= 45)|| (phiCurrent <= 360 && phiCurrent >= 315))
                    {
                        phi = 0;
                    }
                    else if (phiCurrent >= 45 && phiCurrent <= 135)
                    {
                        phi = Math.PI / 2;
                    }
                    else if (phiCurrent >= 135 && phiCurrent <= 225)
                    {
                        phi = Math.PI;
                    }
                    else
                    {
                        phi = Math.PI * 3 / 2;
                    }
                }

                double alpha = Math.toDegrees(Math.sqrt((theta - thetaCurrent)*(theta - thetaCurrent) + (phi - phiCurrent)*(phi - phiCurrent)));
                double probability = Math.cos(alpha);

                System.out.println(probability);


                double distance = Math.sqrt(x*x + y*y + z*z);



                fw.write(atomType + " " + x + " " + y + " " + z + "1 0.078 \n");
            }
            else
                fw.write(text + "\n");


        }
    }
}
