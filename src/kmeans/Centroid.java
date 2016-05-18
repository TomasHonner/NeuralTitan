package kmeans;

import model.Sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Tomáš Honner on 2.5.2016.
 */
public class Centroid {

    /**
     * Sigma
     */
    private Double sigma; // odmocneni rozptyl

    /**
     * ArrayList of coordinates
     */
    private ArrayList<Double> coordinates;

    /**
     * ArrayList of samples
     */
    private ArrayList<Sample> samples;

    /**
     * Variable diameter for diameter of centroid
     */
    private Double diameter;

    /**
     * Default constructor of class Centroid
     */
    public Centroid()
    {
        coordinates = new ArrayList<>();
        samples = new ArrayList<>();
    }

    /**
     * Inicialization of class Centroid
     * @param numberOfCoordinates Number of coordinates
     * @param random Random with seed
     * @param min Minimum for Random
     * @param max Maximum for Random
     */
    public void init(int numberOfCoordinates, Random random, Double min, Double max)
    {
        for(int i = 0; i < numberOfCoordinates; i++)
        {
            coordinates.add(i, ((max - min) * random.nextDouble()) + min);
        }
    }

    /**
     * Clear all samples
     */
    public void clearSamples()
    {
        samples.clear();
    }

    /**
     * Getter for variable sigma
     * @return sigma
     */
    public Double getSigma() {
        return sigma;
    }

    /**
     * Setter for variable sigma
     * @param sigma Double sigma
     */
    public void setSigma(Double sigma) {
        this.sigma = sigma;
    }

    /**
     * Getter for variable coordinates
     * @return coordinates
     */
    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    /**
     * Setter for variable coordinates
     * @param coordinates ArrayList of coordinates
     */
    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Setter for variable coordinates
     * @param coordinates Array of coordinates
     */
    public void setCoordinates(Double[] coordinates) {
        ArrayList<Double> r = new ArrayList<Double>(Arrays.asList(coordinates));
        this.coordinates = r;
    }

    /**
     * Getter for variable samples
     * @return samples
     */
    public ArrayList<Sample> getSamples() {
        return samples;
    }

    /**
     * Setter for variable samples
     * @param samples ArrayList of samples
     */
    public void setSamples(ArrayList<Sample> samples) {
        this.samples = samples;
    }

    /**
     * Getter for variable diameter
     * @return  Diameter of centroid
     */
    public Double getDiameter() {
        return diameter;
    }

    /**
     * Setter for variable diameter
     * @param diameter Diameter of centroid
     */
    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }
}
