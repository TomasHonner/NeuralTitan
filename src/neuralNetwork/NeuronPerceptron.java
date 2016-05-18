package neuralNetwork;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Tomáš Honner on 8.3.2016.
 */
public class NeuronPerceptron extends Neuron {

    /**
     * Array of weights
     */
    private ArrayList<Double> w;

    /**
     * Variable theta for threshold
     */
    private Double theta;

    /**
     * Default constructor for class NeuronPerceptron
     */
    public NeuronPerceptron()
    {

    }

    /**
     * Constructor for class NeuronPerceptron
     * @param inputs Array of inputs for parent class
     */
    public NeuronPerceptron(ArrayList<Neuron> inputs)
    {
        setInputs(inputs);
        for (int i = 0; i<inputs.size(); i++)
        {
            w.add(0.0);
        }
    }

    /**
     * Calculate output function of neuron
     */
    @Override
    public void recall()
    {
        Double sum = theta;
        int index = 0;
        for (Neuron n : inputs
             ) {
            sum += w.get(index) * n.getY();
            index++;
        }
        y = 1.0 / (1 + Math.exp(-SIGMA *sum));
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
        for (int i = 0; i<w.size(); i++)
        {
            w.set(i,(max - min)*rand.nextDouble()+min);
        }
        setTreshold((max - min)*rand.nextDouble()+min);
    }

    /**
     * Data print for object NeuronPerceptron
     * @param out print stream
     */
    @Override
    public void print(PrintStream out)
    {
        out.print("Perceptron neuron :");
        for (Double d: w
              ) {
            out.print(String.format(Locale.ENGLISH," Weights: %15.8f", d));
        }
        out.print(String.format(Locale.ENGLISH, " treshold =%15.8f  y =%15.8f", theta, y));
        out.println();
    }

    /**
     * Initialization of variables on start of learning
     */
    @Override
    public void initLearning()
    {
        delta = Double.valueOf(0);
        deltaTheta = Double.valueOf(0);
        oldDeltaTheta = Double.valueOf(0);
        deltaW = new ArrayList<>();
        oldDeltaW = new ArrayList<>();
        for (int i = 0; i<inputs.size(); i++)
        {
            getDeltaW().add(Double.valueOf(0));
            getOldDeltaW().add(Double.valueOf(0));
        }
    }

    /**
     * Initialization of variables for epoch
     */
    @Override
    public void initEpoch()
    {
        deltaTheta = Double.valueOf(0);
        deltaW = new ArrayList<>();
        for (int i = 0; i<inputs.size(); i++)
        {
            getDeltaW().add(Double.valueOf(0));
        }
    }

    /**
     * Ends epoch
     * @param eta
     * @param alpha
     */
    @Override
    public void endEpoch(Double eta, Double alpha)
    {
        for (int i = 0; i<w.size(); i++)
        {
            Double dw = eta * getDeltaW().get(i) + alpha * getOldDeltaW().get(i);
            Double temp = w.get(i) + dw;
            w.set(i,temp);
            getOldDeltaW().set(i, dw);
        }
        oldDeltaTheta = eta * deltaTheta + alpha * oldDeltaTheta;
        theta += oldDeltaTheta;
    }

    /**
     * Ends learning
     */
    @Override
    public void endLearning()
    {
        deltaW = null;
        oldDeltaW = null;
    }

    /**
     * Learn of neural network
     */
    @Override
    public void learn()
    {
        for (int i = 0; i<w.size(); i++)
        {
            Double temp = getDeltaW().get(i) + (delta * inputs.get(i).y);
            getDeltaW().set(i, temp);
        }
        deltaTheta += delta;
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
     * Getter for array of weights
     * @return Array w
     */
    public ArrayList<Double> getW()
    {
        return w;
    }

    /**
     * Setter for array of weights
     * @param w Array of weights
     */
    public void setW(ArrayList<Double> w)
    {
        this.w = w;
    }

    /**
     * Getter for theta treshold
     * @return Theta
     */
    public Double getTheta()
    {
        return theta;
    }

    /**
     * Setter for theta
     * @param theta theta
     */
    public void setTreshold(Double theta)
    {
        this.theta = theta;
    }
}
