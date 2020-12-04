package no.games.chess.fol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import aima.core.logic.fol.inference.InferenceResult;
import aima.core.logic.fol.inference.proof.Proof;
import aima.core.logic.fol.inference.proof.ProofStep;
import aima.core.logic.fol.parsing.ast.Term;
import aima.core.logic.fol.parsing.ast.Variable;
import no.games.chess.fol.BCProof;

public class BCGamesAskHandler implements InferenceResult {

	private ProofStep stepFinal = null;
	private List<Proof> proofs = new ArrayList<>();
	private List<HashMap<Variable,Term>> finalList;

	public BCGamesAskHandler(){
		proofs.add(new BCProof());
	}
	
	
	public List<HashMap<Variable, Term>> getFinalList() {
		return finalList;
	}


	public void setFinalList(List<HashMap<Variable, Term>> finalList) {
		this.finalList = finalList;
	}


	@Override
	public boolean isPossiblyFalse() {
		return finalList == null || finalList.isEmpty();
	}

	@Override
	public boolean isTrue() {
		return (finalList != null && !finalList.isEmpty());
	}

	@Override
	public boolean isUnknownDueToTimeout() {
		return false;
	}

	@Override
	public boolean isPartialResultDueToTimeout() {
		return false;
	}

	@Override
	public List<Proof> getProofs() {
		return proofs;
	}

	public void addProofStep(ProofStep step){
		((BCProof)this.proofs.get(0)).addProofStep(step);
	}
	/**
	 * clearLists
	 * This method clear all lists for the next query
	 * @author olj
	 */
	public void clearLists() {
		proofs.clear();
		if (finalList != null)
			finalList.clear();
		proofs.add(new BCProof());
	}
	public String toString() {
		StringBuilder result = new StringBuilder();
		String head = "The BC Ask Handler\n";
		result.append(head);
		if (finalList != null) {
			for (HashMap<Variable,Term> terms:finalList){
				Set<Variable> vars = terms.keySet();
				result.append(vars.toString()+"\n");
			}
		}
		return result.toString();
	}

}
