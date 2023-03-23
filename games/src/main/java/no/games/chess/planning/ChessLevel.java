package no.games.chess.planning;

import aima.core.logic.fol.kb.data.Literal;
import aima.core.logic.planning.ActionSchema;
import aima.core.logic.planning.Utils;

import java.util.*;

/**This is a chess version of
 * The data structure for calculating and holding the levels of a planning graph.
 *
 * @author samagra
 * 
 * Oluf Jensen 8.3.23
 */
public class ChessLevel {
    private List<Object> levelObjects;
    HashMap<Object, List<Object>> mutexLinks;//can be planned alternatively
    HashMap<Object, List<Object>> nextLinks;
    HashMap<Object, List<Object>> prevLinks;// The prevLinks is a HashMap containing an empty List with the LevelObject as a key
    ChessProblem problem;
    ChessLevel prevLevel;
    private String message = "No message\n";
    
    public ChessLevel(ChessLevel prevLevel, ChessProblem problem) {
        this.prevLevel = prevLevel;
        this.problem = problem;
        if (prevLevel != null) {
            HashMap<Object, List<Object>> linksFromPreviousLevel = prevLevel.getNextLinks();
            this.problem = problem;
            levelObjects = new ArrayList<>();
            prevLinks = new HashMap<>();
            for (Object node :
                    linksFromPreviousLevel.keySet()) {
                List<Object> thisLevelObjects = linksFromPreviousLevel.get(node);
                for (Object nextNode :
                        thisLevelObjects) {
                    if (levelObjects.contains(nextNode)) {
                        List<Object> tempPrevLink = prevLinks.get(nextNode);
                        if (tempPrevLink != null) { // Added 20.3.23
                            tempPrevLink.add(node);
                            prevLinks.put(nextNode, tempPrevLink);
                        } else {
                        	message = "Prev link is null\n";
                        }

                    } else {
                        levelObjects.add(nextNode);
                        prevLinks.put(nextNode, new ArrayList<>(Collections.singletonList(node)));
                    }

                }

            }
            addNoPrecondActions();
            calculateNextLinks();
            calculateMutexLinks(prevLevel);
        } else { // If prevlevel is null
            levelObjects = new ArrayList<>();
            prevLinks = new HashMap<>();
            levelObjects.addAll(problem.getInitialState().getFluents()); // All fluents of the initial state is added to level objects
            for (Object obj :
                    levelObjects) {
                prevLinks.put(obj, new ArrayList<>()); // The prevLinks is a HashMap containing an empty List with the LevelObject as a key
            }
            addNoPrecondActions(); // Action schemas with no preconditions are added
            calculateNextLinks(); // Creates and fills the nextLinks HashMap
            calculateMutexLinks(null);
        }
        addPersistentActions();
    }

    public ChessLevel(ChessLevel prevLevel, ChessProblem problem, String extraLiterals){
        this(prevLevel, problem);
        this.addExtraLiterals(extraLiterals);
    }

    public void addExtraLiterals(String s){
        for (Literal literal :
                Utils.parse(s)) {
            if(!levelObjects.contains(literal)){
                levelObjects.add(literal);
            }
        }
        calculateNextLinks();
        calculateMutexLinks(getPrevLevel());
        addPersistentActions();
    }

    public List<Object> getLevelObjects() {
        return levelObjects;
    }

    public HashMap<Object, List<Object>> getMutexLinks() {
        return mutexLinks;
    }

    public HashMap<Object, List<Object>> getNextLinks() {
        return nextLinks;
    }

    public HashMap<Object, List<Object>> getPrevLinks() {
        return prevLinks;
    }

    public ChessProblem getProblem() {
        return problem;
    }

    private void addPersistentActions() {
       if(getLevelObjects().get(0) instanceof Literal) {
           for (Object literal :
                   getLevelObjects()) {
               ActionSchema action = new ActionSchema("No-op", null,
                       Collections.singletonList((Literal) literal),
                       Collections.singletonList((Literal) literal));
               addToHashMap(literal, action, nextLinks);
           }
       }
    }

