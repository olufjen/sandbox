package no.function;

import java.util.HashMap;

/**
 * This class register and defines functions to be executed
 * @author Oluf
 *
 */
public class FunctionContect {
	 HashMap<String, FunctionExecutor> context=new HashMap<String, FunctionExecutor>();

	    public void register(String name,FunctionExecutor function){
	        context.put(name, function);
	    }

	   public Object call(String name, String... names){
	      return    context.get(name).execute(names);
	   }

	   public FunctionExecutor get(String name){
	      return context.get(name);
	   }
}
