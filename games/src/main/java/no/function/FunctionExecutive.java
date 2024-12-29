package no.function;
/**
 * This interface is used to define functions to be executed
 * when searching for the best move
 * The execute function has a generic return type.
 * Implementations must define the return type
 * @author Oluf
 *
 */
public interface FunctionExecutive<R> {
	public  R execute();
}
