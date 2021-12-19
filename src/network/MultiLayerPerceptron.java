package network;

import kernel.NeuralNetwork;
import operations.InputSamples;
import operations.TrainingNetwork;
import operations.TrainingStrategy;
import operations.TypeOfLearning;
import report.Report;

public class MultiLayerPerceptron extends TrainingNetwork {


    public MultiLayerPerceptron(NeuralNetwork neuralNetwork) {
        super(neuralNetwork);
    }

    public MultiLayerPerceptron(TypeOfLearning typeOfLearning, TrainingStrategy trainingStrategies, NeuralNetwork neuralNetwork, InputSamples inputSamples, Report report) {
        super(typeOfLearning, trainingStrategies, neuralNetwork, inputSamples, report);
    }

    @Override
    public void feedForward() {

    }

    @Override
    public void backPropagation() {

    }

    @Override
    public void startTraining() {

    }
}
