package learning;

import kernel.NeuralNetwork;

/**
 * Classe de treinamento
 */
public class Training {

    private String typeOfLearning = "Supervisioned";
    private String learningStrategies = "Backpropagation";
    /**
     * Objeto rede neural
     */
    private NeuralNetwork neuralNetwork;

    /**
     * Instancia do treinamento redebendo a rede neural
     *
     * @param neuralNetwork rede neural
     */
    public Training(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        neuralNetwork.randomizeWeight();
    }

    /**
     * Função feedfoward
     */
    public void feedfoward() {
    }

    /**
     * Função de backpropagation
     */
    public void backpropagation() {
    }

    public String getTypeOfLearning() {
        return typeOfLearning;
    }

    public void setTypeOfLearning(String typeOfLearning) {
        this.typeOfLearning = typeOfLearning;
    }

    public String getLearningStrategies() {
        return learningStrategies;
    }

    public void setLearningStrategies(String learningStrategies) {
        this.learningStrategies = learningStrategies;
    }
}
