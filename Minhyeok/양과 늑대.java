import java.util.*;

class Solution {
    static class Node {
        public int num;
        public boolean isSheep;
        public List<Node> next = new ArrayList<>();
        public Node prev;
        
        public Node() {}
        public Node(boolean isSheep) {
            this.isSheep = isSheep;
        }
    }
    
    static int sheep;
    static int wolf;
    static int answer;
    static Node[] nodes;
    public int solution(int[] info, int[][] edges) {
        answer = 0;
        sheep = 0;
        wolf = 0;
        int length = info.length;
        nodes = new Node[length];
        for (int i = 0; i < length; i++) {
            nodes[i] = new Node(info[i] == 0);
            nodes[i].num = i;
        }
        
        for (int[] arr : edges) {
            int prev = arr[0];
            int next = arr[1];
            nodes[prev].next.add(nodes[next]);
            nodes[next].prev = nodes[prev];
        }
        Deque<Integer> dq = new ArrayDeque<>();
        findMaxSheep(0, dq);
        
        return answer;
    }
    
    public void findMaxSheep(int start, Deque<Integer> deque) {
        if (nodes[start].isSheep) {
            sheep++;
        } else {
            wolf++;
        }
        if (sheep <= wolf) {
            if (nodes[start].isSheep) {
                sheep--;
            } else {
                wolf--;
            }
            return;
        }
        if (sheep > answer)
            answer = sheep;
        Deque<Integer> dq = new ArrayDeque<>(deque);

        // 자식 노드 추가
        for (int i = 0; i < nodes[start].next.size(); i++) {
            dq.offerLast(nodes[start].next.get(i).num);
        }
        
        for (int i = 0; i < dq.size(); i++) {
            int n = dq.pollFirst();
            findMaxSheep(n, dq);
            dq.offerLast(n);
        }
        
        if (nodes[start].isSheep) {
            sheep--;
        } else {
            wolf--;
        }
        
        
    }
}