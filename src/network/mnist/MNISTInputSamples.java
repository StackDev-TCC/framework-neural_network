package network.mnist;

import operations.InputSamples;

import java.util.ArrayList;

public class MNISTInputSamples extends InputSamples {

    /**
     * Valor mínimo
     */
    private int min = 0;
    /**
     * Valor máximo
     */
    private int max = 255;
//    private int index;
//    private int numberImages;
//    private int rows;
//    private int columns;
//    private String filePath;

    /**
     * Instância para os dados mnist
     *
     * @param samples amostras mnist
     */
    public MNISTInputSamples(ArrayList<ArrayList<Double>> samples) {
        super(samples);
    }


}
