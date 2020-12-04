package no.games.chess.fol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aima.core.logic.fol.inference.InferenceResult;
import aima.core.logic.fol.inference.proof.Proof;
import aima.core.logic.fol.inference.proof.ProofFinal;
import aima.core.logic.fol.inference.proof.ProofStep;
import aima.core.logic.fol.inference.proof.ProofStepFoChAssertFact;
import aima.core.logic.fol.kb.data.Clause;
import aima.core.logic.fol.kb.data.Literal;
import aima.core.logic.fol.parsing.ast.Term;
import aima.core.logic.fol.parsing.ast.Variable;

public class FCGamesAskAnswerHandler implements InferenceResult {

	private ProofStep stepFinal = null;
	private List<Proof> proofs = new ArrayList<Proof>();

	public FCGamesAskAnswerHandler() {

	}

	public ProofStep getStepFinal() {
		return stepFinal;
	}

	public void setStepFinal(ProofStep stepFinal) {
		this.stepFinal = stepFinal;
	}

	public void setProofs(List<Proof> proofs) {
		this.proofs = proofs;
	}

	//
	// START-InferenceResult
	public boolean isPossiblyFalse() {
		return proofs.size() == 0;
	}

	public boolean isTrue() {
		return proofs.size() > 0;
	}

	public boolean isUnknownDueToTimeout() {
		return false;
	}

	public boolean isPartialResultDueToTimeout() {
		return false;
	}

	public List<Proof> getProofs() {
		return proofs;
	}

	// END-InferenceResult
	//
	public void clearProofs() {
		proofs.clear();
	}
	public void addProofStep(Clause implication, Literal fact,
			Map<Variable, Term> bindings) {
		stepFinal = new ProofStepFoChAssertFact(implication, fact,
				bindings, stepFinal);
	}

	public void addProofStep(ProofStep step) {
		stepFinal = step;
	}

	public void setAnswers(Set<Map<Variable, Term>> answers) {
		for (Map<Variable, Term> ans : answers) {
			proofs.add(new ProofFinal(stepFinal, ans));
		}
	}

}
