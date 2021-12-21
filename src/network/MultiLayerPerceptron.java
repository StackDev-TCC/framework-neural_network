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


    public void feedForward() {

    }

    public void backPropagation() {

    }


    public void errorCheck(){
        feedForward();
    }


    public void reconfigureNetwork(){
        backPropagation();
    }


}
