import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        String minIp = "11111111111111111111111111111111"; // 최소 IP
        String maxIp = "00000000000000000000000000000000"; // 최대 IP
        int m = 0;
        String networkMask = "11111111111111111111111111111111";

        for (int i = 0; i < n; i++) {
            String[] ipAddress = new String[4];
            ipAddress = br.readLine().split("\\.");

            StringBuilder tmp = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                int address = Integer.parseInt(ipAddress[j]);
                ipAddress[j] = Integer.toBinaryString(address);
                if (ipAddress[j].length() < 8) {
                    ipAddress[j] = String.format("%8s", ipAddress[j]).replace(' ', '0');
                }
                tmp.append(ipAddress[j]);
            }

            if (i == 0) {
                minIp = String.valueOf(tmp);
                maxIp = String.valueOf(tmp);
                continue;
            }

            boolean is_changed = false;
            for (int j = 0; j < 32; j++) {
                if (minIp.charAt(j) != tmp.charAt(j) && minIp.charAt(j) > tmp.charAt(j)) {
                    minIp = String.valueOf(tmp);
                    is_changed = true;
                }

                if (maxIp.charAt(j) != tmp.charAt(j) && maxIp.charAt(j) < tmp.charAt(j)) {
                    maxIp = String.valueOf(tmp);
                    is_changed = true;
                }

                if (maxIp.charAt(j) != tmp.charAt(j)) {
                    is_changed = true;
                }

                if (is_changed) {
                    for (int k = 0; k < 32; k++) if (minIp.charAt(k) != maxIp.charAt(k)) if (32 - k > m) m = 32 - k;
                    break;
                }
            }
        }
        minIp = minIp.substring(0, minIp.length() - m) + "0".repeat(m);
        networkMask = networkMask.substring(0, networkMask.length() - m) + "0".repeat(m);

        StringBuilder res = new StringBuilder();
        StringBuilder res2 = new StringBuilder();

        for (int i = 0; i < minIp.length(); i += 8) {
            String octet = minIp.substring(i, i + 8);
            String octet2 = networkMask.substring(i, i + 8);
            int decimal = Integer.parseInt(octet, 2);
            int decimal2= Integer.parseInt(octet2, 2);
            res.append(decimal);
            res2.append(decimal2);
            if (i < minIp.length() - 8) {
                res.append(".");
                res2.append(".");
            }
        }

        System.out.println(res);
        System.out.println(res2);

    }
}