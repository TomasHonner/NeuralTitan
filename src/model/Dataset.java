package model;

import core.AccessType;
import core.FileController;
import core.RandomGenerator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Tomáš Honner on 7.3.2016.
 */
@XmlRootElement
public class Dataset {

    /**
     * Array of samples
     */
    @XmlElement(name = "sample")
    public ArrayList<Sample> samples;

    /**
     * Inicialization of class FileController
     */
    private FileController fileController;

    /**
     * Inicialization of class RandomGenerator
     */
    private RandomGenerator randomGenerator;

    /**
     * Constant for new line separator
     */
    public static final String NEW_LINE = System.lineSeparator();

    /**
     * Constant for separator for Csv file
     */
    public static final String SEPARATOR_CSV = ",";

    /**
     * Constant for separator for text file
     */
    public static final String SEPARATOR_TEXT = ",";

    /**
     * Constant for data normalization bottom treshold
     */
    private static final Double NORMALIZE_FROM = 0.0;

    /**
     * Constant for data normalization top treshold
     */
    private static final Double NORMALIZE_TO = 1.0;





    /**
     * Default constructor for class Dataset
     */
    public Dataset() {
        samples = new ArrayList<>();
        fileController = new FileController();
    }

    /**
     * Return size of array samples
     * @return Size of array samples
     */
    public int size() {
        return samples.size();
    }

    /**
     * Get sample from array samples
     * @param index Index of array cell
     * @return Sample from samples
     */
    public Sample get(int index) {
        return samples.get(index);
    }

    /**
     * Get all samples from dataset
     * @return Array of samples
     */
    public ArrayList<Sample> getAllSamples() {
        return samples;
    }

    /**
     * Inicialization of random generator
     * @param type Type of access
     * @param seed Seed for random generator
     */
    public void randomInit(AccessType type, long seed) {
        randomGenerator = new RandomGenerator(type, seed, samples.size());
    }

    /**
     * Get random sample from samples
     * @return Random sample from samples
     */
    public Sample getRandom() {
        return samples.get(randomGenerator.getRandom());
    }

    /**
     * Append dataset to current dataset
     * @param dataset Dataset to append
     */
    public void append(Dataset dataset) {
        samples.addAll(dataset.getAllSamples());
    }

    /**
     * Clear array of samples
     */
    public void clear() {
        samples.clear();
    }

    /**
     * Remove sample from samples by index in array
     *
     * @param index Index of array cell
     */
    public void remove(int index) {
        samples.remove(index);
    }

    /**
     * Remove sample from samples by object Sample
     *
     * @param sample
     */
    public void remove(Sample sample) {
        remove(sample);
    }

    /**
     * Add sample to array of samples
     *
     * @param sample Sample to add
     */
    public void add(Sample sample) {
        samples.add(sample);
    }

    /**
     * Calculate all numbers of inputs and numbers of outputs for all samples
     */
    public void calculateAllSamples() {
        for (Sample s : samples
                ) {
            s.calculateInputs();
            s.calculateOutputs();
        }
    }

    /**
     * Data print for all samples
     *
     * @param out print stream
     */
    public void print(PrintStream out) {
        for (Sample s : samples) {
            ArrayList<Double> inp = s.getInputs();
            ArrayList<Double> outp = s.getOutputs();
            out.print("Inputs: ");
            for (Double d : inp
                    ) {
                out.printf(Locale.ENGLISH, "%7.4f" + SEPARATOR_CSV, d);
            }
            out.print(" || Outputs: ");
            for (Double dd : outp
                    ) {
                out.printf(Locale.ENGLISH, "%7.4f" + SEPARATOR_CSV, dd);
            }
            out.println();
        }
        out.println();
    }

    /**
     * Load data from text file
     * @param file File to load
     */
    public void loadFromTextFile(File file) {
        samples.clear();
        samples.addAll(fileController.loadFromTXTCSV(file, SEPARATOR_TEXT));
    }

    /**
     * Load data from csv file
     * @param file File to load
     */
    public void loadFromCsvFile(File file) {
        samples = fileController.loadFromTXTCSV(file, SEPARATOR_CSV);
    }

