package no.games.chess.fol;

import java.util.*;

import aima.core.logic.fol.Unifier;
import aima.core.logic.fol.inference.InferenceProcedure;
import aima.core.logic.fol.inference.InferenceResult;
import aima.core.logic.fol.inference.proof.Proof;
import aima.core.logic.fol.inference.proof.ProofStep;
import aima.core.logic.fol.kb.FOLKnowledgeBase;
import aima.core.logic.fol.kb.data.Clause;
import aima.core.logic.fol.kb.data.Literal;
import aima.core.logic.fol.parsing.ast.AtomicSentence;
import aima.core.logic.fol.parsing.ast.Sentence;
import aima.core.logic.fol.parsing.ast.Term;
import aima.core.logic.fol.parsing.ast.Variable;
import aima.core.logic.propositional.kb.KnowledgeBase;

/**
 * @since 30.11.20
 * This is my own implementation of The backward chaining inference procedure OLJ 30.11.20
 * It is a copy of FOLBCAsk
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 9.6, page
 * 338.<br>
 * <br>
 *
 * <pre>
 * function FOL-BC-ASK(KB, query) returns a generator of substitutions
  *   return FOL-BC-OR(KB, query, { })
 *
 * generator FOL-BC-OR(KB, goal, θ) yields a substitution
  *   for each rule (lhs ⇒ rhs) in FETCH-RULES-FOR-GOAL(KB, goal) do
  *        (lhs, rhs) ← STANDARDIZE-VARIABLES((lhs, rhs))
  *  	  for each θ' in FOL-BC-AND(KB, lhs, UNIFY(rhs, goal, θ)) do
  *    	       yield θ'
 *
 * generator FOL-BC-AND(KB, goals, θ) yields a substitution
  *   if θ = failure then return
  *   else if LENGTH(goals) = 0 then yield θ
  *   else do
  *        first, rest ← FIRST(goals), REST(goals)
  *        for each θ' in FOL-BC-OR(KB, SUBST(θ, first), θ) do
  *             for each θ'' in FOL-BC-AND(KB, rest, θ') do
  *                  yield θ'
 * </pre>
 *
 * Figure 9.6 A simple backward-chaining algorithm for first-order knowledge bases.
 *
 * @author samagra
 * @author Ritwik Sharma
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 */
public class FOLGamesBCAsk implements InferenceProcedure{
	List<List<Literal>> finalAnswer;// to store the final result
	List<Literal> substitutedLiterals;
	List<HashMap<Variable,Term>> finalList;
	BCGamesAskHandler bcaskHandler = new BCGamesAskHandler();
	public boolean maybeFalse = false;

	public FOLGamesBCAsk() {
		finalAnswer = new ArrayList<>();
		substitutedLiterals = new ArrayList<>();
	}

	public List<Literal> getSubstitutedLiterals() {
		return substitutedLiterals;
	}
	//
	// START-InferenceProcedure
	/**
	 * Returns a set of substitutions
	 * function FOL-BC-ASK(KB, query) returns a generator of substitutions
	 * @param kb
	 *            a knowledge base
	 * @param query
	 *            goals, a list of conjuncts forming a query
	 *
	 * @return a set of substitutions
	 */
	public List<HashMap<Variable,Term>> folBcAsk(FOLKnowledgeBase kb, Literal query){
		//return FOL-BC-OR(KB, query, { })
		return folBcOr(kb,query, new HashMap<>());
	}

	/**
	 * generator FOL-BC-OR(KB, goal, θ) yields a substitution
	 * @param kb
	 * 			The knowleadge base
	 * @param goal
	 * 			The goals at the or node to be achieved
	 * @param theta
	 * 			Substitution
	 * @return
	 * 		a list of substitutions
	 */
	private List<HashMap<Variable, Term>> folBcOr(FOLKnowledgeBase kb, Literal goal, HashMap<Variable, Term> theta) {
		List<HashMap<Variable,Term>> result = new ArrayList<>();
		finalAnswer.add(new ArrayList<>(Collections.singletonList(goal)));
		HashMap<Variable,Term> temp;
		if (fetchRulesForGoal(kb,goal).isEmpty())
			maybeFalse = true;
		// for each rule (lhs ⇒ rhs) in FETCH-RULES-FOR-GOAL(KB, goal) do
		for (Clause rule :
				fetchRulesForGoal(kb,goal)) {
			//(lhs, rhs) ← STANDARDIZE-VARIABLES((lhs, rhs))
			Clause tempClause = kb.standardizeApart(rule);
			temp = new HashMap<>(theta);
			Literal rhs = tempClause.getPositiveLiterals().get(0);
			List<Literal> lhs = new ArrayList<>();
			for (Literal literal :
					tempClause.getNegativeLiterals() ){
				lhs.add(new Literal(literal.getAtomicSentence(),!literal.isNegativeLiteral()));
			}
			//for each θ' in FOL-BC-AND(KB, lhs, UNIFY(rhs, goal, θ)) do
			// yield θ'
			result.addAll(folBcAnd(kb, lhs, new Unifier().unify(rhs.getAtomicSentence(), goal.getAtomicSentence(), temp)));
		}
		return result;
	}

