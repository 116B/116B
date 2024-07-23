import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class JaeSung {
    static class Node {
        int to;
        int weight;

        Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static List<Node>[] graph;
    static boolean[] visited;
    static int maxDist = 0;
    static int maxNode = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int V = Integer.parseInt(br.readLine());

        graph = new ArrayList[V + 1];
        for(int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i = 0; i < V; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            while(true) {
                int adj = Integer.parseInt(st.nextToken());
                if(adj == -1) break;
                int weight = Integer.parseInt(st.nextToken());
                graph[node].add(new Node(adj, weight));
            }
        }

        visited = new boolean[V + 1];
        dfs(1, 0);

        visited = new boolean[V + 1];
        maxDist = 0;
        dfs(maxNode, maxDist);

        System.out.println(maxDist);
    }

    static void dfs(int currNode, int currDist) {
        visited[currNode] = true;

        if (currDist > maxDist) {
            maxDist = currDist;
            maxNode = currNode;
        }

        for (Node neighbor : graph[currNode]) {
            if (!visited[neighbor.to]) {
                dfs(neighbor.to, currDist + neighbor.weight);
                // currDist + neighbor.weight => 현재 노드 ~ 인접 노드까지 거리
            }
        }
    }
}