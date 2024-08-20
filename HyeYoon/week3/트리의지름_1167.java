package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

class Node{
    int end;
    int dis;
    public Node(int end, int dis){
        this.end = end;
        this.dis = dis;
    }
}

public class 트리의지름_1167 {

    static int V;
    static List<Node>[] tree;
    static int maxLen;
    static boolean[] visited;
    static int farNode=0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int V = Integer.parseInt(br.readLine());
        tree = new List[V+1];
        for(int i = 1; i <= V; i++){
            tree[i] = new LinkedList<>();
        }

        for(int v=1; v<=V; v++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            while(st.hasMoreTokens()){
                int end = Integer.parseInt(st.nextToken());
                if(end==-1){
                    break;
                }
                int dis = Integer.parseInt(st.nextToken());
                tree[start].add(new Node(end, dis));
                tree[end].add(new Node(start, dis)); // 무향 그래프이므로 양방향으로 추가
            }
        }

        // 초기화
        visited = new boolean[V+1];
        maxLen = 0;

        //임의의 한 노드에서 시작하여 가장 먼 노드를 찾기
        dfs(1, 0);

        // 초기화
        visited = new boolean[V+1];
        maxLen = 0;

        // 가장 먼 노드를 기준으로 다시 가장 먼 노드를 찾기
        dfs(farNode, 0);

        System.out.println(maxLen);
    }

    static void dfs(int curNode, int lenSum){

        visited[curNode] = true;

//        System.out.println("lenSum: " + lenSum);
        if(lenSum > maxLen){
            maxLen = lenSum;
            farNode = curNode;
        }

        for(Node next : tree[curNode]){
            if(!visited[next.end]){
                dfs(next.end, lenSum + next.dis);
            }
        }
    }
}
