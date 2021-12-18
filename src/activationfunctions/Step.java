package activationfunctions;

import interfaces.ActivationFunction;

/**
 * Função de transferência Degrau
 *
 * @see Sigmoid
 */
public class Step implements ActivationFunction {

    /**
     * Cálculo função de ativação Degrau
     *
     * @param value valor
     * @return calor calculado
     */
    @Override
    public double calculate(double value) {
        if (value >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Cálculo da derivada função de ativação Degrau
     *
     * @param value valor
     * @return calor calculado
     */
    @Override
    public double derivate(double value) {
        return 0;
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