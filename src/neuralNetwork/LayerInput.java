package neuralNetwork;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 8.3.2016.
 */
public class LayerInput extends Layer {

    /**
     * Default constructor for class LayerInput
     */
    public LayerInput()
    {

    }

    /**
     * Constructor for class LayerInput
     * @param size Size of layer
     */
    public LayerInput(int size)
    {
        neurons = new ArrayList<>();
        for (int i = 0; i<size; i++)
        {
            neurons.add(new NeuronInput());
        }
    }

    /**
     * Constructor for class LayerInput
     * @param inputs Inputs for a layer
     */
    public LayerInput(ArrayList<Double> inputs)
    {
        neurons = new ArrayList<>();
        for (Double d:inputs
             ) {
            neurons.add(new NeuronInput(d));
        }
    }

    /**
     * Set output function of neurons in array neurons
     * @param inputs Inputs for setting output function
     */
    public void setInputs(ArrayList<Double> inputs)
    {
        for (int i = 0; i<neurons.size(); i++)
        {
            neurons.get(i).setY(inputs.get(i));
        }
    }

    /**
     * Calculate output function of neurons in layer
     */
    @Override
    public void recall()
    {

    }

    /**
     * Initialization of weights of neurons in layer
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

    @Override
    public void initLearning()
    {

    }

    @Override
    public void initEpoch()
    {

    }

    @Override
    public void endLearning()
    {

    }

    @Override
    public void endEpoch(Double eta, Double alpha)
    {

    }

    @Override
    public void learn() {

    }

    @Override
    public Double outputDelta(ArrayList<Double> d)
    {
        return null;
    }

    @Override
    public void backPropagate()
    {

    }
}
