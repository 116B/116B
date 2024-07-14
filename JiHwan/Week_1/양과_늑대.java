import java.util.*;

class Solution {
    private static int maxSheepCount;
    private static List<List<Integer>> GRAPH = new LinkedList<>();

    public int solution(int[] info, int[][] edges) {
        maxSheepCount = 0;
        for (int i = 0; i < info.length; i++) {
            GRAPH.add(new LinkedList<>());
        }

        for (int[] edge : edges) {
            GRAPH.get(edge[0]).add(edge[1]);
        }

        List<Integer> nextNodes = new LinkedList<>();
        nextNodes.add(0);

        dfs(info, nextNodes, 0, 0, 0);

        return maxSheepCount;
    }

    private void dfs(int[] info, List<Integer> nextNodes, int currentNode, int sheepCount, int wolfCount) {
        if (info[currentNode] == 0) {
            sheepCount += 1;
        } else {
            wolfCount += 1;
        }

        if (sheepCount <= wolfCount) {
            return;
        }

        maxSheepCount = Math.max(maxSheepCount, sheepCount);

        List<Integer> next = new ArrayList<>(nextNodes);
        
        if (!GRAPH.get(currentNode).isEmpty()) {
            next.addAll(GRAPH.get(currentNode));
        }
        next.remove(Integer.valueOf(currentNode));

        for (int nextNode : next) {
            dfs(info, next, nextNode, sheepCount, wolfCount);
        }
    }
}