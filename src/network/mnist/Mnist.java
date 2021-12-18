package network.mnist;

import java.io.*;
import java.util.ArrayList;

public class Mnist {

    private ArrayList<ArrayList<Double>> mnistData = new ArrayList<>();
    private ArrayList<Double> labels = new ArrayList<>();

    public ArrayList<ArrayList<Double>> getAllMnistData() throws IOException {

        //TREINAMENTO DEFINIR ARQUIVO DE IMAGEM (train-images-idx3-ubyte):
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("C:\\train-images.idx3-ubyte")));
//        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("data/test.wav")));
        //Byte mais significativo
        int numeroMagico = dataInputStream.readInt();
        int numeroDeImagens = dataInputStream.readInt();
        int linhas = dataInputStream.readInt();
        int colunas = dataInputStream.readInt();
//
//        System.out.println("Número mágico: " + numeroMagico);
//        System.out.println("Número de imagens: " + numeroDeImagens);
//        System.out.println("Linhas: " + linhas);
//        System.out.println("Colunas: " + colunas);

        //TRAINING SET LABEL FILE (train-labels-idx1-ubyte):
        DataInputStream labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("C:\\train-labels.idx1-ubyte")));
//        int digito = labelInputStream.readInt();
//        int numeroDeDigitos = labelInputStream.readInt();
        for (int i = 0; i < numeroDeImagens; i++) {
            ArrayList<Double> individualData = new ArrayList<>();
            labels.add((double) labelInputStream.readUnsignedByte());
            for (int j = 0; j < (linhas * colunas); j++) {
                individualData.add((double) dataInputStream.readUnsignedByte());
            }
            mnistData.add(i, individualData);
        }
        dataInputStream.close();
        labelInputStream.close();
        return mnistData;
    }

    public ArrayList<Double> getLabels() {
        return labels;
    }
}