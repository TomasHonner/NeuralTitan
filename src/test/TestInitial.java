package test;

import neuralNetwork.LayerInput;
import neuralNetwork.LayerPerceptron;
import neuralNetwork.Network;
import neuralNetwork.NetworkPerceptron;

import java.util.ArrayList;

/**
 * Created by Tomáš Honner on 4.4.2016.
 */
public class TestInitial {

    public static void main(String[] args)
    {
        Network network = new NetworkPerceptron();
        ArrayList<Double> temp = new ArrayList<>();
        temp.add(0.0);
        temp.add(0.0);
        network.addLayer(new LayerInput(temp));
        network.addLayer(new LayerPerceptron(2, network));
        network.addLayer(new LayerPerceptron(1, network));
        network.initWeights(-0.3, 0.3, 10);
        network.recall();
        network.print(System.out);

    }

}
