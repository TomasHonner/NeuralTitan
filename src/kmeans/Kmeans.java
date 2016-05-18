package kmeans;

import model.Dataset;
import model.Sample;
import test.ScatterPlot;

import java.io.PrintStream;
import java.util.*;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by Tomáš Honner on 2.5.2016.
 */
public class Kmeans {

    /**
     * ArrayList of centroids
     */
    private ArrayList<Centroid> centroids;

    /**
     * Random generator
     */
    private Random random;

    /**
     * Dataset
     */
    private Dataset dataset;



    /**
     * Default constructor of class Kmeans
     */
    public Kmeans(Dataset dataset)
    {
        centroids = new ArrayList<>();
        this.dataset = dataset;
    }

    /**
     * Initialization of class Kmeans
     * @param seed Seed for Random
     * @param rMin Minimum for Random
     * @param rMax Maximum for Random
     * @param numberOfCentroids Number of centroids
     * @param out PrintStream for print
     */
    public void init(int seed, Double rMin, Double rMax, int numberOfCentroids, PrintStream out)
    {
        random = new Random(seed);

        for (int i = 0; i < numberOfCentroids; i++)
        {
            centroids.add(new Centroid());
        }

        for (int i = 0; i < centroids.size(); i++)
        {
            centroids.get(i).init(dataset.getNumberOfInputs(), random, rMin, rMax);
            out.println("Centroid number: "+i);
            for (int j = 0; j < dataset.getNumberOfInputs(); j++)
            {
                out.print("coordinate " + j + ": " + centroids.get(i).getCoordinates().get(j) + " ");
            }
            out.println();
        }
    }

    /**
     * Runs Kmeans algorytm
     * @param numberOfIterations Number of iterations
     * @param distanceTheta Distance
     * @param out PrintStream for print
     * @param plot Instance of class ScatterPlot
     */
    public void run(int numberOfIterations, Double distanceTheta, PrintStream out, ScatterPlot plot)
    {
        int counter = 0;
        while (calculateNewCentroids(distanceTheta, out) && (counter <= numberOfIterations)) {
            if (out != null) {
                out.println("Iteration:  " + counter);
                counter++;
            }
            if ((plot != null) && (counter % 10 == 0)) {
                plot.plot(this, dataset);
            }
        }


        for (Centroid centroid: centroids
             ) {
            Double diameter = Double.valueOf(0);
            Double helpVariable = Double.valueOf(0);
            for (int i = 0; i < centroid.getSamples().size() ; i++)
            {
                helpVariable = calculateDistance(centroid.getCoordinates(), centroid.getSamples().get(i).getInputs());
                diameter += helpVariable * helpVariable;
            }
            if (centroid.getSamples().size() != 0 )
            {
                diameter /= centroid.getSamples().size();
            }
            centroid.setDiameter(diameter);
        }

        out.println("Diameters:");
        for (int i = 0; i < centroids.size(); i++) {
            out.print("centroid " + i + ": diameter ");
            out.printf(Locale.ENGLISH, "%1.10f", centroids.get(i).getDiameter());
            out.println();
        }
        if (plot != null) {
            plot.plot(this, dataset);
        }
    }

//    private boolean calculateNewCentroids(Double distanceTheta, PrintStream out)
//    {
//        addDatasetSamplesToCentroid(out);
//        ArrayList<ArrayList<Double>> newCoordinates = new ArrayList<ArrayList<Double>>();
//        int counter;
//        for (Centroid cent: centroids
//             ) {
//            int count = dataset.getNumberOfInputs();
//            ArrayList<Double> result = new ArrayList<>();
//            for (int i = 0; i < count ; i++)
//            {
//                result.add(i,0.0);
//            }
//
//            for (int i = 0; i < result.size() ; i++)
//            {
//                for (Sample sample: cent.getSamples()
//                     ) {
//                    System.out.println(sample.getInputs().get(0));
//                    result.set(i,result.get(i) + sample.getInputs().get(i) );
//                }
//            }
//        }
//        if(!checkMinimumDistance(distanceTheta, newCoordinates)) {
//            for (int i = 0; i < newCoordinates.size(); i++) {
//                centroids.get(i).setCoordinates(newCoordinates.get(i));
//                if (out != null) {
//                    out.println("New Centroid " + i);
//                    for (int j = 0; j < dataset.getNumberOfInputs(); j++) {
//                        out.print("coord " + j + ": " + centroids.get(i).getCoordinates().get(j) + " ");
//                    }
//                    out.println();
//                }
//            }
//            return true;
//        }
//        return false;
//    }


