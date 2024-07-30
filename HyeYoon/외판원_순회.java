import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 비트연산자는 &, | 모두 1번씩만 표기
// 결국 원래의 도시로 다시 돌아와야하므로, 어디서 출발하나 결국 똑같다.

public class Main {


    // 도시-도시 간 비용 행렬
    static int[][] map;

    // 현재 있는 도시가 i이고 이미 방문한 도시들의 집합이 j일 때,
    // 방문하지 않은 나머지 도시들을 모두 방문한 뒤
    // 출발 도시로 돌아올 때 드는 비용을 dp에 저장
    static int[][] dp;

    static int N;
    static int minCost = Integer.MAX_VALUE;
    static final int INF = 11000000; // 최대 비용(=1,000,000)보다 더 큰 값

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        map= new int[N][N];
        dp = new int[N][(1<<N)-1]; // 가능한 모든 방문상태는 0부터 (1<<N)-1까지.


        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<N; i++){
            Arrays.fill(dp[i], INF); // 아직 계산해보지 않은 경로는 최대 비용으로 표시해두기
        }

        System.out.println(travel(0,1, 0));
    }

    // cityIdx : 현재 위치한 도시 idx
    // visited : 지금까지 방문한 도시 비트마스킹
    static int travel(int cityIdx, int visited, int currentCost){

        // 0) 조기 종료 조건: 현재 비용이 이미 최소 비용을 초과하면 탐색 중단
        if (currentCost >= minCost) {
            return INF;
        }

        // 1)모든 도시를 방문했다면
        if(visited == (1<<N)-1){
            if(map[cityIdx][0]==0) return INF; // 현재 도시에서 출발 도시로 가는 경로가 존재하지 않는다면
            return map[cityIdx][0]; // 현재 도시 -> 0번째(시작) 도시 이동 비용
        }

        // 2) 이미 한 번 비용이 계산된 루트라면 (dp 값이 존재하는 경우)
        if(dp[cityIdx][visited]!= INF){
            return dp[cityIdx][visited]; // 그대로 반환
        }

        // 3) 현재 도시에서 각 n의 도시로 이동한 경우에 대해 DFS
        for(int n=0; n<N; n++){
            // visited & (1<<n) == 0 : 도시 n이 아직 방문되지 않았다면
            // map[cityIdx][n] != 0 : 현재 도시에서 도시 n으로의 경로가 존재한다면
            // map[cityIdx][n] != INF : 경로가 유효한 값이라면 (경로가 없는 경우를 처리)
            if( (visited & (1<<n)) ==0 && map[cityIdx][n] != 0 && map[cityIdx][n] != INF){
                // 다음 도시 n으로 방문한다고 가정하고,
                // dp[cityIdx][visited] vs 도시 n으로 이동 후 n부터 시작하는 경로의 최소 비용
                // 더 작은 값을 저장
                int newCost = currentCost + map[cityIdx][n] ;
                dp[cityIdx][visited] = Math.min(
                        dp[cityIdx][visited], // 현재까지 계산된 최소 비용
                        travel(n, visited | (1<< n), newCost) + map[cityIdx][n]); // 다음 도시로 이동한 후 비용
                // travel(n, visited | (1 << n)) : 도시 n을 방문했다고 표시하고, 도시 n에서의 최소 비용을 재귀적으로 계산
                // map[cityIdx][n] : 현재 도시에서 다음 도시 n까지의 비용을 더함
            }
        }



        return dp[cityIdx][visited];
    }
}
