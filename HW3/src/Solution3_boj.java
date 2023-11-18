import java.io.*;
import java.util.*;

public class Solution3_boj {
    final static int numTc = 10;
    static int[][] edges;
    static List<List<Edge>> adjEdges;
    static long[] distances;
    final static long INF = Long.MAX_VALUE;
    private static class Edge {
        int dst;
        int w;
        Edge(int dst, int w) {
            this.dst = dst;
            this.w = w;
        }
    }
    private static boolean naiveBellmanFord(int N, int E, int startV) {
        for (int i = 1; i <= N; i++) {
            distances[i] = INF;
        }
        distances[startV] = 0;

        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < E; j++) {
                int[] edge = edges[j];
                if (distances[edge[0]] != INF) {
                    distances[edge[1]] = Math.min(distances[edge[1]], distances[edge[0]] + edge[2]);
                }
            }
        }

        // check negative cycle
        for (int i = 0; i < E; i++) {
            if (distances[edges[i][0]] != INF && distances[edges[i][1]] > distances[edges[i][0]] + edges[i][2]) {
                return false;
            }
        }
        return true;
    }

    private static boolean improvedBellmanFord(int N, int E, int startV) {
        for (int i = 1; i <= N; i++) {
            distances[i] = INF;
        }
        distances[startV] = 0;

        boolean[] inQueue = new boolean[N + 1];
        int[] numVisited = new int[N + 1];
        Deque<Integer> q = new LinkedList<>();
        q.offer(startV);
        inQueue[startV] = true;
        numVisited[startV]++;

        while (!q.isEmpty()) {
            int v = q.poll();
            inQueue[v] = false;
            List<Edge> outEdges = adjEdges.get(v);
            for (Edge edge : outEdges) {
                if (distances[v] != INF && distances[edge.dst] > distances[v] + edge.w) {
                    distances[edge.dst] = distances[v] + edge.w;
                    if (!inQueue[edge.dst]) {
                        if (++numVisited[edge.dst] >= N) {
                            // neg cycle
                             return false;
                        }
                        q.offer(edge.dst);
                        inQueue[edge.dst] = true;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = 1;

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        edges = new int[E][3]; // src, dst, w
        adjEdges = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) {
            adjEdges.add(new ArrayList<>());
        }
        distances = new long[N + 1];

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            edges[i][0] = src;
            edges[i][1] = dst;
            edges[i][2] = w;
            adjEdges.get(src).add(new Edge(dst, w));

        }

        boolean success = improvedBellmanFord( N, E, 1);
        if (!success) {
            System.out.println(-1);
            return;
        }

        for (int i = 2; i <= N; i++) {
            if (distances[i] != INF) {
                System.out.println(distances[i]);
            } else {
                System.out.println(-1);
            }
        }
    }
}
