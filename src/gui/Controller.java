package gui;

import core.FileController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.Dataset;
import model.FileTypes;
import neuralNetwork.LayerInput;
import neuralNetwork.LayerPerceptron;
import neuralNetwork.Network;
import neuralNetwork.NetworkPerceptron;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea console;

    private ByteArrayOutputStream out;
    private Dataset dataset;
    private Network network;
    private FileController fileController;
    private static final String NEW_LINE = System.lineSeparator();

    @FXML
    private void handleCloseOperation(ActionEvent event)
    {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleButtonInitialize(ActionEvent event)
    {
        network = new NetworkPerceptron();
        ArrayList<Double> temp = new ArrayList<>();
        temp.add(0.0);
        temp.add(0.0);
        network.addLayer(new LayerInput(temp));
        network.addLayer(new LayerPerceptron(2, network));
        network.addLayer(new LayerPerceptron(1, network));
        network.initWeights(-0.3, 0.3, 10);
        network.recall();
    }

    @FXML
    private void handleButtonPrint(ActionEvent event)
    {
        dataset.print(System.out);
        print();
    }

    @FXML
    private void handleButtonPrintNetwork(ActionEvent event)
    {
        network.print(System.out);
        print();
    }

    @FXML
    private void handleButtonClear(ActionEvent event)
    {
        console.clear();
    }

    @FXML
    private void handleLoadFromTextFile(ActionEvent event)
    {
        File file = fileController.loadSaveFile(FileTypes.TXT,1);
        dataset.loadFromTextFile(file);
        console.appendText("Load from text file done."+NEW_LINE);
    }

    @FXML
    private void handleLoadFromCsvFile(ActionEvent event)
    {
        File file = fileController.loadSaveFile(FileTypes.CSV,1);
        dataset.loadFromCsvFile(file);
        console.appendText("Load from CVS file done."+NEW_LINE);
    }

    @FXML
    private void handleLoadFromXmlFile(ActionEvent event)
    {
        File file = fileController.loadSaveFile(FileTypes.XML,1);
        dataset.loadFromXmlFile(file);
        console.appendText("Load from XML file done."+NEW_LINE);
    }

    @FXML
    private void handleSaveToTextFile(ActionEvent event)
    {
        File file = fileController.loadSaveFile(FileTypes.TXT,2);
        dataset.saveToTextFile(file);
        console.appendText("Save to text file done."+NEW_LINE);
    }

    @FXML
    private void handleSaveToCsvFile(ActionEvent event)
    {
        File file = fileController.loadSaveFile(FileTypes.CSV,2);
        dataset.saveToCsvFile(file);
        console.appendText("Save to CVS file done."+ NEW_LINE);
    }

    @FXML
    private void handleSaveToXmlFile(ActionEvent event)
    {
        File file = fileController.loadSaveFile(FileTypes.XML,2);
        dataset.saveToXmlFile(file);
        console.appendText("Save to XML file done."+ NEW_LINE);
    }

    public void print()
    {
        console.appendText(out.toString());
        out.reset();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        out = new ByteArrayOutputStream();
        dataset = new Dataset();
        fileController = new FileController();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(out));
    }
}
