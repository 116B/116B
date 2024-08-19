import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String[] binaryIPs = new String[N];

        for(int i = 0; i < N; i++) {
            String s = br.readLine();
            binaryIPs[i] = makeBinary(s);
        } // 2진수 변환 끝

        int n = countM(binaryIPs);
        int m = 32 - n; // r 업데이트 후 m 계산

        // 네트워크 주소 만들기
        // 뒤에 0 붙여야 함
        String networkAddressBinary = binaryIPs[0].substring(0, n);
        while(networkAddressBinary.length() < 32) {
            networkAddressBinary += "0";
        }
        String networkAddress = makeDecimal(networkAddressBinary);

        // 네트워크 마스크 생성
        StringBuilder networkMaskBinary = new StringBuilder();
        for(int i = 0; i < 32; i++) {
            if(i < n) networkMaskBinary.append('1');
            else networkMaskBinary.append('0');
        }
        String networkMask = makeDecimal(networkMaskBinary.toString());

        System.out.println(networkAddress);
        System.out.println(networkMask);
    }

    static String makeBinary(String s) {
        StringTokenizer st = new StringTokenizer(s, ".");
        StringBuilder sb = new StringBuilder();

        while(st.hasMoreTokens()) {
            int octet = Integer.parseInt(st.nextToken());
            String binaryString = Integer.toBinaryString(octet);
            while(binaryString.length() < 8) {
                binaryString = "0" + binaryString; // 앞에 0 붙이기
            }
            sb.append(binaryString);
        }

        return sb.toString();

    }

    static String makeDecimal(String s) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 4; i++) {
            String octetBinary = s.substring(i * 8, (i + 1) * 8);
            int octet = Integer.parseInt(octetBinary, 2);
            sb.append(octet);
            if(i < 3) sb.append(".");
        }
        return sb.toString();
    }

    static int countM(String[] arr) {

        int r = 0;

        if(arr == null || arr.length == 0) {
            return 0;
        }

        int length = arr[0].length();

        for(int i = 0; i < length; i++) {
            char ch = arr[0].charAt(i);
            boolean isSame = true;

            for(int j = 1; j < arr.length; j++) {
                if(arr[j].charAt(i) != ch) {
                    isSame = false;
                    break;
                }
            }
            if(!isSame) break;
            r++;
        }

        return r;
    }

    static String findMinIP(String[] arr) {
        String minIP = arr[0];
        for (String ip : arr) {
            for (int i = 0; i < ip.length(); i++) {
                if (ip.charAt(i) < minIP.charAt(i)) {
                    minIP = ip;
                    break;
                } else if (ip.charAt(i) > minIP.charAt(i)) {
                    break;
                }
            }
        }
        return minIP;
    }
}
