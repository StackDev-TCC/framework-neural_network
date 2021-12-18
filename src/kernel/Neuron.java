package kernel;

import activationfunctions.Sigmoid;
import interfaces.ActivationFunction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A classe Neuron é responsável conter conexões de saída, formado por um {@link java.util.ArrayList} de {@link kernel.Connection}.
 *
 * <p>Através do construtor {@link Neuron#Neuron(ActivationFunction)} é possível a criação do neurônio, definindo a função de ativação</p>
 * <p>Através do método {@link Neuron#addValue(double)}, é realizado a soma da multiplicação gerada pela {@link kernel.Connection}</p>
 * <p>O peso de suas conexões serão randomizadas através do método {@link Neuron#radomizeOutputWeight()}</p>
 *  <pre>
 *      //Criação dos neurônios
 *      Neuron n1 = new Neuron(new Step());
 *      Neuron n2 = new Neuron();
 *      //Randomização dos pesos
 *      n1.radomizeOutputWeight();
 *  </pre>
 * @see Connection
 */
public class Neuron implements Serializable, Cloneable {

//    /**
//     * Coleção de Connections de entrada do Neuron (conexões para este Neuron)
//     */
//    //depreciado
//    protected ArrayList<Connection> inputConnections;

    /**
     * Coleção de conexões de saída do neurônio
     */
    protected ArrayList<Connection> outConnections;

    /**
     * Representa a entrada total para este neurônio recebida da função de entrada. (Somatória)
     */
    protected double value = 0;

    /**
     * Valor da saída do neuônio
     */
    protected double output = 0;

    /**
     * Rótulo do neurônio
     */
    private String label;

    protected ActivationFunction activationFunction;

    /**
     * Cria a instância vazia do neurônio inicializando as listas de conections de saída e a função de ativação
     */
    public Neuron() {
//        this.inputConnections = new ArrayList<Connection>();
        this.outConnections = new ArrayList<Connection>();
        this.activationFunction = new Sigmoid();
    }

    /**
     * Cria a instância do neurônio inicializando as listas de conections de saída e a função de ativação
     *
     * @param activationFunction função de ativação no neurônio
     */
    public Neuron(ActivationFunction activationFunction) {
//        this.inputConnections = new ArrayList<Connection>();
        this.outConnections = new ArrayList<Connection>();
        this.activationFunction = activationFunction;
    }

    /**
     * deleta todas as conexoes de saida do neuronio
     */
    public void clearConnections() {
        this.getOutConnections().clear();
    }

    /**
     * Randomiza os valores conectados da saída do neurônio
     */
    public void radomizeOutputWeight() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        for (int i = 0; i < this.getOutConnections().size(); i++) {
            this.getOutConnections().get(i).setWeightValue(tlr.nextDouble(-1, 1));
        }
    }

//    /**
//     * Retorna verdadeiro se houver conexões de entrada para este neurônio, falso
//     * por outro lado
//     *
//     * @return true se houver conexão de entrada, false caso contrário
//     */
//    public boolean hasInputConnections() {
//        return (inputConnections.size() > 0);
//    }

    /**
     * Verifica se há nonexões de saída
     *
     * @param toNeuron neurônio de destino
     * @return verdadeiro se houver, falso se não houver
     */
    public boolean hasOutputConnectionTo(Neuron toNeuron) {
        for (Connection connection : outConnections) {
            if (connection.getToNeuron() == toNeuron) {
                return true;
            }
        }
        return false;
    }

//    /**
//     * Verifique a conexão do neurônio
//     *
//     * @param neuron conexão do Neuron a ser verificada
//     * @return true se houver conexão de entrada, false caso contrário
//     */
//    public boolean hasInputConnectionFrom(Neuron neuron) {
//        for (Connection connection : inputConnections) {
//            if (connection.getFromNeuron() == neuron) {
//                return true;
//            }
//        }
//        return false;
//    }


    /**
     * Adiciona conexão de entrada de determinado neurônio
     *
     * @param toNeuron neurônio para conectar ao neurônio de destino
     */
    public void addOutputConnection(Neuron toNeuron) {
        Connection connection = new Connection(this, toNeuron);
//        this.addInputConnection(connection);
    }

//    /**
//     * Adiciona a conexão de entrada especificada
//     *
//     * @param connection input connection para adicionar
//     */
//    public void addInputConnection(Connection connection) {
//        this.inputConnections.add(connection);
//        Neuron fromNeuron = connection.getFromNeuron();
//        fromNeuron.addOutputConnection(connection);
//    }


    /**
     * Adiciona a conexão de saída especificada
     *
     * @param connection adicionar conexão de saída
     */
    protected void addOutputConnection(Connection connection) {
        this.outConnections.add(connection);
    }

    /**
     * Definir valor de saída
     *
     * @param output valor definido
     */
    public void setOutput(double output) {
        this.output = output;
    }

//    /**
//     * Returns conexões de entrada deste neuronio
//     *
//     * @return input connections of this neuron
//     */
//    public final List<Connection> getInputConnections() {
//        return Collections.unmodifiableList(inputConnections);
//    }


    /**
     * Obter valor ativado
     *
     * @return valor ativado
     */
    public double getActivateValue() {
        System.out.println("value: " + value);
        return activationFunction.calculate(value);
    }

    /**
     * Define a entrada do neuronio, realizando a somatoria
     *
     * @param value valor de entrada para definir
     */
    public void addValue(double value) {
        this.value += value;
    }

    /**
     * Retorna o valor
     *
     * @return valor
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Retorna valor de saída do neurônio
     *
     * @return valor saída do neurônio
     */
    public double getOutput() {
        return this.output;
    }

    /**
     * Obter rótulo
     *
     * @return rótulo
     */
    public String getLabel() {
        return label;
    }

    /**
     * Definir rótulo
     *
     * @param label rótulo
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Definir função de ativação
     *
     * @param activationFunction função de ativação
     */
    public void setActivateValue(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    /**
     * Obter função de ativação
     *
     * @return função de ativação
     */
    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    /**
     * Obter conexões de saída
     *
     * @return conexões de saída
     */
    public ArrayList<Connection> getOutConnections() {
        return outConnections;
    }

    /**
     * Definir conexões de saída
     *
     * @param outConnections conexões de saída
     */
    public void setOutConnections(ArrayList<Connection> outConnections) {
        this.outConnections = outConnections;
    }

    /**
     * Propagação das conexões de saída deste neurônio
     */
    public void propagate() {
        for (Connection c : outConnections) {
            c.propagate();
        }
    }
}

