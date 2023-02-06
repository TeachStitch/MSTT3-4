package main.aima.core.search.informed;

import main.aima.core.search.framework.Node;

import java.util.function.ToDoubleFunction;

/**
 * Search algorithms which make use of heuristics to guide the search
 * are expected to implement this interface.
 *
 * @author Ruediger Lunde
 */
public interface Informed<S, A> {
    void setHeuristicFunction(ToDoubleFunction<Node<S, A>> h);
}
