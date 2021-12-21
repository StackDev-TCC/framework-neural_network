package kernel;

import interfaces.ActivationFunction;

import java.util.ArrayList;

/**
 * A classe Input é responsável conter os valores de entrada {@link Input#values}, e um objeto Layer classificada como camada de entrada {@link Input#inputLayer}
 *
 * <p>Através do construtor {@link Input#Input(ArrayList)}, os valores de entrada serão controladas pela classe, após isto
 * serão chamados os métodos {@link Input#convertToLayer()} e {@link Input#getNormalizedInput()} para a normalização de escala dos valores</p>
 * @see Output
 */
public class Input {

    /**
     * lista de valores da amostra
     */
    public ArrayList<Double> values = new ArrayList<>();

    /**
     * camada de entrada
     */
    public Layer inputLayer = new Layer();

    /**
     * Instância recebendo os valores de entrada
     *
     * @param values valores de entrada
     */
    public Input(ArrayList<Double> values) {
        this.values = values;
        convertToLayer();
    }

    /**
     * Normalização de escala dos valores de entrada
     *
     * @param in  valores
     * @param max máximo
     * @param min mínimo
     * @return valores normalizados
     */
    public double[] getNormalizedInput(ArrayList<Number> in, double max, double min) {
        double[] result = new double[in.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (in.get(i).doubleValue() - min) / (double) (max - min);
        }
        return result;
    }

    /**
     * Normalização de escala dos valores de entrada
     *
     * @return valores normalizados
     */
    public double[] getNormalizedInput() {
        double[] result = new double[values.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (values.get(i).doubleValue() - getMinValue()) / (double) (getMaxValue() - getMinValue());
//            System.out.println(result[i]);
        }
        return result;
    }

    /**
     * Normalização de escala dos valores de entrada
     *
     * @param values valores
     * @return valores normalizados
     */
    public double[] getNormalizedInput(ArrayList<Double> values) {
        double[] result = new double[values.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (values.get(i).doubleValue() - getMinValue()) / (double) (getMaxValue() - getMinValue());
        }
        return result;
    }

    /**
     * transfere os valores para os neuônios dentro da camada de entrada
     */
    public void convertToLayer() {
        if (inputLayer.getNeuronsCount() == 0)
            this.inputLayer = new Layer(values.size());
        inputLayer.setActivationFunction(new ActivationFunction() {
            @Override
            public double calculate(double value) {
                return value;
            }

            @Override
            public double derivate(double value) {
                return value;
            }

            @Override
            public String getName() {
                return "None";
            }
        });
        double[] store = getNormalizedInput();
        for (int i = 0; i < inputLayer.getNeuronsCount(); i++) {
            inputLayer.getNeurons().get(i).setOutput(store[i]);
        }
    }


    /**
     * transfere os valores para os neuônios dentro da camada de entrada (Sem a normalização)
     */
    private void convertSimpleToLayer() {
        if (inputLayer.getNeuronsCount() == 0)
            this.inputLayer = new Layer(values.size());
        for (int i = 0; i < inputLayer.getNeuronsCount(); i++) {
//            System.out.println(values.get(i));
            inputLayer.getNeurons().get(i).setOutput(values.get(i));
        }
    }

    /**
     * Obter valor mínimo
     *
     * @return valor mínimo
     */
    public Double getMinValue() {
        Double minValue = Double.MAX_VALUE;
        for (Double value : values) {
            minValue = Math.min(minValue, value);
        }
        return minValue;
    }

    /**
     * Obter valor máximo
     *
     * @return valor máximo
     */
    public Double getMaxValue() {
        Double maxValue = Double.MIN_VALUE;
        for (Double value : values) {
            maxValue = Math.max(maxValue, value);
        }
        return maxValue;
    }

    /**
     * Obter camada
     *
     * @return camada
     */
    public Layer getLayer() {
        return inputLayer;
    }

    /**
     * Definir camada
     *
     * @param inputLayer camada
     */
    public void setLayer(Layer inputLayer) {
        this.inputLayer = inputLayer;
    }

    /**
     * Obter valores
     *
     * @return valores
     */
    public ArrayList<Double> getValues() {
        return values;
    }

    /**
     * Definir valores
     *
     * @param values valores
     */
    public void setValues(ArrayList<Double> values) {
        this.values = values;
        convertToLayer();
    }
}
