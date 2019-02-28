package no.games.learner;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.OperationBuilder;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

public class Learner {
	  public static void main(String[] args) throws Exception {
		   byte[] opList = TensorFlow.registeredOpList();
		    try (Graph g = new Graph()) {
		      final String value = "Hello from " + TensorFlow.version();
		     
		      // Construct the computation graph with a single operation, a constant
		      // named "MyConst" with a value "value".
		      OperationBuilder builder = null;
		      Operation operation1 = null;
		  
		      try (Tensor t = Tensor.create(value.getBytes("UTF-8"))) {
		        // The Java API doesn't yet include convenience functions for adding operations.
		    	// The nodes in the graph (the operations) represent units of computation, and the edges represent 
		    	// data consumed or produced by a computation. This is a Tensor.
		    	// The Tensor represent the edges.
		    	// A tensor is a mathematical representation of a physical entity that may be characterized by magnitude and multiple directions
		    	// A tensor of rank one represent a vector with one basis vector pr. component.  
		    	

		    	  // This creates an operation that produces the string value as output.
		    	g.opBuilder("Const", "MyConst").setAttr("dtype", t.dataType()).setAttr("value", t).build();
		        
		       }

		      int matrix[][] = { {1,2},{3,4} };
		      try (Tensor t2 = Tensor.create(matrix)){
		    	  int[][] copy = new int[2][2];
		    	  t2.copyTo(copy);
//		    	   System.out.println( t2.copyTo(copy));
		    	   
			    	builder =  g.opBuilder("matrix", "matrix"); // Added OLJ The builder adds operations to the graph
//			    	builder.setAttr("NUM", (long) 42.0);
//			    	builder.setAttr("dtype", t.dataType());	
			    	builder.setAttr("dtype", t2);// Added OLJ
			    	builder.setAttr("value", t2);				// Added OLj
			    	operation1 = builder.build();				// Added Olj An operation is a node in the graph
		      }

		      // Execute the "MyConst" operation in a Session.
		      try (Session s = new Session(g);
		          // Generally, there may be multiple output tensors,
		          // all of them must be closed to prevent resource leaks.
		          Tensor output = s.runner().fetch("MyConst").run().get(0)) {
		        System.out.println(new String(output.bytesValue(), "UTF-8"));
		      }
		    }
		  }
}
