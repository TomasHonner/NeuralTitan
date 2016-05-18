package neuralNetwork;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 7.3.2016.
 */
public abstract class Neuron {

    protected Double y;

    protected ArrayList<Neuron> inputs;

    public Double delta; // chyba na jeden neuron

    protected ArrayList<Double> deltaW; // pozadovana zmena vahy

    public Double deltaTheta; // pozadovana zmena prahu

    protected ArrayList<Double> oldDeltaW; // predchozi hodnota deltaW pro ucel setrvacnosti

    public Double oldDeltaTheta;

    public static final Double SIGMA = 1.0;

    public static final Double OMEGA = 1.0;

    /**
     * Default constructor of class Neuron
     */
    public Neuron()
    {
        this.y = Double.valueOf(0);
    }

    /**
     * Calculate output function of neuron
     */
    public abstract void recall();

    /**
     * Initialization of weights of neuron
     * @param min Minimum for weight
     * @param max Maximum for weight
     * @param rand Instance of random generator
     */
    public abstract void initWeights(Double min, Double max, Random rand);

    /**
     * Data print for object NeuronPerceptron
     * @param out print stream
     */
    public abstract void print(PrintStream out);

    /**
     * Initialization of variables on start of learning
     */
    public abstract void initLearning();

    /**
     * Initialization of variables for epoch
     */
    public abstract void initEpoch();

    /**
     * Ends epoch
     * @param eta
     * @param alpha
     */
    public abstract void endEpoch(Double eta, Double alpha);

    /**
     * Ends learning
     */
    public abstract void endLearning();

    /**
     * Learn of neural network
     */
    public abstract void learn();


    /**
     * Getter for output function of neuron
     * @return Output function of neuron
     */
    public Double getY()
    {
        return y;
    }

    /**
     * Setter for output function of neuron
     * @param y Output function of neuron
     */
    public void setY(Double y)
    {
        this.y = y;
    }

    /**
     * Getter for array of inputs
     * @return Array of inputs
     */
    public ArrayList<Neuron> getInputs()
    {
        return inputs;
    }

    /**
     * Setter for array of inputs
     * @param inputs Array of inputs
     */
    public void setInputs(ArrayList<Neuron> inputs)
    {
        this.inputs = inputs;
    }

    public ArrayList<Double> getDeltaW()
    {
        return deltaW;
    }

    public ArrayList<Double> getOldDeltaW()
    {
        return oldDeltaW;
    }
}
