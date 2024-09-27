
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class b14725_solve {



        static int N;

        public static void main(String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());
            StringTokenizer st;
            Node root = new Node();

            for(int i=0; i<N; i++){
                st = new StringTokenizer(br.readLine());
                int size = Integer.parseInt(st.nextToken());
                Node cur = root;

                for(int j=0; j<size; j++){
                    String s = st.nextToken();

                    if(!cur.childs.containsKey(s)){
                        cur.childs.put(s, new Node());
                    }
                    cur = cur.childs.get(s);
                }
            }
            print(root, "");
        }

        public static void print(Node root, String bar){
            Object[] key = root.childs.keySet().toArray();
            Arrays.sort(key);

            for (Object s : key){
                System.out.println(bar+s);
                print(root.childs.get(s),bar+"--");
            }
        }

    }

    class Node{
        HashMap<String, Node> childs = new HashMap<>();
    }

