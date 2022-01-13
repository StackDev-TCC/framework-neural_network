package kernel;

import kernel.Layer;

import java.util.ArrayList;

/**
 * A classe Output é responsável conter o {@link java.util.ArrayList} de objetos de saída {@link Output#outputs}, e um objeto Layer classificada como camada de saída {@link Output#outputLayer}
 *
 * <p>Através do construtor {@link Output#Output(int)}, é gerado uma camada de saída com o numero de neurônios contidos nela.
 * O método {@link Output#getExpectedValue()} será útilizado para obter o neurônio com o valor máximo</p>
 * @see Input
 */
public abstract class Output {

    /**
     * Camada de saída
     */
    protected Layer outputLayer;
    /**
     * Lista de objeto de outputs
     */
    protected ArrayList<Object> outputs = new ArrayList<>();
    /**
     * Valor máximo do output
     */
    protected double max = 0;

    /**
     * Instância output criando e definindo o tamanho da camada
     *
     * @param size tamanho
     */
    public Output(int size) {
        this.outputLayer = new Layer(size);
        defineOutput();
    }

    /**
     * Instância output recebendo a camada
     *
     * @param outputLayer camada de saída
     */
    public Output(Layer outputLayer) {
        this.outputLayer = outputLayer;
        defineOutput();
    }

    /**
     * Obter camada de saída
     *
     * @return camada de saída
     */
    public Layer getLayer() {
        return outputLayer;
    }

    /**
     * definir output
     */
    public abstract void defineOutput();

    /**
     * Obter o valor esperado
     *
     * @return valor esperado
     */
    public Object getExpectedValue() {
        double n;
        int index = 0;
        for (int i = 0; i < outputLayer.getNeuronsCount(); i++) {
            n = outputLayer.getNeurons().get(i).getValue();
            if (n > max) {
                max = n;
                index = i;
            }
        }
        return outputs.get(index);
    }
//Todo realizar o retorno com mais de um elemento
//  public Object[] getExpectedValues() {}
}
