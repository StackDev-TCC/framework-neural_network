package activationfunctions;

import interfaces.ActivationFunction;

/**
 *Função de transferência Sigmoid
 *
 * @see Step
 */
public class Sigmoid implements ActivationFunction {

    /**
     * Cálculo função de ativação Sigmoid
     *
     * @param value valor
     * @return calor calculado
     */
    @Override
    public double calculate(double value) {
        return 1 / (1 + Math.exp(-value));
    }

    /**
     * Cálculo da derivada função de ativação Sigmoid
     *
     * @param value valor
     * @return calor calculado
     */
    @Override
    public double derivate(double value) {
        return value * (1 - value);
    }

    /**
     * Nome da função de ativação
     *
     * @return nome
     */
    @Override
    public String getName() {
        return this.getClass().getName();
    }
}
