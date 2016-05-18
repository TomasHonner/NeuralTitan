package neuralNetwork;

import model.Dataset;
import model.Sample;
import test.ScatterPlot;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 7.3.2016.
 */
public abstract class Network {

    protected ArrayList<Layer> layers;

    /**
     * Default constructor for class Network
     */
    public Network()
    {
        layers = new ArrayList<>();
    }

    /**
     * Calculate output functions of neurons in layers
     */
    public void recall()
    {
        for (Layer l: layers
             ) {
            l.recall();
        }
    }

    public void recall(ArrayList<Double> list)
    {
        getInputLayer().setInputs(list);
        recall();
    }

    /**
     * Calculate output function for all added samples from new dataset and print new content
     * @param dataset Dataset to add
     * @param out print stream
     */
    public void recall(Dataset dataset, PrintStream out)
    {
        for (Sample sample : dataset.getAllSamples()) {
            getInputLayer().setInputs(sample.getInputs());
            for (Layer layer : layers) {
                layer.recall();
            }
            print(out);
        }
    }

    /**
     * Initialization of weights of neurons in layers
     * @param min Minimum for weight
     * @param max Maximum for weight
     * @param seed Seed for random generator
     */
    public void initWeights(Double min, Double max, long seed)
    {
        Random rand = new Random(seed);
        for (Layer l: layers
             ) {
            l.initWeights(min, max, rand);
        }
    }

    /**
     * Return input layer
     * @return Input layer
     */
    public LayerInput getInputLayer()
    {
        return (LayerInput) layers.get(0);
    }

    /**
     * Return output layer
     * @return Output layer
     */
    public Layer getOutputLayer()
    {
        return layers.get(layers.size()-1);
    }

    /**
     * Add layer to array of layers
     * @param layer Layer to add
     */
    public void addLayer(Layer layer)
    {
        layers.add(layer);
    }

    /**
     * Data print of objects Neuron in layers
     * @param out print straem
     */
    public void print(PrintStream out)
    {
        out.print(" Layers: "+System.lineSeparator());
        for (Layer l: layers
             ) {
            l.print(out);
        }
    }

    public abstract void initWeights(Double min, Double max, Random rand);

    public abstract void learn();

    public abstract Double learn(Dataset data, Double weightMin, Double weightMax, Random rand, Double eta, Double alpha, Double minError, int nIterations, ScatterPlot plot);

    public abstract Double learnEpoch(Dataset data, Double eta, Double alpha);

    public abstract void initLearning();

    public abstract void initEpoch();

    public abstract void endLearning();

    public abstract void endEpoch(Double eta, Double alpha);

    /**
     * Array of layers in network
     */
    public ArrayList<Layer> getLayers()
    {
        return layers;
    }
}