	/**
	 * generator FOL-BC-AND(KB, goals, θ) yields a substitution
	 * @param kb
	 * @param goals
	 * @param theta
	 * @return
	 */
	private List<HashMap<Variable, Term>> folBcAnd(FOLKnowledgeBase kb, List<Literal> goals, Map<Variable, Term> theta) {
		List<HashMap<Variable,Term>> result = new ArrayList<>();
		finalAnswer.add(new ArrayList<>(goals));
		// if θ = failure then return
		if (theta==null)
			return result;
			// else if LENGTH(goals) = 0 then yield θ
		else if (goals.size()==0){
			result.add((HashMap<Variable, Term>) theta);
			return result;
		}
		// else do
		else {
			// first, rest ← FIRST(goals), REST(goals)
			Literal first = goals.get(0);
			List<Literal> rest = new ArrayList<>(goals);
			rest.remove(0);
			// for each θ' in FOL-BC-OR(KB, SUBST(θ, first), θ) do
			for (HashMap<Variable, Term> thetaPrime :
					folBcOr(kb,kb.subst(theta,first),(HashMap<Variable,Term>)theta)) {
				substitutedLiterals.add(kb.subst(theta,first));
				// for each θ'' in FOL-BC-AND(KB, rest, θ') do
				// yield θ'
				result.addAll(folBcAnd(kb, rest, thetaPrime));
			}
		}
		return result;
	}

	/**
	 * Fetches all those implication clauses whose rhs meet with the goal
	 * @param kb
	 * @param goal
	 * @return
	 */
	private List<Clause> fetchRulesForGoal(FOLKnowledgeBase kb, Literal goal){
		List<Clause> result = new ArrayList<>();
		for (Clause clause :
				kb.getAllDefiniteClauseImplications()) {
			Literal rhs = clause.getPositiveLiterals().get(0);
			if (rhs.getAtomicSentence().getSymbolicName().equals(goal.getAtomicSentence().getSymbolicName())){
				result.add(clause);
			}
		}
		for (Clause clause :
				kb.getAllClauses()) {
			if (clause.isUnitClause()){
				for (Literal l :
						clause.getLiterals()) {
					if (l.getAtomicSentence().getSymbolicName().equals(goal.getAtomicSentence().getSymbolicName())) {
						result.add(clause);
					}
				}
			}
		}
		for (Clause clause :
				result) {
			ProofStep step = new BCProofStep(clause,this.bcaskHandler.getProofs().get(0).getSteps(),goal);
			this.bcaskHandler.addProofStep(step);
		}
		return result;
	}

	public List<List<Literal>> getFinalAnswer() {
		return finalAnswer;
	}

	/**
	 * clearLists
	 * This method clear all lists for the next query
	 * @author olj
	 */
	public void clearLists() {
		finalAnswer.clear();// to store the final result
		substitutedLiterals.clear();
		if (finalList != null)
			finalList.clear();;
	}
	@Override
	public InferenceResult ask(FOLKnowledgeBase kb, Sentence query) {
		clearLists();
		this.bcaskHandler.clearLists();
		Literal l = new Literal(((AtomicSentence) query));
		List<HashMap<Variable, Term>> substitutes = this.folBcAsk(kb, l);
		this.finalList = substitutes;
		if (l.getAtomicSentence().getArgs().get(0) instanceof Variable) {
			Variable x = (Variable) l.getAtomicSentence().getArgs().get(0);
			for (HashMap<Variable, Term> subs :
					substitutes) {
				HashMap<Variable, Term> toadd = new HashMap<>();
				toadd.put(new Variable(x.getValue()), subs.get(x));
				Proof proof = new BCProof();
				proof.replaceAnswerBindings(new HashMap<>(toadd));
				((BCProof) proof).proofSteps = new ArrayList<>(this.bcaskHandler.getProofs().get(0).getSteps());
				this.bcaskHandler.getProofs().add(proof);
			}
		}
		if (this.bcaskHandler.getProofs().size()>1)
			this.bcaskHandler.getProofs().remove(0);
		this.bcaskHandler.setFinalList(finalList);
		return this.bcaskHandler;
	}




}