    public void addNoPrecondActions(){
        if(getLevelObjects().get(0) instanceof ActionSchema){
            for (ActionSchema action :
                    problem.getGroundActions()) {
                if (action.getPrecondition().size()==0)
                    levelObjects.add(action);
            }
        }
    }



    private void calculateMutexLinks(ChessLevel prevLevel) {
        mutexLinks = new HashMap<>();
        if(prevLevel == null) return;
        if (levelObjects.get(0) instanceof Literal) {
            Literal firstLiteral, secondLiteral;
            List<Object> possibleActionsFirst, possibleActionsSecond;
            for (int i = 0; i < levelObjects.size(); i++) {
                firstLiteral = (Literal) levelObjects.get(i);
                possibleActionsFirst = prevLinks.get(firstLiteral);
                for (int j = i; j < levelObjects.size(); j++) {
                    secondLiteral = (Literal) levelObjects.get(j);
                    possibleActionsSecond = prevLinks.get(secondLiteral);
                    if (firstLiteral.getAtomicSentence().getSymbolicName().equals(
                            secondLiteral.getAtomicSentence().getSymbolicName()) &&
                            ((firstLiteral.isNegativeLiteral() && secondLiteral.isPositiveLiteral()) ||
                                    firstLiteral.isPositiveLiteral() && secondLiteral.isNegativeLiteral()
                            )) {

                        addToHashMap(firstLiteral, secondLiteral, mutexLinks);
                        addToHashMap(secondLiteral, firstLiteral, mutexLinks);
                    } else {
                        boolean eachPossiblePairExclusive = true;
                        HashMap<Object, List<Object>> prevMutexes = prevLevel.getMutexLinks();
                        for (Object firstAction :
                                possibleActionsFirst) {
                            for (Object secondAction :
                                    possibleActionsSecond) {
                                if ((!prevMutexes.containsKey(firstAction))||(!prevMutexes.get(firstAction).contains(secondAction))) {
                                    eachPossiblePairExclusive = false;
                                }
                            }
                        }
                        if (eachPossiblePairExclusive) {
                            addToHashMap(firstLiteral, secondLiteral, mutexLinks);
                            addToHashMap(secondLiteral, firstLiteral, mutexLinks);
                        }
                    }
                }
            }
        } else if (levelObjects.get(0) instanceof ActionSchema) {
            ActionSchema firstAction, secondAction;
            boolean checkMutex;

            for (int i = 0; i < levelObjects.size(); i++) {
                firstAction = (ActionSchema) levelObjects.get(i);
                List<Literal> firstActionEffects = firstAction.getEffects();
                List<Literal> firstActionPositiveEffects = firstAction.getEffectsPositiveLiterals();
                List<Literal> firstActionPreconditions = firstAction.getPrecondition();
                for (int j = i+1; j < levelObjects.size(); j++) {
                    checkMutex = false;
                    secondAction = (ActionSchema) levelObjects.get(j);
                    List<Literal> secondActionEffects = secondAction.getEffects();
                    List<Literal> secondActionNegatedLiterals = secondAction.getEffectsNegativeLiterals();
                    List<Literal> secondActionPreconditions = secondAction.getPrecondition();
                    for (Literal posLiteral :
                            firstActionPositiveEffects) {
                        for (Literal negatedLit :
                                secondActionNegatedLiterals) {
                            if (posLiteral.equals(new Literal(negatedLit.getAtomicSentence(),false))
                            ) {
                                checkMutex = true;
                            }
                        }
                    }
                    if (!checkMutex) {
                        if (checkInterference(secondActionPreconditions, firstActionEffects)) {
                            checkMutex = true;
                        }
                        if (checkInterference(firstActionPreconditions, secondActionEffects)) {
                            checkMutex = true;
                        }
                    }
                    if (!checkMutex) {
                        HashMap<Object, List<Object>> prevMutex = prevLevel.getMutexLinks();
                        if(prevMutex!=null) {
                            for (Literal firstActionPrecondition :
                                    firstActionPreconditions) {
                                for (Literal secondActionPrecondition :
                                        secondActionPreconditions) {
                                    if (prevMutex.get(firstActionPrecondition) != null && prevMutex.get(firstActionPrecondition).contains(secondActionPrecondition)) {
                                        checkMutex = true;
                                    }
                                }

                            }
                        }
                    }
                    if (checkMutex) {
                        addToHashMap(firstAction, secondAction, mutexLinks);
                        addToHashMap(secondAction, firstAction, mutexLinks);
                    }
                }

            }
        }
    }

