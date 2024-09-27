package Trie;

import java.io.*;
import java.util.*;

public class BOJ_14725 {
	
	static class Node implements Comparable<Node> {
	    String Food;
	    TreeMap<String, Node> Room;

	    public Node(String Food) {
	        this.Food = Food;
	        this.Room = new TreeMap<>();
	    }

	    @Override
	    public int compareTo(Node other) {
	        return this.Food.compareTo(other.Food);
	    }
	}	// Node
	
	static int N;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st;
    	
        N = Integer.parseInt(br.readLine());

        Node root = new Node("");

        for (int i = 0; i < N; i++) {
            String[] path = br.readLine().split(" ");
            addPath(root, path, 1);
        }

        printNest(root, 0);
    }

    private static void addPath(Node node, String[] path, int index) {
        if (index >= path.length) return;

        String food = path[index];
        if (!node.Room.containsKey(food)) {
            node.Room.put(food, new Node(food));
        }
        
        addPath(node.Room.get(food), path, index + 1);
    }

    private static void printNest(Node node, int depth) {
        if (node.Food.length() > 0) {
            for (int i = 1; i < depth; i++) {
                System.out.print("--");
            }
            
            System.out.println(node.Food);
        }

        for (Node child : node.Room.values()) {
            printNest(child, depth + 1);
        }
    }
}