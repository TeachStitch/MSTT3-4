package main.aima.core.logic.fol.inference.proof;

import main.aima.core.logic.fol.kb.data.Clause;
import main.aima.core.logic.fol.kb.data.Literal;
import main.aima.core.logic.fol.parsing.ast.Term;
import main.aima.core.logic.fol.parsing.ast.Variable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Ciaran O'Reilly
 */
public class ProofStepFoChAssertFact extends AbstractProofStep {
    //
    private List<ProofStep> predecessors = new ArrayList<ProofStep>();
    //
    private Clause implication = null;
    private Literal fact = null;
    private Map<Variable, Term> bindings = null;

    public ProofStepFoChAssertFact(Clause implication, Literal fact,
                                   Map<Variable, Term> bindings, ProofStep predecessor) {
        this.implication = implication;
        this.fact = fact;
        this.bindings = bindings;
        if (null != predecessor) {
            predecessors.add(predecessor);
        }
    }

    //
    // START-ProofStep
    @Override
    public List<ProofStep> getPredecessorSteps() {
        return Collections.unmodifiableList(predecessors);
    }

    @Override
    public String getProof() {
        StringBuilder sb = new StringBuilder();
        List<Literal> nLits = implication.getNegativeLiterals();
        for (int i = 0; i < implication.getNumberNegativeLiterals(); i++) {
            sb.append(nLits.get(i).getAtomicSentence());
            if (i != (implication.getNumberNegativeLiterals() - 1)) {
                sb.append(" AND ");
            }
        }
        sb.append(" => ");
        sb.append(implication.getPositiveLiterals().get(0));
        return sb.toString();
    }

    @Override
    public String getJustification() {
        return "Assert fact " + fact.toString() + ", " + bindings;
    }
    // END-ProofStep
    //
}
