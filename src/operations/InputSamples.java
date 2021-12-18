package operations;

import kernel.Input;

import java.util.ArrayList;

/**
 * Classe input samples é responsável por receber as amostras e manipular
 */
public class InputSamples {

    /**
     * Lista de amostras
     */
    private ArrayList<ArrayList<Double>> samples;
    /**
     * ìndice da amostra
     */
    private int index = 0;
    /**
     * Objeto Input
     */
    private Input input;
    /**
     * Valor mínimo da amostra
     */
    private double min;
    /**
     * Valor máximo da amostra
     */
    private double max;

    /**
     * Instância da lista de amostras recebendo a lista
     *
     * @param samples amostras
     */
    public InputSamples(ArrayList<ArrayList<Double>> samples) {
        this.samples = samples;
        input = new Input(samples.get(0));
    }

//    public InputSamples(ArrayList<ArrayList<Double>> samples, boolean flag) {
//        this.samples = samples;
//        input = new Input(samples.get(0), flag);
//    }

    //Deixar void?
//    public ArrayList<Double> nextSample() {
//        if (index < samples.size())
//            return samples.get(index++);
//        return null;
//    }

    /**
     * Ir para a próxima amostra
     */
    public void nextSample() {
        if (index < samples.size())
            input = new Input(samples.get(index++));
//            input.setValues(samples.get(index++));
    }

    //Deixar void?
//    public ArrayList<Double> getSample(int i) {
//        if (!samples.get(i).isEmpty())
//            return samples.get(i);
//        return null;
//    }

    /**
     * Obter amostra através de um índice específico
     *
     * @param i índice
     */
    public void getSample(int i) {
        if (!samples.get(i).isEmpty())
            input = new Input(samples.get(i));
//            input.setValues(samples.get(i));
    }

    /**
     * Ir para a próxima amostra
     *
     * @return verdadeiro se tiver próximo, falso se não tiver
     */
    public boolean next() {
        if (samples.get(samples.size() - 1) == null)
            return false;
        return true;
    }

    /**
     * Obter valor mínimo da amostra
     *
     * @return valor
     */
    public double findMin() {
        for (ArrayList<Double> arr : samples) {
            for (double n : arr) {
                if (n < min)
                    min = n;
            }
        }
        return min;
    }

    /**
     * Obter valor máximo da amostra
     *
     * @return valor
     */
    public double findMax() {
        for (ArrayList<Double> arr : samples) {
            for (double n : arr) {
                if (n > max)
                    max = n;
            }
        }
        return max;
    }

    /**
     * Obter input
     *
     * @return input
     */
    public Input getInput() {
        return input;
    }

    /**
     * Definir input
     *
     * @param input inpu
     */
    public void setInput(Input input) {
        this.input = input;
    }

    /**
     * Obter a lista de amostras
     *
     * @return amostras
     */
    public ArrayList<ArrayList<Double>> getSamples() {
        return samples;
    }

    /**
     * Definir a lista de amostras
     *
     * @param samples lista de amostras
     */
    public void setSamples(ArrayList<ArrayList<Double>> samples) {
        this.samples = samples;
    }

    /**
     * Obter índice
     *
     * @return índice
     */
    public int getIndex() {
        return index;
    }

    /**
     * Definir índice
     *
     * @param index índice
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Obter valor mínimo
     *
     * @return valor mínimo
     */
    public double getMin() {
        return min;
    }

    /**
     * Definir valor mínimo
     *
     * @param min valor mínimo
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * Obter valor máximo
     *
     * @return valor máximo
     */
    public double getMax() {
        return max;
    }

    /**
     * Definir valor máximo
     *
     * @param max máximo
     */
    public void setMax(double max) {
        this.max = max;
    }

}
