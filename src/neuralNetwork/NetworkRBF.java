package neuralNetwork;

import model.Dataset;
import model.Sample;
import test.ScatterPlot;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Tomáš Honner on 8.3.2016.
 */
public class NetworkRBF extends Network {

    /**
     * Default constructor for class NetworkRBF
     */
    public NetworkRBF()
    {

    }

    /**
     * Initialization of weights of neurons in layers
     * @param min Minimum for weight
     * @param max Maximum for weight
     * @param rand Random for random generator
     */
    @Override
    public void initWeights(Double min, Double max, Random rand) {

    }


    @Override
    public void learn()
    {
        for (Layer layer: layers
             ) {
            layer.learn();
        }
    }

    @Override
    public Double learn(Dataset data, Double weightMin, Double weightMax, Random rand, Double eta, Double alpha, Double minError, int nIterations, ScatterPlot plot) {
        return null;
    }

    public Double learn(Dataset data, Double weightMin, Double weightMax, Long seed, Double eta, Double alpha, Double minError, int nIterations, ScatterPlot plot, boolean plotOutput)
    {
        initWeights(weightMin, weightMax, seed);
        initLearning();
        Double err = Double.MIN_VALUE;
        for (int i = 0; i < nIterations ; i++)
        {
            err = learnEpoch(data, eta, alpha);
            if ((plot != null) && (i % 100 == 0))
                plot.plot(this, data, plotOutput);
            if (err <= minError)
                break;
        }
        endLearning();
        return err;
    }

    @Override
    public Double learnEpoch(Dataset data, Double eta, Double alpha) {
        Double error = Double.valueOf(0);
        initEpoch();
        for (Sample sample: data.getAllSamples()
             ) {
            getInputLayer().setInputs(sample.getInputs());
            recall();
            error += ((LayerPerceptron) getOutputLayer()).outputDelta(sample.getOutputs());
            learn();
        }
        endEpoch(eta, alpha);
        return error / data.size();
    }

    @Override
    public void initEpoch() {
        for (Layer layer: layers
             ) {
            layer.initEpoch();
        }
    }

    @Override
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

    @Override
    public void endEpoch(Double eta, Double alpha) {
        for (Layer layer: layers
             ) {
            layer.endEpoch(eta, alpha);
        }
    }

    public static final String SEPARATOR_H = "";
    public static final String SEPARATOR_VL = "";
    public static final String SEPARATOR_VM = "";
    public static final String SEPARATOR_VR = "";
    public static final String SEPARATOR_HM = "";

    public void printLearnedOutput(PrintStream out, Dataset samples, boolean round) {
        int charsInput = 7 * samples.get(0).getInputs().size();
        int charsInputLeading = (charsInput - 5) / 2;
        int charsInputTrailing = charsInput - charsInputLeading - 5;
        int charsOutput = 7 * samples.get(0).getOutputs().size();
        int charsOutputLeading = (charsOutput - 6) / 2;
        int charsOutputTrailing = charsOutput - charsOutputLeading - 6;
        int charsLearnedLeading = (charsOutput - 7) / 2;
        int charsLearnedTrailing = charsOutput - charsOutputLeading - 7;
        int charsTotal = charsInput + (2 * charsOutput) + 9 + 8;
        printSeparator(out, SEPARATOR_H, charsTotal);
        out.print(SEPARATOR_VL);
        out.printf("%" + charsInputLeading + "s%5s%" + charsInputTrailing + "s", "", "INPUT", "");
        out.print(SEPARATOR_VM);
        if (charsOutputLeading == 0) {
            charsOutputLeading++;
            charsOutputTrailing--;
            out.printf("%" + charsOutputLeading + "s%6s", "", "OUTPUT");
        } else
            out.printf("%" + charsOutputLeading + "s%6s%" + charsOutputTrailing + "s", "", "OUTPUT", "");
        out.print(SEPARATOR_VR);
        if (charsLearnedLeading == 0) {
            charsLearnedLeading++;
            charsLearnedTrailing--;
            out.printf("%" + charsLearnedLeading + "s%7s", "", "LEARNED");
        } else
            out.printf("%" + charsLearnedLeading + "s%7s%" + charsLearnedTrailing + "s", "", "LEARNED", "");
        out.print(SEPARATOR_VR);
        out.printf("%" + 1 + "s%5s", "", "ERROR", "");
        out.print(SEPARATOR_VR);
        out.println();
        out.print(SEPARATOR_VL);
        printHeader(out, samples.get(0).getInputs().size(), "X");
        out.print(SEPARATOR_VM);
        printHeader(out, samples.get(0).getOutputs().size(), "Y");
        out.print(SEPARATOR_VR);
        printHeader(out, samples.get(0).getOutputs().size(), "L");
        out.print(SEPARATOR_VR);
        printHeader(out, 1, " ");
        out.print(SEPARATOR_VL);
        out.println();
        printSeparator(out, SEPARATOR_HM, charsTotal);
        for (Sample s : samples.getAllSamples()) {
            recall(s.getInputs());
            out.print(SEPARATOR_VL);
            s.print(out);
            out.print(SEPARATOR_VR);
            for (Neuron n : getOutputLayer().getNeurons()) {
                if (round) out.printf(Locale.ENGLISH, "%7.3f", (float)Math.round(n.getY()));
                else out.printf(Locale.ENGLISH, "%7.3f", n.getY());
            }
            out.print(SEPARATOR_VR);
            int counter = 0;
            boolean error = false;
            for (Neuron n : getOutputLayer().getNeurons()) {
                if (!error) {
                    if ((n.getY() >= s.getOutputs().get(counter) + 0.2) || (n.getY() <= s.getOutputs().get(counter) - 0.2))
                        error = true;
                }
                counter++;
            }
            if (error) out.printf(Locale.ENGLISH, "%5s%1s", " ", "1");
            else out.printf(Locale.ENGLISH, "%6s", " ");
            out.print(SEPARATOR_VR);
            out.println();
        }
        printSeparator(out, SEPARATOR_H, charsTotal);
    }


    private void printSeparator(PrintStream out, String separator, int lineLength) {
        for (int i = 0; i < lineLength; i++) {
            out.print(separator);
        }
        out.println();
    }

    public static void printHeader(PrintStream out, int colCount, String colPrefix) {
        for (int i = 0; i < colCount; i++) {
            out.printf("%3s%3s%1s", "", colPrefix + (i + 1), "");
        }
    }
}
