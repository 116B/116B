import java.util.*;

class Solution {
    int memos[];
    public int solution(int[] sales, int[][] links) {
        Node nodes[] = new Node[sales.length+1];
        for(int i = 0; i < sales.length; i++)
            nodes[i+1] = new Node(sales[i], i+1);

        for(int[] link : links)
            nodes[link[0]].add(nodes[link[1]]);


        memos = new int[sales.length+1];
        Arrays.fill(memos, -1);

        return recursion(nodes[1]);
    }



    int recursion(Node node){
        if(node == null) return 0;
        if(memos[node.num] != -1)
            return memos[node.num];
        if(node.childs.size() == 0) return 0;

        int min = Integer.MAX_VALUE>>1;


        int sum = 0;
        for(int i = 0; i < node.childs.size(); i++){
            sum = 0;
            for(int j = 0; j < node.childs.size(); j++){
                if(i == j) continue;
                sum += recursion(node.childs.get(j));
            }
            for(Node child : node.childs.get(i).childs) {
                sum += recursion(child);
            }
            min = Math.min(min, sum+node.childs.get(i).sales);
        }
        sum = 0;
        for(Node child : node.childs)
            sum += recursion(child);
        min = Math.min(min, sum+node.sales);
        memos[node.num] = min;
        return min;
    }


    class Node{
        int sales;
        int num;
        List<Node> childs = new ArrayList<>();
        Node(int sales, int num){
            this.sales = sales;
            this.num = num;
        }
        void add(Node node){
            childs.add(node);
        }

    }
}
