package neuralNetwork;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 8.3.2016.
 */
public class NeuronRBF extends Neuron {

    /**
     * Array of weights
     */
    private ArrayList<Double> w; // souradnice centroidu

    /**
     * Variable for treshold
     */
    private Double treshold;


    /**
     * Variable for sigma
     */
    private Double sigma;


    /**
     * Default contructor for class NeuronRBF
     */
    public NeuronRBF()
    {

    }

    /**
     * Constructor for class NeuronRBF
     * @param inputs Array of inputs for neuron
     */
    public NeuronRBF(ArrayList<Neuron> inputs)
    {
        setInputs(inputs);
    }

    public void init(ArrayList<Neuron> inputs, ArrayList<Double> coordinates, Double diameter)
    {
        super.setInputs(inputs);
        this.w = coordinates;
        this.sigma = Math.sqrt(diameter);
    }

    /**
     * Calculate output function of neuron
     */
    @Override
    public void recall()
    {
        Double sum = 0.0;
        Double temp = 0.0;
        for (int i = 0; i < inputs.size() ; i++) {
            temp = inputs.get(i).getY() - w.get(i);
            sum += temp * temp;
        }
        sum = Math.sqrt(sum);
        y = Math.exp(-((sum * sum) / (2 * (sigma * sigma))));
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
     * Data print of object NeuronRBF
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
     * @param eta Speed of learning
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

    /**
     * Set array of inputs in parent class
     * @param inputs Array of inputs
     */
    public void setInputs(ArrayList<Neuron> inputs)
    {
        super.setInputs(inputs);
        w = new ArrayList<>();
    }

    /**
     * Getter for array w
     * @return Array of weights
     */
    public ArrayList<Double> getW()
    {
        return w;
    }

    /**
     * Setter for array w
     * @param w Array of wieghts
     */
    public void setW(ArrayList<Double> w)
    {
        this.w = w;
    }

    /**
     * Getter for variable treshold
     * @return Treshold
     */
    public Double getTreshold()
    {
        return treshold;
    }

    /**
     * Setter for variable treshold
     * @param treshold Treshold
     */
    public void setTreshold(Double treshold)
    {
        this.treshold = treshold;
    }

    /**
     * Getter for variable sigma
     * @return Value of sigma
     */
    public Double getSigma()
    {
        return sigma;
    }

    /**
     * Setter for variable sigma
     * @param sigma
     */
    public void setSigma(Double sigma)
    {
        this.sigma = sigma;
    }
}
