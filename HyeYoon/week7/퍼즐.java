import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int[] dr= {-1, 1, 0, 0};
    static int[] dc= {0, 0, -1, 1};
    static String start = "";
    static String correct = "123456780";
    static int minMove = Integer.MAX_VALUE;
    static Map<String, Integer> currentMap = new HashMap<>(); // String : 현재 숫자 구성, Integer : 이동 횟수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        for(int i=0; i<3; i++){
            st =  new StringTokenizer(br.readLine());
            for(int j=0; j<3; j++){
                start += st.nextToken();
            }
        }

        currentMap.put(start, 0);
        changeNumber();
        if(minMove == Integer.MAX_VALUE ) System.out.println("-1");
        else System.out.println(minMove);

    }

    static void changeNumber(){

        Queue<String> q = new LinkedList<>();
        q.add(start);

        while(!q.isEmpty()){

            String curString = q.poll();
            int curMove = currentMap.get(curString);
            int blankIdx = curString.indexOf('0'); // 지금 '0'의 위치

            // blank의 r, c
            int br = blankIdx /3;
            int bc = blankIdx %3;

            // 종료 조건
            if(curString.equals(correct)){
                minMove = Math.min(minMove, curMove);
            }

            // 빈 칸을 기준으로 상하좌우에 있는 숫자들과 자리 바꿔보기
            for(int d=0; d<4; d++){
                int nr = br + dr[d];
                int nc = bc + dc[d];

                if(nr<0 || nc<0 || nr>=3 || nc>=3 ) continue;

                char targetNum = curString.charAt(nr*3 + nc);
                String nextString = curString.replace(targetNum, 't');
                nextString = nextString.replace('0', targetNum);
                nextString = nextString.replace('t', '0');

                // 중복 체크 후 큐 삽입
                if(!currentMap.containsKey(nextString)){
                    q.add(nextString);
                    currentMap.put(nextString, curMove+1);
                }
            }
        }
    }


}