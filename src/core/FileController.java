package core;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FileTypes;
import model.Sample;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Tomáš Honner on 7.3.2016.
 */
public class FileController {

    /**
     * Default constructor of class FileController
     */
    public FileController()
    {

    }

    /**
     * Set and show dialog window for diffrent types of files and operations
     * @param type Type of file (.txt .csv .xml)
     * @param operation Type of operation (load, save)
     * @return Returns selected file
     */
    public File loadSaveFile(FileTypes type, int operation)
    {
        String[] r = new String[2];
        if(type == FileTypes.TXT)
        {
            r[0] = "Text file";
            r[1] = "*.txt";
        }
        if(type == FileTypes.CSV)
        {
            r[0] = "CSV file";
            r[1] = "*.csv";
        }
        if(type == FileTypes.XML)
        {
            r[0] = "XML file";
            r[1] = "*.xml";
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("D:\\IT\\Other\\SVI"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(r[0], r[1]));
        File selectedFile = null;
        if(operation == 1)
        {
            selectedFile = fileChooser.showOpenDialog(new Stage());
        }
        if(operation == 2)
        {
            selectedFile = fileChooser.showSaveDialog(new Stage());
        }

        return selectedFile;
    }

    /**
     * Load data from text file or CSV file
     * @param file Selected file
     * @param separator Data separator used in the file
     * @return List of Sample type objects
     */
    public ArrayList<Sample> loadFromTXTCSV(File file, String separator)
    {
        int inputs = 0;
        int outputs = 0;
        String line = "";
        Sample sample = new Sample();
        BufferedReader reader = null;

        ArrayList<Sample> samples = new ArrayList<>();

        try
        {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null)
            {
                line = line.trim();
                if (line.contains("#"))
                {
                    if (line.contains("#!"))
                    {
                        line = line.replace("#!","");
                        line = line.replace(" ","");
                        inputs = Integer.parseInt(line.substring(0,1));
                        outputs = Integer.parseInt(line.substring(1,2));
                    }
                    else
                    {
                        continue;
                    }
                }

                else
                {
                    ArrayList<Double> inputValues = new ArrayList<>();
                    ArrayList<Double> outputValues = new ArrayList<>();
                    line = line.replace(" ","");
                    int counter = inputs + outputs;
                    int border = counter - inputs;
                    String[] values = line.split(separator);
                    for(int i = 0; i<values.length; i++)
                    {
                        if (counter <= border)
                        {
                            outputValues.add(Double.valueOf(values[i]));
                        }
                        else
                        {
                            inputValues.add(Double.valueOf(values[i]));
                        }
                        counter--;
                    }
                    sample = new Sample(inputValues,outputValues);
                    sample.setNumberOfInputs(inputs);
                    sample.setNumberOfOutputs(outputs);
                    samples.add(sample);
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("File not found");
        }
        catch (IOException e)
        {
            throw new RuntimeException("IO Error occured");
        }
        return samples;
    }



    /**
     * Save samples in file
     * @param file File in filesystem
     * @param content String to write
     */
    public void saveTXTCVS(File file, String content)
    {
        try
        {
            if (!file.exists())
            {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            bw.write(content);
            bw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

