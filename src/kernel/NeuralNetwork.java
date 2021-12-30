package kernel;

import operations.TrainingNetwork;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe container para redes neurais artificiais. A classe NeuralNetwork é formada pelas estruturas básicas
 * que compõem uma rede neural artificial. É composta de um e apenas um {@link kernel.Input}, um e apenas um
 * {@link kernel.Output} e um {@link java.util.ArrayList} de 1 ou mais {@link kernel.Layer layers}.
 *
 * <p>Os métodos {@link NeuralNetwork#training()}  e {@link NeuralNetwork#propagate()} só funcionarão após todos estes
 * elementos estiverem definidos, configurados e acoplados à NeuralNetwork pelos métodos {@link #attachInput(Input)},
 * {@link #attachOutput(Output)}} e {@link #addLayer(Layer)}.</p>
 * <div style="background: #f0f3f3; overflow:auto;width:auto;border:solid gray;border-width:.1em .1em .1em .8em;padding:.2em .6em;">
 *     <pre style="margin: 0; line-height: 125%">NeuralNetwork nn <span style="color: #555555">=</span> <span style="color: #006699; font-weight: bold">new</span> NeuralNetwork<span style="color: #555555">();</span>
 *Layer l1 <span style="color: #555555">=</span> <span style="color: #006699; font-weight: bold">new</span> Layer<span style="color: #555555">(</span>numberOfNeurons<span style="color: #555555">);</span>
 *l1<span style="color: #555555">.</span><span style="color: #330099">setActivationFunction</span><span style="color: #555555">(</span>customActivationFunctionIfNeeded<span style="color: #555555">);</span>
 *nn<span style="color: #555555">.</span><span style="color: #330099">addLayer</span><span style="color: #555555">(</span>l1<span style="color: #555555">);</span>
 *<span style="color: #0099FF; font-style: italic">//Repetir para quantas layers forem necessárias</span>
 *<span style="color: #555555">...</span>
 *Input input <span style="color: #555555">=</span> customInput<span style="color: #555555">.</span><span style="color: #330099">getInput</span><span style="color: #555555">();</span>
 *Output output <span style="color: #555555">=</span> customOutput<span style="color: #555555">.</span><span style="color: #330099">getOutput</span><span style="color: #555555">();</span>
 *nn<span style="color: #555555">.</span><span style="color: #330099">attachInput</span><span style="color: #555555">(</span>input<span style="color: #555555">);</span>
 *nn<span style="color: #555555">.</span><span style="color: #330099">attachOutput</span><span style="color: #555555">(</span>output<span style="color: #555555">);</span>
 *Trainer trainer <span style="color: #555555">=</span> <span style="color: #006699; font-weight: bold">new</span> CustomTrainer<span style="color: #555555">(nn);</span>
 *trainer<span style="color: #555555">.</span><span style="color: #330099">startTraining</span><span style="color: #555555">();</span>
 *
 *<span style="color: #0099FF; font-style: italic">//Neste momento a rede está pronta para ser treinada ou usada.</span>
 *nn<span style="color: #555555">.</span><span style="color: #330099">beginTraining</span><span style="color: #555555">();</span>
 * </pre></div>
 * @see Layer
 */
public class NeuralNetwork implements Serializable {

    /**
     * Coleção de camadas
     */
    protected ArrayList<Layer> layers;

    /**
     * Coleção de amostras
     */
    protected ArrayList<ArrayList<Double>> samples;

    /**
     * Rótulo
     */
    protected String label;

    /**
     * Objeto para a camada de entrada
     */
    protected Input input;

    /**
     * Objeto para a camada de saída
     */
    protected Output output;

    /**
     * Cria uma instância da rede neural vazia e criando a lista de camadas
     */
    public NeuralNetwork() {
        this.layers = new ArrayList<>();
    }

    /**
     * propagação dos valores
     *
     * @return verdadeiro se a propagação estiver de acordo
     */
    public boolean propagate() {
        if (layers.isEmpty() || output == null || input == null)
            return false;
        for (Layer l : layers) {
            l.propagate();
        }
        return true;
    }

    /**
     * Adicionar camada
     *
     * @param layer camada adicionada
     */
    public void addLayer(Layer layer) {
        if (layers.size() == 0) {
            layers.add(layer);
        } else {
            addLayer(layers.size() - 1, layer);
        }
    }

    /**
     * Adiciona camada em um indice especifico
     *
     * @param index indice
     * @param layer camada adicionada
     * @return verdadeiro se o função ocorrer
     */
    public boolean addLayer(int index, Layer layer) {
        if (layer.getNeuronsCount() == 0 || index == 0) {
//            System.out.println("Camada está sem neurônios ou o indíce está na posição zero");
            return false;
        } else {
            if (index == layers.size() - 1)
                connect(layers.get(index - 1), layer);
//            layers.add(index, layer);
            if (index < layers.size() - 1) {
                layers.get(index - 1).clearAllConnections();
                connect(layers.get(index - 1), layer);
                connect(layer, layers.get(index + 1));
            }
        }
        layers.add(index, layer);
        return true;
    }

    /**
     * Remover camada
     *
     * @param layer camada removida
     */
    public void removeLayer(Layer layer) {
        layers.remove(layer);
    }

    /**
     * Remove camada no indice especifico
     *
     * @param index indice
     * @return verdadeiro se a função ocorrer
     */
    public boolean removeLayerAt(int index) {
        //Todo verificar quando tiver input
        //Todo verificar quando tiver output
        //Todo verificar quando tiver apenas uma layer
        if (layers.get(index).equals(null))
            return false;
        if (index == 0 || index == layers.size() - 1)
            return false;
        Layer l1 = layers.get(index - 1);
        Layer l2 = layers.get(index + 1);
        l1.clearAllConnections();
        connect(l1, l2);
        layers.get(index).removeAllNeurons();
        layers.remove(index);
        return true;
    }

    /**
     * Conetar duas camadas
     *
     * @param l1 camada 1
     * @param l2 camada 2
     */
    private void connect(Layer l1, Layer l2) {
        for (int i = 0; i < l1.getNeuronsCount(); i++) {
            for (int j = 0; j < l2.getNeuronsCount(); j++) {
                l1.getNeurons().get(i).addOutputConnection(l2.getNeurons().get(j));
            }
        }
        this.randomizeWeight();
    }

    /**
     * Retorna posição da camada
     *
     * @param layer camada
     * @return camadas
     */
    public int indexOf(Layer layer) {
        return layers.indexOf(layer);
    }

    /**
     * Obter numero de camadas
     *
     * @return tamanho da camada
     */
    public int getLayersCount() {
        return layers.size();
    }

    /**
     * Recebe a camada de entrada
     *
     * @param input Camada de entrada
     * @throws Exception já existe camada de entrada
     */
    public void attachInput(Input input) throws Exception {
        if (this.input == null) {
            this.input = input;
            if(layers.size() == 0)
                layers.add(input.getLayer());
            else {
                connect(input.getLayer(), layers.get(0));
                layers.add(0, input.getLayer());
            }

        } else {
            throw new Exception("Ops! Já temos a camada input na rede");
        }
    }

    /**
     * Recebe a camada de saída
     *
     * @param output Camada de saída
     * @throws Exception já existe camada de saída
     */
    public void attachOutput(Output output) throws Exception {
        if (this.output == null) {
            this.output = output;
            addLayer(layers.size(), output.getLayer());
        } else {
            throw new Exception("Ops! Já temos a camada input na rede");
        }
    }

    /**
     * Mostra informações da arquitetura da rede neural
     *
     * @param verbose verdadeiro se a informção for mais detalhada e falso se não
     * @return informações da arquitetura
     */
    public String showInfo(boolean verbose) {
        StringBuilder info = new StringBuilder();
        info.append("Neural Network Info: \n");
        info.append("Layers: " + layers.size() + "\n");
        info.append("Input attached: " + (input != null) + "\n");
        info.append("Output attached: " + (output != null) + "\n");
        for (int i = 0; i < layers.size(); i++) {
            info.append("Layer index: " + i + " Number of neurons: " + layers.get(i).getNeuronsCount() + "\n");
            info.append("Layer index: " + i + " Activation Function: " + layers.get(i).getActivationFunction().getName() + "\n");
            if (verbose) {
                for (int j = 0; j < layers.get(i).getNeuronsCount(); j++) {
                    Neuron n = layers.get(i).getNeurons().get(j);
                    info.append("Neuron: " + j + " Value:" + n.getValue() + "\n");
                    info.append("Neuron: " + j + " Activated Value:" + n.getActivateValue() + "\n");
                }
            }
        }
        return info.toString();
    }

    /**
     * Cria uma instancia de connection weight com valor aleatorio de peso dentro de [-1 .. 1]
     */
    public void randomizeWeight() {
        for (Layer l : layers) {
            l.randomizeLayerWeights();
        }
    }

    /**
     * Checar todos os ponteiros de neuronios e conexões
     *
     * @return verdeiro se toda a arquiterura estiver integro
     */
    public boolean checkIntegrity() {
        //Todo verificar integridade das conexões
        return true;
    }

    /**
     * Realização do treinamento
     */
    public void training() {
//        TrainingNetwork trainingNetwork = new TrainingNetwork(this);
    }

    /**
     * Obter coleção de camadas
     *
     * @return camadas
     */
    public List<Layer> getLayers() {
        return Collections.unmodifiableList(this.layers);
    }

    /**
     * Rótulo da rede neural
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
     * Acesso ao input da Rede Neural.
     * @return Retorna o Input da Rede Neural ou null caso não o possua ainda.
     */
    public Input getInput() {return input;}

    /**
     * Acesso ao Output da Rede Neural.
     * @return Retorna o Output da Rede Neural ou null caso não o possua ainda.
     */
    public Output getOutput() {
        return output;
    }


}
