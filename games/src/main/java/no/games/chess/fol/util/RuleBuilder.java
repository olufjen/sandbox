package no.games.chess.fol.util;

import java.util.ArrayList;
import java.util.List;

import aima.core.logic.fol.Connectors;
import aima.core.logic.fol.Quantifiers;
import aima.core.logic.fol.parsing.ast.ConnectedSentence;
import aima.core.logic.fol.parsing.ast.Constant;
import aima.core.logic.fol.parsing.ast.Variable;
import aima.core.logic.fol.parsing.ast.Term;

import aima.core.logic.fol.parsing.ast.Predicate;
import aima.core.logic.fol.parsing.ast.QuantifiedSentence;
import aima.core.logic.fol.parsing.ast.Sentence;

/**
 * RuleBuilder
 * This RuleBuilder creates rules to the Knowlegdebase based on textlines in Prolog syntax
 * 
 * 
 */
public class RuleBuilder {
    
	
    public Sentence createForAll(List<Variable> vars, Sentence sentence) {
        return new QuantifiedSentence(Quantifiers.FORALL, vars, sentence);
    }
// Hjelper for EXISTS
    public static Sentence createExists(List<Variable> vars, Sentence sentence) {
        return new QuantifiedSentence(Quantifiers.EXISTS, vars, sentence);
    }
	
	
	/*
     * createImplication
     * This method creates a binary implication  A => B
     * @param antecedent - the antecedent sentence
     * @param consequent - the consequent sentence
     * @return the complete sentence
     */
    public Sentence createImplication(Sentence antecedent, Sentence consequent) {
        return new ConnectedSentence(Connectors.IMPLIES, antecedent, consequent);
    }

  
    /**
     * createAnd
     * This method creates Connected sentences with AND with a recursive procedure
     * @param sentences sentences to be connected
     * @return the connected sentence
     */
    public Sentence createAnd(Sentence... sentences) {
        if (sentences.length == 0) throw new IllegalArgumentException("Need sentences");
        Sentence result = sentences[0];
        for (int i = 1; i < sentences.length; i++) {
            result = new ConnectedSentence(Connectors.AND, result, sentences[i]);
        }
        return result;
    }

    // Hjelper for Predikat
    /**
     * createPred
     * This method creates a Predicate based on
     * @param name the name of the Predicate
     * @param terms the set of terms - Capital letters are Constants small letters are Variables
     * @return the Predicate
     */
    public Predicate createPred(String name, String... terms) {
        List<Term> termList = new ArrayList<>();
        for (String t : terms) {
            termList.add(Character.isUpperCase(t.charAt(0)) ? new Constant(t) : new Variable(t));
        }
        return new Predicate(name, termList);
    }
}
