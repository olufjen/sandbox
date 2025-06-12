package no.games.chess.planning;

import aima.core.logic.fol.kb.data.Literal;
import aima.core.logic.fol.parsing.ast.AtomicSentence;
import aima.core.logic.fol.parsing.ast.Constant;
import aima.core.logic.fol.parsing.ast.Term;
import aima.core.logic.planning.ActionSchema;
import aima.core.logic.planning.State;
import aima.core.util.math.permute.PermutationGenerator;
import no.games.chess.fol.util.ChessPermutationGenerator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the chess version of the Problem class used in planning procedures
 * See also page 368
 * Artificial Intelligence A Modern Approach (3rd Edition): page
 * 383.<br>
 * <br>
 * <p>
 * A set of action schemas serves as a definition of a planning domain. A specific problem
 * within the domain is defined with the addition of an initial state and a goal. The initial
 * state is a conjunction of ground atoms.The goal is just like a
 * precondition: a conjunction of literals (positive or negative) that may contain variables, such
 * as At(p, SFO ) ∧ Plane(p). Any variables are treated as existentially quantified, so this goal
 * is to have any plane at SFO. The problem is solved when we can find a sequence of actions
 * that end in a state s that entails the goal.
 *
 * @author samagra
 */
public class ChessProblem {
    private State initialState;// initialState
    private Set<ActionSchema> actionSchemas;// Planning Domain
    private State goalState;// goalState
    private String predSymbol = "occupies";
    private List<String> pieceNames;

    
    public List<String> getPieceNames() {
		return pieceNames;
	}

	public void setPieceNames(List<String> pieceNames) {
		this.pieceNames = pieceNames;
	}

	public ChessProblem(State initialState, State goalState, Set<ActionSchema> actionSchemas) {
        this.initialState = initialState;
        this.actionSchemas = actionSchemas;
        this.goalState = goalState;
    }

    public ChessProblem(State initialState, State goalState, ActionSchema... actions) {
        this(initialState, goalState, new HashSet<>(Arrays.asList(actions)));
    }

    public State getInitialState() {
        return initialState;
    }

    public Set<ActionSchema> getActionSchemas() {
        return actionSchemas;
    }

    public State getGoalState() {
        return goalState;
    }

    public List<Constant> determinConstants(List<Literal>literals) {
    	List<Constant> constants = new ArrayList<>();
     	for (Literal l:literals) {
    		AtomicSentence s = l.getAtomicSentence();
    		String name = s.getSymbolicName();
    		if (name.equals(predSymbol)) {
				List<Term> terms = (List<Term>) s.getArgs();
				Term f = terms.get(0);
				Term p = terms.get(1);
				if(f instanceof Constant) {
					 if (!constants.contains((Constant) f))
	                        constants.add((Constant) f);
				}
				if(p instanceof Constant) {
					 if (!constants.contains((Constant) p))
	                        constants.add((Constant) p);
				}

    		}
    	}
     	return constants;
    }
    /**
     * @return Constants for a particular problem domain.
     * @since 11.02.21 
     */
    public List<Constant> getProblemConstants() {
    	List<Constant> constants = determinConstants(getInitialState().getFluents());
     	List<Constant> goallConstants = determinConstants(getGoalState().getFluents());

     	for (Constant gc:goallConstants) {
     		if (!constants.contains(gc)) {
     			constants.add(gc);
     		}
     	}
 /*     	if (!constants.containsAll(goallConstants)) {
     		constants.addAll(goallConstants);
     	}*/
   /*        for (Literal literal :
                getInitialState().getFluents()) {
            for (Term term :
                    literal.getAtomicSentence().getArgs()) {
                if (term instanceof Constant) {
                    if (!constants.contains((Constant) term))
                        constants.add((Constant) term);
                }
            }
        }
        for (Literal literal :
                getGoalState().getFluents()) {
            for (Term term :
                    literal.getAtomicSentence().getArgs()) {
                if (term instanceof Constant) {
                    if (!constants.contains((Constant) term))
                        constants.add((Constant) term);
                }
            }
        }*/
        for (ActionSchema actionSchema :
                getActionSchemas()) {
            for (Constant constant :
                    actionSchema.getConstants()) {
                if (!constants.contains(constant))
                    constants.add(constant);
            }
        }
        return constants;
    }

    /**
     * getPropositionalisedActions
     * In the original version, this method returns all permutations of the action schema based on all the
     * Constants.
     * This method must be reworked so that it returns a proper set of refinements for an HLA.
     * The refinements are primitive actions produced by this method
     * See section 11.2.1 p. 406
     * 
     * @return Propositionalises all the actionschemas to return a set of possible ground actions
     */
    public List<ActionSchema> getPropositionalisedActions() {
        List<Constant> problemConstants = getProblemConstants();
        List<ActionSchema> result = new ArrayList<>();
        for (ActionSchema actionSchema :
                getActionSchemas()) {
            int numberOfVars = actionSchema.getVariables().size();
            for (List<Constant> constants :
                    ChessPermutationGenerator.generatePermutations(problemConstants, numberOfVars)) {
                result.add(actionSchema.getActionBySubstitution(constants));
            }
        }
        return result;
    }
    /**
     * getGroundActions
     * This method returns a proper set of refinements for an HLA.
     * The refinements are primitive actions produced by this method
     * See section 11.2.1 p. 406
     * @return
     */
    public List<ActionSchema> getGroundActions() {
    	List<ActionSchema> result = new ArrayList<>();
    	List<Constant> problemConstants = getProblemConstants();
    	List<ActionSchema> schemas = new ArrayList<ActionSchema>(getActionSchemas());
    	ActionSchema schema = schemas.get(0);
    	result.add(schema);
    	 for (ActionSchema actionSchema :
             getActionSchemas()) {
    		 result.add(actionSchema.getActionBySubstitution(problemConstants)); 
    	 }
    	return result;
    }
}
