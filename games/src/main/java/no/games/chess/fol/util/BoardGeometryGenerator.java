package no.games.chess.fol.util;

import aima.core.logic.fol.kb.FOLKnowledgeBase;
import aima.core.logic.fol.parsing.ast.Predicate;
import aima.core.logic.fol.parsing.ast.Constant;
import java.util.*;

/**
 * BoardGeometryGenerator
 * This class generates facts to the KB about which positions are between other positions
 * 
 */
public class BoardGeometryGenerator {
	private String pred;
	
    public BoardGeometryGenerator(String pred) {
		super();
		this.pred = pred;
	}

	/**
     * generateAllBetweenFacts
     * This method generates all BETWEEN fact to the KB
     * @param kb - The knowledge base
     */
    public void generateAllBetweenFacts(FOLKnowledgeBase kb) {
        String[] files = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] ranks = {"1", "2", "3", "4", "5", "6", "7", "8"};

        // Vi itererer gjennom alle mulige par av start (s1) og slutt (s2)
        for (int x1 = 0; x1 < 8; x1++) {
            for (int y1 = 0; y1 < 8; y1++) {
                for (int x2 = 0; x2 < 8; x2++) {
                    for (int y2 = 0; y2 < 8; y2++) {
                        
                        // Sjekk om det er en rett linje eller diagonal
                        int dx = x2 - x1;
                        int dy = y2 - y1;

                        if (isLineOrDiagonal(dx, dy)) {
                            generateFactsForLine(kb, x1, y1, x2, y2, files, ranks);
                        }
                    }
                }
            }
        }
    }

    private boolean isLineOrDiagonal(int dx, int dy) {
        return (dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy));
    }

    private void generateFactsForLine(FOLKnowledgeBase kb, int x1, int y1, int x2, int y2, String[] files, String[] ranks) {
        int dx = Integer.compare(x2 - x1, 0); // -1, 0, eller 1
        int dy = Integer.compare(y2 - y1, 0); // -1, 0, eller 1

        int currX = x1 + dx;
        int currY = y1 + dy;

        // Gå gjennom feltene mellom s1 og s2
        while (currX != x2 || currY != y2) {
            String mid = files[currX] + ranks[currY];
            String s1 = files[x1] + ranks[y1];
            String s2 = files[x2] + ranks[y2];

            // Legg til i KB som et predikat
            kb.tell(new Predicate(pred, Arrays.asList(
                new Constant(mid), new Constant(s1), new Constant(s2)
            )));

            currX += dx;
            currY += dy;
        }
    }
}
