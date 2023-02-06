package main.aima.core.environment.vacuum;

import main.aima.core.agent.Action;
import main.aima.core.agent.impl.DynamicState;
import main.aima.core.agent.impl.SimpleAgent;
import main.aima.core.agent.impl.aprog.SimpleReflexAgentProgram;
import main.aima.core.agent.impl.aprog.simplerule.EQUALCondition;
import main.aima.core.agent.impl.aprog.simplerule.Rule;

import java.util.LinkedHashSet;
import java.util.Set;

import static main.aima.core.environment.vacuum.VacuumEnvironment.*;

/**
 * @author Ciaran O'Reilly
 * @auhtor Ruediger Lunde
 */
public class SimpleReflexVacuumAgent extends SimpleAgent<VacuumPercept, Action> {

    private static final String ATT_CURRENT_LOCATION = "currentLocation";
    private static final String ATT_CURRENT_STATE = "currentState";

    public SimpleReflexVacuumAgent() {
        super(new SimpleReflexAgentProgram<VacuumPercept, Action>(getRuleSet()) {
            @Override
            protected DynamicState interpretInput(VacuumPercept percept) {
                DynamicState state = new DynamicState();
                state.setAttribute(ATT_CURRENT_LOCATION, percept.getCurrLocation());
                state.setAttribute(ATT_CURRENT_STATE, percept.getCurrState());
                return state;
            }
        });
    }

    private static Set<Rule<Action>> getRuleSet() {
        // Note: LinkedHashSet preserves iteration order (i.e. implied precedence).
        Set<Rule<Action>> rules = new LinkedHashSet<>();

        // Rules based on REFLEX-VACUUM-AGENT:
        // Artificial Intelligence A Modern Approach (3rd Edition): Figure 2.8, page 48.
        rules.add(new Rule<>(new EQUALCondition(ATT_CURRENT_STATE, LocationState.Dirty), ACTION_SUCK));
        rules.add(new Rule<>(new EQUALCondition(ATT_CURRENT_LOCATION, LOCATION_A), ACTION_MOVE_RIGHT));
        rules.add(new Rule<>(new EQUALCondition(ATT_CURRENT_LOCATION, LOCATION_B), ACTION_MOVE_LEFT));

        return rules;
    }
}
