package test;

import kmeans.Kmeans;
import model.Dataset;
import neuralNetwork.LayerInput;
import neuralNetwork.LayerPerceptron;
import neuralNetwork.LayerRBF;
import neuralNetwork.NetworkRBF;

import java.io.File;
import java.util.Locale;

/**
 * Created by Tomáš Honner on 13.5.2016.
 */
public class TestRBF {

    private static final String PATH_PREFIX = "D:\\IT\\Other\\SVI\\rbftest.txt";

    private static final int NUM_OF_EPOCHS = 5000;
    private static final double DISTANCE_TRESHOLD = 0.00001;
    private static final int NUM_OF_ITERATIONS = 1000;


    public static void main(String[] args) throws Exception {
        Dataset dataset = new Dataset();
        File file = new File(PATH_PREFIX);
        dataset.loadFromTextFile(file);//seed 100
        //data.loadFromCsv(PATH_PREFIX + "iris_2.csv");//seed 1
        dataset = Dataset.normalize(dataset);
        dataset.print(System.out);
        Kmeans kMeans = new Kmeans(dataset);
        kMeans.init(100,Dataset.getMinimum(dataset.getAllSamples()), Dataset.getMaximum(dataset.getAllSamples()),2, System.out);
        kMeans.run(NUM_OF_ITERATIONS, DISTANCE_TRESHOLD, System.out, new ScatterPlot(500,500));
        System.out.println("KMeans proccesing ended.");
        NetworkRBF rNN = new NetworkRBF();
        rNN.addLayer(new LayerInput(dataset.getNumberOfInputs()));
        rNN.addLayer(new LayerRBF(rNN, kMeans,kMeans.getNumberOfCentroids()));
        rNN.addLayer(new LayerPerceptron(dataset.getNumberOfOutputs(), rNN));
        rNN.learn(dataset, -0.3, 0.3, Long.valueOf(10), 0.8, 0.0, 0.0, NUM_OF_ITERATIONS, new ScatterPlot(500,500), true );
        System.out.println("Learning ended.");
        //rNN.printLearnedOutput(System.out, dataset, false);
    }
}
