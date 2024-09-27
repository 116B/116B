import java.util.*;

public class b14725 {

    static TreeSet<Node> treeSet;
    static Map<String, Node> nodeMap = new HashMap<>();

    static class Node {

        private String string;
        private TreeSet<Node> children = new TreeSet<>(childComparator);

        Node(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    static Comparator<Node> childComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            for (int i = 0; i < o1.string.length(); i++) {

                if (o1.string.charAt(i) != o2.string.charAt(i)) {
                    return Integer.compare(o1.string.charAt(i), o2.string.charAt(i));
                }
            }
            return 0;
        }
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        treeSet = new TreeSet<>(childComparator);

        int antNum = sc.nextInt();

        for (int i = 0; i < antNum; i++) {

            int num = sc.nextInt(); //지나가는 노드 개수

            String s = sc.next(); // 루트노드

            Node node = nodeMap.getOrDefault(s, new Node(s));
            nodeMap.put(s, node);
            treeSet.add(node);

            for (int j = 1; j < num; j++) {

                Node childNode = new Node(sc.next());
                node.children.add(childNode);
                node = childNode;
            }
        }
        for (Node node : treeSet) {
            recursion(node, 0);
        }


    }

    public static void recursion(Node node, int depth) {


        for (int j = 0; j < depth; j++) {
            System.out.print("--");
        }
        System.out.println(node.string);


        if (!node.children.isEmpty()){
            for(Node node1 :node.children){
                recursion(node1,depth+1);
            }
        }

    }
}