    private boolean calculateNewCentroids(Double distanceTheta, PrintStream out)
    {
        addDatasetSamplesToCentroid(out);
        Double[][] newCoords = new Double[centroids.size()][];
        int counter = 0;
        for (Centroid c : centroids) {
            int count = dataset.getNumberOfInputs();
            Double[] res = new Double[count];
            for(int i = 0; i< res.length; i++)
            {
                res[i] = Double.valueOf(0);
            }
            for (int i = 0; i < res.length; i++) {
                for (Sample s : c.getSamples()) {
                    System.out.println(s.getInputs().get(0));
                    res[i] += s.getInputs().get(i);
                }
                if (!c.getSamples().isEmpty()) res[i] = res[i] / c.getSamples().size();
                else res[i] = c.getCoordinates().get(i);
            }
            newCoords[counter] = res;
            counter++;
        }
        if(!checkMinimumDistance(distanceTheta, newCoords)) {
            for (int i = 0; i < newCoords.length; i++) {
                centroids.get(i).setCoordinates(newCoords[i]);
                if (out != null) {
                    out.println("New Centroid " + i);
                    for (int j = 0; j < dataset.getNumberOfInputs(); j++) {
                        out.print("coord " + j + ": " + centroids.get(i).getCoordinates().get(j) + " ");
                    }
                    out.println();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Add samples coordinates from dataset to centroid coordinates
     * @param out PrintStream for print
     */
    private void addDatasetSamplesToCentroid(PrintStream out)
    {
        clearCentroidsSamples();
        for (Sample sample: dataset.getAllSamples()
             ) {
            Double minimalDistance = Double.MAX_VALUE;
            Centroid minimalCentroid = null;

            for (Centroid centroid: centroids
                 ) {
                Double distance = calculateDistance(sample.getInputs(), centroid.getCoordinates());
                if (distance < minimalDistance)
                {
                    minimalDistance = distance;
                    minimalCentroid = centroid;
                }
            }
            minimalCentroid.getSamples().add(sample);
        }

        // printing

        for (int i = 0; i < centroids.size() ; i++)
        {
            out.println("Centroid " + i + " samples:");
            for (int j = 0; j < centroids.get(i).getSamples().size(); j++)
            {
                out.print("sample " + j + ": ");
                for (int k = 0; k < dataset.getNumberOfInputs() ; k++)
                {
                    out.print("coords " + k + ": " + centroids.get(i).getSamples().get(j).getInputs().get(k) + " ");
                }
                out.println();
            }
        }

    }

    /**
     * Checking minimum distance with new coordinates
     * @param distanceTheta Minimal distance
     * @param newCoordinates New coordinates
     * @return True or false
     */
    private boolean checkMinimumDistance(Double distanceTheta, Double[][] newCoordinates ) //ArrayList<ArrayList<Double>> newCoordinates
    {
//        for (int i = 0; i < newCoordinates.size() ; i++)
//        {
//            if (calculateDistance(newCoordinates, centroids.get(i).getCoordinates()) < distanceTheta)
//            {
//                return true;
//            }
//            else
//            {
//                return false;
//            }
//        }

        for (int i = 0; i < newCoordinates.length; i++) {
            if (calculateDistance(new ArrayList<Double>(Arrays.asList(newCoordinates[i])), centroids.get(i).getCoordinates()) < distanceTheta) return true;
        }
        return false;
    }

    /**
     * Clear ArrayList of centroids
     */
    private void clearCentroids()
    {
        centroids.clear();
    }

    /**
     * Clear samples of centroids in ArrayList centroids
     */
    private void clearCentroidsSamples()
    {
        for (Centroid centroid: centroids
             ) {
            centroid.clearSamples();
        }
    }

    /**
     * Get number of centroids
     * @return Number of centroids
     */
    public int getNumberOfCentroids()
    {
        return centroids.size();
    }

    //TODO dopsat comment
    /**
     * Calculate euclid distance between
     * @param first
     * @param second
     * @return
     */
    private Double calculateDistance(ArrayList<Double> first, ArrayList<Double> second)
    {
        Double distance = Double.valueOf(0);
        for (int i = 0; i < first.size(); i++) {
            Double res = first.get(i) - second.get(i);
            distance += res * res;
        }
        return Math.sqrt(distance);
    }

    //TODO dopsat komment
    /**
     *
     * @param dAL sou5adnicov8 reprezentace scatter plotu
     * @return Number between 0 and 7 as calor for ScatterPlot
     */
    public int classify(ArrayList<Double> dAL)
    {
        Double minimalDistance = Double.MAX_VALUE;
        int minimalIndex = -1;
        for (int i = 0; i < centroids.size(); i++)
        {
            Double distance = calculateDistance(dAL, centroids.get(i).getCoordinates());
            if (distance < minimalDistance)
            {
                minimalDistance = distance;
                minimalIndex = i;
            }
        }
        return minimalIndex;
    }

    /**
     * Getter for ArrayList of Centroids
     * @return ArrayList of Centroids
     */
    public ArrayList<Centroid> getCentroids() {
        return centroids;
    }

    /**
     * Setter for ArrayList of Centroids
     * @param centroids ArrayList of Centroids
     */
    public void setCentroids(ArrayList<Centroid> centroids) {
        this.centroids = centroids;
    }

}