    /**
     * Load data from xml file
     * @param file File to load
     */
    public void loadFromXmlFile(File file) {
        samples.clear();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Dataset.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Dataset dataset = (Dataset) jaxbUnmarshaller.unmarshal(file);
            System.out.println(dataset.samples.size());
            samples = dataset.getAllSamples();
            dataset.calculateAllSamples();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save data to text file
     * @param file File to save
     */
    public void saveToTextFile(File file) {
        fileController.saveTXTCVS(file, createContentToSave(FileTypes.TXT));
    }

    /**
     * Save data to csv file
     * @param file File to save
     */
    public void saveToCsvFile(File file) {
        fileController.saveTXTCVS(file, createContentToSave(FileTypes.CSV));
    }

    /**
     * Save data to xml file
     * @param file File to save
     */
    public void saveToXmlFile(File file) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Dataset.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(this, file);
            System.out.println("Dataset was saved successfully to file: " + file.getName());
        } catch (Exception e) {

        }
    }

    /**
     * Create content string from samples to save
     * @param type Type of file (txt, csv)
     * @return String of content to save
     */
    public String createContentToSave(FileTypes type) {
        String content = "";
        String separator = "0";
        if (type == FileTypes.TXT) separator = SEPARATOR_TEXT;
        else separator = SEPARATOR_CSV;

        if (samples.size() > 0) {
            content += "# some comment " + NEW_LINE + "#! " + samples.get(0).getNumberOfInputs() + " " + samples.get(0).getNumberOfOutputs() + NEW_LINE;
            for (Sample s : samples) {
                for (Double d : s.getInputs()
                        ) {
                    content += Double.toString(d) + separator;
                }
                for (Double d : s.getOutputs()
                        ) {
                    content += Double.toString(d) + separator;
                }
                content += NEW_LINE;

            }
        } else {
            content = "Something went wrong";
        }
        return content;
    }

    /**
     * Return number of inputs from input layer
     * @return Number of inputs
     */
    public int getNumberOfInputs()
    {
        if (samples.size() == 0)
        {
            return 0;
        }
        else
        {
            return samples.get(0).getInputs().size();
        }
    }

    public int getNumberOfOutputs()
    {

        if (samples.size() == 0)
        {
            return 0;
        }
        else
        {
            return samples.get(0).getOutputs().size();
        }
    }



    /**
     * Normalize inputs in dataset to range 0.0 - 1.0
     * @param data2 Dataset to normalize
     * @return Normalized dataset
     */
    public static Dataset normalize(Dataset data2)
    {
        Double minimum = getMinimum(data2.getAllSamples());
        Double maximum = getMaximum(data2.getAllSamples());

        if ((minimum >= NORMALIZE_FROM) && maximum <= NORMALIZE_TO )
        {
            return data2;
        }
        else
        {
            Dataset newDataset = new Dataset();
            for (Sample sample: data2.getAllSamples()
                    ) {
                ArrayList<Double> delimitedInputs = new ArrayList<>();
                for (int i = 0; i < sample.getInputs().size() ; i++)
                {
                    delimitedInputs.add(0.0);
                }
                for (int i = 0; i < sample.getInputs().size(); i++)
                {
                    delimitedInputs.set(i,(NORMALIZE_TO - NORMALIZE_FROM) * (sample.getInputs().get(i) - minimum) / (maximum - minimum) + NORMALIZE_FROM);
                }
                newDataset.add(new Sample(delimitedInputs, sample.getOutputs()));
            }
            return newDataset;
        }

    }

    /**
     * Get maximum value from all samples
     * @param allSamples All samples
     * @return Maximum value
     */
    public static Double getMaximum(ArrayList<Sample> allSamples) {
        Double maximum = Double.MIN_VALUE;
        for (Sample s : allSamples) {
            for (int i = 0; i < s.getInputs().size(); i++) {
                if (maximum < s.getInputs().get(i)) maximum = s.getInputs().get(i);
            }
        }
        return maximum;
    }

    /**
     * Get minimum value from all samples
     * @param allSamples All samples
     * @return Minimum value
     */
    public static Double getMinimum(ArrayList<Sample> allSamples) {
        Double minimum = Double.MAX_VALUE;
        for (Sample s : allSamples) {
            for (int i = 0; i < s.getInputs().size(); i++) {
                if (minimum > s.getInputs().get(i)) minimum = s.getInputs().get(i);
            }
        }
        return minimum;
    }

    /**
     * Chenage postion of delimiter
     * @param maximum
     * @return
     */
    private static int changeDelimiter(Double maximum) {
        int count = 0;
        while (maximum > NORMALIZE_TO) {
            maximum /= 10.0;
            count++;
        }
        return count;
    }
}