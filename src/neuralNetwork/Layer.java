package neuralNetwork;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 7.3.2016.
 */
public abstract class Layer {

    /**
     * Arrary of neurons
     */
    protected ArrayList<Neuron> neurons;

    /**
     * Variable for previous layer
     */
    protected Layer previousLayer = null;

    /**
     * Default constructor for class Layer
     */
    public Layer()
    {
        neurons = new ArrayList<>();
    }


    /**
     * Calculate output function of neurons in layer
     */
    public abstract void recall();

    /**
     * Initialization of weights of neurons in layer
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
     * Call function initLearning for all neurons in array neurons
     */
    public abstract void initLearning();

    /**
     * Call function initEpoch for all neurons in array neurons
     */
    public abstract void initEpoch();

    /**
     * Call function endLearning for all neurons in array neurons
     */
    public abstract void endLearning();

    /**
     * Call function endEpoch for all neurons in array neurons
     */
    public abstract void endEpoch(Double eta, Double alpha);

    /**
     * Call function learn for all neurons in array neurons
     */
    public abstract void learn();

    /**
     * Call function outputDelta for Percptron layer
     * @param d
     * @return
     */
    public abstract Double outputDelta(ArrayList<Double> d);

    /**
     * Call function backPropagate for Perceptron layer
     */
    public abstract void backPropagate();


    /**
     * Getter for array of neurons
     * @return Array of neurons
     */
    public ArrayList<Neuron> getNeurons()
    {
        return neurons;
    }

    /**
     * Setter for array of neurons
     * @param neurons Array of neurons
     */
    public void setNeurons(ArrayList<Neuron> neurons)
    {
        this.neurons = neurons;
    }


    /**
     * Getter for variable previousLayer
     * @return Previous layer
     */
    public Layer getPreviousLayer()
    {
        return previousLayer;
    }

    /**
     * Setter for variable previousLayer
     * @param previousLayer
     */
    public void setPreviousLayer(Layer previousLayer)
    {
        this.previousLayer = previousLayer;
    }
}
