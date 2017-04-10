package kirklandfile.FileConverter_AtomConfigurations;

import kirklandfile.DensityChange;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by buranova on 10.04.2017.
 */
public class ChemProfileForCuAg {
    static double mask = 0.5;
    static double radiusAl = 1.75; // Angstrom
    public static final double volume = 4*Math.PI*radiusAl*radiusAl*radiusAl/3; // 22.4486 for ideal
    static double step = 0.1; // Angstrom
    static int points;
    static int maskLength = 20;

    static double sizeX;
    static double sizeY;
    static double sizeZ;

    static double[][] directionY;

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(new File("MC.CuAg_750K_SGC.00050000_Kirkland.xyz"));

        FileWriter fw100 = new FileWriter("MC.CuAg_750K_SGC.00050000_Kirkland_profile_100.txt");

        scanner.nextLine(); // line with comments
        String[] parameters = scanner.nextLine().split(" ");

        // Size of the cell
        sizeX = Double.parseDouble(parameters[0]);
        sizeY = Double.parseDouble(parameters[1]);
        sizeZ = Double.parseDouble(parameters[2]);

        points = (int)(sizeY/step);
        directionY = new double[points][3];
        System.out.println("Number of points " + points);

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

                // y calculations
                if ((x <= sizeX/2 + maskLength) && (x >= sizeX/2 - maskLength))
                {
                    int N1 = (int)Math.round((y-mask-radiusAl)/step) - 100; // to remove numerical mistakes
                    int N2 = (int)Math.round((y+radiusAl)/step) + 100;

                    // avoiding borders
                    if (N1 < 0)
                        N1 = 0;
                    if (N2 >= (int)(sizeY/step))
                        N2 = (int)(sizeY/step) - 1;

                    // loop through all masks, where the atom could be accounted
                    for (int i = N1; i <= N2; i++)
                    {
                        double min = i*step; //lower border of mask
                        double max = i*step + mask; // higher border of mask

                        double volumePart = volume; // volume of the particle

                        if ((min >= y + radiusAl)||(max <= y - radiusAl)) // if particle is not in the mask - remove from accounting (numerical problem)
                            volumePart = 0;

                        if(radiusAl >= Math.abs(min - y)) // lower border
                        {
                            double h = radiusAl - Math.abs(min - y); // height of the sector
                            if (h <= radiusAl)
                            {
                                if (y >= min)
                                {
                                    volumePart = volume - Math.PI*h*h*(3*radiusAl - h)/3; // remove sector
                                }
                                else
                                    volumePart = Math.PI*h*h*(3*radiusAl - h)/3; // only sector is accounted
                            }
                        }
                        else if(radiusAl >= Math.abs(max - y)) // higher border
                        {
                            double h = radiusAl - Math.abs(max - y);
                            if (h <= radiusAl)
                            {
                                if (y >= max)
                                    volumePart = Math.PI*h*h*(3*radiusAl - h)/3;
                                else
                                    volumePart = volume - Math.PI*h*h*(3*radiusAl - h)/3;
                            }
                        }

                        if(str[0].equals("29"))
                            directionY[i][1] += volumePart;
                        else if(str[0].equals("47"))
                            directionY[i][2] += volumePart;
                        else
                            directionY[i][0] += volumePart;
                    }
                }
            }
        }

        // printing in file
        for(int i = (int)(mask/step); i < directionY.length - mask/step*2; i++)
        {
            fw100.write(i + " " + directionY[i][0]  + " " + directionY[i][1] + " " + directionY[i][2] + "\n");
        }

        fw100.close();
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

            double max = 0;

            max = 0;

            for(int i = 0; i < directionY.length; i++)
            {
                double sum = directionY[i][0] + directionY[i][1] + directionY[i][2];
                if (sum > max)
                    max = sum;
            }

            for(int i = 0; i < directionY.length*step * 2; i++)
            {
                g2.setColor(Color.BLACK);
                g2.fillRect(i, (int)(directionY[i][0]/max*600), 4, 4);
                g2.setColor(Color.GRAY);
                g2.fillRect(i, (int)(directionY[i][1]/max*600), 4, 4);
                g2.setColor(Color.BLUE);
                g2.fillRect(i, (int)(directionY[i][2]/max*600), 4, 4);

                //System.out.println(i + " " + directionY[i][0] + " " + directionY[i][1] + " " + directionY[i][2]);
            }


        }
    }
}
