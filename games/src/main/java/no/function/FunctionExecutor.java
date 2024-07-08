package no.function;

import aima.core.logic.fol.parsing.ast.Constant;

/**
 * This interface is used to define functions to be executed
 * @author Oluf
 *
 */
public interface FunctionExecutor {
	public Object execute(String... names );
	public Constant findStartpos(String var);
	public Constant findPiecename(String var);
	public Constant findNewpos(String var);
	public Constant findPiecetype(String var);
	
}
