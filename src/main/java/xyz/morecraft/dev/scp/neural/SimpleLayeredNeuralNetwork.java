package xyz.morecraft.dev.scp.neural;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Data
public class SimpleLayeredNeuralNetwork {

    private final List<NeuronLayer> layers;

    private double error;
    private double trainFactor;

    private Function<Double, Double> activationFunction = NeuralMath.sigmoid();
    private Function<Double, Double> activationFunctionDerivative = NeuralMath.sigmoidDerivative();

    public SimpleLayeredNeuralNetwork() {
        this.layers = new ArrayList<>();
        this.error = Double.MAX_VALUE;
        this.trainFactor = 1.0;
    }

    public void addLayer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        layers.add(new NeuronLayer(numberOfNeurons, numberOfInputsPerNeuron));
    }

    public void rebuild() {
        for (NeuronLayer layer : layers) {
            layer.randomize();
        }
    }

    public void train(double[][] trainingSetInputs, double[][] trainingSetOutputs, int numberOfTrainingIterations) {
        for (int i = 0; i < numberOfTrainingIterations; i++) {
            trainOnce(trainingSetInputs, trainingSetOutputs);
        }
    }

    public void trainOnce(double[][] trainingSetInputs, double[][] trainingSetOutputs) {
        think(trainingSetInputs);

        NeuronLayer prevLayer = null;
        for (int i = layers.size() - 1; i >= 0; i--) {
            final NeuronLayer layer = layers.get(i);
            final NeuronLayer nextLayer = i == 0 ? null : layers.get(i - 1);
            if (Objects.isNull(prevLayer)) {
                layer.setError(NeuralMath.subtract(trainingSetOutputs, layer.getOutput()));
                error = NeuralMath.mean(NeuralMath.abs(layer.getError()));
                //System.out.println(String.format("a %+.5f", NeuralMath.mean(layer.getError())));
            } else {
                layer.setError(NeuralMath.dot(prevLayer.getDelta(), NeuralMath.transpose(prevLayer.getSynapticWeights())));
                //System.out.println(String.format("b %+.5f", NeuralMath.mean(layer.getError())));
            }
            layer.setDelta(NeuralMath.multiply(layer.getError(), NeuralMath.proccess(layer.getOutput(), activationFunctionDerivative)));
            if (Objects.isNull(nextLayer)) {
                layer.setAdjustment(NeuralMath.multiply(trainFactor, NeuralMath.dot(NeuralMath.transpose(trainingSetInputs), layer.getDelta())));
            } else {
                layer.setAdjustment(NeuralMath.multiply(trainFactor, NeuralMath.dot(NeuralMath.transpose(nextLayer.getOutput()), layer.getDelta())));
            }
            prevLayer = layer;
        }

        for (NeuronLayer layer : layers) {
            layer.adjust();
        }
    }

    private double[][] thinkLayer(double[][] trainingSetInputs, NeuronLayer layer) {
        layer.setOutput(NeuralMath.proccess(NeuralMath.dot(trainingSetInputs, layer.getSynapticWeights()), NeuralMath.sigmoid()));
        return layer.getOutput();
    }

    public double[] thinkOutput(double[][] trainingSetInputs) {
        final double[][][] think = think(trainingSetInputs);
        return think[think.length - 1][0];
    }

    public double[][][] think(double[][] trainingSetInputs) {
        final List<double[][]> outputs = new ArrayList<>();
        outputs.add(thinkLayer(trainingSetInputs, layers.get(0)));
        for (int i = 1; i < layers.size(); i++) {
            outputs.add(thinkLayer(outputs.get(outputs.size() - 1), layers.get(i)));
        }
        return outputs.toArray(new double[outputs.size()][outputs.get(0).length][outputs.get(0)[0].length]);
    }

}
