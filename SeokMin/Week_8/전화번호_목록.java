import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class 전화번호_목록2 {

    static int t, n;
    static String[] str;
    static PriorityQueue<String> pq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        t = Integer.parseInt(br.readLine());
        for (int T = 0; T < t; T++) {
            n = Integer.parseInt(br.readLine());
            str = new String[n];
            for (int i = 0; i < n; i++) {
                str[i] = br.readLine();
            }

            Arrays.sort(str);

            boolean flag = false;
            for (int i = 0; i < n - 1; i++) {
                if (str[i + 1].startsWith(str[i])) {
                    flag = true;
                    break;
                }
            }
            
            sb.append(flag ? "NO" : "YES").append("\n");
        }   // t에 대한 for문

        System.out.println(sb);
    }   // main
}
