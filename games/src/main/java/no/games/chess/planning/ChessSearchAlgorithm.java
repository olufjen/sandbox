
package no.games.chess.planning;

import aima.core.logic.fol.kb.data.Literal;
import aima.core.logic.planning.ActionSchema;
import aima.core.logic.planning.PlanningProblemFactory;
import aima.core.logic.planning.Problem;
import aima.core.logic.planning.State;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
/**
 * This class is a spesialized version of the 
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 11.5, page
 * 409.<br>
 * <br>
 * 
 * <pre>
 * function HIERARCHICAL-SEARCH(problem, hierarchy) returns a solution, or failure
 *  frontier ← a FIFO queue with [Act] as the only element
 *  loop do
 *    if EMPTY?(frontier) then return failure
 *    plan ← POP(frontier) / chooses the shallowest plan in frontier /
 *    hla ← the first HLA in plan,or null if none
 *    prefix,suffix ← the action subsequences before and after hla in plan
 *    outcome ← RESULT(problem.INITIAL-STATE,prefix)
 *    if hla is null then / so plan is primitive and outcome is its result /
 *      if outcome satisfies problem.GOAL then return plan
 *    else for each sequence in REFINEMENTS(hla,outcome,hierarchy)do
 *      frontier ← INSERT(APPEND(prefix,sequence,suffix),frontier)
 * </pre>
 * <p>
 * Figure 9.3 A breadth-first implementation of hierarchical forward planning
 * search. The initial plan supplied to the algorithm is [Act]. The REFINEMENTS
 * function returns a set of action sequences, one for each refinement of the
 * HLA whose preconditions are satisfied by the specified state, outcome.
 * 
 * @author samagra
 */
public class ChessSearchAlgorithm {
	private FileWriter fw =  null;

	private PrintWriter writer =  null;
	private ChessProblem problem;
	private List<ActionSchema> reserveplan = new ArrayList<ActionSchema>();
	private List<ActionSchema> allPrefix = null;

  public ChessSearchAlgorithm(FileWriter fw, PrintWriter writer) {
	super();
	this.fw = fw;
	this.writer = writer;
}

public FileWriter getFw() {
		return fw;
  }

  public void setFw(FileWriter fw) {
		this.fw = fw;
  }

  public PrintWriter getWriter() {
		return writer;
  }

  public void setWriter(PrintWriter writer) {
		this.writer = writer;
  }

