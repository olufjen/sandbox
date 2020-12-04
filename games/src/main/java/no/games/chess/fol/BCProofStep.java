package no.games.chess.fol;

import java.util.ArrayList;
import java.util.List;

import aima.core.logic.fol.inference.proof.AbstractProofStep;
import aima.core.logic.fol.inference.proof.ProofStep;
import aima.core.logic.fol.kb.data.Clause;
import aima.core.logic.fol.kb.data.Literal;

public class BCProofStep extends AbstractProofStep {

	List<ProofStep> predecessors = new ArrayList<>();
	Clause implication ;
	Literal goal;

	public BCProofStep(Clause implication, List<ProofStep> predecessors, Literal goal){
		this.implication = implication;
		this.predecessors = predecessors;
		this.goal = goal;
		this.setStepNumber(this.predecessors.size()+1);
	}

	@Override
	public List<ProofStep> getPredecessorSteps() {
		return predecessors;
	}

	@Override
	public String getProof() {
		return this.toString();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (implication.getLiterals().size()>1){
			int i =0;
			for (Literal l :
					implication.getNegativeLiterals()) {
				result.append((new Literal(l.getAtomicSentence())).toString());
				i++;
				if (i<implication.getNegativeLiterals().size())
					result.append(" AND ");
			}
			result.append(" => ");
			result.append(implication.getPositiveLiterals().get(0));
			return result.toString();
		}
		result.append(implication.getLiterals().toString());
		return result.toString();
	}

	@Override
	public String getJustification() {
		return "To Prove Backwards :" + goal.toString();
	}
}
