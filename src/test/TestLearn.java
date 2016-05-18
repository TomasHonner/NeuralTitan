package test;

import model.Dataset;
import model.Sample;
import neuralNetwork.LayerInput;
import neuralNetwork.LayerPerceptron;
import neuralNetwork.NetworkPerceptron;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 4.4.2016.
 */
public class TestLearn {

    public static void main(String[] args)
    {
        try
        {
            System.setOut(new PrintStream(new File("D:\\IT\\Other\\SVI\\output-pop.txt")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        File file;
        Dataset dataset = null;
        try
        {
            file = new File("D:\\IT\\Other\\SVI\\XORtest.txt");
            dataset = new Dataset();
            dataset.loadFromTextFile(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ScatterPlot plot = null;
        try {
            plot = new ScatterPlot(500,500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkPerceptron network = new NetworkPerceptron();
        network.addLayer(new LayerInput(2));
        network.addLayer(new LayerPerceptron(2, network));
        network.addLayer(new LayerPerceptron(1, network));
        Random rand = new Random(10);
        network.learn(dataset, -0.3, 0.3, rand, 0.8, 0.0, 0.0, 15000, plot);
        network.print(System.out);

    }


    private static final Dataset createDataset()
    {
        Dataset data = new Dataset();
        ArrayList<Double> inputs1 = new ArrayList<>();
        inputs1.add(0.0);inputs1.add(0.0);
        ArrayList<Double> inputs2 = new ArrayList<>();
        inputs2.add(1.0);inputs2.add(0.0);
        ArrayList<Double> inputs3 = new ArrayList<>();
        inputs3.add(0.0);inputs3.add(1.0);
        ArrayList<Double> inputs4 = new ArrayList<>();
        inputs4.add(1.0);inputs4.add(1.0);

        ArrayList<Double> outputs1 = new ArrayList<>();
        outputs1.add(0.0);
        ArrayList<Double> outputs2 = new ArrayList<>();
        outputs2.add(1.0);
        ArrayList<Double> outputs3 = new ArrayList<>();
        outputs3.add(1.0);
        ArrayList<Double> outputs4 = new ArrayList<>();
        outputs4.add(0.0);

        Sample sample1 = new Sample(inputs1, outputs1);
        sample1.setNumberOfInputs(2);
        sample1.setNumberOfOutputs(1);
        Sample sample2 = new Sample(inputs2, outputs2);
        sample2.setNumberOfInputs(2);
        sample2.setNumberOfOutputs(1);
        Sample sample3 = new Sample(inputs3, outputs3);
        sample3.setNumberOfInputs(2);
        sample3.setNumberOfOutputs(1);
        Sample sample4 = new Sample(inputs4, outputs4);
        sample4.setNumberOfInputs(2);
        sample4.setNumberOfOutputs(1);

        data.add(sample1);
        data.add(sample2);
        data.add(sample3);
        data.add(sample4);

        return data;
    }
}
