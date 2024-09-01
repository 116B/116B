package Boj_5052;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            Trie trie = new Trie();
            boolean isConsistent = true;

            for (int j = 0; j < n; j++) {
                String phoneNumber = br.readLine();
                if (!trie.insertAndCheckConsistency(phoneNumber)) {
                    isConsistent = false;
                }
            }

            System.out.println(isConsistent ? "YES" : "NO");
        }
    }

    public static class Trie {
        private final TrieNode root = new TrieNode();

        public boolean insertAndCheckConsistency(String word) {
            TrieNode curNode = root;

            for (char ch : word.toCharArray()) {
                if (curNode.isLeaf()) {
                    return false; // 기존에 이 경로로 끝나는 번호가 있으므로 일관성 없음
                }
                TrieNode nextNode = curNode.getChildren().get(ch);
                if (nextNode == null) {
                    curNode.getChildren().put(ch, new TrieNode());
                    nextNode = curNode.getChildren().get(ch);
                }
                curNode = nextNode;
            }

            // 새로운 번호가 기존에 있던 번호의 접두어인 경우
            if (!curNode.getChildren().isEmpty()) {
                return false;
            }

            curNode.setLeaf(true);
            return true;
        }

        static class TrieNode {
            private final Map<Character, TrieNode> children = new HashMap<>();
            private boolean isLeaf;

            public Map<Character, TrieNode> getChildren() {
                return children;
            }

            public boolean isLeaf() {
                return isLeaf;
            }

            public void setLeaf(boolean leaf) {
                isLeaf = leaf;
            }
        }
    }
}
