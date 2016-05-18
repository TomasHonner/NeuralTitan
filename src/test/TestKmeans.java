package test;

import kmeans.Kmeans;
import model.Dataset;

import java.io.File;
import java.io.PrintStream;

/**
 * Created by Tomáš Honner on 8.5.2016.
 */
public class TestKmeans {

    private static final String fileIn = "D:\\IT\\Other\\SVI\\kmeanstest.txt";
    //private static final String fileIn = "\\\\VBOXSVR\\h_drive\\SVI\\Projects\\kmeans.txt";

    private static final String fileOut = "";
    private static final Double theta = 0.00001;
    private static final int numberOfIterations = 1000;

    public static void main(String[] args) throws Exception
    {
        Dataset dataset = new Dataset();
        File file = new File(fileIn);
        dataset.loadFromTextFile(file);
        PrintStream out = System.out;


        dataset = dataset.normalize(dataset);
        Kmeans kMeans = new Kmeans(dataset);
        dataset.print(out);
        kMeans.init(1, Dataset.getMinimum(dataset.getAllSamples()), Dataset.getMaximum(dataset.getAllSamples()), 3, out);
        kMeans.run(numberOfIterations,theta, out, new ScatterPlot(500,500));
        System.setOut(out);
        System.out.println("END.");
    }
}
