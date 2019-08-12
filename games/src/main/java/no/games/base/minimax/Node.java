package no.games.base.minimax;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int noOfBones; // This is specific for the game. In this case represent the state of the game
    private boolean isMaxPlayer;
    private int score; // The score is 1 for a win and -1 for a loss
    private int nodeValue; // The value of the node is the path cost. See p. 70 AIMA
    private String nodeId = "";
    private String leafNode = "";
    private String gameStatus = "";
    private List<Node> children;
    private List<Node> lostChildren;
    private Node parent = null;
    private int i = 0; // Used for removing children
    
    public Node(int noOfBones, boolean isMaxPlayer) {
        this.noOfBones = noOfBones;
        this.isMaxPlayer = isMaxPlayer;
        children = new ArrayList<>();
        parent = null;
    }

    
    public int getNodeValue() {
		return nodeValue;
	}

    public boolean terminalNode() {
    	return noOfBones == 0;
    }
    public boolean winningGame() {
    	return terminalNode() && !isMaxPlayer;
    }
    public void moveChild(Node node) {
    	lostChildren = new ArrayList<Node>();
    	children.forEach(child -> {
    		if (node == child) {
    			lostChildren.add(node);
    		}
    	});
    	
    }
    public void cleanChildren() {
    	if (lostChildren != null) {
    		lostChildren.forEach(child -> {
    			children.forEach(node ->{
        			if (node == child) {
        				children.remove(i);
        			}
    			});
    			i++;
    		});
    	}
    }
	public void setNodeValue(int nodeValue) {
    	int ct = 0;
    	Node localParent = parent;
    	while (ct < 100 && localParent !=null) {
    		localParent = localParent.getParent();
    		ct++;
    	}
    	nodeValue = ct;
		this.nodeValue = nodeValue;
	}


	public Node getParent() {
		if (parent != null)
			return parent;
		return null;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getLeafNode() {
		return leafNode;
	}

	public void setLeafNode(String leafNode) {
		this.leafNode = leafNode;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getNoOfBones() {
        return noOfBones;
    }

    public boolean isMaxPlayer() {
        return isMaxPlayer;
    }

    public int getScore() {
        return score;
    }

    void setScore(int score) {
        if (isMaxPlayer && noOfBones == 0) {
        	score = -1;
        }
        if (!isMaxPlayer && noOfBones == 0) {
        	score = 1;
        }
        this.score = score;
    }

    public List<Node> getChildren() {
        return children;
    }

    void addChild(Node newNode) {
        children.add(newNode);
    }
    public String showParent() {
    	StringBuilder sb = new StringBuilder();
    	Node localParent = parent;
    	int ct = 0;
    	while (ct < 100 && localParent !=null) {
    		sb.append(localParent.toString());
    		localParent = localParent.getParent();
    		ct++;
    	}
    	sb.append(" Node value "+nodeValue);
     	return sb.toString();
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        leafNode = "";
        gameStatus = "";
        if (noOfBones == 0) {
        	leafNode = " Leaf Node ";
        }
        if (isMaxPlayer && noOfBones == 0) {
        	gameStatus = " Lost game";
        }
        if (!isMaxPlayer && noOfBones == 0) {
        	gameStatus = " Winning game";
        }
        
        sb.append("Node name "+nodeId+" Node value "+nodeValue+"\n No of bones: "+Integer.toString(noOfBones)+" No of Nodes "+Integer.toString(children.size())+" Score "+score+leafNode+gameStatus+ " Max player "+isMaxPlayer);
        int ix = 0;
        for (Node node:children) {
        	ix++;
        	sb.append("\n  |\n  |\n Child: of "+ nodeId+"\n "+node.toString());
        }
        sb.append(" ID "+super.toString());
        sb.append("\n");
        return sb.toString();
    }    

}
