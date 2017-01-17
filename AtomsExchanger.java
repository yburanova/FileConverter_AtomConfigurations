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

        int numberOfExchanges = 0;
        int totalScInShell = 0;

        // reading and writing files
        Scanner scanner = new Scanner(new File("Al3Sc_110_8.170Angstrom_15Proz.xyz"));
        FileWriter fw = new FileWriter("Al3Sc_110_8.170Angstrom_15Proz_ExchangedAtoms.xyz");

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

            int atomTypeNew = atomType;


            if(atomType == 4 || atomType == 5)
                totalScInShell++;

            if (atomType == 5)
            {

                double thetaBasic = Math.toDegrees(Math.acos(1/Math.sqrt(3)));

                double theta;
                double phi;
                //System.out.println(dir111);

                // defining the R, Theta and Phi for the current atom coordinates
                double RCurrent = Math.sqrt(x*x + y*y + z*z);
                double thetaCurrent = Math.toDegrees(Math.acos(y/RCurrent)); // values from 0 to 180
                double phiCurrent = Math.toDegrees(Math.acos(Math.sqrt(z*z/(x*x + z*z)))); // values from 0 to 90, should be calculated for the range 0 to 360

                if (x > 0 && z < 0)
                    phiCurrent += 90;
                else if (x < 0 && z < 0)
                    phiCurrent += 180;
                else if (x < 0 && z > 0)
                    phiCurrent += 270;
                else if (x == 0 && z < 0)
                    phiCurrent += 180;
                else if (z == 0 && x < 0)
                    phiCurrent += 270;

                // defining the closest [111] directions

                if (phiCurrent <= 45 || phiCurrent > 315)
                    phi = 0;
                else if (phiCurrent <= 135)
                    phi = 90;
                else if (phiCurrent <= 225)
                    phi = 180;
                else
                    phi = 270;

                if (thetaCurrent < 90)
                    theta = thetaBasic;
                else
                    theta = thetaBasic + 90;

                // defining the angle between the current direction and [111]

                double alpha = Math.sqrt((theta - thetaCurrent)*(theta - thetaCurrent) + (phi - phiCurrent)*(phi - phiCurrent));
                double probability = Math.cos(Math.toRadians(alpha));

                // defining the random number, if it larger than the probability number -> exchange
                double randomNumber = Math.random();
                if (randomNumber > probability)
                {
                    atomTypeNew = 4;
                    numberOfExchanges++;
                }

            }

            fw.write(atomTypeNew + " " + x + " " + y + " " + z + "\n");
        }

        System.out.println(numberOfExchanges);
        System.out.println(totalScInShell);

        scanner.close();
        fw.flush();
        fw.close();
    }
}
