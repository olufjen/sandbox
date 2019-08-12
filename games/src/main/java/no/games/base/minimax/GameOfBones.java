package no.games.base.minimax;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GameOfBones {
    /**
     * What is the declarative description of getPossiblestates?
     * Defines the possible moves of this game.
     * A player can remove 1,2 or 3 number of bones from the heap.
     * The method returns all possible moves in a List of Integers.
     * @param noOfBonesInHeap No of bones in the heap
     * .rangeClosed is range 1 to 3 inclusive
     * .boxed returns a Stream<Integer> from the array that
     * .map returns, which is then filtered
     * @return
     */
    static List<Integer> getPossibleStates(int noOfBonesInHeap) {
 //   	 System.out.println("\nStates ");
        return IntStream.rangeClosed(1, 3).boxed()
          .map(i -> {
//        	  System.out.println("Mapping " + i);
          return noOfBonesInHeap - i;
          })
          .filter(newHeapCount -> {
//        	  System.out.println("Left in heap " + newHeapCount);
           return newHeapCount >= 0;
          })
          .collect(Collectors.toList());
    }
}
