package test;


import com.sun.org.apache.bcel.internal.generic.NEW;
import model.Dataset;
import model.Sample;
import neuralNetwork.*;
import kmeans.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScatterPlot extends JFrame implements ImageObserver {


    int x, y;
    BufferedImage graph;
    Dataset data;
    JPanel panel;
    Network net;

    public ScatterPlot(int x, int y) throws Exception {
        super("Scatter Plot");
        /*         if (((InputLayer)net.layers.get(0)).neurons.length != 2) {
         throw new Exception("incompatible network");
         }
         */ this.x = x;
        this.y = y;
        graph = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        panel = new JPanel() {
            @Override
            public void paint(Graphics gc) {
                gc.drawImage(graph, 0, 0, panel);
            }
        };
        panel.setSize(x, y);
        getContentPane().add(panel);
        setResizable(false);
        setSize(x, y);
        setVisible(true);
    }

    public void plot(NetworkPerceptron net, Dataset data) {
        Graphics gc = graph.createGraphics();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Double fy = Double.valueOf(i) / y;
                Double fx = Double.valueOf(j) / x;

                ArrayList<Double> list = new ArrayList<>();
                //net.recall(new Double[]{fx, fy});
                list.add(fx);
                list.add(fy);
                net.recall(list);

                int c = (int) (255 * net.getOutputLayer().getNeurons().get(0).getY() + 0.5);
                c = c & 0xFF;
                graph.setRGB(j, y - i - 1, c << 16 | c << 8 | c);
            }
        }
        LayerPerceptron hidden = (LayerPerceptron) net.getLayers().get(1);
        gc.setColor(Color.yellow);
        for (Neuron neuron : hidden.getNeurons()) {
            NeuronPerceptron n = (NeuronPerceptron) neuron;
            Double y1 = -n.getTheta() / n.getW().get(1);
            Double y2 = -n.getTheta() / n.getW().get(1) - n.getW().get(0) / n.getW().get(1);
            Double x1 = -n.getTheta() / n.getW().get(0);
            Double x2 = -n.getTheta() / n.getW().get(0) - n.getW().get(1) / n.getW().get(0);
            if ((0 <= y1 && y1 <= 1) && (0 <= y2 && y2 <= 1)) {
                gc.drawLine(0, (int) (y - y * y1 - 0.5), x, (int) (y - y * y2 - 0.5));
            }
            if ((0 <= y1 && y1 <= 1) && (0 <= x1 && x1 <= 1)) {
                gc.drawLine(0, (int) (y - y * y1 - 0.5), (int) (x * x1 + 0.5), y);
            }
            if ((0 <= y1 && y1 <= 1) && (0 <= x2 && x2 <= 1)) {
                gc.drawLine(0, (int) (y - y * y1 - 0.5), (int) (x * x2 + 0.5), 0);
            }
            if ((0 <= y2 && y2 <= 1) && (0 <= x1 && x1 <= 1)) {
                gc.drawLine(x, (int) (y - y * y2 - 0.5), (int) (x * x1 + 0.5), y);
            }
            if ((0 <= y2 && y2 <= 1) && (0 <= x2 && x2 <= 1)) {
                gc.drawLine(x, (int) (y - y * y2 - 0.5), (int) (x * x2 + 0.5), 0);
            }
            if ((0 <= x1 && x1 <= 1) && (0 <= x2 && x2 <= 1)) {
                gc.drawLine((int) (x * x1 + 0.5), y, (int) (x * x2 + 0.5), 0);
            }
        }
        for (Sample s : data.getAllSamples()) {
            int sx = (int) (x * s.getInputs().get(0) + 0.5);
            int sy = y - (int) (y * s.getInputs().get(1) + 0.5);
            if (s.getOutputs().get(0) > 0.5) {
                gc.setColor(Color.red);
            } else {
                gc.setColor(Color.green);
            }
            gc.drawLine(sx - 5, sy, sx + 5, sy);
            gc.drawLine(sx, sy - 5, sx, sy + 5);
        }
        panel.repaint();
    }




    public void plot(Kmeans kmeans, Dataset data) {
        int numOfcl = kmeans.getNumberOfCentroids();
        int bits = (int) (Math.log(numOfcl) / Math.log(2));
        bits = (bits % 3 > 0) ? bits / 3 + 1 : bits / 3;
        if (bits > 8) {
            bits = 8;
        }
        Graphics gc = graph.createGraphics();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Double fy = Double.valueOf(i) / y;
                Double fx = Double.valueOf(j) / x;
                int cl;
                ArrayList<Double> toList = new ArrayList(Arrays.asList(new Double[]{fx, fy}));
                cl = kmeans.classify(toList);
                int mask1 = ~(0xFFFFFFFF << bits);
                //int mask2 = (0xFF >>> bits);
                int b = ((cl & mask1) << (8 - bits + 1)) - (((cl & mask1) == 0) ? 0 : 1);
                mask1 <<= bits;
                int g = ((cl & mask1) << (8 - 2 * bits + 1)) - (((cl & mask1) == 0) ? 0 : 1);
                mask1 <<= bits;
                int r = ((cl & mask1) << (8 - 3 * bits + 1)) - (((cl & mask1) == 0) ? 0 : 1);
                graph.setRGB(j, y - i - 1, r << 16 | g << 8 | b);
            }
        }
        for (Sample s : data.getAllSamples()) {
            int sx = (int) (x * s.getInputs().get(0) + 0.5);
            int sy = y - (int) (y * s.getInputs().get(1) + 0.5);
            if (s.getOutputs().get(0) > 0.5) {
                gc.setColor(Color.orange);
            } else {
                gc.setColor(Color.RED);
            }
            gc.drawLine(sx - 5, sy, sx + 5, sy);
            gc.drawLine(sx, sy - 5, sx, sy + 5);
        }
        gc.setColor(Color.WHITE);
        for (Centroid c : kmeans.getCentroids()) {
            int sx = (int) (x * c.getCoordinates().get(0) + 0.5);
            int sy = y - (int) (y * c.getCoordinates().get(1) + 0.5);
            gc.drawArc(sx - 5, sy - 5, 10, 10, 0, 360);
            gc.drawLine(sx - 10, sy, sx + 10, sy);
            gc.drawLine(sx, sy - 10, sx, sy + 10);
        }
        panel.repaint();
    }



    public void plot(NetworkRBF rbf, Dataset data, boolean output) {
        ArrayList<Neuron> hiddenNurons = rbf.getLayers().get(1).getNeurons();
        int numOfcl = hiddenNurons.size() + 1;
        int bits = (int) (Math.log(numOfcl) / Math.log(2));
        bits = (bits % 3 > 0) ? bits / 3 + 1 : bits / 3;
        if (bits > 8) {
            bits = 8;
        }
        Graphics gc = graph.createGraphics();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Double fy = Double.valueOf(i) / y;
                Double fx = Double.valueOf(j) / x;
                ArrayList<Double> toList = new ArrayList<Double>(Arrays.asList(new Double[] {fx, fy}));
                rbf.recall(toList);
                int cl = 0;
                if (output) {
                    cl = (int) (255 * rbf.getOutputLayer().getNeurons().get(0).getY() + 0.5);
                    cl = cl & 0xFF;
                    graph.setRGB(j, y - i - 1, cl << 16 | cl << 8 | cl);
                } else {
                    Double maxy = hiddenNurons.get(0).getY();
                    for (int k = 1; k < hiddenNurons.size(); k++) {
                        Double yy = hiddenNurons.get(k).getY();
                        if (maxy < yy) {
                            maxy = yy;
                            cl = k;
                        }
                    }
                    cl += 1;
                    int mask1 = ~(0xFFFFFFFF << bits);
                    int mask2 = (0xFF >>> bits);
                    int b = ((cl & mask1) << (8 - bits + 1)) - (((cl & mask1) == 0) ? 0 : 1);
                    mask1 <<= bits;
                    int g = ((cl & mask1) << (8 - 2 * bits + 1)) - (((cl & mask1) == 0) ? 0 : 1);
                    mask1 <<= bits;
                    int r = ((cl & mask1) << (8 - 3 * bits + 1)) - (((cl & mask1) == 0) ? 0 : 1);
                    b = (int) (b * maxy + 0.5);
                    g = (int) (g * maxy + 0.5);
                    r = (int) (r * maxy + 0.5);
                    graph.setRGB(j, y - i - 1, r << 16 | g << 8 | b);
                }
            }
        }
        for (Sample s : data.getAllSamples()) {
            int sx = (int) (x * s.getInputs().get(0) + 0.5);
            int sy = y - (int) (y * s.getInputs().get(1) + 0.5);
            if (s.getOutputs().get(0) > 0.5) {
                gc.setColor(Color.red);
            } else {
                gc.setColor(Color.green);
            }
            gc.drawLine(sx - 5, sy, sx + 5, sy);
            gc.drawLine(sx, sy - 5, sx, sy + 5);
        }
        gc.setColor(Color.blue);
        for (Neuron n : hiddenNurons) {
            int sx = (int) (x * ((NeuronRBF) n).getW().get(0) + 0.5);
            int sy = y - (int) (y * ((NeuronRBF) n).getW().get(1) + 0.5);
            gc.drawArc(sx - 5, sy - 5, 10, 10, 0, 360);
            gc.drawLine(sx - 10, sy, sx + 10, sy);
            gc.drawLine(sx, sy - 10, sx, sy + 10);
        }
        panel.repaint();
    }

}
