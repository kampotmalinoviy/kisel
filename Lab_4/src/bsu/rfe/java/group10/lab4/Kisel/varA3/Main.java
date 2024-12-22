package bsu.rfe.java.group10.lab4.Kisel.varA3;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Main {

    private static double x(double t) {
        return 16 * Math.pow(Math.sin(t), 3);
    }

    private static double y(double t) {
        return 13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t);
    }

    private static void createData(File file) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        ArrayList<Double> l = new ArrayList<>();
        for (double t = 0; t <= 2 * Math.PI; t += 0.2) {
            l.add(10 * x(t));
            l.add(10 * y(t));
        }
        for (Double v : l) {
            out.writeDouble(v);
        }
        out.close();
    }

    private static void getData(File file) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        while (in.available() > 0) {
            double x = in.readDouble();
            double y = in.readDouble();
            System.out.println("x: " + x + ", y: " + y);
        }
        in.close();
    }

    private static Double[][] getGraphicsDataFromFile(File file) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        ArrayList<Double[]> data = new ArrayList<>();
        while (in.available() > 0) {
            double x = in.readDouble();
            double y = in.readDouble();
            data.add(new Double[]{x, y});
        }
        in.close();
        return data.toArray(new Double[0][0]);
    }

    private static void transform(File file) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        String new_name = file.getName().split("\\.")[0] + "_new.bin";
        File new_file = new File(new_name);
        DataOutputStream out = new DataOutputStream(new FileOutputStream(new_file));
        int i = 0;
        double a;
        while (in.available() > 0) {
            if (i++ % 100 == 0) {
                out.writeDouble(in.readDouble());
                out.writeDouble(2 * in.readDouble());
            } else {
                a = in.readDouble();
                a = in.readDouble();
            }
        }
        in.close();
        out.close();
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }


}

