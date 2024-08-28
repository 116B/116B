import java.io.*;
import java.util.Arrays;

// main 함수 내에서 static으로 선언하면 타 파일에 영향을 미치지 않음
class TrieNode_number{
    boolean word;
    TrieNode_number[] children;

    TrieNode_number() {
        word = false;
        children = new TrieNode_number[10];
    }
}

class Trie_number {
    TrieNode_number root;

    public Trie_number() {
        root = new TrieNode_number();
    }

    public void insert(Long num) {
        TrieNode_number curr = root;
        String str = Long.toString(num);
        for(char ch : str.toCharArray()) {
            if(curr.children[ch - '0'] == null)
                curr.children[ch - '0'] = new TrieNode_number();
            curr = curr.children[ch - '0'];
        }
        curr.word = true;
    }

    public boolean startsWith(Long num) {
        String str = Long.toString(num);
        TrieNode_number curr = root;
        for(char ch : str.toCharArray()) {
            if(curr.children[ch - '0'] == null)
                return false;
            curr = curr.children[ch - '0'];
        }
        return true;
    }
}

public class BOJ_전화번호_목록 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for(int tc = 1; tc <= T; tc++) {
            String result = "YES";
            int n = Integer.parseInt(br.readLine());
            Trie_number trieNumber = new Trie_number();

            Long[] numbers = new Long[n];

            for(int i = 0; i < n; i++) {
                numbers[i] = Long.parseLong(br.readLine());
            }

            Arrays.sort(numbers);

            for(int i = n - 1; i >= 0; i--) {
                if(trieNumber.startsWith(numbers[i])) {
                    System.out.println(": 접두사" + numbers[i]);
                    result = "NO";
                    break;
                }
                trieNumber.insert(numbers[i]);
            }

            System.out.println(result);

        }
    }
}
