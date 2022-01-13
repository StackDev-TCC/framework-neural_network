package network.mnist;

import kernel.Layer;
import kernel.Output;

public class MNISTOutput extends Output {

    /**
     * Instância do mnist output
     *
     * @param outputLayer output mnist layer
     */
    public MNISTOutput(Layer outputLayer) {
        super(outputLayer);
    }

    /**
     * Definir output
     */
    @Override
    public void defineOutput() {
        for (int i = 0; i < 10; i++)
            outputs.add(Integer.valueOf(i));
    }

    /**
     * Obter valor esperado
     *
     * @return valor esperado
     */
    @Override
    public Integer getExpectedValue() {
        return (Integer) super.getExpectedValue();
    }

    /**
     * Instância vazia
     */
    public MNISTOutput() {
        super(10);
    }

}
