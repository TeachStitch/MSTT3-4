package main.aima.core.learning.neural;

import main.aima.core.learning.framework.Example;
import main.aima.core.util.datastructure.Pair;

import java.util.List;

/**
 * A Numerizer understands how to convert an example from a particular data set
 * into a <code>Pair</code> of lists of doubles. The first represents the input
 * to the neural network, and the second represents the desired output. See
 * <code>IrisDataSetNumerizer</code> for a concrete example
 *
 * @author Ravi Mohan
 */
public interface Numerizer {
    Pair<List<Double>, List<Double>> numerize(Example e);

    String denumerize(List<Double> outputValue);
}
