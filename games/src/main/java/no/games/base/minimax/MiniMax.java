package no.games.base.minimax;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * In order to generalize:
 * Need an Interface for Tree, Node and MiniMax
 * We also need a Node Wrapper
 * 
 * @author Oluf Jensen
 *
 */
public class MiniMax {
    private Tree tree;
    private List<Node> winningGame;
    private List<Node> states;
    private String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private int pos = 1;
    private int newPos = 1;
    private int maxletter = letters.length;
    private String aLetter = "";
    public Tree getTree() {
        return tree;
    }
    

    public List<Node> getstates() {
		return states;
	}


	public void setstates(List<Node> states) {
		this.states = states;
	}


	public List<Node> getWinningGame() {
		return winningGame;
	}


	public void setWinningGame(List<Node> winningGame) {
		this.winningGame = winningGame;
	}


	/**
     * Contruct a tree with the given number of bones in heap.
     * This routine must be generalized
     * @param noOfBones
     */
    public void constructTree(int noOfBones) {
        tree = new Tree();
        
        Node root = new Node(noOfBones, true);
        root.setParent(null);
        root.setNodeId(letters[0]);
        tree.setRoot(root);
        System.out.println("Constructing root Player 1 to play "+ root.toString());
        constructTree(root);
    }

    /**
     * This routine constructs a tree of nodes based on the game No of bones in a heap.
     * This routine must be generalized, so that other games can be implemented    
     * @param parentNode
     */
    private void constructTree(Node parentNode) {
        List<Integer> listofPossibleHeaps = GameOfBones.getPossibleStates(parentNode.getNoOfBones());
        System.out.println("Possible heaps "+ listofPossibleHeaps);
        boolean isChildMaxPlayer = !parentNode.isMaxPlayer();
        listofPossibleHeaps.forEach(n -> {
            Node newNode = new Node(n, isChildMaxPlayer);
            if (pos >= maxletter) {
               	aLetter = letters[newPos]+letters[newPos];
               	newPos++;
               	if (newPos >= maxletter)
               		newPos = 1;
            }else
            	aLetter = letters[pos];
            newNode.setNodeId(aLetter);
            pos++;
            parentNode.addChild(newNode);
            newNode.setParent(parentNode);
            newNode.setNodeValue(0);
            int player = isChildMaxPlayer ? 1 : 2;
//            System.out.println("Adding child Player to play: "+player+" "+ newNode.toString());
            if (newNode.getNoOfBones() > 0) {
                constructTree(newNode);
            }
        });
//        System.out.println("Tree construction complete "+parentNode.toString());
    }

    public void findWinninggame(){
        Node root = tree.getRoot();
        winningGame = new ArrayList<Node>();
        winningGame.add(root);
//        winningGame =  new ArrayList<Node>();
        findWinninggame(root);
    }
    public void findLosinggame(){
        Node root = tree.getRoot();
        findLosinggame(root);
        root.cleanChildren();
    }
    /**
     * findWinninggame
     * @param node
     * When a node has a child that represent a win, then it also contains other children that represent a lost game!!
     * 
     */
    public void findLosinggame(Node node){
        List<Node> children = node.getChildren();
        boolean isMaxPlayer = node.isMaxPlayer();
        children.forEach(child -> {
            if (!child.winningGame()) {
            	node.moveChild(child);

            	System.out.println("Losing game moved: "+node.toString()+" Terminal node: "+child.toString()); 
            }else if (!child.terminalNode()) {
            	findLosinggame(child);
            }
            });
    }    
    /**
     * findWinninggame
     * @param node
     * When a node has a child that represent a win, then it also contains other children that represent a lost game!!
     * 
     */
    public void findWinninggame(Node node){
        List<Node> children = node.getChildren();
        boolean isMaxPlayer = node.isMaxPlayer();
        children.forEach(child -> {
            if (child.winningGame()) {
            	winningGame.add(node);
            	winningGame.add(child);
            	System.out.println("Winning game: "+node.toString()+" Terminal node: "+child.toString()); 
            }else if (!child.terminalNode()) {
            	findWinninggame(child);
            }
            });
    }
    public boolean checkWin() {
        Node root = tree.getRoot();
        states = new ArrayList<Node>();
        states.add(root);
        checkWin(root);
        return root.getScore() == 1;
    }

    private void checkWin(Node node) {
        List<Node> children = node.getChildren();
        boolean isMaxPlayer = node.isMaxPlayer();
        children.forEach(child -> {
            if (child.getNoOfBones() == 0) {
                child.setScore(isMaxPlayer ? -1 : 1); // Changed from 1 : -1 to -1 : 1 !!!! If it is the maxplayer turn when noofbones = 0, then maxplayer has lost
            } else {
                checkWin(child);
            }
        });
        Node bestChild = findBestChild(isMaxPlayer, children);
        node.setScore(bestChild.getScore());
        states.add(bestChild);
//        System.out.println("Player "+isMaxPlayer+" Best child "+bestChild.toString());
    }

    /**
     * findBestChild method finds the node with the maximum score if a player is a maximizer. 
     * Otherwise, it returns the child with the minimum score
     * @param isMaxPlayer
     * @param children
     * @return
     */
    private Node findBestChild(boolean isMaxPlayer, List<Node> children) {
        Comparator<Node> byScoreComparator = Comparator.comparing(Node::getScore);
//          .max(isMaxPlayer ? byScoreComparator : byScoreComparator.reversed())
//        .max(isMaxPlayer ? byScoreComparator.reversed() : byScoreComparator) // reversed !!!
        return children.stream()
        	.max(isMaxPlayer ? byScoreComparator : byScoreComparator.reversed())
          .orElseThrow(NoSuchElementException::new);
    }
    /**
     * findBestMove
     * This method find the best move based on a list of terminal nodes and their node value
     * @param nodeValue  maximum nodevalue
     * @param terminalNodes
     * @return a List of terminal nodes that represent a win based on node value
     */
    public List<Node> findBestMove(int nodeValue,List<Node> terminalNodes ) {

    return terminalNodes.stream()
    		.filter(n -> n.getNodeValue() < nodeValue).limit(3).collect(Collectors.toList());
      
    }
    public String toString() {
        return tree.toString();
   }
}
