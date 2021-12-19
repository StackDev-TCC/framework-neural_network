package operations;

import kernel.NeuralNetwork;
import report.Report;

/**
 * Classe base para treinamento da rede.
 * <p> Realiza o treinamento ao percorrer todas as estruturas presentes na {@link NeuralNetwork}.</p>
 *
 * <p> O treinamento se dá de forma customizada ao construir uma subclasse para esta {@code TrainingNetwork},
 *  e sobrescrever os métodos {}{@link #startTraining()}, {@link #feedForward()} e {@link #backPropagation()}.</p>
 *
 */
public abstract class TrainingNetwork {

    private TypeOfLearning typeOfLearning;
    private TrainingStrategy trainingStrategies;
    private NeuralNetwork neuralNetwork;
    private InputSamples inputSamples;
    private Report report;

    /**
     * Instancia do treinamento recebendo a rede neural
     *
     * @param neuralNetwork rede neural
     */
    public TrainingNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        neuralNetwork.randomizeWeight();
    }

    /**
     * Construtor da classe {@code TrainingNetwork} que assume todos os parametros como entrada.
     * @param typeOfLearning Um {@code Enum} contendo o tipo de aprendizagem que será usado.
     * @param trainingStrategies Um {@code Enum} contendo qual a estratégia de treinamento.
     * @param neuralNetwork A {@link NeuralNetwork Rede Neural} a ser treinada.
     * @param inputSamples O {@link InputSamples Conjunto de dados} de treinamento.
     * @param report Um objeto instanciado e configurado de {@link Report}.
     */
    public TrainingNetwork(TypeOfLearning typeOfLearning, TrainingStrategy trainingStrategies, NeuralNetwork neuralNetwork, InputSamples inputSamples, Report report) {
        this.typeOfLearning = typeOfLearning;
        this.trainingStrategies = trainingStrategies;
        this.neuralNetwork = neuralNetwork;
        this.inputSamples = inputSamples;
        this.report = report;
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

    public TypeOfLearning getTypeOfLearning() {
        return typeOfLearning;
    }

    public void setTypeOfLearning(TypeOfLearning typeOfLearning) {
        this.typeOfLearning = typeOfLearning;
    }

    public TrainingStrategy getTrainingStrategies() {
        return trainingStrategies;
    }

    public void setTrainingStrategies(TrainingStrategy trainingStrategy) {
        this.trainingStrategies = trainingStrategies;
    }



    public Report getReport(){
        return report;
    }

    public void setReport(Report report){
        this.report = report;
    }
}
