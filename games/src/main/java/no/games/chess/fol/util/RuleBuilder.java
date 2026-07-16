package no.games.chess.fol.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import aima.core.logic.fol.Connectors;
import aima.core.logic.fol.Quantifiers;
import aima.core.logic.fol.parsing.ast.ConnectedSentence;
import aima.core.logic.fol.parsing.ast.Constant;
import aima.core.logic.fol.parsing.ast.NotSentence;
import aima.core.logic.fol.parsing.ast.Variable;
import aima.core.logic.fol.parsing.ast.Term;
import aima.core.logic.fol.parsing.ast.TermEquality;
import aima.core.logic.fol.parsing.ast.Predicate;
import aima.core.logic.fol.parsing.ast.QuantifiedSentence;
import aima.core.logic.fol.parsing.ast.Sentence;

/**
 * RuleBuilder
 * This RuleBuilder creates rules to the Knowlegdebase based on simple textlines predicates of the form
 * premises: {"REACHABLE(a,b)", "PAWN(a)", "CENTERSQUARE(b)"} and 
 * conclusion: "PAWNMOVE(a,b)"
 */
public class RuleBuilder {
    
	/**
	 * defineRule
	 * This method creates rules to the Knowlegdebase based on simple textlines predicates of the form
	 * premises: {"REACHABLE(a,b)", "PAWN(a)", "CENTERSQUARE(b)"} and 
	 * conclusion: "PAWNMOVE(a,b)"
	 * @param conclusion the conclusion of the rule
	 * @param premises the premises of the rule
	 * @return
	 */
	public Sentence defineRule(String conclusionStr, String... premisesStr) {
	    // 1. Konverter konklusjon og premisser til Predicate-objekter
	    Sentence conclusion = parseToPredicate(conclusionStr);
	    List<Sentence> pList = new ArrayList<>();
	    for (String s : premisesStr) {
	        pList.add(parseToPredicate(s));
	    }

	    // 2. Finn alle variabler automatisk
	    List<Variable> vars = extractVariables(pList, conclusion);

	    // 3. Bygg regelen: FORALL vars ((P1 AND P2 ...) => C)
	    return createForAll(vars, createImplication(createAnd(pList.toArray(new Sentence[0])), conclusion));
	}
    /**
     * createForAll
     * THis method creates rules in prolog format and the FORALL quantifier 
     * @param vars
     * @param sentence
     * @return
     */
    public Sentence createForAll(List<Variable> vars, Sentence sentence) {
        return new QuantifiedSentence(Quantifiers.FORALL, vars, sentence);
    }
// Hjelper for EXISTS
    public Sentence createExists(List<Variable> vars, Sentence sentence) {
        return new QuantifiedSentence(Quantifiers.EXISTS, vars, sentence);
    }
	
    public Sentence createNot(Sentence s) {
        return new NotSentence(s);
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
     * @since 08.07.26
     * Also works with the connector NOT and equality inequality
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
    public Sentence parseToPredicate(String input) {
        String trimmed = input.trim();

        // 1. Håndter NOT-konnektoren
        if (trimmed.startsWith("NOT(") && trimmed.endsWith(")")) {
            String inner = trimmed.substring(4, trimmed.length() - 1);
            return new NotSentence(parseToPredicate(inner));
        }

        // 2. Håndter likhet/ulikhet (f.eks. s1==s2)
        if (trimmed.contains("==")) {
            String[] parts = trimmed.split("==");
            Term t1 = Character.isUpperCase(parts[0].trim().charAt(0)) ? new Constant(parts[0].trim()) : new Variable(parts[0].trim());
            Term t2 = Character.isUpperCase(parts[1].trim().charAt(0)) ? new Constant(parts[1].trim()) : new Variable(parts[1].trim());
            return new TermEquality(t1, t2);
        }
        if (trimmed.contains("!=")) {
            String[] parts = trimmed.split("!=");
            Term t1 = Character.isUpperCase(parts[0].trim().charAt(0)) ? new Constant(parts[0].trim()) : new Variable(parts[0].trim());
            Term t2 = Character.isUpperCase(parts[1].trim().charAt(0)) ? new Constant(parts[1].trim()) : new Variable(parts[1].trim());
            
            // VIKTIG: Pakk TermEquality inn i en NotSentence for å oppnå "IKKE LIK"
            return new NotSentence(new TermEquality(t1, t2));
        }
        // 3. Standard predikat-parsing (din eksisterende kode)
        String name = trimmed.substring(0, trimmed.indexOf('('));
        String[] args = trimmed.substring(trimmed.indexOf('(') + 1, trimmed.indexOf(')')).split(",");
        
        List<Term> terms = new ArrayList<>();
        for (String arg : args) {
            String t = arg.trim();
            terms.add(Character.isUpperCase(t.charAt(0)) ? new Constant(t) : new Variable(t));
        }
        return new Predicate(name, terms);
    }
    
    private List<Variable> extractVariables(List<Sentence> premises, Sentence conclusion) {
        Set<String> varNames = new HashSet<>();
        List<Sentence> allSentences = new ArrayList<>(premises);
        allSentences.add(conclusion);

        for (Sentence s : allSentences) {
            findVars(s, varNames);
        }

        return varNames.stream().map(Variable::new).collect(Collectors.toList());
    }

    // Hjelpemetode for å finne variabler rekursivt i alle setningstyper
    private void findVars(Sentence s, Set<String> varNames) {
        if (s instanceof Predicate) {
            for (Term t : ((Predicate) s).getTerms()) {
                if (t instanceof Variable) varNames.add(((Variable) t).getValue());
            }
        } else if (s instanceof NotSentence) {
            findVars(((NotSentence) s).getNegated(), varNames);
        } else if (s instanceof TermEquality) {
            TermEquality te = (TermEquality) s;
            if (te.getTerm1() instanceof Variable) varNames.add(((Variable) te.getTerm1()).getValue());
            if (te.getTerm2() instanceof Variable) varNames.add(((Variable) te.getTerm2()).getValue());
        }
    }
}
