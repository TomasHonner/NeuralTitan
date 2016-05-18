package neuralNetwork;

import kmeans.Kmeans;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 8.3.2016.
 */
public class LayerRBF extends Layer {

    /**
     * Default constructor for class LayerRBF
     */
    public LayerRBF()
    {
        neurons = new ArrayList<>();
    }


    /**
     * Constructor with parameters for class LayerRBF
     * @param network
     * @param kmeans
     * @param layerSize Number of layers
     */
    public LayerRBF(Network network, Kmeans kmeans, int layerSize)
    {
        neurons = new ArrayList<>();
        for (int i = 0; i < layerSize ; i++)
        {
            neurons.add(i, new NeuronRBF());
        }
        previousLayer = network.getOutputLayer();
        for (int i = 0; i < neurons.size() ; i++) {
            NeuronRBF temp = (NeuronRBF) neurons.get(i);
            temp.init(network.getOutputLayer().getNeurons(), kmeans.getCentroids().get(i).getCoordinates(), kmeans.getCentroids().get(i).getDiameter());
            neurons.set(i, temp);
        }
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
        out.print(" Neurons :");
        for (Neuron n : neurons
                ) {
            n.print(out);
        }
    }

    /**
     * Call function initLearning for all neurons in array neurons
     */
    @Override
    public void initLearning() {

    }

    /**
     * Call function initEpoch for all neurons in array neurons
     */
    @Override
    public void initEpoch() {

    }

    /**
     * Call function endLearning for all neurons in array neurons
     */
    @Override
    public void endLearning() {

    }

    /**
     * Call function endEpoch for all neurons in array neurons
     *
     * @param eta Speed of learning
     * @param alpha
     */
    @Override
    public void endEpoch(Double eta, Double alpha) {

    }

    /**
     * Call function learn for all neurons in array neurons
     */
    @Override
    public void learn() {

    }

    @Override
    public Double outputDelta(ArrayList<Double> d) {
        return null;
    }

    @Override
    public void backPropagate() {

    }
}
