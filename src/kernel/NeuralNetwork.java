package kernel;

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
 * @author Prof. Luís Emílio C. D. Valle e Maurício Y. Nakandakari
 */
public class NeuralNetwork implements Serializable {

    /**
     * Coleção de camadas
     */
    protected ArrayList<Layer> layers;

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
    public void addLayer(Layer layer) throws IllegalArgumentException{
        /*
        *******************************************************************
        **********  Condições para o addLayer(Layer layer) ****************
        *******************************************************************
        layer (Argumento do método)     Exceções
        ------------------------------------------------------------------------------
        layer == null       		 -> Argumento inválido (só permitir inserção de layers instanciadas e com neurônios
        neuronCount == 0             -> Argumento inválido (Só permitir inserção de layers instanciadas e com neurônios
        *São separadas porque não existe neuronCount() se layer for null (incorreria em NullPointerException)
        Layers (Attr da Classe)     	Ações
        --------------------------------------------------------------------------------------------------------------
        Vazia                        -> Inserir a nova layer   (1)
        Apenas input                 -> Inserir a nova layer mais à direita e conectar o input à nova layer    (2)
        layers sem input e output    -> Inserir a nova layer mais à direita e conectar a anterior à nova layer (2)
        Input + layers               -> Inserir a nova layer mais à direita e conectar a anterior à nova layer (2)
        Apenas output                -> inserir a nova layer antes do output e conectar a nova layer ao output (3)
        Input + output sem layer     -> Inserir a nova layer antes de output, desconectar a anterior de output, conectar a anterior à nova layer e conectar a nova layer ao output
        Layers + output              -> Inserir a nova layer antes do output, desconectar a anterior do output, conectar a anterior à nova layer e conectar a nova layer ao output (4)
        Input + layers + output      -> Inserir a nova layer antes do output, desconectar a anterior do output, conectar a anterior à nova layer e conectar a nova layer ao output (4)
        */

        //Layer (Argumento do método) é nula ou possui 0 neurônios?
        //Layer == null precisa ser a primeira expressão a ser verificada, caso contrário será lançada NullPointerException
        if(layer == null || layer.getNeuronsCount()==0)
            throw new IllegalArgumentException("The layer must not be null or must contain at least 1 Neuron");
        //Layers (Atributo da classe) está vazia?
        if(layers.isEmpty())
            layers.add(layer);
        else{
            if(output != null){
                if(layers.size()==1){
                    layers.add(0,layer);
                    connect(layers.get(0),layers.get(1));
                }else{
                    Layer output = layers.get(layers.size()-1);
                    Layer last = layers.get(layers.size()-2);
                    disconnect(last);
                    layers.add(layers.size()-1, layer);
                    connect(last, layer);
                    connect(layer, output);
                }
            }else{
                Layer last = layers.get(layers.size()-1);
                layers.add(layer);
                connect(last, layer);
            }
        }
    }

    /**
     * Adiciona {@link Layer} em um indice especifico
     * <p>Se a {@code NeuralNetwork} estiver um input e/ou output acoplados, é impossível adicionar a nova camada
     * na posição 0 e {@code qtdLayers-1} respectivamente, sendo lançada uma {@link IllegalArgumentException}</p>
     *
     * @param index indice
     * @param layer camada adicionada
     *
     */
    public void addLayer(int index, Layer layer) {
        if(index < 0 || index >= layers.size())
            throw new IllegalArgumentException("Index does not corresponds to a valid layer position");
        if(layer == null)
            throw new IllegalArgumentException("Layer must not be null");
        if(layer.getNeuronsCount()==0)
            throw new IllegalArgumentException("Layer must contains at least 1 Neuron!");
        if(input != null && index == 0)
            throw new IllegalArgumentException("Impossible to insert on input layer position!");
        if(output != null && index == layers.size()-1)
            throw new IllegalArgumentException("Impossible to insert on output layer position");

        layers.add(index,layer);
        if(index > 0){
            Layer prev = layers.get(index-1);
            disconnect(prev);
            connect(prev, layer);
            if(layers.size()>index)
                connect(layer, layers.get(index+1));
        }
    }

