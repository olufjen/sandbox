package function;

import java.util.HashMap;

/**
 * This class defines functions to be executed
 * @author Oluf
 *
 */
public class FunctionContect {
	 HashMap<String, FunctionExecutor> context=new HashMap<String, FunctionExecutor>();

	    public void register(String name,FunctionExecutor function){
	        context.put(name, function);
	    }

	   public Object call(String name,String from,String to){
	      return    context.get(name).execute(from, to);
	   }

	   public FunctionExecutor get(String name){
	      return context.get(name);
	   }
}
