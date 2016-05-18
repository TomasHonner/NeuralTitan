package neuralNetwork;

import java.io.PrintStream;
import java.util.Random;

/**
 * Created by Tomáš Honner on 8.3.2016.
 */
public class NeuronInput extends Neuron {

    /**
     * Default constructor for class NeuronInput
     */
    public NeuronInput()
    {

    }

    /**
     * Constructor for class NeuronInput
     * @param inputValue Initialization value for output function of neuron
     */
    public NeuronInput(Double inputValue)
    {
        y = inputValue;
    }


    /**
     * Calculate output function of neuron
     */
    @Override
    public void recall()
    {

    }

    /**
     * Initialization of weights of neuron
     * @param min Minimum for weight
     * @param max Maximum for weight
     * @param rand Instance of random generator
     */
    @Override
    public void initWeights(Double min, Double max, Random rand)
    {

    }

    /**
     * Data print for object NeuronPerceptron
     * @param out print stream
     */
    @Override
    public void print(PrintStream out)
    {

    }

    /**
     * Initialization of variables on start of learning
     */
    @Override
    public void initLearning() {

    }

    /**
     * Initialization of variables for epoch
     */
    @Override
    public void initEpoch() {

    }

    /**
     * Ends epoch
     *
     * @param eta
     * @param alpha
     */
    @Override
    public void endEpoch(Double eta, Double alpha) {

    }

    /**
     * Ends learning
     */
    @Override
    public void endLearning() {

    }

    /**
     * Learn of neural network
     */
    @Override
    public void learn() {

    }
}
