package no.function;

import java.util.HashMap;

import aima.core.logic.fol.parsing.ast.Constant;
import aima.core.logic.fol.parsing.ast.Term;

/**
 * This interface is used to define functions to be executed
 * It is specialized for creating Terms to be used in creating Predicates (See Knowledgebuilder)
 * @author Oluf
 *
 */

public interface FunctionExecutor {
	public Object execute();
	public void buildTerms(HashMap<String,Term> cParam,HashMap<String,Term> vParam);
	
/*	public Constant findStartpos(String var);
	public Constant findPiecename(String var);
	public Constant findNewpos(String var);
	public Constant findPiecetype(String var);
	*/
}
