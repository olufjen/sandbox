package no.games.chess.fol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aima.core.logic.fol.inference.proof.Proof;
import aima.core.logic.fol.inference.proof.ProofStep;
import aima.core.logic.fol.parsing.ast.Term;
import aima.core.logic.fol.parsing.ast.Variable;

public class BCProof implements Proof {

	List<ProofStep> proofSteps = new ArrayList<>();
	Map<Variable, Term> answerBindings = new HashMap<>();
	public BCProof(){
	}

	public void addProofStep(ProofStep step){
		proofSteps.add(step);
	}
	@Override
	public List<ProofStep> getSteps() {
		return proofSteps;
	}

	@Override
	public Map<Variable, Term> getAnswerBindings() {
		return answerBindings;
	}

	@Override
	public void replaceAnswerBindings(Map<Variable, Term> updatedBindings) {
		answerBindings = updatedBindings;
	}

}