    /**
     * Remover camada
     *
     * @param layer camada removida
     */
    public void removeLayer(Layer layer) throws IllegalArgumentException{
        removeLayer(layers.indexOf(layer));
    }

    /**
     * Remove uma camada no indice especificado.
     *<p>Caso o índice esteja fora dos limites, ou represente as camadas reservadas de input ou output,
     * uma {@code IllegalArgumentException} será lançada.</p>
     * <p>Caso a layer removida esteja entre outras duas layers, a anterior é reconectada à próxima layer.</p>
     * @param index indice da camada que se deseja remover
     * @return Retorna a {@link Layer} removida e desconectada
     */
    public Layer removeLayer(int index) throws IllegalArgumentException{
        Layer result;
        if(layers.isEmpty())
            throw new IllegalArgumentException("This NeuralNetwork has no layers to remove");
        if(index < 0 || index >= layers.size())
            throw new IllegalArgumentException("Index does not corresponds to a valid layer position");
        if(input != null && index == 0)
            throw new IllegalArgumentException("Impossible to exclude input layer without exclude input itself. Use detachInput() instead!");
        if(output != null && index == layers.size()-1)
            throw new IllegalArgumentException("Impossible to exclude output layer without exclude output itself. Use detatchOutput() instead");

        disconnect(layers.get(index));
        result = layers.remove(index);
        if(index > 0) {
            Layer before = layers.get(index - 1);
            disconnect(before);
            if (layers.size() > index)
                connect(before, layers.get(index));
        }
        return result;
    }

    /**
     * Conecta os {@link Neuron Neurônios} da {@link Layer camada} {@code l1} aos neurônios de {@code l2}
     *<p>Este método conecta cada neurônio de l1 a todos os neurônios de l2. Se outras conexões forem necessárias,
     * é recomendável customizar este comportamento em uma subclasse de {@code NeuralNetwork}</p>
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
     * Elimina as conexões dos neurônios de uma determinada {@link Layer}.
     * @param l A {@link Layer} a ter seus neurônios desconectados.
     */
    private void disconnect(Layer l){
        l.clearAllConnections();
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
     * @throws IllegalStateException já existe camada de entrada
     */
    public void attachInput(Input input) throws IllegalStateException {
        if (this.input == null) {
            this.input = input;
            layers.add(0, input.getLayer());
            if(layers.size() > 1)
                connect(input.getLayer(), layers.get(0));
        } else {
            throw new IllegalStateException("This network already has an configured input attached");
        }
    }

    public Input detachInput(){
        Input result;
        if(input == null)
            throw new IllegalStateException(("This network has no input to detach!"));
        else{
            result = input;
            disconnect(layers.get(0));
            layers.remove(0);
            input = null;
        }
        return result;
    }

    /**
     * Acopla um output à rede
     *
     * <p>Da mesma forma que {@code input}, só é permitida a insersção de um único
     * output. Caso já exista um inserido, uma {@code IllegalStateException} será lançada </p>
     *
     * @param output Camada de saída da rede
     * @throws IllegalStateException caso a {@code NeuralNetwork} já contenha uma {@link Layer camada} de saída,
     */
    public void attachOutput(Output output) throws IllegalStateException {

        if (this.output == null) {
            this.output = output;
            if(layers.size()>0){
                connect(layers.get(layers.size()-1), output.getLayer());
            }
            addLayer(layers.size(), output.getLayer());
        } else {
            throw new IllegalStateException("This network already has an configured output attached!");
        }
    }

    public Output detachOutput(){
        Output result;
        if(this.output == null){
            throw new IllegalStateException("This network has no output to detach!");
        }
        else{
            result = this.output;
            layers.remove(layers.size()-1);
            disconnect(layers.get(layers.size()-1));
            this.output = null;
        }
        return result;
    }

    /**
     * Mostra informações da arquitetura da rede neural
     *
     * @param verbose verdadeiro se a informção for mais detalhada e falso se não
     * @return informações da arquitetura
     */
    public String getInfo(boolean verbose) {
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
