import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class 자동차_테스트 {
	
	static int n, q;
	static ArrayList<Integer> arr;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		q = Integer.parseInt(st.nextToken());

		arr = new ArrayList<Integer>();		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			arr.add(Integer.parseInt(st.nextToken()));
		
		Collections.sort(arr);
	for (int i = 0; i < q; i++) {
            int pre = 0;
            int pos = 0;
            int idx = 0;
            int num = Integer.parseInt(br.readLine());
            
            idx = Collections.binarySearch(arr, num);
            pre = idx;
            pos = n - 1 - idx;
            if (idx < 0)
            	sb.append("0\n");
            else
            	sb.append(pre * pos).append("\n");
        }
		
		System.out.println(sb);
	}	// main
}
