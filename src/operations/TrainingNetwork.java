package operations;

import kernel.NeuralNetwork;
import report.Report;

/**
 * Classe base para treinamento da rede.
 * <p>
 *     Realiza o treinamento ao percorrer todas as estruturas presentes na {@link NeuralNetwork}.
 * </p>
 *
 * <p>
 *  O treinamento se dá de forma customizada ao construir uma subclasse para esta {@code TrainingNetwork},
 *  e sobrescrever os métodos {}{@link #startTraining()}, {@link #feedForward()} e {@link #backPropagation()}.
 * </p>
 */
public abstract class TrainingNetwork {

    private String typeOfLearning = "Supervisioned";
    private String learningStrategies = "Backpropagation";
    /**
     * Objeto rede neural
     */
    private NeuralNetwork neuralNetwork;
    private Report report;

    /**
     * Instancia do treinamento redebendo a rede neural
     *
     * @param neuralNetwork rede neural
     */
    public TrainingNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        neuralNetwork.randomizeWeight();
    }

    /**
     * O método {@code feedForward} é responsável por acompanhar o resultado de cada {@link NeuralNetwork#propagate()},
     *  comparar os resultados alcançados, calcular os erros, adicioná-los ao {@code Array} de erros e
     *  o {@code Array} de tempo gasto, a serem passados para o {@link Report}.
     */
    public abstract void feedForward();

    /**
     * O método {@code backPropagation} se manifesta a cada período estabelecido para, com as informações dos
     * erros, reconfigurar os pessos das {@link kernel.Connection Connections}.
     */
    public abstract void backPropagation() ;

    /**
     * O método {@code startTraining()} é responsável por conduzir as chamadas de {@link #feedForward()} e
     * {@link #backPropagation()} de tal forma que a rede possa ser treinada corretamente.
     */
    public abstract void startTraining();

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

    public Report getReport(){
        return report;
    }

    public void setReport(Report report){
        this.report = report;
    }
}
