package kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import activationfunctions.Sigmoid;
import interfaces.ActivationFunction;

/**
 * A classe Layer é responsável por conter os neurônios, formado por um {@link java.util.ArrayList}
 * de {@link kernel.Neuron}, e constituida por uma função de ativação.
 *
 * <p>Através do construtor {@link Layer#Layer(int neuronsCount)} é possível a  * criação da camada
 * já com seu numero de neurônios definidos</p>
 *
 * <p>Através do construtor {@link Layer#Layer(int qtdNeurons, ActivationFunction activationFunction)}
 * é possível a criação da camada já com seu numero de neurônios definidos e qual será a função
 * de ativação utilizada</p>
 *
 * <p>Utilizando os métodos {@link Layer#addNeuron(Neuron)} e {@link Layer#setNeuron(int, Neuron)}
 * é possível o incremento de neurônios nesta camada</p>
 *
 * <p>Utilizando os métodos {@link Layer#removeNeuron(Neuron)} e {@link Layer#removeAllNeurons()} )}
 * é possível o decremento de neurônios nesta camada</p>
 *
 * <pre>
 *     //Criação das camadas
 *     Layer l1 = new Layer(numberOfNeurons);
 *     Layer l2 = new Layer(numberOfNeurons, new Sigmoid());
 *     Neuron n1 = new Neuron();
 *
 *     //Adicionar neurônio a camada
 *     l1.addNeuron(n1);
 *
 *     //Remover todos os neurônios
 *     l1.removeAllNeurons();
 * </pre>
 * @see Neuron
 */
public class Layer implements Serializable, Cloneable {

    //TODO Pensar numa sintaxe de nomes para ids de layres para construção automática pela Rede Neural
    //TODO Modificar os construtores, para permitir customização destas ids
    //TODO Criar método de busca utilizando as IDs na rede neural para layer, da layer para neurônio e da Rede Neural para neurônio

    /**
     * Coleção de neurônios nesta camada
     */
    protected ArrayList<Neuron> neurons;

//    private ActivationFunction activationFunction;

    /**
     * Cria uma instância de camada vazia
     */
    public Layer() {
        this.neurons = new ArrayList<Neuron>();
    }

    /**
     * Cria uma instância de camada vazia para um número especificado de neurônios
     *
     * @param neuronsCount o número de neurônios nesta camada
     */
    public Layer(int neuronsCount) {
        this.neurons = new ArrayList<Neuron>();
        populateLayer(neuronsCount, new Sigmoid());
    }

    /**
     * Cria uma instância de camada para um número especificado de neurônios e função de ativação
     *
     * @param qtdNeurons quantidade de neurônios
     * @param activationFunction função de ativação
     */
    public Layer(int qtdNeurons, ActivationFunction activationFunction) {
        this.neurons = new ArrayList<Neuron>();
        populateLayer(qtdNeurons, activationFunction);
    }

    /**
     * Limpa as outConnections
     */
    public void clearAllConnections() {
        for (Neuron n : neurons) {
            n.clearConnections();
        }
    }

    /**
     * Randomiza os pesos
     */
    public void randomizeLayerWeights() {
        for (int i = 0; i < this.getNeuronsCount(); i++) {
            this.getNeurons().get(i).radomizeOutputWeight();
        }
    }


    /**
     * Popular a camada com a função de ativação
     *
     * @param neuronsCount números de neurônios
     * @param activationFunction função de ativação
     */
    public void populateLayer(int neuronsCount, ActivationFunction activationFunction) {
        for (int n = 1; n <= neuronsCount; n++)
            neurons.add(new Neuron(activationFunction));
    }

    /**
     * Adicionar neurônio
     *
     * @param neuron a ser adicionado
     */
    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }

    /**
     * Adicionar neurônio na posição específica
     *
     * @param neuron a ser adicionado
     * @param index posição
     */
    public void addNeuron(int index, Neuron neuron) {
        neurons.add(index, neuron);
    }

    /**
     * Substituir neurônio
     *
     * @param index  substitui no índice
     * @param neuron substituto
     */
    public void setNeuron(int index, Neuron neuron) {
        neurons.set(index, neuron);
    }

    /**
     * remover neurônio da camada
     *
     * @param neuron neurônio a ser removido
     */
    public void removeNeuron(Neuron neuron) {
        int index = indexOf(neuron);
        neurons.remove(index);
    }

    /**
     * remover todos os neuronios
     */
    public void removeAllNeurons() {
        neurons.clear();
    }

    /**
     * Retornar a posição do neurônio
     *
     * @param neuron neurônio
     * @return index posição especificada
     */
    public int indexOf(Neuron neuron) {
        return neurons.indexOf(neuron);
    }

    /**
     * Verifica se está vazio
     *
     * @return verdadeiro vazio e falso com neurônios
     */
    public boolean isEmpty() {
        return neurons.isEmpty();
    }

    /**
     * Verifica se há função de ativação nos neurônios na camada
     *
     * @return verdeiro se tiver função de ativação e falso se não
     */
    public boolean checkNeuronsActivation() {
        for (int i = 0; i < this.getNeuronsCount(); i++) {
            if (getActivationFunction(i).equals(getActivationFunction()))
                return true;
        }
        return false;
    }

    /**
     * Obter número de neurônios na camada
     *
     * @return número de neurônios
     */
    public int getNeuronsCount() {
        return neurons.size();
    }


    /**
     * Obter lista de neurônios da camada
     *
     * @return lista de neurônios
     */
    public final List<Neuron> getNeurons() {
        return Collections.unmodifiableList(neurons);
    }

    /**
     * Obter neurônio específico
     * @param index índice
     * @return neurônio específico
     */
    public Neuron getNeuronAt(int index) {
        return neurons.get(index);
    }

    /**
     * Obter função de ativação da camada
     *
     * @return função de ativação da camada
     */
    public ActivationFunction getActivationFunction() {
        //TODO Ideal é verificar se todos os neurônios estão com a mesma activationFunction antes de retorná-la
        return getActivationFunction(0);
    }

    /**
     * Obter função de ativação de um neurônio específico
     *
     * @param pos posição
     * @return função de ativação
     */
    public ActivationFunction getActivationFunction(int pos) {
        if (pos >= 0 && pos < neurons.size()) {
            return neurons.get(pos).getActivationFunction();
        }
        return null;
    }

    /**
     * Definir função de ativação
     *
     * @param activationFunction função de ativação
     */
    public void setActivationFunction(ActivationFunction activationFunction) {
        for (Neuron n : neurons) {
            n.setActivateValue(activationFunction);
        }
    }

    /**
     * Propagação através dos neurônios
     */
    public void propagate() {
        for (Neuron n : neurons) {
            n.propagate();
        }

    }
}
