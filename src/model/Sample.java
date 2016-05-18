package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tomáš Honner on 7.3.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Sample {

    /**
     * Array of inputs
     */
    @XmlElement(name = "input")
    private ArrayList<Double> inputs;

    /**
     * Array of outputs
     */
    @XmlElement(name = "output")
    private ArrayList<Double> outputs;

    /**
     * Number of inputs
     */
    @XmlElement(name = "numberOfInputs")
    private int numberOfInputs;

    /**
     * Number of outputs
     */
    @XmlElement(name = "numberOfOutputs")
    private int numberOfOutputs;

    /**
     * Comment of sample
     */
    @XmlElement(name = "comment")
    private String comment;

    /**
     * Separator for data
     */
    private static final String SEPARATOR = ",";

    /**
     * Default constructor of class Sample
     */
    public Sample()
    {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        numberOfInputs = 0;
        numberOfOutputs = 0;
        comment = "";
    }

    /**
     * Constructor of class Sample
     * @param inputs Array of inputs
     * @param outputs Array of outputs
     */
    public Sample(ArrayList<Double> inputs, ArrayList<Double> outputs)
    {
        this.inputs = inputs;
        this.outputs = outputs;
        numberOfInputs = 0;
        numberOfOutputs = 0;
        comment = "";
    }

    /**
     * Constructor of class Sample
     * @param numberOfInputs Number of inputs
     * @param numberOfOutputs Number of outputs
     */
    public Sample(int numberOfInputs, int numberOfOutputs)
    {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
    }

    /**
     * Data print to output stream
     * @param out Output stream
     */
    public void print(PrintStream out)
    {
        for(double io : this.inputs)
        {
            //out.print("Inputs variable number"+counter+" "+io+" \n");
            out.printf(Locale.ENGLISH,"%7.3f", io);
        }
        out.print(SEPARATOR);
        for(double io : this.outputs)
        {
            //out.print("Outputs variable number"+counter+" "+io+" \n");
            out.printf(Locale.ENGLISH,"%7.3f", io);
        }
    }

    /**
     * Calculate number of inputs from array inputs
     */
    public void calculateInputs()
    {
        numberOfInputs = inputs.size();
    }

    /**
     * Calculate number of outputs from array outputs
     */
    public void calculateOutputs()
    {
       numberOfOutputs = outputs.size();
    }

    /**
     * Clear lists inputs and outputs
     */
    public void clear()
    {
        inputs.clear();
        outputs.clear();
    }


    /**
     * Getter for array inputs
     * @return Array of inputs
     */
    public ArrayList<Double> getInputs()
    {
        return inputs;
    }

    /**
     * Setter for array inputs
     * @param inputs Array of inputs
     */
    public void setInputs(ArrayList<Double> inputs)
    {
        this.inputs = inputs;
    }

    /**
     * Getter for array outputs
     * @return Array of outputs
     */
    public ArrayList<Double> getOutputs()
    {
        return outputs;
    }

    /**
     * Setter for array outputs
     * @param outputs Array of outputs
     */
    public void setOutputs(ArrayList<Double> outputs)
    {
        this.outputs = outputs;
    }

    /**
     * Getter for variable numberOfInputs
     * @return Number of inputs
     */
    public int getNumberOfInputs()
    {
        return numberOfInputs;
    }

    /**
     * Setter for variable numberOfInputs
     * @param numberOfInputs Number of inputs
     */
    public void setNumberOfInputs(int numberOfInputs)
    {
        this.numberOfInputs = numberOfInputs;
    }

    /**
     * Getter for variable numberOfOutputs
     * @return Number of outputs
     */
    public int getNumberOfOutputs()
    {
        return numberOfOutputs;
    }

    /**
     * Setter for variable numberOfOutputs
     * @param numberOfOutputs Number of outputs
     */
    public void setNumberOfOutputs(int numberOfOutputs)
    {
        this.numberOfOutputs = numberOfOutputs;
    }

    /**
     * Getter for variable comment
     * @return Comment
     */
    public String getComment()
    {
        return comment;
    }

    /**
     * Setter for variable comment
     * @param comment Comment
     */
    public void setComment(String comment)
    {
        this.comment = comment;
    }
}
