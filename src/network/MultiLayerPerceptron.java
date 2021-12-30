package network;

import kernel.NeuralNetwork;
import operations.InputSamples;
import operations.TrainingNetwork;
import operations.TrainingStrategy;
import operations.TypeOfLearning;
import report.Report;

import java.util.ArrayList;

public class MultiLayerPerceptron extends TrainingNetwork {


    public MultiLayerPerceptron(NeuralNetwork neuralNetwork) {
        super(neuralNetwork);
    }

    public MultiLayerPerceptron(TypeOfLearning typeOfLearning, TrainingStrategy trainingStrategies, NeuralNetwork neuralNetwork, InputSamples inputSamples, Report report) {
        super(typeOfLearning, trainingStrategies, neuralNetwork, inputSamples, report);
    }


    public boolean feedForward() {
        neuralNetwork.propagate();
        return true;
    }

    public void backPropagation() {
        //Calculo apenas da camada de saída para a oculta anterior.
        ArrayList<Double> errors = new ArrayList<>();
        for (int i = 0; i < neuralNetwork.getOutput().getLayer().getNeuronsCount(); i++) {
            //Todo precisamos saber o valor esperado de cada neurônio de saída
//            errors.add(neuralNetwork.getOutput().getLayer().getNeurons().get(i).getOutput() - neuralNetwork.getOutput().getExpectedValue());
        }

        ArrayList<Double> derivatives = new ArrayList<>();
        for (int i = 0; i < neuralNetwork.getOutput().getLayer().getNeuronsCount(); i++) {
            //Todo adicionar derivada da função de ativação no Neuron??
        }

        ArrayList<Double> gradients = new ArrayList<>();
        for (int i = 0; i < neuralNetwork.getOutput().getLayer().getNeuronsCount(); i++) {
            //Todo calcular hadammard entre as derivadas os erros (gradiente)
            gradients.add(derivatives.get(i) * errors.get(i));
        }
    }


    public void errorCheck() {
        feedForward();
    }


    public void reconfigureNetwork() {
        backPropagation();
    }


}
