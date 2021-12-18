package interfaces;

import kernel.NeuralNetwork;
import operations.TrainingNetwork;

/**
 * Interface {@code ActivationFunction} obriga as classes que a implementem a definir
 * dois métodos: {@link #calculate(double)} e {@link #derivate(double)}. Esta classe
 * será membro de {@link kernel.Neuron}. Ambos os métodos são necessparios para caracterizar
 * uma classe como pertencente a uma função de ativação.
 * Estes métodos serão chamados quando a rede neural criada por {@link NeuralNetwork}
 * necessitar classificar um elemento de entrada, ao executar o método {@link NeuralNetwork#propagate()},
 * chamada tanto por {@link TrainingNetwork#feedForward()}, como por qualquer classe que usará a rede para
 * classificação de sua entrada.
 * Já o método {@link #derivate(double)} será usado apenas durante o treinamento, para reajustar os
 * pesos dos {@link kernel.Connection} através de {@link TrainingNetwork#backPropagation()}.
 */
public interface ActivationFunction {

    /**
     * Implementar a fórmula de transofrmação de {@code value} para um valor de ativação
     * de um determinado {@link kernel.Neuron}.
     * @param value valor armazenado em um {@code Neuron}
     * @return Retorna o valor ajustado conforme a função implementada
     */
    public double calculate(double value);

    /**
     * Inplementar a derivada da função usada em {@link #calculate(double)}, para ser
     * utilizada quando chamar {@link TrainingNetwork#backPropagation()}
     * @param value valor atual de um neurônio
     * @return Retorna a derivada implementada da função presente em {@link #calculate(double)}
     */
    public double derivate(double value);

    /**
     * Sugere a criação de um nome para a  {@code ActivationFunction}
     * @return O {@code label} de uma determinada {@code ActivationFunction}
     */
    public String getName();

}
