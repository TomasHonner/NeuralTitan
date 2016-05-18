package neuralNetwork;

import model.Dataset;
import model.Sample;
import test.ScatterPlot;

import java.io.PrintStream;
import java.util.Random;

/**
 * Created by Tomáš Honner on 8.3.2016.
 */
public class NetworkPerceptron extends Network {

    /**
     * Default constructor for class NetworkPerceptron
     */
    public NetworkPerceptron()
    {

    }

    /**
     * Initialization of weights of neurons in layer
     * @param min Minimum for weight
     * @param max Maximum for weight
     * @param rand Instance of random generator
     */
    public void initWeights(Double min, Double max, Random rand)
    {
        for (Layer l: layers
             ) {
            l.initWeights(min, max, rand);
        }
    }

    @Override
    public void learn()
    {
        for(int i = 1; i<layers.size(); i++)
        {
            layers.get(i).learn();
        }
    }

    @Override
    public Double learn(Dataset data, Double weightMin, Double weightMax, Random rand, Double eta, Double alpha, Double minError, int nIterations, ScatterPlot plot)
    {
        initWeights(weightMin, weightMax, rand);
        initLearning();
        Double err = Double.valueOf(-1);
        for(int i = 0; i<nIterations; i++)
        {
            err = learnEpoch(data, eta, alpha);
            if(err <= minError)
            {
                break;
            }
            if(i%10 == 0 && plot != null)
            {
                plot.plot(this, data);
            }
        }
        endLearning();
        return err;
    }

    @Override
    public Double learnEpoch(Dataset data, Double eta, Double alpha)
    {
        Double err = Double.valueOf(0);
        initEpoch();
        int counter = 1;
        for (Sample sample: data.samples
                ) {
            ((LayerInput)layers.get(0)).setInputs(sample.getInputs());
            counter++;
            recall();
            err += (layers.get(layers.size()-1)).outputDelta(sample.getOutputs());
            backPropagate();
            learn();
        }
        endEpoch(eta, alpha);
        return err / data.samples.size();
    }

    @Override
    public void initEpoch()
    {
        for (int i = 1; i<layers.size(); i++)
        {
            layers.get(i).initEpoch();
        }
    }

    /**
     *
     * @param eta Speed of learning
     * @param alpha setrvacnost
     */
    @Override
    public void endEpoch(Double eta, Double alpha)
    {
        for (int i = 1; i<layers.size(); i++)
        {
            layers.get(i).endEpoch(eta, alpha);
        }
    }

    public void backPropagate()
    {
        for(int i = layers.size()-1; i > 1; i--)
        {
            layers.get(i).backPropagate();
        }
    }


    public void initLearning()
    {
        for (Layer layer: layers
             ) {
            layer.initLearning();
        }
    }

    public void endLearning()
    {
        for (Layer layer: layers
             ) {
            layer.endLearning();
        }
    }

    /* funkci adaptujte na vaši implementaci. Funkce obsahuje jen výpis stavu sítě. 
    Některé výpisy jako např. vstupů, eta, alfa, epochy, iterace jsou umístěny
    hlavním programu nebo funkci learn.
    */
    public void printForTesting(PrintStream out) throws Exception {

        if (layers.size() != 3) {
            throw new Exception("incompatible network for testing");
        }
        LayerPerceptron output = (LayerPerceptron) layers.get(2);
        LayerPerceptron hidden = (LayerPerceptron) layers.get(1);
        LayerInput input = (LayerInput) layers.get(0);
        if (input.neurons.size() != 2
                || hidden.neurons.size() != 2
                || output.neurons.size() != 1) {
            throw new Exception("incompatible network for testing");
        }
        NeuronPerceptron pn = (NeuronPerceptron) output.neurons.get(0);
        out.printf("%20.15f ; output: y \n", pn.y);
        out.printf("%20.15f ; output: threshold\n", pn.getTheta());
        for (int j = 0; j < 2; j++) {
            out.printf("%20.15f ; output: w[%d]\n", pn.getW().get(j), j);
        }
        out.printf("%20.15f ; output: delta\n", pn.delta);
        out.printf("%20.15f ; output: delta threshold\n", pn.deltaTheta);
        for (int j = 0; j < 2; j++) {
            out.printf("%20.15f ; output: delta w[%d]\n", pn.getDeltaW().get(j), j);
        }
        for (int j = 0; j < 2; j++) {
            out.printf("%20.15f ; hidden: y[%d]\n",
                    hidden.neurons.get(j).y, j);
        }
        for (int j = 0; j < 2; j++) {
            out.printf("%20.15f ; hidden: threshold [%d]\n",
                    ((NeuronPerceptron) hidden.neurons.get(j)).getTheta(), j);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                out.printf("%20.15f ; hidden: w[%d][%d]\n",
                        ((NeuronPerceptron) hidden.neurons.get(i)).getW().get(j), i, j);
            }
        }
        for (int j = 0; j < 2; j++) {
            out.printf("%20.15f ; hidden: delta [%d]\n",
                    ((NeuronPerceptron) hidden.neurons.get(j)).delta, j);
        }
        for (int j = 0; j < 2; j++) {
            out.printf("%20.15f ; hidden: delta threshold [%d]\n",
                    ((NeuronPerceptron) hidden.neurons.get(j)).deltaTheta, j);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                out.printf("%20.15f ; hidden: delta w[%d][%d]\n",
                        ((NeuronPerceptron) hidden.neurons.get(i)).getDeltaW().get(j), i, j);
            }
        }
        for (int j = 0; j < 2; j++) {
            out.printf("%20.15f ; hidden: old delta threshold [%d]\n",
                    ((NeuronPerceptron) hidden.neurons.get(j)).oldDeltaTheta, j);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                out.printf("%20.15f ; hidden: old delta w[%d][%d]\n",
                        ((NeuronPerceptron) hidden.neurons.get(i)).getOldDeltaW().get(j), i, j);
            }
        }
    }
}
