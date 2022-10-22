package no.games.chess.planning;

import aima.core.logic.fol.kb.data.Literal;
import aima.core.logic.fol.parsing.ast.Constant;
import aima.core.logic.fol.parsing.ast.Predicate;
import aima.core.logic.fol.parsing.ast.Term;
import aima.core.logic.fol.parsing.ast.Variable;
import aima.core.logic.planning.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 416.<br>
 * <p>
 * A Percept Schema is created to solve a partially observable problem as described on page 416
 * A Precept Schema is essentially a copy of the Action Schema, with the following changes:
 * The schema consists of the percept  name, a list of all the variables used in the schema, and a
 * precondition. A precept schema has no effects.
 * The rule (p. 368) An action a is applicable in state s if the preconditions of a are satisfied by s.
 * @author Oluf Jensen
 */
public class PerceptSchema {
    List<Term> variables;// list of variables
    List<Literal> precondition; //PRECONDITION: treated as a conjunction of fluents

    private String name;//action name

    public PerceptSchema(String name, List<Term> variables,
                        List<Literal> precondition) {
        if (variables == null)
            variables = new ArrayList<>();
        this.name = name;
        this.variables = variables;
        this.precondition = precondition;

     }

    public PerceptSchema(String name, List<Term> variables, String precondition) {
        this(name, variables, Utils.parse(precondition));
    }



    @Override
    public String toString() {
        String result = "Percept(" + this.getName() + ")\n\tPRECOND:";
        for (Literal precond :
                getPrecondition()) {
            result = result + "^" + precond.toString();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PerceptSchema))
            return false;
        return this.getName().equals(((PerceptSchema) obj).getName()) &&
                this.getPrecondition().containsAll(((PerceptSchema) obj).getPrecondition())
                && ((PerceptSchema) obj).getPrecondition().containsAll(this.getPrecondition());
  
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        for (Literal preCo :
                this.getPrecondition()) {
            hashCode = 37 * hashCode + preCo.hashCode();
        }
        for (Term var :
                this.getVariables()) {
            hashCode = 37 * hashCode + var.hashCode();
        }
        return hashCode;
    }

    /**
     * This function generates ground actions from a given actionschema.
     *
     * @param constants The list of constants from which to generate ground actions.
     * @return A ground action.
     */
    public PerceptSchema getActionBySubstitution(List<Constant> constants) {
        List<Literal> precondList = this.getPrecondition();
        List<Term> vars = this.getVariables();
        List<Literal> newPrecond = new ArrayList<>();
        for (Literal precondition :
                precondList) {
            List<Term> newTerms = new ArrayList<>();
            for (Term variable :
                    precondition.getAtomicSentence().getArgs()) {
                if (variable instanceof Variable) {
                    newTerms.add(constants.get(vars.lastIndexOf(variable)));
                } else
                    newTerms.add(variable);
            }
            newPrecond.add(new Literal(new
                    Predicate(precondition.getAtomicSentence().getSymbolicName(),
                    newTerms), precondition.isNegativeLiteral()));
        }
        return new PerceptSchema(this.getName(), null, newPrecond);
    }

    /**
     * To extract constants from an action.
     *
     * @return A list of constants from the acton.
     */
    public List<Constant> getConstants() {
        List<Constant> constants = new ArrayList<>();
        for (Constant constant :
                extractConstant(getPrecondition())) {
            if (!constants.contains(constant))
                constants.add(constant);
        }

        return constants;
    }

    public List<Constant> extractConstant(List<Literal> list) {
        List<Constant> result = new ArrayList<>();
        for (Literal literal :
                list) {
            for (Term term :
                    literal.getAtomicSentence().getArgs()) {
                if (term instanceof Constant && !list.contains(term))
                    result.add((Constant) term);
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public List<Term> getVariables() {
        return variables;
    }

    public List<Literal> getPrecondition() {
        return precondition;
    }

 
}
