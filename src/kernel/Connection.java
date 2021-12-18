package kernel;

import java.io.Serializable;

/**
 * A classe Connection é responsável pelas conexões dos neurônios, como atributos temos o {@link Connection#fromNeuron} Inicial e {@link Connection#toNeuron} destino.
 * <p>Através do construtor {@link Connection#Connection(Neuron, Neuron, double)}, é possível a instância do objeto com os neurônios conectados e seu valor do peso</p>
 * <p>Através do método {@link Connection#propagate()}, será feito a propagação (Multiplicação da saída do neurônio, com o valor do peso).</p>
 * <pre>
 *  //Criação dos neurônios
 *  Neuron n1 = new Neuron();
 *  Neuron n2 = new Neuron();
 *  //Conectando os neurônios
 *  Connection c = new Connection (n1, n2);
 *  //Realizando a propagação
 *  c.propagate();
 * </pre>
 * @see Input
 */
public class Connection implements Serializable {

    /**
     * Neurônio inicial
     */
    protected Neuron fromNeuron;
    /**
     * Neurônio destino
     */
    protected Neuron toNeuron;
    /**
     * Valor do peso
     */
    protected double weightValue;

    /**
     * Cria a instância vazia da conexão
     */
    public Connection() {

    }

    /**
     * Cria a instância da conexão
     *
     * @param fromNeuron  neurônio inicial
     * @param toNeuron    neurônio destino
     * @param weightValue peso da conexão
     */
    public Connection(Neuron fromNeuron, Neuron toNeuron, double weightValue) {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.weightValue = weightValue;
    }

    /**
     * Cria a instância da conexão
     *
     * @param fromNeuron neurônio inicial
     * @param toNeuron   neurônio destino
     */
    public Connection(Neuron fromNeuron, Neuron toNeuron) {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
    }

    /**
     * Cria a instância da conexão
     *
     * @param weightValue peso da conexão
     */
    public Connection(double weightValue) {
        this.weightValue = weightValue;
    }

    /**
     * Realiza a propagação, multiplica a saída doneurônio inicio
     * com o peso da conexão e adicionando ao neurônio de destino
     */
    public void propagate() {
        toNeuron.addValue(fromNeuron.getActivateValue() * weightValue);
    }

    /**
     * Obter saída do neurônio de inicio
     *
     * @return saída do neurônio de inicio
     */
    public double getOutputFromNeuron() {
        return fromNeuron.getOutput();
    }

    /**
     * Obter saída do neurônio
     *
     * @return neurônio de inicio
     */
    public final Neuron getFromNeuron() {
        return fromNeuron;
    }

    /**
     * Obter neurônio de destino
     *
     * @return neurônio de destino
     */
    public final Neuron getToNeuron() {
        return toNeuron;
    }

    /**
     * Definir valor do peso
     *
     * @param value valor do peso
     */
    public final void setWeightValue(double value) {
        this.weightValue = value;
    }

    /**
     * Obter valor do peso
     *
     * @return valor do peso
     */
    public final double getWeightValue() {
        return this.weightValue;
    }


}
