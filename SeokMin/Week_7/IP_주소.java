package 비트마스킹;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.TreeSet;

public class BOJ_2064_IP_주소_정리 {

    static int n;
    static TreeSet<Long> binaryIpAddress;	// 2진수로 바뀐 ip 주소들을 저장할 TreeSet
    static long findPrefix = 0b1_00000000_00000000_00000000_00000000L;
    static long firstAdd;
    static long lastAdd;
    static int numOfCommonPrefix;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        binaryIpAddress = new TreeSet<>(new Comparator<Long>() {
        	public int compare(Long o1, Long o2) {
        		if (o1.equals(o2)) {
        			return 0;
        		}
        		return o1 > o2 ? 1 : -1;
        	};
		});
        for (int i = 0; i < n; i++) {
            // ip 주소를 2진수로 바꿔서 TreeSet에 순서대로 정렬
            binaryIpAddress.add(IpToBinary(br.readLine()));
        }

        // 2진수 ip 주소에서 가장 작은 값과 가장 큰 값의 xor 연산을 통해 공통된 부분의 개수를 구함
        firstAdd = binaryIpAddress.first();
        lastAdd = binaryIpAddress.last();
        long xor = firstAdd ^ lastAdd;
        
        // 오른쪽으로 i번 옮겨서 1이 나왔다면 앞의 (i-1)개의 숫자가 공통이라는 듯
        numOfCommonPrefix = 32;
        for (int i = 1; i <= 32; i++) {
        	if (((findPrefix >> i) & xor) != 0) {
        		numOfCommonPrefix = i - 1;
        		break;
        	}
        }
        
        long commonPrefix = ((1L << 32) - 1) & firstAdd;
        long binaryNetworkAddress = commonPrefix - (commonPrefix & ((1L << (32 - numOfCommonPrefix)) - 1));
        long binaryNetworkMask = ((1L << 32) - 1) ^ ((1L << (32 - numOfCommonPrefix)) - 1);
        
        System.out.println(binaryToIp(binaryNetworkAddress));
        System.out.println(binaryToIp(binaryNetworkMask));
    }   // main

    private static String binaryToIp(long binary) {
    	return Long.toString((binary >> 24) & 255) + "."
    			+ Long.toString((binary >> 16) & 255) + "."
    			+ Long.toString((binary >> 8) & 255) + "."
    			+ Long.toString(binary & 255);
	}	// binaryToIp

	// IP 주소를 2진수로 바꾸는 메소드
	private static long IpToBinary(String IP) {
		String[] netAdd = IP.split("\\.");
		long binary = 0L;
		for (String add : netAdd) {
			binary = (binary << 8) | Integer.parseInt(add);
		}
		
		return binary;
	}	// IpToBinary
}