  /**
   * function HIERARCHICAL-SEARCH(problem, hierarchy) returns a solution, or failure
   * 
   * @param problem The planning problem.
   * @return A list of actions representing the plan.
   */
  public List<ActionSchema> heirarchicalSearch(ChessProblem problem) {
        // frontier ← a FIFO queue with [Act] as the only element
	  this.problem = problem;
        Queue<List<ActionSchema>> frontier = new LinkedList<>();
        allPrefix = new ArrayList<ActionSchema>();
//        frontier.add(new ArrayList<>(Collections.singletonList(ChessPlanningProblemFactory.getHlaAct(problem))));
        ChessHighLevelAction hlax =  ChessPlanningProblemFactory.getHlaAct(problem);
        String content = hlax.toString();
        writer.println("\nInside chess search The HLA:\n");
        writer.println(content);
        writer.println("** End HLA **\n");
        frontier.add(Collections.singletonList(hlax));
    
        // loop do
        while (true) {
            // if EMPTY?(frontier) then return failure
            if (frontier.isEmpty()) {
            	writer.println("\nEnd chess search \n");
            	writer.flush();
            	return reserveplan;
//                return null;
            }
            // plan ← POP(frontier) /* chooses the shallowest plan in frontier */
            /*
             * At present the plan contains only one HLA action with refinements
             */
            List<ActionSchema> plan = frontier.poll();
/*
 * Added logic: If plan is empty but frontier is not then retrieve next plan olj 15.02.21            
 */
            boolean finish = false;
            if (plan.isEmpty() && !frontier.isEmpty()) {
            	plan = frontier.poll();
            	finish = true;
            }
            // hla ← the first HLA in plan, or null if none
            int i = 0;
            ActionSchema hla; 
            /*
             * This procedure make sure that the hla is a HLA schema
             */
            while (i < plan.size() && !(plan.get(i) instanceof ChessHighLevelAction))
                i++;
            if (i < plan.size() && !finish) // added the boolean finish olj 15.02.21
                hla = plan.get(i);
            else
                hla = null;
            // prefix,suffix ← the action subsequences before and after hla in plan
            
            List<ActionSchema> prefix = new ArrayList<>();
            List<ActionSchema> suffix = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                prefix.add(plan.get(j));
            }
            allPrefix.addAll(prefix);
            for (int j = i + 1; j < plan.size(); j++) {
                suffix.add(plan.get(j));
            }
            // outcome ← RESULT(problem.INITIAL-STATE, prefix)
//            State outcome = problem.getInitialState().result(prefix);
            State outcome = problem.getInitialState().result(allPrefix);
            // if hla is null then /* so plan is primitive and outcome is its result See pseudocode p. 409*/
            if (hla == null) {
                // if outcome satisfies problem.GOAL then return plan
                if (outcome.getFluents().containsAll(problem.getGoalState().getFluents())) {
                	List <Literal> goalfluents = outcome.getFluents();
                    List<Literal> statefluents = problem.getGoalState().getFluents();
                    writer.println(" Returns with a plan: The fluents of end outcome:\n");
                    for (Literal l: goalfluents) {
                        writer.println(l.toString());
                    }
                    writer.println("The fluents of goal state:\n");
                    for (Literal l: statefluents) {
                        writer.println(l.toString());
                    }
                    return plan;
                }else { // This is added logic: to make sure to return a spare plan
                    List <Literal> goalfluents = outcome.getFluents();
                    List<Literal> statefluents = problem.getGoalState().getFluents();
                    writer.println("The fluents of end outcome:\n");
                    for (Literal l: goalfluents) {
                        writer.println(l.toString());
                    }
                    writer.println("The fluents of goal state:\n");
                    for (Literal l: statefluents) {
                        writer.println(l.toString());
                    }
                	writer.println("\nEnd chess search with a reserve plan \n");
                	writer.flush();
                	return reserveplan;
                }
            } else {
            	 writer.println("\nHLA is not null \n");
                List<ActionSchema> tempInsertionList = new ArrayList<>();
                // else for each sequence in REFINEMENTS(hla, outcome, hierarchy) do
                for (List<ActionSchema> sequence :
                        refinements(hla, outcome)) {
                    // frontier ← INSERT(APPEND(prefix, sequence, suffix), frontier)
                    tempInsertionList.clear();
                    tempInsertionList.addAll(prefix);
                    tempInsertionList.addAll(sequence);
                    tempInsertionList.addAll(suffix);
                    ((LinkedList<List<ActionSchema>>) frontier).addLast(new ArrayList<>(tempInsertionList));
                }
                writer.println("\nEnd of insertion list \n");
            }
        }
  }

  /**
   * The REFINEMENTS function returns a set of action sequences,
   * one for each refinement of the HLA whose preconditions are
   * satisfied by the specified state, outcome.
   * 
   * @param hla     The hla to which the refinements are to be applied.
   * @param outcome The state in which the refinements are to be applied.
   * @return List of all refinements of the current hla in a given outcome state.
   */
  public List<List<ActionSchema>> refinements(ActionSchema hla,State outcome) {
        List<List<ActionSchema>> result = new ArrayList<>();
//        writer.println("The fluents of outcome: (= problem.getInitialState().result(prefix))\n");
        List<Literal> fluents = outcome.getFluents();
        List <Literal> goalfluents = problem.getGoalState().getFluents();
/*        for (Literal l: fluents) {
            writer.println(l.toString());
        }
        writer.println("The fluents of goalstate\n");
        for (Literal l: goalfluents) {
            writer.println(l.toString());
        }   */
        for (List<ActionSchema> refinement :
                ((ChessHighLevelAction) hla).getRefinements()) {
            if (refinement.size() > 0) {
                if (outcome.isApplicable(refinement.get(0)))
                    result.add(refinement);
            } else
                result.add(refinement);
        }
 //       writer.println("The refinements:\n");
        reserveplan.clear();
        for (List<ActionSchema> refinement :
            ((ChessHighLevelAction) hla).getRefinements()) {
        	for (ActionSchema schema:refinement) {
//        		writer.println(schema.toString());
        		reserveplan.add(schema);
        	}
        	
        }
        return result;
  }

}
