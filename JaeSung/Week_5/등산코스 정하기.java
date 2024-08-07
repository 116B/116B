import java.util.*;

class Solution {
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        
        int[] dist = new int[n + 1];
        
        Set<Integer> summitSet = new HashSet<>();
        for(int i : summits) {
            summitSet.add(i);
        }
        
        int INF = Integer.MAX_VALUE;
        
        Arrays.fill(dist, INF);
        
        for(int start : gates) {
            dist[start] = 0;
            
            PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<Integer, Integer>>() {
                public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                    return Integer.compare(e1.getValue(), e2.getValue());
                }
            });
            
            pq.offer(new AbstractMap.SimpleEntry<>(start, 0));
            
            while(!pq.isEmpty()) {
                Map.Entry<Integer, Integer> entry = pq.poll();
                int curr = entry.getKey();
                int currDist = entry.getValue();
                
                if(currDist > dist[curr]) continue;
                
                for(int[] path : paths) {
                    int u = path[0];
                    int v = path[1];
                    int w = path[2];
                    
                    if(curr == u || curr == v) {
                        int next = (curr == u) ? v : u;
                        int newDist = Math.max(dist[curr], w);
                        
                        if(summitSet.contains(curr)) continue;
                        
                        if(newDist < dist[next]) {
                            dist[next] = newDist;
                            pq.offer(new AbstractMap.SimpleEntry<>(next, newDist));
                        }
                        
                    }
                }
            }
        }
        
        int minIntensity = INF;
        int bestSummit = -1;
        Arrays.sort(summits);
        
        for(int summit : summits) {
            if(dist[summit] < minIntensity) {
                minIntensity = dist[summit];
                bestSummit = summit;
            }
        }
        return new int[]{bestSummit, minIntensity};
    }
}