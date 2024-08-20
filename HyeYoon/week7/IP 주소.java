import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 네트워크 주소
 동일한 네트워크에 속한 모든 호스트가 공유하는 부분
 호스트 부분을 제외한 비트를 0 으로 설정한 주소
 */

/*
 서브넷 마스크
 네트워크 주소와 호스트 주소를 구분하기 위해 사용
 네트워크 부분을 1로, 호스트 부분을 0으로 설정
 */

public class Main {

    static void printIPAddress(StringBuilder sb, StringBuilder sbAddress) {
        sb.append(Integer.parseInt(sbAddress.subSequence(0, 8).toString(), 2));
        for (int i = 1; i < 4; i++) {
            sb.append("." + Integer.parseInt(sbAddress.subSequence(i * 8, i * 8 + 8).toString(), 2));
        }
        sb.append("\n");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        String ipList[][] = new String[N][4];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), ".");
            for (int j = 0; j < 4; j++) {
                int ipNumber = Integer.parseInt(st.nextToken());
                int ipBinary = Integer.parseInt(Integer.toBinaryString(ipNumber));
                ipList[i][j] = String.format("%08d", ipBinary);
            }
        }

        StringBuilder networkAddress = new StringBuilder();
        StringBuilder networkMask = new StringBuilder();
        
        
        // 0번째 ip 주소와 나머지 주소들을 비교
        boolean isDone = false;
        for (int i = 0; i < 4; i++) { // 0번째 주소의 4개의 바이트를 순회
            for (int j = 0; j < 8; j++) { // 각 바이트의 8개 비트를 순회
                char bit = ipList[0][i].charAt(j); // 0 번째 ip 주소의  i번째 바이트, j번째 비트를 가져옴
                for (int k = 1; k < N; k++) { // 나머지 주소들의 i번째 바이트, j번째 비트와 비교
                    char targetBit = ipList[k][i].charAt(j);
                    if (bit != targetBit) { // 비트가 다르다면
                        isDone = true; // 비교 종료
                        break;
                    }
                }
                if (isDone) { // 비트가 다른 경우 종료
                    break;
                } else { // 여러 개의 IP 주소를 비교할 때, 특정 위치의 비트가 모든 IP 주소에서 동일하다면,
                    // 그 비트는 해당 네트워크의 공통된 부분이라는 의미
                    networkAddress.append(bit);
                    networkMask.append("1");
                }
            }
            if (isDone) {
                break;
            }
        }
        
        // 남은 비트들을 '0'으로 채우기
        // 아직 채우지 못한 비트부터 시작
        for (int i = networkAddress.length(); i < 32; i++) {
            networkAddress.append("0");
            networkMask.append("0");
        }

        printIPAddress(sb, networkAddress);
        printIPAddress(sb, networkMask);

        System.out.print(sb.toString());

        br.close();
    }
}