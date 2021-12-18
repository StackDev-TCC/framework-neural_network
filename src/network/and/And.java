package network.and;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class And {
    private ArrayList<ArrayList<Double>> andData = new ArrayList<>();

    public ArrayList<ArrayList<Double>> getAllANDData() {
        ArrayList<Double> a = new ArrayList<>();
        ArrayList<Double> b = new ArrayList<>();
        ArrayList<Double> c = new ArrayList<>();
        ArrayList<Double> d = new ArrayList<>();
        a.addAll(Arrays.asList(0.0, 0.0));
        b.addAll(Arrays.asList(1.0, 0.0));
        c.addAll(Arrays.asList(0.0, 1.0));
        d.addAll(Arrays.asList(1.0, 1.0));
        andData.addAll(Arrays.asList(a, b, c, d));

        return andData;
    }

}
