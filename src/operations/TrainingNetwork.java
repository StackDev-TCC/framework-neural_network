package operations;

import kernel.NeuralNetwork;
import report.Report;

/**
 * Classe base para treinamento da rede.
 * <p> Realiza o treinamento ao percorrer todas as estruturas presentes na {@link NeuralNetwork}.</p>
 *
 * <p> O treinamento se dá de forma customizada ao construir uma subclasse para esta {@code TrainingNetwork},
 * e implementar os métodos {@link #errorCheck()} e {@link #reconfigureNetwork()}.</p>
 *
 * <p> A classe {@code TrainingNetwork} executa o {@@link #startTraining} carregando novos valores de
 * input para cada novo valor em {@link InputSamples}, dentro das configurações de limite de {@code Epoch}
 * e para cada novo valor de {@code samplesPerEpoch}. Se {@code samplesPerEpoch} for 0, o {@link #startTraining()}
 * usará todos os valores em {@code inputSamples}.</p>
 *
 * <p>Durante o treinamento, os valores carregados no {@link kernel.Input} serão propagados pela {@link NeuralNetwork}
 * através do método {@link NeuralNetwork#propagate()}. Com o resultado gerado no {@link kernel.Output}, uma chamada
 * para {@link #errorCheck()} é realizada (para cada novo {@link InputSamples imputSample}. Após todas as amostras
 * de {@code samplesPerEpoch} forem propagadas, uma chamada para {@link #reconfigureNetwork()} é feita</p>
 *
 * <p>Nada impede de se reconfigurar a rede a cada novo {@link kernel.Input}, para isso basta na implementação
 * da subclasse de {@link #errorCheck()}, fazer uma chamada para {@link #reconfigureNetwork()} </p>
 */
public abstract class TrainingNetwork{

    protected TypeOfLearning typeOfLearning;
    protected TrainingStrategy trainingStrategies;
    protected NeuralNetwork neuralNetwork;
    protected InputSamples inputSamples;
    protected int epoch;
    protected int epochLimit;
    protected int samplesPerEpoch;
    protected Report report;

    /**
     * Construtor de {@code TrainingNetwork} que recebe uma {@link NeuralNetwork} assumidamente pronta
     * e configurada.
     *
     * @param neuralNetwork A {@link NeuralNetwork} previamente configurada.
     */
    public TrainingNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        neuralNetwork.randomizeWeight();
    }

    /**
     * Construtor da classe {@code TrainingNetwork} que assume todos os parametros como entrada.
     *
     * @param typeOfLearning     Um {@code Enum} contendo o tipo de aprendizagem que será usado.
     * @param trainingStrategies Um {@code Enum} contendo qual a estratégia de treinamento.
     * @param neuralNetwork      A {@link NeuralNetwork Rede Neural} a ser treinada.
     * @param inputSamples       O {@link InputSamples Conjunto de dados} de treinamento.
     * @param report             Um objeto instanciado e configurado de {@link Report}.
     */
    public TrainingNetwork(TypeOfLearning typeOfLearning, TrainingStrategy trainingStrategies, NeuralNetwork neuralNetwork, InputSamples inputSamples, Report report) {
        this.typeOfLearning = typeOfLearning;
        this.trainingStrategies = trainingStrategies;
        this.neuralNetwork = neuralNetwork;
        this.inputSamples = inputSamples;
        this.report = report;
    }

    /**
     * O método {@code startTraining()} é responsável por conduzir as chamadas de {@link #errorCheck()} e
     * {@link #reconfigureNetwork()}  de tal forma que a rede possa ser treinada corretamente.
     */
    public void startTraining(){
        int samplesLimit;
        for(int ep = 0; ep < epochLimit; ep++){

            if(samplesPerEpoch==0)
                samplesLimit = inputSamples.getSamples().size();
            else
                samplesLimit = samplesPerEpoch;

            for(int samples = 0; samples < samplesLimit; samples++){
                inputSamples.nextSample();
                neuralNetwork.propagate();
                errorCheck();

            }
            reconfigureNetwork();
        }
    }

    public abstract void errorCheck();

    public abstract void reconfigureNetwork();

//    /**
//     * O método {@code feedForward} é responsável por acompanhar o resultado de cada {@link NeuralNetwork#propagate()},
//     * comparar os resultados alcançados, calcular os erros, adicioná-los ao {@code Array} de erros e
//     * o {@code Array} de tempo gasto, a serem passados para o {@link Report}.
//     */
//    public abstract void feedForward();
//
//    /**
//     * O método {@code backPropagation} se manifesta a cada período estabelecido para, com as informações dos
//     * erros, reconfigurar os pessos das {@link kernel.Connection Connections}.
//     */
//    public abstract void backPropagation();

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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