    private boolean checkInterference(List<Literal> firstActionPreconditions, List<Literal> secondActionEffects) {
        boolean checkMutex = false;
        for (Literal secondActionEffect :
                secondActionEffects) {
            for (Literal firstActionPrecondition :
                    firstActionPreconditions) {
                if (secondActionEffect.equals(new Literal(firstActionPrecondition.getAtomicSentence(),firstActionPrecondition.isPositiveLiteral())))
                {
                        checkMutex = true;
                }

            }
        }
        return checkMutex;
    }

    private void addToHashMap(Object firstObject, Object secondObject, HashMap<Object, List<Object>> map) {
        List<Object> tempList;
        if (map.containsKey(firstObject)) {
            tempList = map.get(firstObject);
            tempList.add(secondObject);
            map.put(firstObject, tempList);
        } else {
            map.put(firstObject, new ArrayList<>(Collections.singletonList(secondObject)));
        }
    }

    private void calculateNextLinks() {
        nextLinks = new HashMap<>();
        if (levelObjects.get(0) instanceof Literal) { // IF Levelobject is of type Literal
            for (ActionSchema action :
            	problem.getGroundActions()) {
                if (levelObjects.containsAll(action.getPrecondition())) { // Is this Action applicable in S0 (LevelObject)?
                    List<Object> nextLevelNodes;
                    for (Literal literal :
                            action.getPrecondition()) {
                        if (nextLinks.containsKey(literal)) {
                            nextLevelNodes = nextLinks.get(literal);
                            nextLevelNodes.add(action);
                        } else {
                        	// All ground action schemas that might be applicable is added to the nextLinks HashMap
                            nextLevelNodes = new ArrayList<>(Collections.singletonList(action));
                        }
                        nextLinks.put(literal, nextLevelNodes);
                    }
                }

            }
        } else if (levelObjects.get(0) instanceof ActionSchema) {
            for (Object action : // If the levelObject is of type ActionSchema add the effects to the nextLinks HashMap
                    levelObjects) {
                Object[] effects =  ((ActionSchema) action).getEffects().toArray();
                nextLinks.put(action, new ArrayList<>(Arrays.asList(effects)));
            }
        }

    }

    public ChessLevel getPrevLevel() {
        return prevLevel;
    }
    public String printLevelObject() {
    	String level = message;
    	if (levelObjects.get(0) instanceof Literal) {
    		level = level +"Literals \n";
    		ArrayList<Literal>literals = new ArrayList(levelObjects);
    		for (Literal lit:literals) {
    			level = level+lit.toString();
    		}
    	}
    	if (levelObjects.get(0) instanceof ActionSchema) {
    		level = level +"Action Schemas \n";
    		ArrayList<ActionSchema>actions = new ArrayList(levelObjects);
    		for (ActionSchema act:actions) {
    			level = level+act.toString();
    		}
    	}
    	level = level + "\nPrevious level \n";
    	if (prevLevel != null)
    		level = level + prevLevel.printLevelObject();
    	else
    		level = level + "\nprevious level is empty";
    	int mx = mutexLinks.size() - 1;
    	level = level + "\n number of mutex " + mx;
    	return level;
    }

	@Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChessLevel))
            return false;
        return this.levelObjects.containsAll(((ChessLevel) obj).levelObjects)
                && ((ChessLevel) obj).levelObjects.containsAll(this.levelObjects)
                && this.mutexLinks.equals(((ChessLevel) obj).mutexLinks)
                && this.nextLinks.equals(((ChessLevel) obj).nextLinks)
                && this.prevLinks.equals(((ChessLevel) obj).prevLinks);
    }
}
