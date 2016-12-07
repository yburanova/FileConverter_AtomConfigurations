package kirklandfile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by buranova on 16.11.2016.
 */
public class ChemicalProfile extends JFrame {
    static double[][] temp = null;
    static double mask = 4;
    static double radiusAl = 1.75; // Angstrom
    static double volume = 4*Math.PI*radiusAl*radiusAl*radiusAl/3; // 22.4486 for ideal
    static double step = 0.3; // Angstrom
    static int points;

    static double sizeX;
    static double sizeY;
    static double sizeZ;

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(new File("Al3Sc_110_20.5Proz_Kirkland.xyz"));
        FileWriter fw = new FileWriter("Al3Sc_110_20.5Proz_Kirkland_profile_111.txt");
        scanner.nextLine(); // line with comments
        String[] parameters = scanner.nextLine().split(" ");

        // Size of the cell
        sizeX = Double.parseDouble(parameters[0]);
        sizeY = Double.parseDouble(parameters[1]);
        sizeZ = Double.parseDouble(parameters[2]);

        points = (int)(sizeX/step);
        System.out.println("Number of points " + points);

        double[][] massive = new double[points][3];

        while(scanner.hasNext())
        {
            String text = scanner.nextLine();
            if(text.startsWith("-1"))
                break;
            else
            {
                String[] str = text.split(" "); // atom coordinates
                double x = Double.parseDouble(str[1]);
                double y = Double.parseDouble(str[2]);
                double z = Double.parseDouble(str[3]);

                if ((y > 160) || (y < 120))
                   continue;

                int N1 = (int)Math.round((x-mask-radiusAl)/step) - 100; // to remove numerical mistakes
                int N2 = (int)Math.round((x+radiusAl)/step) + 100;

                // avoiding borders
                if (N1 < 0)
                    N1 = 0;
                if (N2 >= points)
                    N2 = points - 1;

                // loop through all masks, where the atom could be accounted
                for (int i = N1; i <= N2; i++)
                {
                    double min = i*step; //lower border of mask
                    double max = i*step + mask; // higher border of mask

                    double volumePart = volume; // volume of the particle

                    if ((min >= x + radiusAl)||(max <= x - radiusAl)) // if particle is not in the mask - remove from accounting (numerical problem)
                        volumePart = 0;

                    if(radiusAl > Math.abs(min - x)) // lower border
                    {
                        double h = radiusAl - Math.abs(min - x); // height of the sector
                        if (h <= radiusAl)
                        {
                            if (x > min)
                            {
                                volumePart = volume - Math.PI*h*h*(3*radiusAl - h)/3; // remove sector
                            }
                            else
                                volumePart = Math.PI*h*h*(3*radiusAl - h)/3; // only sector is accounted
                        }
                    }
                    else if(radiusAl > Math.abs(max - x)) // higher border
                    {
                        double h = radiusAl - Math.abs(max - x);
                        if (h <= radiusAl)
                        {
                            if (x > max)
                                volumePart = Math.PI*h*h*(3*radiusAl - h)/3;
                            else
                                volumePart = volume - Math.PI*h*h*(3*radiusAl - h)/3;
                        }
                    }

                    if(str[0].equals("21"))
                        massive[i][1] += volumePart;
                    else if(str[0].equals("40"))
                        massive[i][2] += volumePart;
                    else
                        massive[i][0] += volumePart;
                }
            }
        }

        temp = new double[points][3];

        // averaging
        for(int i = 0 ; i < massive.length; i++)
        {
            for(int j = 0; j < 3; j++)
                temp[i][j] = massive[i][j];
        }

        // printing in file
        int max = 0;
        for(int i = (int)(mask/sizeY*points); i < temp.length - mask/sizeY*points*2; i++)
        {
            fw.write(i + " " + temp[i][0]  + " " + temp[i][1] + " " + temp[i][2] + "\n");
            if (temp[i][0] > max)
                max = (int)temp[i][0];
        }

        System.out.println("Maximum is " + max);

        for (int i = 0; i < temp.length; i++)
            for(int j = 0; j < 3; j++)
                temp[i][j] = temp[i][j]/max;


        fw.close();
        scanner.close();

        DensityChange gui = new DensityChange();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        gui.setSize(1500, 500);
        gui.setTitle("Density Profile");
        JPanel cp = new JPanel(new BorderLayout());
        gui.setContentPane(cp);
        cp.add(new Graph(), BorderLayout.CENTER);
    }

    public static class Graph extends JPanel
    {
        public Graph()
        {
            setOpaque(true);
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            Graphics2D g2 = (Graphics2D)g;

            g2.setColor(Color.red);

            for(int i = (int)(mask/sizeX*points); i < temp.length - mask/sizeX*points*2; i++)
            {
                g2.setColor(Color.BLACK);
                g2.fillRect(i, (int)(temp[i][0]*2698), 4, 4);
                g2.setColor(Color.RED);
                g2.fillRect(i, (int)(temp[i][1]*4496), 4, 4);
                g2.setColor(Color.BLUE);
                g2.fillRect(i, (int)(temp[i][2]*9122), 4, 4);

                System.out.println(i + " " + temp[i][0] + " " + temp[i][1] + " " + temp[i][2]);
            }



        }
    }

}
