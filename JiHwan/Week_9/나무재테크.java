import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
        int cnt = 0;
        
        int[][] ground = new int[N][N];
        int[][] winter = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
            	winter[i][j] = Integer.parseInt(st.nextToken());
            	ground[i][j] = 5;
            	
            }
        }
        
        List<PriorityQueue<Integer>> tree = new ArrayList<PriorityQueue<Integer>>();
        for (int i = 0; i < N*N; i++) {
        	tree.add(new PriorityQueue<Integer>());
        }
        
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken()) - 1;
            int k = Integer.parseInt(st.nextToken());
            
            tree.get(n*N + m).add(k);
        }
        
        for (int year = 0; year < K; year++) {
            // 봄은 나무들이 양분을 먹어요~
            // 양분이 있으면 양분을 먹고 1살 더 많아져요
            for (int i = 0; i < N; i++) {
            	for (int j = 0; j < N; j++) {
        			List<Integer> liveTree = new ArrayList<Integer>();
        			List<Integer> deadTree = new ArrayList<Integer>();
            		
        			while (!tree.get(i*N + j).isEmpty()) {
            			int age = tree.get(i*N + j).poll();
            			if (ground[i][j] >= age) {
            				ground[i][j] -= age;
            				liveTree.add(age + 1);
            			}
            			else deadTree.add(age);
        			}
            		for (int age : liveTree) tree.get(i*N + j).add(age);

                    // 여름에는 죽은 나무가 양분으로 변해요~
                    // 나무의 나이 / 2
            		for (int age : deadTree) ground[i][j] += age / 2;
            	}
            }
            
            // 가을에는 나무가 번식을 해요~
            for (int i = 0; i < N; i++) {
            	for (int j = 0; j < N; j++) {
            		for (int age : tree.get(i*N + j)) {
            			if (age % 5 == 0) {
            				for (int dir = 0; dir < 8; dir++) {
            					int ny = i + dy[dir];
            					int nx = j + dx[dir];
            					
            					if (ny < 0 || ny >= N || nx < 0 || nx >= N) continue;
            					tree.get(ny*N + nx).add(1);
            				}
            			}
            		}
            	}	
            }
            
            // 겨울에는 양분을 추가해요~
            for (int i = 0; i < N; i++) {
            	for (int j = 0; j < N; j++) {
            		ground[i][j] += winter[i][j];
            	}
            }
        }
        
        // 나무가 몇개 살아있는지 개수 세기
        for (int i = 0; i < N; i++) {
        	for (int j = 0; j < N; j++) {
        		cnt += tree.get(i*N + j).size();
        	}
        }
        
        System.out.println(cnt);
	}
}