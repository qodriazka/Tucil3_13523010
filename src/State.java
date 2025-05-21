import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class State {
    char[][] board;
    int heuristic;
    Map<Character, Map<String, Integer>> pieces;
    List<Langkah> steps;
    Set<String> visited;

    public State(){
        this.board = new char[0][0];
        this.heuristic = 0;
        this.pieces = new HashMap<>();
        this.steps = new ArrayList<>();
        this.visited = new HashSet<>();
    }
    public State(char[][] board, int heuristic, Map<Character, Map<String, Integer>> pieces, List<Langkah> steps, Set<String> visited) {
        this.board = board;
        this.heuristic = heuristic;
        this.pieces = pieces;
        this.steps = steps;
        this.visited = visited;
    }
}
