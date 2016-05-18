package neuralNetwork;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 8.3.2016.
 */
public class LayerPerceptron extends Layer {

    /**
     * Storage for previous layer
     */
    private Layer previousLayer;

    /**
     * Default constructor for class LayerPerceptron
     */
    public LayerPerceptron()
    {

    }

    /**
     * Constructor for class LayerPerceptron
     * @param size Size of a layer
     * @param inputs Inputs for a new layer
     */
    public LayerPerceptron(int size, Layer inputs)
    {
        for (int i = 0; i<size; i++)
        {
            neurons.add(new NeuronPerceptron());
            neurons.get(i).setInputs(inputs.getNeurons());
        }
    }

    /**
     * Constructor for class LayerPerceptron
     * @param size Size of layer
     * @param network Network with inputs for a layer
     */
    public LayerPerceptron(int size, Network network)
    {
        neurons = new ArrayList<>();
        for (int i = 0; i<size; i++)
        {
            neurons.add(new NeuronPerceptron(network.getOutputLayer().getNeurons()));
        }
        previousLayer = network.getOutputLayer();
    }

    /**
     * Calculate output function of neurons in layer
     */
    @Override
    public void recall()
    {
        for (Neuron n: neurons
             ) {
            n.recall();
        }
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
        for (Neuron n: neurons
             ) {
            n.initWeights(min, max, rand);
        }
    }

    /**
     * Data print for object NeuronPerceptron
     * @param out print stream
     */
    @Override
    public void print(PrintStream out)
    {
        out.print(" Neurons :"+System.lineSeparator());
        for (Neuron n : neurons
             ) {
            n.print(out);
        }
    }

    /**
     * Call function initLearning for all neurons in array neurons
     */
    @Override
    public void initLearning()
    {
        for (Neuron neuron: neurons
             ) {
            neuron.initLearning();
        }
    }

    /**
     * Call function initEpoch for all neurons in array neurons
     */
    @Override
    public void initEpoch()
    {
        for (Neuron neuron: neurons
             ) {
            neuron.initEpoch();
        }
    }

    /**
     * Call function endLearning for all neurons in array neurons
     */
    @Override
    public void endLearning()
    {
        for (Neuron neuron: neurons
             ) {
            neuron.endLearning();
        }
    }

    /**
     * Call function endEpoch for all neurons in array neurons
     */
    @Override
    public void endEpoch(Double eta, Double alpha)
    {
        for (Neuron neuron: neurons
             ) {
            neuron.endEpoch(eta,alpha);
        }
    }

    /**
     * Call function learn for all neurons in array neurons
     */
    @Override
    public void learn()
    {
        for (Neuron neuron: neurons
                ) {
            neuron.learn();
        }
    }

    /**
     *  Calculate total error in layer
     * @param d Outputs from sample
     * @return Total error
     */
    public Double outputDelta(ArrayList<Double> d)
    {
        Double err = Double.valueOf(0);
        for (int i = 0; i<neurons.size(); i++)
        {
            Double diff = d.get(i) - neurons.get(i).y;
            Neuron n = neurons.get(i);
            n.delta = n.y*(1-n.y)*diff;
            err = diff * diff;
        }
        return err/neurons.size();
    }

    /**
     * Implementation of Back Propagation algorytm
     */
    public void backPropagate()
    {
        for (int j = 0; j<previousLayer.neurons.size(); j++)
        {
            Double ds = Double.valueOf(0);
            for (int i = 0; i<neurons.size(); i++)
            {
                NeuronPerceptron n = (NeuronPerceptron) neurons.get(i);
                ds += n.delta * n.getW().get(j);
            }
            NeuronPerceptron in = (NeuronPerceptron) previousLayer.neurons.get(j);
            in.delta = in.y*(1-in.y)*ds;
        }
    }
}
