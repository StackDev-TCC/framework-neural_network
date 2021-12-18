package interfaces;

import java.util.ArrayList;

//TODO O getInput da interface precisa retornar a camada com os valores iniciais normalizados para a Rede Neural
public interface Input {
    public double[] getInput(ArrayList<Number> in, double max, double min);
    //Todo convert a entrada para o neuronio
    //Todo refresh - atulizar os dados (cursor das amostras)
}
