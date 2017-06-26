package xyz.morecraft.dev.scp.neural;

import lombok.Data;

@Data
public class NeuronLayer {

    private int numberOfNeurons;
    private int numberOfInputsPerNeuron;
    private double[][] synapticWeights;
    private double[][] error;
    private double[][] output;
    private double[][] delta;
    private double[][] adjustment;

    public NeuronLayer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        this.numberOfNeurons = numberOfNeurons;
        this.numberOfInputsPerNeuron = numberOfInputsPerNeuron;
        this.synapticWeights = new double[numberOfInputsPerNeuron][numberOfNeurons];
        this.randomize();
    }

    public void randomize() {
        for (int i = 0; i < synapticWeights.length; i++) {
            for (int j = 0; j < synapticWeights[i].length; j++) {
                synapticWeights[i][j] = (2.0 * Math.random()) - 1.0;
            }
        }
    }

    public double[][] calculateErrorAsDifference(double[][] trainingSetInputs) {
        error = new double[1][trainingSetInputs[0].length];
        for (int i = 0; i < output[0].length; i++) {
            error[0][i] = trainingSetInputs[0][i] - output[0][i];
        }
        return error;
    }

    public void adjust() {
        for (int i = 0; i < synapticWeights.length; i++) {
            for (int j = 0; j < synapticWeights[i].length; j++) {
                synapticWeights[i][j] += adjustment[i][j];
            }
        }
    }

}
