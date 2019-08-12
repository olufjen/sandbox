package no.games.base.minimax;

public class Tree {
    private Node root;

    Tree() {
    }

    Node getRoot() {
        return root;
    }

    void setRoot(Node root) {
        this.root = root;
    }
    public String toString() {
         return root.toString();
    }

}
