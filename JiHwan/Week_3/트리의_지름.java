import java.io.*;
import java.util.*;

public class Main {
	
	private static int N, res;
	private static List<List<int[]>> lst;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        lst = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            lst.add(new ArrayList<>());
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            while (true) {
                int a = Integer.parseInt(st.nextToken());
                if (a == -1) break;
                int b = Integer.parseInt(st.nextToken());
                lst.get(s).add(new int[]{a, b});
            }
        }
        
        int sNum = 1;
        res = 0;
        bfs(bfs(sNum));
        System.out.println(res);
    }
    
    private static int bfs(int sNum) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{sNum, 0});
        
        int[] distance = new int[N+1];
        distance[sNum] = Integer.MAX_VALUE;
        int maxNum = 0;
        
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            
            for (int[] ints : lst.get(cur[0])) {
                if (distance[ints[0]] == 0) {
                    distance[ints[0]] = cur[1] + ints[1];
                    q.add(new int[]{ints[0], distance[ints[0]]});
                    if (maxNum < distance[ints[0]]) {
                        maxNum = distance[ints[0]];
                        sNum = ints[0];
                    }
                }
            }
        }
        
        res = maxNum;
        return sNum;
    }
}