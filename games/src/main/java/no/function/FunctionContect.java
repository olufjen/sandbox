package no.function;

import java.util.HashMap;

/**
 * This class register and defines functions to be executed
 * These functions are held in a Map with the name of the function as the key.
 * @author Oluf
 *
 */
public class FunctionContect {
	private HashMap<String, FunctionExecutor> context;

	public FunctionContect() {
		super();
		context = new HashMap<String,FunctionExecutor>();
	}
	public HashMap<String, FunctionExecutor> getContext() {
		return context;
	}

	public void setContext(HashMap<String, FunctionExecutor> context) {
		this.context = context;
	}

	/**
	 * register
	 * This method register functions to be used in the context Map
	 * @param name - The name of the function
	 * @param function - The function implementing the FunctionExecutor interface
	 */
	public void register(String name,FunctionExecutor function){
		context.put(name, function);
	}

	public Object call(String name){
		return    context.get(name).execute();
	}

	/**
	 * get
	 * This method returns the FunctionExecutor with the given name
	 * @param name - The name of the FunctionExecutor
	 * @return
	 */
	public FunctionExecutor get(String name){
		return context.get(name);
	}
}
