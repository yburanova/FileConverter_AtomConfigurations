package kirklandfile.FileConverter_AtomConfigurations.readers;

import com.google.common.primitives.Doubles;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by buranova on 07.12.2016.
 */
public class XYZReader implements ReaderCommand {

    private double[][] massive;
    private int numberOfAtoms;
    private int j;

    private double[] minimums = new double[3];

    @Override
    public double[][] read(File file) throws IOException
    {
            Scanner scan = new Scanner(file);

            // number of atoms
            numberOfAtoms = Integer.valueOf(scan.nextLine());
            massive = new double[4][numberOfAtoms];

            // a comment line
            scan.nextLine();

            // reading file line by line
            while (scan.hasNextLine())
            {
                String text = scan.nextLine();

                String[] str = text.split(" ");

                for (int i = 0; i < 4; i++)
                {
                    massive[i][j] = Double.valueOf(str[i]);
                }

                j++;

            }
            scan.close();

        // moving to the absolute coordinates
        for (int i = 0; i < 3; i++)
            minimums[i] = Doubles.min(massive[i + 1]);

        for (int i = 0; i < massive[0].length; i++)
            for (int j = 1; j < 4; j++)
                massive[j][i] = massive[j][i] - minimums[j - 1];

        System.out.println("Reading is complete");
        return massive;
    }
}
