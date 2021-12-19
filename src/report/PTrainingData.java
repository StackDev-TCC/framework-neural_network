package report;

public class PTrainingData {
    long timestamp;
    int epoch;
    double newEpochProbability;
    double[] weightVariationByLayer;
    double errorSummary;

    public PTrainingData(long timestamp, int epoch, double newEpochProbability, double[] weightVariationByLayer, double errorSummary) {
        this.timestamp = timestamp;
        this.epoch = epoch;
        this.newEpochProbability = newEpochProbability;
        this.weightVariationByLayer = weightVariationByLayer;
        this.errorSummary = errorSummary;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public double getNewEpochProbability() {
        return newEpochProbability;
    }

    public void setNewEpochProbability(double newEpochProbability) {
        this.newEpochProbability = newEpochProbability;
    }

    public double[] getWeightVariationByLayer() {
        return weightVariationByLayer;
    }

    public void setWeightVariationByLayer(double[] weightVariationByLayer) {
        this.weightVariationByLayer = weightVariationByLayer;
    }

    public double getErrorSummary() {
        return errorSummary;
    }

    public void setErrorSummary(double errorSummary) {
        this.errorSummary = errorSummary;
    }
}
