package kirklandfile.FileConverter_AtomConfigurations.writers;

import com.google.common.primitives.Doubles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by buranova on 07.12.2016.
 */
public class KirklandWriter implements WriterCommand
{
    @Override
    public void write(File file, double[][] atomicPositions) throws IOException
    {
        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat("#,#####0.00000", s);

        FileWriter fw = new FileWriter(file);

        int N = atomicPositions[0].length;
        double X = Doubles.max(atomicPositions[1]);
        double Y = Doubles.max(atomicPositions[2]);
        double Z = Doubles.max(atomicPositions[3]);

        String[][] kirk = new String[6][N];//Создаем и заполняем новый массив

        for (int j = 0; j < N; j++)
        {
            // defining the type of atoms
            if((int)atomicPositions[0][j] == 1 || (int)atomicPositions[0][j] == 4)
            {
                kirk[0][j]= 29 + " ";
            }
            else if((int)atomicPositions[0][j] == 5)
            {
                kirk[0][j] = 40 + " ";
            }
            else {
                kirk[0][j]= 47 +" ";
            }

            // new massive
            kirk[1][j]=f.format(atomicPositions[1][j]) + " ";
            kirk[2][j]=f.format(atomicPositions[2][j]) + " ";
            kirk[3][j]=f.format(atomicPositions[3][j]) + " ";
            kirk[4][j]=1+" ";
            kirk[5][j]=0.078+" ";
        }

        fw.write("Kirkland file\n");
        fw.write(X + " ");
        fw.write(Y + " ");
        fw.write(Z + "\n");

        // printing the massive into the file
        for(int j = 0; j < N; j++)
        {
            for(int i = 0; i < 6; i++)
            {
                fw.write(kirk[i][j]);
            }
            fw.write("\n");
        }

        fw.write("-1");
        fw.close();

        System.out.println("Writing is complete");
    }
}
