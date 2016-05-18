package networks;

import model.Dataset;
import model.Sample;
import neuralNetwork.LayerInput;
import neuralNetwork.LayerPerceptron;
import neuralNetwork.NetworkPerceptron;
import neuralNetwork.Neuron;
import test.ScatterPlot;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Tomáš Honner on 19.4.2016.
 */
public class IrisNetworkPerceptron {

    public static void main(String[] args) throws Exception
    {
        NetworkPerceptron network = new NetworkPerceptron();
        network.addLayer(new LayerInput(2));
        network.addLayer(new LayerPerceptron(2, network));
        network.addLayer(new LayerPerceptron(1, network));
        Random rand = new Random(10);

        //File file = new File("D:\\IT\\Other\\SVI\\perceptronIris.txt");
        File file = new File("D:\\IT\\Other\\SVI\\perceptronIris10.txt");
        Dataset dataset = new Dataset();
        dataset.loadFromTextFile(file);
        dataset = Dataset.normalize(dataset);
        network.learn(dataset, -0.3, 0.3, rand, 0.8, 0.5, 0.0001, 5000, new ScatterPlot(500,500)); // nastavit mse na 0.01 pro dalsi pripad
        printTest(dataset, network, System.out);
    }

    public static void printTest(Dataset dataset, NetworkPerceptron net, PrintStream out)
    {
        for(Sample s : dataset.samples)
        {
            net.recall(s.getInputs());
            ArrayList<Double> inp = s.getInputs();
            ArrayList<Double> outp = s.getOutputs();
            out.print("Inputs: ");
            for (Double d: inp
                    ) {
                out.printf(Locale.ENGLISH, "%7.4f"+",", d);
            }
            out.print(" || Outputs: ");
            for (Double dd: outp
                    ) {
                out.printf(Locale.ENGLISH,"%7.4f"+",", dd);
            }
            for(Neuron neuron : net.getOutputLayer().getNeurons())
            {
                out.printf("%10.3f", neuron.getY());
            }

            out.println();
        }
        out.println();
    }
}
